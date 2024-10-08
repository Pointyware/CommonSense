package org.pointyware.commonsense.core.entities

@RequiresOptIn(
    message = "This API is experimental and may change in the future.",
    level = RequiresOptIn.Level.WARNING
)
annotation class ExperimentalType

/**
 * Describes the range of types that values can take on in the Common Sense system.
 *
 * Theoretical types conform to a type-hierarchy that is based on set membership.
 *
 * [Type.Int] is the only non-experimental type.
 */
sealed interface Type {
    // region Theoretical Types

    /**
     * A number is an object used for counting, measuring, or labeling.
     *
     * [Number definitions](https://www.oed.com/dictionary/number_n)
     */
    @ExperimentalType
    interface Number: Type {

    }

    /**
     * An imaginary number is a value that can be represented as a real number multiplied by the square root of -1.
     */
    @ExperimentalType
    interface Imaginary: Number {

    }

    /**
     * A real number is a value that can be represented on a number line.
     */
    @ExperimentalType
    interface Real: Number {

    }

    @ExperimentalType
    interface Natural: Real {

    }

    @ExperimentalType
    interface Integer: Natural {

    }

    /**
     * A rational number is a value that can be represented as a fraction of two integers.
     */
    @ExperimentalType
    interface Rational: Real {

    }

    /**
     * An irrational number is a value that cannot be represented as a fraction of two integers.
     */
    @ExperimentalType
    interface Irrational: Real {

    }

    /**
     * A transcendental number is a value that cannot be represented as a fraction of two integers.
     */
    @ExperimentalType
    interface Transcendental: Real {

    }

    /**
     * A complex number is a value composed of a real part and an imaginary part.
     */
    @ExperimentalType
    interface Complex: Number {
        val real: Real
        val imaginary: Imaginary
    }

    // endregion

    // region Computing Types

    /**
     * A singular value that represents the absence of any other value. Derived from the Latin
     * word "nullus" meaning "none", associated with the concept of zero<sup>(1)</sup>.
     * When used in combination with other types, it represents the absence of a value of that
     * type. When used in combination with numbers, it - somewhat confusingly - does not represent zero.
     *
     * <sup>(1)</sup> - [null etymology](https://www.etymonline.com/word/null)
     */
    @ExperimentalType
    data object Null: Type

    /**
     * A boolean is a value that can be either true or false.
     */
    @ExperimentalType
    data object Boolean: Type

    /**
     * An integer is a value that can be represented as a whole number.
     */
    data object Int: Type // Add min, max constraints or define as further type?

    /**
     * A floating point number is composed of a sign bit, mantissa, and an exponent.
     */
    @ExperimentalType
    data object Float: Type // Add min, max constraints or define as further type?

    // Collections
    /**
     * A collection is a group of objects that are treated as a single entity.
     */
    @ExperimentalType
    data object Collection: Type

    // Homogenous Collections
    /**
     * A sequence is an ordered collection of objects.
     */
    @ExperimentalType
    data object Sequence: Type

    /**
     * An array is a fixed-size collection of objects.
     */
    @ExperimentalType
    data object Array: Type // Add size min, max constraints or define as further type?

    /**
     * A string is a special case sequence of characters.
     */
    @ExperimentalType
    data object String: Type // Add pattern constraints or define as further type?

    /**
     * A set is collection of unique objects.
     */
    @ExperimentalType
    data object Set: Type

    /**
     * A bag is a special case of a set where each element can appear more than once.
     */
    @ExperimentalType
    data object Bag: Type

    /**
     * A map is a special case of a set where each element is a key-value pair.
     */
    @ExperimentalType
    data object Map: Type

    // Heterogeneous Collections

    /**
     * An object is a collection of properties, similar to key-value pairs, with associated
     * behaviors that can be invoked.
     *
     * In the Common Sense system, an object is a collection of properties that are defined by a
     * type template.
     *
     * - See concepts: Struct, Record, Class, Object vs Instance
     * @see Record
     */
    @ExperimentalType
    data object Object: Type

    /**
     * An interface is a collection of properties that define a contract for objects that implement
     * the interface.
     */
    @ExperimentalType
    data object Interface: Type

    // endregion
}
