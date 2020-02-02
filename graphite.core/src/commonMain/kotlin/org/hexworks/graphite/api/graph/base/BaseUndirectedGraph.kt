package org.hexworks.graphite.api.graph.base

import org.hexworks.graphite.api.data.EdgeType
import org.hexworks.graphite.api.data.Vertex
import org.hexworks.graphite.api.graph.UndirectedGraph
import org.hexworks.graphite.internal.graph.AbstractGraph

abstract class BaseUndirectedGraph : AbstractGraph(), UndirectedGraph {

    final override val graphType: EdgeType
        get() = EdgeType.UNDIRECTED

    final override fun degreeOfVertex(vertex: Vertex<Any>): Int {
        return graphEngine.inDegreeOfVertex(vertex)
    }
}