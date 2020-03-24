package org.hexworks.graphite.api.graph.engine

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.graphite.api.data.Edge
import org.hexworks.graphite.api.data.EdgeType
import org.hexworks.graphite.api.data.Vertex
import org.hexworks.graphite.api.exception.VertexNotFoundException
import org.hexworks.graphite.api.graph.Graph

/**
 * a graph engine implementation that uses both **Adjacency list** and **Incidence list**<br></br>
 * designed for optimal complexity for algorithms that require more than a moderate edge<br></br>
 * queries. it does consume more memory as a consequence. can handle both directed and undirected edges.
 * todo: add multi edge capabilities and self loops
 */
class AdjIncidenceGraphEngine(initialGraph: Graph) : AbstractGraphEngine(initialGraph) {

    /**
     * the Vertices list of the Graph G=(V, E)
     */
    private var vertices: MutableMap<String, Vertex<Any>> = linkedMapOf()
    /**
     * the Vertices Adj list
     */
    private val colAdjLists: MutableMap<Vertex<Any>, MutableSet<Vertex<Any>>> = mutableMapOf()
    /**
     * the edges map
     */
    private val setEdges: MutableSet<Edge<Any>> = linkedSetOf()
    /**
     * the presence map
     */
    private val mapPresenceEdges: MutableMap<String, MutableList<Edge<Any>>> = mutableMapOf()
    /**
     * the incidence in list
     */
    private var inEdges: MutableMap<Vertex<Any>, MutableSet<Edge<Any>>> = mutableMapOf()
    /**
     * the incidence out list
     */
    private val outEdges: MutableMap<Vertex<Any>, MutableSet<Edge<Any>>> = mutableMapOf()

    override var graph: Graph
        get() = super.graph
        set(value) {
            super.graph = value
            if (graphType === EdgeType.UNDIRECTED) {
                inEdges = outEdges
            }
        }

    override operator fun iterator(): Iterator<Vertex<Any>> {
        return VerticesIterator()
    }

    override fun vertices(): Collection<Vertex<Any>> = verticesView

    override fun edges(): Collection<Edge<Any>> {
        return if (edgesView == null) EdgesView().also { edgesView = it } else edgesView
    }

    override fun getNeighborsOf(vertex: Vertex<Any>): Collection<Vertex<Any>> {
        return VertexAdjView(vertex)
    }

    override fun getIncidenceOutListOf(vertex: Vertex<Any>): Collection<Edge<Any>> {
        return IncidenceOutView(vertex)
    }

    override fun getIncidenceInListOf(vertex: Vertex<Any>): Collection<Edge<Any>> {
        return IncidenceInView(vertex)
    }

    override fun hasVertex(vertex: Vertex<Any>): Boolean {
        return vertices.containsValue(vertex)
    }

    override fun addVertex(vertex: Vertex<Any>): Boolean {
        if (hasVertex(vertex)) return false
        vertices[vertex.id.get()] = vertex
        if (colAdjLists[vertex] == null) colAdjLists[vertex] = mutableSetOf()
        inEdges[vertex] = mutableSetOf()
        outEdges[vertex] = mutableSetOf()
        return true
    }

    override fun removeVertex(vertex: Vertex<Any>): Boolean {
        removeEdgeNeighborsOfVertex(vertex)
        return vertices.values.remove(vertex)
    }

    /**
     * responsive/modifiable collection views for iteration outside
     * the class.
     */
    var verticesView: Collection<Vertex<Any>> = VerticesView()
    var edgesView: Collection<Edge<Any>> = EdgesView()

    /**
     * vertices view
     */
    inner class VerticesView : AbstractMutableCollection<Vertex<Any>>() {

        private val values: Collection<Vertex<Any>> = vertices.values

        override operator fun iterator(): MutableIterator<Vertex<Any>> {
            return VerticesIterator()
        }

        override val size: Int
            get() = values.size

        override operator fun contains(element: Vertex<Any>): Boolean {
            return values.contains(element)
        }

        override fun add(element: Vertex<Any>): Boolean {
            return addVertex(element)
        }

        override fun remove(element: Vertex<Any>): Boolean {
            return removeVertex(element)
        }

    }

    inner class VerticesIterator : MutableIterator<Vertex<Any>> {

        private val parent: MutableIterator<Vertex<Any>> = vertices.values.iterator()

        private lateinit var current: Vertex<Any>

        override fun hasNext(): Boolean {
            return parent.hasNext()
        }

        override fun next(): Vertex<Any> {
            return parent.next().also { current = it }
        }

        override fun remove() {
            removeEdgeNeighborsOfVertex(current)
            parent.remove()
        }
    }

    inner class EdgesView : AbstractMutableCollection<Edge<Any>>() {

        private val values: Collection<Edge<Any>> = setEdges

        override val size: Int
            get() = values.size

        override operator fun iterator(): MutableIterator<Edge<Any>> {
            return EdgesIterator()
        }

        override fun contains(element: Edge<Any>): Boolean {
            return values.contains(element)
        }

        override fun add(element: Edge<Any>): Boolean {
            return addEdge(element).isPresent
        }

        override fun remove(element: Edge<Any>): Boolean {
            return removeEdge(element).isPresent
        }
    }

    inner class EdgesIterator : MutableIterator<Edge<Any>> {

        private val parent: MutableIterator<Edge<Any>> = setEdges.iterator()

        private lateinit var current: Edge<Any>

        override fun hasNext(): Boolean {
            return parent.hasNext()
        }

        override fun next(): Edge<Any> {
            return parent.next().also { current = it }
        }

        override fun remove() {
            removeEdgeAdjList(current)
            removeEdgeIncidenceList(current)
            removeEdgePresenceList(current)
            parent.remove()
        }
    }

    inner class VertexAdjView(private val vertex: Vertex<Any>) : AbstractCollection<Vertex<Any>>() {

        override val size: Int
            get() = values.size

        private val values: Collection<Vertex<Any>> = colAdjLists[vertex]!!

        override operator fun iterator(): Iterator<Vertex<Any>> {
            return VertexAdjIterator(vertex)
        }

        override operator fun contains(element: Vertex<Any>): Boolean {
            return values.contains(element)
        }
    }

    inner class VertexAdjIterator(vertex: Vertex<Any>) : Iterator<Vertex<Any>> {

        private val parent = colAdjLists[vertex]!!.iterator()

        override fun hasNext(): Boolean {
            return parent.hasNext()
        }

        override fun next(): Vertex<Any> {
            return parent.next()
        }
    }

    /**
     * edges incidence out list view of a vertex.
     *
     *
     * removal/addition of elements are not supported yet
     */
    inner class IncidenceOutView(private val vertex: Vertex<Any>) : AbstractCollection<Edge<Any>>() {

        override val size: Int
            get() = values.size

        private val values: Collection<Edge<Any>> = outEdges[vertex]!!

        override operator fun iterator(): Iterator<Edge<Any>> {
            return IncidenceOutViewIterator(vertex)
        }

        override operator fun contains(element: Edge<Any>): Boolean {
            return values.contains(element)
        }
    }

    inner class IncidenceOutViewIterator(vertex: Vertex<Any>) : Iterator<Edge<Any>> {

        private val parent: Iterator<Edge<Any>> = outEdges[vertex]!!.iterator()

        override fun hasNext(): Boolean {
            return parent.hasNext()
        }

        override fun next(): Edge<Any> {
            return parent.next()
        }
    }

    /**
     * edges incidence in list view of a vertex.
     *
     *
     * removal/addition of elements are not supported yet
     */
    inner class IncidenceInView(private val vertex: Vertex<Any>) : AbstractCollection<Edge<Any>>() {

        override val size: Int
            get() = values.size

        private val values: Collection<Edge<Any>> = inEdges[vertex]!!

        override operator fun iterator(): Iterator<Edge<Any>> {
            return IncidenceOutViewIterator(vertex)
        }

        override operator fun contains(element: Edge<Any>): Boolean {
            return values.contains(element)
        }
    }

    inner class IncidenceInViewIterator(vertex: Vertex<Any>) : Iterator<Edge<Any>> {

        private val parent: Iterator<Edge<Any>> = inEdges[vertex]!!.iterator()

        override fun hasNext(): Boolean {
            return parent.hasNext()
        }

        override fun next(): Edge<Any> {
            return parent.next()
        }
    }

    override fun numEdges(): Int {
        return setEdges.size
    }

    /**
     * check whether there exists an edge (v1, v2) is in graph
     */
    override fun hasEdge(v1: Vertex<Any>, v2: Vertex<Any>): Boolean {
        return getEdge(v1, v2).isPresent
    }

    override fun hasEdge(edge: Edge<Any>): Boolean {
        return internalHasEdge(edge)
    }

    override fun addEdge(v1: Vertex<Any>, v2: Vertex<Any>): Maybe<Edge<Any>> {
        return addEdge(v1, v2, 0f)
    }

    /**
     * connect an edge (v1, v2) into the graph, v1 and v2 have to be members
     *
     * @param v1 a vertex that already belong to the graph
     * @param v2 a vertex that already belong to the graph
     * @param weight weight of the edge
     *
     * @return the edge so use can query the id
     */
    override fun addEdge(v1: Vertex<Any>, v2: Vertex<Any>, weight: Float): Maybe<Edge<Any>> {
        val edge = Edge<Any>(v1, v2, graphType, weight)
        return addEdge(edge)
    }

    override fun addEdge(edge: Edge<Any>): Maybe<Edge<Any>> {
        val v1: Vertex<Any> = edge.v1
        val v2: Vertex<Any> = edge.v2
        // validation
        validateVertex(v1)
        validateVertex(v2)
        if (hasEdge(v1, v2) && !isMultiEdgesSupported) return Maybe.empty()
        if (v1 == v2 && !isSelfLoopsSupported) return Maybe.empty()
        if (edge.edgeType !== graphType) return Maybe.empty()
        // adj list update
        colAdjLists[v1]!!.add(v2)
        if (graphType === EdgeType.UNDIRECTED) colAdjLists[v2]!!.add(v1)
        // edge list update
        setEdges.add(edge)
        // edge description
        mapPresenceEdges.getOrPut(edge.desc) { mutableListOf() }.let { edges ->
            edges.add(edge)
            outEdges[v1]?.add(edge) // TODO: what if it is not there?
            inEdges[v2]?.add(edge)
        }
        return Maybe.of(edge)
    }

    override fun removeEdge(v1: Vertex<Any>, v2: Vertex<Any>): Maybe<Edge<Any>> {
        return getEdge(v1, v2).map {
            internalRemoveEdge(it)
        }
    }

    /**
     * remove edge (v1, v2) from the graph
     *
     * @param edge an edge that already belong to the graph
     *
     * @return the edge if success, or null if failed
     */
    override fun removeEdge(edge: Edge<Any>): Maybe<Edge<Any>> {
        return if (hasEdge(edge)) {
            internalRemoveEdge(edge)
            Maybe.of(edge)
        } else Maybe.empty()
    }

    // TODO: quit validation shit, use proper abstractions instead
    override fun removeMultiEdges(u: Vertex<Any>, v: Vertex<Any>): Collection<Edge<Any>> {
        validateMultiEdgeSupport()
        return mapPresenceEdges[Edge.getEdgeDesc(u, v, graphType)]?.toList()?.apply {
            forEach { internalRemoveEdge(it) }
        } ?: listOf()
    }

    override fun getEdge(v1: Vertex<Any>, v2: Vertex<Any>): Maybe<Edge<Any>> {
        return Maybe.ofNullable(mapPresenceEdges[Edge.getEdgeDesc(v1, v2, graphType)]?.let { edges ->
            edges[0]
        })
    }

    override fun getMultiEdges(u: Vertex<Any>, v: Vertex<Any>): Collection<Edge<Any>> {
        validateMultiEdgeSupport()
        return mapPresenceEdges[Edge.getEdgeDesc(u, v, graphType)]?.toList() ?: listOf()
    }

    private fun validateMultiEdgeSupport() {

    }

    override fun numVertices(): Int {
        return vertices().size
    }

    /**
     * clear the graph into an empty graph
     */
    override fun clear() {
        setEdges.clear()
        vertices.clear()
        colAdjLists.clear()
        inEdges.clear()
        outEdges.clear()
    }

    override fun outDegreeOfVertex(vertex: Vertex<Any>): Int {
        return colAdjLists[vertex]!!.size
    }

    override fun inDegreeOfVertex(vertex: Vertex<Any>): Int {
        return inEdges[vertex]!!.size
    }

    override fun dispose() {
        for (vertex in vertices.values) {
            vertex.dispose()
        }
        vertices.clear()
        colAdjLists.clear()
    }

    /**
     * internal method to query edge inclusion via id
     */
    private fun internalHasEdge(edge: Edge<Any>): Boolean {
        return setEdges.contains(edge)
    }

    /**
     * validates if a vertex is in the graph and throws an error otherwise
     */
    private fun validateVertex(v: Vertex<Any>) {
        if (!hasVertex(v)) throw VertexNotFoundException(v, this.graph)
    }

    /**
     * internal method for removal the neighbor edges of a vertex
     *
     * @param vertex the vertex whose neighbors are to be removed
     */
    private fun removeEdgeNeighborsOfVertex(vertex: Vertex<Any>) {
        if (hasVertex(vertex)) {
            val setIn = inEdges[vertex]
            val setOut = outEdges[vertex]
            var edge: Edge<Any>
            setIn?.iterator()?.let { iterator ->
                while (iterator.hasNext()) {
                    edge = iterator.next()
                    iterator.remove()
                    internalRemoveEdge(edge)
                }
            }
            setOut?.iterator()?.let { iterator ->
                while (iterator.hasNext()) {
                    edge = iterator.next()
                    iterator.remove()
                    internalRemoveEdge(edge)
                }
            }
            colAdjLists.remove(vertex)?.clear()
            setIn?.clear()
            setOut?.clear()
            inEdges.remove(vertex)
            outEdges.remove(vertex)
        }
    }

    private fun internalRemoveEdge(edge: Edge<Any>): Edge<Any> {
        removeEdgeAdjList(edge)
        // incidence lists update
        removeEdgeIncidenceList(edge)
        // edge list update
        setEdges.remove(edge)
        removeEdgePresenceList(edge)
        return edge
    }

    private fun removeEdgeAdjList(edge: Edge<Any>) {
        colAdjLists[edge.v1]?.remove(edge.v2)
        if (graphType === EdgeType.UNDIRECTED) {
            colAdjLists[edge.v2]!!.remove(edge.v1)
        }
    }

    private fun removeEdgeIncidenceList(edge: Edge<Any>) {
        outEdges[edge.v1]?.remove(edge)
        inEdges[edge.v2]?.remove(edge)
    }

    private fun removeEdgePresenceList(edge: Edge<Any>) {
        mapPresenceEdges[edge.desc]?.remove(edge)
    }
}