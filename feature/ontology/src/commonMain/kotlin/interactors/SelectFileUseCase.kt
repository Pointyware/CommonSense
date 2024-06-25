package org.pointyware.commonsense.ontology.interactors

import org.pointyware.commonsense.core.local.KmpFile

/**
 *
 */
expect class SelectFileUseCase() {
    suspend operator fun invoke(): Result<KmpFile>
}
