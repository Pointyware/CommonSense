package org.pointyware.commonsense.feature.ontology.category.data

import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.feature.ontology.Concept
import org.pointyware.commonsense.feature.ontology.IndependentConcept
import org.pointyware.commonsense.feature.ontology.db.OntologyDb
import org.pointyware.commonsense.feature.ontology.entities.Category
import org.pointyware.commonsense.feature.ontology.local.DriverFactory


/**
 *
 */
class CategorySqlDataSource(
    private val driverFactory: DriverFactory
): CategoryDataSource {

    private val driver = driverFactory.createDriver()
    private val db = OntologyDb(driver)

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

    override suspend fun getCategory(id: Uuid): Result<Category> {
        TODO("Not yet implemented")
    }

    override suspend fun getSubcategories(id: Uuid): Result<List<Category>> {
        TODO("Not yet implemented")
    }

    override suspend fun addConcept(
        subject: Uuid,
        name: String,
        description: String
    ): Result<Concept> = kotlin.runCatching {
        val newUuid = Uuid.v4()
        db.categoryQueries.insertConcept(subject.bytes, newUuid.bytes, name, description)
        IndependentConcept(newUuid, name, description)
    }

    override suspend fun getConcepts(id: Uuid): Result<List<Concept>> {
        TODO("Not yet implemented")
    }
}
