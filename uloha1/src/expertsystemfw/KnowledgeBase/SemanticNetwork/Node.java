
package expertsystemfw.KnowledgeBase.SemanticNetwork;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author werneric
 */
public class Node {
    private String label;
    private ArrayList<Edge> edges;

    public Node(String label) {
        this.label = label;
        this.edges = new ArrayList<>();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }
    
    public ArrayList<Node> getNeighbours(){
        ArrayList<Node> neighbourList = new ArrayList<>();
        this.getEdges().forEach(a -> {
            neighbourList.add(a.getTo());
        });
        
        return neighbourList;
    }

    @Override
    public String toString() {
        return "Node{" + "label=" + label + ", edges=" + edges + '}' + System.lineSeparator();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.label);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        return Objects.equals(this.label, other.label);
    }
    
    

    
}