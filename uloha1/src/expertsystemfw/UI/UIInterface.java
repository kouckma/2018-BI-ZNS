/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expertsystemfw.UI;

import expertsystemfw.DataBase.Answer;
import expertsystemfw.KnowledgeBase.Element.Conclusion;
import expertsystemfw.KnowledgeBase.Element.Predicate;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javafx.util.Pair;

/**
 * This abstract class represents the UI.
 * It extends the Observable class from which there are methods used to notify the Observer (inference engine) to do an action.
 * The action might be stopping or starting the engine or explaining (why/what) via the UI.
 * 
 * @author werneric
 */
public abstract class UIInterface extends Observable { 
    
    /**
     * Notifies the inference engine to start.
     */
    protected void notifyStart(){
        setChanged();
        notifyObservers(Notification.START);
    }
    
    /**
     * Notifies the inference engine to stop.
     */
    protected void notifyStop(){
        setChanged();
        notifyObservers(Notification.STOP);
    }
    
    /**
     * Notifies the inference engine to explain WHY.
     */
    protected void notifyWhy(){
        setChanged();
        notifyObservers(Notification.WHY);
    }
    
    /**
     * Notifies the inference engine to explain HOW.
     */
    protected void notifyHow(){
        setChanged();
        notifyObservers(Notification.HOW);
    }
    
    /**
     * Adds a new observer.
     * This method must be used before starting the inference engine to give this class a reference of the Observer (inference engine in this case). 
     * 
     * @param observer an Observer class -> the inference engine
     */
    public void addNewObserver(Observer observer){
        addObserver(observer);
    }

    /**
     * Prints/shows to the user a list of conclusions and their percentage of certainty.
     * The implementation should cover single element in the list, multiple elements and also an empty list.
     * 
     * @param conclusions list of pairs which contains conclusion object and it's double value of certainty.
     */
    public abstract void conclude(List<Pair<Conclusion, Double>> conclusions);
    
    /**
     * Asks the user, if the specified predicate is true for his case and returns an Answer object.
     * 
     * @param question the predicate which is the user being asked for.
     * @return Answer object containing the predicate and double value of user certainty.
     */
    public abstract Answer askUser(Predicate question);
    
    /**
     * Based on the conclusion, explains what is the inference engine up for at the moment.
     * This method is meant to be called from the update() method from the inference engine.
     * 
     * @param conclusion the conclusion to be presented as an explanation.
     */
    public abstract void explainWhy(Conclusion conclusion);
    
    /**
     * Based on the list of answers, explains how did the inference engine get to the point where it is now.
     * This method is meant to be called from the update() method from the inference engine.
     * 
     * @param answers the list of answers to be presented as an explanation.
     */
    public abstract void explainHow(List<Answer> answers);
}
