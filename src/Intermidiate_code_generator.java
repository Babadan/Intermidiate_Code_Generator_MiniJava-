
import intermidiate_code_generator.V_Table.SymbolTable_VTable;
import intermidiate_code_generator.V_Table.V_Table;
import intermidiate_code_generator.symbol_table.File;
import intermidiate_code_generator.visitor.Code_Generating_Visitor;
import intermidiate_code_generator.visitor.Visitor1;
import intermidiate_code_generator.visitor.Visitor2;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

import syntaxtree.Goal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lumberjackuble
 */
public class Intermidiate_code_generator {

    /**
     * @param args the command line arguments
     */
    
    static String path_to_Spiglet_files = "../SpigletFiles/" ;
    static String path_to_Java_files = "../JavaFiles/";
    
    public static void main(String[] args) throws IOException {
        if(args.length < 1){
            System.err.println("Usage: java Driver <inputFile>");
            System.exit(1);
        }
        
     
       for(String filename : args){
                
            FileInputStream fis = null;

            try{
                fis = new FileInputStream(path_to_Java_files+filename);
				

            } catch (FileNotFoundException ex) {
                System.err.println("File not found " +filename);
            }
            MiniJavaParser parser = new MiniJavaParser(fis);
            System.err.println("Program parsed successfully.");
            Visitor1 visitor = new Visitor1();
            Visitor2 visitor2 = new Visitor2();
            Code_Generating_Visitor cg_visitor = new Code_Generating_Visitor(); 
            Goal root = null;
            try {
                root = parser.Goal();
            } catch (ParseException ex) {
                Logger.getLogger(Intermidiate_code_generator.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            File file = new File();
             
            root.accept(visitor,file);
            root.accept(visitor2,file);
           
            V_Table vtable = new V_Table();
            vtable.Create_Vtable(file);
            
			String [] n = filename.split("\\.");
            if(n.length==0){
                System.err.println("Wrong name for file , file need extension . file.java");
            }
			
            SymbolTable_VTable sv_table =new SymbolTable_VTable(file,vtable,path_to_Spiglet_files + n[0]); 
            
           
            root.accept(cg_visitor,sv_table);
            
            sv_table.closefile();
            
            System.out.println("Spiglet_code for "+filename + " generated in \"SpigletFiles\".");
        }  
    }
    
}    
        
        

    

