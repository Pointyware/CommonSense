package org.pointyware.commonsense.core.entities

/**
 * @param V The value type of the vertices in the graph.
 * @param L The label type of the edges in the graph.
 */
interface Graph<V, L> {
    val nodes: Set<Node<V>>
    fun edgesFrom(node: Node<V>?): Set<Edge<V, L>>
    fun edgesTo(node: Node<V>?): Set<Edge<V, L>>
}

interface Node<V> {
    val value: V
}

interface Edge<V, L> {
    val start: Node<V>?
    val end: Node<V>?
    val label: L
}

interface MutableGraph<V, L> : Graph<V, L> {
    fun addNode(node: Node<V>)
    fun removeNode(node: Node<V>)
    fun addEdge(edge: Edge<V, L>)
    fun removeEdge(edge: Edge<V, L>)
}
