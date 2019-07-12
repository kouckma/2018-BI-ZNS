/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expertsystemfw.DataBase;

import expertsystemfw.KnowledgeBase.Element.Predicate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author werneric
 */
public class DataBase implements DataBaseInterface {


    public Map<Predicate,Answer> database = new HashMap<>();

    @Override
    public void addAnswer(Answer answer) {
        database.put(answer.getPredicate(),answer);
//        throw new UnsupportedOperationException("Function addAnswer not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contains(Predicate predicate) {
        return database.containsKey(predicate);

//        throw new UnsupportedOperationException("Function contains not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Answer getAnswer(Predicate predicate) {
        return database.get(predicate);
//        throw new UnsupportedOperationException("Function getAnswer not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Answer> getAllAnswers() {
        return new ArrayList<Answer>(database.values());
//        throw new UnsupportedOperationException("Function getAllAnswers not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
