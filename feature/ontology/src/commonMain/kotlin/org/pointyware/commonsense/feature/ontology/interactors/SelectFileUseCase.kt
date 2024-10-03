package org.pointyware.commonsense.feature.ontology.interactors

import kotlinx.io.files.Path

const val DIRECTORY_SPACES = "spaces"

/**
 * Opens a file dialog to select a file and returns the result.
 */
interface SelectFileUseCase {
    suspend operator fun invoke(): Result<Path>
}

expect fun SelectFileUseCaseImpl(): SelectFileUseCase
