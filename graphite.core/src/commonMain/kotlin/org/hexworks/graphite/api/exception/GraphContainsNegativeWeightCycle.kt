package org.hexworks.graphite.api.exception

import org.hexworks.graphite.api.algorithm.GraphAlgorithm
import org.hexworks.graphite.api.graph.Graph

class GraphContainsNegativeWeightCycle(
        algorithm: GraphAlgorithm<Any, Graph>
) : AlgorithmException("Single Source Shortest Path on a graph with negative weight cycle.", algorithm)