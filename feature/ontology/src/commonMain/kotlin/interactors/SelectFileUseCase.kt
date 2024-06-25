package org.pointyware.commonsense.ontology.interactors

import org.pointyware.commonsense.core.local.KmpFile

const val DIRECTORY_SPACES = "spaces"

/**
 * Opens a file dialog to select a file and returns the result.
 */
interface SelectFileUseCase {
    suspend operator fun invoke(): Result<KmpFile>
}
