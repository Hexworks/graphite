package org.hexworks.graphite.api.data

import org.hexworks.graphite.api.behavior.DataHolder
import org.hexworks.graphite.api.behavior.Disposable
import org.hexworks.graphite.api.behavior.Identifiable
import org.hexworks.graphite.api.behavior.Taggable

/**
 * Interface for Graph Vertex
 * @param <T> the data type to store
 */
interface Vertex<T> : Identifiable, DataHolder<T>, Disposable, Taggable, ImmutableHashCode {

    var color: Int
    var index: Int
    var weight: Float

    companion object {
        val NO_COLOR = 0
        val NO_INDEX = -1
        val NO_WEIGHT = 0.0f
    }
}