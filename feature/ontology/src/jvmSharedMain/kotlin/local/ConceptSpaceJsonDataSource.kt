package org.pointyware.commonsense.feature.ontology.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.core.local.LocalStorage
import org.pointyware.commonsense.feature.ontology.Concept
import org.pointyware.commonsense.feature.ontology.ConceptSpace
import org.pointyware.commonsense.feature.ontology.IndependentConcept
import org.pointyware.commonsense.feature.ontology.MutableConceptSpace
import org.pointyware.commonsense.feature.ontology.mutableOntology
import java.io.File

class ConceptSpaceJsonDataSource(
    private val json: Json
): ConceptSpaceDataSource {
    private val mutableActiveSpace = MutableSharedFlow<ConceptSpace>(replay = 1)
    override val activeSpace: Flow<ConceptSpace>
        get() = mutableActiveSpace

    private val workSpace: MutableConceptSpace = MutableConceptSpace(
        id = generateRandomId(),
        mutableOntology(id = generateRandomId())
    )

    override suspend fun loadConceptSpace(file: LocalStorage): Result<ConceptSpace> {
        val spaceFile = File(file.path)

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

        val space = MutableConceptSpace(
            id = spaceJson.id,
            focus = ontology,
        )

        return Result.success(space)
    }

    override suspend fun saveConceptSpace(file: LocalStorage): Result<Unit> {
        val space = workSpace
        val spaceFile = File(file.path)

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
        workSpace.focus.addConcept(newNode)
        mutableActiveSpace.emit(workSpace)
        return Result.success(newNode)
    }

    override suspend fun removeNode(id: Uuid): Result<Unit> {
        workSpace.focus.removeConcept(id)
        mutableActiveSpace.emit(workSpace)
        return Result.success(Unit)
    }
}
