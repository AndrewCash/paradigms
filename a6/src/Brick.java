// Andrew Cash
// Programming Paradigms
// Fall 2018
// Assignment 6

import java.awt.Graphics;
import java.awt.Color;

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
