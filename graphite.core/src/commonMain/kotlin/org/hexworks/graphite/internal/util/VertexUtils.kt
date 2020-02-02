package org.hexworks.graphite.internal.util

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.graphite.api.data.Vertex
import org.hexworks.graphite.api.graph.Graph

object VertexUtils {

    /**
     * compute the index of a given vertex in a graph. it is okay for occasional query.
     * for frequent queries use [.getVerticesIndices]
     */
    fun indexOf(vertex: Vertex<Any>, graph: Graph): Int {
        for ((index, v) in graph.vertices().withIndex()) {
            if (vertex == v) return index
        }
        return -1
    }

    /**
     * find the vertex at a specific index. it is okay for occasional query.
     * for frequent queries use [.getIndicesVertices]
     */
    fun getVertexAt(index: Int, graph: Graph): Maybe<Vertex<Any>> {
        for ((idx, v) in graph.vertices().withIndex()) {
            if (idx == index) return Maybe.of(v)
        }
        return Maybe.empty()
    }

    /**
     * get a complete mapping between vertices and their corresponding indices in a graph
     */
    fun getVerticesIndices(graph: Graph): MutableMap<Vertex<Any>, Int> {
        val map = mutableMapOf<Vertex<Any>, Int>()
        for ((idx, v) in graph.vertices().withIndex()) {
            map[v] = idx
        }
        return map
    }

    /**
     * get a complete mapping between indices and their corresponding vertices in a graph
     */
    fun getIndicesVertices(graph: Graph): MutableMap<Int, Vertex<Any>> {
        val map = mutableMapOf<Int, Vertex<Any>>()
        for ((idx, v) in graph.vertices().withIndex()) {
            map[idx] = v
        }
        return map
    }
}