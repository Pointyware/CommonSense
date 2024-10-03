package org.pointyware.commonsense.feature.ontology.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.onClick
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.feature.ontology.viewmodels.CategoryExplorerUiState

/**
 * Displays the contents of the current category, including subcategories and concepts.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryExplorer(
    state: CategoryExplorerUiState,
    modifier: Modifier = Modifier,
    onCategorySelected: (Uuid)->Unit,
    onConceptSelected: (Uuid)->Unit,
    onDeleteSelected: (concepts:List<Uuid>, categories:List<Uuid>)->Unit,
) {
    val currentCategory = state.currentCategory
    Column(
        modifier = modifier
    ) {
        var isSelectionActive by remember { mutableStateOf(false) }
        var selectedConcepts by remember { mutableStateOf(listOf<Uuid>()) }
        var selectedCategories by remember { mutableStateOf(listOf<Uuid>()) }

        AnimatedVisibility(
            visible = isSelectionActive,
        ) {
            Row(
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { },
                ) {
                    Text(
                        text = "Delete"
                    )
                }
                Button(
                    onClick = { },
                ) {
                    Text(
                        text = "Cancel"
                    )
                }
            }
        }
        currentCategory.selected?.let { category ->
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
            items(currentCategory.subcategories) { category ->
                CategoryItem(
                    value = category.copy(selected = selectedCategories.contains(category.id)),
                    showCheckbox = isSelectionActive,
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            selectedCategories += category.id
                        } else {
                            selectedCategories -= category.id
                        }
                    },
                    modifier = Modifier
                        .onClick(
                            onClick = { onCategorySelected(category.id) },
                            onLongClick = { isSelectionActive = true }
                        ),
                )
            }
            items(currentCategory.concepts) { concept ->
                ConceptItem(
                    value = concept.copy(selected = selectedConcepts.contains(concept.id)),
                    showCheckbox = isSelectionActive,
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            selectedConcepts += concept.id
                        } else {
                            selectedConcepts -= concept.id
                        }
                    },
                    modifier = Modifier
                        .onClick(
                            onClick = { onConceptSelected(concept.id) },
                            onLongClick = { isSelectionActive = true }
                        ),
                )
            }
        }
    }
}
