package org.pointyware.commonsense.core.entities

import org.pointyware.commonsense.assertions.assert
import kotlin.experimental.and
import kotlin.test.Test

/**
 */
class UuidUnitTest {

    @Test
    fun `version 4 - random uuid`() {
        val uuid = Uuid.v4()

        assert().that(uuid[6] and 0xF0.toByte()).isEqualTo(0x40.toByte())
    }
}
