package org.hexworks.graphite.api.data.base

import org.hexworks.graphite.api.data.Node

abstract class BaseNode<T>(
        override var data: T,
        override var parent: Node<T>? = null
) : Node<T>