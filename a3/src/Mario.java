// Andrew Cash
// Programming Paradigms
// Fall 2018
// Assignment 3

import java.util.ArrayList;

public class Mario
{
	int x = 100;
	int y;
	int w = 60;
	int h = 95;
	double vert_vel;
	Boolean isCollision;

	boolean isCollision(int _x, int _y, int _w, int _h)
	{
		if (x + w <= _x)
			return false;
		else if (x >= _x + _w)
			return false;
		else if (x + h <= _h)
			return false;
		else if (y >= _y + _h)
			return false;
		else
			return true;
	}

	void update() // (ArrayList<Brick> bricks)
	{
		vert_vel += 1.2;
		y += vert_vel;

		if (y > 355)
		{
			vert_vel = 0;
			y = 355;
		}

		// for (int i=0; i<bricks.size(); i++)
		// {
		// 	Brick b = bricks.get(i);
		//
		// 	if(this.right_side < b.left_side)
		// 		return false;
		// 	if(this.left_side > b.right_side)
		// 		return false;
		// 	if(this.bottom_side < b.top_side) // assumes bigger is downward
		// 		return false;
		// 	if(this.top_side > b.bottom_side) // assumes bigger is downward
		// 		return false;
		// 	return true;
		// }
	}
}
