package org.pointyware.commonsense.feature.ontology.viewmodels

import org.pointyware.commonsense.core.common.Uuid

/**
 *
 */
data class CategoryEditorUiState(
    val id: Uuid?,
    val name: String,
) {
    companion object {
        val Empty = CategoryEditorUiState(null, "")
    }
}
