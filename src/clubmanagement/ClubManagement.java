/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clubmanagement;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author serper
 */
public class ClubManagement extends Application {
    
    @Override
    public void start(Stage primaryStage) {
    }
    /**
     * @param args login to the application
     */
    public static void main(String[] args) {
        //show the login frame
        new loginFrame().setVisible(true);
        //new DashboardFrame().setVisible(true);
    }
    
}
