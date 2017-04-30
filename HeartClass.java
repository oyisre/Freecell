//import hsa.Console;
import java.awt.*;

class HeartClass extends SuitClass
{
    protected boolean filled = true;

    public HeartClass ()
    {
    }
    
    public HeartClass (int xNew, int yNew, int widthNew, Color colourNew)
    {
	x = xNew;
	y = yNew;
	setWidth(widthNew);
	setColour(colourNew);
    }
    
    public void setIsFilled (boolean newfilled)
    {
	filled = newfilled;
    }


    public boolean getIsFilled ()
    {
	return filled;
    }


    /*public void draw (Console c)
    {
	c.setColor (colour);

	c.fillOval (x - width / 2, y - height / 2, width / 2, height / 2);
	c.fillOval (x, y - height / 2, width / 2, height / 2);

	int iPointsX[] = new int [3];
	int iPointsY[] = new int [3];

	iPointsX [0] = x - width / 2;
	iPointsY [0] = y - height / 4;
	iPointsX [1] = x + width / 2;
	iPointsY [1] = y - height / 4;
	iPointsX [2] = x;
	iPointsY [2] = y + width / 2;

	if (filled = true)
	{
	    c.fillPolygon (iPointsX, iPointsY, 3);
	}
	else
	{
	    c.drawPolygon (iPointsX, iPointsY, 3);
	}
    }*/
    
	public void draw (Graphics c)
    {
	c.setColor (colour);

	c.fillOval (x - width / 2, y - height / 2, width / 2, height / 2);
	c.fillOval (x, y - height / 2, width / 2, height / 2);

	int iPointsX[] = new int [3];
	int iPointsY[] = new int [3];

	iPointsX [0] = x - width / 2;
	iPointsY [0] = y - height / 4;
	iPointsX [1] = x + width / 2;
	iPointsY [1] = y - height / 4;
	iPointsX [2] = x;
	iPointsY [2] = y + width / 2;

	if (filled = true)
	{
	    c.fillPolygon (iPointsX, iPointsY, 3);
	}
	else
	{
	    c.drawPolygon (iPointsX, iPointsY, 3);
	}
    }
}
