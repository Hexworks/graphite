package org.hexworks.graphite.api.exception

import org.hexworks.graphite.api.data.Vertex
import org.hexworks.graphite.api.graph.Graph

class VertexNotFoundException(
        v: Vertex<Any>,
        graph: Graph
) : GraphException("${v.id.orElse("null")} not found in graph", graph)