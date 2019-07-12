
package expertsystemfw.KnowledgeBase;

import expertsystemfw.KnowledgeBase.Element.Conclusion;
import expertsystemfw.KnowledgeBase.Element.Predicate;
import java.util.Set;

/**
 * This interface represents knowledge base for an expert system.
 * 
 * @author werneric
 */
public interface KnowledgeBaseInterface {
    
    /**
     * This method loads the file located in this project to fill the knowledge base.
     * It is not manually used since it is used in constructor in every implementation.
     */
    void loadFile();
    
    /**
     * Returns set of all the conclusions detected in the knowledge base.
     * 
     * @return set of all the conclusions.
     */
    Set<Conclusion> getAllConclusions();
    
    /**
     * Returns set of all the predicates detected in the knowledge base.
     * 
     * @return set of all the predicates.
     */
    Set<Predicate> getAllPredicates();
    
    /**
     * Returns predicates relevant to the specified conclusion.
     * Relevant predicates are those that, when queried to the knowledge base with the specified conclusion, return true;
     * 
     * @param conclusion the specified conclusion.
     * @return relevant predicates to the conclusion.
     */
    Set<Predicate> getPredicates(Conclusion conclusion);
    
    /**
     * Queries the knowledge base with combination of a predicate and a conclusion and returns true if the predicate is true for the conclusion.
     * 
     * @param predicate the specified predicate.
     * @param conclusion the specified conclusion.
     * @return true if the predicate is true for the conclusion.
     */
    boolean query(Predicate predicate, Conclusion conclusion);
}
