package notfalsecompiler.flowcontroll;

public class SintaticResolver {

    public static int getTypeNumber(String lexem) {
        switch (lexem) {
            case "int":
            case "INTEGER":
                return SemanticTable.INT;
            case "float":
            case "REAL":
                return SemanticTable.FLO;
            case "char":
            case "CARACTER":
                return SemanticTable.CHA;
            case "bool":
                return SemanticTable.BOO;
            case "string":
            case "STRING":
                return SemanticTable.STR;
            case "sysout":
            case "sysin":
                return SemanticTable.SYSINOUT;
        }
        return -1;
    }

    public static int getOperatorNumber(String operator) {
        switch (operator) {
            case "+":
                return SemanticTable.SUM;
            case "-":
                return SemanticTable.SUB;
            case "/":
                return SemanticTable.DIV;
            case "*":
                return SemanticTable.MUL;
            case "&&":
            case "||":
            case "!":
            case ">":
            case "<":
            case ">=":
            case "<=":
            case "==":
            case "!=":
                return SemanticTable.REL;
        }
        return -1;
    }

    public static String symbolToAttrType(int symbol) {
        String token = "";
        switch (symbol) {
            case 20:
                token = "INTEGER";
                break;
            case 21:
                token = "REAL";
                break;
            case 22:
                token = "CARACTER";
                break;
            case 23:
                token = "STRING";
                break;
            case 28:
                token = "BOOL_FALSE";
                break;
            case 29:
                token = "BOOL_TRUE";
                break;
        }
        return token;
    }
}
