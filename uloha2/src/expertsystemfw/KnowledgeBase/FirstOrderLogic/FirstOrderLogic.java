
package expertsystemfw.KnowledgeBase.FirstOrderLogic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import net.sf.tweety.commons.ParserException;
import net.sf.tweety.logics.commons.syntax.Constant;
import net.sf.tweety.logics.commons.syntax.Predicate;
import net.sf.tweety.logics.commons.syntax.Sort;
import net.sf.tweety.logics.fol.FolBeliefSet;
import net.sf.tweety.logics.fol.parser.FolParser;
import net.sf.tweety.logics.fol.prover.EProver;
import net.sf.tweety.logics.fol.prover.FolTheoremProver;
import net.sf.tweety.logics.fol.prover.Prover9;
import net.sf.tweety.logics.fol.syntax.FolFormula;
import net.sf.tweety.logics.fol.syntax.FolSignature;
import net.sf.tweety.logics.fol.writer.FolWriter;
import net.sf.tweety.logics.fol.writer.TptpWriter;
import org.apache.commons.lang.SystemUtils;

/**
 *
 * @author werneric
 */
public class FirstOrderLogic {
    private FolBeliefSet folBeliefSet = new FolBeliefSet();
    private final FolParser folParser = new FolParser();
    private final FolWriter folWriter = new TptpWriter();
    private final FolTheoremProver folProver;
    private FolSignature folSignature = new FolSignature();
    
    public FirstOrderLogic() {
        String fs = File.separator;
        if(SystemUtils.IS_OS_WINDOWS){
            FolTheoremProver.setDefaultProver(new EProver(System.getProperty("user.dir") + fs + "bin" + fs + "eprover.exe"));
        } else {
            FolTheoremProver.setDefaultProver(new EProver(System.getProperty("user.dir") + fs + "bin" + fs + "eprover"));
        }
        this.folProver = FolTheoremProver.getDefaultProver();
    }
    
    public Sort addSortSignature(String sortName){
        Sort newSort = new Sort(sortName);
        folSignature.add(newSort);
        return newSort;
    }
    
    public void addConstantSignature(String constName, Sort sortForConstant ){
        Constant newConst = new Constant(constName, sortForConstant);
        folSignature.add(newConst);
    }
    
    public void addAllConstantSignatures(ArrayList<String> listOfConsts, Sort sortForConstants){
        listOfConsts.forEach(s -> {
            Constant newConst = new Constant(s);
            folSignature.add(newConst);
        });
    }
    
    public void addPredicateSignature(String predicateName, ArrayList<Sort> listOfArguments){
        Predicate newPredicate = new Predicate(predicateName, listOfArguments);
        folSignature.add(newPredicate);
    }
    
    public void addFormula(String formula){
        folParser.setSignature(folSignature);
        try {
            FolFormula newFormula = (FolFormula)folParser.parseFormula(formula);
            folBeliefSet.add(newFormula);
        } catch (IOException ex) {
            System.err.println("Error while reading the string.");
        } catch (ParserException ex) {
            System.err.println("Error while parsing the string formula");
        }        
    }  
    
    public boolean queryKnowledgeBase(String query){
        try {
            return folProver.query(folBeliefSet, (FolFormula)folParser.parseFormula(query));
        } catch (IOException ex) {
            System.err.println("Error while reading the string.");
        } catch (ParserException ex) {
            System.err.println("Error while parsing the string query");
        }
        return false;
    }
    
    public void createFolBeliefSetFromString(String s){
        try{
            folBeliefSet = folParser.parseBeliefBase(s);
            folSignature = (FolSignature)folBeliefSet.getSignature();
        } catch (IOException e) {
            System.err.println("Syntax error while creating the knowledge base. Creating an empty base instead.");
            folBeliefSet = new FolBeliefSet();
        }
    }
    
    public void createFolBeliefSetFromFile(String filePath){
        try {
            this.folBeliefSet = folParser.parseBeliefBaseFromFile(filePath);
            folSignature = (FolSignature)folBeliefSet.getSignature();
        } catch (IOException e) {
            System.err.println("Error while reading the file at: " + filePath + '\n' + 
                               "The file is either missing or there was an (syntax) error while reading it." + '\n' + 
                               "Creating an empty knowledge base instead.");
            folBeliefSet = new FolBeliefSet();
        }
    }
    
    public void createFolBeliefSetFromFile(){
        String fs = File.separator;
        String filePath = System.getProperty("user.dir") + fs + "src" + fs + "expertsystemfw" + fs + "KnowledgeBase" + fs + "Files" + fs + "FOLKnowledgeBase";
        this.createFolBeliefSetFromFile(filePath);
    }

    public FolBeliefSet getFolBeliefSet() {
        return this.folBeliefSet;
    }
    
    public Set<Sort> getSorts(){
        return folSignature.getSorts();
    }
    
    public Sort getSort(String sortName){
        return folSignature.getSort(sortName);
    }
    
    public Set<Constant> getConstants(){
        return folSignature.getConstants();
    }
    
    public Constant getConstant(String constantName){
        return folSignature.getConstant(constantName);
    }
    
    public Set<Predicate> getPredicates(){
        return folSignature.getPredicates();
    }
    
    public Predicate getPredicate(String predicateName){
        return folSignature.getPredicate(predicateName);
    }
    
    public void printBeliefSet(){
        try{
            folWriter.printBase(folBeliefSet);
        } catch (IOException e) {
            System.err.println("Error during reading/printing the knowledge base.");
        }
    }
    
}
