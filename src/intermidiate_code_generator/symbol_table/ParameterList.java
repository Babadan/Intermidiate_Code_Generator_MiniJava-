/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intermidiate_code_generator.symbol_table;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lumberjack
 */
public class ParameterList extends SymbolTable {
    
    List<Variables> varlist ;
    
    public ParameterList(){
        this.varlist = new ArrayList<Variables>();
    }

    public List<Variables> getVarlist() {
        return varlist;
    }

    public void setVarlist(List<Variables> varlist) {
        this.varlist = varlist;
    }
    
}
