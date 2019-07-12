
package expertsystemfw.KnowledgeBase.SemanticNetwork;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author werneric
 */
public class SemanticNetwork {
    private Map<String, Node> nodes;
    private final Map<Relation, ArrayList<Edge>> edgesByRelation;

    public SemanticNetwork() {
        nodes = new HashMap<>();
        edgesByRelation = new HashMap<>();
    }

    public Map<String, Node> getNodes() {
        return nodes;
    }

    public void setNodes(Map<String, Node> nodes) {
        this.nodes = nodes;
    }

    public Map<Relation, ArrayList<Edge>> getEdgesByRelation() {
        return edgesByRelation;
    }
       
    public void addNode(Node node){
        if(node == null || node.getLabel() == null){
            throw new NullPointerException("Attempt to insert a null Node into the semantic network!");
        } 
        else if(nodes.containsKey(node.getLabel())) {
            throw new IllegalArgumentException("A node with this name is already in the semantic network!");
        } else {
            nodes.put(node.getLabel(), node);
        }
    }
    
    public void addEdge(Edge edge){
        if(edge == null || edge.getFrom() == null || edge.getTo() == null || edge.getRelation() == null){
            throw new NullPointerException("Attempt to insert a null Edge into semantic network!");
        }
        else if(nodes.get(edge.getFrom().getLabel()).getNeighbours().contains(edge.getTo()) || 
                nodes.get(edge.getTo().getLabel()).getNeighbours().contains(edge.getFrom())){
            throw new IllegalArgumentException("Attempt to create an edge loop between two nodes or duplicate a relation!");
        } else {
            ArrayList<Edge> newEdgeArray = edgesByRelation.get(edge.getRelation());
            
            if(newEdgeArray == null){
                newEdgeArray = new ArrayList<>();
            }
            newEdgeArray.add(edge);
            edgesByRelation.remove(edge.getRelation());
            edgesByRelation.put(edge.getRelation(), newEdgeArray);
            edge.getFrom().getEdges().add(edge);
        }
    }
        
    /*
    * @return returns true if the two specified nodes are in the specified relation, otherwise returns false
    *
    * @param node1 first node
    * @param node2 second node
    * @param relation which relation are the nodes being tested for
    * @param transitive if the nodes should be transitively tested for a relation
    */
    public boolean areNodesInRelation(Node node1, Node node2, Relation relation, boolean transitive){
        boolean answer = false;
        
        if(node1 == null || node2 == null || node1.getEdges() == null || node2.getEdges() == null){
            answer = false;
        } else {
            ArrayList<Edge> node1Edges = node1.getEdges();
            if(node1Edges == null){
                answer = false;
            } else {
                for (Edge n1e : node1Edges) {
                    if(n1e.getRelation() == relation && n1e.getTo().getLabel().equals(node2.getLabel())){
                        answer = true;
                        break;
                    }
                    else if(n1e.getRelation().isTransitive() && transitive){
                        answer = areNodesInRelation(n1e.getTo(), node2, relation, transitive);
                    } else {
                        answer = false;
                    }
                }
            }
        }
        return answer;
    }
    
    public void createBaseFromFile(String filePath){
        Path path = Paths.get(filePath);
        try {
            Files.lines(path).forEach(l -> {
                this.parseLine(l);
            });
        } catch (IOException ex) {
            Logger.getLogger(SemanticNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void createBaseFromFile(){
        String fs = File.separator;
        String filePath = System.getProperty("user.dir") + fs + "src" + fs + "expertsystemfw" + fs + "KnowledgeBase" + fs + "Files" + fs + "SemanticNetworkKnowledgeBase";
        createBaseFromFile(filePath);
    }
    
    private void parseLine(String line){
        String conclusion, relation, predicate, delimiter;
        
        line = line.trim();
        if(line.startsWith("#") || line.length() == 0){
            return;
        }
        
        if(line.split(",").length == 3){
            delimiter = ",";
        }
        else if (line.split(";").length == 3){
            delimiter = ";";
        } else {
            throw new IllegalArgumentException("Bad file format exception. "
                    + "There has to be exactly two delimiters of the same kind (either ',' or ';') on each line");
        }
        conclusion = line.split(delimiter)[0].trim();
        relation = line.split(delimiter)[1].trim();
        predicate = line.split(delimiter)[2].trim();
        
        if(nodes.containsKey(conclusion) && nodes.containsKey(predicate)){
            if(nodes.get(conclusion).getNeighbours().contains(nodes.get(predicate)) || 
               nodes.get(predicate).getNeighbours().contains(nodes.get(conclusion))){
                throw new IllegalArgumentException("This relation between specified nodes already exists!");
            } else {
                if(stringToRelation(relation) != null){
                    
                    this.addEdge(new Edge(nodes.get(conclusion), nodes.get(predicate), stringToRelation(relation)));
                } else {
                    throw new IllegalArgumentException("Non-existing relation name!"
                            + "Relation translation is NOT-case-sensitive, check spelling or add relation to Relation enum if desired.");
                }
                
            }
        } else {
            if(!nodes.containsKey(conclusion)){
                this.addNode(new Node(conclusion));
            }
            if(!nodes.containsKey(predicate)){
                this.addNode(new Node(predicate));
            }
            this.addEdge(new Edge(nodes.get(conclusion), nodes.get(predicate), this.stringToRelation(relation)));
        }
    }
    
    public Relation stringToRelation(String str){
        for (Relation rel : Relation.values()) {
            if(rel.toString().toLowerCase().equals(str.toLowerCase())){
                return rel;
            }
        }
        return null;
    }
    
    public void clear(){
        nodes.clear();
        edgesByRelation.clear();
    }
    
}
