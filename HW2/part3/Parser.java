/* *** This file is given as part of the programming assignment. *** */

public class Parser {
    private SymbolTable st = new SymbolTable();

    // tok is global to all these parsing methods;
    // scan just calls the scanner's scan method and saves the result in tok.
    private Token tok; // the current token
    private void scan() {
	tok = scanner.scan();
    }

    private Scan scanner;
    Parser(Scan scanner) {
	this.scanner = scanner;
	scan();
	program();
	if( tok.kind != TK.EOF )
	    parse_error("junk after logical end of program");
    }

    private void program() {
	block();
    }

    private void block() {
        st.push();
        declaration_list();
        statement_list();
        st.pop();
    }

    private void declaration_list() {
	// below checks whether tok is in first set of declaration.
	// here, that's easy since there's only one token kind in the set.
	// in other places, though, there might be more.
	// so, you might want to write a general function to handle that.
	while( is(TK.DECLARE) ) {
	    declaration();
	}
    }

    private void declaration() {
	mustbe(TK.DECLARE);
              st.declare(tok.string, tok.lineNumber);
	mustbe(TK.ID);
	while( is(TK.COMMA) ) {
	    scan();
                  st.declare(tok.string, tok.lineNumber);
	    mustbe(TK.ID);
	}
    }

    // new implement
    private void statement_list() {
    	while( is(TK.TILDE) || is(TK.ID) || is(TK.PRINT) || is(TK.DO) || is(TK.IF)) {
    	    statement();
    	}
    }

    // statement ::= assignment | print | do | if
    private void statement() {
    	if(is(TK.TILDE)) {
    		assignment();
    	} else if(is(TK.ID)) {
    		assignment();
    	} else if(is(TK.PRINT)) {
    		print();
    	} else if(is(TK.DO)) {
    		doo();
    	} else if(is(TK.IF)) {
    		iff();
    	}
    }

    // print ::= '!' expr
    private void print() {
    	mustbe(TK.PRINT);
    	expression();
    }

    // assignment ::= ref_id '=' expr
    private void assignment() {
    	ref_id();
    	mustbe(TK.ASSIGN);
    	expression();
    }

    // ref_id ::= ['~' [number]] id
    private void ref_id() {
              int lvl = -1;
    	if(is(TK.TILDE)) {
    		mustbe(TK.TILDE);
    		if(is(TK.NUM)) {
                                          lvl = Integer.parseInt(tok.string); // level of scope
    			mustbe(TK.NUM);
    		} else {
                                           st.check_global(tok.string, tok.lineNumber);
                            }
    	} 
              if(lvl == -1) {
                     st.check(tok.string, tok.lineNumber);
    	       mustbe(TK.ID);
              } else { 
                     st.check_scoping(tok.string, tok.lineNumber ,lvl);
                     mustbe(TK.ID);
              }
    }

    private void doo() {
    	mustbe(TK.DO);
    	guard();
    	mustbe(TK.ENDDO);
    }

    private void iff() {
    	mustbe(TK.IF);
    	guard();
    	while(is(TK.ELSEIF)) {
    		mustbe(TK.ELSEIF);
    		guard();
    	}
    	if(is(TK.ELSE)) {
    		mustbe(TK.ELSE);
    		block();
    	}
    	mustbe(TK.ENDIF);
    }

    // guarded_command ::= expr ':' block
    private void guard() {
    	expression();
    	mustbe(TK.THEN);
    	block();
    }

    // expr ::= term {addop term}
    private void expression() {
    	term();
    	// addop ::= '+' | '-'
    	while(is(TK.PLUS) || is(TK.MINUS))
    	{
    		if(is(TK.PLUS)) {
    			mustbe(TK.PLUS);
    		} else {
    			mustbe(TK.MINUS);
    		}
    		term();
    	}
    }

    // term ::= factor {multop factor}
    private void term() {
    	factor();
    	// multop ::= '*' | '/'
    	while(is(TK.TIMES) || is(TK.DIVIDE)) {
    		if(is(TK.TIMES)) {
    			mustbe(TK.TIMES);
    		} else {
    			mustbe(TK.DIVIDE);
    		}
    		factor();
    	}
    }

    // factor ::= '(' expr ')' | ref_id | number
    private void factor() {
    	if(is(TK.LPAREN)) {
    		mustbe(TK.LPAREN);
    		expression();
    		mustbe(TK.RPAREN);
    	} else if(is(TK.TILDE) || is(TK.ID)) {
    		ref_id();
    	} else if(is(TK.NUM)) {
    		mustbe(TK.NUM);
    	}
    }

    // is current token what we want?
    private boolean is(TK tk) {
        return tk == tok.kind;
    }

    // ensure current token is tk and skip over it.
    private void mustbe(TK tk) {
	if( tok.kind != tk ) {
	    System.err.println( "mustbe: want " + tk + ", got " +
				    tok);
	    parse_error( "missing token (mustbe)" );
	}
	scan();
    }

    private void parse_error(String msg) {
	System.err.println( "can't parse: line "
			    + tok.lineNumber + " " + msg );
	System.exit(1);
    }
}
