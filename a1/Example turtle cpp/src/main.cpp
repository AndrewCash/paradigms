/* The contents of this file are dedicated to the public domain (http://creativecommons.org/publicdomain/zero/1.0/). */

#include <stdio.h>
#include <stdlib.h>
#include <exception>
#include <iostream>
#include <vector>
#ifdef WINDOWS
#	include <windows.h>
#else
#	include <unistd.h>
#endif
#include "Model.h"
#include "View.h"
#include "Controller.h"

using std::cerr;
using std::cout;
using std::vector;


void mili_sleep(unsigned int nMiliseconds)
{
#ifdef WINDOWS
	MSG aMsg;
	while(PeekMessage(&aMsg, NULL, WM_NULL, WM_NULL, PM_REMOVE))
	{
		TranslateMessage(&aMsg);
		DispatchMessage(&aMsg);
	}
	SleepEx(nMiliseconds, 1);
#else
	nMiliseconds ? usleep(nMiliseconds * 1024) : sched_yield();
#endif
}



int main(int argc, char *argv[])
{
	int nRet = 0;
	try
	{
		bool keepRunning = true;
		Model m;
		View v(m, 500, 500);
		Controller c(m, &keepRunning);
		while(keepRunning)
		{
			m.update();
			v.update();
			mili_sleep(30);
			c.update();
		}
	}
	catch(const std::exception& e)
	{
		cerr << e.what() << "\n";
		nRet = 1;
	}

	return nRet;
}

