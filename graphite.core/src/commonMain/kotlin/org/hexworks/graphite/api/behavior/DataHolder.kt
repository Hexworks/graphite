package org.hexworks.graphite.api.behavior

import org.hexworks.cobalt.datatypes.Maybe

interface DataHolder<T> {

    var data: Maybe<T>

}