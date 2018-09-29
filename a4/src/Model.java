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

		System.out.println("Loading...");
        Json j = Json.load("Map.json");
        unmarshal(j);
    }

    void goRight()
    {
        mario.x += 10;
    }

    void goLeft()
    {
        mario.x -= 10;
    }

    public void update()
    {
        for (int i=0; i < sprites.size(); i++)
        {
                Sprite s = sprites.get(i);
                s.update();
        }
    }

    void addBrick(int x1, int y1, int x2, int y2)
    {
        Brick b = new Brick(x1, y1, x2, y2);
        sprites.add(b);
    }

    void rememberPreviousPosition()
	{
		mario.rememberPreviousPosition();
	}


    //////////////////////////
    // JSON saving and loading

    Json marshal()
    {
        Json jsonObject = Json.newObject();
        Json jsonBricks = Json.newList();

        // Add bricks object to model object
        jsonObject.add("sprites", jsonBricks);

        // Run through model and add to jsonBricks list
        for(int i = 0; i < sprites.size(); i++)
        {
            Sprite spr = sprites.get(i);
            Json j = spr.marshal();
            jsonBricks.add(j);
        }

        return jsonObject;
    }

    void unmarshal(Json obj)
    {
        sprites.clear();
        Json jsonList = obj.get("sprites");
        for (int i=0; i < jsonList.size(); i++)
        {
            Json j = jsonList.get(i);
            String s = j.getString("type");
            if (s.equals("Mario"))
                sprites.add(new Mario(j));
            else
                sprites.add(new Brick(j));
        }

    }

}
