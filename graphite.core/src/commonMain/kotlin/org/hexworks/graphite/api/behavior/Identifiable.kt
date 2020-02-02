package org.hexworks.graphite.api.behavior

import org.hexworks.cobalt.datatypes.Maybe

interface Identifiable {

    var id: Maybe<String>

    fun hasId(): Boolean = id.isPresent
}