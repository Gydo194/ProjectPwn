/*
    HttpServer v1.0
    Gydo Kosten, April 2, 2017.
    Built for Project Pwn.
    
 */
package projectpwn;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
//import java.util.Optional;
import java.util.concurrent.Callable;
import java.lang.reflect.*;
/**
 *
 * @author Gydo Kosten
 */
public class HttpServer implements Runnable { //implements runnable to be able to create a new thread
    ArrayList<String> Urls = new ArrayList<String>();
    ArrayList<String> Data = new ArrayList<String>();
    ArrayList<Callable> callBacks = new ArrayList<Callable>();
    String testText = "";
    Object parentObject;
    
    
    
    
    
    
    
    public void run() { //beware runnable.run cant throw exception so try...catch it
        System.out.println("Starting HTTP Server thread " + Thread.currentThread().getId());
        String input,requestUrl,queryString = "";
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
              requestUrl = parse.getRequestUrl(input); //gets the request URL (ex. /event)
              System.out.println("Parsed URL: " + requestUrl);
              if(parse.hasQueryString(input)){
                  queryString = parse.getQueryString(input);
                  System.out.println("Parsed Query String: " + queryString);
                  
              }
              
              done = false;
              for(int i=0;i<Urls.size();i++){
                  if(requestUrl.equals(Urls.get(i))){
                      System.out.println("Looping, i ="+i);
                      System.out.println("Sending requested data to client...");
                      send.println("HTTP/1.1 200 OK");
                      send.println("Server: ProjectPwn built-in server/1.0");
                      send.println();
                      send.println(Data.get(i));
                      if(queryString != ""){
                          this.testText = queryString;
                          ProjectPwn.setText(queryString);
                          
                      }
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
              queryString = "";
          }
               
        }//whiletrue
        }//try
        catch(IOException e){
            System.out.println("IOexception");
            e.printStackTrace();
        }
       
    }
    
    /*
    private String parseRequest(String request){
        String[] rq =  request.split(" ");
        System.out.println(rq[1]);
        if(request.contains("=")){ //split the request URL at '=' char, the rest is postback data
            System.out.println("QUERY STRING FOUND");
            String[] qs = rq[1].split("=");
            System.out.println(qs[1]);
        }
        System.out.println(rq[1]);
        return rq[1];
        
        //return second element of the GET request, the URL.
    }
    */
    
    
    
    private static class parse {
        public static String getRequestUrl(String req){
            String url = req.split(" ")[1];
            if(url.contains("=")){
                return url.split("=")[0];
            }
            else{
                return url;
            }
            
        }
        public static boolean hasQueryString(String request){
            return request.contains("=");
        }
        
        public static String getQueryString(String url){ //input as an already parsed request URL, why re-parse if not necessary?
            if(url.contains("=")){
                System.out.println("getQueryString(): parsing Query String from:"+url);
                return url.split("=")[1].split(" ")[0];
            }
            else{
                return "";
            }
        }
    }

    
    
    
    
    
    
    public int addURL(String url, String data, Callable callback){
       System.out.println("Starting AddURL");
       if(Urls.indexOf(url) == -1 && Data.indexOf(data) == -1){ //checks the ArrayList for the item being added, if it's not in the array, this returns -1.
            System.out.println("Adding new URL: " + url + " with return data: " + data);
            Urls.add(url);
            Data.add(data);
            callBacks.add(callback);
            return 0;
       }
       else {
            //the item is already in the array
            System.out.println("AddURL: Error: URL to add is already in the array.");
            return -1;
       }    
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
            Data.remove(index);
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
    public void printArrays(){ //used for debugging purposes, dumps the database of URLs and corrsponding data.
        System.out.println("PrintArrays() called. Dumping URL data database.");
        for(int c = 0; c < Urls.size(); c++){
            System.out.println("Item " + c + " : " + Urls.get(c) + ", Data =" + Data.get(c) + ", callBack =" + callBacks.get(c));
        }
        System.out.println("Ending PrintArrays().");
    }
    
    public void setParentObject(Object parent){
        this.parentObject = parent;
    }
    
    
    
    
}// class HttpServer
