package org.pointyware.commonsense.feature.ontology.interactors

import org.pointyware.commonsense.core.local.LocalStorage

const val DIRECTORY_SPACES = "spaces"

/**
 * Opens a file dialog to select a file and returns the result.
 */
interface SelectFileUseCase {
    suspend operator fun invoke(): Result<LocalStorage>
}
