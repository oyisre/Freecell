//import hsa.Console;
import java.awt.*;

class SpadeClass extends SuitClass
{
    protected boolean filled = true;

    public SpadeClass ()
    {
    }


    public SpadeClass (int xNew, int yNew, int widthNew, Color colourNew)
    {
	x = xNew;
	y = yNew;
	setWidth (widthNew);
	setColour (colourNew);
    }


    public void setIsFilled (boolean newfilled)
    {
	filled = newfilled;
    }


    public boolean getIsFilled ()
    {
	return filled;
    }


    /*
    public void draw (Console c)
    {

	c.fillOval (x - width / 2, y - height / 6, width / 2, height * 3 / 7);
	c.fillOval (x, y - height / 6, width / 2, height * 3 / 7);

	int iPointsX[] = new int [3];
	int iPointsY[] = new int [3];

	iPointsX [0] = x;
	iPointsY [0] = y - height / 2;
	iPointsX [1] = x - width / 2;
	iPointsY [1] = y;
	iPointsX [2] = x + width / 2;
	iPointsY [2] = y;

	c.setColor (colour);
	if (filled)
	{
	    c.fillPolygon (iPointsX, iPointsY, 3);
	}
	else
	{
	    c.drawPolygon (iPointsX, iPointsY, 3);
	}

	iPointsX [0] = x;
	iPointsY [0] = y;
	iPointsX [1] = x - width / 4;
	iPointsY [1] = y + height / 2;
	iPointsX [2] = x + width / 4;
	iPointsY [2] = y + height / 2;

	c.setColor (colour);
	if (filled)
	{
	    c.fillPolygon (iPointsX, iPointsY, 3);
	}
	else
	{
	    c.drawPolygon (iPointsX, iPointsY, 3);
	}
    }
    */

    public void draw (Graphics c)
    {
	c.setColor(colour);
	c.fillOval (x - width / 2, y - height / 6, width / 2, height * 3 / 7);
	c.fillOval (x, y - height / 6, width / 2, height * 3 / 7);
	c.fillOval (x - (width/4), y - (height/4), width / 2, height / 2);

	int iPointsX[] = new int [3];
	int iPointsY[] = new int [3];

	iPointsX [0] = x;
	iPointsY [0] = y - height / 2;
	iPointsX [1] = x - width / 2;
	iPointsY [1] = y;
	iPointsX [2] = x + width / 2;
	iPointsY [2] = y;

	c.setColor (colour);
	if (filled)
	{
	    c.fillPolygon (iPointsX, iPointsY, 3);
	}
	else
	{
	    c.drawPolygon (iPointsX, iPointsY, 3);
	}

	iPointsX [0] = x;
	iPointsY [0] = y;
	iPointsX [1] = x - width / 4;
	iPointsY [1] = y + height / 2;
	iPointsX [2] = x + width / 4;
	iPointsY [2] = y + height / 2;

	c.setColor (colour);
	if (filled)
	{
	    c.fillPolygon (iPointsX, iPointsY, 3);
	}
	else
	{
	    c.drawPolygon (iPointsX, iPointsY, 3);
	}
    }
}
