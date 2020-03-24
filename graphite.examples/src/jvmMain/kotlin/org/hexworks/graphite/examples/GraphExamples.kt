package org.hexworks.graphite.examples

import org.hexworks.graphite.api.data.Vertex
import org.hexworks.graphite.api.data.base.DefaultVertex
import org.hexworks.graphite.api.exception.AlgorithmException
import org.hexworks.graphite.internal.graph.SimpleDirectedGraph
import java.util.*

class GraphExamples {

    private lateinit var _v0: Vertex<Any>

    private fun transitiveClosure() {
        val graph = SimpleDirectedGraph()
        val v1 = Vertex.create()
        v1.tag = "1"
        val v2 = Vertex.create()
        v2.setTag("2")
        val v3 = Vertex.create()
        v3.setTag("3")
        val v4 = Vertex.create()
        v4.setTag("4")
        graph.addVertex(v1)
        graph.addVertex(v2)
        graph.addVertex(v3)
        graph.addVertex(v4)
        val e2_3: Edge = DirectedEdge(v2, v3, 3)
        val e2_4: Edge = DirectedEdge(v2, v4, 8)
        val e3_2: Edge = DirectedEdge(v3, v2, 8)
        val e4_1: Edge = DirectedEdge(v4, v1, 8)
        val e4_3: Edge = DirectedEdge(v4, v3, 8)
        graph.addEdge(e2_3)
        graph.addEdge(e2_4)
        graph.addEdge(e3_2)
        graph.addEdge(e4_1)
        graph.addEdge(e4_3)
        val graph_res: DirectedGraph = TransitiveClosure(graph).applyAlgorithm()
        graph_res.print()
        println("")
    }

    private fun floyd_Warshall_and_johnson() {
        val graph = SimpleDirectedGraph()
        val v1 = Vertex.create()
        v1.setTag("1")
        val v2 = Vertex.create()
        v2.setTag("2")
        val v3 = Vertex.create()
        v3.setTag("3")
        val v4 = Vertex.create()
        v4.setTag("4")
        val v5 = Vertex.create()
        v5.setTag("5")
        graph.addVertex(v1)
        graph.addVertex(v2)
        graph.addVertex(v3)
        graph.addVertex(v4)
        graph.addVertex(v5)
        val e1_2: Edge = DirectedEdge(v1, v2, 3)
        val e1_3: Edge = DirectedEdge(v1, v3, 8)
        val e1_5: Edge = DirectedEdge(v1, v5, 4)
        val e2_4: Edge = DirectedEdge(v2, v4, 1)
        val e2_5: Edge = DirectedEdge(v2, v5, 7)
        val e3_2: Edge = DirectedEdge(v3, v2, 4)
        val e4_1: Edge = DirectedEdge(v4, v1, 2)
        val e4_3: Edge = DirectedEdge(v4, v3, 5)
        val e5_4: Edge = DirectedEdge(v5, v4, 6)
        graph.addEdge(e1_2)
        graph.addEdge(e1_3)
        graph.addEdge(e1_5)
        graph.addEdge(e2_4)
        graph.addEdge(e2_5)
        graph.addEdge(e3_2)
        graph.addEdge(e4_1)
        graph.addEdge(e4_3)
        graph.addEdge(e5_4)
        val floyd_result: AllPairsShortPathResult = FloydWarshall(graph).applyAlgorithm()
        floyd_result.shortestPathsTreeOf(v1).print()
        System.out.println(floyd_result.shortestPathBetween(v5, v2).toString())
        val johnson_result: AllPairsShortPathResult = AllPairsShortPathFactory.newAllPairsShortPath(graph, AllPairsShortPathFactory.APSPAlgorithm.Johnson).applyAlgorithm()
        println("")
    }

    private fun matrixUtils() {
        val graph = SimpleDirectedGraph()
        val s = Vertex.create()
        s.setTag("s")
        val t = Vertex.create()
        t.setTag("t")
        val x = Vertex.create()
        x.setTag("x")
        val y = Vertex.create()
        y.setTag("y")
        val z = Vertex.create()
        z.setTag("z")
        graph.addVertex(s)
        graph.addVertex(t)
        graph.addVertex(x)
        graph.addVertex(y)
        graph.addVertex(z)
        val e1 = Edge(s, t, Edge.EDGE_DIRECTION.DIRECTED, 10)
        val e2 = Edge(t, x, Edge.EDGE_DIRECTION.DIRECTED, 1)
        val e3 = Edge(s, y, Edge.EDGE_DIRECTION.DIRECTED, 5)
        val e4 = Edge(y, z, Edge.EDGE_DIRECTION.DIRECTED, 2)
        val e5 = Edge(z, x, Edge.EDGE_DIRECTION.DIRECTED, 6)
        val e6 = Edge(t, y, Edge.EDGE_DIRECTION.DIRECTED, 2)
        val e7 = Edge(y, t, Edge.EDGE_DIRECTION.DIRECTED, 3)
        val e8 = Edge(x, z, Edge.EDGE_DIRECTION.DIRECTED, 4)
        val e9 = Edge(y, x, Edge.EDGE_DIRECTION.DIRECTED, 9)
        val e10 = Edge(z, s, Edge.EDGE_DIRECTION.DIRECTED, 7)
        graph.addEdge(e1)
        graph.addEdge(e2)
        graph.addEdge(e3)
        graph.addEdge(e4)
        graph.addEdge(e5)
        graph.addEdge(e6)
        graph.addEdge(e7)
        graph.addEdge(e8)
        graph.addEdge(e9)
        graph.addEdge(e10)
        val matrix_adjacency: Array<FloatArray> = SMatrixUtils.adjacencyMatrixOf(graph, 0f, true, Float.POSITIVE_INFINITY)
        val matrix_adjacency2: Array<FloatArray> = SMatrixUtils.adjacencyMatrixOf(graph, 0f, true, Float.POSITIVE_INFINITY)
        val matrix_incidence: Array<FloatArray> = SMatrixUtils.incidenceMatrixOf(graph)
        println("")
    }

    private fun DijkstraShortestPath() {
        val graph = SimpleDirectedGraph()
        val s = Vertex.create()
        s.setTag("s")
        val t = Vertex.create()
        t.setTag("t")
        val x = Vertex.create()
        x.setTag("x")
        val y = Vertex.create()
        y.setTag("y")
        val z = Vertex.create()
        z.setTag("z")
        graph.addVertex(s)
        graph.addVertex(t)
        graph.addVertex(x)
        graph.addVertex(y)
        graph.addVertex(z)
        val e_s_t: Edge = DirectedEdge(s, t, 10)
        val e_t_x: Edge = DirectedEdge(t, x, 1)
        val e_s_y: Edge = DirectedEdge(s, y, 5)
        val e_y_z: Edge = DirectedEdge(y, z, 2)
        val e_z_x: Edge = DirectedEdge(z, x, 6)
        val e_t_y: Edge = DirectedEdge(t, y, 2)
        val e_y_t: Edge = DirectedEdge(y, t, 3)
        val e_x_z: Edge = DirectedEdge(x, z, 4)
        val e_y_x: Edge = DirectedEdge(y, x, 9)
        val e_z_s: Edge = DirectedEdge(z, s, 7)
        graph.addEdge(e_s_t)
        graph.addEdge(e_t_x)
        graph.addEdge(e_s_y)
        graph.addEdge(e_y_z)
        graph.addEdge(e_z_x)
        graph.addEdge(e_t_y)
        graph.addEdge(e_y_t)
        graph.addEdge(e_x_z)
        graph.addEdge(e_y_x)
        graph.addEdge(e_z_s)
        graph.print()
        val res: ShortestPathsTree = DijkstraShortestPath(graph).setStartVertex(s).applyAlgorithm()
        res.print()
        println("")
    }

    private fun BellmanFord() {
        val graph = SimpleDirectedGraph()
        val s = DefaultVertex("s")
        val t = DefaultVertex("t")
        val x = DefaultVertex("x")
        val y = DefaultVertex("y")
        val z = DefaultVertex("z")
        graph.addVertex(s)
        graph.addVertex(t)
        graph.addVertex(x)
        graph.addVertex(y)
        graph.addVertex(z)
        graph.addEdge(s, t, 6)
        graph.addEdge(t, x, 5)
        graph.addEdge(x, t, -2)
        graph.addEdge(s, y, 7)
        graph.addEdge(y, z, 9)
        graph.addEdge(t, y, 8)
        graph.addEdge(z, x, 7)
        graph.addEdge(t, z, -4)
        graph.addEdge(y, x, -3)
        graph.addEdge(z, s, 2)
        graph.setTag("graph")
        graph.print()
        val res: ShortestPathsTree = BellmanFordShortestPath(graph).setStartVertex(s).applyAlgorithm()
        res.print()
        println("")
    }

    private fun general_erdos() {
        val allow_self_loops = true
        val allow_multi_edges = true
        val graph_undirected: BaseUndirectedGraph = Erdos.newUndirectedGraphWithEngine(AdjIncidenceGraphEngine(), allow_self_loops, allow_multi_edges)
        val graph: BaseDirectedGraph = Erdos.newGraphWithEngine(AdjIncidenceGraphEngine(), Edge.EDGE_DIRECTION.DIRECTED, allow_self_loops, allow_multi_edges)
        graph.vertices().iterator().remove()
        val iterator: MutableIterator<Vertex> = graph.vertices().iterator()
        while (iterator.hasNext()) {
            val next: Vertex = iterator.next()
            if (next.getTag().equals("tag_v2")) iterator.remove()
        }
    }

    private fun generalTest() {
        val graph = SimpleDirectedGraph()
        val v0 = Vertex.create()
        val v1 = Vertex.create()
        val v2 = Vertex.create()
        val v3 = Vertex.create()
        _v0 = v0
        graph.addVertex(v0)
        graph.addVertex(v1)
        graph.addVertex(v2)
        graph.addVertex(v3)
        graph.addEdge(v0, v1)
        graph.addEdge(v0, v2)
        graph.addEdge(v1, v2)
        graph.addEdge(v2, v3)
        graph.addEdge(v2, v0)
        graph.print()
        //
        val coll: Collection<Vertex> = graph.vertices()
        val coll_edges: Collection<Edge> = graph.edges()
        //coll.remove(v0);
        run {
            val iter: Iterator<Edge> = coll_edges.iterator()
            while (iter.hasNext()) {
                val edge: Edge = iter.next()
            }
        }
        val iter: Iterator<Vertex> = coll.iterator()
        while (iter.hasNext()) {
            val vv: Vertex = iter.next()
        }
        for (edge in graph.edges()) {
            System.out.println(edge.toString())
        }
        for (vertex in graph) {
            vertex.toString()
        }
        for (vertex in coll) { //System.out.println(vertex.getId());
        }
        //
        val breadthFirstTree: BFS.BreadthFirstTree = BFS(graph, _v0).applyAlgorithm()
        breadthFirstTree.print()
        for (edge in breadthFirstTree.edges()) {
            print(edge.toString().toString() + ",")
        }
        val depthFirstForest: DFS.DepthFirstForest = DFS(graph).applyAlgorithm()
        depthFirstForest.print()
        for (edge in depthFirstForest.edges()) {
            print(edge.toString().toString() + ",")
        }
        val hashSets: ArrayList<HashSet<Vertex<Any>>> = SCC(graph).applyAlgorithm()
        for (hashSet in hashSets) {
            print("SCC : {")
            for (vertex in hashSet) {
                print(vertex.getId().toString() + ",")
            }
            println("}")
        }
        try {
            val res_sort: LinkedList<Vertex> = TopologicalSort(graph).applyAlgorithm()
            println("TopoSort")
            for (i in res_sort.indices) {
                print(res_sort[i].getId().toString() + ",")
            }
        } catch (err: AlgorithmException) {
            err.printStackTrace()
        }
    }

    private fun mst() {
        val graph: BaseUndirectedGraph = SimpleGraph()
        val v0 = Vertex.create()
        val v1 = Vertex.create()
        val v2 = Vertex.create()
        val v3 = Vertex.create()
        val e1 = Edge(v0, v1, Edge.EDGE_DIRECTION.UNDIRECTED, 1)
        val e2 = Edge(v0, v2, Edge.EDGE_DIRECTION.UNDIRECTED, 5)
        val e3 = Edge(v1, v2, Edge.EDGE_DIRECTION.UNDIRECTED, 2)
        val e4 = Edge(v2, v3, Edge.EDGE_DIRECTION.UNDIRECTED, 21)
        val e5 = Edge(v1, v3, Edge.EDGE_DIRECTION.UNDIRECTED, 27)
        graph.addVertex(v0)
        graph.addVertex(v1)
        graph.addVertex(v2)
        graph.addVertex(v3)
        graph.addEdge(e1)
        graph.addEdge(e2)
        graph.addEdge(e3)
        graph.addEdge(e4)
        graph.addEdge(e5)
        graph.print()
        val mst_kruskal: BaseUndirectedGraph = MSTKruskal(graph).applyAlgorithm()
        mst_kruskal.print()
        val mst_prim: BaseUndirectedGraph = MSTPrim(graph).setStartVertex(v0).applyAlgorithm()
        mst_prim.print()
    }

    init {
        val ag: AbstractGraph = Erdos.newGraphWithEngine(AdjIncidenceGraphEngine(), Edge.EDGE_DIRECTION.DIRECTED, false, false)
        //generalTest();
//mst();
//BellmanFord();
//DijkstraShortestPath();
//matrixUtils();
        floyd_Warshall_and_johnson()
        //transitiveClosure();
    }
}