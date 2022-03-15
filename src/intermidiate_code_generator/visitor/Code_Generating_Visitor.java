/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intermidiate_code_generator.visitor;
import intermidiate_code_generator.V_Table.Meth;
import intermidiate_code_generator.V_Table.SymbolTable_VTable;
import intermidiate_code_generator.V_Table.V_Table;
import intermidiate_code_generator.V_Table.Vars;
import intermidiate_code_generator.V_Table.Vars_register;
import intermidiate_code_generator.symbol_table.Class1;
import intermidiate_code_generator.symbol_table.File;
import intermidiate_code_generator.symbol_table.Symbol;
import intermidiate_code_generator.symbol_table.SymbolTable;
import intermidiate_code_generator.symbol_table.Variables;
import java.util.Vector;
import visitor.*;
import syntaxtree.*;
/**
 *
 * @author lumberjack
 */
public class Code_Generating_Visitor extends GJDepthFirst <Symbol,SymbolTable_VTable>  {
    
    
    String currentClass; 
    String currentMethod;
    String extendClass;
    
    public Symbol visit(Goal n, SymbolTable_VTable argu) {
        
        Symbol _ret=null;
        SymbolTable_VTable sv_table = (SymbolTable_VTable)argu;
        
        n.f0.accept(this,sv_table);
        
        int i = 0;
        
        
        while( i < n.f1.nodes.size()){
            n.f1.nodes.get(i).accept(this, sv_table);
            i++;
        }
        
        
        n.f2.accept(this, argu);
        return _ret;
    }
    
    /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
    */
    public Symbol visit(ClassDeclaration n, SymbolTable_VTable sv_table) {
        Symbol s = new Symbol();
        
        
        this.currentClass = new String(n.f1.f0.tokenImage);
        this.extendClass = null;
        
        
        
        for(Node node: n.f4.nodes){
            node.accept(this,sv_table);
        }
        
        return s; //?Den kseroume akoma
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
    public Symbol visit(ClassExtendsDeclaration n, SymbolTable_VTable sv_table ) {
        
        Symbol s= new Symbol();
        this.currentClass=new String(n.f1.f0.tokenImage);
        
        this.extendClass=new String(n.f3.f0.tokenImage);
      
        
        for(Node node : n.f6.nodes){
            node.accept(this,sv_table);
        }
      
      return s;
   }
    
    
    boolean emit_Vtable(SymbolTable_VTable sv_table){
        
        int size = sv_table.vtable.class_table.size();
        V_Table vtable = sv_table.vtable;
        
        
        for(int i=0; i<size; i++){
            String reg = sv_table.create_register();
            String reg2 = sv_table.create_register();
            int functions = vtable.methods.get(i).size();
            
            sv_table.emit("\tMOVE " + reg +" "+ vtable.class_table.elementAt(i)+"_Vtable");
            sv_table.emit("\tMOVE " + reg2 +" "+"HALLOCATE " + String.valueOf(functions*4)  );
            sv_table.emit("\tHSTORE " + reg + " "+ "0" + " " + reg2);
            
            Vector<Meth> functVec = vtable.methods.get(i);
            
            for(int j = 0; j<functions; j++){
                String reg3 = sv_table.create_register();
                sv_table.emit("\tMOVE " + reg3 +" "+functVec.get(j).belongs+"_"+functVec.get(j).methodname);
                sv_table.emit("\tHSTORE " + reg2 + " "+ String.valueOf(j*4) + " " + reg3);
            }
            sv_table.emit(" ");
            
        }
        
        return true;
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
    public Symbol visit(MainClass n, SymbolTable_VTable argu) {
      
        Symbol _ret=null;
        SymbolTable_VTable sv_table = (SymbolTable_VTable)argu;
        
        int max_args = sv_table.vtable.find_max_arguments();
        sv_table.setRegisterNum(max_args);
        
        this.currentClass = new String(n.f1.f0.tokenImage);
        this.currentMethod = "Main";
        
        sv_table.emit("MAIN");
        int size = sv_table.vtable.class_table.size();
        this.emit_Vtable(sv_table);
        
        
        for(Node node : n.f14.nodes){
            node.accept(this,argu);
        }
        
        for(Node node : n.f15.nodes){
            node.accept(this, argu);
        }
        
        sv_table.emit("END");
        sv_table.emit("");
        
        return _ret;
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
    public Symbol visit(MethodDeclaration n,  SymbolTable_VTable sv_table) {
        
        Symbol s= new Symbol();
        Vector<String> registers = new Vector<String>();
        this.currentMethod = new String(n.f2.f0.tokenImage);
        
        Meth meth  = sv_table.vtable.find_meth(currentClass, this.currentMethod);
        
        int num_of_param = sv_table.fl.getClass(this.currentClass).getMethod(this.currentMethod).getParList().size();
        for(String reg : registers){
            reg = sv_table.create_register();
        }
        
       
        for(int i = 1; i<=num_of_param; i++){
            meth.varreg.get(i-1).register = new String("TEMP "+ i);
        }
        
        
        sv_table.emit(this.currentClass+"_"+this.currentMethod + " [" + String.valueOf(num_of_param + 1) +"]");
        sv_table.emit("BEGIN");
           
        for(Node node:n.f7.nodes){
            node.accept(this, sv_table);
        }
        
        for(Node node : n.f8.nodes){
            node.accept(this, sv_table);
        }
        
        s = n.f10.accept(this, sv_table);
        sv_table.emit("RETURN");
        
        sv_table.emit("\t"+s.regname);
        
        sv_table.emit("END");
        
        return s;
    }
    
    /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
    public Symbol visit(VarDeclaration n, SymbolTable_VTable sv_table) {
        Symbol s=new Symbol();
      
        s = n.f1.accept(this, sv_table);
     
        return s;
    }
    
     /**
    * f0 -> ( FormalParameterTerm() )*
    */
    public Symbol visit(FormalParameterTail n, SymbolTable_VTable sv_table ) {
      
        Symbol s = null;
        
        for(Node node : n.f0.nodes){
            s=node.accept(this, sv_table);
        }
        
        return s; 
    }
    
    /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
    public Symbol visit(Block n,SymbolTable_VTable sv_table) {
        Symbol s = null;
        for(Node node : n.f1.nodes){
            s = node.accept(this,sv_table);
        }
        return s;
    }

    /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
    public Symbol visit(AssignmentStatement n, SymbolTable_VTable sv_table) {
        sv_table.left_or_right = "LEFT";
        Symbol s = n.f0.accept(this, sv_table);
        
        sv_table.left_or_right = "RIGHT";
        Symbol s1 = n.f2.accept(this, sv_table);
        
        String reg1 = s.regname;
        String reg2 = s1.regname;
        String reg3 = sv_table.create_register();
            
        if(s.isDatamember.compareTo("YES")==0){
            sv_table.emit("\tHSTORE " + s.regname + " 0 " + reg2 );
        }
        else {
            sv_table.emit("\tMOVE " + reg1 + " " +reg2 );
        }
        
        
        s.isDatamember = "NO";
        s.regname=null;
        s.setDirives("AssignmentStatement");
        s.setType(null);
        
        return s ;
    }
    
    /**
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> Expression()
    * f3 -> "]"
    * f4 -> "="
    * f5 -> Expression()
    * f6 -> ";"
    */
    public Symbol visit(ArrayAssignmentStatement n, SymbolTable_VTable sv_table) {
        
        Symbol s = n.f0.accept(this, sv_table);
        Symbol s1 = n.f2.accept(this, sv_table);
        Symbol s2 = n.f5.accept(this, sv_table);
        
        String reg1 = s.regname;
        String reg2 = s1.regname;
        String reg3 = s2.regname;
        String reg4 = sv_table.create_register();
        String reg5 = sv_table.create_register();
        String reg6 = sv_table.create_register();
        
        String label1 = sv_table.create_label();
        
        
        sv_table.emit("\tHLOAD " + reg4 + " " + reg1 + " 0");
        sv_table.emit("\tMOVE " + reg5 + " LT " + reg2 + " 0" );
        sv_table.emit("\tCJUMP "+ reg5 + " " + label1 );
        sv_table.emit("\tERROR");
        sv_table.emit(label1+ "\n\tNOOP");
        sv_table.emit("\tMOVE " + reg6 + " PLUS " + reg2 + " 1" );
        sv_table.emit("\tMOVE " + reg6 + " TIMES " + reg6 + " 4" );
        sv_table.emit("\tMOVE " + reg6 + " PLUS " + reg1 + " " +reg6 );
        sv_table.emit("\tHSTORE " + reg6 + " 0 " + reg3 );
        
        s.regname=null;
        s.setDirives("ArrayAssignmentStatement");
        s.setType(null);
        
        return s;
    }
        
    /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    * f5 -> "else"
    * f6 -> Statement()
    */
    public Symbol visit(IfStatement n, SymbolTable_VTable sv_table ) {
        Symbol s =new Symbol();
        s = n.f2.accept(this, sv_table);
        
        String reg1 = s.regname;
        
        String label1 = sv_table.create_label();
        String label2 = sv_table.create_label();
        
        sv_table.emit("\tCJUMP "+ reg1 + " "+ label1);
        
        n.f4.accept(this, sv_table);
        
        sv_table.emit("\tJUMP " + label2);
        sv_table.emit(label1 + "\n\tNOOP"); 
        
        n.f6.accept(this, sv_table);
        
        sv_table.emit(label2 + "\n\tNOOP");
        
        s.regname = null;
        s.setDirives("IfStatement");
        s.setType(null);
        
        return s;
    }
     
     /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
    public Symbol visit(WhileStatement n, SymbolTable_VTable sv_table) {
        Symbol s =new Symbol();
        Symbol s1 =new Symbol();
        String label1 = sv_table.create_label();
        String label2 = sv_table.create_label();
       
        sv_table.emit(label1 + "\n\tNOOP");
        s = n.f2.accept(this, sv_table);
        String reg1 = s.regname;
       
        sv_table.emit("\tCJUMP "+ reg1 +" "+ label2);
        n.f4.accept(this, sv_table);
        sv_table.emit("\tJUMP "+label1);
        sv_table.emit(label2 + "\n\tNOOP");
        
        s.regname = null;
        s.setDirives("WhileStatement");
        s.setType(null);
        
        return s;
   }
    
    /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
    */
    public Symbol visit(PrintStatement n, SymbolTable_VTable sv_table) {
        
        Symbol s =new Symbol();
        s = n.f2.accept(this, sv_table);
        String reg1 = s.regname;
        sv_table.emit("\tPRINT " + reg1);
        
        s.regname = null;
        s.setDirives("PrintStatement");
        s.setType(null);
        
        return s;
    }
    
    /**
    * f0 -> Clause()
    * f1 -> "&&"
    * f2 -> Clause()
    */
    public Symbol visit(AndExpression n, SymbolTable_VTable sv_table) {
        
        Symbol s =new Symbol();
        Symbol s1 = new Symbol();
        String reg1 = null;
        String reg2 = sv_table.create_register();
        String reg3 = null;
        String label1 = sv_table.create_label();
        
        s = n.f0.accept(this, sv_table);
        reg1 = s.regname;
        sv_table.emit("\tMOVE " + reg2 + " " + reg1);
        sv_table.emit("\tCJUMP " + reg1 + " " + label1);
        
        s1 = n.f2.accept(this, sv_table);
        reg3 = s1.regname;
        sv_table.emit("\tMOVE " + reg2 + " " + reg3);
        
        sv_table.emit(label1 + "\n\tNOOP");
        
        s.regname=reg2;
        s.setDirives("AndExpression");
        s.setType("boolean");
        
        return s;
    }
    
    /**
    * f0 -> PrimaryExpression()
    * f1 -> "<"
    * f2 -> PrimaryExpression()
    */
    public Symbol visit(CompareExpression n, SymbolTable_VTable sv_table ) {
        Symbol s =new Symbol();
        Symbol s1 = new Symbol();
        s = n.f0.accept(this, sv_table);
        s1 = n.f2.accept(this, sv_table);
        
        String reg1 = s.regname;
        String reg2 = s1.regname;
        String reg3 = sv_table.create_register();
        
        sv_table.emit("\tMOVE " + reg3 + " LT "+ reg1 + " " + reg2 );
        
        s.regname=reg3;
        s.setDirives("CompareExpression");
        s.setType("boolean");
        
        return s;
    }
    
    /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
    public Symbol visit(PlusExpression n, SymbolTable_VTable sv_table) {
        Symbol s =new Symbol();
        Symbol s1 = new Symbol();
        s = n.f0.accept(this, sv_table);
        s1 = n.f2.accept(this, sv_table);
        
        String reg1 = s.regname;
        String reg2 = s1.regname;
        String reg3 = sv_table.create_register();
        
        sv_table.emit("\tMOVE " + reg3 + " PLUS "+ reg1 + " " + reg2 );
        
        s.regname=reg3;
        s.setDirives("PlusExpression");
        s.setType("int");
        
        return s;
    }
    
    /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
    public Symbol visit(MinusExpression n, SymbolTable_VTable sv_table) {
        Symbol s =new Symbol();
        Symbol s1 = new Symbol();
        s = n.f0.accept(this, sv_table);
        s1 = n.f2.accept(this, sv_table);
        
        String reg1 = s.regname;
        String reg2 = s1.regname;
        String reg3 = sv_table.create_register();
        
        sv_table.emit("\tMOVE " + reg3 + " MINUS "+ reg1 + " " + reg2 );
        
        s.regname=reg3;
        s.setDirives("MinusExpression");
        s.setType("int");
        return s;
    }
    
    /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
    public Symbol visit(TimesExpression n, SymbolTable_VTable sv_table) {
        Symbol s =new Symbol();
        Symbol s1 = new Symbol();
        s = n.f0.accept(this, sv_table);
        s1 = n.f2.accept(this, sv_table);
        
        String reg1 = s.regname;
        String reg2 = s1.regname;
        String reg3 = sv_table.create_register();
        
        sv_table.emit("\tMOVE " + reg3 + " TIMES "+ reg1 + " " + reg2 );
        
        s.regname=reg3;
        s.setDirives("TimesExpression");
        s.setType("int");
        
        return s;
    }
    
    /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
    public Symbol visit(ArrayLookup n, SymbolTable_VTable sv_table ) {
        Symbol s = new Symbol();
        Symbol s1 = new Symbol();
        s = n.f0.accept(this, sv_table);
        s1 = n.f2.accept(this, sv_table);
        
        String reg1 = s.regname;
        String reg2 = s1.regname;
        String reg3 = sv_table.create_register();
        String reg4 = sv_table.create_register();
        String reg5 = sv_table.create_register();
        String reg6 = sv_table.create_register();
        String reg7 = sv_table.create_register();
        String reg8 = sv_table.create_register();
        
        String label1 = sv_table.create_label();
        String label2 = sv_table.create_label();
        
        sv_table.emit("\tHLOAD "+ reg3 + " " + reg1 + " 0");
        sv_table.emit("\tMOVE " + reg3 + " MINUS " + reg3 + " 1");
        sv_table.emit("\tMOVE " + reg4 + " LT " + reg3 + " " + reg2);
        sv_table.emit("\tCJUMP " + reg4 + " " + label1);
        sv_table.emit("\tERROR");
        sv_table.emit(label1 + "\n\tNOOP");
        
        sv_table.emit("\tMOVE " + reg4 + " LT " + reg2 + " 0");
        sv_table.emit("\tCJUMP " + reg4 + " " + label2);
        sv_table.emit("\tERROR");
        sv_table.emit(label2 + "\n\tNOOP");
        
        sv_table.emit("\tMOVE " + reg8 + " PLUS " + reg2 + " 1");
        sv_table.emit("\tMOVE " + reg5 + " TIMES " + reg8 + " 4");
        
        sv_table.emit("\tMOVE " + reg6 + " PLUS " + reg1 + " " + reg5);
        sv_table.emit("\tHLOAD " + reg7 + " " + reg6 + " " + " 0");
        
        s.regname=reg7;
        s.setDirives("ArrayLookup");
        s.setType("int");
        return s;
    }
    
    /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
    public Symbol visit(ArrayLength n, SymbolTable_VTable sv_table ) {
        Symbol s = new Symbol();
        s = n.f0.accept(this, sv_table);
        String reg = sv_table.create_register();
        
        sv_table.emit("\tHLOAD " + reg + " " + s.regname + " 0");
        
        s.regname=reg;
        s.setDirives("ArrayLength");
        s.setType("int");
        
        return s;
    }
    
     /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( ExpressionList() )?
    * f5 -> ")"
    */
    public Symbol visit(MessageSend n, SymbolTable_VTable sv_table) {
        Symbol s=new Symbol();
        Symbol s1 =new Symbol();
        
        s = n.f0.accept(this, sv_table); //callie object address
        s1= n.f4.accept(this,sv_table);
       
        int function_num = sv_table.vtable.get_method_number(s.getType(),n.f2.f0.tokenImage);
        
        
        String reg1 = sv_table.create_register();
        String reg2 = sv_table.create_register();
        String reg3 = sv_table.create_register();
      
        
        sv_table.emit("\tHLOAD " + reg1 + " " + s.regname+ " 0");// current object address
        sv_table.emit("\tHLOAD " + reg2 + " " + reg1 + " " + String.valueOf((function_num)*4)); // Funct address
        
        
        
        sv_table.emit_no_newline("\tMOVE " + reg3 + " CALL " + reg2 + " (" + s.regname + " ");
        if(s1!=null){
            for(Symbol sym: s1.getExpList() ){
                sv_table.emit_no_newline(sym.regname + " ");
            }
        }    
        sv_table.emit_no_newline(")\n");
        
        s.regname = reg3;
        
        if(s.getDirives().compareTo("IDENTIFIER")==0){
           String str = sv_table.vtable.get_var_type(this.currentClass, this.currentMethod,s.getName());
           s.setType(str);
        }
        else{
            s.setType(sv_table.vtable.get_method(s.getType(),n.f2.f0.tokenImage).type);
        }
        s.setDirives("MessageSend");
        return s;
    }
    
     /**
    * f0 -> Expression()
    * f1 -> ExpressionTail()
    */
    public Symbol visit(ExpressionList n, SymbolTable_VTable sv_table ) {
        Symbol s = new Symbol();
        s.setExpList(new Vector<Symbol>());
        s.getExpList().add(n.f0.accept(this, sv_table));
        
        for(Node node : n.f1.f0.nodes ){
            Symbol s1 = node.accept(this, sv_table);
            s.getExpList().add(s1);
        }
        s.setDirives("ExpressionList");
        s.setType(null);
        return s;
    }
    
    /**
    * f0 -> ","
    * f1 -> Expression()
    */
    public Symbol visit(ExpressionTerm n, SymbolTable_VTable sv_table) {
        return n.f1.accept(this, sv_table);
    }
    
    /**
    * f0 -> <INTEGER_LITERAL>
    */
    public Symbol visit(IntegerLiteral n, SymbolTable_VTable sv_table) {
        Symbol s = new Symbol();
        String reg = sv_table.create_register();
        sv_table.emit("\tMOVE "+ reg + " " + n.f0.tokenImage);
        s.regname=reg;
        s.setDirives("IntegerLiteral");
        
        return s;
    }
    
    /**
    * f0 -> "true"
    */
    public Symbol visit(TrueLiteral n, SymbolTable_VTable sv_table ) {
        
        Symbol s = new Symbol();
        String reg = sv_table.create_register();
        
        sv_table.emit("\tMOVE "+ reg + " 1");
        s.regname=reg;
        s.setDirives("TrueLiteral");
        
        return s;
    }
    
    /**
    * f0 -> "false"
    */
    public Symbol visit(FalseLiteral n, SymbolTable_VTable sv_table ) {
        
        Symbol s = new Symbol();
        String reg = sv_table.create_register();
        
        sv_table.emit("\tMOVE "+ reg + " 0");
        s.regname=reg;
        s.setDirives("FalseLiteral");
        
        return s;
    }
   
    /**
    * f0 -> <IDENTIFIER>
    */
    public Symbol visit(Identifier n, SymbolTable_VTable sv_table) {
        
        Symbol s = new Symbol();
        Meth meth = sv_table.vtable.find_meth(currentClass, this.currentMethod);
        
        Vars_register var = meth.find_varger(n.f0.tokenImage);
        
        if(var == null){
            Vars var1 = sv_table.vtable.find_match_in_fields(currentClass, n.f0.tokenImage);
            s.regname= sv_table.create_register();
            int offset = sv_table.vtable.get_field_number(this.currentClass,n.f0.tokenImage);
            s.isDatamember = "YES";
            
            if(sv_table.left_or_right.compareTo("RIGHT")==0){
                sv_table.emit("\tHLOAD " + s.regname + " TEMP 0 " + String.valueOf(offset*4));  
            }
            if(sv_table.left_or_right.compareTo("LEFT")==0){
                sv_table.emit("\tMOVE "+ s.regname + " PLUS" + " TEMP 0 " + String.valueOf(offset*4));
                sv_table.left_or_right = "RIGHT";
            }
        }
        else{
            
            s.isDatamember = "NO";
            if(var.register==null){
                s.regname = sv_table.create_register();
                var.register = s.regname;
            }
            else {
                s.regname = var.register;
            }
            
        }
        
        s.setType(sv_table.vtable.get_var_type(currentClass, this.currentMethod, n.f0.tokenImage));
        sv_table.vtable.get_var_type(currentClass, this.currentMethod, n.f0.tokenImage);
        s.setDirives("IDENTIFIER");
        s.setName(n.f0.tokenImage);
        return s;
    }
    
    /**
    * f0 -> "this"
    */
    public Symbol visit(ThisExpression n, SymbolTable_VTable sv_table) {
        Symbol s = new Symbol();
        s.regname = "TEMP 0";
        s.setDirives("ThisExpression");
        s.setType(this.currentClass);
        return s;
    }
    
    /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Expression()
    * f4 -> "]"
    */
    public Symbol visit(ArrayAllocationExpression n, SymbolTable_VTable sv_table) {
        
       Symbol s = new Symbol();
       s = n.f3.accept(this, sv_table);
       String reg1 = s.regname;
       String reg2 = sv_table.create_register();
       String reg3 = sv_table.create_register();
       String reg4 = sv_table.create_register();
       String reg5 = sv_table.create_register();
       String reg6 = sv_table.create_register();
       String reg7 = sv_table.create_register();
       
       String label1 = sv_table.create_label();
       String label2 = sv_table.create_label();
       String label3 = sv_table.create_label();
       
       sv_table.emit("\tMOVE " + reg2 + " LT " + reg1 + " 0");
       sv_table.emit("\tCJUMP " + reg2 + " "+ label1);
       sv_table.emit("\tERROR");
       sv_table.emit(label1 + "\n\tNOOP");
       sv_table.emit("\tMOVE " + reg3 + " PLUS " + reg1 + " 1");
       sv_table.emit("\tMOVE " + reg3 + " TIMES " + reg3 + " 4");
       sv_table.emit("\tMOVE " + reg4 + " HALLOCATE " + reg3);
       sv_table.emit("\tHSTORE " + reg4 + " 0 " + reg1);
       sv_table.emit("\tMOVE " + reg5 + " 0 ");
       sv_table.emit("\tMOVE " + reg2 +" LT " + reg5 + " " + reg1 );
       sv_table.emit("\tMOVE " + reg6 +" PLUS " + reg4 + " 4" );
       sv_table.emit("\tMOVE " + reg7 +" PLUS " + reg1 + " " + reg3 );
       sv_table.emit("\tCJUMP " + reg2 + " " + label2);
       sv_table.emit(label3 + "\n\tNOOP");
       sv_table.emit("\tHSTORE " + reg6 + " 0 " + reg5);
       sv_table.emit("\tMOVE " + reg6 +" PLUS " + reg6 + " 4");
       sv_table.emit("\tMOVE " + reg2 +" LT " + reg7 + " " + reg6 );
       sv_table.emit("\tCJUMP " + reg2 + " "+ label3);
       sv_table.emit(label2 + "\n\tNOOP");
       
       s.regname = reg4;
       s.setDirives("ArrayAllocationExpression");
       s.setType("int[]");
      
       return s;  //? ti epistrefw
    }
    
    /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
    public Symbol visit(AllocationExpression n, SymbolTable_VTable sv_table) {
        
        String reg = sv_table.create_register();
        String reg1 = sv_table.create_register();
        String reg3 = sv_table.create_register();
        String reg4 = sv_table.create_register();
        
        
        Symbol s = new Symbol();        
        sv_table.emit("\tMOVE " + reg + " " + n.f1.f0.tokenImage+"_Vtable" );
        int varnum = sv_table.vtable.get_field_size(n.f1.f0.tokenImage);
        
        
        sv_table.emit("\tMOVE " + reg1 + " HALLOCATE " + String.valueOf((varnum+1)*4));
        
        sv_table.emit("\tHLOAD " + reg3 + " " + reg + " 0");
        sv_table.emit("\tHSTORE " + reg1 + " 0 " + reg3);
        sv_table.emit("\tMOVE "+ reg4 + " 0");
        for(int i =1; i<= varnum; i++ ){
            sv_table.emit("\tHSTORE " + reg1 + " " + String.valueOf(i*4) + reg4);
        }
        
        s.regname=reg1;
        s.setDirives("AllocationExpression");
        s.setType(n.f1.f0.tokenImage);
        
        
        return s;
    }
  //--  
    /**
    * f0 -> "!"
    * f1 -> Clause()
    */
    public Symbol visit(NotExpression n, SymbolTable_VTable sv_table) {
        Symbol s = new Symbol();
        s = n.f1.accept(this, sv_table);
        String reg = sv_table.create_register();
        String reg1 = sv_table.create_register();
        
        sv_table.emit("\tMOVE " + reg + " " + "1");
        sv_table.emit("\tMOVE " + reg1 + " " + "MINUS " + reg + " " + s.regname); // ERwthsh 
       
        s.regname=reg1;
        s.setDirives("NotExpression");
        return s;
    }
    
    /**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
    */
   public Symbol visit(BracketExpression n, SymbolTable_VTable argu) {    
      
       Symbol s = n.f1.accept(this, argu);
       s.setDirives("BracketExpression");
       return s;
   }
    
}
