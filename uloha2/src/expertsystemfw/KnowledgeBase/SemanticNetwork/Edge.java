
package expertsystemfw.KnowledgeBase.SemanticNetwork;

import java.util.Objects;

/**
 *
 * @author werneric
 */
public class Edge {
    
    private Node from;
    private Node to;
    private Relation relation;

    public Edge(Node from, Node to, Relation relation) {
        this.from = from;
        this.to = to;
        this.relation = relation;
    }

    /*public Edge(){
        this.from = null;
        this.to = null;
        this.relation = null;
    }*/

    public Node getFrom() {
        return from;
    }

    public void setFrom(Node from) {
        this.from = from;
    }

    public Node getTo() {
        return to;
    }

    public void setTo(Node to) {
        this.to = to;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    @Override
    public String toString() {
        return "{" + from.getLabel() + ", " + relation.toString() + ", " + to.getLabel() + "}";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.from);
        hash = 43 * hash + Objects.hashCode(this.to);
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
        final Edge other = (Edge) obj;
        if (!Objects.equals(this.from, other.from)) {
            return false;
        }
        return Objects.equals(this.to, other.to);
    }
    
    
    
}
