// Andrew Cash
// Programming Paradigms
// Fall 2018
// Assignment 3

import java.util.ArrayList;

public class Mario
{
	int prev_x;
	int prev_y;
	int x = 100;
	int y;
	int w = 60;
	int h = 95;
	double vert_vel;
	Boolean isCollision;
	//String case;
	Model model;

	// Mario's hitbox
	int mario_right= x + w;
	int mario_left = x;
	int mario_bottom = y + h;
	int mario_top = y;

	Mario(Model m)
	{
		model = m;
	}

	void rememberPreviousPosition()
	{
		prev_x = x;
		prev_y = y;
	}

	boolean isColliding(int _x, int _y, int _w, int _h)
	{
		// Brick hitbox
		int brick_right = _x + _w;
		int brick_left = _x;
		int brick_bottom = _y + _h;
		int brick_top = _y;

		if (x + w <= brick_left){
			//case = top;
			return false;
		}else if (x >= _x + _w){
			//case =
			return false;
		}else if (x + h <= _h){
			//case =
			return false;
		}else if (y >= _y + _h){
			//case =
			return false;
		}else
			return true;
	}

	void getOut(int _x, int _y, int _w, int _h)
	{
		// Brick hitbox
		int brick_right = _x + _w;
		int brick_left = _x;
		int brick_bottom = _y + _h;
		int brick_top = _y;

		// Came from the left
		if (mario_right >= brick_left && prev_x + w < _x)
		{
			x = prev_x;
		}

		// Came from the right
		else if (mario_left >= brick_right && prev_x < _x + _w)
		{
			x = prev_x;
		}

		// Came from above
		else if (mario_bottom <= brick_top && prev_x + h > _h)
		{
			y = prev_y;
		}

		// Came from below
		else if (mario_top >= brick_bottom && prev_y < _y + _h)
		{
			y = prev_y;
		}
	}

	void update() // (ArrayList<Brick> bricks)
	{
		rememberPreviousPosition();

		vert_vel += 1.2;
		y += vert_vel;

		if (y > 355)
		{
			vert_vel = 0;
			y = 355;
		}

		// Check for collisions
		for (int i=0; i < model.bricks.size(); i++)
		{
			Brick b = model.bricks.get(i);
			if (isColliding(b.x, b.y, b.w, b.h))
			{
				System.out.println("Colliding!!");
				System.out.println("was at: " + Integer.toString(prev_x));
				System.out.println("is at: " + Integer.toString(x));

				getOut(b.x, b.y, b.w, b.h);
			}
			else
				 System.out.println("");
		}

	}
}
