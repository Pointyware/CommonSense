package org.pointyware.commonsense.ontology.di

import org.koin.dsl.module
import org.pointyware.commonsense.ontology.data.ConceptSpaceRepository
import org.pointyware.commonsense.ontology.data.ConceptSpaceRepositoryImpl
import org.pointyware.commonsense.ontology.interactors.LoadConceptSpaceUseCase
import org.pointyware.commonsense.ontology.viewmodels.ConceptSpaceViewModel

/**
 *
 */
fun ontologyModule() = module {
    single<ConceptSpaceRepository> { ConceptSpaceRepositoryImpl(get()) }
    single<LoadConceptSpaceUseCase> { LoadConceptSpaceUseCase(get()) }
    single<ConceptSpaceViewModel> { ConceptSpaceViewModel(get()) }
}
