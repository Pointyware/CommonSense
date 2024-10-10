package org.pointyware.commonsense.feature.ontology.data

import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.core.local.db.createOrMigrate
import org.pointyware.commonsense.feature.ontology.Concept
import org.pointyware.commonsense.feature.ontology.IndependentConcept
import org.pointyware.commonsense.feature.ontology.db.OntologyDb
import org.pointyware.commonsense.feature.ontology.Category
import org.pointyware.commonsense.feature.ontology.local.DriverFactory
import org.pointyware.commonsense.feature.ontology.local.Persistence


/**
 *
 */
@Deprecated("Use RecordsSqlDataSource")
class CategorySqlDataSource(
    private val driverFactory: DriverFactory,
    private val persistence: Persistence = Persistence.File
): CategoryDataSource {

    private val driver = driverFactory.createSqlDriver(persistence)
    private val db by lazy {
        OntologyDb.Schema.createOrMigrate(driver)
        OntologyDb(driver)
    }

    override suspend fun createCategory(name: String): Result<Category> = runCatching {
        val newUuid = Uuid.v4()
        db.categoryQueries.insertCategory(newUuid.bytes, ByteArray(1), name)
        Category(newUuid, name)
    }

    override suspend fun addCategory(subject: Uuid, name: String): Result<Category> = runCatching {
        val newUuid = Uuid.v4()
        db.categoryQueries.insertCategory(newUuid.bytes, subject.bytes, name)
        Category(newUuid, name)
    }

    override suspend fun getCategory(id: Uuid): Result<Category> = runCatching {
        db.categoryQueries.getCategory(id.bytes) { uuid, name ->
            Category(Uuid(uuid), name)
        }.executeAsOne()
    }

    override suspend fun getSubcategories(id: Uuid): Result<List<Category>> = runCatching {
        db.categoryQueries.getCategories(id.bytes) { uuid, name ->
            Category(Uuid(uuid), name)
        }.executeAsList()
    }

    override suspend fun addConcept(
        subject: Uuid,
        name: String,
        description: String
    ): Result<Concept> = runCatching {
        val newUuid = Uuid.v4()
        db.categoryQueries.insertConcept(subject.bytes, newUuid.bytes, name, description)
        IndependentConcept(newUuid, name, description)
    }

    override suspend fun getConcepts(id: Uuid): Result<List<Concept>> = runCatching {
        db.categoryQueries.getConcepts(id.bytes) { uuid, name, description ->
            IndependentConcept(Uuid(uuid), name, description)
        }.executeAsList()
    }

    override suspend fun removeCategories(ids: Set<Uuid>): Result<Unit> = runCatching {
        db.categoryQueries.deleteCategories(ids.map { it.bytes })
    }

    override suspend fun removeConcepts(ids: Set<Uuid>): Result<Unit> = runCatching{
        db.categoryQueries.deleteConcepts(ids.map { it.bytes })
    }
}
