package api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph {
    ArrayList<node_data> nodes;
    int edgeCount;
    int mc = 0;
    int nodeCount = 0;

    public DWGraph_DS(Collection<node_data> nodes, int edgeCount) {
        this.nodes = new ArrayList<>(nodes);
        this.edgeCount = edgeCount;
    }

    public DWGraph_DS() {
        this(new ArrayList<>(), 0);
    }

    public DWGraph_DS(DWGraph_DS g) {
        this.nodes = new ArrayList<>(g.getV());
        this.edgeCount = g.edgeSize();
    }

    /**
     * returns the node_data by the node_id,
     *
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_data getNode(int key) {
        try {
            return this.nodes.get(key);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("ERROR: the given node key isn't in an acceptable range");
            return null;
        }
//        if (nodeExists(key)) {
//            return this.nodesCollection.get(key);
//        }
//        return null;
    }

    /**
     * returns the data of the edge (src,dest), null if none.
     * Note: this method should run in O(1) time.
     *
     * @param src
     * @param dest
     * @return
     */
    @Override
    public edge_data getEdge(int src, int dest) { // TODO implement
        return null;
    }

    /**
     * adds a new node to the graph with the given node_data.
     * Note: this method should run in O(1) time.
     *
     * @param n
     */
    @Override
    public void addNode(node_data n) {
        // assumed it will always be 0,1,2..
        // check if node already exists
        int key = n.getKey();
        if (this.nodeExists(key)) {
            return;
        }

        n.setTag(this.nodeSize());
        this.nodes.add(n);
        this.mc++;
        this.nodeCount++;
    }

    /**
     * Connects an edge with weight w between node src to node dest.
     * * Note: this method should run in O(1) time.
     *
     * @param src  - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w    - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w) { // TODO change to directional
        Node_DS srcNode = (Node_DS) getNode(src);
        Node_DS destNode = (Node_DS) getNode(dest);
        if(src==dest) { //Assume you can't connect a node to itself
            return;
        }
        if (srcNode == null || destNode == null) {
            return;
        }
        if(!srcNode.hasNi(dest)) {
            srcNode.addNi(destNode);
            destNode.addNi(srcNode);
            this.edgeCount++;
        }
        srcNode.addNiW(destNode, w);
        destNode.addNiW(srcNode, w);
    }

    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the nodes in the graph.
     * Note: this method should run in O(1) time.
     *
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_data> getV() {
        return this.nodes;
    }

    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the edges getting out of
     * the given node (all the edges starting (source) at the given node).
     * Note: this method should run in O(k) time, k being the collection size.
     *
     * @param node_id
     * @return Collection<edge_data>
     */
    @Override
    public Collection<edge_data> getE(int node_id) {
        return null;
    } // TODO implement

    /**
     * Deletes the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(k), V.degree=k, as all the edges should be removed.
     *
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    public node_data removeNode(int key) {
        Node_DS node = (Node_DS) this.getNode(key);
        if (node == null) {
            return null;
        }
//        if (node.getKey() != key) {
//            return null;
//        }
        ArrayList<Node_DS> neighbors = node.getNi();
        while (!neighbors.isEmpty()) {
            neighbors.get(0).removeNode(node);
            neighbors.get(0).getKey();
            this.removeEdge(key, neighbors.get(0).getKey());
            neighbors.remove(0);
        }
        this.nodes.set(key, null);
        this.nodeCount--;
        return node;
    }

    /**
     * Deletes the edge from the graph,
     * Note: this method should run in O(1) time.
     *
     * @param src
     * @param dest
     * @return the data of the removed edge (null if none).
     */
    @Override
    public edge_data removeEdge(int src, int dest) { // TODO implement
        return null;
    }

    /**
     * Returns the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int nodeSize() {
        return this.nodeCount;
    }

    /**
     * Returns the number of edges (assume directional graph).
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int edgeSize() {
        return this.edgeCount;
    }

    /**
     * Returns the Mode Count - for testing changes in the graph.
     *
     * @return
     */
    @Override
    public int getMC() { // TODO implement
        return 0;
    }

    private boolean nodeExists(int key) {
        if (this.nodes.size() > key) {
            if (this.nodes.get(key) != null) {
                return true;
            }
        }
        return false;
    }
}
