/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.data

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.commonsense.core.entities.Type
import org.pointyware.commonsense.core.entities.Value
import org.pointyware.commonsense.feature.ontology.di.ontologyLocalPlatformModule
import org.pointyware.commonsense.feature.ontology.local.Persistence
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.DefaultAsserter.assertNotEquals
import kotlin.test.DefaultAsserter.assertTrue
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.fail
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 *
 */
@OptIn(ExperimentalUuidApi::class)
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
    fun createRecord_should_throw_on_empty_name() = runTest {
        val recordName = ""

        assertFailsWith<IllegalArgumentException> { runBlocking {
            unitUnderTest.createRecord(recordName)
        } }
    }

    @Test
    fun createRecord_should_create_record() = runTest {
        val validName = "record"

        val result = unitUnderTest.createRecord(validName).getOrThrow()

        assertEquals("Record name should match given name",
            validName, result.name)
        assertNotEquals("Record should have non-null UUID",
            Uuid.NIL, result.uuid)
        assertTrue("Record should have no fields",
            result.fields.isEmpty())
    }

    /*
    suspend fun <T:Type> addField(original: Type.Record, name: String, type: T, defaultValue: Value<T>?): Result<Type.Record>
    */

    @Test
    fun addField_should_throw_on_empty_name() = runTest {
        val recordName = "record"
        val record = unitUnderTest.createRecord(recordName).getOrThrow()
        val emptyName = ""

        assertFailsWith<IllegalArgumentException> { runBlocking {
            unitUnderTest.addField(record, emptyName, Type.Int, Value.IntValue(0))
        } }
    }

    @Test
    fun addField_should_register_field_with_given_name_type_and_value() = runTest {
        val recordName = "record"
        val record = unitUnderTest.createRecord(recordName).getOrThrow()
        val fieldName = "foo"

        val recordInstance = unitUnderTest.addField(record, fieldName, Type.Int, Value.IntValue(0)).getOrThrow()

        assertEquals("Record should have a single field.",
            1, recordInstance.fields.size)
        val field = recordInstance.fields.first()
        assertEquals("Field name should match given name.",
            fieldName, field.name)
        assertEquals("Field type should match given type.",
            Type.Int, field.type)
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
