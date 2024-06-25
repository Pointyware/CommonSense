package org.pointyware.commonsense.ontology.interactors

import JvmFile
import org.pointyware.commonsense.core.local.KmpFile

/**
 *
 */
actual class SelectFileUseCase actual constructor() {
    actual suspend operator fun invoke(): Result<KmpFile> {
        return Result.success(JvmFile("")) // TODO: coordinate with file dialogs
    }
}
