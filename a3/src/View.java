// Andrew Cash
// Programming Paradigms
// Fall 2018
// Assignment 3

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color;

class View extends JPanel
{
	Controller controller;
	Model model;
	Image[] mario_images = null;
	Boolean facingRight = true;
	Image background = null;
	int marioImagesIndex = 0;
	int marioArraySize = 5;

	View(Controller c, Model m)
	{
		mario_images = new Image[marioArraySize];
		controller = c;
		model = m;

		// Load Mario images
		if (mario_images[0] == null)
		{
			try
			{
				mario_images[0] = ImageIO.read(new File("images/mario1.png"));
				mario_images[1] = ImageIO.read(new File("images/mario2.png"));
				mario_images[2] = ImageIO.read(new File("images/mario3.png"));
				mario_images[3] = ImageIO.read(new File("images/mario4.png"));
				mario_images[4] = ImageIO.read(new File("images/mario5.png"));
				background = ImageIO.read(new File("images/background.jpg"));

			}	catch(Exception e) {
					e.printStackTrace(System.err);
					System.exit(1);
			}
		}

	}

	// Called in Game.run() through view.repaint
	// Should act as update() function
	// Runs every 25 ms
	public void paintComponent(Graphics g)
	{
		// Draw background
		g.setColor(new Color(44, 171, 244));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.drawImage(this.background, (-model.scrollPos - 200) / 2, 0,
					getWidth() + 800, getHeight(), this);

		// Draw bricks
		g.setColor(new Color(47, 76, 122));
		for (int i=0; i < model.bricks.size(); i++)
		{
			Brick  b = model.bricks.get(i);
			g.fillRect(b.x - model.scrollPos, b.y, b.w, b.h);
		}

		// Draw ground
		// - put this in the model if you want to have pits
		g.setColor(new Color(150, 149, 108));
		g.fillRect(0, 450, 2000, 30);

		// Draw Mario
		// - determine if Mario moved left or right
		// - increment or decrement array of Mario images
		// - draw Mario image
		if (controller.keyRight)
		{
			marioImagesIndex++;
			marioImagesIndex = marioImagesIndex % marioArraySize;
			facingRight = true;
		}
		else if (controller.keyLeft)
		{
			marioImagesIndex--;
			marioImagesIndex = marioImagesIndex % marioArraySize;
			facingRight = false;
		}

		if (facingRight)
			g.drawImage(this.mario_images[Math.abs(marioImagesIndex)],
						model.mario.x - model.scrollPos, model.mario.y, null);
		else
			g.drawImage(this.mario_images[Math.abs(marioImagesIndex)],
						model.mario.x + model.mario.w - model.scrollPos, model.mario.y,
						-model.mario.w, model.mario.h, null);

	}


}
