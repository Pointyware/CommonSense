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

    override suspend fun createRecord(name: String): Result<Type.Record> = runCatching {
        require(name.matches("[a-zA-Z][a-zA-Z0-9_]*".toRegex())) { "Invalid record name: $name" }
        val newUuid = Uuid.random()
        db.recordsQueries.createRecord(newUuid.toByteArray(), name)
        Type.Record(name, newUuid)
    }

    @OptIn(ExperimentalValue::class, ExperimentalType::class)
    override suspend fun <T : Type> defineField(
        original: Type.Record,
        name: String,
        type: T,
        defaultValue: Value<T>?
    ): Result<Field<T>> = runCatching {
        val recordId = original.uuid
        when (type) {
            is Type.Int -> {
                defaultValue?.let {
                    if (defaultValue !is Value.IntValue) throw IllegalArgumentException("Expected Value.IntValue, got $defaultValue")
                    db.transaction {
                        db.recordsQueries.addIntField(recordId.toByteArray(), name, 1)
                        db.recordsQueries.setInstanceIntValue(Uuid.NIL.toByteArray(), original.uuid.toByteArray(), name, defaultValue.rawValue.toLong())
                    }
                } ?: run {
                    db.recordsQueries.addIntField(recordId.toByteArray(), name, 0)
                }
            }
            is Type.Float -> {
                defaultValue?.let {
                    if (defaultValue !is Value.RealValue) throw IllegalArgumentException("Expected Value.RealValue, got $defaultValue")
                    db.transaction {
                        db.recordsQueries.addFloatField(recordId.toByteArray(), name, 1)
//                        db.recordsQueries.setInstanceTextValue(Uuid.NIL.toByteArray(), original.uuid.toByteArray(), name, defaultValue.rawValue.toLong())
                        TODO("setInstanceTextValue")
                    }
                } ?: run {
                    db.recordsQueries.addFloatField(recordId.toByteArray(), name, 0)
                }
            }
            is Type.String -> {
                defaultValue?.let {
                    if (defaultValue !is Value.StringValue) throw IllegalArgumentException("Expected Value.StringValue, got $defaultValue")
                    db.transaction {
                        db.recordsQueries.addTextField(recordId.toByteArray(), name, 1)
//                        db.recordsQueries.setInstanceStringValue(Uuid.NIL.toByteArray(), original.uuid.toByteArray(), name, defaultValue.rawValue.toLong())
                        TODO("setInstanceStringValue")
                    }
                } ?: run {
                    db.recordsQueries.addTextField(recordId.toByteArray(), name, 0)
                }
            }
            is Type.Record -> {
                defaultValue?.let {
                    if (defaultValue !is Value.Instance) throw IllegalArgumentException("Expected Value.Instance, got $defaultValue")
                    db.transaction {
                        db.recordsQueries.addRecordField(recordId.toByteArray(), name, 1)
                        db.recordsQueries.setRecordFieldType(original.uuid.toByteArray(), name, type.uuid.toByteArray())
//                        db.recordsQueries.setInstanceRecordValue(Uuid.NIL.toByteArray(), original.uuid.toByteArray(), name, defaultValue.id.toByteArray())
                        TODO("setInstanceRecordValue")
                    }
                } ?: run {
                    db.transaction {
                        db.recordsQueries.addRecordField(recordId.toByteArray(), name, 0)
                        db.recordsQueries.setRecordFieldType(original.uuid.toByteArray(), name, type.uuid.toByteArray())
                    }
                }
            }
            else -> throw IllegalArgumentException("Unsupported type: $type")
        }

        Field(name, type, defaultValue)
    }

    override suspend fun getRecord(id: Uuid): Result<Type.Record> = runCatching {
        db.recordsQueries.getRecord(id.toByteArray()).executeAsOne().let {
            Type.Record(it.name, Uuid.fromByteArray(it.uuid))
        }
    }

    override suspend fun createInstance(
        template: Type.Record,
    ): Result<Value.Instance> = runCatching {
        val newUuid = Uuid.random()
        db.recordsQueries.createInstance(template.uuid.toByteArray(), newUuid.toByteArray())
        Value.Instance(newUuid, template, emptyMap())
    }

    override suspend fun <T : Type> setFieldValue(
        original: Value.Instance,
        field: Field<T>,
        value: Value<T>
    ): Result<Value.Instance> = runCatching {
        val instanceId: Uuid = TODO("original.uuid")
        val recordId: Uuid = TODO("original.record.uuid")
        // TODO: db.recordsQueries.defineIntAttribute(instanceId.toByteArray(), recordId.toByteArray(), fieldName, value)
        Value.Instance(
            instanceId,
            original.type,
            original.values + (field to value)
        )
    }
}
