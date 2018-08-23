// Andrew Cash
// Programming Paradigms
// Fall 2018
// Assignment 1

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


	View(Controller c)
	{
        c.setView(this);

		// Load turtle image
		try
		{
			this.turtle_image =
				ImageIO.read(new File("turtle.png"));
		}	catch(Exception e) {
				e.printStackTrace(System.err);
				System.exit(1);
		}

		// Reference model in component
		model = c.model;
	}

    void removeButton()
    {
        this.remove(b1);
        this.repaint();
    }

	public void paintComponent(Graphics g)
	{
		g.setColor(new Color(128, 255, 255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.drawImage(this.turtle_image, model.turtle_x, model.turtle_y, null);
	}


}
