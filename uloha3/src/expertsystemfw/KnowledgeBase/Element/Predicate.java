/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expertsystemfw.KnowledgeBase.Element;

import java.util.Objects;

/**
 * This class represents a simple predicate.
 * It contains only the name of the predicate and overrides toString(), hashCode() and equals().
 * @author werneric
 */
public class Predicate implements Element{
    protected String name;

    public Predicate(String name) {
        this.name = name;
    }
    
    /**
     * Returns the name of the predicate.
     * @return the name (only the name) of the predicate
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Equivalent to getName().
     * 
     * @return the name (only the name) of the predicate
     */
    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Predicate other = (Predicate) obj;
        return Objects.equals(this.name, other.name);
    }
    
    
    
}
