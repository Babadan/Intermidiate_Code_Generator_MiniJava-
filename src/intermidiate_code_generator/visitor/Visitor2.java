/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intermidiate_code_generator.visitor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import intermidiate_code_generator.symbol_table.Class1;
import intermidiate_code_generator.symbol_table.File;
import intermidiate_code_generator.symbol_table.Method;
import intermidiate_code_generator.symbol_table.Symbol;
import intermidiate_code_generator.symbol_table.SymbolTable;
import intermidiate_code_generator.symbol_table.Variables;
import syntaxtree.ArrayType;
import syntaxtree.BooleanType;
import syntaxtree.ClassDeclaration;
import syntaxtree.ClassExtendsDeclaration;
import syntaxtree.FormalParameter;
import syntaxtree.FormalParameterList;
import syntaxtree.FormalParameterTerm;
import syntaxtree.Goal;
import syntaxtree.Identifier;
import syntaxtree.IntegerType;
import syntaxtree.MainClass;
import syntaxtree.MethodDeclaration;
import syntaxtree.VarDeclaration;
import visitor.GJDepthFirst;

/**
 *
 * @author lumberjack
 */
public class Visitor2 extends GJDepthFirst <SymbolTable,SymbolTable>{
    
    
    Variables tempvar;
    Method tempmethod;
    String tempident;
    String temptype;
    String curclassname;
    String extendclass;
    
    
    public void reset_Datamembers(){
        this.tempvar = new Variables();
        this.tempmethod = new Method();
        this.tempident = new String();
        this.temptype = new String();
        this.extendclass = new String();
        this.curclassname = new String();
    }
    
    
    public void reset_tempmethos_tempvar_tempident(){
        this.tempvar = new Variables();
        this.tempident = new String();
        this.tempmethod = new Method();
    
    }
    public void reset_tempident(){
        this.tempident = new String();
    }
    
    public void reset_tempvar_tempident(){
        this.tempvar = new Variables();
        this.tempident = new String();
    }

    public Visitor2() {
        this.tempvar = new Variables();
        this.tempmethod = new Method(); 
    }
    
   
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
      
      n.f1.accept(this, argu);
      String classname = this.tempident;
      this.reset_Datamembers();
      
      this.tempmethod.setType("void");
      this.tempmethod.setName("Main");
      
      n.f11.accept(this, argu);
      
      this.tempvar.setType("String[]");
      this.tempvar.setIdentifier(this.tempident);
      this.tempmethod.insert_parList(this.tempvar);
      
//----------------------------------------------Check if dublicate exists-----------------------------------      
      for(int i=0; i<n.f14.size(); i++){
            n.f14.nodes.get(i).accept(this, argu);
            if(!this.tempmethod.insert_variable(this.tempvar)){}
            this.reset_tempvar_tempident();
       }
      
      Vector<Variables> vec_vars = this.tempmethod.getValues_varDeclare();
//------------------------------------------Check if type exists---------------------------------------------------
      
      for(Variables var : vec_vars){
            if(var.getType().compareTo("int[]")!=0 && var.getType().compareTo("int")!=0 && var.getType().compareTo("boolean")!=0){
                if(!file.ExistsClass(var.getType())){  }
            } 
       }
      
      
    
      file.getClass(classname).updatemethod(this.tempmethod);
      
      this.reset_Datamembers();
      return file;
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
        
        n.f1.accept(this, argu);
        String classname = this.tempident;
        this.reset_Datamembers();
        
        this.curclassname= classname;
       
        Class1 cl = (Class1) file.getClass(classname);
       
//--------------------------------------Var Declaration----------------------------------------------------------------       
        for(int i=0; i<n.f3.size(); i++){
            n.f3.nodes.get(i).accept(this, argu);
            if(!cl.insert_variable(this.tempvar)){} 
        
        
            if(this.tempvar.getType().compareTo("int[]")!=0 && this.tempvar.getType().compareTo("int")!=0 && this.tempvar.getType().compareTo("boolean")!=0 ){
                if(!file.ExistsClass(this.tempvar.getType())){}
            }
        }
        
//---------------------------------------------Method Declaration -----------------------------------------------------        
        for(int i=0; i<n.f4.size(); i++){
            
            this.reset_tempmethos_tempvar_tempident();
            
            n.f4.nodes.get(i).accept(this,argu);
            
            if(cl.exist_method(this.tempmethod.getName())){}
            
           
            
            if(this.tempmethod.getType().compareTo("int[]")!=0 && this.tempmethod.getType().compareTo("int")!=0 && this.tempmethod.getType().compareTo("boolean")!=0 ){
                if(!file.ExistsClass(this.tempmethod.getType())){}
            }
            
            Vector<Variables> vec_vars = this.tempmethod.getParList();

//---------------------------------------------Type check parameter List------------------------------------------------------------------------            
            for(Variables var : vec_vars){
                if(var.getType().compareTo("int[]")!=0 && var.getType().compareTo("int")!=0 && var.getType().compareTo("boolean")!=0){
                    if(!file.ExistsClass(var.getType())){}
                } 
            }
            
            vec_vars = this.tempmethod.getValues_varDeclare();

//---------------------------------------------Type check function variables----------------------------------------------            
            for(Variables var : vec_vars){
                if(var.getType().compareTo("int[]")!=0 && var.getType().compareTo("int")!=0 && var.getType().compareTo("boolean")!=0){
                    if(!file.ExistsClass(var.getType())){}
                } 
            }
            
            cl.updatemethod(this.tempmethod);
        
        }
        return argu;
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
       
        this.reset_Datamembers();
        n.f1.accept(this, argu);
        this.curclassname= this.tempident;
        
        n.f3.accept(this, argu);
        this.extendclass = this.tempident;
        this.reset_tempvar_tempident();
    
        Class1 cl = (Class1) file.getClass(this.curclassname);
               
        for(int i=0; i<n.f5.size(); i++){
            n.f5.nodes.get(i).accept(this, argu);
            if(!cl.insert_variable(this.tempvar)){} 
            if(this.tempvar.getType().compareTo("int[]")!=0 && this.tempvar.getType().compareTo("int")!=0 && this.tempvar.getType().compareTo("boolean")!=0 ){
                if(!file.ExistsClass(this.tempvar.getType())){}
            }
        }
        
        
        for(int i=0; i<n.f6.size(); i++){
            
            this.reset_tempmethos_tempvar_tempident();
            
            n.f6.nodes.get(i).accept(this,argu);
            
            if(cl.exist_method(this.tempmethod.getName())){}
    
            if(this.tempmethod.getType().compareTo("int[]")!=0 && this.tempmethod.getType().compareTo("int")!=0 && this.tempmethod.getType().compareTo("boolean")!=0 ){
                if(!file.ExistsClass(this.tempmethod.getType())){}
            }
            
            Vector<Variables> vec_vars = this.tempmethod.getParList();
            

//---------------------------------------------Type check parameter List------------------------------------------------------------------------            
            for(Variables var : vec_vars){
                if(var.getType().compareTo("int[]")!=0 && var.getType().compareTo("int")!=0 && var.getType().compareTo("boolean")!=0){
                    if(!file.ExistsClass(var.getType())){}
                } 
            }
            
//-------------------------------------Checking if we overide method in parent class--------------------------------------
            
            Class1 extendClass = file.getClass(this.extendclass);
            while(extendClass!=null){
                
                int flag = extendClass.compare_method(this.tempmethod);
                
                if(flag==-1){}
                if(flag==1){
                    break;
                }
                extendClass = file.getClass(extendClass.getExtendClassName());
            }
            
            
            vec_vars = this.tempmethod.getValues_varDeclare();

//---------------------------------------------Type check function variables----------------------------------------------            
            
            for(Variables var : vec_vars){
                if(var.getType().compareTo("int[]")!=0 && var.getType().compareTo("int")!=0 && var.getType().compareTo("boolean")!=0){
                    if(!file.ExistsClass(var.getType())){
 
                    }
                } 
            }
            
            cl.updatemethod(this.tempmethod);
        
        }
//-------------------------------------------------------------------------------------------------------------------------        
      return argu;
   }
      
   /**
    * f0 -> "public"
    * f1 -> Type()
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( FormalParameterList() )?
    * f5 -> ")"
    * f6 -> "{"
    * f7 -> ( VarDeclaration() )*
    * f8 -> ( Statement() )*
    * f9 -> "return"
    * f10 -> Expression()
    * f11 -> ";"
    * f12 -> "}"
    */
   public SymbolTable visit(MethodDeclaration n, SymbolTable argu) {
      
      
     
        n.f1.accept(this, argu);
        this.tempmethod.setType(this.tempident);
        n.f2.accept(this, argu);
        this.tempmethod.setName(this.tempident);
      
        n.f4.accept(this, argu);
//-------------------------------Dublicate variable check----------------------------------------        
        
        for(int i=0; i<n.f7.size(); i++){
            n.f7.nodes.get(i).accept(this, argu);
            if(!this.tempmethod.insert_variable(this.tempvar)){}
            this.reset_tempvar_tempident();
        }
      return argu;
   }
   
    /**
    * f0 -> FormalParameter()
    * f1 -> FormalParameterTail()
    */
   public SymbolTable visit(FormalParameterList n, SymbolTable argu) {
      n.f0.accept(this, argu); 

//-------------------------------Dublicate variable check----------------------------------------              
      
      if(!this.tempmethod.insert_parList(this.tempvar)){
      }
      
      for(int i=0; i< n.f1.f0.nodes.size(); i++){
          n.f1.f0.nodes.get(i).accept(this, argu);
      }
      
      
      return argu;
   }
   
   /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
   public SymbolTable visit(FormalParameterTerm n, SymbolTable argu) {
      
      n.f1.accept(this, argu);
      if(!this.tempmethod.insert_parList(this.tempvar)){}
      
      return argu;
   }
   
   /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
   public SymbolTable visit(FormalParameter n, SymbolTable argu) {
      
      n.f0.accept(this, argu);
      this.tempvar.setType(this.tempident);
      
      n.f1.accept(this, argu);
      this.tempvar.setIdentifier(this.tempident);
      
      return argu;
   }
   
   
   /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
   public SymbolTable visit(VarDeclaration n, SymbolTable argu) {
      
      n.f0.accept(this, argu);
      this.tempvar.setType(this.tempident);
      
      n.f1.accept(this, argu);
      this.tempvar.setIdentifier(tempident);
      
     
      return argu;
   }
   
   /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
    public SymbolTable visit(ArrayType n, SymbolTable argu) {
      this.tempident = new String("int[]"); 
      return argu;
    }
    
    /**
    * f0 -> "boolean"
    */
   public SymbolTable visit(BooleanType n, SymbolTable argu) {
       this.tempident = new String("boolean");  
       return argu;
   }
   
   /**
    * f0 -> "int"
    */
    public SymbolTable visit(IntegerType n, SymbolTable argu) {
        this.tempident=  new String("int");  
        return argu;
    }
    
    public SymbolTable visit(Identifier n, SymbolTable ident) {
               
        this.tempident = n.f0.tokenImage;
        
        return ident ;
    
    }   
}
