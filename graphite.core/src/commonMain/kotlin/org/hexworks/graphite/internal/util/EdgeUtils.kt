package org.hexworks.graphite.internal.util

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.graphite.api.data.Edge
import org.hexworks.graphite.api.graph.Graph

object EdgeUtils {
    /**
     * compute the index of a given edge in a graph. it is okay for occasional query.
     * for frequent queries use [.getEdgesIndices]
     *
     * @return the index of the edge, or -1 if none.
     */
    fun indexOf(edge: Edge<Any>, graph: Graph): Int {
        for ((index, e) in graph.edges().withIndex()) {
            if (edge == e) return index
        }
        return -1
    }

    /**
     * find the edge at a specific index. it is okay for occasional query.
     * for frequent queries use [.getIndicesEdges]
     *
     * @return the edge at the specified index/location, or `null` otherwise
     */
    fun getEdgeAt(index: Int, graph: Graph): Maybe<Edge<Any>> {
        for ((idx, e) in graph.edges().withIndex()) {
            if (idx == index) return Maybe.of(e)
        }
        return Maybe.empty()
    }

    /**
     * get a complete mapping between edges and their corresponding indices in a graph
     *
     * @see .getIndicesEdges
     */
    fun getEdgesIndices(graph: Graph): MutableMap<Edge<Any>, Int> {
        val result = mutableMapOf<Edge<Any>, Int>()
        graph.edges().forEachIndexed { index, edge ->
            result[edge] = index
        }
        return result
    }

    /**
     * get a complete mapping between indices and their corresponding edges in a graph
     *
     * @param graph the graph
     *
     * @return a correspondence mapping
     *
     * @see .getEdgesIndices
     */
    fun getIndicesEdges(graph: Graph): MutableMap<Int, Edge<Any>> {
        val result = mutableMapOf<Int, Edge<Any>>()
        graph.edges().forEachIndexed { index, edge ->
            result[index] = edge
        }
        return result
    }
}
