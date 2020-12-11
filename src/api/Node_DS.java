package api;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Node_DS implements node_data, Serializable, Comparable<node_data>  {
    transient ArrayList<Node_DS> neighbors;
    ArrayList<Double> weights;
    String color;
    int tag;
    int key;

    public Node_DS(int key) {
        this.neighbors = new ArrayList<>();
        this.weights = new ArrayList<>();
        this.color = "";
        this.key = key;
    }
    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public geo_location getLocation() { // TODO implement
        return null;
    }

    @Override
    public void setLocation(geo_location p) { // TODO implement

    }

    @Override
    public double getWeight() {
        return 0;
    } // TODO implement

    @Override
    public void setWeight(double w) { // TODO implement

    }

    @Override
    public String getInfo() {
        return this.color;
    }

    @Override
    public void setInfo(String s) {
        this.color = s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.key = t;
    }

    public ArrayList<Node_DS> getNi() {
        return this.neighbors;
    }

    public boolean hasNi(int key) {
        if (this.neighbors == null) {
            return false;
        }
        for (node_data neighbor : this.neighbors) {
            if (neighbor.getKey() == key) {
                return true;
            }
        }
        return false;
    }

    public void addNi(node_data t) {
        this.neighbors.add((Node_DS) t);
        this.weights.add(-1.0);
    }

    public void addNiW(node_data t, double weight) {
        int key = this.neighbors.indexOf(t);
        this.weights.set(key, weight);
    }

    public double getNiW(node_data t) {
        int key = this.neighbors.indexOf(t);
        return this.weights.get(key);
    }

    public void removeNode(node_data node) {
        if (this == node) {
            return;
        }
        this.neighbors.remove(node);
        this.weights.set(node.getKey(), -1.0);
    }

    @Override
    public int compareTo(@NotNull node_data o) {
        if (this.getTag() > o.getTag()) {
            return 1;
        } else if (this.getTag() < o.getTag()) {
            return -1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        // Check if o is an instance of the graph
        if (!(o instanceof Node_DS)) {
            return false;
        }
        // compare data members
        Node_DS other = (Node_DS) o;
        if (this.getKey() != other.getKey() || this.getTag() != other.getTag() || !this.getInfo().equals(other.getInfo())) {
            return false;
        }
        return true;
    }
}
