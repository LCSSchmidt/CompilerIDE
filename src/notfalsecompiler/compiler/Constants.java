package notfalsecompiler.compiler;

public interface Constants extends ScannerConstants, ParserConstants
{
    int EPSILON  = 0;
    int DOLLAR   = 1;

    int t_ID = 2;
    int t_TYPE_INT = 3;
    int t_TYPE_FLOAT = 4;
    int t_TYPE_STRING = 5;
    int t_TYPE_BOOL = 6;
    int t_FUNCTION = 7;
    int t_TYPE_CHAR = 8;
    int t_TYPE_VOID = 9;
    int t_INTEGER = 10;
    int t_REAL = 11;
    int t_CARACTER = 12;
    int t_STRING = 13;
    int t_HEX = 14;
    int t_BINARY = 15;
    int t_IF = 16;
    int t_ELIF = 17;
    int t_ELSE = 18;
    int t_END = 19;
    int t_FOR = 20;
    int t_WHILE = 21;
    int t_DO = 22;
    int t_ADDITION = 23;
    int t_SUBTRACTION = 24;
    int t_SUB_NEGATION = 25;
    int t_DIVISION = 26;
    int t_MULTIPLICATION = 27;
    int t_ASSIGN = 28;
    int t_MOD = 29;
    int t_GREATER = 30;
    int t_SMALLER = 31;
    int t_GREATER_EQUAL = 32;
    int t_SMALLER_EQUAL = 33;
    int t_EQUAL = 34;
    int t_NOT_EQUAL = 35;
    int t_AND = 36;
    int t_OR = 37;
    int t_NOT = 38;
    int t_RIGHT_SHIFT = 39;
    int t_LEFT_SHIFT = 40;
    int t_BIT_AND = 41;
    int t_BIT_OR = 42;
    int t_BIT_XOR = 43;
    int t_BIT_NOT = 44;
    int t_COMMENT = 45;
    int t_MULTI_COMMENT = 46;
    int t_DOT = 47;
    int t_COMMA = 48;
    int t_SEMICOLON = 49;
    int t_COLON = 50;
    int t_OP_SQUARE_BRACKETS = 51;
    int t_CL_SQUARE_BRACKETS = 52;
    int t_OP_PARENTHESES = 53;
    int t_CL_PARENTHESES = 54;
    int t_OP_KEY = 55;
    int t_CL_KEY = 56;
    int t_SYS_IN = 57;
    int t_SYS_OUT = 58;

}
