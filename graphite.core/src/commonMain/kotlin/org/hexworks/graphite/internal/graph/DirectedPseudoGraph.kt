package org.hexworks.graphite.internal.graph

import org.hexworks.graphite.api.graph.base.BaseDirectedGraph
import org.hexworks.graphite.api.graph.engine.GraphEngine

class DirectedPseudoGraph : BaseDirectedGraph() {

    override fun hasMultiEdges() = true

    override fun hasSelfLoops() = true

    val typedGraphEngine: AdjIncidenceGraphEngine
        get() = graphEngine as AdjIncidenceGraphEngine

    override fun createGraphEngine(): GraphEngine {
        return AdjIncidenceGraphEngine()
    }
}