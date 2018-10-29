// Andrew Cash
// Programming Paradigms
// Fall 2018
// Assignment 4

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.io.File;


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

		this.setTitle("Game");
		this.setSize(960, 540);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	// Test brick marshal function
	// UNIT TEST UNIT TEST UNIT TEST
	static void testBrickMarshaller()
	{
		Brick b = new Brick(400, 300, 200, 100);
		Json j = b.marshal();
		j.save("testbrick.json");
	}

	// Test model marshal function
	static void testModelMarshaller()
	{
		Model m = new Model();
		m.sprites.add(new Brick(400, 300, 200, 100));

		Json j = m.marshal();
		j.save("testmodel.json");
	}

	public void run()
	{
		while(true)
		{
			controller.update();
			model.update();
			view.repaint(); // Indirectly calls View.paintComponent
			Toolkit.getDefaultToolkit().sync(); // Updates screen

			// Go to sleep for 25 milliseconds
			try
			{
				Thread.sleep(25);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	public static void main(String[] args)
	{
		Game g = new Game();
		g.run();
	}

}

class Model
{
    int scrollPos;
    ArrayList<Sprite> sprites;
    Mario mario;
    boolean isCollision = false;

    Model()
    {
        sprites = new ArrayList<Sprite>();
        mario = new Mario(this);
        sprites.add(new Mario(this));

        //mario = sprites.add(new Mario(this));

		System.out.println("Loading...");
        Json j = Json.load("Map.json");
        unmarshal(j);

    }

    void goRight()
    {
        mario.facingRight = true;
        mario.x += 10;
    }

    void goLeft()
    {
        mario.facingRight = false;
        mario.x -= 10;
    }

    public void update()
    {
        for (int i=0; i < sprites.size(); i++)
        {
            Sprite s = sprites.get(i);
            s.update();

            if (s.jumpCounter > 50 ||
               (s.am_I_a_Coin() && s.isColliding(s, mario)) ) // Coin goes off screen
            {
                sprites.remove(i);
                i--;

                // Decrement i so that the next spite is not skipped over
                // this could be handled by Iterator class
            }

            // for (int j=0; i < sprites.size(); j++)
            // {
            //     Sprite t = sprites.get(j);
            //     if ((s != t) && s.am_I_a_Coin() && s.isColliding(s, t))
            //     {
            //         sprites.remove(i);
            //         i--;
            //     }
            // }

            if (s.am_I_a_Coin() && s.touchedMario ||
                s.am_I_a_Coin() && s.hittingTop) // Mario grabs coin
            {
                // Decrement i so that the next spite is not skipped over
                // this could be handled by Iterator class

                s.coinCounter++;
                mario.coinCounter++;
                //mario.soundEffects.playCoinCollect();
                sprites.remove(i);
                i--;
            }
        }

        //if (mario != null)
            mario.update();
    }

    /////////////////////////////////////////////////////////////
    // Methods to add various sprites
    /////////////////////////////////

    void addBrick(int x1, int y1, int x2, int y2)
    {
        Brick b = new Brick(x1, y1, x2, y2);
        sprites.add(b);
    }

    void addCoinBlock(int x, int y)
    {
        CoinBlock cb = new CoinBlock(x, y, this);
        sprites.add(cb);
    }

    void addCoin(int x, int y)
    {
        Coin c = new Coin(x, y);
        sprites.add(c);
    }

    void rememberPreviousPosition()
	{
		mario.rememberPreviousPosition();
	}


    //////////////////////////
    // JSON saving and loading

    Json marshal()
    {
        Json jsonModel = Json.newObject();
        Json jsonSprites = Json.newList();

        // Add sprites object to model object
        jsonModel.add("sprites", jsonSprites);

        // Run through model and add to jsonSprites list
        for(int i = 0; i < sprites.size(); i++)
        {
            jsonSprites.add(sprites.get(i).marshal());
        }

        return jsonModel;
    }

    void unmarshal(Json obj)
    {
        //mario = null;
        sprites.clear();
        Json jsonList = obj.get("sprites");
        for (int i=0; i < jsonList.size(); i++)
        {
            Json j = jsonList.get(i);
            String s = j.getString("type");
            if (s.equals("Mario"))
                sprites.add(new Mario(j, this));
            else if (s.equals("Brick"))
                sprites.add(new Brick(j));
            else if (s.equals("CoinBlock"))
                sprites.add(new CoinBlock(j, this));
            else if (s.equals("Coin"))
                sprites.add(new Coin(j));
        }


    }

}

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


class Brick extends Sprite
{
    Brick(int _x, int _y, int _w, int _h)
    {
        type = "Brick";
        x = _x;
        y = _y;
        w = _w;
        h = _h;
    }

    // Unmarshaling constructor
    Brick (Json obj)
    {
        x = (int)obj.getLong("x");
        y = (int)obj.getLong("y");
        w = (int)obj.getLong("w");
        h = (int)obj.getLong("h");
    }

    void update()
    {
    }

    void draw(Graphics g, Model m, View v)
    {
        g.setColor(new Color(47, 122, 88));
        g.fillRect(this.x - m.scrollPos, this.y, this.w, this.h);
    }

    boolean am_I_a_Brick()
    {
        return true;
    }


}
