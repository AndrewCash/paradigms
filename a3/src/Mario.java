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
	Model model;
	int jumpCounter;

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

		if (x + w <= _x){ //
			//System.out.println("Coming from right");
			return false;
		}else if (x >= _x + _w){
			//System.out.println("Coming from left");
			return false;
		}else if (x + h <= _h){
			//System.out.println("Coming from top");
			return false;
		}else if (y >= _y + _h){
			//System.out.println("Coming from bottom");
			return false;
		}else
			return true;
	}

	void getOut(int _x, int _y, int _w, int _h)
	{
		// // Brick hitbox
		// int brick_right = _x + _w;
		// int brick_left = _x;
		// int brick_bottom = _y + _h;
		// int brick_top = _y;
		//
		// // Mario's hitbox
		// int mario_right= x + w;
		// int mario_left = x;
		// int mario_bottom = y + h;
		// int mario_top = y;


		// M right side hits B left side
		if (x + w >= _x && prev_x + w < _x)
		{
			x = _x - w;
		}

		// M left side hits B right side
		else if (x <= _x + _w && prev_x > _x + _w)
		{
			x = _x + w + 10;
		}

		// M bottom hits B top
		if (y + h >= _y && prev_y + h > _h)
		{
			y = _y - h - 1;
			vert_vel = 0;
			jumpCounter = 0;
		}

		// M top hits B bottom
		else if (y >= _y + _h && prev_y < _y + _h)
		{
			y = _y + _h;
		}
	}

	void jump()
	{
		if (model.mario.jumpCounter < 10)
			model.mario.vert_vel = -10;

		model.mario.jumpCounter++;
	}

	void update()
	{
		rememberPreviousPosition();

		// Update gravity
		vert_vel += 1.2;
		y += vert_vel;

		// Set ground level
		if (y > 355)
		{
			vert_vel = 0;
			jumpCounter = 0;
			y = 355;
		}

		// Check for collisions
		for (int i=0; i < model.bricks.size(); i++)
		{
			Brick b = model.bricks.get(i);
			if (isColliding(b.x, b.y, b.w, b.h))
			{
				System.out.println("Colliding!!");
				System.out.println("was at: (" + Integer.toString(prev_x) + "," + Integer.toString(prev_y) + ")");
				System.out.println("is at: (" + Integer.toString(x) + "," + Integer.toString(y) + ")");
				System.out.println("");

				getOut(b.x, b.y, b.w, b.h);
			}
			else
				 System.out.println("");
		}

	}
}
