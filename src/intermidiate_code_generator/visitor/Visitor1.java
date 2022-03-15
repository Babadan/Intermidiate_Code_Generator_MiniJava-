/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intermidiate_code_generator.visitor;

import java.lang.Exception;
import java.util.ArrayList;

import visitor.*;
import syntaxtree.*;

import intermidiate_code_generator.symbol_table.Class1;
import intermidiate_code_generator.symbol_table.File;
import intermidiate_code_generator.symbol_table.SymbolTable;
/**
 *
 * @author lumberjack
 */
public class Visitor1 extends GJDepthFirst <SymbolTable,SymbolTable> {
        
    
    String MainClass;
    
    public Visitor1(){}
    
    /**
     * f0 -> MainClass()
     * f1 -> ( TypeDeclaration() )*
     * f2 -> <EOF>
     * @param n
     */
    public SymbolTable visit(Goal n, SymbolTable argu) {
        
        SymbolTable _ret=null;
        File file = (File)argu;
        
        n.f0.accept(this,file);
        int i = 0;
        
        
        while( i < n.f1.nodes.size()){
            n.f1.nodes.get(i).accept(this, file);
            i++;
        }
        
        
        n.f2.accept(this, argu);
        return _ret;
    }
    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "{"
     * f3 -> "public"
     * f4 -> "static"
     * f5 -> "void"
     * f6 -> "main"
     * f7 -> "("
     * f8 -> "String"
     * f9 -> "["
     * f10 -> "]"
     * f11 -> Identifier()
     * f12 -> ")"
     * f13 -> "{"
     * f14 -> ( VarDeclaration() )*
     * f15 -> ( Statement() )*
     * f16 -> "}"
     * f17 -> "}"
     */
    public SymbolTable visit(MainClass n, SymbolTable argu) {
          
      File file = (File)argu;
      
      this.MainClass = n.f1.f0.toString();
      Class1 className = new Class1(MainClass,null);     
      file.getClassmap().getMap_className().put(this.MainClass, className);
      file.insert_class(className);
           
      
      return file;
   }
    
    
    /**
     * f0 -> ClassDeclaration()
     *       | ClassExtendsDeclaration()
     */
    public SymbolTable visit(TypeDeclaration n, SymbolTable argu){
        return n.f0.accept(this, argu);
    }
    
    
    /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
    */
    public SymbolTable visit(ClassDeclaration n, SymbolTable argu) {
        
        File file = (File)argu;
        n.f0.accept(this, argu);
        String className = n.f1.f0.toString();
         
        Class1 className1 = new Class1(className,null);
        file.getClassmap().getMap_className().put(className, className1);
        file.insert_class(className1);
       
        return file;
    }
    
    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "extends"
     * f3 -> Identifier()
     * f4 -> "{"
     * f5 -> ( VarDeclaration() )*
     * f6 -> ( MethodDeclaration() )*
     * f7 -> "}"
     */
    
    public SymbolTable visit(ClassExtendsDeclaration n, SymbolTable argu) {
        
        File file = (File)argu;
      
    /*------------------Double Class Name ----------------------*/    
        String _ret=null;
        String className = n.f1.f0.toString();
        
    /*------------------Extend to main or noclass---------------------*/    
        String extendClassName = n.f3.f0.toString();
        
        //if(extendClassName.compareTo(this.MainClass)==0){
            //throw new Semantic_Exception("Class " + className + " extends to Main class "  + extendClassName +"\n");
        //}
    /*---------------------------------------------------------------*/    
       
        Class1 className1 = new Class1(className,extendClassName);
        file.insert_class(className1);
        
        return file;
    }
    
    
   
}
