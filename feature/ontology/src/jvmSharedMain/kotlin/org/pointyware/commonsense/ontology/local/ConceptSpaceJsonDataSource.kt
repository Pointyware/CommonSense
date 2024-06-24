package org.pointyware.commonsense.ontology.local

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.pointyware.commonsense.ontology.Concept
import org.pointyware.commonsense.ontology.ConceptSpace
import org.pointyware.commonsense.ontology.IndependentConcept
import org.pointyware.commonsense.ontology.mutableOntology
import java.io.File

class ConceptSpaceJsonDataSource(
    private val spaceDirectory: File,
    private val json: Json
): ConceptSpaceDataSource {

    override suspend fun loadConceptSpace(id: String): Result<ConceptSpace> {
        val spaceFile = File(spaceDirectory, "$id.json")

        val spaceJson = json.decodeFromString<ConceptSpaceJson>(spaceFile.readText())

        val ontology = mutableOntology(spaceJson.focus.id) {

        }
//        val decodedConcepts = spaceJson.focus.concepts.map { conceptJson ->
//            Concept(
//                id = conceptJson.id,
//                name = conceptJson.name,
//                description = conceptJson.description,
//                relations = conceptJson.relations
//            )
//        }.toSet()
//        val decodedRelations = spaceJson.focus.relations.map { relationJson ->
//            Relation(
//                id = relationJson.id,
//                name = relationJson.name,
//                description = relationJson.description,
//                source = relationJson.source,
//                target = relationJson.target
//            )
//        }.toSet()

        val space = ConceptSpace(
            id = spaceJson.id,
            focus = ontology,
        )

        return Result.success(space)
    }

    override suspend fun saveConceptSpace(space: ConceptSpace): Result<Unit> {
        val spaceFile = File(spaceDirectory, "${space.id}.json")

        val spaceJson = ConceptSpaceJson(
            id = space.id,
            focus = OntologyJson(
                id = space.focus.id,
                concepts = space.focus.concepts.map { concept ->
                    ConceptJson(
                        id = concept.id,
                        name = concept.name,
                        description = concept.description,
                    )
                }.toSet(),
                relations = space.focus.relations.map { relation ->
                    RelationJson(
                        id = relation.id,
                        name = relation.type,
                        source = relation.source.id,
                        target = relation.target.id,
                        weight = relation.weight
                    )
                }.toSet()
            )
        )

        spaceFile.writeText(json.encodeToString<ConceptSpaceJson>(spaceJson))

        return Result.success(Unit)
    }

    override suspend fun createNode(name: String): Result<Concept> {
        val id = generateRandomId()
        val newNode = IndependentConcept(id, name, description = null)
        return Result.success(newNode)
    }
}
