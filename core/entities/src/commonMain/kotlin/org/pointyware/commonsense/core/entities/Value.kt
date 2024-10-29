package org.pointyware.commonsense.core.entities

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@RequiresOptIn(
    message = "This API is experimental and may change in the future.",
    level = RequiresOptIn.Level.WARNING
)
annotation class ExperimentalValue

/**
 * Realizes the range of values that types express in the Common Sense system.
 * @see Type
 */
sealed interface Value<out T: Type> {

    @OptIn(ExperimentalType::class)
    @ExperimentalValue
    class ComplexValue(val real: RealValue, val imaginary: ImaginaryValue): Value<Type.Complex>

    @OptIn(ExperimentalType::class)
    @ExperimentalValue
    class RealValue(val rawValue: Double): Value<Type.Float>

    @ExperimentalValue
    class ImaginaryValue(val rawValue: Double)

    data class IntValue(val rawValue: Int): Value<Type.Int>

    class StringValue(val rawValue: String): Value<Type.String>

    class BoolValue(val rawValue: Boolean): Value<Type.Boolean>

    @OptIn(ExperimentalUuidApi::class)
    class Instance(
        val id: Uuid,
        val type: Type.Record,
        fieldValues: Map<Field<*>, Value<*>>
    ): Value<Type.Record> {

        private val _values: MutableMap<Field<*>, Value<*>> = fieldValues.toMutableMap()
        val values: Map<Field<*>, Value<*>> get() = _values.toMap()

        operator fun <T:Type> get(field: Field<T>): Value<T> {
            @Suppress("UNCHECKED_CAST")
            return _values[field] as? Value<T> ?: throw IllegalArgumentException("Field not found")
        }

        fun <T:Type> set(field: Field<T>, value: Value<T>) {
            _values[field] = value
        }
    }
}
