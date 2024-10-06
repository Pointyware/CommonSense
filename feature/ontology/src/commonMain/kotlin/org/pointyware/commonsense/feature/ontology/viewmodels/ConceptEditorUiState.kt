package org.pointyware.commonsense.feature.ontology.viewmodels

import org.pointyware.commonsense.core.common.Uuid

/**
 * Represents the state of a Concept Editor UI.
 */
@Deprecated("Concept is formalized by Record",
    ReplaceWith(expression = "RecordEditorUiState", imports = []))
data class ConceptEditorUiState(
    val id: Uuid?,
    val name: String,
    val description: String,
) {
    companion object {
        val Empty = ConceptEditorUiState(null, "", "")
    }
}
