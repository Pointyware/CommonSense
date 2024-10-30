@file:OptIn(ExperimentalUuidApi::class)

package org.pointyware.commonsense.feature.ontology.viewmodels

import org.pointyware.commonsense.core.common.joinToString
import org.pointyware.commonsense.core.entities.Value
import org.pointyware.commonsense.feature.ontology.Category
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Represents the contents of a category.
 */
data class CategoryUiState(
    val selected: CategoryItemUiState? = null,
    val subcategories: List<CategoryItemUiState> = emptyList(),
    val types: List<RecordItemUiState> = emptyList(),
    @Deprecated("Concepts no longer used")
    val concepts: List<ConceptItemUiState> = emptyList(),
    val instances: List<InstanceItemUiState> = emptyList()
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
 * Represents a type/record as an item in a list.
 */
data class RecordItemUiState(
    val id: Uuid,
    val name: String,
)

/**
 * Represents a single instance as an item in a list.
 */
data class InstanceItemUiState(
    val id: Uuid,
    val description: String
)

/**
 * Represents a concept as an item in a list.
 */
data class ConceptItemUiState(
    val id: Uuid,
    val name: String,
    val selected: Boolean = false,
)

fun Value.Instance.toUiState(): InstanceItemUiState {
    val itemName = type.name + values.joinToString()

    return InstanceItemUiState(
        id,
        itemName
    )
}
