/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.data

import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.commonsense.feature.ontology.di.ontologyLocalPlatformModule
import org.pointyware.commonsense.feature.ontology.local.Persistence
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.fail

/**
 *
 */
class RecordsSqlDataSourceUnitTest {

    private lateinit var unitUnderTest: RecordsSqlDataSource

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(ontologyLocalPlatformModule())
        }
        val koin = getKoin()
        unitUnderTest = RecordsSqlDataSource(koin.get(), persistence = Persistence.InMemory)
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    /*
    suspend fun createRecord(name: String): Result<Type.Record>
    */

    @Test
    fun createRecord_should_throw_on_empty_name() {
        fail("Not implemented")
    }

    @Test
    fun createRecord_should_create_record() {
        fail("Not implemented")
    }

    /*
    suspend fun <T:Type> addField(original: Type.Record, name: String, type: T, defaultValue: Value<T>?): Result<Type.Record>
    */

    @Test
    fun addField_should_throw_on_empty_name() {
        fail("Not implemented")
    }

    @Test
    fun addField_should_throw_on_empty_type() {
        fail("Not implemented")
    }

    /*
    suspend fun getRecord(id: Uuid): Result<Type.Record>
    */

    @Test
    fun getRecord_should_return_known_record_for_known_uuid() {
        fail("Not implemented")
    }

    @Test
    fun getRecord_should_throw_for_unknown_uuid() {
        fail("Not implemented")
    }

    /*
    suspend fun createInstance(template: Type.Record): Result<Value.Instance>
    */

    @Test
    fun createInstance_should_create_a_new_empty_instance() {
        fail("Not implemented")
    }

    /*
    suspend fun <T:Type> setAttribute(original: Value.Instance, fieldName: String, value: Value<T>): Result<Value.Instance>
     */

    @Test
    fun setAttribute_should_set_field_for_valid_name() {
        fail("Not implemented")
    }

    @Test
    fun setAttribute_should_throw_for_invalid_field_name() {
        fail("Not implemented")
    }

    @Test
    fun setAttribute_should_throw_for_invalid_value_type() {
        fail("Not implemented")
    }
}
