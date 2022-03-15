/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intermidiate_code_generator.V_Table;

/**
 *
 * @author lumberjack
 */
public class Vars_register {
    
    public String varname;
    public String vartype;
    public String register;
    public String initialized;
    
    public Vars_register(String varname,String vartype,String register,String initialized){
        this.initialized=initialized;
        this.register = register;
        this.varname = varname;
        this.vartype = vartype;
    }
    
}
