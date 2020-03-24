package org.hexworks.graphite.api.exception

import org.hexworks.graphite.api.algorithm.GraphAlgorithm
import org.hexworks.graphite.api.graph.Graph

class NegativeEdgeWeightException(
        algorithm: GraphAlgorithm<Any, Graph>
) : AlgorithmException("algorithm does not support negative edge weights!! ", algorithm)