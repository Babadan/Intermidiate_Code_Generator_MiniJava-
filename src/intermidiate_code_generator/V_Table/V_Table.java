/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intermidiate_code_generator.V_Table;

import intermidiate_code_generator.symbol_table.Class1;
import intermidiate_code_generator.symbol_table.File;
import intermidiate_code_generator.symbol_table.Method;
import intermidiate_code_generator.symbol_table.Variables;
import java.util.Vector;

/**
 *
 * @author lumberjack
 */
public class V_Table {
    
    public Vector<String> class_table = null;
    public Vector<Vector<Vars>> fields = null;
    public Vector<Vector<Meth>> methods = null;
    
    public V_Table(){
        class_table = new Vector<String>();
        fields = new Vector<Vector<Vars>>();
        methods = new Vector<Vector<Meth>>();
        
    }
    
    
    public int find_max_arguments(){
        int number = 0;
        
        for(Vector<Meth> cl: methods){
            for(Meth method : cl){
                if(number < method.parlist.size()) 
                    number = method.parlist.size(); 
            }
        }
        return number;
    }
    
    
    
    
    public int get_field_number(String classname,String datamember){
        int num = 0;
        int i = this.getnum_with(classname);
    
        for(Vars var : this.fields.get(i)){
            num++;
            if(var.varname.compareTo(datamember)==0 && var.belongs.compareTo(classname)==0){
               break;
            }
        }
        return num;
        
    }
    
    public Vars find_match_in_fields(String classname,String datamember){
        
        int num = this.getnum_with(classname);
        
        for(Vars var : this.fields.get(num)){
            if(var.varname.compareTo(datamember)==0 && var.belongs.compareTo(classname)==0){
                return var;
            }
        }
        return null;
        
    } 
    
    public Meth find_meth(String cl,String methodname){
        int i = 0;
        for(String cl1 :this.class_table){
            if(cl1.compareTo(cl)==0){
                break;
            }
            i++;
        }
        for(Meth meth : this.methods.get(i)){
            if(meth.methodname.compareTo(methodname)==0){
                return meth;
            }
        }
        
        return null;
        
    }
    
    public Meth get_method(String clname, String methodname){
        
        int i = 0;
        for(String cl : this.class_table){
            if(cl.compareTo(clname)==0){
                break;
            }
            i++;
        }
        for(Meth meth : this.methods.get(i)){
            if(meth.methodname.compareTo(methodname)==0){
                return meth;
            }
        }
        return null;
    }
    
    public int  get_field_size(String classname ){
        
        int i = this.getnum_with(classname);
        
        return this.fields.get(i).size();
        
    }
    
    public String get_var_type(String cl ,String methodname ,String varname){
        
        int i = 0;
        
        for(String cl1 :this.class_table){
            if(cl1.compareTo(cl)==0){
                break;
            }
            i++;
        }
        for(Meth meth : this.methods.get(i)){
            if(meth.methodname.compareTo(methodname)==0){
                for(Variables var : meth.parlist){
                    if(var.getIdentifier().compareTo(varname)==0){
                        return var.getType();
                    }
                }
                for(Variables var : meth.vardecl){
                    if(var.getIdentifier().compareTo(varname)==0){
                        return var.getType();
                    }
                }
            }
        }
        
        for(Vars var : this.fields.get(i)){
            if(var.varname.compareTo(varname)==0){
                return var.type;
            }
        }    
        
        return null;
        
        
        
    }
    
    public int get_method_number(String clname, String methodname){
        
        int num = this.getnum_with(clname);
       
        int i = 0;
        for(Meth method: this.methods.get(num)){
            
            if(method.methodname.compareTo(methodname)==0){
                break;
            }
            i++;
            
        }
        return i;
    }
    
    int getnum_with(String name){
        int i = 0;
        
        for(String cl : this.class_table){
            if(cl.compareTo(name)==0){
                return i;
            }
            i++;
        }
        return -1;
    }
    
    public boolean Create_Vtable(File fl){
        
        for(Class1 cl : fl.classes){
            
            class_table.add(cl.getClassName());
           
            int j = this.class_table.size()-1;
            if(cl.getExtendClassName()!=null){
                
                int i = this.getnum_with(cl.getExtendClassName());
            //------------------------------------------------------------------    
                Vector<Vars> temp = new Vector<Vars> ();
                for(Vars var:this.fields.get(i)){
                    Vars var1 = new Vars(var.varname,var.belongs,var.type,null);
                    temp.add(var1);
                }
                this.fields.add(temp);
            //------------------------------------------------------------------    
                Vector<Meth> temp1 = new Vector<Meth> ();
                for(Meth meth:this.methods.get(i)){
                    Meth meth1 = new Meth(meth.methodname,meth.belongs,meth.vardecl,meth.parlist,meth.type);
                    temp1.add(meth1);
                }
                this.methods.add(temp1);
                
                temp = new Vector<Vars> ();
            //------------------------------------------------------------------    
                for(Variables variable : cl.variables){
                    Vars var1 = new Vars(variable.getIdentifier(),cl.getClassName(),variable.getType(),null);
                    this.fields.get(j).add(var1);
                    
                }
            //------------------------------------------------------------------
                
                int size = this.methods.get(i).size();  // Extend class
                Vector<Meth> buildingclass = new Vector<Meth>();
                for(Meth meth : this.methods.get(i)){
                    Meth m = new Meth(meth);
                    buildingclass.add(m);
                }
                
                for(int k = 0; k<size; k++){
                    for(Method meth : cl.methodnames){
                        if(buildingclass.get(k).methodname.compareTo(meth.getName())==0){
                            Meth m=new Meth(meth.getName(),cl.getClassName(),meth.getValues_varDeclare(),meth.getParList(),meth.getType());
                            buildingclass.set(k,m);  
                        }
                    }
                }
                
                int flag = 1;
                Vector<Meth> temp2 = new Vector<Meth>();
                for(Method method: cl.methodnames){
                    for(Meth meth : buildingclass){
                        if(meth.methodname.compareTo(method.getName())==0){
                            flag = 0;
                            break;
                        }
                    }
                    
                    if(flag ==1){
                        Meth m = new Meth(method.getName(), cl.getClassName() ,method.getValues_varDeclare(),method.getParList(),method.getType());
                        temp2.add(m);
                    }
                    flag = 1;
                }
                
                for(Meth meth: temp2){
                    buildingclass.add(meth);
                }
                this.methods.set(j, buildingclass);
            //------------------------------------------------------------------       
            }
            else{
                
                this.fields.add(new Vector<Vars>());
                this.methods.add(new Vector<Meth>());
                
                for(Variables variable : cl.variables){
                    Vars var1 = new Vars(variable.getIdentifier(),cl.getClassName(),variable.getType(),null);
                    this.fields.get(j).add(var1);
                }
                for(Method method : cl.methodnames){
                    Meth meth1 = new Meth(method.getName(),cl.getClassName(),method.getValues_varDeclare(),method.getParList(),method.getType());
                    this.methods.get(j).add(meth1);
                }
        
            }
            
            
        }
        
        return true;
    }
    
}
