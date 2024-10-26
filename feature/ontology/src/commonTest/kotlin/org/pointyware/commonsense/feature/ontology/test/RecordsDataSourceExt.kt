/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.commonsense.feature.ontology.test

import org.pointyware.commonsense.core.entities.Type
import org.pointyware.commonsense.core.entities.Value
import org.pointyware.commonsense.feature.ontology.data.RecordsDataSource


/**
 *
 */
suspend fun RecordsDataSource.setup() {
    val zooRecord: Type.Record = this.createRecord("Zoo").getOrThrow()
    val kar = this.defineField(zooRecord, "kar", Type.Int, Value.IntValue(36)).getOrThrow()
    val fooRecord: Type.Record = this.createRecord("Foo").getOrThrow()
    val bar = this.defineField(fooRecord, "bar", Type.Boolean, Value.BoolValue(false)).getOrThrow()

    val kaz = this.defineField(zooRecord, "kaz", fooRecord, null).getOrThrow()
    val baz = this.defineField(fooRecord, "baz", zooRecord, null).getOrThrow()

    val zooInstance: Value.Instance = this.createInstance(zooRecord).getOrThrow()
    val fooInstance: Value.Instance = this.createInstance(fooRecord).getOrThrow()
    this.setFieldValue(zooInstance, kar, Value.IntValue(42))
    this.setFieldValue(fooInstance, bar, Value.BoolValue(true))
    this.setFieldValue(zooInstance, kaz, fooInstance)
    this.setFieldValue(fooInstance, baz, zooInstance)
}
