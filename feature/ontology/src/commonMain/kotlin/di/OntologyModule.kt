package org.pointyware.commonsense.feature.ontology.di

import kotlinx.serialization.json.Json
import org.koin.dsl.module
import org.pointyware.commonsense.core.navigation.CymaticsNavController
import org.pointyware.commonsense.feature.ontology.category.data.CategoryRepository
import org.pointyware.commonsense.feature.ontology.category.data.CategoryRepositoryImpl
import org.pointyware.commonsense.feature.ontology.category.interactors.CreateNewCategoryUseCase
import org.pointyware.commonsense.feature.ontology.category.interactors.CreateNewConceptUseCase
import org.pointyware.commonsense.feature.ontology.category.interactors.GetSelectedCategoryUseCase
import org.pointyware.commonsense.feature.ontology.category.interactors.GetSelectedConceptUseCase
import org.pointyware.commonsense.feature.ontology.category.viewmodels.CategoryExplorerViewModel
import org.pointyware.commonsense.feature.ontology.data.ArrangementController
import org.pointyware.commonsense.feature.ontology.data.ConceptEditorController
import org.pointyware.commonsense.feature.ontology.data.ConceptEditorControllerImpl
import org.pointyware.commonsense.feature.ontology.data.ConceptSpaceRepository
import org.pointyware.commonsense.feature.ontology.data.ConceptSpaceRepositoryImpl
import org.pointyware.commonsense.feature.ontology.data.SimpleArrangementController
import org.pointyware.commonsense.feature.ontology.interactors.AddNewNodeUseCase
import org.pointyware.commonsense.feature.ontology.interactors.GetActiveConceptSpaceUseCase
import org.pointyware.commonsense.feature.ontology.interactors.LoadConceptSpaceUseCase
import org.pointyware.commonsense.feature.ontology.interactors.RemoveNodeUseCase
import org.pointyware.commonsense.feature.ontology.interactors.SaveConceptSpaceUseCase
import org.pointyware.commonsense.feature.ontology.interactors.SelectFileUseCase
import org.pointyware.commonsense.feature.ontology.interactors.SelectFileUseCaseImpl
import org.pointyware.commonsense.feature.ontology.interactors.UpdateNodeUseCase
import org.pointyware.commonsense.feature.ontology.local.ConceptSpaceDataSource
import org.pointyware.commonsense.feature.ontology.ui.ConceptSpaceUiStateMapper
import org.pointyware.commonsense.feature.ontology.viewmodels.CategoryCreatorViewModel
import org.pointyware.commonsense.feature.ontology.viewmodels.ConceptEditorViewModel
import org.pointyware.commonsense.feature.ontology.viewmodels.ConceptEditorViewModelImpl
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

    single<CategoryRepository> { CategoryRepositoryImpl() }
    single<ConceptEditorController> { ConceptEditorControllerImpl() }
}

fun ontologyInteractorModule() = module {
    single<GetActiveConceptSpaceUseCase> { GetActiveConceptSpaceUseCase(get<ConceptSpaceRepository>()) }
    single<LoadConceptSpaceUseCase> { LoadConceptSpaceUseCase(get<ConceptSpaceRepository>()) }
    single<AddNewNodeUseCase> { AddNewNodeUseCase(get<ConceptSpaceRepository>(), get<ArrangementController>()) }
    single<RemoveNodeUseCase> { RemoveNodeUseCase(get<ConceptSpaceRepository>(), get<ArrangementController>()) }
    factory<SaveConceptSpaceUseCase> { SaveConceptSpaceUseCase(get<SelectFileUseCase>(), get<ConceptSpaceRepository>()) }
    factory<SelectFileUseCase> { SelectFileUseCaseImpl() }
    factory<UpdateNodeUseCase> { UpdateNodeUseCase(get<ConceptSpaceRepository>()) }
    factory<GetSelectedCategoryUseCase> { GetSelectedCategoryUseCase(
        get<ConceptEditorController>(),
        get<CategoryRepository>()
    ) }
    factory<GetSelectedConceptUseCase> { GetSelectedConceptUseCase(
        get<CategoryRepository>()
    ) }
    factory<CreateNewConceptUseCase> { CreateNewConceptUseCase(
        get<ConceptEditorController>(),
        get<CategoryRepository>()
    ) }
    factory<CreateNewCategoryUseCase> { CreateNewCategoryUseCase(
        get<ConceptEditorController>(),
        get<CategoryRepository>()
    ) }
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

    single<CategoryExplorerViewModel> { CategoryExplorerViewModel(
        get<GetSelectedCategoryUseCase>(),
        get<GetSelectedConceptUseCase>(),
        get<ConceptEditorViewModel>(),
    ) }
    single<ConceptEditorViewModel> { ConceptEditorViewModelImpl(
        get<CreateNewConceptUseCase>(),
    ) }
    single<CategoryCreatorViewModel> { CategoryCreatorViewModel(
        get<CreateNewCategoryUseCase>(),
        get<CymaticsNavController>()
    ) }
}

fun ontologyUiModule() = module {
    single<ConceptSpaceUiStateMapper> { ConceptSpaceUiStateMapper }
}
