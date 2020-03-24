package org.hexworks.graphite.internal.data

import org.hexworks.graphite.api.data.ImmutableHashCode
import org.hexworks.graphite.api.data.UnionFind

/**
 * a naive implementation of UnionFind
 * @param <T> the class type of the members of the sets, has to have an immutable hashCode
 * while the operations take place.
 */
class NaiveUnionFind<T : ImmutableHashCode> : UnionFind<T> {

    private var sets: MutableList<Set<T>> = mutableListOf()
    private var mapMembers: MutableMap<T, MutableSet<T>> = mutableMapOf()

    override val disjointSets: List<Set<T>>
        get() {
            val arrResult: MutableList<Set<T>> = mutableListOf()
            for (set in sets) {
                if (set.isNotEmpty()) {
                    arrResult.add(set)
                }
            }
            return arrResult
        }

    override fun union(member1: T, member2: T) {
        val setMember1: MutableSet<T> = validateGetMemberSet(member1)
        val setMember2: MutableSet<T> = validateGetMemberSet(member2)
        for (t in setMember2) {
            mapMembers[t] = setMember1
        }
        setMember1.addAll(setMember2)
        setMember2.clear()
    }

    override fun makeSet(member: T) {
        if (mapMembers.containsKey(member).not()) {
            val setNew: MutableSet<T> = mutableSetOf()
            setNew.add(member)
            mapMembers.put(member, setNew)
            sets.add(setNew)
        }
    }

    override fun findSet(member: T): Set<T> {
        return validateGetMemberSet(member)
    }

    protected fun validateGetMemberSet(member: T): MutableSet<T> {
        return mapMembers[member] ?: error("UnionFind error member does not belong to any set")
    }
}