/* The contents of this file are dedicated to the public domain (http://creativecommons.org/publicdomain/zero/1.0/). */

#ifndef VIEW_H
#define VIEW_H

#include "SDL_image.h"


class Model;


class View
{
protected:
	SDL_Window* m_pWindow;
	SDL_Renderer* m_pRenderer;
	SDL_Surface* m_pPrimarySurface;

	SDL_Texture* m_pTexTurtle;

	Model& model;

public:
	// Constructor
	View(Model& m, int w, int h);

	// Destructor
	virtual ~View();

	// Draws the screen
	void update();

	// Loads a PNG file
	SDL_Texture* loadTexture(const char* szFilename);

	// Draws a texture on the screen
	void blitTexture(SDL_Texture* pTexture, int x, int y);
};



#endif
