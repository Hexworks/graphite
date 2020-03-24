package org.hexworks.graphite.api.exception

import org.hexworks.graphite.api.graph.Graph

open class GraphException(
        message: String,
        graph: Graph
) : RuntimeException("Graph ID: ${graph.id.orElse("null")}, tag: ${graph.tag.orElse("null")} :: Error - " + message)