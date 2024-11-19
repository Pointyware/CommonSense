/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.data

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
    fun createRecord_should_throw_on_invalid_names() = runTest {
        listOf(
            "a record",
            "a-record",
            "1record",
            "record!",
            "record@",
            " ",
            "",
        ).forEach { recordName ->
            val result = unitUnderTest.createRecord(recordName)
            assertTrue("Result should be an error",
                result.isFailure)
            assertEquals("Result error should be IllegalArgumentException",
                IllegalArgumentException::class, result.exceptionOrNull()!!::class)
        }
    }

    @Test
    fun createRecord_should_create_record_for_valid_names() = runTest {
        listOf(
            "record",
            "Record",
            "record1",
            "record_1",
        ).forEach { recordName ->
            val result = unitUnderTest.createRecord(recordName).getOrThrow()

            assertEquals("Record name should match given name",
                recordName, result.name)
            assertNotEquals("Record should have non-null UUID",
                Uuid.NIL, result.uuid)
            assertTrue("Record should have no fields",
                result.fields.isEmpty())
        }
    }

    /*
    suspend fun <T:Type> addField(original: Type.Record, name: String, type: T, defaultValue: Value<T>?): Result<Type.Record>
    */

    @Test
    fun addField_should_throw_on_empty_name() = runTest {
        val recordName = "record"
        val record = unitUnderTest.createRecord(recordName).getOrThrow()
        val emptyName = ""

        val result = unitUnderTest.defineField(record, emptyName, Type.Int, Value.IntValue(0))

        assertTrue("Result should be an error",
            result.isFailure)
        assertEquals("Result error should be IllegalArgumentException",
            IllegalArgumentException::class, result.exceptionOrNull()!!::class)
    }

    @Test
    fun addField_should_register_field_with_given_name_type_and_value() = runTest {
        val recordName = "record"
        val record = unitUnderTest.createRecord(recordName).getOrThrow()
        val fieldName = "foo"

        val field = unitUnderTest.defineField(record, fieldName, Type.Int, Value.IntValue(0)).getOrThrow()
        val updatedRecord = unitUnderTest.getRecord(record.uuid).getOrThrow()

        assertEquals("Record should have a single field.",
            1, updatedRecord.fields.size)
        assertEquals("Field name should match given name.",
            fieldName, field.name)
        assertEquals("Field type should match given type.",
            Type.Int, field.type)
        assertEquals("Field default should match given value.",
            Value.IntValue(0), field.defaultValue)
        assertEquals("Returned record field should match created field.",
            field, updatedRecord.fields.first())
    }

    /*
    suspend fun getRecord(id: Uuid): Result<Type.Record>
    */

    @Test
    fun getRecord_should_return_known_record_for_known_uuid() = runTest {
        val recordName = "aRecord"
        val record = unitUnderTest.createRecord(recordName).getOrThrow()
        val generatedId = record.uuid

        val retrievedRecord = unitUnderTest.getRecord(generatedId).getOrThrow()

        assertEquals("Retrieved record should match created record",
            record, retrievedRecord)
    }

    @Test
    fun getRecord_should_throw_for_unknown_uuid() = runTest {
        val recordName = "aRecord"
        val record = unitUnderTest.createRecord(recordName).getOrThrow()
        val generatedId = record.uuid
        val mutatedId = with(generatedId.toByteArray()) {  this[0] = (this[0] + 1).toByte(); Uuid.fromByteArray(this) }

        val retrievedRecord = unitUnderTest.getRecord(mutatedId)

        assertTrue("Result should be an error",
            retrievedRecord.isFailure)
        assertEquals("Result error should be IllegalArgumentException",
            IllegalArgumentException::class, retrievedRecord.exceptionOrNull()!!::class)
    }

    /*
    suspend fun createInstance(template: Type.Record): Result<Value.Instance>
    */

    @Test
    fun createInstance_should_create_a_new_empty_instance() = runTest {
        val recordName = "bRecord"
        val baseRecord = unitUnderTest.createRecord(recordName).getOrThrow()
        val foo = unitUnderTest.defineField(baseRecord, "foo", Type.Int, Value.IntValue(10)).getOrThrow()
        val completeRecord = unitUnderTest.getRecord(baseRecord.uuid).getOrThrow()

        val instance = unitUnderTest.createInstance(completeRecord).getOrThrow()

        assertTrue("Instance should have one field value",
            instance.values.isNotEmpty())
        val fooValue = instance[foo]
        assertEquals("Attribute value should be the default value",
            Value.IntValue(10), fooValue)
    }

    /*
    suspend fun <T:Type> setFieldValue(original: Value.Instance, field: Field<T>, value: Value<T>): Result<Value.Instance>
     */

    @Test
    fun setFieldValue_should_return_persisted_value() = runTest {
        val recordName = "cRecord"
        val baseRecord = unitUnderTest.createRecord(recordName).getOrThrow()
        val foo = unitUnderTest.defineField(baseRecord, "foo", Type.Int, null).getOrThrow()
        val completeRecord = unitUnderTest.getRecord(baseRecord.uuid).getOrThrow()
        val instance = unitUnderTest.createInstance(completeRecord).getOrThrow()

        val result = unitUnderTest.setFieldValue(instance, foo, Value.IntValue(10)).getOrThrow()

        assertEquals("Attribute value should be the new value",
            Value.IntValue(10), result[foo])
    }
}
