// Andrew Cash
// Programming Paradigms
// Fall 2018
// Assignment 3

import java.util.ArrayList;
import java.util.Iterator;

public class Mario
{
	int prev_x;
	int prev_y;
	int x = 100;
	int y;
	int w = 60;
	int h = 95;
	double vert_vel;
	Model model;
	int jumpCounter;
	int spaceCounter;

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


		if ((x + w) <= _x) {
			//System.out.println("Coming from right");
			return false;
		}else if (x >= (_x + _w)){
			//System.out.println("Coming from left");
			return false;
		}else if ((y + h) <= _y){
			//System.out.println("Coming from top");
			// Assume down is positive
			return false;
		}else if (y >= (_y + _h)){
			//System.out.println("Coming from bottom");
			// Assume down is positive
			return false;
		}else
		{
			//System.out.println("Colliding with object");
			return true;
		}
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


		// M left side hits B right side
		if (x <= (_x + _w) && prev_x > (_x + _w))
		{
			System.out.println("Hitting Right");
			x += 10;
			return;
		}

		// M right side hits B left side
		else if ((x + w) >= _x && (prev_x + w) < _x)
		{
			System.out.println("Hitting Left");
			x += -10;
			return;
		}

		// M top hits B bottom
		else if (y <= (_y + _h) && prev_y >= (_y + _h))
		{
			System.out.println("Hitting Bottom");
			y = _y + _h + 1;
			vert_vel = 0;
			return;
		}

		// M bottom hits B top
		else if ((y + h) >= _y && (prev_y + h) >= _y)
		{
			System.out.println("Hitting Top");
			y = _y - h + 1; // y + h = _y
			vert_vel = 0;
			jumpCounter = 0;
		}


	}

	void jump()
	{
		jumpCounter++;

		if (vert_vel > 10)
			return;
		else if (jumpCounter < 10)
				vert_vel = -10;
	}

	void update()
	{
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
		Iterator<Brick> it = model.bricks.iterator();
		while (it.hasNext())
		{
			Brick b = it.next();
			if (isColliding(b.x, b.y, b.w, b.h))
			{
				System.out.println("Colliding!!");
				System.out.println("was at: (" + Integer.toString(x) + "," + Integer.toString(y) + "," + Integer.toString(x + w)+ "," + Integer.toString(y + h) + ")");
				System.out.println("is at: (" + Integer.toString(prev_x) + "," + Integer.toString(prev_y) + "," + Integer.toString(prev_x + w)+ "," + Integer.toString(prev_y + h) + ")");
				System.out.println("Brick is at: (" + Integer.toString(b.x) + "," + Integer.toString(b.y) + "," + Integer.toString(b.w)+ "," + Integer.toString(b.h) + ")");
				System.out.println("velocity is: " + Double.toString(vert_vel));

				getOut(b.x, b.y, b.w, b.h);

				System.out.println("");
			}
		}

	}
}
