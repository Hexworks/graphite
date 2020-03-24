package org.hexworks.graphite.internal.graph

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.graphite.api.data.Edge
import org.hexworks.graphite.api.data.Vertex
import org.hexworks.graphite.api.graph.Graph
import org.hexworks.graphite.api.graph.engine.GraphEngine

abstract class AbstractGraph : Graph {

    final override var id: Maybe<String> = Maybe.empty()
    final override var tag: Maybe<String> = Maybe.empty()

    final override val isEmpty: Boolean
        get() = this.graphEngine.isEmpty

    final override val graphEngine: GraphEngine = createGraphEngine(this)

    final override operator fun iterator(): Iterator<Vertex<Any>> {
        return this.graphEngine.iterator()
    }

    final override fun vertices(): Collection<Vertex<Any>> {
        return this.graphEngine.vertices()
    }

    final override fun edges(): Collection<Edge<Any>> {
        return this.graphEngine.edges()
    }

    final override fun getNeighborsOf(vertex: Vertex<Any>): Collection<Vertex<Any>> {
        return this.graphEngine.getNeighborsOf(vertex)
    }

    final override fun getIncidenceOutListOf(vertex: Vertex<Any>): Collection<Edge<Any>> {
        return this.graphEngine.getIncidenceOutListOf(vertex)
    }

    final override fun getIncidenceInListOf(vertex: Vertex<Any>): Collection<Edge<Any>> {
        return this.graphEngine.getIncidenceInListOf(vertex)
    }

    final override fun hasVertex(vertex: Vertex<Any>): Boolean {
        return this.graphEngine.hasVertex(vertex)
    }

    final override fun addVertex(vertex: Vertex<Any>): Boolean {
        return this.graphEngine.addVertex(vertex)
    }

    final override fun addAll(vertices: Collection<Vertex<Any>>, edges: Collection<Edge<Any>>) {
        this.graphEngine.addAll(vertices, edges)
    }

    final override fun removeVertex(vertex: Vertex<Any>): Boolean {
        return this.graphEngine.removeVertex(vertex)
    }

    final override fun hasEdge(v1: Vertex<Any>, v2: Vertex<Any>): Boolean {
        return this.graphEngine.hasEdge(v1, v2)
    }

    final override fun hasEdge(edge: Edge<Any>): Boolean {
        return this.graphEngine.hasEdge(edge)
    }

    final override fun addEdge(v1: Vertex<Any>, v2: Vertex<Any>): Edge<Any> {
        return this.graphEngine.addEdge(v1, v2)
    }

    final override fun addEdge(v1: Vertex<Any>, v2: Vertex<Any>, weight: Float): Edge<Any> {
        return this.graphEngine.addEdge(v1, v2, weight)
    }

    final override fun addEdge(edge: Edge<Any>): Maybe<Edge<Any>> {
        return this.graphEngine.addEdge(edge)
    }

    final override fun removeEdge(v1: Vertex<Any>, v2: Vertex<Any>): Edge<Any> {
        return this.graphEngine.removeEdge(v1, v2)
    }

    final override fun getEdge(v1: Vertex<Any>, v2: Vertex<Any>): Edge<Any> {
        return this.graphEngine.getEdge(v1, v2)
    }

    final override fun removeEdge(edge: Edge<Any>): Maybe<Edge<Any>> {
        return this.graphEngine.removeEdge(edge)
    }

    final override fun numVertices(): Int {
        return this.graphEngine.numVertices()
    }

    final override fun numEdges(): Int {
        return this.graphEngine.numEdges()
    }

    final override fun clear() {
        this.graphEngine.clear()
    }

    final override fun dispose() {
        this.graphEngine.dispose()
    }

    final override fun hasId(): Boolean {
        return id.isPresent
    }

    final override fun print() {
        println(toString())
    }

    final override fun toString(): String {
        return "id=" + id + ", tag=" + tag + "::" + this.graphEngine.toString()
    }
}