import java.util.ArrayList;

public class CardPile {
	private ArrayList<Card> cards;
	
	CardPile()
	{
		cards = new ArrayList<Card>();
	}
	
	public Card get(int number)
	{
		return cards.get(number);
	}
	public void add(Card card)
	{
		cards.add(card);
	}
	
	public void remove(int number)
	{
		cards.remove(number);
	}
	
	public int size()
	{
		return cards.size();
	}
	
	public void clear()
	{
		cards.clear();
	}
}
