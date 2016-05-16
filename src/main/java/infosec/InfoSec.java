package infosec;

import infosec.lexer.*;
import infosec.parser.*;
import infosec.AST.Statement.*;
import infosec.codegen.*;
import infosec.codegen.classfile.*;


public class InfoSec {
    public static void main(String[] argv) {
        CodeGen cg = new CodeGen();
        cg.newString("test");
        cg.newClass("test", false);
        cg.newClass("java/lang/Object", true);
        cg.setSuper(true);
        cg.setPublic(true);
        VirtualMethod method = new VirtualMethod("main", "void");
        method.addArg("argv", "java/lang/String", 1);
        method.addOperation(OPCode.OP_return);
        method.setStatic(true);
        method.compileTo(cg);
        cg.save("test.class");

        
        // Lexer lex = new Lexer(argv[0]);
        // Parser parser = new Parser(lex);
        //
        // Statement stmt = parser.nextStatement();
        //
        // while ( stmt != null ) {
        //     System.out.println(stmt);
        //     stmt = parser.nextStatement();
        // }
        //
        // lex.close();
    }
}
