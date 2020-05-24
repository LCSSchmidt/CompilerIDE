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
                this.data += var.getId() + " : 0\n";
            }
        }
//        System.out.println(this.data);
    }

    public void LD(String lexeme) {
        this.text += "\tLD\t" + lexeme + "\n";
    }
    
    public void LDI(String lexeme) {
        this.text += "\tLDI\t" + lexeme + "\n";
    }
    
    public void LDV(String lexeme) {
        this.text += "\tLDV\t" + lexeme + "\n";
    }
    
    public void ADD(String lexeme) {
        this.text += "\tADD\t" + lexeme + "\n";
    }

    public void ADDI(String lexeme) {
        this.text += "\tADDI\t" + lexeme + "\n";
    }

    public void SUB(String lexeme) {
        this.text += "\tSUB\t" + lexeme + "\n";
    }

    public void STO(String lexeme) {
        this.text += "\tSTO\t" + lexeme + "\n";
    }

    public void STOV(String lexeme) {
        this.text += "\tSTOV\t" + lexeme + "\n";
    }
    
    public void BEQ(String lexeme) {
        this.text += "\tBEQ\t" + lexeme + "\n";
    }
    
    public void BNE(String lexeme) {
        this.text += "\tBNE\t" + lexeme + "\n";
    }
    
    public void BGT(String lexeme) {
        this.text += "\tBGT\t" + lexeme + "\n";
    }
    
    public void BGE(String lexeme) {
        this.text += "\tBGE\t" + lexeme + "\n";
    }
    
    public void BLT(String lexeme) {
        this.text += "\tBLT\t" + lexeme + "\n";
    }
    
    public void BLE(String lexeme) {
        this.text += "\tBLE\t" + lexeme + "\n";
    }
    
    public void textInsert(String op, String lexeme) {
        this.text += "\t" + op + "\t" + lexeme + "\n";
//        System.out.println(this.text);
    }

    public void concatBipideText(String bipide) {
        this.text += bipide;
    }

    public void createCode() {
        this.code = this.data + this.text + addHLT();
    }

    public String addHLT() {
        return "\tHLT 0";
    }

    @Override
    public String toString() {
        return this.code;
    }
}
