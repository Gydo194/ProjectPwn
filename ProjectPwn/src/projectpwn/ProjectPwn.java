/*
    Project Pwn main class,
    Gydo Kosten, April 2, 2017.
    





*/
package projectpwn;

//import java.io.IOException;
//import javax.swing.*;

/**
 *
 * @author Gydo Kosten
 *
 */

import java.lang.reflect.*;
import java.util.concurrent.Callable;

public class ProjectPwn {

    /**
     * @param args the command line arguments
     */
    
    
    public static final Gui projectGui = new Gui();
    public static void main(String[] args) {
        // TODO code application logic here
        
      
        //Start the HTTP Server core
        final HttpServer projectServer = new HttpServer();
        Thread projectServerThread = new Thread(projectServer);
        projectServerThread.start(); //starts the server in new thread
            
        //start the GUI   
        
        projectGui.setVisible(true);
        projectGui.DatabaseLabel.setText("<html></html>"); //sets the database label text
        projectGui.ExecuteCustomCodeButton.addActionListener(new java.awt.event.ActionListener() { //add execute button listener in main class to be able to use the projectServer(HttpServer) object.
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(projectServer.urlExists("/event")){
                projectServer.removeUrl("/event");
                }
                String event = "var event = '" + projectGui.CustomCodeTextArea.getText().replace("\n", "").replace("'", "\\'") + "';";
                //replaces newlines with null to avoid unterminated string literal error in browser,
                //and replaces ' with \' so you can use single quotes like normal.
                //And then encapsulates that into a variable for event.js to handle the rest.
                System.out.println("Executing: " + event);
                projectServer.addURL("/event", event, null);
                }
            });
            
            
            
        projectServer.addURL("/ack","Acknowledgement complete", null);
        projectServer.addURL("/dist/projectpwn", "<html><h1>Welcome to ProjectPwn</h1><br><p>This is a demo page for the built-in web server.</p></html>", null);
        projectServer.addURL("/gydo194", "<html><p>Welcome to ProjectPwn's secret dev page of</p><br><h1>Gydo194 or theWiz()</h1></html>", null);
        projectServer.addURL("/event", "var event = ''", null);
        projectServer.addURL("/threadid", "HTTP Server current threadid: " + Long.toString(projectServerThread.getId()), null);
        projectServer.addURL("/post", "post", null  );
        projectServer.printArrays();
        /*
        while(true){
            try{
            projectGui.DatabaseLabel.setText(projectServer.testText);
            Thread.sleep(50);
            }
            catch(InterruptedException e){
                System.out.print("Bla. interrupted.");
            }
        }
*/
            
            
        
        
    }
    
    
    public static void setText(String text){
        String current_text = projectGui.DatabaseLabel.getText().split("</html>")[0]; // <html>
        projectGui.DatabaseLabel.setText(current_text + text + "<br></html>");
    }
    public static void clearDatabase() {
        projectGui.DatabaseLabel.setText("<html></html>");
    }
}