// The DiamondClass Class
// Second in a series of demonstration programs for introducing Java

//import hsa.Console;
import java.awt.*;

class DiamondClass extends SuitClass
{
    // global variables for this class
    // encapsulated data
    protected boolean filled = true;

    public DiamondClass ()
    {
    }


    public DiamondClass (int xNew, int yNew, int widthNew, Color colourNew)
    {
	x = xNew;
	y = yNew;
	setWidth (widthNew);
	setColour (colourNew);
    }


    // communicator methods
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
	// declare two arrays for X & Y coordinates of Diamond
	int iPointsX[] = new int [4];
	int iPointsY[] = new int [4];

	// calculate points on diamond & store in the arrays
	iPointsX [0] = x - (width / 2);
	iPointsY [0] = y;
	iPointsX [1] = x;
	iPointsY [1] = y - (height / 2);
	iPointsX [2] = x + (width / 2);
	iPointsY [2] = y;
	iPointsX [3] = x;
	iPointsY [3] = y + (height / 2);

	// draw the diamond using methods available from the Console object (c)
	c.setColor (colour);
	if (filled)
	{
	    c.fillPolygon (iPointsX, iPointsY, 4);
	}
	else
	{
	    c.drawPolygon (iPointsX, iPointsY, 4);
	}
    }*/

    public void draw (Graphics c)
    {
	// declare two arrays for X & Y coordinates of Diamond
	int iPointsX[] = new int [4];
	int iPointsY[] = new int [4];

	// calculate points on diamond & store in the arrays
	iPointsX [0] = x - (width / 2);
	iPointsY [0] = y;
	iPointsX [1] = x;
	iPointsY [1] = y - (height / 2);
	iPointsX [2] = x + (width / 2);
	iPointsY [2] = y;
	iPointsX [3] = x;
	iPointsY [3] = y + (height / 2);

	// draw the diamond using methods available from the Console object (c)
	c.setColor (colour);
	if (filled)
	{
	    c.fillPolygon (iPointsX, iPointsY, 4);
	}
	else
	{
	    c.drawPolygon (iPointsX, iPointsY, 4);
	}
    }
}
