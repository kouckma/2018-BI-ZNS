
package expertsystemfw.KnowledgeBase.Element;

import expertsystemfw.KnowledgeBase.SemanticNetwork.Relation;
import java.util.Objects;

/**
 * This class represents the predicate for the semantic network and extends the Predicate class.
 * It additionally to the Predicate contains Relation.
 * 
 * @author werneric
 */
public class SemPredicate extends Predicate {
    private final Relation relation;

    /**
     * Constructor which takes name and a relation as parameters.
     * 
     * @param name the name of the predicate,
     * @param relation the Relation object for the relation.
     */
    public SemPredicate(String name, Relation relation) {
        super(name);
        this.relation = relation;
    }
    
    /**
     * Returns the name of the relation.
     * @return the name of the relation.
     */
    public Relation getRelation(){
        return relation;
    }

    /**
     * returns the name of the relation and the name of the predicate in format:
     * relation_name:predicate_name
     * 
     * @return both relation and predicate name in format mentioned above
     * 
     */
    @Override
    public String toString() {
        return this.getRelation().translation() + ' ' + this.getName();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.relation);
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
        final SemPredicate other = (SemPredicate) obj;
        return Objects.equals(this.relation, other.relation) &&
                Objects.equals(this.name, other.name);
    }
    
    
}
