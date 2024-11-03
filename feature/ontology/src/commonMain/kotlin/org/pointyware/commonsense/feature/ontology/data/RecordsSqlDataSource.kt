/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.data

import org.pointyware.commonsense.core.entities.ExperimentalType
import org.pointyware.commonsense.core.entities.ExperimentalValue
import org.pointyware.commonsense.core.entities.Field
import org.pointyware.commonsense.core.entities.Type
import org.pointyware.commonsense.core.entities.Value
import org.pointyware.commonsense.core.local.db.createOrMigrate
import org.pointyware.commonsense.feature.ontology.db.OntologyDb
import org.pointyware.commonsense.feature.ontology.local.DriverFactory
import org.pointyware.commonsense.feature.ontology.local.Persistence
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 *
 */
@OptIn(ExperimentalUuidApi::class)
class RecordsSqlDataSource(
    private val driverFactory: DriverFactory,
    private val persistence: Persistence = Persistence.File
): RecordsDataSource {

    private val driver = driverFactory.createSqlDriver(persistence)
    private val db by lazy {
        OntologyDb.Schema.createOrMigrate(driver)
        OntologyDb(driver)
    }

    private val recordNamePattern = "[a-zA-Z][a-zA-Z0-9_]*".toRegex()
    override suspend fun createRecord(name: String): Result<Type.Record> = runCatching {
        require(name.matches(recordNamePattern)) { "Invalid record name: $name" }
        val newUuid = Uuid.random()
        db.recordsQueries.createRecord(newUuid.toByteArray(), name)
        Type.Record(name, newUuid)
    }

    override suspend fun <T : Type> defineField(
        original: Type.Record,
        name: String,
        type: T,
        defaultValue: Value<T>?
    ): Result<Field<T>> = runCatching {
        require(name.isNotEmpty()) { "Field name cannot be empty" }
        val recordId = original.uuid
        when (type) {
            is Type.Int -> {
                defaultValue?.let {
                    if (defaultValue !is Value.IntValue) throw IllegalArgumentException("Expected Value.IntValue, got $defaultValue")
                    db.transaction {
                        db.recordsQueries.addIntField(recordId.toByteArray(), name, 1)
                        db.recordsQueries.createInstance(recordId.toByteArray(), Uuid.NIL.toByteArray())
                        db.recordsQueries.setInstanceIntValue(Uuid.NIL.toByteArray(), recordId.toByteArray(), name, defaultValue.rawValue.toLong())
                    }
                } ?: run {
                    db.recordsQueries.addIntField(recordId.toByteArray(), name, 0)
                }
            }
//            is Type.Float -> {
//                defaultValue?.let {
//                    if (defaultValue !is Value.RealValue) throw IllegalArgumentException("Expected Value.RealValue, got $defaultValue")
//                    db.transaction {
//                        db.recordsQueries.addFloatField(recordId.toByteArray(), name, 1)
////                        db.recordsQueries.setInstanceTextValue(Uuid.NIL.toByteArray(), original.uuid.toByteArray(), name, defaultValue.rawValue.toLong())
//                        TODO("setInstanceTextValue")
//                    }
//                } ?: run {
//                    db.recordsQueries.addFloatField(recordId.toByteArray(), name, 0)
//                }
//            }
//            is Type.String -> {
//                defaultValue?.let {
//                    if (defaultValue !is Value.StringValue) throw IllegalArgumentException("Expected Value.StringValue, got $defaultValue")
//                    db.transaction {
//                        db.recordsQueries.addTextField(recordId.toByteArray(), name, 1)
////                        db.recordsQueries.setInstanceStringValue(Uuid.NIL.toByteArray(), original.uuid.toByteArray(), name, defaultValue.rawValue.toLong())
//                        TODO("setInstanceStringValue")
//                    }
//                } ?: run {
//                    db.recordsQueries.addTextField(recordId.toByteArray(), name, 0)
//                }
//            }
//            is Type.Record -> {
//                defaultValue?.let {
//                    if (defaultValue !is Value.Instance) throw IllegalArgumentException("Expected Value.Instance, got $defaultValue")
//                    db.transaction {
//                        db.recordsQueries.addRecordField(recordId.toByteArray(), name, 1)
//                        db.recordsQueries.setRecordFieldType(original.uuid.toByteArray(), name, type.uuid.toByteArray())
////                        db.recordsQueries.setInstanceRecordValue(Uuid.NIL.toByteArray(), original.uuid.toByteArray(), name, defaultValue.id.toByteArray())
//                        TODO("setInstanceRecordValue")
//                    }
//                } ?: run {
//                    db.transaction {
//                        db.recordsQueries.addRecordField(recordId.toByteArray(), name, 0)
//                        db.recordsQueries.setRecordFieldType(original.uuid.toByteArray(), name, type.uuid.toByteArray())
//                    }
//                }
//            }
            else -> throw IllegalArgumentException("Unsupported type: $type")
        }

        Field(name, type, defaultValue)
    }

    override suspend fun getRecord(id: Uuid): Result<Type.Record> = runCatching {
        db.recordsQueries.getRecord(id.toByteArray()).executeAsOneOrNull().let {
            require(it != null) { "Record not found: $id" }
            val fields = db.recordsQueries.getRecordFields(id.toByteArray()).executeAsList().map { fieldRow ->
                val fieldName = fieldRow.fieldName
                val type = when (fieldRow.typeName) {
                    "int" -> Type.Int
//                    "float" -> Type.Float
//                    "text" -> Type.String
//                    "record" -> {
//                        TODO("Get Field Record SubType")
//                        Type.Record(TODO("subtype name"), TODO("subtype uuid"))
//                    }
                    else -> throw IllegalArgumentException("Unsupported field type: ${fieldRow.typeName}")
                }
                val default = if (fieldRow.hasDefault > 0) {
                    when (type) {
                        Type.Int -> {
                            db.recordsQueries.getIntField(Uuid.NIL.toByteArray(), id.toByteArray(), fieldName).executeAsOneOrNull()?.let { value ->
                                Value.IntValue(value.toInt()) // TODO: expand IntValue to support Long or add LongValue type?
                            }
                        }
//                        Type.Float -> {
//                            TODO("Get Field Float Default Value")
//                        }
//                        Type.String -> {
//                            TODO("Get Field String Default Value")
//                        }
//                        is Type.Record -> {
//                            TODO("Get Field Record Default Value")
//                            Value.Instance(TODO("Instance Value"), type, TODO("Fetch Instance Values from record"))
//                        }
                        else -> throw IllegalArgumentException("Unsupported type: $type")
                    }
                } else { null }
                Field(fieldName, type, default)
            }.toSet()

            Type.Record(it.name, Uuid.fromByteArray(it.uuid), fields)
        }
    }

    private fun setIntValue(instanceId: Uuid, recordId: Uuid, field: Field<*>, value: Value.IntValue) {
        require(field.type is Type.Int) { "Field type must be Type.Int" }
        db.recordsQueries.setInstanceIntValue(instanceId.toByteArray(), recordId.toByteArray(), field.name, value.rawValue.toLong())
    }

    override suspend fun createInstance(
        template: Type.Record,
    ): Result<Value.Instance> = runCatching {
        val newUuid = Uuid.random()
        db.recordsQueries.createInstance(template.uuid.toByteArray(), newUuid.toByteArray())
        val fieldMap = template.fields.flatMap { field ->
            field.defaultValue?.let { default ->
                when (default) {
                    is Value.IntValue -> setIntValue(newUuid, template.uuid, field, default)
//                    is Value.RealValue -> TODO()
//                    is Value.StringValue -> TODO()
//                    is Value.Instance -> TODO()
                    else -> throw IllegalArgumentException("Unsupported default value: $default")
                }
            }
            field.defaultValue?.let {
                listOf(field to it)
            } ?: emptyList()
        }.toMap()

        Value.Instance(newUuid, template, fieldMap)
    }

    override suspend fun <T : Type> setFieldValue(
        original: Value.Instance,
        field: Field<T>,
        value: Value<*>
    ): Result<Value.Instance> = runCatching {
        val instanceId = original.id
        val recordId = original.type.uuid
        when (value) {
            is Value.IntValue -> setIntValue(instanceId, recordId, field, value)
//            is Value.RealValue -> TODO()
//            is Value.StringValue -> TODO()
//            is Value.Instance -> TODO()
            else -> throw IllegalArgumentException("Unsupported value: $value")
        }
        Value.Instance(
            instanceId,
            original.type,
            original.values + (field to value)
        )
    }
}
