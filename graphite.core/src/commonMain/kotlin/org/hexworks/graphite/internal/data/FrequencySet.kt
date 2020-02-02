package org.hexworks.graphite.internal.data

class FrequencySet<E : Any> internal constructor(

        private val backingMap: HashMap<E, Int> = hashMapOf()
) : AbstractSet<E>(), MutableSet<E> {

    override val size: Int
        get() = backingMap.size

    override fun add(element: E): Boolean {
        return backingMap.put(element, frequencyOf(element) + 1) == null
    }

    fun frequencyOf(e: E) = backingMap[e] ?: 0

    override fun clear() {
        backingMap.clear()
    }

    override operator fun contains(element: E) = backingMap.containsKey(element)

    override fun isEmpty() = backingMap.isEmpty()

    override fun iterator(): MutableIterator<E> = FrequencyIterator()

    private inner class FrequencyIterator : MutableIterator<E> {

        private val backingIterator = backingMap.keys.iterator()
        private lateinit var current: E

        override fun hasNext() = backingIterator.hasNext()

        override fun next(): E {
            current = backingIterator.next()
            return current
        }

        override fun remove() {
            val newFrequency = decreaseFrequencyOf(current)
            if (newFrequency == 0) {
                backingIterator.remove()
            }
        }
    }

    fun increaseFrequencyOf(e: E): Int {
        val newValue = frequencyOf(e) + 1
        backingMap[e] = newValue
        return newValue
    }


    private fun decreaseFrequencyOf(e: E): Int {
        val newValue = frequencyOf(e) - 1
        backingMap[e] = newValue
        return newValue
    }

    override fun remove(element: E): Boolean {
        val frequency = decreaseFrequencyOf(element)
        return if (frequency >= 1) {
            true
        } else backingMap.remove(element) != null
    }

    override fun addAll(elements: Collection<E>): Boolean {
        return elements.map(::add).fold(false, Boolean::or)
    }

    override fun removeAll(elements: Collection<E>): Boolean {
        return elements.map(::remove).fold(false, Boolean::or)
    }

    override fun retainAll(elements: Collection<E>): Boolean {
        return elements.map {
            if (contains(it).not()) remove(it) else false
        }.fold(false, Boolean::or)
    }
}