import org.junit.Assert;
import org.junit.Test;
import cs3500.freecell.model.Card;

/**
 * testing class for card.
 */
public class CardTest {
  Card cardA = new Card(1, "♣");
  Card card2 = new Card(2, "♣");
  Card cardJ = new Card(11, "♦");
  Card cardQ = new Card(12, "♦");
  Card cardK = new Card(13, "♥");

  @Test
  public void testToString() {
    Assert.assertEquals(cardA.toString(), "A♣");
    Assert.assertEquals(card2.toString(), "2♣");
    Assert.assertEquals(cardJ.toString(), "J♦");
    Assert.assertEquals(cardQ.toString(), "Q♦");
    Assert.assertEquals(cardK.toString(), "K♥");
  }
}
