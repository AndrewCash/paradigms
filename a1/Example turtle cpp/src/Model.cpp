/* The contents of this file are dedicated to the public domain (http://creativecommons.org/publicdomain/zero/1.0/). */

#include "Model.h"
#include "error.h"
#include "string.h"
#include <stdlib.h>
#include <iostream>

using std::cout;


Model::Model()
: turtle_x(0), turtle_y(0), dest_x(10), dest_y(10)
{
}

Model::~Model()
{
}

void Model::update()
{
	if(this->turtle_x < this->dest_x)
		this->turtle_x++;
	if(this->turtle_x > this->dest_x)
		this->turtle_x--;
	if(this->turtle_y < this->dest_y)
		this->turtle_y++;
	if(this->turtle_y > this->dest_y)
		this->turtle_y--;
}

void Model::setDestination(int x, int y)
{
	this->dest_x = x;
	this->dest_y = y;
}
