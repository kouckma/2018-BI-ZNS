
package expertsystemfw.KnowledgeBase.Rules;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.tweety.commons.Formula;
import net.sf.tweety.commons.ParserException;
import net.sf.tweety.logics.pl.parser.PlParser;
import net.sf.tweety.logics.pl.syntax.Contradiction;
import net.sf.tweety.logics.pl.syntax.Proposition;
import net.sf.tweety.logics.pl.syntax.PropositionalFormula;
import net.sf.tweety.logics.pl.syntax.Tautology;

/**
 *
 * @author werneric
 */
public class Rules {
    private final Map<String, PropositionalFormula> formulas = new HashMap<>();
    private final PlParser plParser = new PlParser();
    
    public void createRulesFromFile(String filePath){
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                parseLine(line.trim());
            }
        } catch (IOException ex) {
            Logger.getLogger(Rules.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void createRulesFromFile(){
        String fs = File.separator;
        String filePath = System.getProperty("user.dir") + fs + "src" + fs + "expertsystemfw" + fs + "KnowledgeBase" + fs + "Files" + fs + "RulesKnowledgeBase";
        this.createRulesFromFile(filePath);
    }
    
    public boolean parseLine(String line){
        if(!line.startsWith("IF ") || !line.contains(" THEN ")){
            System.err.println("Line doesn't start with \"IF \" or doesn't contain \" THEN \" clauses (separated by spaces).");
            return false;
        }
        
        String conclusion = line.split("IF ")[1].split(" THEN")[0].trim();
        String formula = line.split("THEN ")[1].trim();
        
        saveFormula(conclusion, formula);
                
        return true;
    }
    
    public boolean saveFormula(String conclusion, String formula){
        Formula newFormula;
        try {
            newFormula = plParser.parseFormula(formula);
        } catch (IOException | ParserException ex) {
            Logger.getLogger(Rules.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        formulas.put(conclusion, (PropositionalFormula)newFormula);
        return true;
    }
    
    public PropositionalFormula getFormula(String conclusionName){
        return formulas.get(conclusionName);
    }
    
    public Set<String> getAllConclusions(){
        return new HashSet<>(formulas.keySet());
    }
    
    public double getUniformProbability(String conclusion){
        return formulas.get(conclusion).getUniformProbability().doubleValue();
    }
    
    public Set<String> getPredicates(String conclusion){
        Set<String> predicates = new HashSet<>();
        formulas.get(conclusion).getAtoms().forEach(a -> {
            predicates.add(a.toString());  
        });
        return predicates;
    }
    
    public Set<String> getRelevantPredicates(){
        Set<String> relevantPredicates = new HashSet<>();
        this.getAllConclusions().forEach(a -> {
            if(formulas.get(a).getUniformProbability().doubleValue() > 0){
                relevantPredicates.add(a);
            }
        });
        return relevantPredicates;
    }
    
    public Set<String> getAllPredicates(){
        Set<String> conclusions = new HashSet<>();
        
        formulas.keySet().forEach(k -> {
            formulas.get(k).getAtoms().forEach(a -> {
                conclusions.add(a.getName());
            });
        });
        
        return conclusions;
    }
    
    public void setPredicateToTrue(String predicate){
        for(String k : formulas.keySet()){
            int count = formulas.get(k).numberOfOccurrences(new Proposition(predicate));
            
            for (int i = 0; i < count; i++) {
                formulas.put(k, formulas.get(k).replace(new Proposition(predicate), new Tautology(), 1));
            } 
        }
    }
    
    public void setPredicateToFalse(String predicate){
        for(String k : formulas.keySet()){
            int count = formulas.get(k).numberOfOccurrences(new Proposition(predicate));
            
            for (int i = 0; i < count; i++) {
                formulas.put(k, formulas.get(k).replace(new Proposition(predicate), new Contradiction(), 1));
            } 
        }
    }
}
