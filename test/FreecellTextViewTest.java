import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import cs3500.freecell.model.Card;
import cs3500.freecell.model.SimpleFreecellModel;
import cs3500.freecell.view.FreecellTextView;

/**
 * testing class for FreecellTextView.
 */
public class FreecellTextViewTest {
  @Test
  public void testToString() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    FreecellTextView tv = new FreecellTextView(model);
    Assert.assertEquals(tv.toString(), "");

    List<Card> deck = model.getDeck();
    model.startGame(deck, 6, 4, false);
    Assert.assertEquals(tv.toString(), "F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 7♣, K♣, 6♦, Q♦, 5♥, J♥, 4♠, 10♠\n" +
            "C2: 2♣, 8♣, A♦, 7♦, K♦, 6♥, Q♥, 5♠, J♠\n" +
            "C3: 3♣, 9♣, 2♦, 8♦, A♥, 7♥, K♥, 6♠, Q♠\n" +
            "C4: 4♣, 10♣, 3♦, 9♦, 2♥, 8♥, A♠, 7♠, K♠\n" +
            "C5: 5♣, J♣, 4♦, 10♦, 3♥, 9♥, 2♠, 8♠\n" +
            "C6: 6♣, Q♣, 5♦, J♦, 4♥, 10♥, 3♠, 9♠");
  }
}
