// The "TableauClass" Class
// Contains a vector of CellClasses for drawing and storing purposes rather than a vector of cards. We found that this implementation helped fix a lot of possible problems.

import java.util.*;
import java.awt.*;

public class TableauClass extends MShapes
{

    private Vector stack = new Vector (0, 1);
    private String size;

    public TableauClass ()
    {
	setSize ("medium");
    }


    public TableauClass (String sizeNew)
    {
	setSize (sizeNew);
    }


    public void forceAddCard (CardClass cardToAdd)  //Forces a card to be added regardless of suit and value
    {
	if (cardToAdd != null)
	{
	    CardClass temp = cardToAdd;
	    temp.setSize (size);
	    stack.addElement (new CellClass (temp));
	    ((CellClass) (stack.elementAt (stack.size () - 1))).setCenter (x, y + (20 * (stack.size () - 1)));
	}
    }


    public void setCenter (int xNew, int yNew)
    {
	super.setCenter (xNew, yNew);
	for (int i = 0 ; i < stack.size () ; i++)
	{
	    ((CellClass) (stack.elementAt (i))).setCenter (xNew, yNew + (20 * i));
	}
    }


    public boolean isValid (int pos)  //Checks to see if a stack in the tableau is a valid arrangement
    {
	if (pos == 0)
	{
	    return true;
	}
	for (int i = pos ; i < stack.size () ; i++)
	{
	    if ((((((CellClass) (stack.elementAt (i - 1))).viewCard (-1)).getValue ()) - (((CellClass) (stack.elementAt (i))).viewCard (-1)).getValue ()) == 1)
	    {
		if ((((((CellClass) (stack.elementAt (i - 1))).viewCard (-1)).getSuit ()) == 1) || (((((CellClass) (stack.elementAt (i - 1))).viewCard (-1)).getSuit ()) == 3))
		{
		    if (((((CellClass) (stack.elementAt (i))).viewCard (-1)).getSuit ()) != 2 && ((((CellClass) (stack.elementAt (i))).viewCard (-1)).getSuit ()) != 4)
		    {
			return false;
		    }
		}
		else if ((((((CellClass) (stack.elementAt (i - 1))).viewCard (-1)).getSuit ()) == 2) || (((((CellClass) (stack.elementAt (i - 1))).viewCard (-1)).getSuit ()) == 4))
		{
		    if (((((CellClass) (stack.elementAt (i))).viewCard (-1)).getSuit ()) != 1 && ((((CellClass) (stack.elementAt (i))).viewCard (-1)).getSuit ()) != 3)
		    {
			return false;
		    }
		}
	    }
	    else
	    {
		return false;
	    }
	}
	return true;


    }


    public boolean addCard (CardClass cardToAdd)  //Adds a card if it is of the opposite suit colour and has a value of 1 less than the parent card
    {
	if (stack.size () > 0)
	{
	    if ((cardToAdd.getSuit () == 1) || (cardToAdd.getSuit () == 3))
	    {
		if (((((CellClass) (stack.elementAt (stack.size () - 1))).viewCard (-1)).getSuit ()) == 2 || ((((CellClass) (stack.elementAt (stack.size () - 1))).viewCard (-1)).getSuit ()) == 4)
		{
		    if ((cardToAdd.getValue () - (((CellClass) (stack.elementAt (stack.size () - 1))).viewCard (-1)).getValue ()) == -1)
		    {
			forceAddCard (cardToAdd);
			return true;
		    }
		}
	    }
	    else if ((cardToAdd.getSuit () == 2) || (cardToAdd.getSuit () == 4))
	    {
		if (((((CellClass) (stack.elementAt (stack.size () - 1))).viewCard (-1)).getSuit ()) == 1 || ((((CellClass) (stack.elementAt (stack.size () - 1))).viewCard (-1)).getSuit ()) == 3)
		{
		    if ((cardToAdd.getValue () - (((CellClass) (stack.elementAt (stack.size () - 1))).viewCard (-1)).getValue ()) == -1)
		    {
			forceAddCard (cardToAdd);
			return true;
		    }
		}
	    }
	    return false;
	}
	else
	{
	    forceAddCard (cardToAdd);
	    return true;
	}
    }


    public void forceAddCardAt (CardClass cardToAdd, int pos)
    {
	if (stack.size () == 0)
	{
	    forceAddCard (cardToAdd);
	}
	else
	{
	    CardClass temp = cardToAdd;
	    temp.setSize (size);
	    stack.insertElementAt (new CellClass (temp), pos);
	    setCenter (x, y);
	}
    }


    public CardClass dealCard (int pos)
    {
	if (pos == stack.size ())
	{
	    return ((CardClass) ((CellClass) (stack.remove (stack.size () - 1))).dealCard (-1));
	}
	else
	{
	    return ((CardClass) ((CellClass) (stack.remove (stack.size () - 1))).dealCard (-1));
	}
    }


    public void removeCard (int pos)
    {
	if (stack.size () > 0)
	{
	    if (pos >= stack.size () || pos == -1)
	    {
		stack.removeElementAt (stack.size () - 1);
	    }
	    else
	    {
		stack.removeElementAt (pos);
	    }
	}
    }


    public CardClass viewCard (int pos)
    {
	if (pos >= stack.size () || pos == -1)
	{
	    return ((CardClass) ((CellClass) (stack.elementAt (stack.size () - 1))).viewCard (-1));
	}
	else
	{
	    return ((CardClass) ((CellClass) (stack.elementAt (pos))).viewCard (-1));
	}
    }


    public int whichCard (int xPos, int yPos)  //Returns which card the x and y positions are located on.
    {
	if (stack.size () > 0)
	{
	    for (int i = 0 ; i < stack.size () ; i++)
	    {
		if ((yPos >= (y - (height / 2) + (20 * i))) && (yPos <= (y - (height / 2) + (20 * (i + 1)))))
		{
		    if ((xPos >= x - (width / 2)) && (xPos <= x + (width / 2)))
		    {
			return i + 1;
		    }
		}
	    }
	    if ((yPos >= (y - (height / 2) + (20 * stack.size () - 1))) && (yPos <= (y - (height / 2) + (20 * (stack.size () - 1)) + (((CellClass) (stack.elementAt (stack.size () - 1))).viewCard (-1)).getHeight ())))
	    {
		if ((xPos >= x - (width / 2)) && (xPos <= x + (width / 2)))
		{
		    return stack.size ();
		}
	    }
	}


	return 0;
    }


    public boolean isPointInside (int getx, int gety)
    {
	if (getx > x - width / 2 && getx < x + width / 2)
	{
	    if (gety > (y - (height / 2)) && gety < (y + (height / 2) + (20 * (stack.size () - 1))))
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


    public boolean isEmpty ()
    {
	return stack.isEmpty ();
    }


    public int getCardCount ()
    {
	return stack.size ();
    }


    public void setSize (String sizeNew)
    {
	size = sizeNew;
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


	for (int i = 0 ; i < stack.size () ; i++)
	{
	    ((CellClass) (stack.elementAt (i))).setSize (sizeNew);
	}
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


    /*public void draw (Console c)
    {
	if (stack.size () != 0)
	{
	    for (int i = 0 ; i < stack.size () ; i++)
	    {
		((CellClass) (stack.elementAt (i))).draw (c);
	    }
	}
    }*/


    public void draw (Graphics c)
    {
	if (stack.size () != 0)
	{
	    for (int i = 0 ; i < stack.size () ; i++)
	    {
		((CellClass) (stack.elementAt (i))).draw (c);
	    }
	}
	else
	{
	    c.setColor (Color.gray);
	    c.draw3DRect (x - (width / 2), y - (height / 2), width, height, false);
	    c.setColor (Color.black);
	}
    }


    public void drawTransparent (Graphics c)
    {
	for (int i = 0 ; i < stack.size () ; i++)
	{
	    ((CellClass) (stack.elementAt (i))).setTrans (true);
	}
	draw (c);
	for (int i = 0 ; i < stack.size () ; i++)
	{
	    ((CellClass) (stack.elementAt (i))).setTrans (false);
	}
    }


    public int getX (int pos)  //gets the x position of a specific card
    {
	if (pos == -1)
	{
	    return x;
	}
	return ((CellClass) (stack.elementAt (pos))).getX ();
    }


    public int getY (int pos)  //gets the x position of a specific card
    {
	if (pos == -1)
	{
	    return y - 20;
	}
	return ((CellClass) (stack.elementAt (pos))).getY ();
    }
}


