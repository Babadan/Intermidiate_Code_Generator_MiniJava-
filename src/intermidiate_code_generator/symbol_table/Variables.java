/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intermidiate_code_generator.symbol_table;

/**
 *
 * @author lumberjack
 */
public class Variables extends SymbolTable {
  
    private String type = null ;
    private String identifier = null;
    
    public Variables(){}
    
    public Variables(Variables var){
        this.type = var.type;
        this.identifier = var.identifier;
    }
    
    public Variables(String type,String identifier){
        
        this.identifier = identifier;
        this.type = type;    
    }
    public Variables(String type){
        
        this.type = type;    
    }

    public String getType() {
        return type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    
    
    
    
    
}
