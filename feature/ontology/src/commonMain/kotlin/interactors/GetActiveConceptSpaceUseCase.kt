package org.pointyware.commonsense.ontology.interactors

import kotlinx.coroutines.flow.Flow
import org.pointyware.commonsense.ontology.ConceptSpace
import org.pointyware.commonsense.ontology.data.ConceptSpaceRepository

/**
 * Passes along the active concept space.
 */
class GetActiveConceptSpaceUseCase(
    private val repository: ConceptSpaceRepository
) {

    operator fun invoke(): Flow<ConceptSpace> =
        repository.activeSpace
}
