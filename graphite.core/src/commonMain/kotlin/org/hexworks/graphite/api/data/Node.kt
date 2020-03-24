package org.hexworks.graphite.api.data

interface Node<T> {

    var parent: Node<T>?

    var data: T
}