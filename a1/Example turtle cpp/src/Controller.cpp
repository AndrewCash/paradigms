/* The contents of this file are dedicated to the public domain (http://creativecommons.org/publicdomain/zero/1.0/). */

#include "Controller.h"
#include "View.h"
#include <iostream>

using std::cout;

Controller::Controller(Model& model, bool* pKeepRunning)
: m_model(model), m_pKeepRunning(pKeepRunning)
{
}

Controller::~Controller()
{
}

void Controller::update()
{
	SDL_Event event;
	while(SDL_PollEvent(&event))
	{
		if(event.type == SDL_QUIT)
			*m_pKeepRunning = false;
	}
	const Uint8* keys = SDL_GetKeyboardState(NULL);
	if(keys[SDL_SCANCODE_ESCAPE])
		*m_pKeepRunning = false;
	int mouseX, mouseY;
	Uint32 mouse_buttons = SDL_GetMouseState(&mouseX, &mouseY);
	if(keys[SDL_SCANCODE_SPACE])
		cout << "Hey, don't press the space bar!\n";
	if(keys[SDL_SCANCODE_RIGHT])
		m_model.setDestination(m_model.turtle_x + 1, m_model.turtle_y);
	if(keys[SDL_SCANCODE_LEFT])
		m_model.setDestination(m_model.turtle_x - 1, m_model.turtle_y);
	if(keys[SDL_SCANCODE_DOWN])
		m_model.setDestination(m_model.turtle_x, m_model.turtle_y + 1);
	if(keys[SDL_SCANCODE_UP])
		m_model.setDestination(m_model.turtle_x, m_model.turtle_y - 1);
	if(mouse_buttons & SDL_BUTTON(SDL_BUTTON_LEFT))
		m_model.setDestination(mouseX, mouseY);
}


