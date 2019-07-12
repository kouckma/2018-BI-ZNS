
package expertsystemfw.KnowledgeBase;

import expertsystemfw.KnowledgeBase.Element.Conclusion;
import expertsystemfw.KnowledgeBase.Element.Predicate;
import expertsystemfw.KnowledgeBase.Rules.Rules;
import java.util.HashSet;
import java.util.Set;
import net.sf.tweety.logics.pl.syntax.Proposition;
import net.sf.tweety.logics.pl.syntax.Tautology;

/**
 *
 * @author werneric
 */
public class RulesKnowledgeBase implements KnowledgeBaseInterface{
    private final Rules knowledgeBase = new Rules();

    public RulesKnowledgeBase() {
        knowledgeBase.createRulesFromFile();
    }

    @Override
    public Set<Conclusion> getAllConclusions() {
        Set<Conclusion> conclusions = new HashSet<>();
        knowledgeBase.getAllConclusions().forEach(c -> {
            conclusions.add(new Conclusion(c));
        });
        return conclusions;
    }

    @Override
    public Set<Predicate> getAllPredicates() {
        Set<Predicate> predicates = new HashSet<>();
        knowledgeBase.getAllPredicates().forEach(p -> {
            predicates.add(new Predicate(p));
        });
        return predicates;
    }

    @Override
    public Set<Predicate> getPredicates(Conclusion conclusion) {
        Set<Predicate> predicates = new HashSet<>();
        knowledgeBase.getPredicates(conclusion.getName()).forEach(p -> {
            predicates.add(new Predicate(p));
        });
        return predicates;
    }

    @Override
    public void loadFile() {
        knowledgeBase.createRulesFromFile();
    }

    @Override
    public boolean query(Predicate predicate, Conclusion conclusion) {
        return knowledgeBase.getUniformProbability(conclusion.getName()) < 
               knowledgeBase.getFormula(conclusion.getName()).replace(new Proposition(predicate.getName()), new Tautology(), 1).getUniformProbability().doubleValue();
    }
    
}
