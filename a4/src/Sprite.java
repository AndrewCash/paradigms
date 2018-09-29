// Andrew Cash
// Programming Paradigms
// Fall 2018
// Assignment 4

import java.awt.Graphics;

abstract class Sprite
{
    int x;
    int y;
    int w;
    int h;
    double vert_vel;
    int prev_x;
    int prev_y;

    abstract void update();
    abstract void draw(Graphics g);

    boolean am_I_a_Brick() { return false; }

    //////////////////////////
    // Collison Detection

    void rememberPreviousPosition()
	{
		prev_x = x;
		prev_y = y;
	}

    boolean isColliding(Sprite a, Sprite b)
	{
        int x = a.x;
        int y = a.y;
        int w = a.w;
        int h = a.h;
        int _x = b.x;
        int _y = b.y;
        int _w = b.w;
        int _h = b.h;

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

	void getOut(Sprite a, Sprite b)
	{
        int x = a.x;
        int y = a.y;
        int w = a.w;
        int h = a.h;
        int _x = b.x;
        int _y = b.y;
        int _w = b.w;
        int _h = b.h;

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
			a.x += 10;
			return;
		}

		// M right side hits B left side
		else if ((x + w) >= _x && (prev_x + w) < _x)
		{
			System.out.println("Hitting Left");
			a.x += -10;
			return;
		}

		// M top hits B bottom
		else if (y <= (_y + _h) && prev_y >= (_y + _h))
		{
			System.out.println("Hitting Bottom");
			y = _y + _h + 1;
			a.vert_vel = 0;
			return;
		}

		// M bottom hits B top
		else if ((y + h) >= _y && (prev_y + h) >= _y)
		{
			System.out.println("Hitting Top");
			y = _y - h + 1; // y + h = _y
			a.vert_vel = 0;
			a.jumpCounter = 0;
		}


	}

    Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        ob.add("vert_vel", vert_vel);
        return ob;
    }
}
