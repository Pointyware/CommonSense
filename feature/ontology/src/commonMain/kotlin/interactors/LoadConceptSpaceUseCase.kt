package org.pointyware.commonsense.feature.ontology.interactors

import org.pointyware.commonsense.core.local.LocalStorage
import org.pointyware.commonsense.feature.ontology.ConceptSpace
import org.pointyware.commonsense.feature.ontology.data.ConceptSpaceRepository

/**
 *
 */
class LoadConceptSpaceUseCase(
    private val conceptSpaceRepository: ConceptSpaceRepository
) {

    suspend operator fun invoke(file: LocalStorage): Result<ConceptSpace> {
        return conceptSpaceRepository.loadConceptSpace(file)
    }
}
