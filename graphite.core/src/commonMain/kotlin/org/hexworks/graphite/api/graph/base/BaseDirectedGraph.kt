package org.hexworks.graphite.api.graph.base

import org.hexworks.graphite.api.data.EdgeType
import org.hexworks.graphite.api.data.Vertex
import org.hexworks.graphite.api.graph.DirectedGraph
import org.hexworks.graphite.internal.graph.AbstractGraph

abstract class BaseDirectedGraph : AbstractGraph(), DirectedGraph {

    final override val graphType: EdgeType
        get() = EdgeType.DIRECTED

    final override fun outDegreeOfVertex(vertex: Vertex<Any>): Int {
        return graphEngine.outDegreeOfVertex(vertex)
    }

    final override fun inDegreeOfVertex(vertex: Vertex<Any>): Int {
        return graphEngine.inDegreeOfVertex(vertex)
    }
}