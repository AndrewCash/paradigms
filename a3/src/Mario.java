// Andrew Cash
// Programming Paradigms
// Fall 2018
// Assignment 3

import java.util.ArrayList;

public class Mario
{
	int x = 100;
	int y = 0;
	double vert_vel;
	Boolean isCollision;

	// Mario's hitbox
	int right_side = x + 60;
	int left_side = x;
	int bottom_side = y + 95;
	int top_side = y;

	void update(ArrayList<Brick> bricks)
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
