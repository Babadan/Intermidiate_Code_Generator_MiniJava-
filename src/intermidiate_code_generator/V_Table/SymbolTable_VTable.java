/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intermidiate_code_generator.V_Table;

import intermidiate_code_generator.symbol_table.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 *
 * @author lumberjack
 */
public class SymbolTable_VTable {
    
    
    public File fl;
    public V_Table vtable;
    
    
    String path_to_file;
    PrintWriter writer;
    
    int registerNum;
    int labelNum;
    
    
    public String left_or_right = "RIGHT" ;
    
    public SymbolTable_VTable(File fl , V_Table vtable,String path){
        this.fl=fl;
        this.vtable=vtable;
        this.path_to_file =path;
        this.registerNum = 0;  
     
        try {
             writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(path+".sp")));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SymbolTable_VTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean emit(String str){
        writer.println(str);
        return true;
    }
    
    public boolean emit_no_newline(String str){
        writer.print(str);
        return true;
    }
    
    
    public String create_register(){
        this.registerNum ++;
        return new String("TEMP " + String.valueOf(registerNum));
    }
    
    public String create_label(){
        this.labelNum++;
        return new String("L" + String.valueOf(labelNum));
    }

    public void setRegisterNum(int registerNum) {
        this.registerNum = registerNum;
    }
    
    public boolean closefile(){
        writer.close();
        return true;
    }
}
