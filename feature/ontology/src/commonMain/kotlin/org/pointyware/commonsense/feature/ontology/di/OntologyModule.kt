package org.pointyware.commonsense.feature.ontology.di

import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.pointyware.commonsense.feature.ontology.data.CategoryDataSource
import org.pointyware.commonsense.feature.ontology.data.CategoryRepository
import org.pointyware.commonsense.feature.ontology.data.CategoryRepositoryImpl
import org.pointyware.commonsense.feature.ontology.data.CategorySqlDataSource
import org.pointyware.commonsense.feature.ontology.interactors.CreateNewCategoryUseCase
import org.pointyware.commonsense.feature.ontology.interactors.CreateNewConceptUseCase
import org.pointyware.commonsense.feature.ontology.interactors.GetSelectedCategoryUseCase
import org.pointyware.commonsense.feature.ontology.interactors.GetSelectedConceptUseCase
import org.pointyware.commonsense.feature.ontology.viewmodels.CategoryEditorViewModel
import org.pointyware.commonsense.feature.ontology.viewmodels.CategoryEditorViewModelImpl
import org.pointyware.commonsense.feature.ontology.viewmodels.CategoryExplorerViewModel
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
        ontologyUiModule(),

        ontologyLocalModule()
    )
}

fun ontologyDataModule() = module {
    single<Json> { Json.Default }
    single<ConceptSpaceRepository> { ConceptSpaceRepositoryImpl(get<ConceptSpaceDataSource>()) }
    single<ArrangementController> { SimpleArrangementController() }

    singleOf(::CategoryRepositoryImpl) { bind<CategoryRepository>() }
    single<ConceptEditorController> { ConceptEditorControllerImpl() }
}

expect fun ontologyLocalPlatformModule(): Module

fun ontologyLocalModule() = module {
    single<CategoryDataSource> { CategorySqlDataSource(get()) }

    includes(
        ontologyLocalPlatformModule()
    )
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

    singleOf(::CategoryExplorerViewModel)
    singleOf(::ConceptEditorViewModelImpl) { bind<ConceptEditorViewModel>() }
    singleOf(::CategoryEditorViewModelImpl) { bind<CategoryEditorViewModel>() }
}

fun ontologyUiModule() = module {
    single<ConceptSpaceUiStateMapper> { ConceptSpaceUiStateMapper }
}
