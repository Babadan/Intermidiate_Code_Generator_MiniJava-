/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intermidiate_code_generator.symbol_table;

import java.util.HashMap;
import java.util.Vector;


/**
 *
 * @author lumberjack
 */
public class File extends SymbolTable {
    
    MapSymbol classmap;
    public Vector<Class1> classes;
    
    public File(){
        this.classmap = new MapSymbol(MapKinds.CLASSMAP);
        this.classes = new Vector<Class1>();
    }
    
    public boolean insert_class(Class1 cl){
        
        this.classmap.getMap().put(cl.getClassName(), cl);
        this.classes.add(cl);
        return true;
    }
    
    public Class1 getClass(String className){
        Class1 cl  = (Class1) this.classmap.getMap().get(className);
        if(cl == null) return null;
        return cl;
    }
    
    public boolean ExistsClass(String classname){
        if(this.classmap.getMap().get(classname)==null)
            return false;
        return true;
    }
    
    public String getClassName(String classname){
        Class1 cl = (Class1)this.classmap.getMap().get(classname); 
        if(cl ==null ) return null;
        return cl.getClassName();
    }

    public MapSymbol getClassmap() {
        return classmap;
    }
    
    public boolean checkIfSubtype(String vartype1,String vartype2){
        Class1 cl = this.getClass(vartype1);
        if(cl==null) return false;
        while(cl.getExtendClassName()!=null){
            if(cl.getExtendClassName().compareTo(vartype2)==0){
                return true;
            }
        }
        return false;
    }
   
    
    public Method locatemethod(String classname,String classextendname,String method){
        
        Class1 class1 = (Class1)this.classmap.getMap().get(classname);
        Method meth;
        if(classextendname==null){
            
            if(class1.getMethod(method)==null){
                return null;
            }
            meth = class1.getMethod(method);
            return meth;
        }
        
        if(class1.getMethod(method)==null){
            Class1 class2 = this.getClass(class1.getExtendClassName());
            while(class2!=null){
                if(class2.getMethod(method)==null){
                    class2 = this.getClass(class2.getExtendClassName());
                    continue;
                }
                meth=(Method) class2.getMethod(method);
                return meth;
            }
            return null;
        }
        
        meth=(Method) class1.getMethod(method);
        return meth; 
    }
    
    public String locateClassScope(String classname,String classextendname,String varname){
        
        Class1 class1 = (Class1)this.classmap.getMap().get(classname);
        Variables var;
        
           
        if(class1.getVarDeclare().getMap().get(varname)!=null) {
            var = (Variables)class1.getVarDeclare().getMap().get(varname);
            return var.getType();
        }
        if(classextendname==null) return null;
            
        if(class1.getVarDeclare().getMap().get(varname)==null){
            Class1 class2 = this.getClass(class1.getExtendClassName());
            while(class2!=null){
                if(class2.getVarDeclare().getMap().get(varname)==null){
                    class2 = this.getClass(class2.getExtendClassName());
                    continue;
                }
                var=(Variables) class2.getVarDeclare().getMap().get(varname);
                return var.getType();
            }
            return null;
        }
        
        var=(Variables) class1.getVarDeclare().getMap().get(varname);
        return var.getType();

        
    }
    public String locateMethodScope(String classname,String methodname,String varname){
        
        Class1 class1 = (Class1)this.classmap.getMap().get(classname);
        Method method = (Method)class1.getMethod(methodname);
        Variables var ;
        
        if(method.contains_parList(varname)){
            var = (Variables)method.getParameter(varname);
            return var.getType();
        }
        if(method.getVarDeclare().getMap().containsKey(varname)){
            var = (Variables)method.getVarDeclare().getMap().get(varname);
            return var.getType();
        }
        return null;
    }
    
    public String locateClass_MethodScope(String classname,String methodname,String varname){
        
        Class1 class1 = (Class1)this.classmap.getMap().get(classname);
        Variables var;
        
        Method method = (Method)class1.getMethod(methodname);

        
        if(method.contains_parList(varname)){
            var = (Variables)method.getParameter(varname);
            return var.getType();
        }
        if(method.getVarDeclare().getMap().containsKey(varname)){
            var = (Variables)method.getVarDeclare().getMap().get(varname);
            return var.getType();
        }
        
        if(class1.getVarDeclare().getMap().get(varname)!=null) {
            var = (Variables)class1.getVarDeclare().getMap().get(varname);
            return var.getType();
        }
            
        if(class1.getVarDeclare().getMap().get(varname)==null){
            Class1 class2 = this.getClass(class1.getExtendClassName());
            while(class2!=null){
                if(class2.getVarDeclare().getMap().get(varname)==null){
                    class2 = this.getClass(class2.getExtendClassName());
                    continue;
                }
                var=(Variables) class2.getVarDeclare().getMap().get(varname);
                return var.getType();
            }
            return null;
        }
        
        var=(Variables) class1.getVarDeclare().getMap().get(varname);
        return var.getType();
        
    }
    
    
}
