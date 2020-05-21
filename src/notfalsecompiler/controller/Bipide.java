package notfalsecompiler.controller;

import java.util.ArrayList;
import notfalsecompiler.symbolTable.Symbol;
import java.util.Iterator;
import java.util.List;

public class Bipide {

    private String data;
    private String text;
    private String code;

    public Bipide() {
        this.data = ".data\n\t";
        this.text = ".text\n";
        this.code = "";
    }

    public void dataSectionInsert(List<Symbol> symbols) {
        for (Iterator<Symbol> it = symbols.iterator(); it.hasNext();) {
            Symbol var = it.next();
            if ("int".equals(var.getTipo())) {
                this.data += var.getId() + " : 0\n\t";
            }
        }
//        System.out.println(this.data);
    }

    public void textInsert(String op, String lexeme) {
        this.text += "\t" + op + "\t" + lexeme + "\n";
//        System.out.println(this.text);
    }

    public void concatBipideText(String bipide) {
        this.text += bipide;
    }
    
    public void createCode(){
        this.code = this.data + this.text + addHLT();
    }
    
    public String addHLT(){
        return "\tHLT 0";
    }
    
    @Override
    public String toString() {
        return this.code;
    }
}
