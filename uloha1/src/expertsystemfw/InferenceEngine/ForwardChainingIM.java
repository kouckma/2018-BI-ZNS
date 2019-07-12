package expertsystemfw.InferenceEngine;


import expertsystemfw.DataBase.Answer;
import expertsystemfw.DataBase.DataBaseInterface;
import expertsystemfw.KnowledgeBase.Element.Conclusion;
import expertsystemfw.KnowledgeBase.Element.Predicate;
import expertsystemfw.KnowledgeBase.KnowledgeBaseInterface;
import expertsystemfw.UI.UIInterface;
import expertsystemfw.UncertaintyModule.UncertaintyModuleInterface;
import scala.Array;

import java.util.Iterator;
import java.util.Set;

/**
 * @author werneric
 */
public class ForwardChainingIM extends InferenceEngineInterface {

    public ForwardChainingIM(KnowledgeBaseInterface kb, UIInterface gui, UncertaintyModuleInterface um, DataBaseInterface db) {
        super(kb, gui, um, db);
    }

    public void answerQuestion(Predicate INpred) {
        Answer ans = gui.askUser(INpred);
        if (ans != null) {
            dataBase.addAnswer(ans);
            questionID++;
            AnsweredCount++;
        }

    }

    private boolean alreadyAnswered(Predicate INpred) {
        return dataBase.contains(INpred);
    }

    @Override
    public void startInference() {

        Set<Predicate> predicates = knowledgeBase.getAllPredicates();
        Set<Conclusion> conclusions = knowledgeBase.getAllConclusions();


//        System.out.println("print all conclusions");
//
//        for (Conclusion item : conclusions) {
//            System.out.println(item);
//        }
//
//        Array<Predicate> predicatesArray;
//        int
//        for (Predicate pr : predicates)
//        {
//            System.out.println(pr);
//predicatesArray[]
//        }
//
//        Iterator<Predicate> predIter = predicates.iterator();

        while (true) {

            for (Predicate pre : predicates)
            {
                if (!alreadyAnswered(pre)) {
                    currentPredicate = pre;
                    answerQuestion(pre);
                }
            }

//            if (!alreadyAnswered(currentPredicate)) {
//                answerQuestion(currentPredicate);
//                if (predIter.hasNext())
//                    currentPredicate = predicates.iterator().next();
//                else
//                    break;
//            }

//            currentPredicate = new Predicate("uprchlici");
//            if (!alreadyAnswered(currentPredicate))
//                answerQuestion(currentPredicate);
//
//            currentPredicate = new Predicate("konopi");
//            if (!alreadyAnswered(currentPredicate))
//                answerQuestion(currentPredicate);
//
//            currentPredicate = new Predicate("pravice");
//            if (!alreadyAnswered(currentPredicate))
//                answerQuestion(currentPredicate);
//
//            currentPredicate = new Predicate("levice");
//            if (!alreadyAnswered(currentPredicate))
//                answerQuestion(currentPredicate);
//
//            currentPredicate = new Predicate("referendumNATO");
//            if (!alreadyAnswered(currentPredicate))
//                answerQuestion(currentPredicate);
//
//            currentPredicate = new Predicate("euro");
//            if (!alreadyAnswered(currentPredicate))
//                answerQuestion(currentPredicate);


            if (AnsweredCount == predicates.size())
                break;
        }
//        for (Predicate pred : predicates)
//        {
//            if(dataBase.contains(pred))
//            System.out.println(dataBase.getAnswer(pred).getAnswer());
//            System.out.println(pred.getName());
//
//        }
        int countChoices = 0;
        for (Conclusion con : conclusions) {
            Set<Predicate> tempPredicates = knowledgeBase.getPredicates(con);
            int n = 0;
            for (Predicate tmp : tempPredicates) {
                if (dataBase.contains(tmp))
                    if (dataBase.getAnswer(tmp).getAnswer() == 1) {
                        n++;
                    }
            }
            if (n == tempPredicates.size()) {
                System.out.println("You should consider voting for: " + con);
                countChoices++;
            }
        }
        if(countChoices == 0)
            System.out.println("You probably shouldnt vote at all.");


//        RulesKnowledgeBase knowBase = new RulesKnowledgeBase();
//        knowBase.getAllPredicates();
    }
}