package org.hexworks.graphite.api.data.base

import org.hexworks.cobalt.core.api.Identifier
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.graphite.api.data.Vertex

class DefaultVertex<T : Any>(
        override var id: Identifier = Identifier.randomIdentifier(),
        override var tag: Maybe<String> = Maybe.empty(),
        override var data: Maybe<T> = Maybe.empty(),
        override var color: Int = Vertex.NO_COLOR,
        override var index: Int = Vertex.NO_INDEX,
        override var weight: Float = Vertex.NO_WEIGHT
) : Vertex<T> {

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as DefaultVertex<*>

        if (id != other.id) return false

        return true
    }

    override fun toString() = DefaultVertex.toString(this)

    override fun dispose() {
        data = Maybe.empty()
    }

    companion object {
        fun toString(vertex: Vertex<out Any>) = vertex.tag.orElse(vertex.id.toString())
    }
}