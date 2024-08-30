package org.pointyware.commonsense.feature.ontology.category.viewmodels

import org.pointyware.commonsense.feature.ontology.Concept
import org.pointyware.commonsense.feature.ontology.entities.Category

/**
 * UI-agnostic state of a category explorer.
 */
data class CategoryExplorerUiState(
    val loading: Boolean = false,
    val selected: Category? = null,
    val subcategories: List<Category> = emptyList(),
    val concepts: List<Concept> = emptyList(),
) {
    companion object {
        val Loading = CategoryExplorerUiState(true, null)
    }
}
