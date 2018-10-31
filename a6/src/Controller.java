// Andrew Cash
// Programming Paradigms
// Fall 2018
// Assignment 6

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.io.File;

class Controller implements ActionListener, MouseListener, KeyListener
{
	Model model;
	View view;

	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;
	boolean keySpace;
	boolean mapEditor = false;

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
		if (mapEditor)
		{
			int x1 = mouseDownX;
			int x2 = e.getX();
			int y1 = mouseDownY;
			int y2 = e.getY();

			int top = Math.min(y1,y2);
			int bottom = Math.max(y1, y2);
			int left = Math.min(x1, x2);
			int right = Math.max(x1, x2);

			// If mouse is dragged add a brick
			if (x1 != x2)
				model.addBrick(left + model.scrollPos, top, right - left, bottom - top);
			// Else add a Coin Block
			else
				model.addCoinBlock(left + model.scrollPos, top);
		}

	}

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
			case KeyEvent.VK_SPACE: keySpace = true; break;
		}

		if (mapEditor)
		{
			char c = e.getKeyChar();
			if (c == 's')
			{
				System.out.println("Saving...");
				Json j = model.marshal();
				j.save("Map.json");
				//model.marshal().save("Map.json");
			}
			else if (c == 'l')
			{
				System.out.println("Loading...");
				Json j = Json.load("Map.json");
				model.unmarshal(j);
			}

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
			case KeyEvent.VK_SPACE: keySpace = false; break;
		}
	}

	public void keyTyped(KeyEvent e) {    }


	///////////////////////
	// Update model destination
	//
	void update()
	{
		// Save Mario's prevoius position
		if (model.mario != null)
			model.rememberPreviousPosition();

		if(keyRight)
			model.goRight();

		if(keyLeft)
			model.goLeft();

		if (keySpace || keyUp)
			model.mario.jump();
		//
		// if (keySpace == false)
		// 	model.mario.spaceReleased = false;


		//////////////////
		// automatic AI
		//////////////////

		// Evaluate each possible action
		double score_run = model.evaluateAction(Model.Action.run, 0);
		double score_jump = model.evaluateAction(Model.Action.jump, 0);
		double score_jump_and_run = model.evaluateAction(Model.Action.jump_and_run, 0);

		// Do the best one
		if(score_jump_and_run > score_jump && score_jump_and_run > score_run)
			model.do_action(Model.Action.jump_and_run);
		else if(score_jump > score_run)
			model.do_action(Model.Action.jump);
		else
			model.do_action(Model.Action.run);

	}

}
