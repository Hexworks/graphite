package org.hexworks.graphite.internal.data

import org.hexworks.graphite.api.data.Edge

class EdgeWeightComparator : Comparator<Edge<Any>> {
    override fun compare(a: Edge<Any>, b: Edge<Any>): Int {
        return a.weight.minus(b.weight).toInt()
    }
}