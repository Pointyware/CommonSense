@file:OptIn(ExperimentalUuidApi::class)

package org.pointyware.commonsense.feature.ontology.viewmodels

import org.pointyware.commonsense.core.entities.Value
import org.pointyware.commonsense.feature.ontology.Category
import org.pointyware.commonsense.feature.ontology.Concept
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Represents the contents of a category.
 */
data class CategoryUiState(
    val selected: CategoryItemUiState? = null,
    val subcategories: List<CategoryItemUiState> = emptyList(),
    val concepts: List<ConceptItemUiState> = emptyList(),
)

/**
 * Represents a category as an item in a list.
 */
data class CategoryItemUiState(
    val id: Uuid,
    val name: String,
    val selected: Boolean = false,
)

fun Category.toUiState() = CategoryItemUiState(id, name)

/**
 * Represents a concept as an item in a list.
 */
data class ConceptItemUiState(
    val id: Uuid,
    val name: String,
    val selected: Boolean = false,
)

@Deprecated("Prefer Record/Instances")
fun Concept.toUiState() = ConceptItemUiState(id, name)

fun Value.Instance.toUiState(): ConceptItemUiState {
    val itemName = type.name + " " + attributes.joinToString { it.name }
    return ConceptItemUiState(
        id,
        itemName
    )
}
