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
import java.util.List;
/**
 *
 * @author Gydo Kosten
 */
public class HttpServer {
    String[] listenUrls = { "/ack", "/event.js", "/event" };
    String[] listenData = { "ack ok", "//event.js 3.0 by Gydo Kosten", "var event = 'alert();'" };
    
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
        System.out.println("HttpServer.setup(): done");
        
    }
    public void addURL(String url, String data){
        //listenUrls[0] = "/ack";
        //listenData[0] = "ack ok";
        
        System.out.println("HttpServer.addURL: done");
    }
    
    
    
    
    public void run() throws IOException { //beware runnable.run cant throw exception
        String input;
        //while(true){
        while(true){
        if((input = rec.readLine()) != null && input != null && input != "" && input.contains("GET")){
            //if(input.contains("GET")){
            //if(input.contains("GET")){
            System.out.println(input);
            send.println("HTTP/1.1 200 OK");
            send.println("");
            send.println("200");
            send.println("");
            //break;
            break;
            //}//if input contains
            //rec.close();
           }
        }//whiletrue
        //}//whiletrue 2
        //return 0;
    }
    
    public String parseRequest(String request){
        return request.split(" ")[1];
    }
    
    
    
    
    
}
