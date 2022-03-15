/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intermidiate_code_generator.symbol_table;

import java.util.Vector;

/**
 *
 * @author lumberjack
 */
public class Symbol {
    
    String name ;
    String type ;
    String dirives;
    Vector<Symbol> expList;
    String line;
    
    
    public String regname;
    public String isDatamember;
    

    public void setLine(String line) {
        this.line = line;
    }
    

    public String getLine() {
        return line;
    }

    public Symbol() {
    
        
    }

    public Symbol(String name, String type, String dirives) {
        this.name = name;
        this.type = type;
        this.dirives = dirives;
    }
    
    public Vector<Symbol> getExpList() {
        return expList;
    }

    public Symbol(String name, String type, String dirives, Vector<Symbol> expList) {
        this.name = name;
        this.type = type;
        this.dirives = dirives;
        this.expList = expList;
    }

    public void setExpList(Vector<Symbol> expList) {
        this.expList = expList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Symbol(String name, String type, String dirives, String line) {
        this.name = name;
        this.type = type;
        this.dirives = dirives;
        this.line = line;
    }

    public void setDirives(String dirives) {
        this.dirives = dirives;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDirives() {
        return dirives;
    }
    
}
