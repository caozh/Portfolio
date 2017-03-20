#include "Student.h"


Student::Student()
    {

    }

string Student::GetFirstName()
    {
        return firstName;
    }
string Student::GetLastName()
    {
        return lastName;
    }
string Student::GetAddress1()
    {
        return address1;
    }
string Student::GetAddress2()
    {
        return address2;
    }
string Student::GetCity()
    {
        return city;
    }
// This is going to get tedious, I'm not going to bother tabbing braces
// anymore.
string Student::GetState()
{
    return state;
}
string Student::GetBirthMonth()
{
    return birthMonth;
}
string Student::GetBirthYear()
{
    return birthYear;
}
string Student::GetBirthDay()
{
    return birthDay;
}
string Student::GetCompletionMonth()
{
    return completionMonth;
}
string Student::GetCompletionDay()
{
    return completionDay;
}
string Student::GetCompletionYear()
{
    return completionYear;
}
string Student::GetGPA()
{
    return gpa;
}
string Student::GetCreditHours()
{
    return creditHours;
}
void Student::SetFirstName(string name)
{
    firstName = name;
}
void Student::SetLastName(string name)
{
    lastName = name;
}
void Student::SetAddress1(string addr1)
{
    address1 = addr1;
}
void Student::SetAddress2(string addr2)
{
    address2 = addr2;
}
void Student::SetCity(string ci)
{
    city = ci;
}
void Student::SetState(string st)
{
    state = st;
}
void Student::SetBirthMonth(string bmon)
{
    birthMonth = bmon;
}
void Student::SetBirthYear(string byear)
{
    birthYear = byear;
}
void Student::SetBirthDay(string bday)
{
    birthDay = bday;
}
void Student::SetCompletionMonth(string mon)
{
    completionMonth = mon;
}
void Student::SetCompletionDay(string day)
{
    completionDay = day;
}
void Student::SetCompletionYear(string year)
{
    completionYear = year;
}
void Student::SetGPA(string gp)
{
    gpa = gp;
}
void Student::SetCreditHours(string crh)
{
    creditHours = crh;
}

void Student::SetZip(string zip)
{
    zip = zip;
}

string Student::GetZip()
{
    return zip;
}

void Student::PrintData()
{
    cout << "First Name: " << firstName << " Last Name: " << lastName << " Street Address: " << address1 << " City: " << city << " Zip: " << zip << " Birthday: "
     << birthMonth << " " << birthDay << ", " << birthYear << " Graduation Date: " << completionMonth << " " << completionDay << ", " << completionYear << " GPA: "
     << gpa << " Credit Hours: " << creditHours << "\n";
}
