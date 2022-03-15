/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intermidiate_code_generator.symbol_table;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 *
 * @author lumberjack
 */
public class Method extends SymbolTable{
    
    private String type = null;
    private String name = null;
    private Vector<Variables> parList = null ;
    private MapSymbol varDeclare = null ;
    public Vector<Variables> var_declare = null;
    
    
    
    public Method(String type,String name){
        this.type = new String(type);
        this.name = new String(name);
        this.parList = new Vector<Variables>();
        this.varDeclare = new MapSymbol(MapKinds.METHODVARSMAP);
        this.var_declare = new Vector<Variables>();
    }

    public Method() {
        this.parList = new Vector<Variables>();
        this.varDeclare = new MapSymbol(MapKinds.METHODVARSMAP);
        this.var_declare = new Vector<Variables>();
    }
    
    public boolean insert_parList(Variables var){
        
        Variables var1 = new Variables(var);
        if(this.contains_parList(var1.getIdentifier())){
            return false;
        }
        
        this.parList.add(var1);
        return true;
        
        
    }
    
    public boolean insert_variable(Variables var){
        
        Variables var1 = new Variables(var);
        if(this.varDeclare.getMap().containsKey(var1.getIdentifier())){
            return false;
        }
        if(this.contains_parList(var1.getIdentifier())){
            return false;
        }
    
        this.varDeclare.getMap().put(var1.getIdentifier(), var1);
        this.var_declare.add(var1);
        return true;
        
    }
    
//    public Vector<Variables> getValues_paramList(){
//        
//        Set set = this.parList.getMap().entrySet();
//        Iterator it = set.iterator();
//        Map.Entry me;
//        Vector<Variables> vector_var = new Vector<>();
//        while(it.hasNext()){
//            me =(Map.Entry<String,Variables>) it.next();
//            vector_var.add((Variables)me.getValue());
//        }
//        
//        return vector_var;
//    }
//    
    public Vector<Variables> getValues_varDeclare(){
        
        Set set = this.varDeclare.getMap().entrySet();
        Iterator it = set.iterator();
        Map.Entry me;
        Vector<Variables> vector_var = new Vector<>();
        while(it.hasNext()){
            me =(Map.Entry<String,Variables>) it.next();
            vector_var.add((Variables)me.getValue());
        }
        
        return vector_var;
        
    }
    
    public void setType(String type) {
        this.type = new String(type);
    }

    public void setName(String name) {
        this.name = new String(name);
    }
    
    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Vector<Variables> getParList() {
        return parList;
    }
    
    

    public MapSymbol getVarDeclare() {
        return varDeclare;
    }
    
    public Variables getParameter(String varname){
        
        for(int i=0; i< this.parList.size(); i++){
            if(this.parList.get(i).getIdentifier().compareTo(varname)==0){
                return this.parList.get(i);
            }
        }
        return null;
    }
    
    public boolean contains_parList(String varname){
        
        for(int i=0; i<this.parList.size(); i++){
            if(this.parList.get(i).getIdentifier().compareTo(varname)==0){
                return true;
            }
        }
        return false;
    }

    
}
