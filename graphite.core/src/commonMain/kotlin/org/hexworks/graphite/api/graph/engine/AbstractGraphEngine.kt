package org.hexworks.graphite.api.graph.engine

import org.hexworks.graphite.api.data.Edge
import org.hexworks.graphite.api.data.EdgeType
import org.hexworks.graphite.api.data.Vertex
import org.hexworks.graphite.api.data.base.DefaultVertex
import org.hexworks.graphite.api.graph.Graph

@Suppress("SetterBackingFieldAssignment")
abstract class AbstractGraphEngine(
        initialGraph: Graph
) : GraphEngine {

    override var graph: Graph = initialGraph
        set(_) {
            error("graph can only be setup once")
        }

    override val graphType: EdgeType
        get() = graph.graphType

    override val isEmpty: Boolean
        get() = vertices().isEmpty() && edges().isEmpty()

    val isMultiEdgesSupported: Boolean
        get() = graph.hasMultiEdges()

    val isSelfLoopsSupported: Boolean
        get() = graph.hasSelfLoops()

    override fun addAll(
            vertices: Collection<Vertex<Any>>,
            edges: Collection<Edge<Any>>
    ) {
        for (vertex in vertices) {
            addVertex(vertex)
        }
        for (edge in edges) {
            addEdge(edge)
        }
    }

    /**
     * print the graph:: vertices and edges
     */
    override fun print() {
        println(verticesToString())
        println(edgesToString())
    }

    fun verticesToString(): String {
        var res = "V = {"
        for (v in vertices()) {
            res += DefaultVertex.toString(v).toString() + ", "
        }
        res += "}"
        return res
    }

    fun edgesToString(): String {
        var res = "E = {"
        for (e in edges()) {
            res += "$e, "
        }
        res += "}"
        return res
    }

    override fun toString(): String {
        return verticesToString() + " " + edgesToString()
    }
}