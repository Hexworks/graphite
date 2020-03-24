package org.hexworks.graphite.api.algorithm

import org.hexworks.graphite.api.behavior.Disposable
import org.hexworks.graphite.api.behavior.Taggable
import org.hexworks.graphite.api.graph.Graph

interface GraphAlgorithm<T, E : Graph> : Disposable, Taggable {

    var inputGraph: E
    val result: T

    fun applyAlgorithm(): T
}