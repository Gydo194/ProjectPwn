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
        HttpServer projectServer = new HttpServer();
            Thread projectServerThread = new Thread(projectServer);
            projectServerThread.start(); //starts the server in new thread
            System.out.println("Starting httpserver.setup()");
            System.out.println("Starting httpserver.addURL()");
            projectServer.addURL("/ack","Acknowledgement complete");
            projectServer.addURL("/dist/projectpwn", "<html><h1>Welcome to ProjectPwn</h1><br><p>This is a demo page for the built-in web server.</p></html>");
            projectServer.addURL("/asdf", "LEL, asdf. haha.");
            if(projectServer.urlExists("/asdf")){
               System.out.println("Removing Url"); //removes the URL only if it exists, to create a 404 again
                projectServer.removeUrl("/asdf");
            }
            projectServer.addURL("/threadid", "HTTP Server current threadid: " + Long.toString(projectServerThread.getId()) );
            //test adding URLS
            //JFrame addFrame = new JFrame();
            //String UrlToAdd = JOptionPane.showInputDialog(null,"Add new URL to the server");
            //String DataToAdd = JOptionPane.showInputDialog(null,"Add return data for URL " + UrlToAdd);
            //projectServer.addURL(UrlToAdd,DataToAdd);
            projectServer.printArrays();
            
            
        
        
    }
    
}
