// Andrew Cash
// Programming Paradigms
// Fall 2018
// Assignment 6

import java.awt.Graphics;

abstract class Sprite
{
    Model model;
    String type;
    int x;
    int y;
    int w;
    int h;
    double vert_vel;
    double horz_vel;
    int prev_x;
    int prev_y;
    int jumpCounter;
    int coinCounter;
    boolean onObject;
    boolean hittingBottom;
    boolean hittingTop;
    boolean touchedMario;
    boolean facingRight;
    Sounds soundEffects = new Sounds();

    abstract void update();
    abstract void draw(Graphics g, Model m, View v);
    abstract Sprite cloneMe();

    boolean am_I_a_Brick() { return false; }
    boolean am_I_a_CoinBlock() { return false; }
    boolean am_I_a_Coin() { return false; }
    boolean am_I_a_Mario() { return false; }

    // Sprite(Sprite copyMe, Model theNewModel)
    // {
    //     x = copyMe.x;
    //     y = copyMe.y;
    //     w = copyMe.w;
    //     h = copyMe.h;
    // }

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
            a.onObject = false;
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

        // if (b.am_I_a_Coin() == true)
        // {
        //     b.touchedMario = true;
        //     return;
        // }

		// M left side hits B right side
		if (a.x <= (b.x + b.w) && a.prev_x > (b.x + b.w))
		{
			//System.out.println("Hitting Right");
			a.x += 10;
			return;
		}

		// M right side hits B left side
		else if ((a.x + a.w) >= b.x && (a.prev_x + a.w) < b.x)
		{
			//System.out.println("Hitting Left");
			a.x += -10;
			return;
		}

		// M top hits B bottom
		else if (a.y <= (b.y + b.h) && a.prev_y >= (b.y + b.h))
		{
			//System.out.println("Hitting Bottom");
			a.y = b.y + b.h + 1;
			a.vert_vel = 0.0;
            //a.onObject = true;

            if (b.am_I_a_CoinBlock())
            {
                Coin c = new Coin(b.x, b.y);
                b.hittingBottom = true;
                if (b.coinCounter < 5)
                    soundEffects.playCoinSound();
            }

			return;
		}

		// M bottom hits B top
		else if ((a.y + a.h) >= b.y && (a.prev_y + a.h) >= b.y)
		{
			//System.out.println("Hitting Top");
			a.y = b.y - a.h + 1; // y + h = _y
			a.vert_vel = 3.0;
			a.jumpCounter = 0;
            b.hittingTop = true;

            b.onObject = true;
		}


	}

    Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("type", type);
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        ob.add("vert_vel", vert_vel);
        ob.add("horz_vel", horz_vel);
        ob.add("coinCounter", coinCounter);
        return ob;
    }

}
