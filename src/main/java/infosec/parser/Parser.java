package infosec.parser;

import infosec.lexer.*;
import infosec.lexer.token.*;
import infosec.AST.*;
import infosec.AST.Expression.*;
import infosec.AST.Statement.*;

public class Parser {
    private Lexer lexer;

    public String[][] op_pres_prefix = new String[][] {
        new String[] {"--", "++", "#"}
    };

    public String[][] op_pres_suffix = new String[][] {
        new String[] {"--", "++"}
    };

    public String[][] op_pres_infix = new String[][] {
        new String[] {"=", "+=", "-=", "*=", "/=", "&=", "^="},
        new String[] {"<", ">", ">=", "<=", "==", "!=", "||", "&&"},
        new String[] {"+", "-"},
        new String[] {"*", "%", "/"},
        new String[] {"<<<", "<<", ">>", ">>>", "&", "^", "|"}
    };

    private int debugLevel = 0;

    public void debug(int level, String msg) {
        if ( level >= debugLevel ) {
            System.out.println("[DEBUG] " + msg);
        }
    }

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public int getInfixPresidence(String op) {
        for ( int i = 0; i < op_pres_infix.length; i++ ) {
            for ( int j = 0; j < op_pres_infix[i].length; j++ ) {
                if ( op_pres_infix[i][j].equals(op) ) {
                    return i;
                }
            }
        }

        return -1;
    }

    public int getPrefixPresidence(String op) {
        for ( int i = 0; i < op_pres_prefix.length; i++ ) {
            for ( int j = 0; j < op_pres_prefix[i].length; j++ ) {
                if ( op_pres_prefix[i][j].equals(op) ) {
                    return i;
                }
            }
        }

        return -1;
    }

    public int getSuffixPresidence(String op) {
        for ( int i = 0; i < op_pres_suffix.length; i++ ) {
            for ( int j = 0; j < op_pres_suffix[i].length; j++ ) {
                if ( op_pres_suffix[i][j].equals(op) ) {
                    return i;
                }
            }
        }

        return -1;
    }

    public Expression nextExpression(int pres) {
        Token next = lexer.current();

        if ( next == null ) {
            return null;
        }

        Expression lhs = null;
        PrefixExpression pfx = null;

        if ( lexer.match("Special") != null ) {
            String op = ((SpecialToken) lexer.current()).value();

            if ( getPrefixPresidence(op) > -1 ) {
                pfx = new PrefixExpression(op, null);
                lexer.next();
                next = lexer.current();
            }
        }

        if ( matchSpecial("(") ) {
            lhs = nextExpression(0);

            if ( !expectSpecial(")") ) {
                return null;
            }
        }

        if ( next.type() == "Number" ) {
            lexer.next();
            lhs = new NumberExpression(((NumberToken) next).intValue());
        }
        else if ( next.type() == "Float" ) {
            lexer.next();
            lhs = new FloatExpression(((FloatToken) next).floatValue());
        }
        else if ( next.type() == "Name" ) {
            lexer.next();

            if ( matchSpecial("(") ) {
                String name = ((NameToken) next).value();
                FunctionCallExpression exp = new FunctionCallExpression(name);

                while ( (lexer.match("Special", ")") == null) && lexer.current() != null ) {
                    exp.addArg(nextExpression(0));

                    if ( lexer.match("Special", ",") == null && lexer.match("Special", ")") == null ) {
                        System.out.println("Expected , or ), got " + lexer.current());
                        return null;
                    }

                    if ( lexer.match("Special", ")") != null ) {
                        break;
                    }

                    lexer.next();
                }

                lhs = (Expression) exp;

                if ( !expectSpecial(")") ) {
                    return null;
                }
            }
            else {
                lhs = new VariableExpression(((NameToken) next).value());
            }

            if ( matchSpecial("[") ) {
                String name = ((NameToken) next).value();
                lhs = new ArrayDereferenceExpression(new VariableExpression(name), nextExpression(0));

                if ( !expectSpecial("]") ) {
                    return null;
                }
            }
        }
        else if ( next.type() == "String" ) {
            lexer.next();
            lhs = new StringExpression(((StringToken) next).value());
        }
        else if ( matchSpecial("[") ) {
            ArrayExpression aex = new ArrayExpression();

            do {
                aex.addExpression(nextExpression(0));
            } while ( matchSpecial(",") );

            lhs = aex;

            if ( !expectSpecial("]") ) {
                return null;
            }
        }
        else if ( matchSpecial("@") ) {
            String name = "";

            do {
                String read = readName();

                if ( read.equals("") ) {
                    break;
                }

                name += read + "/";
            } while ( matchSpecial(".") );

            name = name.substring(0, name.length() - 1);

            lhs = new VariableExpression(name);
        }


        while ( matchSpecial(".") ) {
            String name = readName();

            if ( matchSpecial("(") ) {
                MethodCallExpression exp = new MethodCallExpression(lhs, name);

                while ( (lexer.match("Special", ")") == null) && lexer.current() != null ) {
                    exp.addArg(nextExpression(0));

                    if ( lexer.match("Special", ",") == null && lexer.match("Special", ")") == null ) {
                        System.out.println("Expected , or ), got " + lexer.current());
                        return null;
                    }

                    if ( lexer.match("Special", ")") != null ) {
                        break;
                    }

                    lexer.next();
                }

                lhs = (Expression) exp;

                if ( !expectSpecial(")") ) {
                    return null;
                }
            }
            else {
                lhs = new FieldDereferenceExpression(lhs, name);
            }
        }

        if ( pfx != null ) {
            pfx.setRHS(lhs);
            lhs = pfx;
        }

        if ( lexer.match("Special") != null ) {
            String op = ((SpecialToken) lexer.current()).value();

            if ( getSuffixPresidence(op) != -1 ) {
                lexer.next();
                lhs = new SuffixExpression(op, lhs);

                Token tmp = lexer.current();

                if ( !(tmp instanceof SpecialToken) ) {
                    return lhs;
                }

                op = ((SpecialToken) tmp).value();
            }

            while ( lexer.match("Special") != null ) {
                op = ((SpecialToken) lexer.current()).value();
                int c_pres = getInfixPresidence(op);

                if ( c_pres != -1 ) {
                    lexer.next();

                    Expression rhs = nextExpression(c_pres);

                    if ( rhs instanceof InfixExpression ) {
                        InfixExpression a = (InfixExpression) rhs;

                        if ( c_pres > getInfixPresidence(a.getOP()) ) {
                            lhs = (Expression) new InfixExpression(new InfixExpression(lhs, op, a.getLHS()), a.getOP(), a.getRHS());
                        }
                        else {
                            lhs = (Expression) new InfixExpression(lhs, op, rhs);
                        }
                    }
                    else {
                        lhs = (Expression) new InfixExpression(lhs, op, rhs);
                    }
                }
                else {
                    break;
                }
            }
        }

        return lhs;
    }

    public String readName() {
        NameToken tkn = (NameToken) lexer.match("Name");

        if ( tkn == null ) {
            return "";
        }

        lexer.next();

        return tkn.value();
    }

    public Type readType() {
        String basicType = readName();

        if ( basicType.equals("") ) {
            System.out.println("Failed to read a name for a type.");
            return null;
        }

        int arrayDepth = 0;

        while ( matchSpecial("[") ) {
            if ( !expectSpecial("]") ) {
                System.out.println("Failed to match a [ with a ] in a type.");
                return null;
            }

            arrayDepth++;
        }

        return new Type(basicType, arrayDepth);
    }

    public Block readBlock() {
        if ( lexer.match("Special", "{") == null ) {
            return new Block(nextStatement());
        }

        lexer.next();

        Block block = new Block();
        Statement stmt = nextStatement();

        while ( stmt != null ) {
            block.addStatement(stmt);
            stmt = nextStatement();
        }

        if ( !expectSpecial("}") ) {
            return null;
        }

        return block;
    }

    public boolean matchName(String token) {
        if ( lexer.match("Name", token) == null ) {
            return false;
        }

        lexer.next();

        return true;
    }

    public boolean matchSpecial(String token) {
        if ( lexer.match("Special", token) == null ) {
            return false;
        }

        lexer.next();

        return true;
    }

    public boolean expectName(String token) {
        if ( lexer.match("Name", token) == null ) {
            System.out.println("Expected a Name<" + token + "> but found " + lexer.current());
            return false;
        }

        lexer.next();

        return true;
    }

    public boolean expectSpecial(String token) {
        if ( lexer.match("Special", token) == null ) {
            System.out.println("Expected a " + token + " but found " + lexer.current());
            return false;
        }

        lexer.next();

        return true;
    }

    public String readString() {
        StringToken tkn = (StringToken) lexer.match("String");

        if ( tkn == null ) {
            return "";
        }

        lexer.next();

        return tkn.value();
    }

    public int getCurrentLine() {
        return lexer.current().getLineNumber();
    }

    public Statement nextStatement() {
        debug(1, "Reading a statement.");
        Token next = lexer.current();

        if ( next instanceof NameToken ) {
            NameToken let = (NameToken) next;

            if ( matchName("var") ) {
                String name = readName();

                if ( name.equals("") ) {
                    System.out.println("var must be followed by a name.");
                    return null;
                }

                Type type = null;

                if ( matchSpecial(":") ) {
                    type = readType();

                    if ( type == null ) {
                        return null;
                    }
                }
                else {
                    type = new Type("void", 0);
                }

                Expression value = null;

                if ( lexer.match("Special", "=") != null ) {
                    lexer.next();
                    value = nextExpression(0);

                    if ( value == null ) {
                        System.out.println("Variable declaration equality must be followed by an expression.");
                        return null;
                    }
                }
                else {
                    if ( type.getBasicType().equals("void") ) {
                        System.out.println("Can't implicitly type a variable without a value.");
                        return null;
                    }
                }

                if ( !expectSpecial(";") ) {
                    return null;
                }

                return new VariableDecStatement(name, type, value);
            }
            else if ( matchName("if") ) {
                Expression condition = nextExpression(0);
                Block block = readBlock();
                Block elseBlock = null;

                if ( lexer.match("Name", "else") != null ) {
                    lexer.next();
                    elseBlock = readBlock();
                }

                return new IfStatement(condition, block, elseBlock);
            }
            else if ( matchName("for") ) {
                Statement initial = nextStatement();
                Expression condition = nextExpression(0);

                if ( !expectSpecial(";") ) {
                    return null;
                }

                Statement each = nextStatement();
                Block block = readBlock();

                return new ForStatement(initial, condition, each, block);
            }
            else if ( matchName("while") ) {
                Expression condition = nextExpression(0);
                Block block = readBlock();

                return new WhileStatement(condition, block);
            }
            else if ( matchName("range") ) {
                String variable = readName();

                if ( variable.equals("") ) {
                    System.out.println("Expected a variable, but got a " + lexer.current());
                    return null;
                }

                if ( !expectName("from") ) {
                    return null;
                }

                Token tkn = lexer.next();

                if ( !tkn.type().equals("Number") ) {
                    System.out.println("Expected a number, but got a " + tkn);
                    return null;
                }

                int start = ((NumberToken) tkn).intValue();

                if ( !expectName("to") ) {
                    return null;
                }

                tkn = lexer.next();

                if ( !tkn.type().equals("Number") ) {
                    System.out.println("Expected a number, but got a " + tkn);
                    return null;
                }

                int end = ((NumberToken) tkn).intValue();
                Block block = readBlock();

                return new RangeStatement(variable, start, end, block);
            }
            else if ( matchName("fun") ) {
                String fnName = readName();

                if ( fnName.equals("") ) {
                    System.out.println("Expected a name, got a " + lexer.current());
                    return null;
                }

                if ( !expectSpecial("(") ) {
                    return null;
                }

                FunctionDecStatement dec = new FunctionDecStatement(fnName);

                while ( lexer.current() != null ) {
                    String argName = readName();

                    if ( argName.equals("") )  {
                        break;
                    }

                    if ( !expectSpecial(":") ) {
                        return null;
                    }

                    Type type = readType();

                    if ( type == null ) {
                        return null;
                    }

                    dec.addArg(argName, type);

                    if ( !matchSpecial(",") ) {
                        break;
                    }
                }

                if ( !expectSpecial(")") ) {
                    return null;
                }

                if ( matchSpecial(":") ) {
                    Type fnRet = readType();

                    if ( fnRet == null ) {
                        return null;
                    }

                    dec.setRetType(fnRet);
                }
                else {
                    dec.setRetType(new Type("void", 0));
                }

                dec.setBlock(readBlock());

                return dec;
            }
            else if ( matchName("class") ) {
                String name = readName();

                if ( name.equals("") ) {
                    return null;
                }

                ClassStatement stmt = new ClassStatement(name);

                if ( matchSpecial(":") ) {
                    do {
                        stmt.addInheritor(readName());
                    } while ( matchSpecial(",") );
                }

                stmt.setBlock(readBlock());

                return stmt;
            }
            else {
                Expression exp = nextExpression(0);

                if ( exp == null ) {
                    return null;
                }

                if ( !expectSpecial(";") ) {
                    return null;
                }

                return new ExpressionStatement(exp);
            }
        }
        else if ( next instanceof SpecialToken ) {
            Expression exp = nextExpression(0);

            if ( exp == null ) {
                return null;
            }

            if ( !expectSpecial(";") ) {
                return null;
            }

            return new ExpressionStatement(exp);
        }

        return null;
    }
}
