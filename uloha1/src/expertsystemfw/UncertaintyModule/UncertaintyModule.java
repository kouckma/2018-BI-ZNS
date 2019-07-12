
package expertsystemfw.UncertaintyModule;

import expertsystemfw.KnowledgeBase.Element.Conclusion;
import expertsystemfw.KnowledgeBase.Element.Predicate;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.math.NumberUtils;

/**
 * An implementation of UncertaintyModule interface using nested HashMap.
 * 
 * @author werneric
 */
public final class UncertaintyModule implements UncertaintyModuleInterface{

    private final String filePath = "src" + File.separator + "expertsystemfw" + File.separator + "KnowledgeBase" + File.separator + "Files" + File.separator + "Uncertainty";

    public UncertaintyModule() {
        this.loadFile();
    }
    
    @Override
    public void loadFile() {
        Path path = Paths.get(filePath);
        
        try {
            Files.lines(path).forEach(l -> {
                this.parseLine(l);
            });
        } catch (IOException ex) {
            Logger.getLogger(UncertaintyModule.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Parses a single line from the file.
     * @param line a single line of the file.
     */
    private void parseLine(String line){
        throw new UnsupportedOperationException("Function addAnswer not supported yet.");
    }

    /**
     * Returns Double which represents the certainty for the key CONCLUSION-PREDICATE when present, otherwise returns 1.0 (100 %) as default
     * 
     * @param conclusion the specified conclusion.
     * @param predicate the specified predicate.
     * @return double value of certainty for the specified combination of conclusion and predicate.
     */
    @Override
    public Double getCertainty(Conclusion conclusion, Predicate predicate) {
        throw new UnsupportedOperationException("Function addAnswer not supported yet.");
        
    }

    /**
     * Adds new certainty for conclusion-predicate key
     * 
     * @param conclusion specified conclusion
     * @param predicate specified predicate
     * @param certainty Double value of certainty
     */
    @Override
    public void addCertainty(Conclusion conclusion, Predicate predicate, Double certainty) {
        throw new UnsupportedOperationException("Function addAnswer not supported yet.");
    }

    
    
}
