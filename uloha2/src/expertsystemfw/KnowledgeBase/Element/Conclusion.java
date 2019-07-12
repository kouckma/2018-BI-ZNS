/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expertsystemfw.KnowledgeBase.Element;

import java.util.Objects;

/**
 * This class represents a simple conclusion.
 * It contains only the name of the conclusion and overrides toString(), hashCode() and equals().
 * 
 * @author werneric
 */
public class Conclusion implements Element{
    private final String name;

    public Conclusion(String name) {
        this.name = name;
    }
    
    /**
     * Returns the name of the conclusion.
     * @return the name of the conclusion
     */
    @Override
    public String getName() {
        return this.name;
    }
    
    /**
     * Returns the name of the conclusion.
     * equivalent to getName()
     * 
     * @return the name of the conclusion
     */
    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Conclusion other = (Conclusion) obj;
        return Objects.equals(this.name, other.name);
    }
    
    
    
}
