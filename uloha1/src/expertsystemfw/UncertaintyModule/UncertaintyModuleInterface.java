/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expertsystemfw.UncertaintyModule;

import expertsystemfw.KnowledgeBase.Element.Conclusion;
import expertsystemfw.KnowledgeBase.Element.Predicate;

/**
 * This interface represents the uncertainty module of an expert system.
 * 
 * @author werneric
 */
public interface UncertaintyModuleInterface {
    /**
     * This method loads the file located in this project to fill the uncertainty module.
     * It should be called in the constructor.
     */
    void loadFile();
    
    /**
     * Returns the certainty for the combination of conclusion and predicate.
     * If the combination of conclusion and predicate is not present, returns 1.0.
     * It means that you don't need to insert all combinations into the module, but only those with reduced certainty.
     * 
     * @param conclusion the specified conclusion.
     * @param predicate the specified predicate.
     * @return the double value of certainty for the specified combination of conclusion and predicate.
     */
    Double getCertainty(Conclusion conclusion, Predicate predicate);
    
    /**
     * Adds a new certainty entry for the specified combination of conclusion and predicate.
     * 
     * @param conclusion the specified conclusion.
     * @param predicate the specified predicate.
     * @param certainty the certainty for the specified combination of conclusion and predicate.
     */
    void addCertainty(Conclusion conclusion, Predicate predicate, Double certainty);
}
