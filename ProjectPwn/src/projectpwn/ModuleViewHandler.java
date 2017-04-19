/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectpwn;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author gydokosten
 */
public class ModuleViewHandler  {
    private ConfRead config = new ConfRead();
    public JTree tree;
    DefaultMutableTreeNode root;
    
    
    
    public ModuleViewHandler() {
        root = new DefaultMutableTreeNode("Modules");
        tree = new JTree(root);
        //tree.addTreeSelectionListener(new TreeListener() );
        ProjectPwn.projectGui.ModuleTree.addTreeSelectionListener(new TreeListener() );
        System.out.println("ModuleViewHandler: creating tree with new rootnode");
        
    }
    
    public DefaultMutableTreeNode buildNodeFromString(String name){
        DefaultMutableTreeNode t = new DefaultMutableTreeNode(name);
        return t;
    }
    
    public JTree getTree() {
        return tree;
    }
    
    public DefaultMutableTreeNode getRootNode() {
        return root;
    }
    
    
    public String node;
    public static String modulePath;
    
    public class TreeListener implements TreeSelectionListener {
    @Override
    public void valueChanged(TreeSelectionEvent tse){
        if(tse.getNewLeadSelectionPath() != null){
           node = tse.getNewLeadSelectionPath().getLastPathComponent().toString();
           System.out.println("node = " + node);
            System.out.println("FULL path:");
            System.out.println( tse.getNewLeadSelectionPath().getParentPath() );
            modulePath = "/modules/" + node;
            System.out.println("modulePath: " + modulePath);
            String desc = config.fromJar(modulePath).split("//DESC")[1].split("//!DESC")[0];
            System.out.println("desc = " + desc);
            ProjectPwn.projectGui.ModuleInfoLabel.setText(desc);
        }
        else {
            System.out.println("tse.getNewleadSelectionPath = null");
            System.out.println( tse.getNewLeadSelectionPath().getLastPathComponent().toString() );
        }
    }
    }
    
    
    public void set(DefaultTreeModel model){
        this.tree.setModel(model); 
    }
    
    
    //test methods
    public DefaultTreeModel getModel() {
        ConfRead t = new ConfRead();
        java.util.ArrayList<String> mods = t.listDb();
       for(String b: mods){
            this.root.add(this.buildNodeFromString(b.split("/")[b.split("/").length-1]));
       }
        return new DefaultTreeModel(root);
    }
    
    public void setTree() {
        ProjectPwn.projectGui.ModuleTree.setModel(getModel());
    }
    
    //class moduleDataBase {
        
    //}
    
    //end test methods
    
    //parse default mutable tree nodes from string
}
