package org.pointyware.commonsense.ontology.local

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.pointyware.commonsense.ontology.ConceptSpace
import java.io.File

class ConceptSpaceJsonDataSource(
    val spaceDirectory: File,
    val json: Json
): ConceptSpaceDataSource {

    override suspend fun loadConceptSpace(id: String): Result<ConceptSpace> {
        val spaceFile = File(spaceDirectory, "$id.json")

        val spaceJson = json.decodeFromString<ConceptSpaceJson>(spaceFile.readText())

        val space = TODO("map ConceptSpaceJson to ConceptSpace")

        return space
    }

    override suspend fun saveConceptSpace(space: ConceptSpace): Result<Unit> {
        val spaceFile = File(spaceDirectory, "${space.id}.json")

        val spaceJson = TODO("map ConceptSpace to ConceptSpaceJson")

        spaceFile.writeText(json.encodeToString<ConceptSpaceJson>(spaceJson))

        return Result.success(Unit)
    }
}
