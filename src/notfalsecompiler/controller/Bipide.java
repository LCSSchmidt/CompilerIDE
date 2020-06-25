package notfalsecompiler.controller;

import java.util.ArrayList;
import notfalsecompiler.symbolTable.Symbol;
import java.util.Iterator;
import java.util.List;
import notfalsecompiler.compiler.Semantico;

public class Bipide {

    private String data;
    private String text;
    private String code;

    public Bipide() {
        this.data = ".data\n";
        this.text = ".text\n";
        this.code = "";
    }

    public void dataSectionInsert(List<Symbol> symbols) {
        String vetDecl = null;
        String scope = null;

        for (Iterator<Symbol> it = symbols.iterator(); it.hasNext();) {
            Symbol var = it.next();
            if ("int".equals(var.getTipo())) {
                scope = var.getEscopo();
                if (scope.toUpperCase().equals("GLOBAL0")) {
                    if (var.isVet()) {
                        vetDecl = String.format("%0" + var.getVetLength() + "d", 0).replaceAll("0", ",0").replaceFirst(",", "");
                        this.data += "\t" + var.getId() + " : " + vetDecl + "\n";
                    } else if (!var.isFunc()) {
                        this.data += "\t" + var.getId() + " : 0\n";
                    }
                } else {
                    if (var.isVet()) {
                        vetDecl = String.format("%0" + var.getVetLength() + "d", 0).replaceAll("0", ",0").replaceFirst(",", "");
                        this.data += "\t" + Semantico.getScopeName(scope) + "_" + var.getId() + " : " + vetDecl + "\n";
                    } else {
                        this.data += "\t" + Semantico.getScopeName(scope) + "_" + var.getId() + " : 0\n";
                    }
                }
            }
        }
//        System.out.println(this.data);
    }

    public void removeLastLabel(String lastScopeName) {
        this.text = this.text.split(lastScopeName.toUpperCase() + ":\n")[0];
    }
    
    public void removeLastLine() {
        String strAux = this.text.substring(0, this.text.lastIndexOf("\t")); 
        this.text = strAux.substring(0, strAux.lastIndexOf("\n")) + "\n";
    }

    public void replaceJMPS(String oldJumpName, String newJumpName) {
        this.text = this.text.replaceAll("\tJMP\t" + oldJumpName.toUpperCase() + "\n", "\tJMP\t" + newJumpName.toUpperCase() + "\n");
    }

    public void addLabel(String labelName) {
        this.text += labelName.toUpperCase() + ":\n";
    }

    public void JMP(String labelName) {
        this.text += "\tJMP\t" + labelName.toUpperCase() + "\n";
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

    public void CALL(String funcName) {
        this.text += "\tCALL\t" + funcName + "\n";
    }
    
    public void return0() {
        this.text += "\tRETURN\t0\n";
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
