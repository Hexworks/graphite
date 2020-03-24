package org.hexworks.graphite.api.graph

import org.hexworks.graphite.api.behavior.Disposable
import org.hexworks.graphite.api.behavior.Identifiable
import org.hexworks.graphite.api.behavior.Taggable
import org.hexworks.graphite.api.data.Vertex
import org.hexworks.graphite.api.graph.engine.GraphEngine
import org.hexworks.graphite.api.graph.engine.GraphRepresentation

interface Graph : GraphRepresentation, Identifiable, Taggable, Disposable, Iterable<Vertex<Any>> {

    val graphEngine: GraphEngine

    fun createGraphEngine(graph: Graph): GraphEngine

    /**
     * does this graph support multi edges
     */
    fun hasMultiEdges(): Boolean

    /**
     * does this graph support self loops
     */
    fun hasSelfLoops(): Boolean

}
