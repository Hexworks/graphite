package org.hexworks.graphite.api.behavior

import org.hexworks.cobalt.datatypes.Maybe

interface Taggable {

    var tag: Maybe<String>
}