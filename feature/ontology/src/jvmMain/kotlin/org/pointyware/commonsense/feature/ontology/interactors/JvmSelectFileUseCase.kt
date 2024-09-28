package org.pointyware.commonsense.feature.ontology.interactors

import kotlinx.io.files.Path

/**
 *
 */
class JvmSelectFileUseCase(
    /*
    TODO: coordinate with file dialogs
     */
): SelectFileUseCase {
    override suspend operator fun invoke(): Result<Path> {
        return Result.success(Path(DIRECTORY_SPACES)) // TODO: coordinate with file dialogs
    }
}

actual fun SelectFileUseCaseImpl(): SelectFileUseCase {
    return JvmSelectFileUseCase()
}
