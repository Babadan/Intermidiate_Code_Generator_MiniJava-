/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intermidiate_code_generator.symbol_table;

import java.util.Map;
import java.util.Vector;

/**
 *
 * @author lumberjack
 */
public class Class1 extends SymbolTable{
    
    private String className = null;
    private String extendClassName = null;
    private MapSymbol varDeclare = null;
    private MapSymbol method = null;
    public Vector<Variables> variables = null ;
    public Vector<Method> methodnames = null ;

    public Class1(String className ,String extendClassName ){
        this.className = new String(className);
        if(extendClassName!=null) this.extendClassName= new String(extendClassName);
        varDeclare = new MapSymbol(MapKinds.CLASSVARMAP);
        method = new MapSymbol(MapKinds.METHODMAP);
        variables = new Vector<Variables>(); 
        methodnames = new Vector<Method>(); 
    }
    
    
    public boolean updatemethod(Method method){
        
        if(null==this.getMethod().getMap().put(method.getName(),method)){ 
            methodnames.add(method);
            return false;
        }   
        return true;
    }
    
    public boolean insert_variable(Variables var){
        Variables var1 = new Variables(var);
        Map map = varDeclare.getMap();
        if(varDeclare.getMap().containsKey(var1.getIdentifier())){
            return false;
        }
        
        this.varDeclare.getMap().put(var1.getIdentifier(), var1);
        this.variables.add(var1);
        return true;
    }
    
    public boolean exist_method(String methodname){
        if(this.method.getMap().get(methodname)==null)
            return false;
        return true;
    }
    
    public int compare_method(Method method){
        
        Method method2 = (Method)this.method.getMap().get(method.getName());
        if(method2!=null){ // Compare if method exists with the same name .
            
            if(method.getType().compareTo(method2.getType())!=0){ // Compare if return type is the same 
                return -1;
            }
            
            Vector<Variables> vec = method.getParList();
            Vector<Variables> vec2 = method2.getParList();
            
            if(vec.size()!=vec2.size()) return -1;
            
            
            for(int i=0; i<vec.size(); i++){
                if(vec.get(i).getType().compareTo(vec2.get(i).getType())!=0){ // Compare parameter List
                    return -1;
                }
            }
            return 1;
        }
        return 0;
    }
    
    public Method getMethod(String methodname){
        return (Method) this.method.getMap().get(methodname);
    }
    
    public String getClassName() {
        return className;
    }

    public String getExtendClassName() {
        return extendClassName;
    }

    public MapSymbol getVarDeclare() {
        return varDeclare;
    }

    public MapSymbol getMethod() {
        return method;
    }
           
    
}
