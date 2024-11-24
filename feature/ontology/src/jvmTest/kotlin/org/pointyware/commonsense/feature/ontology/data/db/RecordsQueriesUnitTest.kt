/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.data.db

import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.commonsense.core.local.db.createOrMigrate
import org.pointyware.commonsense.feature.ontology.db.OntologyDb
import org.pointyware.commonsense.feature.ontology.db.RecordsQueries
import org.pointyware.commonsense.feature.ontology.di.ontologyLocalPlatformModule
import org.pointyware.commonsense.feature.ontology.local.DriverFactory
import org.pointyware.commonsense.feature.ontology.local.Persistence
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 *
 */
@OptIn(ExperimentalUuidApi::class)
class RecordsQueriesUnitTest {

    private lateinit var unitUnderTest: RecordsQueries

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(ontologyLocalPlatformModule())
        }
        val koin = getKoin()
        val driverFactory = koin.get<DriverFactory>()
        val driver = driverFactory.createSqlDriver(Persistence.InMemory)
        OntologyDb.Schema.createOrMigrate(driver)
        unitUnderTest = RecordsQueries(driver)
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun getIntFieldValue_should_return_default_for_null_instance() {
        val defaultValue = 42L
        val recordId = Uuid.random().toByteArray()
        val nullId = Uuid.NIL.toByteArray()
        unitUnderTest.createRecord(recordId, "foobar")
        unitUnderTest.addIntField(recordId, "field", 1)
        unitUnderTest.createInstance(recordId, nullId, nullId)
        unitUnderTest.setInstanceIntValue(nullId, recordId, "field", defaultValue)

        val result = unitUnderTest.getIntFieldValue(nullId, recordId, "field").executeAsOneOrNull()

        assertEquals(defaultValue, result, "Int Field should have $defaultValue default value")
    }
}
