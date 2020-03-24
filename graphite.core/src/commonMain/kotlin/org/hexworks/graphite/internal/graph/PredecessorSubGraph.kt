package org.hexworks.graphite.internal.graph

import org.hexworks.graphite.api.data.Vertex

/**
 * the graph induced by a predecessor list mapping
 *
 * @author Tomer Shalev
 */
class PredecessorSubGraph() : SimpleDirectedGraph() {

    /**
     * the predecessor of u is stored in PIE. If u has no predecessor (for example, if u = s or u has not been discovered), then π[u] = NIL
     */
    private lateinit var PIE: MutableMap<Vertex<Any>, Vertex<Any>>

    /**
     * start vertex, in case of a tree (Optional)
     */
    private lateinit var startVertex: Vertex<Any>

    /**
     * @param PIE a mapping of vertex to it's parent
     * @param startVertex start vertex, in case of a tree (Optional)
     */
    constructor(PIE: Map<Vertex<Any>, Vertex<Any>>, startVertex: Vertex<Any>) : this() {
        commitPredecessorList(PIE.toMutableMap(), startVertex)
    }

    /**
     * setup the graph according to the predecessor list
     *
     * @param PIE a mapping of vertex to it's parent
     * @param startVertex start vertex, in case of a tree (Optional)
     */
    fun commitPredecessorList(PIE: Map<Vertex<Any>, Vertex<Any>>, startVertex: Vertex<Any>) {
        this.PIE = PIE.toMutableMap()
        this.startVertex = startVertex
        val pieKeys: Set<Vertex<Any>> = this.PIE.keys
        for (key in pieKeys) {
            this.PIE[key]?.let { parent ->
                addVertex(parent)
                addVertex(key)
                addEdge(parent, key)
            }
        }
        addVertex(this.startVertex)
    }
}