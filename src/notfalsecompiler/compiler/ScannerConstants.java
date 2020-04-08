package notfalsecompiler.compiler;

import notfalsecompiler.util.importador.Importador;

public interface ScannerConstants
{
    int[][] SCANNER_TABLE = new Importador().getTabela();

    int[] TOKEN_STATE = { 0,  0, 38, -1, 29, 41, -1, 53, 54, 27, 23, 48, 24, 47, 26, 10, 10, 50, 49, 31, 28, 30,  2, 51, 52, 43, 55, 42, 56, 44, 35, -1, 36, -1, -1, 45, -1, -1, 14, 40, 33, 34, 32, 39, 37, -1, 12, -1, -1, -1, 11, 15, 13, -1, 46, -1, -1, 46 };

    int[] SPECIAL_CASES_INDEXES =
        { 0, 0, 0, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16 };

    String[] SPECIAL_CASES_KEYS =
        {  "bool", "char", "do", "elif", "else", "end", "float", "for", "function", "if", "int", "string", "sysin", "sysout", "void", "while" };

    int[] SPECIAL_CASES_VALUES =
        {  6, 8, 22, 17, 18, 19, 4, 20, 7, 16, 3, 5, 57, 58, 9, 21 };

    String[] SCANNER_ERROR =
    {
        "Caractere nao esperado",
        "",
        "",
        "Erro identificando STRING",
        "",
        "",
        "Erro identificando CARACTER",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "Erro identificando STRING",
        "",
        "Erro identificando CARACTER",
        "Erro identificando MULTI_COMMENT",
        "",
        "Erro identificando REAL",
        "Erro identificando BINARY",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "Erro identificando STRING",
        "",
        "Erro identificando MULTI_COMMENT",
        "Erro identificando MULTI_COMMENT",
        "Erro identificando MULTI_COMMENT",
        "",
        "",
        "",
        "Erro identificando MULTI_COMMENT",
        "",
        "Erro identificando MULTI_COMMENT",
        "Erro identificando MULTI_COMMENT",
        ""
    };

}
