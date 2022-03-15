/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intermidiate_code_generator.symbol_table;

import java.util.*;

/**
 *
 * @author lumberjack
 */
public class MapSymbol {

    private HashMap map_className = null ;
    private HashMap map_method = null ;
    private HashMap map_classvars = null ;
    private HashMap map_parmlist = null;
    private HashMap map_methodvars = null;
    private Vector<MapSymbol> NestedTables; 

    public Vector<MapSymbol> getNestedTables() {
        return NestedTables;
    }
    
    public MapSymbol(MapKinds mapKind){
        
        switch (mapKind) {
            case CLASSMAP:
                this.map_className = new HashMap<String ,Class1>() ;
                break;
            case METHODMAP:
                this.map_method = new HashMap<Class1,Method>() ;
                break;
            case CLASSVARMAP:
                this.map_classvars = new HashMap<String,Variables>() ;  //Key is ident of var 
                break;
            case PARAMETERSMAP:  
                this.map_parmlist = new HashMap<String,Variables>() ; // Key is idetifier of var
                break;
            case METHODVARSMAP:
                this.map_methodvars = new HashMap<String,Variables>() ; 
                break;
        }
                  
    }
    
 
    public HashMap getMap() {
        if(this.map_className!=null) return this.map_className;
        if(this.map_classvars!=null) return this.map_classvars;
        if(this.map_method!=null) return this.map_method;
        if(this.map_parmlist!=null) return this.map_parmlist;
        if(this.map_methodvars!=null) return this.map_methodvars;
        return null;
    }
    public Map getMap_method() {
        return map_method;
    }

    public Map getMap_classvars() {
        return map_classvars;
    }

    public Map getMap_parmlist() {
        return map_parmlist;
    }

    public Map getMap_className() {
        return map_className;
    }
    
   
    
}

    
