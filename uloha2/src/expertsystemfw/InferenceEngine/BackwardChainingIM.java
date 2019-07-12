package expertsystemfw.InferenceEngine;


import expertsystemfw.DataBase.Answer;
import expertsystemfw.DataBase.DataBase;
import expertsystemfw.DataBase.DataBaseInterface;
import expertsystemfw.KnowledgeBase.Element.Conclusion;
import expertsystemfw.KnowledgeBase.Element.Predicate;
import expertsystemfw.KnowledgeBase.KnowledgeBaseInterface;
import expertsystemfw.KnowledgeBase.RulesKnowledgeBase;
import expertsystemfw.UI.UIInterface;
import expertsystemfw.UncertaintyModule.UncertaintyModuleInterface;

import java.util.Set;

/**
 * @author werneric
 */
public class BackwardChainingIM extends InferenceEngineInterface {

    public BackwardChainingIM(KnowledgeBaseInterface kb, UIInterface gui, UncertaintyModuleInterface um, DataBaseInterface db) {
        super(kb, gui, um, db);
    }

    @Override
    public void startInference() {

//        knowledgeBase.loadFile();
        Set<Predicate> predicates = knowledgeBase.getAllPredicates();
        Set<Conclusion> conclusions = knowledgeBase.getAllConclusions();


        System.out.println("print all conclusions");

        for (Conclusion item:conclusions) {
            System.out.println(item);
        }

//        for (int i = 0; i < 10; i++) {
////            System.out.println(item);
//            System.out.println("hello");
//        }

        while (true) {
            Predicate currentPredicate = new Predicate("uprchlici");
            Answer ans = gui.askUser(currentPredicate);
            dataBase.addAnswer(ans);

            currentPredicate = new Predicate("babis");
            ans = gui.askUser(currentPredicate);
            dataBase.addAnswer(ans);

            currentPredicate = new Predicate("pravice");
            ans = gui.askUser(currentPredicate);
            dataBase.addAnswer(ans);

            currentPredicate = new Predicate("levice");
            ans = gui.askUser(currentPredicate);
            dataBase.addAnswer(ans);

            break;
        }
//        RulesKnowledgeBase knowBase = new RulesKnowledgeBase();
//        knowBase.getAllPredicates();
    }

}
