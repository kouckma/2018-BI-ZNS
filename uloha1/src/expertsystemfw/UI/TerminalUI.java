
package expertsystemfw.UI;

import expertsystemfw.DataBase.Answer;
import expertsystemfw.KnowledgeBase.Element.Conclusion;
import expertsystemfw.KnowledgeBase.Element.Predicate;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

import javafx.util.Pair;
import org.apache.commons.lang.StringUtils;

/**
 * @author werneric
 */
public class TerminalUI extends UIInterface {
    private final Scanner reader = new Scanner(System.in);

    @Override
    public Answer askUser(Predicate question) {
        System.out.println("How do you feel about: " + question + "?" + System.lineSeparator() +
                "Answer: ");

        String userInput = reader.next();
        if (userInput != null && userInput.length() > 0 &&
                (userInput.toLowerCase().matches("yes|y|no|n|idk|stop|why|how") || StringUtils.isNumeric(userInput) ||
                        userInput.toLowerCase().matches("[-+]?[0-9]*\\.?[0-9]+"))) {

            Double answer;
            if (userInput.toLowerCase().matches("yes|y")) {
                answer = 1.0;
            } else if (userInput.toLowerCase().matches("no|n")) {
                answer = 0.0;
            } else if (userInput.toLowerCase().matches("idk")) {
                answer = 0.5;
            } else if (userInput.toLowerCase().matches("stop")) {
                this.notifyStop();
                return null;
            } else if (userInput.toLowerCase().matches("why")) {
                this.notifyWhy();
                return null;
            } else if (userInput.toLowerCase().matches("how")) {
                this.notifyHow();
                return null;
            } else {
                answer = Double.valueOf(userInput);
            }
            return new Answer(question, answer);
        } else {
            System.err.println("Wrong answer! Again!");
            return askUser(question);
        }
    }

    @Override
    public void conclude(List<Pair<Conclusion, Double>> conclusions) {
        throw new UnsupportedOperationException("Conclusion not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void explainWhy(Conclusion conclusion) {
        throw new UnsupportedOperationException("ExplainWhy not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void explainHow(List<Answer> answers) {
        throw new UnsupportedOperationException("ExplainHow not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
