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
        mario = new Mario();
    }

    public void update()
    {
        mario.update();
    }

    // returns true iff two rectangles overlap
	void collision()
	{
        for (int i=0; i < bricks.size(); i++)
		{
            if(mario.right_side < bricks.get(i).x)
    			isCollision = false;
    		if(mario.left_side > bricks.get(i).w)
    			isCollision = false;
    		if(mario.bottom_side < bricks.get(i).y) // assumes bigger is downward
    			isCollision = false;
    		if(mario.top_side > bricks.get(i).h) // assumes bigger is downward
    			isCollision = false;
    		else
            {
                isCollision = true;

            }
		}

        if (isCollision == false)
        {
            System.out.println("no collision...");
        }

        if (isCollision == true)
        {
            System.out.println("COLLISION!!");
        }

	}

    void addBrick(int x1, int y1, int x2, int y2)
    {
        Brick b = new Brick(x1, y1, x2, y2);
        bricks.add(b);
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
