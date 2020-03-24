package org.hexworks.graphite.api.exception

import org.hexworks.graphite.api.algorithm.GraphAlgorithm
import org.hexworks.graphite.api.graph.Graph

open class AlgorithmException(message: String,
                         algorithm: GraphAlgorithm<Any, Graph>
) : RuntimeException("Algorithm " + algorithm.tag.orElse("null") + ":: message - " + message)