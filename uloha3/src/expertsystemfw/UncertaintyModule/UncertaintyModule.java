
package expertsystemfw.UncertaintyModule;

import expertsystemfw.KnowledgeBase.Element.Conclusion;
import expertsystemfw.KnowledgeBase.Element.Predicate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javafx.util.Pair;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.math.NumberUtils;

/**
 * An implementation of UncertaintyModule interface using nested HashMap.
 *
 * @author werneric
 */
public final class UncertaintyModule implements UncertaintyModuleInterface {

    private final String filePath = "src" + File.separator + "expertsystemfw" + File.separator + "KnowledgeBase" + File.separator + "Files" + File.separator + "Uncertainty";
    public HashMap<String, Double> apriorP;
    public Map<Pair<String,String>,Double> conditionalP;

    public UncertaintyModule() {
        apriorP = new HashMap<>();
        conditionalP = new HashMap<>();
        this.loadFile();
    }

    @Override
    public void loadFile() {
        Path path = Paths.get(filePath);

        final AtomicBoolean conditionedProbLoaded = new AtomicBoolean(false);

        try {
            Files.lines(path).forEach(l -> {
                if (l.equals(";")) {
                    conditionedProbLoaded.set(true);
                    return;
                }
                if (conditionedProbLoaded.get()) {
                    parseApriorProb(l);
                } else {
                    parseConditionalProb(l);
                }


            });
        } catch (IOException ex) {
            Logger.getLogger(UncertaintyModule.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private void parseApriorProb(String line) {
        String conclusion = line.split(" ")[0].trim();
        Double probability = Double.valueOf(line.split(" ")[1].trim());
//            System.out.println("ukladam apriori " + conclusion + " " + probability);
        apriorP.put(conclusion, probability);
    }


    private void parseConditionalProb(String line) {
        String conclusion = line.split(" ")[0].trim();
        String predicate = line.split(" ")[1].trim();
        Double probability = Double.valueOf(line.split(" ")[2].trim());
//        System.out.println("ukladam conditioned " + predicate+" "+conclusion + " " + probability);
        conditionalP.put(new Pair<>(conclusion, predicate), probability);
    }


    /**
     * Parses a single line from the file.
     *
     * @param line a single line of the file.
     */
    private void parseLine(String line) {

        throw new UnsupportedOperationException("Function addAnswer not supported yet.");
    }

    /**
     * Returns Double which represents the certainty for the key CONCLUSION-PREDICATE when present, otherwise returns 1.0 (100 %) as default
     *
     * @param conclusion the specified conclusion.
     * @param predicate  the specified predicate.
     * @return double value of certainty for the specified combination of conclusion and predicate.
     */
    @Override
    public Double getCertainty(Conclusion conclusion, Predicate predicate) {
        return conditionalP.get(new Pair<>(conclusion.getName(), predicate.getName()));
    }
    public Double getApriorCertainty(Conclusion conclusion) {
//        System.out.println(ap);
        return apriorP.get(conclusion.getName());
    }

    /**
     * Adds new certainty for conclusion-predicate key
     *
     * @param conclusion specified conclusion
     * @param predicate  specified predicate
     * @param certainty  Double value of certainty
     */
    @Override
    public void addCertainty(Conclusion conclusion, Predicate predicate, Double certainty) {
        throw new UnsupportedOperationException("Function addAnswer not supported yet.");
    }


}
