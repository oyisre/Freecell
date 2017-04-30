//import hsa.Console;
import java.awt.*;

public abstract class ShapeClass
{

    protected int x, y, height, width = 100;
    protected Color colour = Color.red;

    //public abstract void draw (Console c);

    public abstract void draw (Graphics c);

    public void setColour (Color cNew)
    {
	colour = cNew;
    }


    public void setCenter (int xNew, int yNew)
    {
	x = xNew;
	y = yNew;
    }


    public void setHeight (int heightNew)
    {
	height = heightNew;
    }


    public void setWidth (int widthNew)
    {
	width = widthNew;
    }


    public Color getColour ()
    {
	return colour;
    }


    public int getHeight ()
    {
	return height;
    }


    public int getWidth ()
    {
	return width;
    }


    public int getX ()
    {
	return x;
    }


    public int getY ()
    {
	return y;
    }

    /*
    public void erase (Console c)
    {
	Color iOldColour = getColour ();
	colour = Color.white;
	draw (c);
	colour = iOldColour;
    }
    */


    public void erase (Graphics c)
    {
	Color iOldColour = getColour ();
	colour = Color.white;
	draw (c);
	colour = iOldColour;
    }


    public void delay (int iDelayTime)
    {
	long lFinalTime = System.currentTimeMillis () + iDelayTime;
	do
	{
	}
	while (lFinalTime >= System.currentTimeMillis ());
    }


    public boolean isPointInside (int getx, int gety)
    {
	if (getx > x - width / 2 && getx < x + width / 2)
	{
	    if (gety > y - height / 2 && gety < y + height / 2)
	    {
		return true;
	    }
	    else
	    {
		return false;
	    }
	}
	else
	{
	    return false;
	}

    }
}
