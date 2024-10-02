package org.pointyware.commonsense.feature.ontology

import org.pointyware.commonsense.core.common.Uuid

/**
 * Represents a category in the ontology.
 */
data class Category(
    val id: Uuid,
    val name: String
)
