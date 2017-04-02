/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectpwn;

import java.io.IOException;

/**
 *
 * @author root
 */
public class ProjectPwn {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        HttpServer projectServer = new HttpServer();
            Thread projectServerThread = new Thread(projectServer);
            projectServerThread.start(); //starts the server in new thread
            System.out.println("Starting httpserver.setup()");
            System.out.println("Starting httpserver.addURL()");
            projectServer.addURL("/ack","Acknowledgement complete");
            projectServer.addURL("/dist/projectpwn", "<html><h1>Welcome to ProjectPwn</h1><br><p>This is a demo page for the built-in web server.</p></html>");
            projectServer.addURL("/asdf", "LEL, asdf. haha.");
            if(projectServer.urlExists("/asdf")){
                System.out.println("Removing Url"); //removes the URL only is it exists, to create a 404 again
                projectServer.removeUrl("/asdf");
            }
            projectServer.addURL("/threadid", "HTTP Server current threadid: " + Long.toString(projectServerThread.getId()) );
            
            
        
        
    }
    
}
