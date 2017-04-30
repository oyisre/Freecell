//import hsa.Console;
import java.awt.*;

public class CardClass extends MShapes
{
    private int suit = 1;
    private int value = 1;
    private String size = "medium";
    private boolean isFlipped;
    private boolean isTrans = false;

    CardClass ()
    {
	setSize (size);
    }


    CardClass (int cx, int cy, int newSuit, int newValue, String newSize, boolean flipped)
    {
	setCenter (cx, cy);
	setSuit (newSuit);
	setValue (newValue);
	setSize (newSize);
	setFlipped (flipped);
    }


    public void setWidth (int newWidth)
    {
	width = newWidth;
	height = newWidth * 10 / 7;
    }


    public void setHeight (int newHeight)
    {
	height = newHeight;
	width = newHeight * 7 / 10;
    }


    public void setSuit (int newSuit)
    {
	suit = newSuit;
    }


    public void setValue (int newValue)
    {
	value = newValue;
    }


    public void setSize (String newSize)
    {
	size = newSize;
	if (size == "small")
	{
	    setHeight (60);
	}
	else if (size == "medium")
	{
	    setHeight (80);
	}
	else if (size == "large")
	{
	    setHeight (100);
	}
	else if (size == "extra large")
	{
	    setHeight (120);
	}
    }


    public void setFlipped (boolean flipped)
    {
	isFlipped = flipped;
    }


    public int getSuit ()
    {
	return suit;
    }


    public int getValue ()
    {
	return value;
    }


    public String getSize ()
    {
	return size;
    }


    public boolean getFlipped ()
    {
	return isFlipped;
    }


    /*
    public void draw (Console c)
    {
	c.setColour (Color.white);
	c.fillRect (x - (width / 2), y - (height / 2), width, height);
	c.setColour (Color.black);
	c.drawRect (x - (width / 2), y - (height / 2), width, height);

	Font f1 = new Font ("TimesNewRoman", Font.BOLD, 16);
	c.setFont (f1);

	Color cOldColor = getColour ();
	if (suit == 1)
	{
	    setColour (Color.black);
	    SpadeClass S1 = new SpadeClass (x, y, width / 3, colour);
	    S1.draw (c);
	    S1.setCenter (x - width / 2 + 20, y - height / 2 + 11);
	    S1.setHeight (14);
	    S1.draw (c);
	}
	else if (suit == 2)
	{
	    setColour (Color.red);
	    HeartClass H1 = new HeartClass (x, y, width / 3, colour);
	    H1.draw (c);
	    H1.setCenter (x - width / 2 + 20, y - height / 2 + 11);
	    H1.setHeight (14);
	    H1.draw (c);
	}
	else if (suit == 3)
	{
	    setColour (Color.black);
	    ClubClass C1 = new ClubClass (x, y, width / 3, colour);
	    C1.draw (c);
	    C1.setCenter (x - width / 2 + 20, y - height / 2 + 11);
	    C1.setHeight (14);
	    C1.draw (c);
	}
	else if (suit == 4)
	{
	    setColour (Color.red);
	    DiamondClass D1 = new DiamondClass (x, y, width / 3, colour);
	    D1.draw (c);
	    D1.setCenter (x - width / 2 + 20, y - height / 2 + 11);
	    D1.setHeight (14);
	    D1.draw (c);
	}
	if (value == 1)
	{
	    c.drawString ("A", x - width / 2 + 3, y - height / 2 + 18);
	}
	else if (value == 11)
	{
	    c.drawString ("J", x - width / 2 + 3, y - height / 2 + 18);
	}
	else if (value == 12)
	{
	    c.drawString ("Q", x - width / 2 + 3, y - height / 2 + 18);
	}
	else if (value == 13)
	{
	    c.drawString ("K", x - width / 2 + 3, y - height / 2 + 18);
	}
	else
	{
	    c.drawString (Integer.toString (value), x - width / 2 + 3, y - height / 2 + 18);
	}
	setColour (cOldColor);
    }
    */


    public void draw (Graphics c)
    {

	if (isTrans == true)
	    c.setColor (new Color (255, 255, 255, 200));
	else
	    c.setColor (Color.white);
	c.fillRect (x - (width / 2), y - (height / 2), width, height);
	c.setColor (Color.black);
	c.drawRect (x - (width / 2), y - (height / 2), width, height);

	Font f1 = new Font ("TimesNewRoman", Font.BOLD, 16);
	c.setFont (f1);

	Color cOldColor = getColour ();
	if (suit == 1)
	{
	    setColour (Color.black);
	    SpadeClass S1 = new SpadeClass (x, y, width / 3, colour);
	    S1.draw (c);
	    S1 = new SpadeClass (x + (width / 2) - 10, y - (height / 2) + 11, 14, colour);
	    S1.draw (c);
	}
	else if (suit == 2)
	{
	    setColour (Color.red);
	    HeartClass H1 = new HeartClass (x, y, width / 3, colour);
	    H1.draw (c);
	    H1 = new HeartClass (x + (width / 2) - 10, y - (height / 2) + 11, 14, colour);
	    H1.draw (c);
	}
	else if (suit == 3)
	{
	    setColour (Color.black);
	    ClubClass C1 = new ClubClass (x, y, width / 3, colour);
	    C1.draw (c);
	    C1 = new ClubClass (x + (width / 2) - 10, y - (height / 2) + 11, 14, colour);
	    C1.draw (c);
	}
	else if (suit == 4)
	{
	    setColour (Color.red);
	    DiamondClass D1 = new DiamondClass (x, y, width / 3, colour);
	    D1.draw (c);
	    D1 = new DiamondClass (x + (width / 2) - 10, y - (height / 2) + 11, 14, colour);
	    D1.draw (c);
	}
	if (value == 1)
	{
	    c.drawString ("A", x - width / 2 + 3, y - height / 2 + 18);
	}
	else if (value == 11)
	{
	    c.drawString ("J", x - width / 2 + 3, y - height / 2 + 18);
	}
	else if (value == 12)
	{
	    c.drawString ("Q", x - width / 2 + 3, y - height / 2 + 18);
	}
	else if (value == 13)
	{
	    c.drawString ("K", x - width / 2 + 3, y - height / 2 + 18);
	}
	else
	{
	    c.drawString (Integer.toString (value), x - width / 2 + 3, y - height / 2 + 18);
	}
	setColour (cOldColor);
    }

    public void setTrans (boolean setting)
    {
	isTrans = setting;
    }
}
