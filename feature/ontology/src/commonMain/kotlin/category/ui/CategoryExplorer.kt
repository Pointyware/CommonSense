package org.pointyware.commonsense.feature.ontology.category.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.feature.ontology.Concept
import org.pointyware.commonsense.feature.ontology.entities.Category

/**
 * Represents the state of the CategoryExplorer composable.
 */
data class CategoryExplorerState(
    val currentCategory: Category?,
    val categories: List<Category>,
    val concepts: List<Concept>,
)

/**
 * Displays the contents of the current category, including subcategories and concepts.
 */
@Composable
fun CategoryExplorer(
    state: CategoryExplorerState,
    modifier: Modifier = Modifier,
    onCategorySelected: (Uuid)->Unit,
    onConceptSelected: (Uuid)->Unit,
    onAddCard: ()->Unit,
) {
    Column(
        modifier = modifier
    ) {
        state.currentCategory?.let { category ->
            CategoryItem(
                value = category,
                modifier = Modifier.clickable { onCategorySelected(category.id) },
            )
            Spacer(
                modifier = Modifier
                    .height(2.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondary)
            )
        }
        LazyColumn(
            modifier = modifier
        ) {
            items(state.categories) { category ->
                CategoryItem(
                    value = category,
                    modifier = Modifier.clickable { onCategorySelected(category.id) },
                )
            }
            items(state.concepts) { concept ->
                ConceptItem(
                    value = concept,
                    modifier = Modifier.clickable { onConceptSelected(concept.id) }
                )
            }
        }
        Button(onClick = onAddCard) {
            Text(text = "New Card")
        }
    }
}
