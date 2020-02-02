package org.hexworks.graphite.api.data

class UndirectedEdge<T : Any>(
        v1: Vertex<Any>,
        v2: Vertex<Any>,
        weight: Float = 0f
) : Edge<T>(v1, v2, EdgeType.UNDIRECTED, weight)