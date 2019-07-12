/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expertsystemfw.DataBase;

import expertsystemfw.KnowledgeBase.Element.Predicate;
import java.util.List;

/**
 * This interface represents the data base module for an expert system. It stores user responses (Answer objects).
 * @author werneric
 */
public interface DataBaseInterface {
    /**
     * Adds a new answer into the data base.
     * @param answer the answer to be added.
     */
    void addAnswer(Answer answer);
    
    /**
     * Returns true if the data base contains the answer for the specified predicate.
     * @param predicate 
     * @return true if the data base contains an answer for he specified predicate, else false.
     */
    boolean contains(Predicate predicate);
    
    /**
     * Returns the answer for the specified predicate or null if there's no answer for the predicate.
     * @param predicate the specified predicate.
     * @return Answer object representing the answer for the predicate, or null if there's no answer for the predicate
     */
    Answer getAnswer(Predicate predicate);
    
    /**
     * Returns an ordered list of all answers which the user answered to. Usable for the explanation process.
     * @return an ordered list of all the answers which the user answered to.
     */
    List<Answer> getAllAnswers();
}
