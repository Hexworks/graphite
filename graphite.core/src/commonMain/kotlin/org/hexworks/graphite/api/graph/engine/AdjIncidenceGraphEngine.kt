package org.hexworks.graphite.api.graph.engine

import org.hexworks.graphite.api.data.Edge
import org.hexworks.graphite.api.data.EdgeType
import org.hexworks.graphite.api.data.Vertex
import org.hexworks.graphite.api.graph.Graph
import kotlin.jvm.Transient
import kotlin.jvm.Volatile

/**
 * a graph engine implementation that uses both **Adjacency list** and **Incidence list**<br></br>
 * designed for optimal complexity for algorithms that require more than a moderate edge<br></br>
 * queries. it does consume more memory as a consequence. can handle both directed and undirected edges.
 * todo: add multi edge capabilities and self loops
 */
class AdjIncidenceGraphEngine(initialGraph: Graph) : AbstractGraphEngine(initialGraph) {

    private var _vertices = linkedMapOf<String, Vertex<Any>>()
    private var _colAdjLists = mutableMapOf<Vertex<Any>, MutableSet<Vertex<Any>>>()
    private var _setEdges = linkedSetOf<Edge<Any>>()
    private var _mapPresenceEdges = mutableMapOf<String, MutableList<Edge<Any>>>()
    private var _inEdges = mutableMapOf<Vertex<Any>, MutableSet<Edge<Any>>>()
    private var _outEdges = mutableMapOf<Vertex<Any>, MutableSet<Edge<Any>>>()

    /**
     * set the graph that this engine works for.
     *
     * @param graph the graph
     */
    override var graph: Graph
        get() = super.graph
        set(graph) {
            super.graph = graph
            if (graphType === EdgeType.UNDIRECTED) _inEdges = _outEdges
        }

    /**
     * iterator over the vertices of the graph
     * example: <br></br>
     * `
     * <pre></pre>
     * for (IVertex<Any> vertex : graph) {
     * }
    ` *
     *
     * @return the vertices iterator
     */
    override fun iterator(): Iterator<Vertex<Any>> {
        return VerticesIterator()
    }

    /**
     * {@inheritDoc}
     *
     *
     * changes that are made to this collection will reflect
     * changes to the edges of the graph (removal wise)
     */
    override fun vertices(): Collection<Vertex<Any>> {
        return if (verticesView == null) VerticesView().also { verticesView = it } else verticesView!!
    }

    /**
     * {@inheritDoc}
     *
     *
     * changes that are made to this collection will reflect
     * changes to the vertices of the graph
     */
    override fun edges(): Collection<Edge<Any>> {
        return if (edgesView == null) Edge<Any> sView ().also { edgesView = it } else edgesView!!
    }

    /**
     * get the accessible vertex neighbors of vertex v1
     *
     * @param vertex the vertex
     * @return the neighbors
     */
    fun getNeighborsOf(vertex: Vertex<Any>?): Collection<Vertex<Any>> {
        return Vertex<Any> AdjView (vertex)
    }

    /**
     * get the list of incident edges that go out from vertex
     *
     * @param vertex the vertex
     * @return the out incidence list of vertex
     */
    fun getIncidenceOutListOf(vertex: Vertex<Any>?): Collection<Edge<Any>> {
        return IncidenceOutView(vertex)
    }

    /**
     * get the list of incident edges that go into the vertex
     *
     * @param vertex the vertex
     * @return the in incidence list of vertex
     */
    fun getIncidenceInListOf(vertex: Vertex<Any>?): Collection<Edge<Any>> {
        return IncidenceInView(vertex)
    }

    /**
     * check whether `vertex` belongs to the graph's vertex list
     *
     * @param vertex the vertex in question
     * @return true if graph contains this vertex
     */
    fun hasVertex<Any>(vertex: Vertex<Any>?): Boolean {
        return _vertices.containsValue(vertex)
    }

    /**
     * add `vertex` into the graph
     *
     * @return true if success
     */
    fun addVertex<Any>(vertex: Vertex<Any>): Boolean {
        if (hasVertex<Any>(vertex)) return false
        _vertices.put(vertex.getId(), vertex)
        if (_colAdjLists.get(vertex) == null) _colAdjLists.put(vertex, java.util.HashSet())
        _inEdge<Any> s . put (vertex, java.util.HashSet())
        _outEdge<Any> s . put (vertex, java.util.HashSet())
        return true
    }

    /**
     * remove `vertex` from the graph
     *
     * @return true if success
     */
    fun removeVertex<Any>(vertex: Vertex<Any>?): Boolean {
        internal_removeEdge<Any> NeighborsOfVertex < Any >(vertex)
        return _vertices.values.remove(vertex)
    }

    /**
     * responsive/modifiable collection views for iteration outside
     * the class.
     */
    @Volatile
    @Transient
    var verticesView: Collection<Vertex<Any>>? = null
    @Volatile
    @Transient
    var edgesView: Collection<Edge<Any>>? = null

    /**
     * vertices view
     */
    inner class VerticesView : AbstractMutableCollection<Vertex<Any>>() {

        private val _values = _vertices.values

        override operator fun iterator(): MutableIterator<Vertex<Any>> {
            return VerticesIterator()
        }

        override val size: Int
            get() = _values.size

        override fun contains(element: Vertex<Any>): Boolean {
            return _values.contains(element)
        }

        override fun add(vertex: Vertex<Any>): Boolean {
            return addVertex<Any>(vertex)
        }

        override fun remove(element: Vertex<Any>): Boolean {
            return removeVertex<Any>(element as Vertex<Any>)
        }
    }

    protected inner class VerticesIterator : MutableIterator<Vertex<Any>?> {
        private val _iteratorParent: MutableIterator<Vertex<Any>>
        private var _current: Vertex<Any>?
        override fun hasNext(): Boolean {
            return _iteratorParent.hasNext()
        }

        override fun next(): Vertex<Any> {
            return _iteratorParent.next().also({ _current = it })
        }

        override fun remove() {
            internal_removeEdgeNeighborsOfVertex(_current)
            _iteratorParent.remove()
        }

        init {
            _iteratorParent = _vertices.values.iterator()
            _current = null
        }
    }
    //
    /**
     * edges view
     */
    inner class EdgesView : AbstractMutableCollection<Edge<Any>>() {

        private val _values: Collection<Edge<Any>>
        override operator fun iterator(): MutableIterator<Edge<Any>> {
            return EdgesIterator()
        }

        override fun size(): Int {
            return _values!!.size
        }

        override operator fun contains(o: Any): Boolean {
            return _values!!.contains(o)
        }

        override fun add(edge: Edge<Any>): Boolean {
            return addEdge<Any>(edge) != null
        }

        override fun remove(o: Any): Boolean {
            return removeEdge<Any>(o as Edge<Any>) != null
        }

        init {
            _values = _setEdges
        }
    }

    protected inner class EdgesIterator : MutableIterator<Edge<Any>?> {
        private val _iterParent: MutableIterator<Edge<Any>>
        private var _current: Edge<Any>?
        override fun hasNext(): Boolean {
            return _iterParent.hasNext()
        }

        override fun next(): Edge<Any> {
            return _iterParent.next().also({ _current = it })
        }

        override fun remove() { // adj list update
            internal_removeEdge_adjList(_current)
            // incidence lists update
            internal_removeEdge_incidenceList(_current)
            internal_removeEdge_presenceList(_current)
            // remove from main edge list
            _iterParent.remove()
        }

        init {
            _iterParent = _setEdges.iterator()
            _current = null
        }
    }

    /**
     * vertex adjacency list view.
     *
     * removal/addition of elements are not supported
     */
    inner class VertexAdjView(vertex: Vertex<Any>) : AbstractCollection<Vertex<Any>>() {
        private val _values: Collection<Vertex<Any>>
        private var _vertex: Vertex<Any>? = null
        override operator fun iterator(): MutableIterator<Vertex<Any>> {
            return VertexAdjIterator(_vertex)
        }

        override fun size(): Int {
            return _values.size
        }

        override operator fun contains(o: Any): Boolean {
            return _values.contains(o)
        }

        override fun add(vertex: Vertex<Any>): Boolean {
            throw UnsupportedOperationException("adding a vertex directly to the adj list is not supported!! use graph methods instead")
        }

        override fun remove(o: Any): Boolean {
            throw UnsupportedOperationException("removing a vertex directly to the adj list is not supported!! use graph methods instead")
        }

        init {
            _values = _colAdjLists.get(vertex.also({ _vertex = it }))
        }
    }

    protected inner class VertexAdjIterator(vertex: Vertex<Any>) : MutableIterator<Vertex<Any>> {
        private val _iterParent: Iterator<Vertex<Any>>
        override fun hasNext(): Boolean {
            return _iterParent.hasNext()
        }

        override fun next(): Vertex<Any> {
            return _iterParent.next()
        }

        override fun remove() {
            throw UnsupportedOperationException("removing a vertex directly to the adj list is not supported!! use graph methods instead")
        }

        init {
            _iterParent = _colAdjLists.get(vertex).iterator()
        }
    }

    /**
     * edges incidence out list view of a vertex.
     *
     *
     * removal/addition of elements are not supported yet
     */
    inner class IncidenceOutView(vertex: Vertex<Any>) : AbstractMutableCollection<Edge<Any>>() {
        private val _values: Collection<Edge<Any>>
        private var _vertex: Vertex<Any>? = null
        override operator fun iterator(): MutableIterator<Edge<Any>> {
            return IncidenceOutViewIterator(_vertex)
        }

        override val size: Int
            get() = _values.size

        override fun contains(element: Edge<Any>): Boolean {
            return _values.contains(element)
        }

        override fun add(vertex: Edge<Any>): Boolean {
            throw UnsupportedOperationException("adding a vertex directly to the adj list is not supported!! use graph methods instead")
        }

        override fun remove(o: Any): Boolean {
            throw UnsupportedOperationException("removing a vertex directly to the adj list is not supported!! use graph methods instead")
        }

        init {
            _values = _outEdges[vertex.also({ _vertex = it })]!!
        }
    }

    protected inner class IncidenceOutViewIterator(vertex: Vertex<Any>?) : MutableIterator<Edge<Any>?> {
        private val _iterParent: Iterator<Edge<Any>>
        override fun hasNext(): Boolean {
            return _iterParent.hasNext()
        }

        override fun next(): Edge<Any> {
            return _iterParent.next()
        }

        override fun remove() {
            throw UnsupportedOperationException("removing a vertex directly to the adj list is not supported!! use graph methods instead")
        }

        init {
            _iterParent = _outEdges.get(vertex).iterator()
        }
    }

    /**
     * edges incidence in list view of a vertex.
     *
     *
     * removal/addition of elements are not supported yet
     */
    inner class IncidenceInView(vertex: Vertex<Any>) : AbstractMutableCollection<Edge<Any>>() {

        private val _values: Collection<Edge<Any>>
        private var _vertex: Vertex<Any>? = null

        override operator fun iterator(): MutableIterator<Edge<Any>> {
            return IncidenceOutViewIterator(_vertex)
        }

        override val size: Int
            get() = _values.size

        override fun contains(element: Edge<Any>): Boolean {
            return _values.contains(element)
        }

        override fun add(edge: Edge<Any>): Boolean {
            throw UnsupportedOperationException("adding a vertex directly to the adj list is not supported!! use graph methods instead")
        }

        override fun remove(o: Any): Boolean {
            throw UnsupportedOperationException("removing a vertex directly to the adj list is not supported!! use graph methods instead")
        }

        init {
            _values = _inEdges[vertex.also({ _vertex = it })]!!
        }
    }

    protected inner class IncidenceInViewIterator(vertex: Vertex<Any>?) : MutableIterator<Edge<Any>?> {
        private val _iteratorParent: Iterator<Edge<Any>>
        override fun hasNext(): Boolean {
            return _iteratorParent.hasNext()
        }

        override fun next(): Edge<Any> {
            return _iteratorParent.next()
        }

        override fun remove() {
            throw UnsupportedOperationException("removing a vertex directly to the adj list is not supported!! use graph methods instead")
        }

        init {
            _iteratorParent = _inEdges[vertex]!!.iterator()
        }
    }

    /**
     * @return the number of edges in the graph
     */
    override fun numEdges(): Int {
        return _setEdges.size
    }

    /**
     * check whether there exists an edge (v1, v2) is in graph
     *
     * @param v1 vertex v1
     * @param v2 vertex v2
     * @return true if graph contains this edge
     */
    fun hasEdge(v1: Vertex<Any>?, v2: Vertex<Any>?): Boolean {
        return getEdge(v1, v2) != null
    }

    /**
     * check whether edge instance is in graph
     *
     * @param edge the edge in question
     * @return true if graph contains this edge
     */
    override fun hasEdge(edge: Edge<Any>): Boolean {
        return internal_hasEdge(edge)
    }

    /**
     * connect an edge (v1, v2) into the graph, v1 and v2 have to be members
     */
    override fun addEdge(v1: Vertex<Any>, v2: Vertex<Any>): Edge<Any> {
        return addEdge(v1, v2, 0f)
    }

    /**
     * connect an edge (v1, v2) into the graph, v1 and v2 have to be members
     */
    override fun addEdge(v1: Vertex<Any>, v2: Vertex<Any>, weight: Float): Edge<Any> {
        val edge = Edge<Any>(v1, v2, graphType, weight)
        return addEdge(edge)
    }

    /**
     * connect an edge (v1, v2) into the graph, v1 and v2 have to be members
     *
     * @return the edge so use can query the id, or `null` if the edge is incompatible
     * with the graph type
     */
    override fun addEdge(edge: Edge<Any>): Edge<Any> {
        val v1: Vertex<Any> = edge.v1
        val v2: Vertex<Any> = edge.v2
        // validation
        validateVertex<Any>(v1)
        validateVertex<Any>(v2)
        if (hasEdge(v1, v2) && !isMultiEdgesSupported) error("incompatible edge type")
        if (v1.equals(v2) && !isSelfLoopsSupported) error("incompatible edge type")
        if (edge.edgeType !== graphType) error("incompatible edge type")
        // adj list update
        _colAdjLists[v1]!!.add(v2)
        if (graphType === EdgeType.UNDIRECTED) _colAdjLists[v2]!!.add(v1)
        // edge list update
        _setEdges.add(edge)
        // edge description
        var edges = _mapPresenceEdges.getOrPut(edge.desc, mutableListOf<Edge<Any>>())
        edges.add(edge)
        // incidence lists update
        _outEdge<Any> s . get (v1).add(edge)
        _inEdge<Any> s . get (v2).add(edge)
        return edge
    }

    override fun removeEdge(v1: Vertex<Any>, v2: Vertex<Any>): Edge<Any> {
        val edge: Edge<Any> = getEdge(v1, v2)
        return if (edge != null) internal_removeEdge(edge) else error("?")
    }

    /**
     * remove edge (v1, v2) from the graph
     *
     * @param edge an edge that already belong to the graph
     *
     * @return the edge if success, or null if failed
     */
    fun removeEdge<Any>(edge: Edge<Any>): Edge<Any> {
        return internal_removeEdge<Any>(edge)
    }

    /**
     * remove all the edges that connect the two vertices
     *
     * @param u first vertex
     * @param v second vertex
     *
     * @return a collection of edges that connect the two vertices that were removed if any
     *
     * @throws java.lang.UnsupportedOperationException if the engine does not support multi edges
     */
    fun removeMultiEdge<Any>s(u: Vertex<Any>?, v: Vertex<Any>?): Collection<Edge<Any>>
    {
        validateMultiEdge<Any> Support ()
        var multiEdge<Any>s: Collection<Edge<Any>> = _mapPresenceEdge<Any>s.get(Edge<Any>.getEdge<Any>Desc(u, v, getGraphType()))
        multiEdge<Any> s = java . util . ArrayList (multiEdge<Any> s)
        // we can remove
        for (edge in multiEdge<Any> s) {
            internal_removeEdge<Any>(edge)
        }
        return multiEdge<Any> s
    }

    /**
     * get the edge that connects (v1, v2) from the graph
     *
     * @param v1 a vertex that already belong to the graph
     * @param v2 a vertex that already belong to the graph
     *
     * @return the edge if success, or null if failed
     */
    fun getEdge<Any>(v1: Vertex<Any>?, v2: Vertex<Any>?): Edge<Any>? {
        val edges: java.util.ArrayList<Edge<Any>> = _mapPresenceEdge<Any> s . get (Edge<Any>.getEdge<Any> Desc (v1, v2, getGraphType()))
        return if (edges != null && edges.size > 0) edges.get(0) else null
    }

    /**
     * get all the edges that connect the two vertices
     *
     * @param u first vertex
     * @param v second vertex
     *
     * @return a collection of edges that connect the two vertices if any
     *
     * @throws java.lang.UnsupportedOperationException if the engine does not support multi edges
     */
    fun getMultiEdge<Any>s(u: Vertex<Any>?, v: Vertex<Any>?): Collection<Edge<Any>>
    {
        validateMultiEdge<Any> Support ()
        return java.util.Collections.unmodifiableList(_mapPresenceEdge<Any> s . get (Edge<Any>.getEdge<Any> Desc (u, v, getGraphType())))
    }

    private fun validateMultiEdge<Any>Support()
    {}
    /**
     * @return the number of vertices in the Graph
     */
    override fun numVertices(): Int {
        return vertices().size
    }

    /**
     * clear the graph into an empty graph
     */
    override fun clear() {
        _setEdge<Any> s . clear ()
        _vertices.clear()
        _colAdjLists.clear()
        _inEdge<Any> s . clear ()
        _outEdge<Any> s . clear ()
    }

    /**
     * @param vertex the vertex in question
     * @return the out degree of  the vertex
     */
    fun outDegreeOfVertex<Any>(vertex: Vertex<Any>?): Int {
        return _colAdjLists.get(vertex).size
    }

    /**
     * @param vertex the vertex in question
     * @return the in degree of  the vertex
     */
    fun inDegreeOfVertex<Any>(vertex: Vertex<Any>?): Int {
        return _inEdge<Any> s . get (vertex).size
    }

    /**
     *
     * {@inheritDoc}
     */
    override fun dispose() {
        for (vertex in _vertices.values) {
            vertex.dispose()
        }
        _vertices.clear()
        _colAdjLists.clear()
        _vertices = null
    }

    /**
     * internal method to query edge inclusion via id
     *
     * @param edge the edge
     * @return true if graph contains this edge
     */
    private fun internal_hasEdge<Any>(edge: Edge<Any>): Boolean {
        return _setEdge<Any> s . contains (edge)
    }

    /**
     * validates if a vertex is in the graph and throws an error otherwise
     *
     * @param v the vertex
     * @throws Vertex<Any>NotFoundException
     */
    private fun validateVertex<Any>(v: Vertex<Any>) {
        if (!hasVertex<Any>(v)) throw Vertex<Any> NotFoundException (v, this.getGraph())
    }

    /**
     * internal method for removal the neighbor edges of a vertex
     *
     * @param vertex the vertex whose neighbors are to be removed
     */
    private fun internal_removeEdge<Any>NeighborsOfVertex<Any>(vertex: Vertex<Any>?)
    {
        if (vertex == null || !hasVertex<Any>(vertex)) return
        val setIn: java.util.HashSet<Edge<Any>> = _inEdge<Any> s . get (vertex)
        val setOut: java.util.HashSet<Edge<Any>> = _outEdge<Any> s . get (vertex)
        var edge: Edge<Any>
        run {
            val iterator: MutableIterator<Edge<Any>> = setIn.iterator()
            while (iterator.hasNext()) {
                edge = iterator.next()
                iterator.remove()
                internal_removeEdge<Any>(edge)
            }
        }
        val iterator: MutableIterator<Edge<Any>> = setOut.iterator()
        while (iterator.hasNext()) {
            edge = iterator.next()
            iterator.remove()
            internal_removeEdge<Any>(edge)
        }
        _colAdjLists.remove(vertex).clear()
        setIn.clear()
        setOut.clear()
        _inEdge<Any> s . remove (vertex)
        _outEdge<Any> s . remove (vertex)
    }

    /**
     * internal removal of edge by it's id
     *
     * @param edge the edge in question
     * @return the removed edge, or null if not found
     */
    private fun internal_removeEdge<Any>(edge: Edge<Any>): Edge<Any> { // adj list update
        internal_removeEdge<Any> _adjList (edge)
        // incidence lists update
        internal_removeEdge<Any> _incidenceList (edge)
        // edge list update
        _setEdge<Any> s . remove (edge)
        internal_removeEdge<Any> _presenceList (edge)
        return edge
    }

    /**
     * internal method for removing edge from the adjacency lists it is connected to
     *
     * @param edge the edge
     * @return the removed edge, or null if none
     */
    private fun internal_removeEdge_adjList(edge: Edge<Any>?): Edge<Any>?
    { // validation
        if (!hasEdge<Any>(edge)) return null
        val v1: Vertex<Any> = edge.getV1()
        val v2: Vertex<Any> = edge.getV2()
        if (!hasVertex<Any>(v1) || !hasVertex<Any>(v2)) return null
        // adj list update
        _colAdjLists.get(v1).remove(v2)
        if (getGraphType() === Edge<Any> Type . UNDIRECTED) _colAdjLists.get(v2).remove(v1)
        return edge
    }

    /**
     * internal method for removing edge from the incidence lists it is connected to
     */
    private fun internal_removeEdge_incidenceList(edge: Edge<Any>): Edge<Any> {
        if (!hasEdge(edge)) error("edge not found")
        val v1: Vertex<Any> = edge.v1
        val v2: Vertex<Any> = edge.v2
        if (!hasVertex<Any>(v1) || !hasVertex<Any>(v2)) error("edge not found")
        // incidence lists update
        val setOut = _outEdges[v1]!!
        val setIn = _inEdges[v2]!!
        setOut.remove(edge)
        setIn.remove(edge)
        return edge
    }

    private fun internal_removeEdge_presenceList(edge: Edge<Any>): Edge<Any> {
        val edges = _mapPresenceEdges[edge.desc]!!
        edges.remove(edge)
        return edge
    }

}