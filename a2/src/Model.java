// Andrew Cash
// Programming Paradigms
// Fall 2018
// Assignment 2

import java.util.ArrayList;
import java.util.List;


class Model
{

    ArrayList<Brick> bricks;

    Model()
    {
        bricks = new ArrayList<Brick>();

        Brick b = new Brick(400, 300, 200, 100);
        bricks.add(b);

    }

    public void update()
    {
    }

    public void setDestination(int x, int y)
    {
    }

}
