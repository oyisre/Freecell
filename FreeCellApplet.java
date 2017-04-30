// The "FreeCellApplet" class.
// The main game class

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.image.BufferStrategy;
import java.util.*;
import javax.imageio.*;
import java.io.*;

public class FreeCellApplet extends Applet implements ActionListener, MouseListener, MouseMotionListener
{
    // Place instance variables here
    //Graphics g;
    Graphics backBuffer; // declares a graphics canvas for drawing
    Image offscreen; // declares an image to use for the buffer
    BufferedImage img;
    Panel pde = new Panel ();

    Button buttonNewGame = new Button ("New Game");
    Button buttonUndo = new Button ("Undo");
    Button buttonReset = new Button ("Reset");
    Button buttonInstructions = new Button ("Instructions");
    Button buttonSolve = new Button ("Quick Solve");
    Choice choiceSize = new Choice ();
    {
	choiceSize.add ("Small");        // H = 60;  W = 45
	choiceSize.add ("Medium");      // H = 80;  W = 60
	choiceSize.add ("Large");       // H = 100; W = 75
	choiceSize.add ("Extra Large");       // H = 120; W = 84
    }


    Vector undoList; //Vector to store moves to undo.
    //various boolean variables for resetting the gamescreen
    boolean newgameFlag = true;
    boolean releaseFlag = false;
    boolean menuFlag = true;
    boolean instructionsFlag = false;
    boolean wonFlag = false;
    boolean isHeld = false;
    boolean resetPos = true;
    int temp; //General temp variable to be used throughout
    //Variables used to store where to highlight.
    int highlightX, highlightY, highlightHeight, highlightWidth;
    boolean isHovering = false; //should the highlight be drawn
    //to temporarily store where the selected card(s) are from
    int selectedType = 0; //1 == cell, 2 == foundation, 3 == tableau
    int selectedColumn;
    //How many cells are free.
    int freeCells;
    double shadowDist = 1; //Offset for shadow
    //Offset of the card based on where the mouse is.
    int mouseDiffX = 0;
    int mouseDiffY = 0;
    //Global size variables for cards
    String size;
    int globalWidth;
    int globalHeight;

    int moveCount = 0;

    CellClass Cells[] = new CellClass [4];
    FoundationClass Foundations[] = new FoundationClass [4];
    TableauClass Tableaux[] = new TableauClass [8];

    TableauClass CurrentCard; //A hidden tableau object that will store held cards.

    public void init ()
    {
	size = "small"; //Defaults the size to small for button positioning purposes.
	globalWidth = 45;
	globalHeight = 60;
	setSize (globalWidth * 12, globalHeight * 6); //Resize window based on size.
	setLayout (new BorderLayout ());
	// Place the body of the initialization method here
	pde.setLayout (new GridLayout ());
	pde.add ("North", buttonNewGame);
	pde.add ("North", buttonInstructions);
	pde.add ("North", choiceSize);
	pde.add ("North", buttonUndo);
	pde.add ("North", buttonSolve);
	pde.add ("North", buttonReset);
	add ("North", pde);
	//buttonPanel.add ("North", buttonUndo);
	//buttonPanel.add ("North", buttonReset);
	//buttonPanel.add ("North", buttonInstructions);
	setBackground (new Color (0, 150, 0)); //The background's green colour
	choiceSize.select (1); //Default the dropbox to position 1 ("Medium")
	try
	{
	    img = ImageIO.read (new File ("startScreen.png"));
	}
	catch (IOException e)
	{
	}
	/*add (buttonNewGame);   // adds button to canvas
	add (buttonReset);
	add (buttonUndo);
	add (choiceSize);
	add (buttonInstructions);
	add (buttonSolve);*/
	buttonSolve.disable ();
	buttonInstructions.disable ();
	buttonReset.disable ();
	choiceSize.disable ();
	buttonNewGame.addActionListener (this);
	buttonUndo.addActionListener (this);
	buttonReset.addActionListener (this);
	buttonInstructions.addActionListener (this);
	buttonSolve.addActionListener (this);
	addMouseListener (this);
	addMouseMotionListener (this);

	resetVars (); //Calls resetVars to start the game.

    } // init method


    public void checkFreeCells ()  //Scans for free cells.
    {
	freeCells = 0;
	for (int i = 0 ; i < 4 ; i++)
	{
	    if (Cells [i].isEmpty () == true)
	    {
		freeCells++;
	    }
	}
	for (int i = 0 ; i < 8 ; i++)
	{
	    if (Tableaux [i].isEmpty () == true)
	    {
		freeCells++;
	    }
	}
    }


    public void startGame ()  //Hides the menu screen image
    {
	menuFlag = false;
	choiceSize.enable ();
	buttonSolve.enable ();
	buttonInstructions.enable ();
	buttonReset.enable ();
	resetVars ();
    }


    public boolean action (Event e, Object o)
    {

	if (e.target instanceof Choice) //If a size is selected from the drop down menu, this function will resize the window and set the size of all cards and objects to the selected one.
	{
	    if (choiceSize.getSelectedIndex () == 0)
	    {
		size = "small";
		for (int i = 0 ; i < 4 ; i++)
		{
		    Cells [i].setSize (size);
		    Foundations [i].setSize (size);
		}
		for (int i = 0 ; i < 8 ; i++)
		{
		    Tableaux [i].setSize (size);
		}
		globalWidth = 45;
		globalHeight = 60;
	    }
	    else if (choiceSize.getSelectedIndex () == 1)
	    {
		size = "medium";
		for (int i = 0 ; i < 4 ; i++)
		{
		    Cells [i].setSize (size);
		    Foundations [i].setSize (size);
		}
		for (int i = 0 ; i < 8 ; i++)
		{
		    Tableaux [i].setSize (size);
		}
		globalWidth = 60;
		globalHeight = 80;
	    }
	    else if (choiceSize.getSelectedIndex () == 2)
	    {
		size = "large";
		for (int i = 0 ; i < 4 ; i++)
		{
		    Cells [i].setSize (size);
		    Foundations [i].setSize (size);
		}
		for (int i = 0 ; i < 8 ; i++)
		{
		    Tableaux [i].setSize (size);
		}
		globalWidth = 75;
		globalHeight = 100;
	    }
	    else if (choiceSize.getSelectedIndex () == 3)
	    {
		size = "extra large";
		for (int i = 0 ; i < 4 ; i++)
		{
		    Cells [i].setSize (size);
		    Foundations [i].setSize (size);
		}
		for (int i = 0 ; i < 8 ; i++)
		{
		    Tableaux [i].setSize (size);
		}
		globalWidth = 84;
		globalHeight = 120;
	    }
	    resetPos = true;
	    setSize (globalWidth * 12, globalHeight * 6);
	}
	resetVars ();
	return true;
    }


    //Takes 5 values to represent the move. The initial type of deck and column, the final type of deck and column, and the number of cards moved.
    public void isSolved (ActionEvent e)  //Puts as many cards as possible onto the foundation.
    {
	boolean noMovesRemaining = false;

	while (noMovesRemaining == false)
	{
	    noMovesRemaining = true;
	    for (int i = 0 ; i < 4 ; i++)
	    {
		selectedColumn = i;
		selectedType = 1;
		if (Cells [i].getCardCount () != 0)
		{
		    for (int ii = 0 ; ii < 4 ; ii++)
		    {
			if (Foundations [ii].addCard (Cells [i].viewCard (-1), -1) == true)
			{
			    moveCount++;
			    Cells [i].removeCard (-1);

			    undoList.insertElementAt (new Integer (1), 0);
			    undoList.insertElementAt (new Integer (ii), 0);
			    undoList.insertElementAt (new Integer (2), 0);
			    undoList.insertElementAt (new Integer (selectedColumn), 0);
			    undoList.insertElementAt (new Integer (selectedType), 0);
			    buttonUndo.enable ();

			    noMovesRemaining = false;
			    break;
			}
		    }
		}
	    }

	    for (int i = 0 ; i < 8 ; i++)
	    {
		selectedColumn = i;
		selectedType = 3;
		if (Tableaux [i].getCardCount () != 0)
		{
		    for (int ii = 0 ; ii < 4 ; ii++)
		    {

			if (Foundations [ii].addCard (Tableaux [i].viewCard (-1), -1) == true)
			{

			    moveCount++;
			    Tableaux [i].removeCard (-1);

			    undoList.insertElementAt (new Integer (1), 0);
			    undoList.insertElementAt (new Integer (ii), 0);
			    undoList.insertElementAt (new Integer (2), 0);
			    undoList.insertElementAt (new Integer (selectedColumn), 0);
			    undoList.insertElementAt (new Integer (selectedType), 0);
			    buttonUndo.enable ();

			    noMovesRemaining = false;

			    break;
			}
		    }
		}
	    }
	}

	wonFlag = isWon ();

    }


    public void actionPerformed (ActionEvent e)
    {
	Object objSource = e.getSource ();
	if (objSource == buttonNewGame) //Restarts the game if NewGame is selected.
	{
	    if (menuFlag == true)
	    {
		startGame ();
	    }
	    newgameFlag = true;
	    instructionsFlag = false;
	    wonFlag = false;
	    resetVars ();
	}
	else if (objSource == buttonUndo) //Undoes the last move
	{
	    moveCount++;
	    buttonSolve.enable ();
	    buttonNewGame.enable();
	    buttonReset.enable ();
	    instructionsFlag = false;
	    wonFlag = false;
	    undoMove ();
	}
	else if (objSource == buttonReset) //Resets the game using the same deck.
	{
	    instructionsFlag = false;
	    wonFlag = false;
	    while (buttonUndo.isEnabled () == true)
	    {
		undoMove ();
	    }
	}
	else if (objSource == buttonInstructions)
	{
	    try
	    {
		img = ImageIO.read (new File ("instructionsImage.png"));
	    }
	    catch (IOException e2)
	    {
	    }
	    if (instructionsFlag == false)
	    {
		buttonSolve.disable ();
		buttonReset.disable ();
		buttonNewGame.disable ();
		instructionsFlag = true;
	    }
	    else
	    {
		buttonSolve.enable ();
		buttonReset.enable ();
		buttonNewGame.enable ();
		instructionsFlag = false;
	    }
	    repaint ();
	}
	else if (objSource == buttonSolve)
	{
	    isSolved (e);
	    repaint ();
	}
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
	if (CurrentCard.getCardCount () == 0) //To ensure no card is currently selected somehow
	{
	    for (int i = 0 ; i < 4 ; i++)
	    {
		if (Cells [i].isPointInside (e.getX (), e.getY ()) == true)
		{
		    if (Cells [i].isEmpty () == false)
		    {
			CurrentCard.setCenter (Cells [i].getX (), Cells [i].getY ());
			CurrentCard.forceAddCard (Cells [i].dealCard (-1));
			selectedType = 1;
			selectedColumn = i;
			isHeld = true;
			undoList.insertElementAt (new Integer (1), 0);
		    }
		    i = 4;
		}
		else if (Foundations [i].isPointInside (e.getX (), e.getY ()) == true)
		{
		    if (Foundations [i].isEmpty () == false)
		    {
			CurrentCard.setCenter (Foundations [i].getX (), Foundations [i].getY ());
			CurrentCard.forceAddCard (Foundations [i].dealCard (-1));
			selectedType = 2;
			selectedColumn = i;
			isHeld = true;
			undoList.insertElementAt (new Integer (1), 0);
			wonFlag = false;
		    }
		    i = 4;
		}
		else if (Tableaux [(i * 2)].isPointInside (e.getX (), e.getY ()) == true)
		{
		    if (Tableaux [(i * 2)].isEmpty () == false)
		    {
			if (Tableaux [(i * 2)].whichCard (e.getX (), e.getY ()) == Tableaux [(i * 2)].getCardCount ())
			{
			    CurrentCard.setCenter (Tableaux [(i * 2)].getX (Tableaux [(i * 2)].whichCard (e.getX (), e.getY ()) - 1), Tableaux [(i * 2)].getY (Tableaux [(i * 2)].whichCard (e.getX (), e.getY ()) - 1));
			    CurrentCard.forceAddCard (Tableaux [(i * 2)].dealCard (-1));
			    selectedType = 3;
			    selectedColumn = (i * 2);
			    isHeld = true;
			    undoList.insertElementAt (new Integer (1), 0);
			}
			else if (Tableaux [(i * 2)].getCardCount () - (Tableaux [(i * 2)].whichCard (e.getX (), e.getY ())) <= freeCells)
			{
			    if (Tableaux [(i * 2)].isValid (Tableaux [(i * 2)].whichCard (e.getX (), e.getY ())) == true)
			    {
				selectedType = 3;
				selectedColumn = (i * 2);
				isHeld = true;
				undoList.insertElementAt (new Integer ((Tableaux [(i * 2)].getCardCount () - (Tableaux [(i * 2)].whichCard (e.getX (), e.getY ()))) + 1), 0);
				for (int j = (Tableaux [(i * 2)].getCardCount () - (Tableaux [(i * 2)].whichCard (e.getX (), e.getY ()))) ; j >= 0 ; j--)
				{
				    CurrentCard.forceAddCardAt (Tableaux [(i * 2)].dealCard (-1), 0);
				}
				CurrentCard.setCenter (Tableaux [(i * 2)].getX (Tableaux [(i * 2)].getCardCount () - 1), (Tableaux [(i * 2)].getY (Tableaux [(i * 2)].getCardCount () - 1) + 20));
			    }
			}
		    }
		    i = 4;
		}
		else if (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].isPointInside (e.getX (), e.getY ()) == true)
		{
		    if (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].isEmpty () == false)
		    {
			if (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].whichCard (e.getX (), e.getY ()) == Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getCardCount ())
			{
			    CurrentCard.setCenter (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getX (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].whichCard (e.getX (), e.getY ()) - 1), Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getY (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].whichCard (e.getX (), e.getY ()) - 1));
			    CurrentCard.forceAddCard (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].dealCard (-1));
			    selectedType = 3;
			    selectedColumn = (int) ((((float) (i)) + 0.5) * 2);
			    isHeld = true;
			    undoList.insertElementAt (new Integer (1), 0);
			}
			else if (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getCardCount () - (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].whichCard (e.getX (), e.getY ())) <= freeCells)
			{
			    if (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].isValid (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].whichCard (e.getX (), e.getY ())) == true)
			    {
				selectedType = 3;
				selectedColumn = (int) ((((float) (i)) + 0.5) * 2);
				isHeld = true;
				undoList.insertElementAt (new Integer ((Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getCardCount () - (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].whichCard (e.getX (), e.getY ()))) + 1), 0);
				for (int j = (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getCardCount () - (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].whichCard (e.getX (), e.getY ()))) ; j >= 0 ; j--)
				{
				    CurrentCard.forceAddCardAt (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].dealCard (-1), 0);
				}
				CurrentCard.setCenter (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getX (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getCardCount () - 1), (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getY (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getCardCount () - 1) + 20));
			    }
			}
		    }
		    i = 4;
		}
	    }
	    if (isHeld == true)
	    {
		mouseDiffX = e.getX () - (CurrentCard.viewCard (0)).getX ();
		mouseDiffY = e.getY () - (CurrentCard.viewCard (0)).getY ();
		CurrentCard.setCenter (e.getX () - (mouseDiffX), e.getY () - (mouseDiffY));
		CurrentCard.setSize (size);
		repaint ();
	    }
	}
	checkFreeCells ();
    }


    public void mouseReleased (MouseEvent e)
    {
	if (CurrentCard.getCardCount () == 1)
	{
	    releaseFlag = true;
	    for (int i = 0 ; i < 4 ; i++)
	    {
		if (Cells [i].isPointInside (e.getX (), e.getY ()) == true)
		{
		    if (Cells [i].addCard (CurrentCard.viewCard (-1), -1) == true)
		    {
			undoList.insertElementAt (new Integer (i), 0);
			undoList.insertElementAt (new Integer (1), 0);
			CurrentCard.removeCard (-1);
			releaseFlag = false;
		    }
		    i = 4;
		}
		else if (Foundations [i].isPointInside (e.getX (), e.getY ()) == true)
		{
		    if (Foundations [i].addCard (CurrentCard.viewCard (-1), -1) == true)
		    {
			undoList.insertElementAt (new Integer (i), 0);
			undoList.insertElementAt (new Integer (2), 0);
			CurrentCard.removeCard (-1);
			releaseFlag = false;
			wonFlag = isWon ();
		    }
		    i = 4;
		}
		else if (Tableaux [(i * 2)].isPointInside (e.getX (), e.getY ()) == true)
		{
		    if (Tableaux [(i * 2)].addCard (CurrentCard.viewCard (-1)) == true)
		    {
			undoList.insertElementAt (new Integer ((i * 2)), 0);
			undoList.insertElementAt (new Integer (3), 0);
			CurrentCard.removeCard (-1);
			releaseFlag = false;
		    }
		    i = 4;
		}
		else if (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].isPointInside (e.getX (), e.getY ()) == true)
		{
		    if (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].addCard (CurrentCard.viewCard (-1)) == true)
		    {
			undoList.insertElementAt (new Integer ((int) ((((float) (i)) + 0.5) * 2)), 0);
			undoList.insertElementAt (new Integer (3), 0);
			CurrentCard.removeCard (-1);
			releaseFlag = false;
		    }
		    i = 4;
		}
	    }
	    if (releaseFlag == true) //If the card was not placed on any suitable location, it's location is reset.
	    {
		if (selectedType == 1)
		{
		    Cells [selectedColumn].addCard (CurrentCard.dealCard (-1), -1);
		}
		else if (selectedType == 2)
		{
		    Foundations [selectedColumn].addCard (CurrentCard.dealCard (-1), -1);
		    wonFlag = isWon ();
		}
		else if (selectedType == 3)
		{
		    Tableaux [selectedColumn].forceAddCard (CurrentCard.dealCard (-1));
		}
		isHovering = false;
		undoList.removeElementAt (0);
	    }
	    else
	    {
		moveCount++;
		undoList.insertElementAt (new Integer (selectedColumn), 0);
		undoList.insertElementAt (new Integer (selectedType), 0);
		buttonUndo.enable ();
	    }
	}
	else if (CurrentCard.getCardCount () > 1)
	{
	    releaseFlag = true;
	    for (int i = 0 ; i < 8 ; i++)
	    {
		if (Tableaux [i].isPointInside (e.getX (), e.getY ()) == true)
		{
		    if (Tableaux [i].addCard (CurrentCard.viewCard (0)) == true)
		    {
			undoList.insertElementAt (new Integer (i), 0);
			undoList.insertElementAt (new Integer (3), 0);
			for (int j = 1 ; j < CurrentCard.getCardCount () ; j++)
			{
			    Tableaux [i].forceAddCard (CurrentCard.viewCard (j));
			}
			for (int j = 0 ; j <= 16 ; j++)
			{
			    CurrentCard.removeCard (-1);
			}
			releaseFlag = false;
		    }
		    i = 8;
		}
	    }
	    if (releaseFlag == true)
	    {
		temp = Tableaux [selectedColumn].getCardCount ();
		for (int i = CurrentCard.getCardCount () ; i > 0 ; i--)
		{
		    Tableaux [selectedColumn].forceAddCardAt (CurrentCard.dealCard (-1), temp);
		}
		undoList.removeElementAt (0);
	    }
	    else
	    {
		moveCount++;
		undoList.insertElementAt (new Integer (selectedColumn), 0);
		undoList.insertElementAt (new Integer (selectedType), 0);
		buttonUndo.enable ();
	    }
	}
	checkHover (e);
	isHeld = false;
	mouseDiffX = 0;
	mouseDiffY = 0;
	selectedType = 0;
	selectedColumn = -2;
	checkFreeCells ();
	repaint ();
    }


    public void mouseDragged (MouseEvent e)
    {
	CurrentCard.setCenter (e.getX () - mouseDiffX, e.getY () - mouseDiffY); //Move the card's center position in relation to the mouse's movement.
	checkHover (e);
    }


    public void mouseMoved (MouseEvent e)
    {
	checkHover (e); //check if the mouse is hovering over anything
    }


    public void update (Graphics g)  //Overrides a built in method that clears screen then calls paint but we don't want to screen to be erased here.
    {
	paint (g);
    }


    public void paint (Graphics g)  //Draws the screen.
    {
	// Wipe off everything that has been drawn before
	// Otherwise previous drawings would also be displayed.
	Dimension Screen = getSize ();
	backBuffer.setColor (new Color (0, 150, 0));
	backBuffer.fillRect (0, 0, Screen.width, Screen.height);
	//Draws the card containers
	if (instructionsFlag == true) //Draws the instructions image
	{
	    if (choiceSize.getSelectedIndex () == 0)
	    {
		choiceSize.select (1);
		size = "medium";
		for (int i = 0 ; i < 4 ; i++)
		{
		    Cells [i].setSize (size);
		    Foundations [i].setSize (size);
		}
		for (int i = 0 ; i < 8 ; i++)
		{
		    Tableaux [i].setSize (size);
		}
		globalWidth = 60;
		globalHeight = 80;
		resetPos = true;
		setSize (globalWidth * 12, globalHeight * 6);
		resetVars ();
	    }
	    backBuffer.fillRect (0, 0, 84 * 12, 120 * 6);
	    backBuffer.drawImage (img, 0, 0, this);
	    g.drawImage (offscreen, 0, 0, this);
	    return;
	}
	else if (menuFlag == true) //Draws the intro menu
	{
	    if (size == "small")
	    {
		size = "medium"; //Defaults the size to medium
		globalWidth = 60;
		globalHeight = 80;
		setSize (globalWidth * 12, globalHeight * 6); //Resize window based on size.
	    }
	    g.drawImage (img, 0, 0, this);
	    return;
	}
	for (int i = 0 ; i < 8 ; i++)
	{
	    Tableaux [i].draw (backBuffer);
	}
	if (wonFlag == true) //Draws the win message
	{
	    try
	    {
		img = ImageIO.read (new File ("winImage.png"));
	    }
	    catch (IOException e2)
	    {
	    }
	    backBuffer.setColor (new Color (0, 150, 0));
	    backBuffer.fillRect (0, 0, 84 * 12, 120 * 6);
	    if (choiceSize.getSelectedIndex () == 0)
	    {
		choiceSize.select (1);
		size = "medium";
		for (int i = 0 ; i < 4 ; i++)
		{
		    Cells [i].setSize (size);
		    Foundations [i].setSize (size);
		}
		for (int i = 0 ; i < 8 ; i++)
		{
		    Tableaux [i].setSize (size);
		}
		globalWidth = 60;
		globalHeight = 80;
		resetPos = true;
		setSize (globalWidth * 12, globalHeight * 6);
		resetVars ();
	    }
	    backBuffer.setColor (new Color (0, 150, 0));
	    backBuffer.fillRect (0, 0, 84 * 12, 120 * 6);
	    backBuffer.drawImage (img, 0, 0, this);
	    backBuffer.fillRect (0, 0, Screen.width, Screen.height);
	    backBuffer.drawImage (img, 0, 0, this);
	}


	for (int i = 0 ; i < 4 ; i++)
	{
	    Cells [i].draw (backBuffer);
	    Foundations [i].draw (backBuffer);
	}


	backBuffer.setColor (Color.black);
	backBuffer.setFont (new Font ("Helvetica", Font.BOLD, 16));
	backBuffer.drawString ("Moves:", 10, globalHeight * 6 - 30);
	backBuffer.drawString (Integer.toString (moveCount), 10, globalHeight * 6 - 10);
	if (isHovering == true) //Draws the yellow highlight box
	{
	    backBuffer.setColor (new Color (255, 225, 0));
	    backBuffer.drawRect (highlightX - (highlightWidth / 2), highlightY - (highlightHeight / 2), highlightWidth, highlightHeight);
	    backBuffer.drawRect (highlightX - (highlightWidth / 2) - 1, highlightY - (highlightHeight / 2) - 1, highlightWidth + 2, highlightHeight + 2);
	    backBuffer.drawRect (highlightX - (highlightWidth / 2) + 1, highlightY - (highlightHeight / 2) + 1, highlightWidth - 2, highlightHeight - 2);
	}


	if (isHeld == true) //Draws the shadow
	{
	    if (shadowDist <= 10)
	    {
		shadowDist = shadowDist + 0.5;
	    }

	    backBuffer.setColor (new Color (0, 0, 0, 125));
	    backBuffer.fillRect (CurrentCard.getX () - (globalWidth / 2) + (int) shadowDist, CurrentCard.getY () - (globalHeight / 2) + (int) shadowDist, globalWidth, globalHeight + (CurrentCard.getCardCount () - 1) * 20);
	    CurrentCard.drawTransparent (backBuffer);
	}


	else
	{
	    shadowDist = 1;
	}


	// draw the offscreen image to the screen like a normal image.
	// Since offscreen is the screen width we start at 0,0.
	g.drawImage (offscreen, 0, 0, this);

    } // paint method


    public void resetVars ()  //Resets card locations and starts a new game if specified.
    {
	if (newgameFlag == true) //if newgameFlag is true, all game variables are reset.
	{
	    for (int i = 0 ; i < 4 ; i++)
	    {
		Cells [i] = new CellClass (size);
		Foundations [i] = new FoundationClass (size, 5 - (i + 1));
		Cells [i].setCenter ((i + 1) * (4 * globalWidth / 3), 2 * globalHeight / 3 + 30);
		Foundations [i].setCenter ((i + 1) * (4 * globalWidth / 3) + 16 * globalWidth / 3, 2 * globalHeight / 3 + 30);
	    }
	    for (int i = 0 ; i < 8 ; i++)
	    {
		Tableaux [i] = new TableauClass (size);
		Tableaux [i].setCenter ((i + 1) * (4 * globalWidth / 3), 2 * globalHeight + 30);
	    }
	    wonFlag = false;
	    moveCount = 0;
	    checkFreeCells ();
	    CurrentCard = new TableauClass ();
	    freeCells = 4;
	    undoList = new Vector (0, 1);
	    buttonUndo.disable (); //Disables the undo button since no moves are made
	    //clear everything and reset game
	    DeckClass InitializerDeck = new DeckClass ('s', size);
	    InitializerDeck.shuffle ();

	    for (int i = 0 ; i < 8 ; i++)
	    {
		if (i % 2 == 1)
		{
		    for (int ii = 0 ; ii < 6 ; ii++)
		    {
			Tableaux [i].forceAddCard (InitializerDeck.dealCard (-1));
		    }
		}
		else if (i % 2 == 0)
		{
		    for (int ii = 0 ; ii < 7 ; ii++)
		    {
			Tableaux [i].forceAddCard (InitializerDeck.dealCard (-1));
		    }
		}
	    }

	    newgameFlag = false;
	    resetPos = true;
	}


	if (resetPos == true)
	{
	    for (int i = 0 ; i < 4 ; i++)
	    {
		Cells [i].setCenter ((i + 1) * (4 * globalWidth / 3), 2 * globalHeight / 3 + 30);
		Foundations [i].setCenter ((i + 1) * (4 * globalWidth / 3) + 16 * globalWidth / 3, 2 * globalHeight / 3 + 30);
	    }
	    for (int i = 0 ; i < 8 ; i++)
	    {
		Tableaux [i].setCenter ((i + 1) * (4 * globalWidth / 3), 2 * globalHeight + 30);
	    }
	    resetPos = false;
	}


	offscreen = createImage (globalWidth * 12, globalHeight * 6);
	backBuffer = offscreen.getGraphics ();
	repaint ();
    }


    public void checkHover (MouseEvent e)  //Checks if the mouse is hovering over any valid move.
    {
	if (CurrentCard.getCardCount () == 0)
	{
	    for (int i = 0 ; i < 4 ; i++)
	    {
		if ((Cells [i].isPointInside (e.getX (), e.getY ()) == true) && (Cells [i].isEmpty () == false))
		{
		    isHovering = true;
		    highlightX = Cells [i].getX ();
		    highlightY = Cells [i].getY ();
		    highlightHeight = Cells [i].getHeight ();
		    highlightWidth = Cells [i].getWidth ();
		    repaint ();
		    return;
		}
		else if ((Foundations [i].isPointInside (e.getX (), e.getY ()) == true) && (Foundations [i].isEmpty () == false))
		{
		    isHovering = true;
		    highlightX = Foundations [i].getX ();
		    highlightY = Foundations [i].getY ();
		    highlightHeight = Cells [i].getHeight ();
		    highlightWidth = Cells [i].getWidth ();
		    repaint ();
		    return;
		}
		else if ((Tableaux [(i * 2)].isPointInside (e.getX (), e.getY ()) == true) && (Tableaux [(i * 2)].isEmpty () == false) && (Tableaux [(i * 2)].isValid (Tableaux [(i * 2)].whichCard (e.getX (), e.getY ())) == true) && (Tableaux [(i * 2)].getCardCount () - (Tableaux [(i * 2)].whichCard (e.getX (), e.getY ())) <= freeCells))
		{
		    isHovering = true;
		    highlightX = Tableaux [(i * 2)].getX ();
		    highlightY = Tableaux [(i * 2)].getY () + (20 * (Tableaux [i * 2].whichCard (e.getX (), e.getY ()) - 1) + (10 * ((Tableaux [(i * 2)].getCardCount () - (Tableaux [(i * 2)].whichCard (e.getX (), e.getY ()))))));
		    highlightHeight = Tableaux [(i * 2)].getHeight () + (20 * ((Tableaux [(i * 2)].getCardCount () - (Tableaux [(i * 2)].whichCard (e.getX (), e.getY ())))));
		    highlightWidth = Tableaux [(i * 2)].getWidth ();
		    repaint ();
		    return;
		}
		else if ((Tableaux [(int) ((((float) (i)) + 0.5) * 2)].isPointInside (e.getX (), e.getY ()) == true) && (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].isEmpty () == false) && (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].isValid (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].whichCard (e.getX (), e.getY ())) == true) && (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getCardCount () - (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].whichCard (e.getX (), e.getY ())) <= freeCells))
		{
		    isHovering = true;
		    highlightX = Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getX ();
		    highlightY = Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getY () + (20 * (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].whichCard (e.getX (), e.getY ()) - 1) + (10 * ((Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getCardCount () - (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].whichCard (e.getX (), e.getY ()))))));
		    highlightHeight = Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getHeight () + (20 * ((Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getCardCount () - (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].whichCard (e.getX (), e.getY ())))));
		    highlightWidth = Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getWidth ();
		    repaint ();
		    return;
		}
	    }
	}


	else
	{
	    for (int i = 0 ; i < 4 ; i++)
	    {
		if (CurrentCard.getCardCount () == 1)
		{
		    if ((Cells [i].isPointInside (e.getX (), e.getY ()) == true) && (Cells [i].isEmpty () == true))
		    {
			isHovering = true;
			highlightX = Cells [i].getX ();
			highlightY = Cells [i].getY ();
			highlightHeight = Cells [i].getHeight ();
			highlightWidth = Cells [i].getWidth ();
			repaint ();
			return;
		    }
		    else if ((Foundations [i].isPointInside (e.getX (), e.getY ()) == true) && (Foundations [i].addCard (CurrentCard.viewCard (-1), -1) == true))
		    {
			Foundations [i].removeCard (-1);
			CurrentCard.setCenter (e.getX () - mouseDiffX, e.getY () - mouseDiffY);
			isHovering = true;
			highlightX = Foundations [i].getX ();
			highlightY = Foundations [i].getY ();
			highlightHeight = Foundations [i].getHeight ();
			highlightWidth = Foundations [i].getWidth ();
			repaint ();
			return;
		    }
		    else if ((Tableaux [(i * 2)].isPointInside (e.getX (), e.getY ()) == true) && (Tableaux [(i * 2)].whichCard (e.getX (), e.getY ()) == Tableaux [(i * 2)].getCardCount ()))
		    {
			if (Tableaux [(i * 2)].isEmpty () == true)
			{
			    isHovering = true;
			    highlightX = Tableaux [(i * 2)].getX ();
			    highlightY = Tableaux [(i * 2)].getY ();
			    highlightHeight = Tableaux [(i * 2)].getHeight ();
			    highlightWidth = Tableaux [(i * 2)].getWidth ();
			    repaint ();
			    return;
			}
			else if (Tableaux [(i * 2)].addCard (CurrentCard.viewCard (0)) == true)
			{
			    Tableaux [(i * 2)].removeCard (-1);
			    CurrentCard.setCenter (e.getX () - mouseDiffX, e.getY () - mouseDiffY);
			    isHovering = true;
			    highlightX = Tableaux [(i * 2)].getX ();
			    highlightY = Tableaux [(i * 2)].getY () + (20 * (Tableaux [i * 2].whichCard (e.getX (), e.getY ()) - 1) + (10 * ((Tableaux [(i * 2)].getCardCount () - (Tableaux [(i * 2)].whichCard (e.getX (), e.getY ()))))));
			    highlightHeight = Tableaux [(i * 2)].getHeight () + (20 * ((Tableaux [(i * 2)].getCardCount () - (Tableaux [(i * 2)].whichCard (e.getX (), e.getY ())))));
			    highlightWidth = Tableaux [(i * 2)].getWidth ();
			    repaint ();
			    return;
			}
		    }
		    else if (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].isPointInside (e.getX (), e.getY ()) == true)
		    {
			if (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].whichCard (e.getX (), e.getY ()) == Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getCardCount ())
			{
			    if (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].isEmpty () == true)
			    {
				isHovering = true;
				highlightX = Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getX ();
				highlightY = Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getY ();
				highlightHeight = Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getHeight ();
				highlightWidth = Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getWidth ();
				repaint ();
				return;
			    }
			    else if ((Tableaux [(int) ((((float) (i)) + 0.5) * 2)].isEmpty () == true) || (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].addCard (CurrentCard.viewCard (0)) == true))
			    {
				Tableaux [(int) ((((float) (i)) + 0.5) * 2)].removeCard (-1);
				CurrentCard.setCenter (e.getX () - mouseDiffX, e.getY () - mouseDiffY);
				isHovering = true;
				highlightX = Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getX ();
				highlightY = Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getY () + (20 * (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].whichCard (e.getX (), e.getY ()) - 1) + (10 * ((Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getCardCount () - (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].whichCard (e.getX (), e.getY ()))))));
				highlightHeight = Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getHeight () + (20 * ((Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getCardCount () - (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].whichCard (e.getX (), e.getY ())))));
				highlightWidth = Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getWidth ();
				repaint ();
				return;
			    }
			}
		    }
		}
		else
		{
		    if ((Tableaux [(i * 2)].isPointInside (e.getX (), e.getY ()) == true) && (Tableaux [(i * 2)].whichCard (e.getX (), e.getY ()) == Tableaux [(i * 2)].getCardCount ()))
		    {
			if (Tableaux [(i * 2)].isEmpty () == true)
			{
			    isHovering = true;
			    highlightX = Tableaux [(i * 2)].getX ();
			    highlightY = Tableaux [(i * 2)].getY ();
			    highlightHeight = Tableaux [(i * 2)].getHeight ();
			    highlightWidth = Tableaux [(i * 2)].getWidth ();
			    repaint ();
			    return;
			}
			else if (Tableaux [(i * 2)].addCard (CurrentCard.viewCard (0)) == true)
			{
			    Tableaux [(i * 2)].removeCard (-1);
			    CurrentCard.setCenter (e.getX () - mouseDiffX, e.getY () - mouseDiffY);
			    isHovering = true;
			    highlightX = Tableaux [(i * 2)].getX ();
			    highlightY = Tableaux [(i * 2)].getY () + (20 * (Tableaux [i * 2].whichCard (e.getX (), e.getY ()) - 1) + (10 * ((Tableaux [(i * 2)].getCardCount () - (Tableaux [(i * 2)].whichCard (e.getX (), e.getY ()))))));
			    highlightHeight = Tableaux [(i * 2)].getHeight () + (20 * ((Tableaux [(i * 2)].getCardCount () - (Tableaux [(i * 2)].whichCard (e.getX (), e.getY ())))));
			    highlightWidth = Tableaux [(i * 2)].getWidth ();
			    repaint ();
			    return;
			}
		    }
		    else if ((Tableaux [(int) ((((float) (i)) + 0.5) * 2)].isPointInside (e.getX (), e.getY ()) == true) && (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].whichCard (e.getX (), e.getY ()) == Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getCardCount ()))
		    {
			if (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].isEmpty () == true)
			{
			    isHovering = true;
			    highlightX = Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getX ();
			    highlightY = Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getY ();
			    highlightHeight = Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getHeight ();
			    highlightWidth = Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getWidth ();
			    repaint ();
			    return;
			}
			else if ((Tableaux [(int) ((((float) (i)) + 0.5) * 2)].isEmpty () == true) || (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].addCard (CurrentCard.viewCard (0)) == true))
			{
			    Tableaux [(int) ((((float) (i)) + 0.5) * 2)].removeCard (-1);
			    CurrentCard.setCenter (e.getX () - mouseDiffX, e.getY () - mouseDiffY);
			    isHovering = true;
			    highlightX = Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getX ();
			    highlightY = Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getY () + (20 * (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].whichCard (e.getX (), e.getY ()) - 1) + (10 * ((Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getCardCount () - (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].whichCard (e.getX (), e.getY ()))))));
			    highlightHeight = Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getHeight () + (20 * ((Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getCardCount () - (Tableaux [(int) ((((float) (i)) + 0.5) * 2)].whichCard (e.getX (), e.getY ())))));
			    highlightWidth = Tableaux [(int) ((((float) (i)) + 0.5) * 2)].getWidth ();
			    repaint ();
			    return;
			}
		    }
		}
	    }
	}


	if (isHovering == true)
	{
	    isHovering = false;
	}


	repaint ();
    }


    public void undoMove ()  //Reads a vector to perform an undo
    {
	if (undoList.size () > 0) //Takes 5 values to represent the move. The initial type of deck and column, the final type of deck and column, and the number of cards moved.
	{
	    int firstType = ((Integer) (undoList.remove (0))).intValue ();
	    int firstCol = ((Integer) (undoList.remove (0))).intValue ();
	    int endType = ((Integer) (undoList.remove (0))).intValue ();
	    int endCol = ((Integer) (undoList.remove (0))).intValue ();
	    int cardsToUndo = ((Integer) (undoList.remove (0))).intValue ();

	    if (cardsToUndo == 1)
	    {
		if (endType == 1)
		{
		    CurrentCard.forceAddCard (Cells [endCol].dealCard (-1));
		}
		else if (endType == 2)
		{
		    CurrentCard.forceAddCard (Foundations [endCol].dealCard (-1));
		}
		else if (endType == 3)
		{
		    CurrentCard.forceAddCard (Tableaux [endCol].dealCard (-1));
		}
		if (firstType == 1)
		{
		    Cells [firstCol].addCard (CurrentCard.dealCard (-1), -1);
		}
		else if (firstType == 2)
		{
		    Foundations [firstCol].addCard (CurrentCard.dealCard (-1), -1);
		}
		else if (firstType == 3)
		{
		    Tableaux [firstCol].forceAddCard (CurrentCard.dealCard (-1));
		}
	    }
	    else
	    {
		for (int j = cardsToUndo ; j > 0 ; j--)
		{
		    CurrentCard.forceAddCardAt (Tableaux [endCol].dealCard (-1), 0);
		}
		for (int j = 0 ; j < cardsToUndo ; j++)
		{
		    Tableaux [firstCol].forceAddCard (CurrentCard.viewCard (j));
		}
		for (int j = cardsToUndo ; j >= 0 ; j--)
		{
		    CurrentCard.removeCard (-1);
		}
	    }
	}


	if (undoList.size () == 0)
	{
	    buttonUndo.disable (); //If there are no more moves left in the vector, the button is disabled
	}


	checkFreeCells ();
	wonFlag = isWon ();
	repaint ();
    }


    public boolean isWon ()  //Checks if the game is won
    {
	for (int i = 0 ; i < 4 ; i++)
	{
	    if (Foundations [i].getCardCount () != 13) //If there are 13 cards in each foundation, the game is won.
	    {
		return false;
	    }
	}


	try
	{
	    img = ImageIO.read (new File ("winImage.png"));
	}


	catch (IOException e2)
	{
	}


	return true;
    }
}


