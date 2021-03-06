package org.hexworks.graphite.internal.graph

import org.hexworks.graphite.api.graph.Graph
import org.hexworks.graphite.api.graph.base.BaseDirectedGraph
import org.hexworks.graphite.api.graph.engine.AdjIncidenceGraphEngine
import org.hexworks.graphite.api.graph.engine.GraphEngine

class DirectedPseudoGraph : BaseDirectedGraph() {

    val typedGraphEngine: AdjIncidenceGraphEngine
        get() = graphEngine as AdjIncidenceGraphEngine

    override fun hasMultiEdges() = true

    override fun hasSelfLoops() = true

    override fun createGraphEngine(graph: Graph): GraphEngine {
        return AdjIncidenceGraphEngine(graph)
    }
}