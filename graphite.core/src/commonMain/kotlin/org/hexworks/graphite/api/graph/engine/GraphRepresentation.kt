package org.hexworks.graphite.api.graph.engine

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.graphite.api.data.Edge
import org.hexworks.graphite.api.data.EdgeType
import org.hexworks.graphite.api.data.Vertex

interface GraphRepresentation {

    val graphType: EdgeType

    fun vertices(): Collection<Vertex<Any>>

    fun edges(): Collection<Edge<Any>>

    /**
     * Returns the accessible vertex neighbors of [vertex].
     */
    fun getNeighborsOf(vertex: Vertex<Any>): Collection<Vertex<Any>>

    /**
     * Returns the list of incident edges that go out from vertex
     */
    fun getIncidenceOutListOf(vertex: Vertex<Any>): Collection<Edge<Any>>

    /**
     * Returns the list of incident edges that go into the vertex
     */
    fun getIncidenceInListOf(vertex: Vertex<Any>): Collection<Edge<Any>>

    /**
     * Tells whether [vertex] belongs to the graph's vertex list.
     */
    fun hasVertex(vertex: Vertex<Any>): Boolean

    /**
     * Adds [vertex] to the graph.
     */
    fun addVertex(vertex: Vertex<Any>): Boolean

    /**
     * Adds all the vertices and edges of the collection into the graph.
     */
    fun addAll(vertices: Collection<Vertex<Any>>, edges: Collection<Edge<Any>>)

    /**
     * Removes [vertex] from the graph.
     */
    fun removeVertex(vertex: Vertex<Any>): Boolean

    /**
     * Checks whether there exists an edge (v1, v2) in graph.
     */
    fun hasEdge(v1: Vertex<Any>, v2: Vertex<Any>): Boolean

    /**
     * Checks whether edge instance is in the graph.
     */
    fun hasEdge(edge: Edge<Any>): Boolean

    /**
     * Connects the vertices [v1] and [v2] which are already
     * in the graph.
     * @return the newly created [Edge]
     */
    fun addEdge(v1: Vertex<Any>, v2: Vertex<Any>): Maybe<Edge<Any>>

    /**
     * Connects the vertices [v1] and [v2] which are already
     * in the graph.
     * @return the newly created [Edge]
     */
    fun addEdge(v1: Vertex<Any>, v2: Vertex<Any>, weight: Float): Maybe<Edge<Any>>

    /**
     * Adds the given [Edge] to the graph. [Edge.v1] and [Edge.v2]
     * have to be members.
     */
    fun addEdge(edge: Edge<Any>): Maybe<Edge<Any>>

    /**
     * Removes the [Edge] with vertices ([v1], [v2]) from the graph.
     */
    fun removeEdge(v1: Vertex<Any>, v2: Vertex<Any>): Maybe<Edge<Any>>

    /**
     * Returns the edge that connects ([v1], [v2]) from the graph.
     */
    fun getEdge(v1: Vertex<Any>, v2: Vertex<Any>): Maybe<Edge<Any>>

    /**
     * Removes [edge] form the graph.
     */
    fun removeEdge(edge: Edge<Any>): Maybe<Edge<Any>>

    /**
     * @return the number of vertices in the Graph
     */
    fun numVertices(): Int

    /**
     * @return the number of edges in the graph
     */
    fun numEdges(): Int

    /**
     * @return true if graph is empty, False - otherwise
     */
    val isEmpty: Boolean

    /**
     * clear the graph into an empty graph
     */
    fun clear()

    fun print()
}