package org.pointyware.commonsense.feature.ontology.interactors

import kotlinx.io.files.Path

/**
 *
 */
class NativeSelectFileUseCase(): SelectFileUseCase {
    override suspend operator fun invoke(): Result<Path> {
        TODO("Not yet implemented")
    }
}

actual fun SelectFileUseCaseImpl(): SelectFileUseCase {
    return NativeSelectFileUseCase()
}
