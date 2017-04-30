//import hsa.Console;
import java.awt.*;

public abstract class MShapes extends ShapeClass
{
    /*public void slideTo (int newX, int newY, int steps, Console c)
    {
	int stepsNew = steps;

	if (stepsNew == 0)
	    stepsNew = 1;


	int xDist = (int) (Math.ceil ((newX - x) / stepsNew));
	int yDist = (int) (Math.ceil ((newY - y) / stepsNew));

	for (int i = 1 ; i < stepsNew ; i++)
	{
	    erase (c);
	    setCenter ((x + xDist), (y + yDist));
	    draw (c);
	    delay (25);
	}
    }*/
    
	public void slideTo (int newX, int newY, int steps, Graphics c)
    {
	int stepsNew = steps;

	if (stepsNew == 0)
	    stepsNew = 1;


	int xDist = (int) (Math.ceil ((newX - x) / stepsNew));
	int yDist = (int) (Math.ceil ((newY - y) / stepsNew));

	for (int i = 1 ; i < stepsNew ; i++)
	{
	    erase (c);
	    setCenter ((x + xDist), (y + yDist));
	    draw (c);
	    delay (25);
	}
    }
}
