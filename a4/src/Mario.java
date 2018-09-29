// Andrew Cash
// Programming Paradigms
// Fall 2018
// Assignment 4

class Mario extends Sprite
{
	Model model;
	int jumpCounter;
	int spaceCounter;
	boolean facingRight = true;

	Mario(Model m)
	{
		model = m;

        x = 100;
        w = 60;
        h = 95;
	}

	Mario (Json obj)
	{
        x = (int)obj.getLong("x");
        y = (int)obj.getLong("y");
        w = (int)obj.getLong("w");
        h = (int)obj.getLong("h");
		vert_vel = (double)obj.getLong("vert_vel");
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
        // Update scrollPos
        model.scrollPos = x - 200;

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

		// Check for collisions with bricks
		Iterator<Sprite> it = model.sprites.iterator();
		while (it.hasNext())
		{
			Sprite s = it.next();

            if (s.am_I_a_Brick())
            {
                if (isColliding(this, s))
    			{
    				// System.out.println("Colliding!!");
    				// System.out.println("was at: (" + Integer.toString(x) + "," + Integer.toString(y) + "," + Integer.toString(x + w)+ "," + Integer.toString(y + h) + ")");
    				// System.out.println("is at: (" + Integer.toString(prev_x) + "," + Integer.toString(prev_y) + "," + Integer.toString(prev_x + w)+ "," + Integer.toString(prev_y + h) + ")");
    				// System.out.println("Brick is at: (" + Integer.toString(b.x) + "," + Integer.toString(b.y) + "," + Integer.toString(b.w)+ "," + Integer.toString(b.h) + ")");
    				// System.out.println("velocity is: " + Double.toString(vert_vel));

    				getOut(this, s);

    				// System.out.println("");
    			}
            }
		}
	}

    boolean am_I_a_Brick()
    {
        return false;
    }
}
