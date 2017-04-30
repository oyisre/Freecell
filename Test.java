// The "DiamondApplet" class.
import java.applet.*;
import java.awt.*;
import hsa.Console;
import java.awt.event.*;

public class Test extends Applet implements ActionListener, MouseListener, MouseMotionListener
{
    // Place instance variables here
    Graphics g;   // declares a graphics canvas for drawing
    Console c;

    Choice choiceSize = new Choice ();
    {
	choiceSize.add ("Small");        // H = 60;  W = 45
	choiceSize.add ("Medium");      // H = 80;  W = 60
	choiceSize.add ("Large");       // H = 100; W = 75
	choiceSize.add ("Extra Large");       // H = 100; W = 75
    }


    boolean OKtoMove = false;

    DeckClass D1;
    DeckClass D2;
    CardClass C1;
    CardClass C2;
    DeckClass CurrentCard;

    public void init ()
    {
	g = getGraphics ();

	C1 = new CardClass ();
	C1.setCenter (500, 100);
	C2 = new CardClass ();
	C2.setCenter (300, 100);

	D1 = new DeckClass ('s', "extra large");
	D2 = new DeckClass ('s', "extra large");

	D1.setCenter (300, 500);
	D2.setCenter (500, 500);

	D1.draw (g);
	D2.draw (g);

	CurrentCard = new DeckClass ();
	CurrentCard.setCenter (200, 200);

	add (choiceSize);

	addMouseListener (this);
	addMouseMotionListener (this);
    }


    public boolean action (Event e, Object o)
    {

	if (e.target instanceof Choice)
	{
	    if (choiceSize.getSelectedIndex () == 0)
	    {
		D1.setSize ("small");
		D2.setSize ("small");
	    }
	    else if (choiceSize.getSelectedIndex () == 1)
	    {
		D1.setSize ("medium");
		D2.setSize ("medium");
	    }
	    else if (choiceSize.getSelectedIndex () == 2)
	    {
		D1.setSize ("large");
		D2.setSize ("large");
	    }
	    else if (choiceSize.getSelectedIndex () == 3)
	    {
		D1.setSize ("extra large");
		D2.setSize ("extra large");
	    }
	}
	repaint (); //Calls paint
	return true;
    }


    public void actionPerformed (ActionEvent e)
    {
    }


    public void mouseClicked (MouseEvent e)
    {
    }


    public void mouseEntered (MouseEvent e)
    {
    }


    public void mouseExited (MouseEvent e)
    {
    }


    public void mousePressed (MouseEvent e)
    {
	if (CurrentCard.getCardCount () == 0)
	{
	    if (D1.isPointInside (e.getX (), e.getY ()) == true)
	    {
		OKtoMove = true;
		CurrentCard.addCard (D1.dealCard (-1), -1);
		CurrentCard.draw (g);
		repaint ();
	    }
	    else if (D2.isPointInside (e.getX (), e.getY ()) == true)
	    {
		OKtoMove = true;
		CurrentCard.addCard (D2.dealCard (-1), -1);
		CurrentCard.draw (g);
		repaint ();
	    }
	}
    }


    public void mouseReleased (MouseEvent e)
    {
	OKtoMove = false;
    }


    public void mouseDragged (MouseEvent e)
    {
	if (OKtoMove == true)
	{
	    D1.setCenter (e.getX (), e.getY ());
	    CurrentCard.draw (g);
	    repaint ();
	}

    }


    public void mouseMoved (MouseEvent e)
    {
    }


    public void paint (Graphics g)
    {

	C1.draw (g);
	C2.draw (g);
	D1.draw (g);
	D2.draw (g);
	//CurrentCard.draw (g);

    }
}
