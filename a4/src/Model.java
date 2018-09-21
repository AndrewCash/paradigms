// Andrew Cash
// Programming Paradigms
// Fall 2018
// Assignment 4

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

class Sprite
{
    int x;
    int y;
    int w;
    int h;
}

class Brick extends Sprite
{
    Brick(int _x, int _y, int _w, int _h)
    {
        x = _x;
        y = _y;
        w = _w;
        h = _h;
    }

    Brick(Json obj)
    {
        x = (int)obj.getLong("x");
        y = (int)obj.getLong("y");
        w = (int)obj.getLong("w");
        h = (int)obj.getLong("h");
    }

    Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        return ob;
    }
}

class Mario extends Sprite
{
	int prev_x;
	int prev_y;
	double vert_vel;
	Model model;
	int jumpCounter;
	int spaceCounter;

	Mario(Model m)
	{
		model = m;
        
        x = 100;
        w = 60;
        h = 95;
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


class Model
{
    int scrollPos;
    ArrayList<Brick> bricks;
    Mario mario;
    boolean isCollision = false;

    Model()
    {
        bricks = new ArrayList<Brick>();
        mario = new Mario(this);

		System.out.println("Loading...");
        Json j = Json.load("Map.json");
        unmarshal(j);
    }

    public void update()
    {
        mario.update();
        scrollPos = mario.x - 200;
    }

    void addBrick(int x1, int y1, int x2, int y2)
    {
        Brick b = new Brick(x1, y1, x2, y2);
        bricks.add(b);
    }

    void rememberPreviousPosition()
	{
		mario.rememberPreviousPosition();
	}

    //////////////////////////
    // JSON saving and loading

    Json marshal()
    {
        Json jsonModel = Json.newObject();
        Json jsonBricks = Json.newList();

        // Add bricks object to model object
        jsonModel.add("bricks", jsonBricks);

        // Run through model and add to jsonBricks list
        for(int i = 0; i < bricks.size(); i++)
            jsonBricks.add(bricks.get(i).marshal());

        return jsonModel;
    }

    void unmarshal(Json obj)
    {
        bricks = new ArrayList<Brick>();
        Json jsonList = obj.get("bricks");
        for (int i=0; i < jsonList.size(); i++)
            bricks.add(new Brick(jsonList.get(i)));
    }

}
