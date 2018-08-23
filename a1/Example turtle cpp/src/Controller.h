/* The contents of this file are dedicated to the public domain (http://creativecommons.org/publicdomain/zero/1.0/). */

#ifndef CONTROLLER_H
#define CONTROLLER_H

#include "Model.h"
#include <SDL2/SDL.h>


class Controller
{
public:
	Model& m_model;
	bool* m_pKeepRunning;

public:
	// Constructor
	Controller(Model& m, bool* pKeepRunning);

	// Destructor
	virtual ~Controller();

	// Responds to keyboard and mouse state
	void update();
};


#endif
