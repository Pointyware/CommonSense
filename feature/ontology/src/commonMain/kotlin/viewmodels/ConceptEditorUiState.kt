package org.pointyware.commonsense.feature.ontology.viewmodels

import org.pointyware.commonsense.core.common.Uuid

/**
 *
 */
data class ConceptEditorUiState(
    val id: Uuid?,
    val name: String,
    val description: String,
) {
    companion object {
        val Empty = ConceptEditorUiState(null, "", "")
    }
}
