// Andrew Cash
// Programming Paradigms
// Fall 2018
// Assignment 3

import java.util.ArrayList;
import java.util.List;

class Model
{
    int scrollPos;
    ArrayList<Brick> bricks;
    Mario mario;
    boolean isCollision = false;

    Model()
    {
        bricks = new ArrayList<Brick>();
        mario = new Mario(this);
    }

    public void update()
    {
        mario.update();
        scrollPos = mario.x - 200;
    }

    void addBrick(int x1, int y1, int x2, int y2)
    {
        Brick b = new Brick(x1, y1, x2, y2);
        bricks.add(b);
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
        Json jsonBricks = Json.newList();

        // Add bricks object to model object
        jsonModel.add("bricks", jsonBricks);

        // Run through model and add to jsonBricks list
        for(int i = 0; i < bricks.size(); i++)
            jsonBricks.add(bricks.get(i).marshal());

        return jsonModel;
    }

    void unmarshal(Json obj)
    {
        bricks = new ArrayList<Brick>();
        Json jsonList = obj.get("bricks");
        for (int i=0; i < jsonList.size(); i++)
            bricks.add(new Brick(jsonList.get(i)));
    }

}
