package api;

import java.io.*;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DWGraph_Algo implements dw_graph_algorithms {
    Gson gson;
    String fileName = "src/api/graph.json";
    DWGraph_DS g;

    /**
     * Init the graph on which this set of algorithms operates on.
     *
     * @param g
     */
    @Override
    public void init(directed_weighted_graph g) {
        this.g = (DWGraph_DS) g;
    }

    /**
     * Return the underlying graph of which this class works.
     *
     * @return
     */
    @Override
    public directed_weighted_graph getGraph() {
        return this.g;
    }

    /**
     * Compute a deep copy of this weighted graph.
     *
     * @return
     */
    @Override
    public directed_weighted_graph copy() {
        return null;
    } // TODO implement

    /**
     * Returns true if and only if (iff) there is a valid path from each node to each
     * other node. NOTE: assume directional graph (all n*(n-1) ordered pairs).
     *
     * @return
     */
    @Override
    public boolean isConnected() {
        return false;
    } // TODO implement

    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        // check if source and destination exist
        if (this.g.getNode(src) == null || this.g.getNode(dest) == null) {
            return -1;
        }

        HashMap<Node_DS, Node_DS> prev = dijkstra(src, dest);
        Node_DS node = prev.get(this.g.getNode(dest));
        // check if the destination has been reached
        if (node == null) {
            return -1;
        }
        return this.g.getNode(dest).getTag();
    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        ArrayList<node_data> path = new ArrayList<>();
        // check if source and destination exist
        if (this.g.getNode(src) == null || this.g.getNode(dest) == null) {
            return null;
        }

        HashMap<Node_DS, Node_DS> prev = dijkstra(src, dest);
//        ArrayList<Node_DS> prev = dijkstra(src);

        Node_DS node = prev.get(this.g.getNode(dest));
        // check if the destination has been reached
        if (node == null) {
            return null;
        }
        while (node != null) {
            path.add(node);
            node = prev.get(node);
        }

        Collections.reverse(path);
        path.add((Node_DS) this.g.getNode(dest));
        if (path.get(0).getKey() == src) {
            return path;
        }
        return null;
    }

    private HashMap<Node_DS, Node_DS> dijkstra(int src, int dest) {
        HashMap<Node_DS, Node_DS> prevDict = new HashMap<>(); //TODO Pqueue
        double w;
        for (node_data node : this.g.getV()) {
            node.setInfo("white"); // not visited
            node.setTag(-1); // no distance from src
            prevDict.put((Node_DS) node, null);
        }
        Node_DS node = (Node_DS) this.g.getNode(src);
        if (node == null) {
            System.out.println("source node doesn't exist");
            return null;
        }

        PriorityQueue<Node_DS> pq = new PriorityQueue<>();
        pq.add(node);
        node.setTag(0);

        while (!pq.isEmpty()) {
            node = pq.poll();
            if (node.getKey() == dest) {
                return prevDict;
            }
            for (Node_DS neighbor : node.getNi()) {
                if (neighbor.getInfo().equals("white")) {
//                    w = this.g.getEdge(node.getKey(), neighbor.getKey());
                    w = node.getNiW(neighbor);
                    // if never visited add first distance
                    if (neighbor.getTag() == -1) {
                        neighbor.setTag((int) (node.getTag() + w)); // TODO change to double
                        prevDict.replace(neighbor, node);
                    }
                    // check if distance can improve
                    if (neighbor.getTag() > node.getTag()+w) {
                        neighbor.setTag((int) (node.getTag() + w));
                        prevDict.replace(neighbor, node);
                    }
                    // add node to search queue
//                    q.add(neighbor);
                    pq.add(neighbor);
                }
            }
            // mark as visited
            node.setInfo("black");
        }

        return prevDict;
    }

    /**
     * Saves this weighted (directed) graph to the given
     * file name - in JSON format
     *
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        this.gson = new Gson();
//        Gson gson= newGsonBuilder().create();
//        Node_DS nodeDS = (Node_DS) this.getGraph().getNode(0);
//        String node = gson.toJson(nodeDS);
//        String nodeNi = gson.toJson(nodeDS.getNi());
        String wdGraph = gson.toJson(this.getGraph());

        try {
            FileWriter fw = new FileWriter(fileName);
            PrintWriter outs = new PrintWriter(fw);
            outs.println(wdGraph);
            outs.close();
            fw.close();
        } catch(IOException e) {
            System.out.print("Error writing file\n" + e);
        }

        return false;
    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     *
     * @param file - file name of JSON file
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        String str;
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            str = br.readLine();
            System.out.println(0+") "+str);
            for (int i=1; str!=null; i=i+1) {
                str = br.readLine();
                if (str != null) {
                    System.out.println(i+") "+str);
                }
            }
            br.close();
            fr.close();
        } catch(IOException ex) {
            System.out.print("Error reading file\n" + ex);
            return false;
        }
        // String g5 = gson.fromJson("\"abc\"", String.class); //print ->abc
        // int[] g6 = gson.fromJson(s6, int[].class);//print ->[1, 2, 4]
        // Person g7 = gson.fromJson(s7, Person.class);
        DWGraph_DS dwGraph = this.gson.fromJson(str, DWGraph_DS.class);

        return false;
    }
}
