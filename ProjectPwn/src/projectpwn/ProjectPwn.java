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
        try {
        projectServer.setup();
        projectServer.addListener("/ack","Acknowledgement complete");
        }
        catch(IOException e){
            System.out.println("IOException.");
            System.exit(-1);
        }
    }
    
}
