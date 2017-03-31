/* 
	Parser for CLIP 
	Name: Zhihao Cao
	NN: 04
*/

%{
	/* standard headers */
#include <stdio.h>
#include <ctype.h>
#include "header04.h"
%}

	/* reference for identifier, text, number to symbol table */
%union { int s_ref; } 

	/* tokens -- terminals */
%token         FUNCT
%token <s_ref> IDENTIFIER
%token         ASSGN
%token <s_ref> INTEGER
%token         PRINT
%token <s_ref> TEXT
%token         IF
%token         THEN
%token         ENDIF
%token         ELSE
%token         WHILE
%token         DO
%token         ENDDO
%token         INPUT
%token         RETURN
%token         INT
%token         CONTINUE

	/* precedence */
%left '+' '-'
%left '*' '/'

%%

	/* grammar rules */
program				: function_list
						{ printf("(04) Program\n"); }
					;

function_list		: function
					| function_list function
						{ printf("(04) FunctionList, Function\n"); }
					;
					
function			: FUNCT IDENTIFIER '(' parameter_list ')' statement
						{   printf("(04) Function\n"); 
							printf("(04) %s symbol\n", GetName($2));
							if (GetUsage($2) == 'U')
							{
								SetUsage($2, 'F');
							}
							else
							{
								yyerror("(04) Function name already exists.");
							}
						}
					| error
						{ printf("(04) Incorrect function syntax.\n"); }
					;	
						
parameter_list		: identifier_list
						{ printf("(04) Parameter list.\n"); }
					|	{ printf("(04) Null parameter list.\n"); }
					;
					
identifier_list		: IDENTIFIER
						{	
							printf("(04) Identifier: %s \n", GetName($1));
							if (GetUsage($1) == 'U')
							{
								SetUsage($1, 'I');
							}
							else
							{
								yyerror("(04) Identifier name already exists.");
							}							
						}
					| identifier_list ',' IDENTIFIER
						{ 	printf("(04) Identifier list\n"); 
							printf("(04) Identifier: %s \n", GetName($3));
							if (GetUsage($3) == 'U')
							{
								SetUsage($3, 'I');
							}
							else
							{
								yyerror("(04) Multiply identifier declared.");
							}
						}
					;

statement			: assignment_statement
					| return_statement
					| print_statement
					| input_statement
					| null_statement
					| if_statement
					| while_statement
					| block
					;

assignment_statement : IDENTIFIER ASSGN expression
						{ printf("(04) Assignment statement\n"); }
					
expression			: expression '+' expression
						{ printf("(04) Add\n"); }
					| expression '-' expression
						{ printf("(04) Subtract\n"); }
					| expression '*' expression
						{ printf("(04) Multiply\n"); }
					| expression '/' expression
						{ printf("(04) Divide\n"); }						
					| '(' expression ')'
						{ printf("(04) Parentheses\n"); }
					| INTEGER
						{ printf("(04) %d is the value of the interger\n", GetReference($1)); }
					| IDENTIFIER
						{ 	
							printf("(04) Identifier: %s \n", GetName($1));
							if (GetUsage($1) == 'U')
							{
								yyerror("(04) Undeclared identifier.");
							}
						}
					| IDENTIFIER '(' argument_list ')'
						{ printf("(04) Identifier ( argument_list )\n"); 
							if (GetUsage($1) == 'U')
							{
								yyerror("(04) Undefined function.");
							}
						}
					;		

argument_list		: { printf("(04) Null argument_list\n"); }
					| expression_list
						{ printf("(04) Expression list\n"); }
					;
					
expression_list		: expression
						{ printf("(04) Expression\n"); }
					| expression_list ',' expression
						{ printf("(04) Expression_ list, Expression\n"); }
					;
					
print_statement		: PRINT print_list
						{ printf("(04) Print statement\n"); }
					;

print_list			: print_item
					| print_list ',' print_item
						{ printf("(04) Print list\n"); }
					;
					
print_item			: expression
					| text_string
					;
					
text_string			: TEXT
						{ 
							printf("(04) %s is the text string\n", GetName($1));
						}
					;
					
input_item			: IDENTIFIER
						{ printf("(04) Identifier\n"); }
					;
					
input_list			: input_item
					| input_list ',' IDENTIFIER
						{ printf("(04) Input list, Identifier\n"); }
					;
					
input_statement		: INPUT input_list
						{ printf("(04) Input statement\n"); }
					;
					
return_statement	: RETURN expression
						{ printf("(04) Return statement\n"); }
					;
					
null_statement		: CONTINUE
						{ printf("(04) Null statement\n"); }
					;
					
if_statement		: IF expression THEN statement ENDIF
						{ printf("(04) If statement\n"); }
					| IF expression THEN statement ELSE statement ENDIF
						{ printf("(04) If-else statement\n"); }
					;
					
while_statement		: WHILE expression DO statement ENDDO
						{ printf("(04) While statement\n"); }
					;
					
block				: '{' declaration_list statement_list '}'
						{ printf("(04) Block\n"); }
					;
					
declaration_list	: declaration_list declaration
						{ printf("(04) Declaration_list\n"); }
					| { printf("(04) Null declaration list\n"); }
					;
					
declaration			: INT identifier_list
						{ printf("(04) Declaration\n"); }
					;
					
statement_list		: statement
						{ printf("(04) Statement\n"); }
					| statement_list statement
						{ printf("(04) Join statements\n"); }
					;
					
%%

char* GetName(int index)
{
	return mySymTab[index].name;
}

char GetUsage(int index)
{
	return mySymTab[index].usage;
}

SetUsage(int index, char usage)
{
	mySymTab[index].usage = usage;
}

int GetReference(int index)
{
	return mySymTab[index].ref;
}

SetReference(int index)
{
	mySymTab[index].ref = atoi(mySymTab[index].name);
}

int yyerror(char *str)
{
	error(str);
}
