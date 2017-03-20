/* A Heap Of Students
Author: Zhihao Cao
Purpose: To make a list of students, place them on a heap and sort them alphabetically.
 */

#include "StudentHeap.h"

int main()
    {
        StudentHeap database;
        database.LoadFromFile("students");
        database.DumpData();
        database.PrintNames();
        return 0;
    }
