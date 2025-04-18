@file:OptIn(ExperimentalUuidApi::class)

package org.pointyware.commonsense.feature.ontology.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.io.files.Path
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.pointyware.commonsense.core.local.readText
import org.pointyware.commonsense.core.local.writeText
import org.pointyware.commonsense.feature.ontology.ConceptSpace
import org.pointyware.commonsense.feature.ontology.MutableConceptSpace
import org.pointyware.commonsense.feature.ontology.mutableOntology
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
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

    override suspend fun loadConceptSpace(file: Path): Result<ConceptSpace> {
        val spaceFile = Path(file)

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

    override suspend fun saveConceptSpace(file: Path): Result<Unit> {
        val space = workSpace

        val spaceJson = ConceptSpaceJson(
            id = space.id,
            focus = OntologyJson(
                id = space.focus.id,
                instances = space.focus.instances.map {
                    InstanceJson(id = it.id)
                }.toSet(),
                relations = space.focus.relations.map { relation ->
                    RelationJson(
                        id = relation.id,
                        source = relation.source2.id,
                        target = relation.target2.id,
                        label = relation.label.id
                    )
                }.toSet()
            )
        )

        file.writeText(json.encodeToString<ConceptSpaceJson>(spaceJson))

        return Result.success(Unit)
    }

//    override suspend fun createNode(name: String): Result<Concept> {
//        val id = generateRandomId()
////        val newNode = IndependentConcept(id, name, description = null)
////        workSpace.focus.addConcept(newNode) // TODO: replace with instance creation?/retrieval
//        mutableActiveSpace.emit(workSpace)
//        return Result.success(newNode)
//    }

    override suspend fun updateNode(id: Uuid, name: String, description: String?): Result<Unit> {
//        val newNode = IndependentConcept(id, name, description)
        return try {
//            workSpace.focus.updateConcept(newNode) // TODO: replace with instance update?/retrieval
            Result.success(Unit)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override suspend fun removeNode(id: Uuid): Result<Unit> {
//        workSpace.focus.removeConcept(id)
        mutableActiveSpace.emit(workSpace)
        return Result.success(Unit)
    }
}
