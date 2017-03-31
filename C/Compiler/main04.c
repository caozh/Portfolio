/*
	main.c
	Name: Zhihao Cao
	NN: 04
*/

#include "header04.h"
#include "parser04.h"



main() 
{
	printf("NN: 04, Name: Zhihao Cao, Assignment 06\n");
	printf("yacc test\n");
	
	scope_count = 0;  //initial scope level
	
	clear();     //symbal table initilization
	init();
	
	printf("Start the parse.\n");
	
	yyparse();   //start the parser
	
	print_sym(); //display all symbol table entries
	
	printf("End of test. \n");
}

void error(char *str)
{
	printf("CLIP: %s\n", str);
}