package org.pointyware.commonsense.feature.ontology.interactors

import JvmFile
import org.pointyware.commonsense.core.local.KmpFile

/**
 *
 */
class JvmSelectFileUseCase(
    /*
    TODO: coordinate with file dialogs
     */
): SelectFileUseCase {
    override suspend operator fun invoke(): Result<KmpFile> {
        return Result.success(JvmFile(DIRECTORY_SPACES)) // TODO: coordinate with file dialogs
    }
}
