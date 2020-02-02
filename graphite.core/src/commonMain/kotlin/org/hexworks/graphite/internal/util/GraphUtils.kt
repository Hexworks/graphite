package org.hexworks.graphite.internal.util

import org.hexworks.graphite.api.data.Edge
import org.hexworks.graphite.api.graph.Graph
import org.hexworks.graphite.internal.data.EdgeWeightComparator

object GraphUtils {

    /**
     * helper method for sorting weighted edges of a graph
     * @param graph the graph
     * @param order `EdgeSortOrder.NONDECREASING, EdgeSortOrder.NONINCREASING`
     * @return array list of sorted edges
     */
    fun sortWeightedEdgesOf(
            graph: Graph,
            order: EdgeSortOrder
    ): List<Edge<Any>> {
        val edges = graph.edges().toMutableList()
        edges.sortWith(if (order == EdgeSortOrder.NONDECREASING) EdgeWeightComparator() else EdgeWeightComparator().reversed())
        return edges
    }

    /**
     * common vertices colors for graphs search algorithms
     */
    enum class COLOR {
        WHITE, GREY, BLACK
    }

    /**
     * sort order enum for weighted edges
     * @see .sortWeightedEdgesOf
     */
    enum class EdgeSortOrder {
        NONDECREASING, NONINCREASING
    }
}