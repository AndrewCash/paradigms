// Andrew Cash
// Programming Paradigms
// Fall 2018
// Assignment 4

import java.util.Iterator;
import java.awt.Graphics;
import java.lang.Math;

class Mario extends Sprite
{
	Model model;
	int spaceCounter;
	boolean facingRight;

	Mario(Model m)
	{
		type = "Mario";
		model = m;
		onObject = true;
		facingRight = true;

        x = 100;
		y = 0;
        w = 60;
        h = 95;
	}

	// Unmarshaling constructor
	Mario (Json obj, Model m)
	{
		type = "Mario";
		model = m;
		onObject = true;

        x = (int)obj.getLong("x");
        y = (int)obj.getLong("y");
		w = (int)obj.getLong("w");
		h = (int)obj.getLong("h");
		vert_vel = obj.getDouble("vert_vel");
		coinCounter = (int)obj.getLong("coinCounter");
    }

	void jump()
	{
		jumpCounter++;

		if (vert_vel > 10)
			return;
		else if (jumpCounter < 10)
				vert_vel = -10;

		onObject = false;
	}

	void update()
	{
        // Update scrollPos
        model.scrollPos = x - 200;

		// Update gravity
		if (onObject)
			vert_vel = 0;
		else if (vert_vel < 20)
			vert_vel += 1.2;
		y += vert_vel;

		// Check for collisions with bricks
		Iterator<Sprite> it = model.sprites.iterator();
		while (it.hasNext())
		{
			Sprite s = it.next();

			if (!s.am_I_a_Mario())
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

	void draw(Graphics g, Model model, View view)
	{
		// - determine if Mario moved left or right
		// - increment or decrement array of Mario images
		// - draw Mario image


		if (facingRight && view.controller.keyRight)
		{
			view.marioImagesIndex++;
			view.marioImagesIndex = view.marioImagesIndex % view.marioArraySize;
		}
		else if (!facingRight && view.controller.keyLeft)
		{
			view.marioImagesIndex--;
			view.marioImagesIndex = view.marioImagesIndex % view.marioArraySize;
		}


		if (facingRight)
			g.drawImage(view.mario_images[Math.abs(view.marioImagesIndex)],
						model.mario.x - model.scrollPos, model.mario.y, null);
		else
			g.drawImage(view.mario_images[Math.abs(view.marioImagesIndex)],
						model.mario.x + model.mario.w - model.scrollPos, model.mario.y,
						-model.mario.w, model.mario.h, null);
	}

    boolean am_I_a_Mario()
    {
        return true;
    }
}
