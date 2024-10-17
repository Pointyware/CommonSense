package org.pointyware.commonsense.feature.ontology.viewmodels

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


/**
 *
 */
@OptIn(ExperimentalUuidApi::class)
data class CategoryEditorUiState(
    val id: Uuid?,
    val name: String,
) {
    companion object {
        val Empty = CategoryEditorUiState(null, "")
    }
}
