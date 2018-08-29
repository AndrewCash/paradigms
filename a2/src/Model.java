// Andrew Cash
// Programming Paradigms
// Fall 2018
// Assignment 2

import java.util.ArrayList;
import java.util.List;


class Model
{
    int scrollPos;
    ArrayList<Brick> bricks;

    Model()
    {
        bricks = new ArrayList<Brick>();
    }

    public void update()
    {
    }

    void addBrick(int x1, int y1, int x2, int y2)
    {
        Brick b = new Brick(x1, y1, x2, y2);
        bricks.add(b);
    }

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


        // Submarine(Json ob)
        // {
        //     atomic = ob.getBool("atomic");
        //     crewSize = (int)ob.getLong("crewSize");
        //     depth = ob.getDouble("depth");
        //     peri = new Periscope(ob.get("peri"));
        //     ammo = new ArrayList<Torpedo>();
        //     Json tmpList = ob.get("ammo");
        //     for(int i = 0; i < tmpList.size(); i++)
        //         ammo.add(new Torpedo(tmpList.get(i)));
        // }

}
