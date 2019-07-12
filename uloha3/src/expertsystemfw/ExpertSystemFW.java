/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expertsystemfw;

import expertsystemfw.InferenceEngine.ForwardChainingIM;
import expertsystemfw.KnowledgeBase.FuzzyDatabase;
import expertsystemfw.KnowledgeBase.RulesKnowledgeBase;
import expertsystemfw.UI.TerminalUI;
import expertsystemfw.UI.UIInterface;
import expertsystemfw.DataBase.DataBase;
import expertsystemfw.InferenceEngine.BackwardChainingIM;
import expertsystemfw.InferenceEngine.InferenceEngineInterface;
import expertsystemfw.KnowledgeBase.FOLKnowledgeBase;
import expertsystemfw.UncertaintyModule.UncertaintyModule;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author werneric
 */
public class ExpertSystemFW  {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        InferenceEngineInterface ie;
        UIInterface uii = new TerminalUI();
        ie = new ForwardChainingIM(new RulesKnowledgeBase(), uii, new UncertaintyModule(), new DataBase(),new FuzzyDatabase());
        uii.addNewObserver(ie);
        ie.startInference();
        System.exit(0);
    }
    
}
