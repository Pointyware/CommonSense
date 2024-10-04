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
sealed interface Value<T: Type> {

    @OptIn(ExperimentalType::class)
    @ExperimentalValue
    class ComplexValue(val real: Type.Real, val imaginary: Type.Imaginary): Value<Type.Complex>

    class IntValue(val rawValue: Int): Value<Type.Int>
}
