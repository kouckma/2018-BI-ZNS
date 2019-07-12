
package expertsystemfw.InferenceEngine;

import expertsystemfw.DataBase.DataBase;
import expertsystemfw.DataBase.DataBaseInterface;
import expertsystemfw.KnowledgeBase.*;
import expertsystemfw.KnowledgeBase.Element.Conclusion;
import expertsystemfw.KnowledgeBase.Element.Predicate;
import expertsystemfw.UI.Notification;
import expertsystemfw.UI.UIInterface;
import expertsystemfw.UncertaintyModule.UncertaintyModuleInterface;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

/**
 * This abstract class represents the inference engine of an expert system.
 * It contains reference for all the other modules and uses these modules to store answers, communicate with the user, etc.
 * It implements the Observer interface for the purpose of communicating with the UI.
 * (More precisely, for the UI to be able to communicate with the inference engine)
 * 
 * @author werneric
 */
public abstract class InferenceEngineInterface implements Observer{

    protected KnowledgeBaseInterface knowledgeBase;
    protected UIInterface gui;
    protected UncertaintyModuleInterface uncertaintyModule;
    protected DataBaseInterface dataBase = new DataBase();
    protected int questionID;
    protected int AnsweredCount = 0;
    protected  Predicate currentPredicate = null;
    
    /**
     * The constructor which takes all the other modules as parameters.
     * 
     * @param kb the knowledge base implementation.
     * @param gui the UI implementation.
     * @param um the uncertainty module implementation.
     * @param db the data base implementation.
     */
    public InferenceEngineInterface(KnowledgeBaseInterface kb, UIInterface gui, UncertaintyModuleInterface um, DataBaseInterface db) {
        this.knowledgeBase = kb;
        this.gui = gui;
        this.uncertaintyModule = um;
        this.dataBase = db;
        this.questionID = 0;
    }

    /**
     * The overridden method of the Observer interface.
     * This method is used (executed) whenever the UI executes one of it's notification methods.
     * The purpose might be stopping and starting the engine or explaining.
     * The Object arg argument is an element from the Notification enumerate by which we determine, what is the purpose of this update.
     * 
     * @param o the reference to the object which notified the Observer (this class) to execute update() method.
     * @param arg an argument, which was sent from the Observable (UI in this case). An element from the Notification enumerate.
     */
    @Override
    public void update(Observable o, Object arg) {
        Set<Predicate> predicates = knowledgeBase.getAllPredicates();
        Set<Conclusion> conclusions = knowledgeBase.getAllConclusions();
        switch ((Notification)arg) {
            case START:
                System.out.println("START: You forgot to override the update() method in the Inference engine implementation!");
            case STOP:
                System.exit(0);
            case WHY:
                System.out.println("Im asking about: " + currentPredicate + " because it is not answered yet and it is needed for following conclusions:");
                for (Conclusion con : conclusions) {
                    Set<Predicate> tempPredicates = knowledgeBase.getPredicates(con);
                    if(tempPredicates.contains(currentPredicate))
                        System.out.println(con);
                }
                break;
            case HOW:
                for (Conclusion con : conclusions) {
                    Set<Predicate> tempPredicates = knowledgeBase.getPredicates(con);
                    int n = 0;
                    for (Predicate tmp : tempPredicates) {
                        System.out.println(tmp + ": ");
                        if (dataBase.contains(tmp)) {
                            n++;
                            System.out.println(dataBase.getAnswer(tmp).getAnswer());
                        }
                        else
                            System.out.println("No Answer");
                    }
                    if (n == tempPredicates.size()) {
                        System.out.println("Choice " + con + " has been fully determined.");
                    }
                }
                break;
            default:
                break;
        }
    }
    
    /**
     * This is the main cycle for the system.
     * That's where the behavior of the system is implemented, type of inference engine and the way, which the system uses other modules.
     * This method is what starts the system and when this method ends, the system stops.
     */
    public abstract void startInference();
}
