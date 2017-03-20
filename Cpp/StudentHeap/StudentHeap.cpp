#include "StudentHeap.h"


StudentHeap::~StudentHeap()
    {
        //destructor
    }

    StudentHeap::StudentHeap()
    {
        //constructor
        studs.clear();
    }

void StudentHeap::LoadFromFile(std::string filename)
    {
        std::ifstream in_stream(filename.c_str());
        std::vector<std::string> file_lines;
        while(!in_stream.eof())
            {
                std::string temp_string;
                getline(in_stream, temp_string);
                file_lines.push_back(temp_string);
            } //end while
        StringSplitter splitter;
        in_stream.close();
        studs.clear();

        for (unsigned int i = 0; i < file_lines.size() - 1; i++)
            {
                std::vector<std::string> data = splitter.split(file_lines[i], '|');
                Student temp_student;
                temp_student.SetFirstName(data[0]);
                temp_student.SetLastName(data[1]);
                temp_student.SetAddress1(data[2]);
                temp_student.SetCity(data[3]);
                temp_student.SetZip(data[4]);
                temp_student.SetBirthMonth(data[5]);
                temp_student.SetBirthDay(data[6]);
                temp_student.SetBirthYear(data[7]);
                temp_student.SetCompletionMonth(data[8]);
                temp_student.SetCompletionDay(data[9]);
                temp_student.SetCompletionYear(data[10]);
                temp_student.SetGPA(data[11]);
                temp_student.SetCreditHours(data[12]);
                studs.push_back(temp_stud);
            }
    }

void StudentHeap::DumpData()
    {
        for (unsigned int r = 0; i < studs.size(); r++)
            {
                studs[r].PrintData();
            }
    }

bool sort_comparator(Student x, Student y)
    {
        string namex = x.GetlastName();
        string namey = y.GetlastName();
        if (namex.compare(namey)<=0)
            return true;
        else
            return false;
    }

void StudentHeap::PrintNamesAlphabetically()
    {
        std::vector<Student> studs_copy(studs); // Makes a temporary copy of student heap
        sort(studs_copy.begin(), studs_copy.end(), sort_comparator);
        for (int x = 0; i < studs_copy.size(); x++)
            {
                std::cout << studs_copy[x].GetlastName() << " " << studs_copy[x].GetfirstName() << "\n";
            }
    }
