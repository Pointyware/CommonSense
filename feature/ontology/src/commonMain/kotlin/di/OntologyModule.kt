package org.pointyware.commonsense.feature.ontology.di

import kotlinx.serialization.json.Json
import org.koin.dsl.module
import org.pointyware.commonsense.feature.ontology.data.ArrangementController
import org.pointyware.commonsense.feature.ontology.data.ConceptSpaceRepository
import org.pointyware.commonsense.feature.ontology.data.ConceptSpaceRepositoryImpl
import org.pointyware.commonsense.feature.ontology.data.SimpleArrangementController
import org.pointyware.commonsense.feature.ontology.interactors.AddNewNodeUseCase
import org.pointyware.commonsense.feature.ontology.interactors.GetActiveConceptSpaceUseCase
import org.pointyware.commonsense.feature.ontology.interactors.LoadConceptSpaceUseCase
import org.pointyware.commonsense.feature.ontology.interactors.RemoveNodeUseCase
import org.pointyware.commonsense.feature.ontology.interactors.SaveConceptSpaceUseCase
import org.pointyware.commonsense.feature.ontology.interactors.UpdateNodeUseCase
import org.pointyware.commonsense.feature.ontology.local.ConceptSpaceDataSource
import org.pointyware.commonsense.feature.ontology.ui.ConceptSpaceUiStateMapper
import org.pointyware.commonsense.feature.ontology.viewmodels.ConceptSpaceViewModel

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
    single<Json> { Json.Default }
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
            get<SaveConceptSpaceUseCase>(),
            get<AddNewNodeUseCase>(),
            get<UpdateNodeUseCase>(),
            get<RemoveNodeUseCase>(),
            get<ArrangementController>()
        )
    }
}

fun ontologyUiModule() = module {
    single<ConceptSpaceUiStateMapper> { ConceptSpaceUiStateMapper }
}
