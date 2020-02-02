package org.hexworks.graphite.api.graph

import org.hexworks.graphite.api.data.Vertex

interface DirectedGraph : Graph {

    /**
     * @param vertex the vertex in question
     */
    fun outDegreeOfVertex(vertex: Vertex<Any>): Int

    /**
     * @param vertex the vertex in question
     */
    fun inDegreeOfVertex(vertex: Vertex<Any>): Int
}