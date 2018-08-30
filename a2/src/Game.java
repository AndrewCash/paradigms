// Andrew Cash
// Programming Paradigms
// Fall 2018
// Assignment 2

import javax.swing.JFrame;
import java.awt.Toolkit;

public class Game extends JFrame
{
	Model model;
	Controller controller;
	View view;

	public Game()
	{
		model = new Model();
		controller = new Controller(model);
		view = new View(controller, model);
		view.addMouseListener(controller);
		this.addKeyListener(controller);

		this.setTitle("Map Editor");
		this.setSize(500, 500);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	// Test brick marshall function
	// UNIT TEST UNIT TEST UNIT TEST
	static void testBrickMarshaller()
	{
		Brick b = new Brick(400, 300, 200, 100);
		Json j = b.marshal();
		j.save("testbrick.json");
	}

	// Test model marshall function
	static void testModelMarshaller()
	{
		Model m = new Model();
		m.bricks.add(new Brick(400, 300, 200, 100));

		Json j = m.marshal();
		j.save("testmodel.json");
	}

	public static void main(String[] args)
	{
		Game g = new Game();
		g.run();
	}

	public void run()
	{
		while(true)
		{
			controller.update();
			model.update();
			view.repaint(); // Indirectly calls View.paintComponent
			Toolkit.getDefaultToolkit().sync(); // Updates screen

			// Go to sleep for 25 miliseconds
			try
			{
				Thread.sleep(25);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

}
