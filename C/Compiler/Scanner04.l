/* 
	Lex module
	Name: Zhihao
	NN: 04
*/

%{
#include <stdio.h>
#include <string.h>
#include <stdlib.h> 
#include "header04.h"
#include "parser04.h"
%}
		/*regular expression definitions */				
space            [ \t]
sp               {space}+
delim            [ \t\n]
ws               {delim}+
comment          \/\/([^\n])*
letter           [a-zA-Z]
digit            [0-9]
integer          {digit}+
ascii_char       [^\"\n]   
escaped_char     \\n      
text_string      ({ascii_char}|{escaped_char})* 
text             \"{text_string}\"
symbol           {letter}|{digit}|\_
identifier       {letter}({symbol})*

%%                    
			/* rules */
{ws}                    {/* ignore */}
{comment}               {/* ignore */}         
funct                   {return(FUNCT);}
print                   {return(PRINT);}
if                      {return(IF);}
then                    {return(THEN);}
endif                   {return(ENDIF);}
else                    {return(ELSE);}
while                   {return(WHILE);}
do                      {return(DO);}
enddo                   {return(ENDDO);}
input                   {return(INPUT);}
return                  {return(RETURN);}
continue                {return(CONTINUE);}
int                     {return(INT);}
{text}                  {install('T'); return(TEXT);}
{identifier}            {install('I'); return(IDENTIFIER);}
{integer}               {install('N'); return(INTEGER);}
"="                     {return(ASSGN);}
.                       {return(yytext[0]);}

%%                    

int counter;  	                     //indicate the newest entry in the symbol table
int Null = -1;                       //flag valriable: -1   

clear()                              //clear the symbol table
{
	counter = Null;
}

init()                               //insert pre-load items
{                            
	create("FRED", 0, 'I', 'U'); 	
}

int read(char name[16], int scope)   //search the item from symbol table
{
	int i;
	for ( i = counter; i > 0; i-- )                 //search from backward
	{
		if( !strcmp( mySymTab[i].name, name ) )     //name 
		{
			if( mySymTab[i].scope == scope )        //scope
			{
				return i;                           //found match from symbol table
			}
		}
	}
	return Null;                                  //no match found
}

int create(name, scope, type, usage)                //insert item into symbol table
	char name[16], type, usage; int scope;
{
	int ref = Null;
	if (type == 'I' && usage == 'L')
	{
		ref = atoi(name);
	}
	counter++;                                      //increment the symbol table row counter
	strcpy( mySymTab[counter].name, name );         //copy values to a new entry of table
	mySymTab[counter].type = type; 
	mySymTab[counter].usage = usage; 
	mySymTab[counter].scope = scope;
	mySymTab[counter].ref = ref;    
	
	return counter;
}

print_sym()                            //display all symbol table contents
{
	int k;                             //row count
	int space = 10;                    //padding length
	printf("SYMBOL TABLE\n");
	printf("%*s", 5, "#");             //print the table column names
	printf("%*s", 16, "Name");
	printf("%*s", space, "Scope");
	printf("%*s", space, "Type");
	printf("%*s", space, "Usage");
	printf("%*s\n",13, "Reference");	
	
	for ( k = 0; k < counter+1 ; k++ ) //diaplay all rows
	{
		printf("%5d",k);
		printf("%16s",mySymTab[k].name);
		printf("%10d",mySymTab[k].scope); 
		printf("%10c",mySymTab[k].type);
		printf("%10c",mySymTab[k].usage);
		printf("%13d\n",mySymTab[k].ref);
	}
}

install(arg)              //install lexeme into symbol table
	char arg;
{
	int index; 
	char type, usage;
	
	if (arg == 'I')       //if the input lexeme is identifier
	{
		type = 'I';
		usage = 'U';
	}
	else if (arg == 'T')  //if the input lexeme is string literal
	{
		type = 'S';
		usage = 'L';
	}
	else if (arg == 'N')  //if the input lexeme is numeric literal
	{
		type = 'I';
		usage = 'L';
	}
	
	index = read(yytext, 0);  
	if( index == Null )           //item not exist
	{                              //insert item into symbol table
		index = create(yytext, scope_count, type, usage); 
	} 
	yylval.s_ref = index;
}

//main

yywrap(){return (1);}














