// The "FoundationClass" class.
// A deck that contains only 1 suit ascending from Ace to King.

import java.util.*;
import java.awt.*;

public class FoundationClass extends DeckClass
{

    int topVal = 0; //Indicates what value the topmost card represents
    int suitContained;

    FoundationClass ()
    {

    }


    FoundationClass (int suitSelection)
    {
	suitContained = suitSelection;
    }


    FoundationClass (String sizeNew, int suitSelection)
    {
	super.setSize(sizeNew);
	suitContained = suitSelection;
    }


    public boolean addCard (CardClass cardToAdd, int Pos)
    {
	if ((cardToAdd.getSuit () == suitContained) && (cardToAdd.getValue () == (topVal + 1))) //Checks to see if the added card is the right suit and value.
	{
	    topVal++;
	    return super.addCard (cardToAdd, -1);
	}
	else
	{
	    return false;
	}
    }

    public CardClass dealCard (int Pos)
    {
	topVal--;
	return super.dealCard (-1);
    }
    
    public void removeCard (int Pos)
    {
	topVal--;
	super.removeCard(Pos);
    }
    
    public void draw (Graphics g)
    {
	super.draw(g);
	//draws the respected suit indicating which ace the foundation accepts if empty
	if (suitContained == 1)
	{
	    setColour (Color.black);
	    SpadeClass S1 = new SpadeClass (x, y, width / 3, colour);
	    S1.draw (g);
	}
	else if (suitContained == 2)
	{
	    setColour (Color.red);
	    HeartClass H1 = new HeartClass (x, y, width / 3, colour);
	    H1.draw (g);
	}
	else if (suitContained == 3)
	{
	    setColour (Color.black);
	    ClubClass C1 = new ClubClass (x, y, width / 3, colour);
	    C1.draw (g);
	}
	else if (suitContained == 4)
	{
	    setColour (Color.red);
	    DiamondClass D1 = new DiamondClass (x, y, width / 3, colour);
	    D1.draw (g);
	}
    }
}
