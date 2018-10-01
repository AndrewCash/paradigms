// Andrew Cash
// Programming Paradigms
// Fall 2018
// Assignment 4

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

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

        sprites.add(mario);

		// System.out.println("Loading...");
        // Json j = Json.load("Map.json");
        // unmarshal(j);

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
                // Decrement i so that the next spite is not skipped over
                // this could be handled by Iterator class
                sprites.remove(i);
                i--;
            }

            if (s.am_I_a_Coin() && s.touchedMario == true) // Mario grabs coin
            {
                // Decrement i so that the next spite is not skipped over
                // this could be handled by Iterator class
                s.coinCounter++;
                System.out.println(s.coinCounter);
                mario.coinCounter++;

                sprites.remove(i);
                i--;
            }
        }

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
        sprites.clear();
        Json jsonList = obj.get("sprites");
        for (int i=0; i < jsonList.size(); i++)
        {
            Json j = jsonList.get(i);
            String s = j.getString("type");
            if (s.equals("Brick"))
                sprites.add(new Brick(j));
            else if (s.equals("CoinBlock"))
                sprites.add(new CoinBlock(j, this));
            else if (s.equals("Coin"))
                sprites.add(new Coin(j));
            else if (s.equals("Mario"))
                sprites.add(new Mario(j, this));
        }


    }

}
