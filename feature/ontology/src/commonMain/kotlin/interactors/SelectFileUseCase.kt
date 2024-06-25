package org.pointyware.commonsense.ontology.interactors

import org.pointyware.commonsense.core.local.KmpFile

const val DIRECTORY_SPACES = "spaces"
/**
 *
 */
expect class SelectFileUseCase() {
    suspend operator fun invoke(): Result<KmpFile>
}
