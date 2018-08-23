// Andrew Cash
// Programming Paradigms
// Fall 2018
// Assignment 2

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements ActionListener, MouseListener, KeyListener
{
	Model model;
	View view;

	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;

	Controller(Model m)
	{
		model = m;
	}

	///////////////////////
	// Action Events
	//
	public void actionPerformed(ActionEvent e)
	{
		view.removeButton();
	}

    void setView(View v)
    {
        view = v;
    }

	///////////////////////
	// Mouse Events
	//
	public void mousePressed(MouseEvent e)
	{
		model.setDestination(e.getX(), e.getY());
	}

	public void mouseReleased(MouseEvent e) {    }
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) {    }

	///////////////////////
	// Keyboard Events
	//
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = true; break;
			case KeyEvent.VK_LEFT: keyLeft = true; break;
			case KeyEvent.VK_UP: keyUp = true; break;
			case KeyEvent.VK_DOWN: keyDown = true; break;

			// WASD directional support
			case KeyEvent.VK_D: keyRight = true; break;
			case KeyEvent.VK_A: keyLeft = true; break;
			case KeyEvent.VK_W: keyUp = true; break;
			case KeyEvent.VK_S: keyDown = true; break;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = false; break;
			case KeyEvent.VK_LEFT: keyLeft = false; break;
			case KeyEvent.VK_UP: keyUp = false; break;
			case KeyEvent.VK_DOWN: keyDown = false; break;

			// WASD directional support
			case KeyEvent.VK_D: keyRight = false; break;
			case KeyEvent.VK_A: keyLeft = false; break;
			case KeyEvent.VK_W: keyUp = false; break;
			case KeyEvent.VK_S: keyDown = false; break;
		}
	}

	public void keyTyped(KeyEvent e) {    }

	///////////////////////
	// Update model destination
	//
	void update()
	{
		if(keyRight) model.dest_x++;
		if(keyLeft) model.dest_x--;
		if(keyDown) model.dest_y++;
		if(keyUp) model.dest_y--;
	}

}
