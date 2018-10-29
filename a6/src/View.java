// Andrew Cash
// Programming Paradigms
// Fall 2018
// Assignment 6

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
	Image background = null;
	Image fullBlock = null;
	Image emptyBlock = null;
	Image coin = null;
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
				fullBlock = ImageIO.read(new File("images/block1.png"));
				emptyBlock = ImageIO.read(new File("images/block2.png"));
				coin = ImageIO.read(new File("images/coin.png"));

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

		// Draw sprites
		for (int i=0; i < model.sprites.size(); i++)
		{
			Sprite s = model.sprites.get(i);
			s.draw(g, model, this);
		}

		// Draw Mario
		//model.mario.draw(g, model, this);

	}


}
