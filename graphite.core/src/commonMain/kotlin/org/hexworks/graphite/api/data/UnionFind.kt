package org.hexworks.graphite.api.data

/**
 * Union Find Data Structure
 * @param <T> the class type of the members of the sets
 */
interface UnionFind<T> {

    /**
     * unites the dynamic sets that contain member1 and member2
     */
    fun union(member1: T, member2: T)

    /**
     * creates a new set whose only member (and thus representative) is member
     */
    fun makeSet(member: T)

    /**
     * returns a reference to the set containing member
     * @return the set containing member
     */
    fun findSet(member: T): Set<T>

    /**
     * @return the collection of disjoint sets
     */
    val disjointSets: List<Set<T>>
}