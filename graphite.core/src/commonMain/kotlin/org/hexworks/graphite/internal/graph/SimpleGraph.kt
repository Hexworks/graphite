package org.hexworks.graphite.internal.graph

import org.hexworks.graphite.api.graph.Graph
import org.hexworks.graphite.api.graph.base.BaseUndirectedGraph
import org.hexworks.graphite.api.graph.engine.AdjIncidenceGraphEngine

/**
 * undirected simple graph
 */
class SimpleGraph : BaseUndirectedGraph() {

    val typedGraphEngine: AdjIncidenceGraphEngine
        get() = graphEngine as AdjIncidenceGraphEngine

    override fun hasMultiEdges(): Boolean {
        return false
    }

    override fun hasSelfLoops(): Boolean {
        return false
    }

    override fun createGraphEngine(graph: Graph) = AdjIncidenceGraphEngine(this)
}