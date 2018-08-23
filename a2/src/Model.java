// Andrew Cash
// Programming Paradigms
// Fall 2018
// Assignment 1

class Model
{
    int turtle_x;
    int turtle_y;
    int dest_x;
    int dest_y;

    Model()
    {
    }

    public void update()
    {
        // Move the turtle
        //  - move 4 units
        //  - if distance moved is less than 4 move the difference between destination and location

        if (this.turtle_x < this.dest_x) // Move right
            this.turtle_x += Math.min(4, dest_x - turtle_x);
        if (this.turtle_x > this.dest_x) // Move left
            this.turtle_x -= Math.min(4, turtle_x - dest_x);
        if (this.turtle_y < this.dest_y) // Move up
            this.turtle_y += Math.min(4, dest_y - turtle_y);
        else if (this.turtle_y > this.dest_y) // Move down
            this.turtle_y -= Math.min(4, turtle_y - dest_y);
    }

    public void setDestination(int x, int y)
    {
        this.dest_x = x;
        this.dest_y = y;
    }
}
