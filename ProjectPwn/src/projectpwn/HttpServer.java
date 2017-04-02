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
import java.util.ArrayList;
/**
 *
 * @author Gydo Kosten
 */
public class HttpServer implements Runnable { //implements runnable to be able to create a new thread
    //String[] listenUrls = new String[100]; // array exception, length is fixed at 100, addUrl will try to loop it at 101
    //String[] listenData = new String[100];
    ArrayList<String> Urls = new ArrayList<String>();
    ArrayList<String> Data = new ArrayList<String>();
   
    
    
    
    public int addURL(String url, String data){
       System.out.println("Starting AddURL");
       if(Urls.indexOf(url) == -1 && Data.indexOf(data) == -1){ //checks the ArrayList for the item being added, if it's not in the array, this returns -1.
            System.out.println("Adding new URL: " + url + " whit return data: " + data);
            Urls.add(url);
            Data.add(data);
            return 0;
       }
       else {
            //the item is already in the array
            System.out.println("AddURL: Error: URL to add is already in the array.");
            return -1;
       }    
    }
    
    
    
    
    public void run() { //beware runnable.run cant throw exception so try...catch it
        System.out.println("Starting HTTP Server thread " + Thread.currentThread().getId());
        String input,requestUrl;
        boolean done;
        try{
        ServerSocket httpServer = new ServerSocket(3500);
        while(true){
          
          Socket http = httpServer.accept();
          PrintWriter send = new PrintWriter(http.getOutputStream(),true);
          BufferedReader recv = new BufferedReader(new InputStreamReader(http.getInputStream()));
          
          while((input = recv.readLine()) != null && input.contains("GET")){
              System.out.println("Running loop");
              System.out.println("Received: " + input);
              requestUrl = this.parseRequest(input); 
              System.out.println("Parsed URL: " + requestUrl);
              done = false;
              for(int i=0;i<Urls.size();i++){
                  if(requestUrl.equals(Urls.get(i))){
                      System.out.println("Looping, i ="+i);
                      System.out.println("Sending requested data to client...");
                      send.println("HTTP/1.1 200 OK");
                      send.println("Server: ProjectPwn built-in server/1.0");
                      send.println();
                      send.println(Data.get(i));
                      done = true;
                      
                }   
              }
              if(!done){
                  //return 404
                  System.out.println("Client requested URL not found by the server. Returning 404.");
                  send.println("HTTP/1.1 404 NOT FOUND");
                  send.println("Server: ProjectPwn built-in server/1.0");
                  send.println();
                  send.println("404 not found");
                  
              }
              http.close(); //close the connection to prefent infinilooping
          }
               
        }//whiletrue
        }//try
        catch(IOException e){
            System.out.println("IOexception");
            e.printStackTrace();
        }
       
    }
    
        private String parseRequest(String request){
        return request.split(" ")[1]; //return second element of the GET request, the URL.
    }
        
        
    public boolean urlExists(String url){
        try{
            if(Urls.indexOf(url) != -1){
                return true;
            }
            else { //item doesnt exist
                return false;
            }
        }//try
        catch(IndexOutOfBoundsException i){
            return false; //also, doesnt exist.
        
        }
    }
    
    public int removeUrl(String url){// API call to remove one of the listening urls.
        try{
            int index;
            if((index = Urls.indexOf(url)) != -1){;
            Urls.remove(index);
            return 0;
        }
            else{
                return -1;
            }
        }//try
        catch(IndexOutOfBoundsException i){
            return -1; //not found, catch the exception.
        }
    }
    
    
    
}// class HttpServer
