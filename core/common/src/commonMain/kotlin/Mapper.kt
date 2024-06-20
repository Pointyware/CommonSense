package org.pointyware.commonsense.core.common

/**
 *
 */
interface Mapper<I, O> {
    fun map(input: I): O
}
