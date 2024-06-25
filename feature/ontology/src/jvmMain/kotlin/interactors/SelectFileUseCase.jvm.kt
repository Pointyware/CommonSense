package org.pointyware.commonsense.ontology.interactors

import JvmFile
import org.pointyware.commonsense.core.local.KmpFile

/**
 *
 */
actual class SelectFileUseCase actual constructor() {
    actual suspend operator fun invoke(): Result<KmpFile> {
        /*
        TODO: Implement file selection for JVM. This will likely involve using a file dialog
          1. Instead of expect/actual define cross-platform interface for file selection
          2. Implement in higher layer for access to platform-specific file dialog
          3. Bind implementations for each platform
         */
        return Result.success(JvmFile(DIRECTORY_SPACES)) // TODO: coordinate with file dialogs
    }
}
