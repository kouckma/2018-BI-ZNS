/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expertsystemfw.DataBase;

import expertsystemfw.KnowledgeBase.Element.Predicate;

/**
 * This class represents the user answer throughout the system.
 * The Answer consists of predicate and Double value, where the double value is the user's answer for the predicate.
 * 
 * @author werneric
 */
public class Answer {
    private Predicate predicate;
    private Double answer;

    /**
     * Constructor for the Answer class
     * @param predicate the predicate to which the user responded
     * @param answer the double value of the answer (number between 0 and 1, including both)
     */
    public Answer(Predicate predicate, Double answer) {
        this.predicate = predicate;
        this.answer = answer;
    }

    /**
     * Returns the predicate.
     * @return the predicate.
     */
    public Predicate getPredicate() {
        return predicate;
    }

    /**
     * Sets the predicate to the specified one.
     * @param predicate the specified predicate to be set.
     */
    public void setPredicate(Predicate predicate) {
        this.predicate = predicate;
    }

    /**
     * Returns the double value of the answer.
     * @return the double value of the answer.
     */
    public Double getAnswer() {
        return answer;
    }

    /**
     * Sets the answer to the specified one.
     * @param answer the specified answer to be set.
     */
    public void setAnswer(Double answer) {
        this.answer = answer;
    }
    
    /**
     * Returns true, if the answer equals 1.0, else returns false.
     * This method is not contrary to the isFalse() method! Both return false, if the answer is BETWEEN 0 and 1.
     * @return true if the answer equals 1.0, else returns false.
     */
    public boolean isTrue(){
        return answer.equals(1.0);
    }
    
    /**
     * Returns true, if the answer equals 0.0, else returns false.
     * This method is not contrary to the isTrue() method! Both return false, if the answer is BETWEEN 0 and 1.
     * @return true if the answer equals 0.0, else returns false.
     */
    public boolean isFalse(){
        return answer.equals(0.0);
    }
    
}
