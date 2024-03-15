import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.freecell.model.Card;
import cs3500.freecell.model.Pile;
import cs3500.freecell.model.PileType;

/**
 * testing class for Pile.
 */
public class PileTest {

  Pile p1 = new Pile(PileType.CASCADE);
  Card cardA = new Card(1, "♣");
  Card card2 = new Card(2, "♣");

  @Test
  public void testAddCard() {
    p1.addCard(cardA);
    Assert.assertEquals(p1.returnCard(0).toString(), cardA.toString());
    p1.addCard(card2);
    Assert.assertEquals(p1.returnCard(1).toString(), card2.toString());
  }

  @Test
  public void testAddCards() {
    List<Card> addedCards = new ArrayList<>();
    addedCards.add(cardA);
    addedCards.add(card2);

    p1.addCards(addedCards);
    Assert.assertEquals(p1.returnCard(0).toString(), cardA.toString());
    Assert.assertEquals(p1.returnCard(1).toString(), card2.toString());
  }

  @Test
  public void testReturnCard() {
    p1.addCard(cardA);
    p1.addCard(card2);
    Card exCard = p1.returnCard(1);
    Assert.assertEquals(exCard.toString(), card2.toString());
  }

  @Test
  public void testRemoveCard() {
    p1.addCard(cardA);
    p1.addCard(card2);
    Assert.assertEquals(p1.returnCard(0).toString(), cardA.toString());
    p1.removeCard(0);
    Assert.assertEquals(p1.returnCard(0).toString(), card2.toString());
  }

  @Test
  public void testRemoveCards() {
    p1.addCard(cardA);
    p1.addCard(card2);
    Assert.assertFalse(p1.isEmpty());

    List<Card> removeCards = new ArrayList<>();
    removeCards.add(cardA);
    removeCards.add(card2);

    p1.removeCards(removeCards);
    Assert.assertTrue(p1.isEmpty());
  }

  @Test
  public void testLastCard() {
    p1.addCard(cardA);
    p1.addCard(card2);
    Card c = p1.lastCard();
    Assert.assertEquals(c.toString(), card2.toString());
  }

  @Test
  public void testIsEmpty() {
    Assert.assertTrue(p1.isEmpty());
    p1.addCard(cardA);
    Assert.assertFalse(p1.isEmpty());
  }
}
