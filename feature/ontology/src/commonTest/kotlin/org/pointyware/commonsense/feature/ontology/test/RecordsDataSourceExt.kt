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
    this.addField(zooRecord, "kar", Type.Int, Value.IntValue(36))
    val fooRecord: Type.Record = this.createRecord("Foo").getOrThrow()
    this.addField(fooRecord, "bar", Type.Boolean, Value.BoolValue(false))

    this.addField(zooRecord, "kaz", fooRecord, null)
    this.addField(fooRecord, "baz", zooRecord, null)

    val zooInstance: Value.Instance = this.createInstance(zooRecord).getOrThrow()
    val fooInstance: Value.Instance = this.createInstance(fooRecord).getOrThrow()
    this.setAttribute(zooInstance, "kar", Value.IntValue(42))
    this.setAttribute(fooInstance, "bar", Value.BoolValue(true))
    this.setAttribute(zooInstance, "kaz", fooInstance)
    this.setAttribute(fooInstance, "baz", zooInstance)
}
