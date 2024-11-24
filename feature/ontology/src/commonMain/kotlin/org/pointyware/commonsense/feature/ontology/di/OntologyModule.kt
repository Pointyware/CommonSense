package org.pointyware.commonsense.feature.ontology.di

import app.cash.sqldelight.db.SqlDriver
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.pointyware.commonsense.core.local.db.createOrMigrate
import org.pointyware.commonsense.feature.ontology.data.ArrangementController
import org.pointyware.commonsense.feature.ontology.data.CategoryDataSource
import org.pointyware.commonsense.feature.ontology.data.CategorySqlDataSource
import org.pointyware.commonsense.feature.ontology.data.ConceptEditorController
import org.pointyware.commonsense.feature.ontology.data.ConceptEditorControllerImpl
import org.pointyware.commonsense.feature.ontology.data.ConceptSpaceRepository
import org.pointyware.commonsense.feature.ontology.data.ConceptSpaceRepositoryImpl
import org.pointyware.commonsense.feature.ontology.data.RecordsDataSource
import org.pointyware.commonsense.feature.ontology.data.RecordsRepository
import org.pointyware.commonsense.feature.ontology.data.RecordsRepositoryImpl
import org.pointyware.commonsense.feature.ontology.data.RecordsSqlDataSource
import org.pointyware.commonsense.feature.ontology.data.SimpleArrangementController
import org.pointyware.commonsense.feature.ontology.db.OntologyDb
import org.pointyware.commonsense.feature.ontology.interactors.AddNewNodeUseCase
import org.pointyware.commonsense.feature.ontology.interactors.CreateNewCategoryUseCase
import org.pointyware.commonsense.feature.ontology.interactors.CreateNewInstanceUseCase
import org.pointyware.commonsense.feature.ontology.interactors.GetActiveConceptSpaceUseCase
import org.pointyware.commonsense.feature.ontology.interactors.GetSelectedCategoryUseCase
import org.pointyware.commonsense.feature.ontology.interactors.GetSelectedInstanceUseCase
import org.pointyware.commonsense.feature.ontology.interactors.LoadConceptSpaceUseCase
import org.pointyware.commonsense.feature.ontology.interactors.RemoveNodeUseCase
import org.pointyware.commonsense.feature.ontology.interactors.SaveConceptSpaceUseCase
import org.pointyware.commonsense.feature.ontology.interactors.SelectFileUseCase
import org.pointyware.commonsense.feature.ontology.interactors.SelectFileUseCaseImpl
import org.pointyware.commonsense.feature.ontology.interactors.UpdateNodeUseCase
import org.pointyware.commonsense.feature.ontology.local.ConceptSpaceDataSource
import org.pointyware.commonsense.feature.ontology.local.DriverFactory
import org.pointyware.commonsense.feature.ontology.local.Persistence
import org.pointyware.commonsense.feature.ontology.space.ui.ConceptSpaceUiStateMapper
import org.pointyware.commonsense.feature.ontology.space.viewmodels.ConceptSpaceViewModel
import org.pointyware.commonsense.feature.ontology.viewmodels.CategoryEditorViewModel
import org.pointyware.commonsense.feature.ontology.viewmodels.CategoryEditorViewModelImpl
import org.pointyware.commonsense.feature.ontology.viewmodels.CategoryExplorerViewModel
import org.pointyware.commonsense.feature.ontology.viewmodels.InstanceEditorViewModel
import org.pointyware.commonsense.feature.ontology.viewmodels.InstanceEditorViewModelImpl
import org.pointyware.commonsense.feature.ontology.viewmodels.RecordEditorViewModel
import org.pointyware.commonsense.feature.ontology.viewmodels.RecordEditorViewModelImpl

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

    single<ConceptEditorController> { ConceptEditorControllerImpl() }

    singleOf(::RecordsRepositoryImpl) { bind<RecordsRepository>() }
}

expect fun ontologyLocalPlatformModule(): Module

fun ontologyLocalModule() = module {
    single<RecordsDataSource> { RecordsSqlDataSource(get())}
    singleOf(::CategorySqlDataSource) { bind<CategoryDataSource>() }
    single<Lazy<OntologyDb>> { lazy {
        val driver: SqlDriver = get()
        OntologyDb.Schema.createOrMigrate(driver)
        OntologyDb(driver)
    } }
    single<SqlDriver> {
        val persistence: Persistence? = getOrNull()
        val driverFactory = get<DriverFactory>()
        driverFactory.createSqlDriver(persistence ?: Persistence.File)
    }

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
    factoryOf(::GetSelectedCategoryUseCase)
    factoryOf(::GetSelectedInstanceUseCase)
    factoryOf(::CreateNewInstanceUseCase)
    factoryOf(::CreateNewCategoryUseCase)
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
    singleOf(::InstanceEditorViewModelImpl) { bind<InstanceEditorViewModel>() }
    singleOf(::RecordEditorViewModelImpl) { bind<RecordEditorViewModel>() }
    singleOf(::CategoryEditorViewModelImpl) { bind<CategoryEditorViewModel>() }
}

fun ontologyUiModule() = module {
    single<ConceptSpaceUiStateMapper> { ConceptSpaceUiStateMapper }
}
