package org.pointyware.commonsense.core.entities

import kotlin.experimental.and
import kotlin.experimental.or
import kotlin.random.Random

private const val BYTE_COUNT = 16
private const val VERSION_INDEX = 6
private const val VERSION_MASK_INVERSE = 0x0F.toByte()
private const val VERSION_VALUE_4 = 0x40.toByte()
