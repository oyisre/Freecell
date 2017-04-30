// The "CellClass" class.
// A deck that contains only 1 card.

import java.util.*;
import java.awt.*;

public class CellClass extends DeckClass
{

    public CellClass ()
    {

    }

    public CellClass (String sizeNew)
    {
	super.setSize(sizeNew);
    }

    public CellClass (int xNew, int yNew)
    {
	x = xNew;
	y = yNew;
    }


    public CellClass (CardClass cardToAdd)
    {
	addCard (cardToAdd, -1);
    }

    public boolean addCard (CardClass cardToAdd, int Pos)
    {
	if (deck.size () == 0) //Since the cell can only hold one card, addCard is overridden to accept only one card.
	{
	    return super.addCard (cardToAdd, Pos);
	}
	else
	{
	    return false;
	}
    }
}
