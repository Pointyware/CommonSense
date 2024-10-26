/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.core.common

/**
 *
 */
fun <K, V> Map<K, V>.joinToString() {
    entries.joinToString(prefix = "{", separator = ", ", postfix = "}") { (k, v) -> "$k=$v" }
}
