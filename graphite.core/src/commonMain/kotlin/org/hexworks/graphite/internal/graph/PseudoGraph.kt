package org.hexworks.graphite.internal.graph

import org.hexworks.graphite.api.graph.Graph
import org.hexworks.graphite.api.graph.base.BaseUndirectedGraph
import org.hexworks.graphite.api.graph.engine.AdjIncidenceGraphEngine

/**
 * Pseudo graph
 */
class PseudoGraph : BaseUndirectedGraph() {

    val typedGraphEngine: AdjIncidenceGraphEngine
        get() = graphEngine as AdjIncidenceGraphEngine

    override fun hasMultiEdges(): Boolean {
        return true
    }

    override fun hasSelfLoops(): Boolean {
        return true
    }

    override fun createGraphEngine(graph: Graph) = AdjIncidenceGraphEngine(graph)
}