package org.hexworks.graphite.api.graph.engine

import org.hexworks.graphite.api.behavior.Disposable
import org.hexworks.graphite.api.data.Edge
import org.hexworks.graphite.api.data.Vertex
import org.hexworks.graphite.api.graph.Graph

/**
 * The back bone engine of a graph vertices and edges.
 * implement this if ou would like to try different engine, representations, data structures etc
 * for a graph. by default, the interface implies that all types of graphs may be supported.
 */
interface GraphEngine : GraphRepresentation, Iterable<Vertex<Any>>, Disposable {

    var graph: Graph

    /**
     * @return the out degree of  the vertex
     */
    fun outDegreeOfVertex(vertex: Vertex<Any>): Int

    /**
     * @return the in degree of  the vertex
     */
    fun inDegreeOfVertex(vertex: Vertex<Any>): Int

    /**
     * remove all the edges that connect the two vertices
     *
     * @return a collection of edges that connect the two vertices that were removed if any
     */
    fun removeMultiEdges(u: Vertex<Any>, v: Vertex<Any>): Collection<Edge<Any>>

    /**
     * get all the edges that connect the two vertices
     *
     * @return a collection of edges that connect the two vertices if any
     */
    fun getMultiEdges(u: Vertex<Any>, v: Vertex<Any>): Collection<Edge<Any>>

}