// The "DeckClass" class.
// A container of a vector of card objects.

import java.util.*;
import java.awt.*;

public class DeckClass extends MShapes
{
    protected Vector deck = new Vector (0, 1);
    protected String size = "medium";


    public DeckClass ()
    {
	height = 80;
	width = height * 7 / 10;
    }


    public DeckClass (char deckType, String sizeNew)
    {
	x = 100;
	y = 100;
	setSize (sizeNew);
	if (deckType == 's') // std deck
	{
	    // generate 52 cards (2 nested loops) and add them to the deck
	    for (int i = 4 ; i > 0 ; i--)
	    {
		for (int j = 13 ; j > 0 ; j--)
		{
		    //CardClass cTemp = new CardClass (x, y, i, j, size, false);
		    addCard (new CardClass (x, y, i, j, size, false), 0);
		}
	    }
	}
    }


    public void setCenter (int xNew, int yNew)  //Sets the center for the DeckClass object and all contained CardClass objects
    {
	super.setCenter (xNew, yNew);
	for (int i = 0 ; i < deck.size () ; i++)
	{
	    ((CardClass) (deck.elementAt (i))).setCenter (xNew, yNew);
	}
    }


    public void setSize (String sizeNew)  //Sets the size for the DeckClass object and all contained CardClass objects
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
	for (int i = 0 ; i < deck.size () ; i++)
	{
	    ((CardClass) (deck.elementAt (i))).setSize (sizeNew);
	}
    }


    public boolean addCard (CardClass cardToAdd, int Pos)
    {
	if (Pos == -1 || deck.size () == 0 || Pos > deck.size ())
	{
	    deck.addElement (cardToAdd);
	    ((CardClass) (deck.elementAt (deck.size () - 1))).setCenter (x, y);

	}
	else
	{
	    deck.insertElementAt (cardToAdd, Pos);
	    ((CardClass) (deck.elementAt (Pos))).setCenter (x, y);
	}
	return true;
    }


    public CardClass dealCard (int Pos)  //Returns a CardClass object and removes it from the deck
    {
	if (deck.size () != 0)
	{
	    if (Pos <= deck.size () && Pos > -1)
	    {
		return (CardClass) (deck.remove (Pos));
	    }
	    else
	    {
		return (CardClass) (deck.remove (deck.size () - 1));
	    }
	}
	//System.out.println ("returned null");
	return null;
    }


    public void removeCard (int Pos)  //removes a card from the deck
    {
	if (deck.size () != 0)
	{
	    if (Pos <= deck.size () && Pos > -1)
	    {
		deck.removeElementAt (Pos);
	    }
	    else
	    {
		deck.removeElementAt (deck.size () - 1);
	    }
	}
    }


    public CardClass viewCard (int Pos)  //returns a CardClass object
    {
	if (Pos <= deck.size () && Pos > -1)
	{
	    return (CardClass) (deck.elementAt (Pos));
	}
	else
	{
	    return (CardClass) (deck.elementAt (deck.size () - 1));
	}
    }


    public void shuffle ()
    {
	for (int i = 0 ; i < (deck.size () * 2) ; i++)
	{
	    addCard (dealCard ((int) (i / 2)), ((int) (Math.random () * deck.size ())));
	}
    }


    public boolean isEmpty ()
    {
	return deck.isEmpty ();
    }


    /*public void draw (Console c)
    {
	if (deck.size () != 0)
	{
	    ((CardClass) (deck.elementAt (deck.size () - 1))).draw (c);
	}
    }*/


    public void draw (Graphics c)
    {
	if (deck.size () != 0)
	{
	    ((CardClass) (deck.elementAt (deck.size () - 1))).draw (c);
	}
	else
	{
	    c.setColor (Color.gray);
	    c.draw3DRect (x - (width / 2), y - (height / 2), width, height, false);
	    c.setColor (Color.black);
	}
    }


    public void setTrans (boolean setting)
    {
	for (int i = 0 ; i < deck.size () ; i++)
	{
	    ((CardClass) (deck.elementAt (i))).setTrans (setting);
	}
    }


    public int getCardCount ()
    {
	return deck.size ();
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


    /*public void erase (Console c)
    {
	c.setColour (Color.white);
	c.fillRect (x - (width / 2), y - (height / 2), width + 1, height + 1);
	c.setColour (Color.black);
    }*/


    public void erase (Graphics c)
    {
	c.setColor (Color.white);
	c.fillRect (x - (width / 2), y - (height / 2), width + 1, height + 1);
	c.setColor (Color.black);
    }
}
