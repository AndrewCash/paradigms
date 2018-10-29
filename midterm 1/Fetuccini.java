class Chicken
{
}

class AlfredoSauce
{
}

class BreadStick
{

}

class Fetuccini extends Object // 1
{
    static Chicken c = new Chicken();
    AlfredoSauce a; // 2

    void slurp(BreadStick b)
    {
        if(this.a == null) // 3
            this.b = null; // 4
        else
            c = null; // 5
    }

    static AlfredoSauce swallow()// 6
    {
        if(c == null) // 7
            return this.a; // 8
        else
            return null;
    }
}
