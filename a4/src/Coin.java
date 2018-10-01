// Andrew Cash
// Programming Paradigms
// Fall 2018
// Assignment 4

import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;
import java.lang.Math;

class Coin extends Sprite
{
    static Random rand = new Random();

    Coin(int _x, int _y)
    {
        type = "Coin";
        x = _x;
        y = _y;
        w = 75;
        h = 75;

        // Generate random horizontal velocity
        double d = rand.nextDouble() * 16 - 8;
        double e = rand.nextDouble() * 16 - 8;
        horz_vel = d;
        vert_vel = -(3 + Math.abs(e));
    }

    // Unmarshaling constructor
    Coin(Json obj)
    {
        type = "Coin";
        x = (int)obj.getLong("x");
        y = (int)obj.getLong("y");
        w = (int)obj.getLong("w");
        h = (int)obj.getLong("h");
        horz_vel = (double)obj.getLong("horz_vel");
        vert_vel = (double)obj.getLong("vert_vel");

    }

    void update()
    {
        // Update gravity
		vert_vel += 1.2;
        y += vert_vel;

        x += horz_vel;
    }

    void draw(Graphics g, Model model, View view)
    {
        g.drawImage(view.coin, this.x - model.scrollPos, this.y, null);
    }

    boolean am_I_a_Coin()
    {
        return true;
    }
}

class CoinBlock extends Sprite
{
    Model model;
    int numCoinsReleased;

    CoinBlock(int _x, int _y, Model m)
    {
        type = "CoinBlock";
        x = _x;
        y = _y;
        w = 89;
        h = 83;
        model = m;
    }

    // Unmarshaling constructor
    CoinBlock(Json obj, Model m)
    {
        x = (int)obj.getLong("x");
        y = (int)obj.getLong("y");
        w = 89;
        h = 83;

        model = m;
    }

    void update()
    {
        if (hittingBottom && numCoinsReleased < 5)
        {
            numCoinsReleased++;
            //System.out.println("aaaaaaa");
            hittingBottom = false;

            model.addCoin(x, y - 75);
            //onObject = true;
        }

    }

    void draw(Graphics g, Model model, View view)
    {
        if (numCoinsReleased < 5)
            g.drawImage(view.fullBlock, this.x - model.scrollPos, this.y, null);
        else
            g.drawImage(view.emptyBlock, this.x - model.scrollPos, this.y, null);
    }

    boolean am_I_a_CoinBlock()
    {
        return true;
    }


}
