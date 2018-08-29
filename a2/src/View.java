// Andrew Cash
// Programming Paradigms
// Fall 2018
// Assignment 2

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color;

class View extends JPanel
{
	JButton b1;
	BufferedImage turtle_image;	// Add turtle
	Model model;


	View(Controller c, Model m)
	{
		model = m;
	}

    void removeButton()
    {
        this.remove(b1);
        this.repaint();
    }

	public void paintComponent(Graphics g)
	{
		// Set cyan background
		g.setColor(new Color(255, 255, 255));

		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		g.setColor(new Color(0, 0, 0));
		for (int i=0; i < model.bricks.size(); i++)
		{
			Brick  b = model.bricks.get(i);
			g.drawRect(b.x - model.scrollPos, b.y, b.w, b.h);
		}
	}


}
