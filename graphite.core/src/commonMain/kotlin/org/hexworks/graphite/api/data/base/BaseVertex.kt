package org.hexworks.graphite.api.data.base

import org.hexworks.cobalt.core.api.Identifier
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.graphite.api.data.Vertex

abstract class BaseVertex<T: Any>(
        override var id: Maybe<String> = Maybe.of(Identifier.randomIdentifier().toString()),
        override var tag: Maybe<String> = Maybe.empty(),
        override var data: Maybe<T> = Maybe.empty(),
        override var color: Int = Vertex.NO_COLOR,
        override var index: Int = Vertex.NO_INDEX,
        override var weight: Float = Vertex.NO_WEIGHT
) : Vertex<T> {

    override fun hashCode(): Int {
        return "graph_vertex".hashCode() + id.hashCode()
    }

    override fun dispose() {
        data = Maybe.empty()
    }

    override fun toString() = BaseVertex.toString(this)

    companion object {
        fun toString(vertex: Vertex<out Any>) = vertex.tag.orElse(vertex.id.orElse(""))
    }
}