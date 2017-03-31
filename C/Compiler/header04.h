/* 
	Header file
	Name: Zhihao
	NN: 04
*/

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

symTab_type mySymTab[MAX_SYM];       //create the symbol table globally

int scope_count;