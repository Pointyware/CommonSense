package org.pointyware.commonsense.feature.ontology.viewmodels

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


/**
 * Represents the state of a [Category] being created and/or modified.
 */
@OptIn(ExperimentalUuidApi::class)
data class CategoryEditorUiState(
    /**
     * The id of the category being edited. If null, a new category is being created.
     */
    val id: Uuid?,
    /**
     * The name of the category.
     */
    val name: String,
) {
    companion object {
        val Empty = CategoryEditorUiState(null, "")
    }
}
