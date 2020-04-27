package notfalsecompiler.symbolTable;

import notfalsecompiler.flowcontroll.ScopeStack;

public class Symbol {

    private String id;
    private String tipo;
    private boolean ini;
    private boolean usada;
    private String escopo;
    private boolean param;
    private int pos;
    private boolean vet;
    private boolean matriz;
    private boolean ref;
    private boolean func;

    public Symbol(String id, String tipo, boolean ini, boolean usada, String escopo, boolean param, int pos, boolean vet, boolean matriz, boolean ref, boolean func) {
        this.id = id;
        this.tipo = tipo;
        this.ini = ini;
        this.usada = usada;
        this.escopo = escopo;
        this.param = param;
        this.pos = pos;
        this.vet = vet;
        this.matriz = matriz;
        this.ref = ref;
        this.func = func;
    }

    public Symbol(String name, String type, String escopo) {
        this.id = name;
        this.tipo = type;
        this.escopo = escopo;
    }

    public Symbol(String id, String tipo, String escopo, int pos) {
        this.id = id;
        this.tipo = tipo;
        this.escopo = escopo;
        this.pos = pos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isIni() {
        return ini;
    }

    public void setIni(boolean ini) {
        this.ini = ini;
    }

    public boolean isUsada() {
        return usada;
    }

    public void setUsada(boolean usada) {
        this.usada = usada;
    }

    public String getEscopo() {
        return escopo;
    }

    public void setEscopo(String escopo) {
        this.escopo = escopo;
    }

    public boolean isParam() {
        return param;
    }

    public void setParam(boolean param) {
        this.param = param;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public boolean isVet() {
        return vet;
    }

    public void setVet(boolean vet) {
        this.vet = vet;
    }

    public boolean isMatriz() {
        return matriz;
    }

    public void setMatriz(boolean matriz) {
        this.matriz = matriz;
    }

    public boolean isRef() {
        return ref;
    }

    public void setRef(boolean ref) {
        this.ref = ref;
    }

    public boolean isFunc() {
        return func;
    }

    public void setFunc(boolean func) {
        this.func = func;
    }
}
