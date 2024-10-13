/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.data

import org.pointyware.commonsense.core.entities.Attribute
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
        val newUuid = Uuid.random()
        db.recordsQueries.createRecord(newUuid.toByteArray(), name)
        Type.Record(name, newUuid)
    }

    override suspend fun <T : Type> addField(
        original: Type.Record,
        name: String,
        type: T,
        defaultValue: Value<T>?
    ): Result<Type.Record> = runCatching {
        val recordId = original.uuid
        // TODO: check that value type matches given type
        // TODO: determine how to store default value
        db.recordsQueries.addField(recordId.toByteArray(), name, type.name, TODO())
        // TODO: db.recordQueries.createIntValue(defaultValue.value, recordId.toByteArray(), null)
        // TODO: db.recordQueries.createInstanceValue(defaultValue.value, recordId.toByteArray(), null)

        Type.Record("name", recordId)
    }

    override suspend fun getRecord(id: Uuid): Result<Type.Record> = runCatching {
        TODO("Not yet implemented")
    }

    override suspend fun createInstance(
        template: Type.Record,
    ): Result<Value.Instance> = runCatching {
        val newUuid = Uuid.random()
        db.recordsQueries.createInstance(template.uuid.toByteArray(), newUuid.toByteArray())
        Value.Instance(emptySet())
    }

    override suspend fun <T : Type> setAttribute(
        original: Value.Instance,
        fieldName: String,
        value: Value<T>
    ): Result<Value.Instance> = runCatching {
        val instanceId: Uuid = TODO("original.uuid")
        val recordId: Uuid = TODO("original.record.uuid")
        // TODO: db.recordsQueries.defineIntAttribute(instanceId.toByteArray(), recordId.toByteArray(), fieldName, value)
        val newAttribute: Attribute<T> = TODO("Construct new Attribute")
        Value.Instance(
            original.attributes + newAttribute
        )
    }
}
