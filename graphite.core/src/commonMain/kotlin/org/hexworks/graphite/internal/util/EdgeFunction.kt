package org.hexworks.graphite.internal.util

import org.hexworks.graphite.api.behavior.Disposable
import org.hexworks.graphite.api.data.Edge
import org.hexworks.graphite.api.data.EdgeType
import org.hexworks.graphite.api.data.Vertex

class EdgeFunction(
        edges: Collection<Edge<Any>>
) : Disposable {

    protected var mapEdges = linkedMapOf<String, Float>()


    /**
     * add all of the edges to the function and use their [Edge.weight]
     * as the value
     *
     * @param edges a [java.util.Collection] of edges
     */
    fun addAll(edges: Collection<Edge<Any>>) {
        for (edge in edges) {
            addValue(edge, edge.weight)
        }
    }

    /**
     * add a new value for edge `(u, v)`
     *
     * @param u     first vertex
     * @param v     second vertex
     * @param value the value
     */
    fun addValue(u: Vertex<Any>, v: Vertex<Any>, value: Float) {
        val edgeDesc: String = Edge.getEdgeDesc(u, v, EdgeType.DIRECTED)
        mapEdges.put(edgeDesc, value)
    }

    /**
     * add a new value for edge `e`
     *
     * @param e     the edge
     * @param value the value
     */
    fun addValue(e: Edge<Any>, value: Float) {
        addValue(e.v1, e.v2, value)
    }

    /**
     * get the function value of edge `(u, v)`
     *
     * @param u     first vertex
     * @param v     second vertex
     *
     * @return the function value of the edge
     *
     * @throws EdgeFunction.NoFunctionValueException if there is no value
     */
    fun valueOf(u: Vertex<Any>, v: Vertex<Any>): Float {
        val edgeDesc: String = Edge.getEdgeDesc(u, v, EdgeType.DIRECTED)
        return mapEdges[edgeDesc] ?: throw NoFunctionValueException(u, v)
    }

    /**
     * get the function value of edge `e`
     *
     * @param e     the edge
     *
     * @return the function value of the edge
     *
     * @throws EdgeFunction.NoFunctionValueException if there is no value
     */
    fun valueOf(e: Edge<Any>, value: Float): Float {
        return valueOf(e.v1, e.v2)
    }

    /**
     * dispose the function
     */
    override fun dispose() {
        mapEdges.clear()
    }

    /**
     * general [java.lang.RuntimeException] for when trying to query
     * a missing function value
     */
    protected inner class NoFunctionValueException : RuntimeException {
        constructor(e: Edge<Any>) : super("No value for edge: " + e.desc) {}
        constructor(u: Vertex<Any>, v: Vertex<Any>) : super("No value for edge: " + Edge.getEdgeDesc(u, v, EdgeType.DIRECTED)) {}
    }
}