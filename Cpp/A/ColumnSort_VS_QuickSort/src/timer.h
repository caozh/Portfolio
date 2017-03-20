#pragma once

#include "utility.h"


/*******************TIMER CLASS*******************/
class Timer
{
public:
	Timer();
	//constructor - turns on the timer

	double elapsed_time();
	//compute elapsed time between start and stop

	void reset();
	//restarts the timer

private:
	clock_t start_time;
	//type of value returned by system function clock()
};
/*****************END TIMER CLASS DEFINITION********/
