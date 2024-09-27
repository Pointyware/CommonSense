package org.pointyware.commonsense.feature.ontology.category.viewmodels

import org.pointyware.commonsense.feature.ontology.Concept
import org.pointyware.commonsense.feature.ontology.entities.Category
import org.pointyware.commonsense.feature.ontology.viewmodels.ConceptEditorUiState

/**
 * UI-agnostic state of a category explorer.
 *
 * @param loading Whether the category explorer is currently loading data.
 * @param currentCategory The current category being viewed.
 * @param conceptEditor The state of the concept editor, if it is open.
 */
data class CategoryExplorerUiState(
    val loading: Boolean = false,
    val currentCategory: CategoryUiState,
    val conceptEditor: ConceptEditorUiState? = null
) {
    companion object {
        val Loading = CategoryExplorerUiState(true, CategoryUiState())
    }
}

data class CategoryUiState(
    val selected: Category? = null,
    val subcategories: List<Category> = emptyList(),
    val concepts: List<Concept> = emptyList(),
)
