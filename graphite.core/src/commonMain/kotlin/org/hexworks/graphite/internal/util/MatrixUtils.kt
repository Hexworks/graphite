package org.hexworks.graphite.internal.util

import org.hexworks.graphite.api.data.Edge
import org.hexworks.graphite.api.data.EdgeType
import org.hexworks.graphite.api.data.Vertex
import org.hexworks.graphite.api.graph.Graph

object MatrixUtils {

    /**
     * compute the adjacency matrix of a graph
     *
     * @param fillWeights   if `true` then values will be replaced with weights.
     *
     * @return the adjacency matrix of the graph
     */
    fun adjacencyMatrixOf(
            graph: Graph,
            fillWeights: Boolean
    ): Array<FloatArray> {
        return adjacencyMatrixOf(graph, 0f, fillWeights, 0f)
    }

    /**
     * compute the adjacency matrix of a graph with custom conditions
     *
     * @param graph             the graph
     * @param condition1_value  the value to fill if `e{i, j}` and `i==j`
     * @param fillWeights       if `true` then values will be replaced with weights, other wise 1
     * @param condition2_value  the value to fill if `e{i, j}` does not belong to E and `i!=j`
     *
     * @return the custom adjacency matrix of the graph
     */
    fun adjacencyMatrixOf(
            graph: Graph,
            condition1_value: Float,
            fillWeights: Boolean,
            condition2_value: Float
    ): Array<FloatArray> {
        val numVertices: Int = graph.numVertices()
        val graphType: EdgeType = graph.graphType
        val matrix = Array(numVertices) { FloatArray(numVertices) }
        // init
// condition 2 => the value to fill if {@code e{i, j}} does not belong to E and {@code i!=j}
        for (ma in matrix.indices) {
            for (mb in matrix[ma].indices) {
                matrix[ma][mb] = condition2_value
            }
        }
        // condition 1 => the value to fill if {@code e{i, j}} and {@code i==j}
        for (ma in matrix.indices) {
            matrix[ma][ma] = condition1_value
        }
        // fill weights or presence
        val verticesIndices = VertexUtils.getVerticesIndices(graph)
        var v1: Vertex<Any>
        var v2: Vertex<Any>
        var v1Index: Int
        var v2Index: Int
        for (edge in graph.edges()) {
            v1 = edge.v1
            v2 = edge.v2
            v1Index = verticesIndices[v1]!!
            v2Index = verticesIndices[v2]!!
            matrix[v1Index][v2Index] = if (fillWeights) edge.weight else 1f
            if (graphType === EdgeType.UNDIRECTED) matrix[v2Index][v1Index] = matrix[v1Index][v2Index]
        }
        return matrix
    }

    /**
     * return the incidence matrix of a graph `G=<V, E>`.<br></br>
     * i.e, a `|V|x|E|` matrix `M` where, `M(i,j)=1` if vertex with index `i` is incident to an edge with <br></br>
     * index `j`, otherwise `M(i,j)=0`.
     *
     * @param graph the graph whose incidence matrix is to be computed
     *
     * @return the incidence matrix
     */
    fun incidenceMatrixOf(graph: Graph): Array<FloatArray> {
        val numVertices: Int = graph.numVertices()
        val numEdges: Int = graph.numEdges()
        val matrix = Array(numVertices) { FloatArray(numEdges) }
        val indicesVertices = VertexUtils.getIndicesVertices(graph)
        val indicesEdges = EdgeUtils.getIndicesEdges(graph)
        var incidenceInListOf: Collection<Edge<Any>>
        var incidenceOutListOf: Collection<Edge<Any>>
        var edge: Edge<Any>
        for (v_index in matrix.indices) {
            for (e_index in matrix[v_index].indices) {
                incidenceInListOf = graph.getIncidenceInListOf(indicesVertices[v_index]!!)
                incidenceOutListOf = graph.getIncidenceOutListOf(indicesVertices[v_index]!!)
                edge = indicesEdges.get(e_index)!!
                if (incidenceInListOf.contains(edge) || incidenceOutListOf.contains(edge)) matrix[v_index][e_index] = 1f
            }
        }
        return matrix
    }

    /**
     * deep copy a 2D array
     */
    fun array2dCopy(
            src: Array<Array<Any>>,
            dest: Array<Array<Any>>
    ) {
        val n = src.size
        for (i in 0 until n) {
            src[i].copyInto(dest[i], 0, 0, n)
        }
    }

    /**
     * deep copy a 2D array
     */
    fun array2dCopy(
            src: Array<FloatArray>,
            dest: Array<FloatArray>
    ) {
        val n = src.size
        for (i in 0 until n) {
            src[i].copyInto(dest[i], 0, 0, n)
        }
    }

    /**
     * deep copy a 2D array
     */
    fun array2dCopy(
            src: Array<IntArray>,
            dest: Array<IntArray>
    ) {
        val n = src.size
        for (i in 0 until n) {
            src[i].copyInto(dest[i], 0, 0, n)
        }
    }
}