package org.pointyware.commonsense.ontology.interactors

import org.pointyware.commonsense.core.local.KmpFile
import org.pointyware.commonsense.ontology.ConceptSpace
import org.pointyware.commonsense.ontology.data.ConceptSpaceRepository

/**
 *
 */
class LoadConceptSpaceUseCase(
    private val conceptSpaceRepository: ConceptSpaceRepository
) {

    suspend operator fun invoke(file: KmpFile): Result<ConceptSpace> {
        return conceptSpaceRepository.loadConceptSpace(file)
    }
}
