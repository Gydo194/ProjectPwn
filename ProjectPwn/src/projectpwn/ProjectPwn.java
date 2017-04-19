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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import javax.swing.JOptionPane;

public class ProjectPwn {

    /**
     * @param args the command line arguments
     */
    
    public static String localIp = "127.0.0.1";
    public static String port = "3500";
    public static final Gui projectGui = new Gui(); //public static final
    public static final HttpServer projectServer = new HttpServer();
    public static void main(String[] args) {
        // TODO code application logic here
        ConfRead config = new ConfRead();
        //System.out.println("+++++++++++++++++++++++++++++++++++++++CONFIG FROMJAR" + config.fromJar("/modules/playsound.js"));
        //System.out.println("+++++++++------======++++++++   sout" + config.listDb().get(0)     );
        
        
        
       
        try{
        localIp = InetAddress.getLocalHost().getHostAddress();
            System.out.println("IP detected: " + localIp);
            projectGui.ConfigIpTextField.setText(localIp);
        }
        catch(UnknownHostException e){
            throwError(e.getLocalizedMessage());
        }
        
        if(args.length > 0){
            port = args[0];
        }
     
        //*****************************************************************************************
        //testing area
        
        
        ModuleViewHandler moduleHandler = new ModuleViewHandler();
        
        moduleHandler.setTree();
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        //end testing area
        //*******************************************************************************************
        
        
        //Start the HTTP Server core
        //final HttpServer projectServer = new HttpServer();
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
                projectServer.addURL("/event", event, new HttpServer.NO_CALLBACK() );
                }
            });
            
            
        projectServer.addURL("/event.js", getHookScript(localIp, port), new HttpServer.NO_CALLBACK() ) ;   
        projectServer.addURL("/ack","Acknowledgement complete", new appendDetails() );
        projectServer.addURL("/dist/projectpwn", "<html><script src='/event.js'></script><h1>Welcome to ProjectPwn</h1><br><p>This is a demo page for the built-in web server.</p></html>", new HttpServer.NO_CALLBACK());
        projectServer.addURL("/devs", "<html><p>Welcome to ProjectPwn's secret dev page of</p><br><h1>Gydo194 and weshuiz13</h1><br><p>The main developers and testers of Project Pwn.</p><br><a href='https://gydo194.github.io/projectpwn/devs'>Click here for more info</a></html>", new HttpServer.NO_CALLBACK());
        projectServer.addURL("/event", "var event = ''", new HttpServer.NO_CALLBACK());
        projectServer.addURL("/threadid", "HTTP Server current threadid: " + Long.toString(projectServerThread.getId()), new HttpServer.NO_CALLBACK() );
        projectServer.addURL("/post", "post", new UpdateText() );
        projectServer.addURL("/demo", "<html><script src='/event.js'></script><h1>XSS demo</h1><br><p>This is a demo page for the built-in web server.</p></html>", new HttpServer.NO_CALLBACK());
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
    public static void throwError(String message){
        JOptionPane.showMessageDialog(null, message);
        
    }
    public static void stopExecuting() {
        if(projectServer.urlExists("/event")){
            projectServer.removeUrl("/event");
            projectServer.addURL("/event", "var event = ''", new HttpServer.NO_CALLBACK() ); 
            projectServer.printArrays();
            
        }
    }
    public void setCodeAreaText(String text){
        projectGui.CustomCodeTextArea.setText(text);
    }
    
    
    public static void resetEventFile() {
        projectServer.removeUrl("/event.js");
        projectServer.addURL("/event.js", getHookScript(localIp, port), new HttpServer.NO_CALLBACK() ) ;   
    }
    
    
    
    
    
    
    static class UpdateText implements Callable<Boolean> { //class implementing the call back for post data
        public Boolean call() {
            System.out.println("UpdateText.call");
            if(projectServer.queryString != ""){
            setText(projectServer.queryString);
            }
            return true;
        }
    }
    
    static class appendDetails implements Callable<Boolean> {
        public Boolean call() {
            String currentText = projectGui.DetailsLabel.getText().split("</html>")[0];
            String newText = currentText + projectServer.clientAddress.getHostAddress() + "(" + projectServer.clientAddress.getHostName() + ") sent an ACK." + "<br></html>";
            System.out.println("newText = " + newText);
            projectGui.DetailsLabel.setText(newText);
            stopExecuting(); // stop the command execution, the browser has completed executing it.
            //set statuc icon/light in execute tab to inform the user that the command has been executed by a browser.
            return true;
        }
        
    }
    
    public static String getHookScript(String ip, String port) {
         //read the Event.js hook file
        
        String hook = "";
        System.out.println("getHookScript(): IP:" + ip + ", port:" +port);
        try{
            InputStream eventFileStream = ProjectPwn.class.getResourceAsStream("/event.js");
            BufferedReader eventFile = new BufferedReader(new InputStreamReader(eventFileStream));
            String line;
            while((line = eventFile.readLine()) != null ){
            if(line.isEmpty()){
                break;
            }
            //System.out.println(line.replace("<HOST>", "var host = " + "\"" + ip + ":" + port + "\""));
            hook += line.replace("<HOST>", "var host = " + "\"" + ip + ":" + port + "\"") + "\n";
            }
            
        }
        catch(IOException e){
            ProjectPwn.throwError(e.getLocalizedMessage());
            e.printStackTrace();
            System.exit(-1);
        }
        //end reader
        return hook;
    }
    
    
    
    
    
    
    
}
/*

class NO_CALLBACKS implements Callable<Boolean> {
        public NO_CALLBACKS() {
            System.out.println("NO_CALLBACK: HTTP server callback ignored");
        }
        public Boolean call() {
            System.out.println("NO_CALLBACKS_MAIN_CLASS: call back ignored");
            return true;
        }
    }

class UpdateData implements Callable<Boolean> {
    public UpdateData() {
        System.out.println("UpdateData()");
    }
    public Boolean call() {
        
        
        //(new Runnable(){public void run(){}).run();
        
        //ProjectPwn.throwError(ProjectPwn.projectServer.queryString);
    return true;    
    }
    
}
*/


