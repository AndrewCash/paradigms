// Andrew Cash
// Programming Paradigms
// Fall 2018
// Assignment 4

import java.awt.Graphics;

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

    void draw(Graphics g)
    {
        g.setColor(new Color(47, 76, 122));
        Sprite s = model.sprites.get(i);
        g.fillRect(s.x - model.scrollPos, s.y, s.w, s.h);
    }

    boolean am_I_a_Brick()
    {
        return true;
    }


}
