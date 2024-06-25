package org.pointyware.commonsense.ontology.interactors

import org.pointyware.commonsense.core.local.KmpFile
import org.pointyware.commonsense.ontology.data.ConceptSpaceRepository

/**
 *
 */
class SaveConceptSpaceUseCase(
    private val selectFileUseCase: SelectFileUseCase,
    private val conceptSpaceRepository: ConceptSpaceRepository
) {

    suspend operator fun invoke(selectFile: Boolean = false): Result<KmpFile> {
        val openFile = conceptSpaceRepository.openFile
        if (selectFile || openFile == null) {
            return selectFileUseCase()
                .onSuccess {
                    conceptSpaceRepository.saveConceptSpace(it)
                }
        } else {
            conceptSpaceRepository.saveConceptSpace(openFile)
                .onSuccess { return Result.success(openFile) }
                .onFailure { return Result.failure(it) }
            throw IllegalStateException("saveConceptSpace should not fail")
        }
    }
}
