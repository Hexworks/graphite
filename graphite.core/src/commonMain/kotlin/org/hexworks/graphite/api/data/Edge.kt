package org.hexworks.graphite.api.data

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.graphite.api.behavior.DataHolder
import org.hexworks.graphite.api.behavior.Identifiable
import org.hexworks.graphite.api.behavior.Taggable

open class Edge<T : Any>(
        val v1: Vertex<Any>,
        val v2: Vertex<Any>,
        val edgeType: EdgeType,
        val weight: Float = 0f
) : Identifiable, Taggable, DataHolder<T> {

    override var id: Maybe<String> = Maybe.empty()
    override var tag: Maybe<String> = Maybe.empty()
    var desc: String = getEdgeDesc(this, edgeType)
    override var data: Maybe<T> = Maybe.empty()

    override fun toString(): String {
        if (tag.isPresent) return tag.get()
        var res: String? = null
        val v1_r: String = v1.toString()
        val v2_r: String = v2.toString()
        if (edgeType == EdgeType.DIRECTED) {
            res = "$v1_r->$v2_r"
        } else if (edgeType == EdgeType.UNDIRECTED) {
            val compare = v1_r.compareTo(v2_r)
            res = if (compare < 0) "$v1_r<->$v2_r" else "$v2_r<->$v1_r"
        }
        return res!!
    }

    companion object {

        fun getEdgeDesc(
                v1: Vertex<Any>,
                v2: Vertex<Any>,
                edgeType: EdgeType
        ): String {
            return when (edgeType) {
                EdgeType.DIRECTED -> v1.id.get() + "->" + v2.id.get()
                EdgeType.UNDIRECTED -> v1.id.get() + "<->" + v2.id.get()
            }
        }

        fun getEdgeDesc(edge: Edge<Any>): String {
            return getEdgeDesc(edge.v1, edge.v2, edge.edgeType)
        }

        fun <T : Any> getEdgeDesc(edge: Edge<T>, edgeType: EdgeType): String {
            return getEdgeDesc(edge.v1, edge.v2, edgeType)
        }

    }

}
