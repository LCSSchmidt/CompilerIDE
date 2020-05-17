/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notfalsecompiler.controller;

import java.util.ArrayList;
import notfalsecompiler.symbolTable.Symbol;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Marcelo
 */
public class CodeGenerator {
    
    private String data;
    private String text;

    public CodeGenerator() {
        this.data = ".data\n";
        this.text = ".text\n";
    }
   
    
    public void dataSectionInsert(List<Symbol> symbols){
        for (Iterator<Symbol> it = symbols.iterator(); it.hasNext();) {
            Symbol var = it.next();
            if("int".equals(var.getTipo())){
                this.data += var.getId() + " : 0\n";
            }
        }
        System.out.println(this.data);
    }
    
    public void textInsert(String op, String lexeme){
        this.text += op + " " + lexeme + "\n";
        System.out.println(this.text);
    }
    
}
