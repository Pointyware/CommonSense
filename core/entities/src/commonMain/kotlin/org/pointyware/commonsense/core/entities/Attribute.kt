/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.core.entities

/**
 * An [Attribute] is a part of a [Value.Instance] that defines a [Field] of a [Type.Record].
 */
data class Attribute<T:Type>(
    val name: String,
    val value: Value<T>
)
