/*
 Config reader 1.0beta-1 by Gydo Kosten
 10-4-17
 */
package projectpwn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author gydokosten
 */
public class ConfRead {
    StringBuffer out;
    ArrayList<String> contents = new ArrayList<String>();
    public String fromJar(String filename){
        //StringBuffer out;
        try{
          InputStream in = getClass().getResourceAsStream(filename);
          InputStreamReader rd = new InputStreamReader(in);
          BufferedReader bf = new BufferedReader(rd);
          out = new StringBuffer();
          String inLine;
          while((inLine = bf.readLine()) != null){
              out.append(inLine);
          }
          in.close();
          bf.close();
          rd.close();
          //return out.toString();
            
            
        }
        //return out.toString();
        catch(IOException e){
            //derp.
        }
        return out.toString();
    }
    
    
    public ArrayList<String> listDb(){
        contents.clear();
        String db = fromJar("/Modules.db");
        System.out.println("DB:" + db);
        String[] spl = db.split(";");
   //     contents.clear();
        for(String i : spl){
            System.out.println("Printing i:");
           System.out.println(i);
           contents.add(i); 
           //contents.add("/Modules/playsound.js");
        }
        return contents;
        
    }
    public String[] getDb() {
        return fromJar("/Modules.db").split(";");
    }
    
    
    
    public String fromDisk(String filename){
        System.out.println("WIP method called.");
        return "Work in Progress";
    }
    
    
    
    
    
}
