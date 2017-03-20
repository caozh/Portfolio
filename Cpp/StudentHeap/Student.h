#ifndef STUDENT_H
#define STUDENT_H
#include <string>
#include <iostream>

using std::cout;
using std::string;

class Student
{
    public:
        Student();
        void PrintData();

        string GetFirstName();
        string GetLastName();
        string GetAddress1();
        string GetAddress2();
        string GetCity();
        string GetState();
        string GetBirthMonth();
        string GetBirthYear();
        string GetBirthDay();
        string GetCompletionMonth();
        string GetCompletionDay();
        string GetCompletionYear();
        string GetGPA();
        string GetCreditHours();
        string GetZip();

        void SetFirstName(string);
        void SetLastName(string);
        void SetAddress1(string);
        void SetAddress2(string);
        void SetCity(string);
        void SetState(string);
        void SetBirthMonth(string);
        void SetBirthYear(string);
        void SetBirthDay(string);
        void SetCompletionMonth(string);
        void SetCompletionDay(string);
        void SetCompletionYear(string);
        void SetGPA(string);
        void SetCreditHours(string);
        void SetZip(string);
    private:
        string firstName;
        string lastName;
        string address1;
        string address2;
        string city;
        string state;
        string birthMonth;
        string birthYear;
        string birthDay;
        string completionMonth;
        string completionDay;
        string completionYear;
        string gpa;
        string creditHours;
        string zip;
};

#endif
