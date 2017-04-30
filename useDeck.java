import java.awt.*;
import hsa.Console;

public class useDeck
{
    public static void main (String args[])
    {
	CardClass testC = new CardClass (100, 100, 1, 6, "medium", false);
	DiamondClass testD = new DiamondClass (100,100,100,Color.red);
	Console c = new Console ();
	DeckClass d1 = new DeckClass ('s',"extra large");
	TableauClass t1 = new TableauClass("extra large");
	t1.setCenter(100,100);
	t1.forceAddCard(testC);
	t1.draw(c);
	t1.delay(1000);
	t1.erase(c);
	t1.forceAddCard(testC);
	t1.draw(c);
	//testC.draw (c);
	d1.draw (c);
	d1.delay(1000);
	d1.erase(c);
	d1.setCenter(0,0);
	d1.shuffle();
	d1.draw(c);
	d1.delay(1000);
	
	d1.erase(c);
	d1.setCenter(250,250);
	d1.shuffle();
	d1.draw(c);
	
    }
}
