package expertsystemfw.InferenceEngine;

import expertsystemfw.DataBase.Answer;
import expertsystemfw.DataBase.DataBaseInterface;
import expertsystemfw.KnowledgeBase.Element.Conclusion;
import expertsystemfw.KnowledgeBase.Element.Predicate;
import expertsystemfw.KnowledgeBase.FuzzyDatabase;
import expertsystemfw.KnowledgeBase.KnowledgeBaseInterface;
import expertsystemfw.KnowledgeBase.Trapezoid;
import expertsystemfw.UI.UIInterface;
import expertsystemfw.UncertaintyModule.UncertaintyModuleInterface;
import javafx.util.Pair;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.math3.exception.NotANumberException;
import scala.Array;

import javax.validation.constraints.Null;
import java.util.*;

/**
 * @author werneric
 */
public class ForwardChainingIM extends InferenceEngineInterface {

    public ForwardChainingIM(KnowledgeBaseInterface kb, UIInterface gui, UncertaintyModuleInterface um, DataBaseInterface db, FuzzyDatabase fd) {
        super(kb, gui, um, db, fd);
    }

    public boolean answerQuestion(Predicate INpred) {
        Answer ans = gui.askUser(INpred);
        if (ans != null) {
            dataBase.addAnswer(ans);
            questionID++;
            AnsweredCount++;
            return true;
        }
        return false;
    }

    private boolean alreadyAnswered(Predicate INpred) {
        return dataBase.contains(INpred);
    }

    private Conclusion isObsolete() {
        Set<Conclusion> conclusions = knowledgeBase.getAllConclusions();
        Set<Predicate> predicates = knowledgeBase.getAllPredicates();
        for (Conclusion con : conclusions) {
            Set<Predicate> currPredicates = knowledgeBase.getPredicates(con);
            if (currPredicates.contains(currentPredicate)) {
                boolean isOBS = false;
                for (Predicate pr : currPredicates) {
                    if (dataBase.contains(pr)) {
                        if (dataBase.getAnswer(pr).getAnswer() == 0) {
                            isOBS = true;
//                            System.out.println("jsem true :( a pritom mam hodnotu:: " + dataBase.getAnswer(pr).getAnswer());
                        }
                    }
                }
                if (!isOBS)
                    return con;
            }
        }
        return null;

    }

    private boolean isObs(Conclusion con) {
        Set<Predicate> currPredicates = knowledgeBase.getPredicates(con);
        for (Predicate pr : currPredicates) {
            if (!dataBase.contains(pr)) {
                return false;
            }
        }
        return true;
    }

    private boolean isWrong(Conclusion con) {
        Set<Predicate> currPredicates = knowledgeBase.getPredicates(con);
        for (Predicate pr : currPredicates) {
            if (dataBase.contains(pr)) {
                if (dataBase.getAnswer(pr).getAnswer() == 0)
                    return true;
            }
        }
        return false;
    }

    private void doHow() {
        Set<Conclusion> conclusions = knowledgeBase.getAllConclusions();

        for (Conclusion con : conclusions) {
            Set<Predicate> tempPredicates = knowledgeBase.getPredicates(con);
            int n = 0;
            for (Predicate tmp : tempPredicates) {
                System.out.println(tmp + ": ");
                if (dataBase.contains(tmp)) {
                    n++;
                    System.out.println(dataBase.getAnswer(tmp).getAnswer());
                } else
                    System.out.println("No Answer");
            }
            if (n == tempPredicates.size()) {
                System.out.println("Choice " + con + " has been fully determined.");
                System.out.println("============================================================\n");
            } else {
                System.out.println("Choice " + con + " has not been fully determined.");
                System.out.println("============================================================\n");
            }
        }
    }

    private Predicate findPred(String nameOf, Predicate[] predArr) {
        for (int i = 0; i < predArr.length; i++)
            if (predArr[i].getName().equals(nameOf))
                return predArr[i];

        return null;
    }

    private void updateNegative(int id, Predicate[] predicates) {
        Predicate tmp = null;

        //~~~~~~~~~~~~~start~~~~~~~~~~~~~~~~

        if (currentPredicate.toString().equals("rightLeaning"))
            tmp = findPred("NErightLeaning", predicates);
        else if (currentPredicate.toString().equals("NErightLeaning"))
            tmp = findPred("rightLeaning", predicates);
        else if (currentPredicate.toString().equals("uprchlici"))
            tmp = findPred("NEuprchlici", predicates);
        else if (currentPredicate.toString().equals("NEuprchlici"))
            tmp = findPred("uprchlici", predicates);
        else if (currentPredicate.toString().equals("marihuana"))
            tmp = findPred("NEmarihuana", predicates);
        else if (currentPredicate.toString().equals("immigrants"))
            tmp = findPred("NEimmigrants", predicates);
        else if (currentPredicate.toString().equals("NEmarihuana"))
            tmp = findPred("marihuana", predicates);
        else if (currentPredicate.toString().equals("NEimmigrants"))
            tmp = findPred("immigrants", predicates);
        else if (currentPredicate.toString().equals("referendumNATO"))
            tmp = findPred("NEreferendumNATO", predicates);
        else if (currentPredicate.toString().equals("NEreferendumNATO"))
            tmp = findPred("referendumNATO", predicates);
        else if (currentPredicate.toString().equals("euro"))
            tmp = findPred("NEeuro", predicates);
        else if (currentPredicate.toString().equals("NEeuro"))
            tmp = findPred("euro", predicates);


        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~end~~~~~~~~~~~~~~~~~~~~~~~~~
        if (tmp != null) {
//            if (!alreadyAnswered(tmp)) {
            Answer ans = new Answer(tmp, 1.0 - dataBase.getAnswer(currentPredicate).getAnswer());
            dataBase.addAnswer(ans);
            System.out.println("pridal sem" + ans.getAnswer());
            AnsweredCount++;
//            }
        }
    }


    private void fillEmpty() {
        for (Predicate predicate : knowledgeBase.getAllPredicates()) {
            if (dataBase.contains(predicate)) {
                continue;
            } else {
                dataBase.addAnswer(new Answer(predicate, 0.5));
            }
        }
    }

    private Double getProbability(Conclusion con) {

        Double startProb = 1.0;
        for (Predicate predicate : knowledgeBase.getPredicates(con)) {
            startProb *= uncertaintyModule.getCertainty(con, predicate) * dataBase.getAnswer(predicate).getAnswer();
//            System.out.println(startProb);
        }

//        System.out.println(startProb);
        return startProb;
    }

    private double min(double a, double b) {
        if (a < b)
            if (a < 0)
                return 0;
            else
                return a;
        else if (b < 0) return 0;
        else return b;
    }

    private double max(double a, double b) {
        if (a > b)
            if (a > 1)
                return 1;
            else
                return a;
        else if (b > 1) return 1;
        else return b;
    }

    double doTheMathEND(double input, double c, double d) {
        if (input >= d)
            return 1;
        else if (input > c) {
            return max(0, min((input - c) / (d - c), 1));
        } else
            return 0;
    }

    double doTheMathSTART(double input, double a, double b) {
        if (input <= a)
            return 1;
        else if (input < b) {
            return max(0, min((b - input) / (b - a), 1));
        } else
            return 0;
    }

    double doTheMath(double input, double a, double b, double c, double d) {
        if (input >= b && input <= c)
            return 1;
        else if (input > a && input < d) {
            return max(0, min((input - a) / (b - a), (d - input) / (d - c)));
        } else
            return 0;
    }

    private void getFuzzyInputs() {
        predicateResults.put(currentPredicate.toString(), new HashMap<>());
        double value = dataBase.getAnswer(currentPredicate).getAnswer().doubleValue();
        Map<String, Trapezoid> fuzzymap = fuzzyBase.getFuzzySets().get(currentPredicate.toString());
        double result = 0;
        for (Map.Entry<String, Trapezoid> entry : fuzzymap.entrySet()) {
            if (entry.getValue().atEnd) {
                result = doTheMathEND(value, entry.getValue().c, entry.getValue().d);
            } else if (entry.getValue().atStart) {
                result = doTheMathSTART(value, entry.getValue().a, entry.getValue().b);
            } else {
                result = doTheMath(value, entry.getValue().a, entry.getValue().b, entry.getValue().c, entry.getValue().d);
//                System.out.println(entry.getKey() + " is at end ?" + entry.getValue().atEnd + " val of abcd: " + entry.getValue().a + " : " + entry.getValue().b + " : " + entry.getValue().c
//                        + " : " + entry.getValue().d);
            }
            predicateResults.get(currentPredicate.toString()).put(entry.getKey(), result);
//            System.out.println("and the result of _ " + entry.getKey() + " _ is: ___ " + result);
        }
        for (Map.Entry<String, Map<String, Double>> test : predicateResults.entrySet()) {
//            System.out.println("it... works ? " + test.getKey() + " with size: " + test.getValue().size()
//                    + "  " + test.getValue().get("no").doubleValue()
//            );
        }

    }

    void solveCurrentConc(Conclusion conc) {
        Map<String, String> tmp = fuzzyBase.getFuzzyRules().get(conc.getName());
        double prevValue = 1;
        for (Map.Entry<String, String> entry : tmp.entrySet()) {
//            System.out.println(entry.getKey() + entry.getValue());
//            System.out.println(prevValue + " ?????? " + min(prevValue,predicateResults.get(entry.getKey()).get(entry.getValue()).doubleValue()));
            prevValue = min(prevValue, predicateResults.get(entry.getKey()).get(entry.getValue()).doubleValue());
        }
        results.put(conc.getName(), prevValue);
    }

    double calcA(String name, double val, Trapezoid trap) {
        return 0.5 * ((trap.c - trap.b) + (trap.d - trap.a)) * val;
    }

    double doCOS() {
        double sumUP = 0;
        for (Map.Entry<String, Double> entry : results.entrySet()) {
            Trapezoid trap = fuzzyBase.getFuzzySets().get("result_set").get(entry.getKey());
            if (entry.getValue() > 0) {
//                System.out.println(entry.getKey() + "  " + entry.getValue());
                double a = calcA(entry.getKey(), entry.getValue(), trap);
                double x = (trap.d + trap.a) / 2;
                sumUP += a * x;
            }
        }
        double sumDOWN = 0;
        for (Map.Entry<String, Double> entry : results.entrySet()) {
            Trapezoid trap = fuzzyBase.getFuzzySets().get("result_set").get(entry.getKey());
            if (entry.getValue() > 0) {
//                System.out.println(entry.getKey() + "  " + entry.getValue());
                double a = calcA(entry.getKey(), entry.getValue(), trap);
                sumDOWN += a;
            }
        }
        if (sumDOWN > 0)
            return sumUP / sumDOWN;
        else {
//            System.out.println("wtf");
            return 0;
        }
    }

    double getmaxRight() {
        Map<String, Trapezoid> tmpMap = fuzzyBase.getFuzzySets().get("result_set");
        double maxVal = 0;
        for (Map.Entry<String, Trapezoid> entry : tmpMap.entrySet()) {
            if (entry.getValue().d > maxVal)
                maxVal = entry.getValue().d;
        }
        return maxVal;
    }

    @Override
    public void startInference() {

        fuzzyBase.loadFile();


        Set<Predicate> tmpPredicates = knowledgeBase.getAllPredicates();
        Set<Conclusion> conclusions = knowledgeBase.getAllConclusions();


//        Predicate[] predicates = new Predicate[tmpPredicates.size()];
//        int i = 0;
//        for (Predicate pre : tmpPredicates) {
//            predicates[i] = pre;
//            i++;
//        }
        int predID = 0;
        System.out.println(
//                "\nHello user, you may answer yes, no or express your feelings more specifically using percentage (yes == 1.00 ; no == 0.00 ; kinda == 0.73)" +
                "\nHello user, you may answer by typing how strongly you agree or disagree on a scale" +
                        " of 1 to 10, for example 5 could mean 'not sure' and 10 could mean fully agree" +
                        "\nyou also have the option to end the program by typing 'stop'" +
                        "\n--------------------------------------------------------------------------------------------------------------------------------------------------------------"
//                "\nanswers are of two types positive ('euro') and their negations ('NEeuro') answer however you feel about them or their negations (they will not be asked twice)"
        );
        System.out.println("===============================================================================================================================================================\n");
//        //ODS,0.25
//        Map<String,double> results;
//        //immigrants,<no,0.5><perQuotes,1><yes,0>
//        Map<String,Map<String,double>> predicateResults;

        for (Conclusion concl : conclusions) {

            currentConclusion = concl;
//            System.out.println("Im now determining this conclusion: " + concl);

            //this I give to the following for;
            Predicate[] predicates = knowledgeBase.getPredicates(concl).toArray(new Predicate[knowledgeBase.getPredicates(concl).size()]);

            for (int i = 0; i < predicates.length; i++) {
                currentPredicate = predicates[i];
                if (!alreadyAnswered(currentPredicate)) {
                    if (answerQuestion(currentPredicate))
                        getFuzzyInputs();
                }
            }
            solveCurrentConc(concl);
        }


        //for debug or more info in general
        for (Map.Entry<String, Double> entry : results.entrySet()) {
            if(entry.getValue() > 0)
            System.out.println(entry.getKey() + "  " + entry.getValue());
        }

        //METODA COS - Center Of Sums
        double res = doCOS();

        System.out.println("\n======================");
        System.out.println("===  your results:  ==");
        System.out.println("======================\n");

        if (res == 0)
            System.out.println("Im sorry but I couldn't determine where on political spectrum of right/left you belong" +
                    "\nthis may be due to limitations to political parties."
            );
        else {
            System.out.println(
                    "\nYou probably belong here: " + res +
                            "\nmost left-wing is: 0 and most right-wing is " + getmaxRight() +
                            "\nnow for graphical illustration:"
            );

            double perc = res/getmaxRight();
            for (int i = 0; i< ((int)(perc*100));i++)
                System.out.print("*");
            System.out.print("OOO");
            for (int i = 0; i< (100-((int)(perc*100)));i++)
                System.out.print("*");

        }


        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~start of bayes~~~~~~~~~~~~~~~~~~~~~~~


//        HashMap<Conclusion, Double> probabilities = new HashMap<>();
//        HashMap<Conclusion, Double> newProbabilities = new HashMap<>();
//        for (
//                Conclusion con : conclusions) {
//            double prob = getProbability(con);
//            newProbabilities.put(con, prob);
//            prob *= uncertaintyModule.getApriorCertainty(con);
//            probabilities.put(con, prob);
//        }
//
//        double probOfAll = 0.0;
//        double newProbOfAll = 0.0;
//        for (
//                Conclusion con : conclusions) {
//            probOfAll += probabilities.get(con);
//            newProbOfAll += newProbabilities.get(con);
//        }
//
//        //        HashMap<Conclusion, Double> finalMap = new HashMap<>();
//        ArrayList<Pair<Conclusion, Double>> finalList = new ArrayList<>();
//        int j = 0;
//
//        for (
//                Conclusion con : conclusions) {
//            Double prob = probabilities.get(con) / probOfAll;
//            Double newProb = newProbabilities.get(con) / newProbOfAll;
//            finalList.add(new Pair<Conclusion, Double>(con, newProb));
////            finalMap.put(con, newProb);
//            if (prob > 0.00)
//                System.out.println(String.format("You may have voted for: " + con + " with probability of: ~" + "%.2f", prob));
//            j++;
//        }
//
//        Collections.sort(finalList, Comparator.comparingDouble(p -> -p.getValue()));
//
//        System.out.println("\nNext time consider voting for these: \n");
//
//        j = 0;
//        for (
//                Pair<Conclusion, Double> tmp : finalList) {
//            if (tmp.getValue() < 0.1 || tmp.getValue().isNaN()) {
//                break;
//            }
//            System.out.println(String.format(tmp.getKey().getName() + " with benefit to you according to your opinions: %.2f", tmp.getValue()));
//            System.out.print('\n');
//            for (int o = 0; o < (int) (tmp.getValue() * 50); o++)
//                System.out.print('*');
//            System.out.print('\n');
//            j++;
//            if (j > 2)
//                break;
//        }
//        if (j == 0.0)
//            System.out.println("\nActually, you shouldn't vote at all\n");

        //~~~~~~~~~~~~~~~end of bayes~~~~~~~~~~~~~~~~~~~~~~


//        int countChoices = 0;
//        for (Conclusion con : conclusions) {
//            Set<Predicate> tempPredicates = knowledgeBase.getPredicates(con);
//            int n = 0;
//            for (Predicate tmp : tempPredicates) {
//                if (dataBase.contains(tmp))
//                    if (dataBase.getAnswer(tmp).getAnswer() == 1) {
//                        n++;
//                    }
//            }
//            if (n == tempPredicates.size()) {
//                System.out.println("You should consider voting for: " + con);
//                countChoices++;
//            }
//        }
//
//        if (countChoices == 0)
//            System.out.println("You probably shouldnt vote at all.");

        //~~~~~~~~~~~~~~~~~~~~~~~~~~HOW implementation~~~~~~~~~~~~~~~~~~~~

//        System.out.println("===================================================");
//        System.out.println("");
//        System.out.println("Do you wish to ask HOW I came to this conclusion ?\n'how'|'yes' => yes, 'anything else' => no ");
//
//
//        final Scanner reader = new Scanner(System.in);
//        String userInput = reader.next();
//        if (userInput != null && userInput.length() > 0 &&
//                (userInput.toLowerCase().
//
//                        matches("how|yes|y") || StringUtils.isNumeric(userInput) ||
//                        userInput.toLowerCase().
//
//                                matches("[-+]?[0-9]*\\.?[0-9]+"))) {
//            if (userInput.toLowerCase().matches("HOW|how|h|yes|y")) {
//                doHow();
//            }
////        RulesKnowledgeBase knowBase = new RulesKnowledgeBase();
////        knowBase.getAllPredicates();
//        }


    }
}