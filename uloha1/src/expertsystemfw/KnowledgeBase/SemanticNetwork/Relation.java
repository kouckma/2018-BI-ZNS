
package expertsystemfw.KnowledgeBase.SemanticNetwork;

/**
 *
 * @author werneric
 */
public enum Relation {
    is_a("is a", true),
    a_kind_of("is a subclass of", true),
    has_property("is", true),
    does("", false);
    
    private final String translation;
    private final boolean transitive;

    Relation(String translation, boolean transitive) {
        this.translation = translation;
        this.transitive = transitive;
    }
    
    public boolean isTransitive(){
        return this.transitive;
    }
    
    public String translation(){
        return this.translation;
    }
}
