/* 
	Name: Zhihao
	NN: 04
*/

%{
	                /*Ddeclarations */
#define FUNCT      300
#define ID         301
#define ASSGN      302
#define INTEGER    303
#define PRINT      304
#define TEXT       305
#define IF         306
#define THEN       307
#define ENDIF      308
#define ELSE       309
#define WHILE      310
#define DO         311
#define ENDDO      312
#define INPUT      313
#define RETURN     314
#define INT        315
#define END        316

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
id               {letter}({symbol})*
end_source_code  end

%%                    
			/* rules */
{ws}                    {/* ignore */}
{comment}               {/* ignore */}
{end_source_code}       {return END;}             
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
int                     {return(INT);}
{text}                  {install('L'); return(TEXT);}
{id}                    {install('I'); return(ID);}
{integer}               {install('N'); return(INTEGER);}
"="                     {return(ASSGN);}
.                       {return(yytext[0]);}

%%                    

//#include "SymTab04.c"
#include <stdio.h>
#include <string.h>
#include <stdlib.h> 

#define MAX_SYM 200

struct symTab            //declear the symbol table struct
{	
	char name[16];       //any ascii characters
	char type;           //I->integer; S->string; 
	char usage;          //I->identifier; L->literal; F->function;
	int scope;           //[0-9]
	int ref;             //default value: -1
};

typedef struct symTab symTab_type;   //define the struct type

int counter;  	                     //indicate the newest entry in the symbol table
int negOne = -1;                     //flag valriable: -1
symTab_type mySymTab[MAX_SYM];       //create the symbol table globally

clear()                              //clear the symbol table
{
	counter = 0;
}

init()                               //insert pre-load items
{                            
	create("FRED", 0, 'I', 'I'); 	
}

int read(name, scope, type, usage)   //search the item from symbol table
	char name[16], type, usage; int scope;
{
	int i;
	for ( i = counter; i > 0; i-- )                 //search from backward
	{
		if( !strcmp( mySymTab[i].name, name ) )     //name 
		{
			if( mySymTab[i].type == type )          //type
			{
				if( mySymTab[i].usage == usage )    //usage
				{
					if( mySymTab[i].scope == scope )//scope
					{
						return i;                   //found match from symbol table
					}
				}
			}
		}
	}
	return negOne;                                  //no match found
}

int create(name, scope, type, usage)                //insert item into symbol table
	char name[16], type, usage; int scope;
{
	int ref = negOne;
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
	
	for ( k = 1; k < counter + 1; k++ ) //diaplay all rows
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
	char arg;             //represent the type of lexeme
{
	int result; 
	int negOne = -1; 
	char type, usage;
	
	if (arg == 'I')       //if the input lexeme is identifier
	{
		type = 'I';
		usage = 'I';
	}
	else if (arg == 'L')  //if the input lexeme is literal
	{
		type = 'S';
		usage = 'L';
	}
	else if (arg == 'N')  //if the input lexeme is number
	{
		type = 'I';
		usage = 'L';
	}
	result = read(yytext, 0, type, usage);  
	if( result == negOne )           //item not exist
	{                                //insert item into symbol table
		create(yytext, 0, type, usage); 
	} else {                         //item exists
		//printf( "This item already exists in symbol table row[%d]\n", result );
	}
}

main()                /* main program */
{
	int i = 0;
	
	printf( "NN: 04, Name: Zhihao Cao, Assignment 04\n" );
	
	clear();
	init();
	print_sym();
		
	while(i != 316) {    //while end lexeme is not detected
		i = yylex();
		printf("%3d", i) ;
		printf(" returned value: %s\n", yytext) ;
	}
	print_sym();
	
	printf("End of test.\n");
}

yywrap(){return (1);}














