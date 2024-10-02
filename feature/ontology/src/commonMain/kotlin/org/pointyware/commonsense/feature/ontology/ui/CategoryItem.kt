package org.pointyware.commonsense.feature.ontology.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.pointyware.commonsense.feature.ontology.Category

/**
 * Displays a single category.
 */
@Composable
fun CategoryItem(
    value: Category,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = value.name,
            style = MaterialTheme.typography.labelLarge
        )
    }
}
