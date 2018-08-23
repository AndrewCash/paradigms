/* The contents of this file are dedicated to the public domain (http://creativecommons.org/publicdomain/zero/1.0/). */

#ifndef MODEL_H
#define MODEL_H

class Model
{
public:
	int turtle_x;
	int turtle_y;
	int dest_x;
	int dest_y;

	// Constructor
	Model();

	// Destructor
	~Model();

	// Moves the turtle one pixel closer to the destination
	void update();

	// Sets the new destination
	void setDestination(int x, int y);
};

#endif
