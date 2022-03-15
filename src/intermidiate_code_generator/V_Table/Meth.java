/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intermidiate_code_generator.V_Table;

import intermidiate_code_generator.symbol_table.Variables;
import java.util.Vector;

/**
 *
 * @author lumberjack
 */
public class Meth {
    
    public String methodname =null;
    public String belongs = null;
    public String type = null; 
    public Vector<Variables> vardecl = null;
    public Vector<Variables> parlist = null;
    
    public Vector<Vars_register> varreg =null; 
    
    Meth(String methodname , String belongs ,Vector<Variables> vardecl,Vector<Variables> parlist,String type){
        this.belongs=belongs;
        this.methodname =methodname;
        this.vardecl=vardecl;
        this.parlist=parlist;
        this.varreg = new Vector<Vars_register>(); 
        for(Variables var : parlist){
            Vars_register v = new Vars_register(var.getIdentifier(),var.getType(),null,null);
            varreg.add(v);
        }
        for(Variables var : vardecl){
            Vars_register v = new Vars_register(var.getIdentifier(),var.getType(),null,null);
            varreg.add(v);
        }
    }
    
    Meth(Meth m){
        this.belongs = m.belongs;
        this.methodname = m.methodname;
        this.parlist = m.parlist;
        this.type = m.type;
        this.vardecl = m.vardecl;
        this.varreg = m.varreg;
    }
    
    public Vars_register find_varger(String name){
        
        for(Vars_register var:this.varreg){
            if(var.varname.compareTo(name)==0){
                return var;
            }
        }
        return null;
    }
}
