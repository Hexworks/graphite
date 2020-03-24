package org.hexworks.graphite.internal.graph

import org.hexworks.graphite.api.graph.Graph
import org.hexworks.graphite.api.graph.base.BaseDirectedGraph
import org.hexworks.graphite.api.graph.engine.AdjIncidenceGraphEngine

open class SimpleDirectedGraph : BaseDirectedGraph() {

    val typedGraphEngine: AdjIncidenceGraphEngine
        get() = graphEngine as AdjIncidenceGraphEngine

    override fun hasMultiEdges(): Boolean {
        return false
    }

    override fun hasSelfLoops(): Boolean {
        return false
    }

    override fun createGraphEngine(graph: Graph) = AdjIncidenceGraphEngine(graph)
}