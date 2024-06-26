package org.pointyware.commonsense.feature.ontology.interactors

import org.pointyware.commonsense.core.local.KmpFile
import org.pointyware.commonsense.ontology.interactors.SelectFileUseCase

/**
 *
 */
class NativeSelectFileUseCase(): SelectFileUseCase {
    override suspend operator fun invoke(): Result<KmpFile> {
        TODO("Not yet implemented")
    }
}
