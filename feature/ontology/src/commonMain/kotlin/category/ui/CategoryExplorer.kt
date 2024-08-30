package org.pointyware.commonsense.feature.ontology.category.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.pointyware.commonsense.feature.ontology.Concept

data class CategoryExplorerState(
//    val currentCategory: Category,
//    val categories: List<Category>,
    val concepts: List<Concept>,
)

/**
 * Displays the contents of the current category, including subcategories and concepts.
 */
@Composable
fun CategoryExplorer(
    state: CategoryExplorerState,
    modifier: Modifier = Modifier,
//    onCategorySelected: (Category) -> Unit,
    onConceptSelected: (Concept) -> Unit,
) {

}
