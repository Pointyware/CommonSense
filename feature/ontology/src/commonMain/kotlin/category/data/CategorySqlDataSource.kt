package org.pointyware.commonsense.feature.ontology.category.data

import org.pointyware.commonsense.core.common.Uuid
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

    override suspend fun createCategory(name: String): Result<Category> {
        return try {
            val newUuid = Uuid.v4()
            db.categoryQueries.insertCategory(newUuid.bytes, ByteArray(1), name)
            Result.success(Category(newUuid, name))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
