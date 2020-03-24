package org.hexworks.graphite.api.data

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.graphite.api.behavior.DataHolder
import org.hexworks.graphite.api.behavior.Disposable
import org.hexworks.graphite.api.behavior.Identifiable
import org.hexworks.graphite.api.behavior.Taggable
import org.hexworks.graphite.api.data.base.DefaultVertex

/**
 * Interface for Graph Vertex
 * @param <T> the data type to store
 */
interface Vertex<T> : Identifiable, DataHolder<T>, Disposable, Taggable, ImmutableHashCode {

    var color: Int
    var index: Int
    var weight: Float

    companion object {

        const val NO_COLOR = 0
        const val NO_INDEX = -1
        const val NO_WEIGHT = 0.0f

        fun <T : Any> create(data: T): Vertex<T> = DefaultVertex(
                data = Maybe.of(data))

        fun create(): Vertex<Any> = DefaultVertex()

    }
}