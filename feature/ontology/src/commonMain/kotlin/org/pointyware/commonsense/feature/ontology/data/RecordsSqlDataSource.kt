/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.data

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
//        db.recordQueries.insertRecord(newUuid.bytes, name)
//        Type.Record(newUuid, name)
        TODO()
    }

    override suspend fun <T : Type> addField(
        recordId: org.pointyware.commonsense.core.common.Uuid,
        name: String,
        type: T,
        defaultValue: Value<T>
    ): Result<Type.Record> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecord(id: org.pointyware.commonsense.core.common.Uuid): Result<Type.Record> {
        TODO("Not yet implemented")
    }

    override suspend fun createInstance(
        recordId: org.pointyware.commonsense.core.common.Uuid,
        name: String
    ): Result<Value.Instance> {
        TODO("Not yet implemented")
    }

    override suspend fun <T : Type> setAttribute(
        instanceId: org.pointyware.commonsense.core.common.Uuid,
        fieldId: org.pointyware.commonsense.core.common.Uuid,
        value: Value<T>
    ): Result<Value.Instance> {
        TODO("Not yet implemented")
    }
}
