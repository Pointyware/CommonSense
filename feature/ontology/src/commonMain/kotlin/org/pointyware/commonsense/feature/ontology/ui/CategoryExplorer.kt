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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.core.ui.rememberSelectionController
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
    onDeleteSelected: (concepts:Set<Uuid>, categories:Set<Uuid>)->Unit,
) {
    val currentCategory = state.currentCategory
    Column(
        modifier = modifier
    ) {
        val conceptSelectionController = rememberSelectionController<Uuid>()
        val categorySelectionController = rememberSelectionController<Uuid>()
        val selectionActive = conceptSelectionController.isSelectionActive.value || categorySelectionController.isSelectionActive.value

        AnimatedVisibility(
            visible = selectionActive,
        ) {
            var confirmDialog by remember { mutableStateOf(false) }
            Row(
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        confirmDialog = true
                    },
                ) {
                    Text(
                        text = "Delete"
                    )
                }
                Button(
                    onClick = {
                        conceptSelectionController.deactivate()
                        categorySelectionController.deactivate()
                    },
                ) {
                    Text(
                        text = "Cancel"
                    )
                }
            }
            if (confirmDialog) {
                Dialog(
                    onDismissRequest = {
                        conceptSelectionController.deactivate()
                        categorySelectionController.deactivate()
                    }
                ) {
                    Column(
                        modifier = Modifier.semantics {
                            contentDescription = "Delete Concepts"
                        }
                    ) {
                        Text(
                            "You are about to delete ${conceptSelectionController.selectedItems.value.size} concepts and ${categorySelectionController.selectedItems.value.size} categories."
                        )
                        Row {
                            Button(
                                onClick = {
                                    confirmDialog = false
                                    onDeleteSelected(
                                        conceptSelectionController.selectedItems.value,
                                        categorySelectionController.selectedItems.value
                                    )
                                },
                            ) {
                                Text(
                                    text = "Confirm"
                                )
                            }
                            Button(
                                onClick = {
                                    confirmDialog = false
                                    conceptSelectionController.deactivate()
                                    categorySelectionController.deactivate()
                                },
                            ) {
                                Text(
                                    text = "Cancel"
                                )
                            }
                        }
                    }
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
                .semantics {
                    contentDescription = "Category Contents"
                }
        ) {
            items(currentCategory.subcategories) { category ->
                CategoryItem(
                    value = category.copy(selected = categorySelectionController.isSelected(category.id)),
                    showCheckbox = selectionActive,
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            categorySelectionController.select(category.id)
                        } else {
                            categorySelectionController.deselect(category.id)
                        }
                    },
                    modifier = Modifier
                        .onClick(
                            onClick = { onCategorySelected(category.id) },
                            onLongClick = {
                                categorySelectionController.select(category.id)
                                categorySelectionController.activate()
                            }
                        ),
                )
            }
            items(currentCategory.concepts) { concept ->
                ConceptItem(
                    value = concept.copy(selected = conceptSelectionController.isSelected(concept.id)),
                    showCheckbox = selectionActive,
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            conceptSelectionController.select(concept.id)
                        } else {
                            conceptSelectionController.deselect(concept.id)
                        }
                    },
                    modifier = Modifier
                        .onClick(
                            onClick = { onConceptSelected(concept.id) },
                            onLongClick = {
                                conceptSelectionController.select(concept.id)
                                conceptSelectionController.activate()
                            }
                        ),
                )
            }
        }
    }
}
