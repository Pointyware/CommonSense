package org.pointyware.commonsense.feature.ontology.viewmodels

import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.feature.ontology.Category
import org.pointyware.commonsense.feature.ontology.Concept

/**
 * Represents the contents of a category.
 */
data class CategoryUiState(
    val selected: Category? = null,
    val subcategories: List<Category> = emptyList(),
    val concepts: List<Concept> = emptyList(),
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

fun Concept.toUiState() = ConceptItemUiState(id, name)
