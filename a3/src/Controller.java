// Andrew Cash
// Programming Paradigms
// Fall 2018
// Assignment 3

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

	int mouseDownX;
	int mouseDownY;

	Controller(Model m)
	{
		model = m;
	}

	///////////////////////
	// Action Events
	//
	public void actionPerformed(ActionEvent e)
	{
		//view.removeButton();
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
		//model.setDestination(e.getX(), e.getY());
		mouseDownX = e.getX();
		mouseDownY = e.getY();
	}

	public void mouseReleased(MouseEvent e)
	{
		int x1 = mouseDownX;
		int x2 = e.getX();
		int y1 = mouseDownY;
		int y2 = e.getY();

		int top = Math.min(y1,y2);
		int bottom = Math.max(y1, y2);
		int left = Math.min(x1, x2);
		int right = Math.max(x1, x2);

		model.addBrick(left + model.scrollPos, top, right - left, bottom - top);
	}

	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) 
	{    
		if(e.getY() < 100)
		{
			System.out.println("break here");
		}
	}

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
		}

		char c = e.getKeyChar();
		if (c == 's')
		{
			System.out.println("Saving...");
			model.marshal().save("model.json");
		}
		else if (c == 'l')
		{
			System.out.println("Loading...");
			Json j = Json.load("model.json");
			model.unmarshal(j);
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
		}
	}

	public void keyTyped(KeyEvent e) {    }

	///////////////////////
	// Update model destination
	//
	void update()
	{
		if(keyRight) {model.scrollPos = model.scrollPos + 5;}
		if(keyLeft) {model.scrollPos = model.scrollPos - 5;}
		// if(keyDown) model.dest_y++;
		// if(keyUp) model.dest_y--;
	}

}
