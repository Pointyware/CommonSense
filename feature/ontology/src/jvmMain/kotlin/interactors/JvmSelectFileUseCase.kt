package org.pointyware.commonsense.feature.ontology.interactors

import org.pointyware.commonsense.core.local.JvmFile
import org.pointyware.commonsense.core.local.LocalStorage

/**
 *
 */
class JvmSelectFileUseCase(
    /*
    TODO: coordinate with file dialogs
     */
): SelectFileUseCase {
    override suspend operator fun invoke(): Result<LocalStorage> {
        return Result.success(JvmFile(DIRECTORY_SPACES)) // TODO: coordinate with file dialogs
    }
}

actual fun SelectFileUseCaseImpl(): SelectFileUseCase {
    return JvmSelectFileUseCase()
}
