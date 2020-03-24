package org.hexworks.graphite.internal.graph

import org.hexworks.graphite.api.data.Edge
import org.hexworks.graphite.api.data.Vertex
import org.hexworks.graphite.api.graph.Graph
import org.hexworks.graphite.api.graph.base.BaseUndirectedGraph
import org.hexworks.graphite.api.graph.engine.AdjIncidenceGraphEngine

/**
 * undirected multi graph
 */
class MultiGraph : BaseUndirectedGraph() {

    val typedGraphEngine: AdjIncidenceGraphEngine
        get() = graphEngine as AdjIncidenceGraphEngine

    override fun createGraphEngine(graph: Graph) = AdjIncidenceGraphEngine(graph)

    /**
     * get all the edges that connect the two vertices
     *
     * @return a collection of edges that connect the two vertices if any
     */
    fun getMultiEdges(u: Vertex<Any>, v: Vertex<Any>): Collection<Edge<Any>> {
        return graphEngine.getMultiEdges(u, v)
    }

    /**
     * remove all the edges that connect the two vertices
     *
     * @param u first vertex
     * @param v second vertex
     *
     * @return a collection of edges that connect the two vertices that were removed if any
     *
     * @throws java.lang.UnsupportedOperationException if the engine does not support multi edges
     */
    fun removeMultiEdges(u: Vertex<Any>, v: Vertex<Any>): Collection<Edge<Any>> {
        return graphEngine.removeMultiEdges(u, v)
    }

    override fun hasMultiEdges(): Boolean {
        return true
    }

    override fun hasSelfLoops(): Boolean {
        return false
    }

}