/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expertsystemfw.KnowledgeBase;

import expertsystemfw.KnowledgeBase.Element.Conclusion;
import expertsystemfw.KnowledgeBase.Element.Predicate;
import expertsystemfw.KnowledgeBase.FirstOrderLogic.FirstOrderLogic;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author werneric
 */
public class FOLKnowledgeBase implements KnowledgeBaseInterface{

    private final FirstOrderLogic knowledgeBase = new FirstOrderLogic();

    public FOLKnowledgeBase() {
        knowledgeBase.createFolBeliefSetFromFile();
    }    
    
    @Override
    public Set<Conclusion> getAllConclusions() {
        Set<Conclusion> newSet = new HashSet<>();
        knowledgeBase.getConstants().forEach(c -> {
            newSet.add(new Conclusion(c.get()));
        });
        return newSet;
    }

    @Override
    public Set<Predicate> getAllPredicates() {
        Set<Predicate> newSet = new HashSet<>();
        knowledgeBase.getPredicates().forEach(p -> {
            newSet.add(new Predicate(p.getName()));
        });
        return newSet;
    }

    @Override
    public Set<Predicate> getPredicates(Conclusion conclusion) {
        Set<Predicate> newSet = new HashSet<>();
        knowledgeBase.getPredicates().forEach(p -> {
            if(knowledgeBase.queryKnowledgeBase(p.getName() + "(" + conclusion.getName() + ")") == true){
                newSet.add(new Predicate(p.getName()));
            }
        });
        return newSet;
    }

    @Override
    public void loadFile() {
        knowledgeBase.createFolBeliefSetFromFile();
    }

    @Override
    public boolean query(Predicate predicate, Conclusion conclusion) {
        return knowledgeBase.queryKnowledgeBase(predicate.getName() + "(" + conclusion.getName() + ")");
    }
    
}
