/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expertsystemfw.KnowledgeBase;

import expertsystemfw.KnowledgeBase.Element.Conclusion;
import expertsystemfw.KnowledgeBase.Element.Predicate;
import expertsystemfw.KnowledgeBase.Element.SemPredicate;
import expertsystemfw.KnowledgeBase.SemanticNetwork.Node;
import expertsystemfw.KnowledgeBase.SemanticNetwork.SemanticNetwork;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author werneric
 */
public class SemanticKnowledgeBase implements KnowledgeBaseInterface {
    private final SemanticNetwork knowledgeBase = new SemanticNetwork();

    public SemanticKnowledgeBase() {
        knowledgeBase.createBaseFromFile();
    }   
    
    @Override
    public void loadFile() {
        knowledgeBase.createBaseFromFile();
    }

    @Override
    public Set<Conclusion> getAllConclusions() {
        Set<Node> nonConclusions = new HashSet<>();
        Set<Conclusion> conclusions = new HashSet<>();
        
        knowledgeBase.getNodes().forEach((k,v) -> {
            nonConclusions.addAll(v.getNeighbours());
        });
        
        knowledgeBase.getNodes().forEach((k,v) -> {
            if(!nonConclusions.contains(v)){
                conclusions.add(new Conclusion(v.getLabel()));
            }
        });
        
        return conclusions;
    }

    @Override
    public Set<Predicate> getAllPredicates() {
        Set<Predicate> nonConclusions = new HashSet<>();
        
        knowledgeBase.getEdgesByRelation().forEach((k,v) -> {
            v.forEach(e -> {
                nonConclusions.add(new SemPredicate(e.getTo().getLabel(), e.getRelation()));
            });
        });
        
        return nonConclusions;
    }

    @Override
    public Set<Predicate> getPredicates(Conclusion conclusion) {
        Set<Predicate> predicates = new HashSet<>();
        
        knowledgeBase.getEdgesByRelation().forEach((k,v) -> {
            v.forEach(e -> {
                if(e.getFrom().getLabel().equalsIgnoreCase(conclusion.getName())){
                    predicates.add(new SemPredicate(e.getTo().getLabel(), e.getRelation()));
                }
            });
        });
        
        return predicates;
    }

    @Override
    public boolean query(Predicate predicate, Conclusion conclusion) {
        String relation = ((SemPredicate)predicate).getRelation().toString();
        String onlyPred = predicate.getName();
        
        return knowledgeBase.areNodesInRelation(knowledgeBase.getNodes().get(conclusion.getName()), 
                                                knowledgeBase.getNodes().get(onlyPred), 
                                                knowledgeBase.stringToRelation(relation), true);
    }
    
}
