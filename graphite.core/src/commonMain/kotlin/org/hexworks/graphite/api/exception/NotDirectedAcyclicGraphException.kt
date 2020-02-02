package org.hexworks.graphite.api.exception

import org.hexworks.graphite.api.algorithm.GraphAlgorithm
import org.hexworks.graphite.api.graph.Graph

class NotDirectedAcyclicGraphException(
        algorithm: GraphAlgorithm<Any, Graph>
) : AlgorithmException("Topological sorting on a non DAG", algorithm)