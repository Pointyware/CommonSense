package org.pointyware.commonsense.feature.ontology.category.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.pointyware.commonsense.feature.ontology.Concept

/**
 * Displays a single concept as a list item.
 */
@Composable
fun ConceptItem(
    value: Concept,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = value.name,
            style = MaterialTheme.typography.labelMedium
        )
    }
}
