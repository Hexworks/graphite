package org.hexworks.graphite.api.graph

import org.hexworks.graphite.api.data.Vertex

interface UndirectedGraph : Graph {

    /**
     * @param vertex the vertex in question
     */
    fun degreeOfVertex(vertex: Vertex<Any>): Int
}