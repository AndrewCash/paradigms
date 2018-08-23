/* The contents of this file are dedicated to the public domain (http://creativecommons.org/publicdomain/zero/1.0/). */

#include "View.h"
#include "Model.h"
#include "error.h"
#include <SDL2/SDL_image.h>


View::View(Model& m, int w, int h)
: model(m)
{
	// Init SDL stuff
	if(SDL_Init(SDL_INIT_VIDEO) < 0)
		throw Ex("Unable to Init SDL: ", SDL_GetError());
	if(!SDL_SetHint(SDL_HINT_RENDER_SCALE_QUALITY, "1"))
		throw Ex("Unable to Init hinting: ", SDL_GetError());
	m_pWindow = SDL_CreateWindow("Turtle attack!", SDL_WINDOWPOS_UNDEFINED, SDL_WINDOWPOS_UNDEFINED, w, h, SDL_WINDOW_SHOWN);
	if(m_pWindow == NULL)
		throw Ex("Unable to create SDL Window: ", SDL_GetError());
	m_pPrimarySurface = SDL_GetWindowSurface(m_pWindow);
	m_pRenderer = SDL_CreateRenderer(m_pWindow, -1, SDL_RENDERER_ACCELERATED);
	if(m_pRenderer == NULL)
		throw Ex("Unable to create renderer");
	SDL_SetRenderDrawColor(m_pRenderer, 0x00, 0xff, 0xff, 0xff);

	// Initialize image loading for PNGs
	if(!(IMG_Init(IMG_INIT_PNG) & IMG_INIT_PNG)) {
		throw Ex("Unable to init SDL_image: ", IMG_GetError());
	}

	// Load the textures
	m_pTexTurtle = loadTexture("turtle.png");
}

// virtual
View::~View()
{
	SDL_DestroyTexture(m_pTexTurtle);
	if(m_pRenderer)
		SDL_DestroyRenderer(m_pRenderer);
//	if(m_pWindow)
//		SDL_DestroyWindow(m_pWindow);
	IMG_Quit();
//	SDL_Quit();
}


SDL_Texture* View::loadTexture(const char* szFilename)
{
	SDL_Surface* pTempSurface = IMG_Load(szFilename);
	if(pTempSurface == NULL)
		throw Ex("Unable to load image : ", szFilename, " : ", IMG_GetError());

	// Convert SDL surface to a texture
	SDL_Texture* pTexture = SDL_CreateTextureFromSurface(m_pRenderer, pTempSurface);
	if(pTexture == NULL)
	{
		SDL_FreeSurface(pTempSurface);
		throw Ex("Unable to create SDL Texture : ", szFilename, " : ", IMG_GetError());
	}

	SDL_FreeSurface(pTempSurface);
	
	return pTexture;
}

void View::update()
{
	SDL_RenderClear(m_pRenderer);

	// Draw the turtle
	blitTexture(m_pTexTurtle, this->model.turtle_x, this->model.turtle_y);

	SDL_RenderPresent(m_pRenderer);
}

void View::blitTexture(SDL_Texture* pTexture, int x, int y)
{
	SDL_Rect source;
	source.x = 0;
	source.y = 0;
	SDL_QueryTexture(pTexture, NULL, NULL, &source.w, &source.h);
	SDL_Rect dest;
	dest.x = x;
	dest.y = y;
	dest.w = source.w;
	dest.h = source.h;
	SDL_RenderCopy(m_pRenderer, pTexture, &source, &dest);
}

