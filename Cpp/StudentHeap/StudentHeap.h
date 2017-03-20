#ifndef STUDENTHEAP_H
#define STUDENTHEAP_H
#include "Student.h"
#include <fstream>
#include <string>
#include <sstream>
#include <iostream>
#include <algorithm>
#include <vector>

class StudentHeap
    {
        public:
            StudentHeap();
            virtual ~StudentHeap();
            void LoadFromFile(std::string filename);
            void DumpData();
            void PrintNames();
        private:
            std::vector<Student> studs;
    };

#endif
