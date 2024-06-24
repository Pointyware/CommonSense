package org.pointyware.commonsense.ontology.di

import org.koin.dsl.module
import org.pointyware.commonsense.ontology.data.ArrangementController
import org.pointyware.commonsense.ontology.data.ConceptSpaceRepository
import org.pointyware.commonsense.ontology.data.ConceptSpaceRepositoryImpl
import org.pointyware.commonsense.ontology.data.SimpleArrangementController
import org.pointyware.commonsense.ontology.interactors.AddNewNodeUseCase
import org.pointyware.commonsense.ontology.interactors.GetActiveConceptSpaceUseCase
import org.pointyware.commonsense.ontology.interactors.LoadConceptSpaceUseCase
import org.pointyware.commonsense.ontology.interactors.RemoveNodeUseCase
import org.pointyware.commonsense.ontology.local.ConceptSpaceDataSource
import org.pointyware.commonsense.ontology.ui.ConceptSpaceUiStateMapper
import org.pointyware.commonsense.ontology.viewmodels.ConceptSpaceViewModel

/**
 *
 */
fun ontologyModule() = module {
    includes(
        ontologyDataModule(),
        ontologyInteractorModule(),
        ontologyViewModelModule(),
        ontologyUiModule()
    )
}

fun ontologyDataModule() = module {
    single<ConceptSpaceRepository> { ConceptSpaceRepositoryImpl(get<ConceptSpaceDataSource>()) }
    single<ArrangementController> { SimpleArrangementController() }
}

fun ontologyInteractorModule() = module {
    single<GetActiveConceptSpaceUseCase> { GetActiveConceptSpaceUseCase(get<ConceptSpaceRepository>()) }
    single<LoadConceptSpaceUseCase> { LoadConceptSpaceUseCase(get<ConceptSpaceRepository>()) }
    single<AddNewNodeUseCase> { AddNewNodeUseCase(get<ConceptSpaceRepository>(), get<ArrangementController>()) }
    single<RemoveNodeUseCase> { RemoveNodeUseCase(get<ConceptSpaceRepository>(), get<ArrangementController>()) }
}

fun ontologyViewModelModule() = module {
    single<LoadConceptSpaceUseCase> { LoadConceptSpaceUseCase(
        get<ConceptSpaceRepository>()
    ) }
    single<ConceptSpaceViewModel> {
        ConceptSpaceViewModel(
            get<GetActiveConceptSpaceUseCase>(),
            get<LoadConceptSpaceUseCase>(),
            get<AddNewNodeUseCase>(),
            get<RemoveNodeUseCase>(),
            get<ArrangementController>()
        )
    }
}

fun ontologyUiModule() = module {
    single<ConceptSpaceUiStateMapper> { ConceptSpaceUiStateMapper }
}
