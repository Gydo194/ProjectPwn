/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectpwn;

import javax.swing.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
/**
 *
 * @author Gydo Kosten
 */
public class HttpServer {
    String[][] listeners;
    ServerSocket server;
    int port = 3500;
    Socket http;
    BufferedReader rec;
    PrintWriter send;
    public void setup() throws IOException {
        server = new ServerSocket(3500);
        http = server.accept();
        send = new PrintWriter(http.getOutputStream(), true);
        rec = new BufferedReader(new InputStreamReader(http.getInputStream()));
        
        
    }
    public void addURL(String url, String data){
        listeners[listeners.length + 1][0] = url;
        listeners[listeners.length + 1][1] = data;          
    }
    
    
    
    
    public void run() throws IOException {
        String input;
        while(true){
        while((input = rec.readLine()) != null){  
         //process the request
         System.out.println("Received: " + input);
         //Parse GET request
         String requestURL = this.parseRequest(input);
         //for i in listeners.length
         //check if the requested url exists in the listeners array,
         //and then output the data stored for that url,
         //otherwise return a http 404.
         
         //remember this runs once for every HTTP header sent, so we have to break;.
         break;
        } //while input
        } //whiletrue
        //return 0;
    }
    
    public String parseRequest(String request){
        return request.split(" ")[1];
    }
    
    
}
