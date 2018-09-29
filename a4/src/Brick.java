// Andrew Cash
// Programming Paradigms
// Fall 2018
// Assignment 4

import java.awt.Graphics;
import java.awt.Color;

class Brick extends Sprite
{
    Brick(int _x, int _y, int _w, int _h)
    {
        x = _x;
        y = _y;
        w = _w;
        h = _h;
    }

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

    void draw(Graphics g, Model m)
    {
        g.setColor(new Color(47, 76, 122));
        g.fillRect(this.x - m.scrollPos, this.y, this.w, this.h);
    }

    boolean am_I_a_Brick()
    {
        return true;
    }


}
