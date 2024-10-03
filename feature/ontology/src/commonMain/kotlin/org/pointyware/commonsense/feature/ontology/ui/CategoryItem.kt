package org.pointyware.commonsense.feature.ontology.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import org.pointyware.commonsense.feature.ontology.Category
import org.pointyware.commonsense.feature.ontology.viewmodels.CategoryItemUiState

/**
 * Displays a single category.
 */
@Composable
fun CategoryItem(
    value: CategoryItemUiState,
    onCheckedChange: ((Boolean)->Unit)? = null,
    showCheckbox: Boolean = onCheckedChange != null,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
    ) {
        AnimatedVisibility(
            visible = showCheckbox,
        ) {
            Checkbox(
                checked = value.selected,
                onCheckedChange = onCheckedChange,
                modifier = Modifier.semantics {
                    contentDescription = if (value.selected) {
                        "Deselect"
                    } else {
                        "Select"
                    }
                }
            )
        }
        Text(
            text = value.name,
            style = MaterialTheme.typography.labelLarge
        )
    }
}
