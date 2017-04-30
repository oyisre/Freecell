import java.awt.*;
//import hsa.Console;

public abstract class SuitClass extends MShapes
{
    public void setWidth (int widthNew)
    {
	super.setWidth (widthNew);
	super.setHeight ((int)(widthNew + Math.floor (widthNew * (1 / 5))));
    }


    public void setHeight (int heightNew)
    {
	super.setWidth ((int)(Math.floor (heightNew * (4 / 5))));
	super.setHeight (heightNew);
    }
}
