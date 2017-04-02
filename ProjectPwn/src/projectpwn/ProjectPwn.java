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
 */
public class ProjectPwn {

    /**
     * @param args the command line arguments
     */
   
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        //Start the HTTP Server core
        HttpServer projectServer = new HttpServer();
        Thread projectServerThread = new Thread(projectServer);
        projectServerThread.start(); //starts the server in new thread
            
        //start the GUI   
        Gui projectGui = new Gui();
        projectGui.setVisible(true);
        projectGui.DatabaseLabel.setText("set from code"); //sets the database label text
        projectGui.ExecuteCustomCodeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(projectServer.urlExists("/event")){
                projectServer.removeUrl("/event");
                }
                projectServer.addURL("/event", projectGui.CustomCodeTextArea.getText());
                }
            });
            
            
            
        projectServer.addURL("/ack","Acknowledgement complete");
        projectServer.addURL("/dist/projectpwn", "<html><h1>Welcome to ProjectPwn</h1><br><p>This is a demo page for the built-in web server.</p></html>");
        projectServer.addURL("/asdf", "LEL, asdf. haha.");
        if(projectServer.urlExists("/asdf")){
            System.out.println("Removing Url"); //removes the URL only if it exists, to create a 404 again
            projectServer.removeUrl("/asdf");
        }
        projectServer.addURL("/threadid", "HTTP Server current threadid: " + Long.toString(projectServerThread.getId()) );
        projectServer.addURL("/404", "http 200 ok.");
        projectServer.printArrays();
            
            
        
        
    }
    
    
}
