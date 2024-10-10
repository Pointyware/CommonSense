package org.pointyware.commonsense.core.entities

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

    @ExperimentalValue
    class RealValue(val rawValue: Double)

    @ExperimentalValue
    class ImaginaryValue(val rawValue: Double)

    class IntValue(val rawValue: Int): Value<Type.Int>

    class StringValue(val rawValue: String): Value<Type.String>

    class Instance(val attributes: Set<Attribute<*>>): Value<Type.Record>
}
