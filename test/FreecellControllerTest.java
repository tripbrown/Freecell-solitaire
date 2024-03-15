import org.junit.Assert;
import org.junit.Test;

import java.io.InputStreamReader;
import java.io.StringReader;

import cs3500.freecell.controller.SimpleFreecellController;
import cs3500.freecell.model.Card;
import cs3500.freecell.model.SimpleFreecellModel;

/**
 * JUnit test cases for the freecell controller.
 */
public class FreecellControllerTest {
  SimpleFreecellModel m = new SimpleFreecellModel();

  @Test
  public void testPlayGameFail() {
    Readable input = new StringReader("");
    Appendable output = new StringBuilder();
    SimpleFreecellController<Card> c = new SimpleFreecellController<Card>(m, input, output);

    c.playGame(m.getDeck(), 2, 4, false);

    Assert.assertEquals("Could not start game.", output.toString());
  }

  @Test
  public void testQuitGame() {
    Readable input = new StringReader("q");
    Appendable output = new StringBuilder();
    SimpleFreecellController<Card> c = new SimpleFreecellController<Card>(m, input, output);
    c.playGame(m.getDeck(), 14, 4, false);
    Assert.assertEquals("F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 2♦, 3♥, 4♠\n" +
            "C2: 2♣, 3♦, 4♥, 5♠\n" +
            "C3: 3♣, 4♦, 5♥, 6♠\n" +
            "C4: 4♣, 5♦, 6♥, 7♠\n" +
            "C5: 5♣, 6♦, 7♥, 8♠\n" +
            "C6: 6♣, 7♦, 8♥, 9♠\n" +
            "C7: 7♣, 8♦, 9♥, 10♠\n" +
            "C8: 8♣, 9♦, 10♥, J♠\n" +
            "C9: 9♣, 10♦, J♥\n" +
            "C10: 10♣, J♦, Q♥, K♠\n" +
            "C11: J♣, Q♦, K♥, Q♠\n" +
            "C12: Q♣, K♦, A♠\n" +
            "C13: K♣, A♥, 2♠\n" +
            "C14: A♦, 2♥, 3♠\n" +
            "Game quit prematurely.\n", output.toString());
  }


  @Test
  public void testMoveCascade2CascadeControl() {
    Readable input = new StringReader("C9 4 C11");
    Appendable output = new StringBuilder();
    SimpleFreecellController<Card> c = new SimpleFreecellController<Card>(m, input, output);
    c.playGame(m.getDeck(), 14, 4, false);

    Assert.assertEquals("F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 2♦, 3♥, 4♠\n" +
            "C2: 2♣, 3♦, 4♥, 5♠\n" +
            "C3: 3♣, 4♦, 5♥, 6♠\n" +
            "C4: 4♣, 5♦, 6♥, 7♠\n" +
            "C5: 5♣, 6♦, 7♥, 8♠\n" +
            "C6: 6♣, 7♦, 8♥, 9♠\n" +
            "C7: 7♣, 8♦, 9♥, 10♠\n" +
            "C8: 8♣, 9♦, 10♥, J♠\n" +
            "C9: 9♣, 10♦, J♥\n" +
            "C10: 10♣, J♦, Q♥, K♠\n" +
            "C11: J♣, Q♦, K♥, Q♠\n" +
            "C12: Q♣, K♦, A♠\n" +
            "C13: K♣, A♥, 2♠\n" +
            "C14: A♦, 2♥, 3♠", output.toString());
  }

  @Test
  public void testMoveCascade2OpenControl() {
    Readable input = new StringReader("C9 4 O1");
    Appendable output = new StringBuilder();
    SimpleFreecellController<Card> c = new SimpleFreecellController<Card>(m, input, output);
    c.playGame(m.getDeck(), 14, 4, false);

    Assert.assertEquals("F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: Q♠\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 2♦, 3♥, 4♠\n" +
            "C2: 2♣, 3♦, 4♥, 5♠\n" +
            "C3: 3♣, 4♦, 5♥, 6♠\n" +
            "C4: 4♣, 5♦, 6♥, 7♠\n" +
            "C5: 5♣, 6♦, 7♥, 8♠\n" +
            "C6: 6♣, 7♦, 8♥, 9♠\n" +
            "C7: 7♣, 8♦, 9♥, 10♠\n" +
            "C8: 8♣, 9♦, 10♥, J♠\n" +
            "C9: 9♣, 10♦, J♥\n" +
            "C10: 10♣, J♦, Q♥, K♠\n" +
            "C11: J♣, Q♦, K♥\n" +
            "C12: Q♣, K♦, A♠\n" +
            "C13: K♣, A♥, 2♠\n" +
            "C14: A♦, 2♥, 3♠", output.toString());
  }

  @Test
  public void testMoveCascade2FoundationControl() {
    Readable input = new StringReader("C12 3 F1");
    Appendable output = new StringBuilder();
    SimpleFreecellController<Card> c = new SimpleFreecellController<Card>(m, input, output);
    c.playGame(m.getDeck(), 14, 4, false);

    Assert.assertEquals("F1: A♠\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: \n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 2♦, 3♥, 4♠\n" +
            "C2: 2♣, 3♦, 4♥, 5♠\n" +
            "C3: 3♣, 4♦, 5♥, 6♠\n" +
            "C4: 4♣, 5♦, 6♥, 7♠\n" +
            "C5: 5♣, 6♦, 7♥, 8♠\n" +
            "C6: 6♣, 7♦, 8♥, 9♠\n" +
            "C7: 7♣, 8♦, 9♥, 10♠\n" +
            "C8: 8♣, 9♦, 10♥, J♠\n" +
            "C9: 9♣, 10♦, J♥, Q♠\n" +
            "C10: 10♣, J♦, Q♥, K♠\n" +
            "C11: J♣, Q♦, K♥\n" +
            "C12: Q♣, K♦\n" +
            "C13: K♣, A♥, 2♠\n" +
            "C14: A♦, 2♥, 3♠", output.toString());
  }

  @Test
  public void testInvalidMoveCascade2FoundationControl() {
    Readable input = new StringReader("C12 3 F1");
    Appendable output = new StringBuilder();
    SimpleFreecellController<Card> c = new SimpleFreecellController<Card>(m, input, output);
    c.playGame(m.getDeck(), 14, 4, false);

    Assert.assertEquals("F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: \n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 2♦, 3♥, 4♠\n" +
            "C2: 2♣, 3♦, 4♥, 5♠\n" +
            "C3: 3♣, 4♦, 5♥, 6♠\n" +
            "C4: 4♣, 5♦, 6♥, 7♠\n" +
            "C5: 5♣, 6♦, 7♥, 8♠\n" +
            "C6: 6♣, 7♦, 8♥, 9♠\n" +
            "C7: 7♣, 8♦, 9♥, 10♠\n" +
            "C8: 8♣, 9♦, 10♥, J♠\n" +
            "C9: 9♣, 10♦, J♥, Q♠\n" +
            "C10: 10♣, J♦, Q♥, K♠\n" +
            "C11: J♣, Q♦, K♥\n" +
            "C12: Q♣, K♦, A♠\n" +
            "C13: K♣, A♥, 2♠\n" +
            "C14: A♦, 2♥, 3♠\n" +
            "Invalid Move. Try again", output.toString());
  }

  @Test
  public void testInvalidMoveCascade2CascadeControl() {
    Readable input = new StringReader("C12 3 C10");
    Appendable output = new StringBuilder();
    SimpleFreecellController<Card> c = new SimpleFreecellController<Card>(m, input, output);
    c.playGame(m.getDeck(), 14, 4, false);

    Assert.assertEquals("F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: \n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 2♦, 3♥, 4♠\n" +
            "C2: 2♣, 3♦, 4♥, 5♠\n" +
            "C3: 3♣, 4♦, 5♥, 6♠\n" +
            "C4: 4♣, 5♦, 6♥, 7♠\n" +
            "C5: 5♣, 6♦, 7♥, 8♠\n" +
            "C6: 6♣, 7♦, 8♥, 9♠\n" +
            "C7: 7♣, 8♦, 9♥, 10♠\n" +
            "C8: 8♣, 9♦, 10♥, J♠\n" +
            "C9: 9♣, 10♦, J♥, Q♠\n" +
            "C10: 10♣, J♦, Q♥, K♠\n" +
            "C11: J♣, Q♦, K♥\n" +
            "C12: Q♣, K♦, A♠\n" +
            "C13: K♣, A♥, 2♠\n" +
            "C14: A♦, 2♥, 3♠\n" +
            "Invalid Move. Try again", output.toString());
  }

  @Test
  public void testInvalidSourcePileType() {
    Readable input = new StringReader("G12 3 O4");
    Appendable output = new StringBuilder();
    SimpleFreecellController<Card> c = new SimpleFreecellController<Card>(m, input, output);
    c.playGame(m.getDeck(), 14, 4, false);

    Assert.assertEquals("F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: \n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 2♦, 3♥, 4♠\n" +
            "C2: 2♣, 3♦, 4♥, 5♠\n" +
            "C3: 3♣, 4♦, 5♥, 6♠\n" +
            "C4: 4♣, 5♦, 6♥, 7♠\n" +
            "C5: 5♣, 6♦, 7♥, 8♠\n" +
            "C6: 6♣, 7♦, 8♥, 9♠\n" +
            "C7: 7♣, 8♦, 9♥, 10♠\n" +
            "C8: 8♣, 9♦, 10♥, J♠\n" +
            "C9: 9♣, 10♦, J♥, Q♠\n" +
            "C10: 10♣, J♦, Q♥, K♠\n" +
            "C11: J♣, Q♦, K♥\n" +
            "C12: Q♣, K♦, A♠\n" +
            "C13: K♣, A♥, 2♠\n" +
            "C14: A♦, 2♥, 3♠\n" +
            "Invalid source Pile type. Input new Pile type: ", output.toString());
  }

  @Test
  public void testInvalidSourcePileIndex() {
    Readable input = new StringReader("C-1 3 O1");
    Appendable output = new StringBuilder();
    SimpleFreecellController<Card> c = new SimpleFreecellController<Card>(m, input, output);
    c.playGame(m.getDeck(), 14, 4, false);

    Assert.assertEquals("F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: \n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 2♦, 3♥, 4♠\n" +
            "C2: 2♣, 3♦, 4♥, 5♠\n" +
            "C3: 3♣, 4♦, 5♥, 6♠\n" +
            "C4: 4♣, 5♦, 6♥, 7♠\n" +
            "C5: 5♣, 6♦, 7♥, 8♠\n" +
            "C6: 6♣, 7♦, 8♥, 9♠\n" +
            "C7: 7♣, 8♦, 9♥, 10♠\n" +
            "C8: 8♣, 9♦, 10♥, J♠\n" +
            "C9: 9♣, 10♦, J♥, Q♠\n" +
            "C10: 10♣, J♦, Q♥, K♠\n" +
            "C11: J♣, Q♦, K♥\n" +
            "C12: Q♣, K♦, A♠\n" +
            "C13: K♣, A♥, 2♠\n" +
            "C14: A♦, 2♥, 3♠\n" +
            "Invalid source Pile index. Input new index for pile type: C", output.toString());
  }

  @Test
  public void testInvalidCardIndex() {
    Readable input = new StringReader("C3 -1 C10");
    Appendable output = new StringBuilder();
    SimpleFreecellController<Card> c = new SimpleFreecellController<Card>(m, input, output);
    c.playGame(m.getDeck(), 14, 4, false);

    Assert.assertEquals("F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: \n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 2♦, 3♥, 4♠\n" +
            "C2: 2♣, 3♦, 4♥, 5♠\n" +
            "C3: 3♣, 4♦, 5♥, 6♠\n" +
            "C4: 4♣, 5♦, 6♥, 7♠\n" +
            "C5: 5♣, 6♦, 7♥, 8♠\n" +
            "C6: 6♣, 7♦, 8♥, 9♠\n" +
            "C7: 7♣, 8♦, 9♥, 10♠\n" +
            "C8: 8♣, 9♦, 10♥, J♠\n" +
            "C9: 9♣, 10♦, J♥, Q♠\n" +
            "C10: 10♣, J♦, Q♥, K♠\n" +
            "C11: J♣, Q♦, K♥\n" +
            "C12: Q♣, K♦, A♠\n" +
            "C13: K♣, A♥, 2♠\n" +
            "C14: A♦, 2♥, 3♠\n" +
            "Invalid source Card index. Input new Card index from Pile C3", output.toString());
  }

  @Test
  public void testInvalidDestPileType() {
    Readable input = new StringReader("C12 3 G10");
    Appendable output = new StringBuilder();
    SimpleFreecellController<Card> c = new SimpleFreecellController<Card>(m, input, output);
    c.playGame(m.getDeck(), 14, 4, false);

    Assert.assertEquals("F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: \n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 2♦, 3♥, 4♠\n" +
            "C2: 2♣, 3♦, 4♥, 5♠\n" +
            "C3: 3♣, 4♦, 5♥, 6♠\n" +
            "C4: 4♣, 5♦, 6♥, 7♠\n" +
            "C5: 5♣, 6♦, 7♥, 8♠\n" +
            "C6: 6♣, 7♦, 8♥, 9♠\n" +
            "C7: 7♣, 8♦, 9♥, 10♠\n" +
            "C8: 8♣, 9♦, 10♥, J♠\n" +
            "C9: 9♣, 10♦, J♥, Q♠\n" +
            "C10: 10♣, J♦, Q♥, K♠\n" +
            "C11: J♣, Q♦, K♥\n" +
            "C12: Q♣, K♦, A♠\n" +
            "C13: K♣, A♥, 2♠\n" +
            "C14: A♦, 2♥, 3♠\n" +
            "Invalid destination Pile type. Input new Pile type to move card at C12 3 to",
            output.toString());
  }

  @Test
  public void testInvalidDestPileIndex() {
    Readable input = new StringReader("C12 3 C-1");
    Appendable output = new StringBuilder();
    SimpleFreecellController<Card> c = new SimpleFreecellController<Card>(m, input, output);
    c.playGame(m.getDeck(), 14, 4, false);

    Assert.assertEquals("F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: \n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 2♦, 3♥, 4♠\n" +
            "C2: 2♣, 3♦, 4♥, 5♠\n" +
            "C3: 3♣, 4♦, 5♥, 6♠\n" +
            "C4: 4♣, 5♦, 6♥, 7♠\n" +
            "C5: 5♣, 6♦, 7♥, 8♠\n" +
            "C6: 6♣, 7♦, 8♥, 9♠\n" +
            "C7: 7♣, 8♦, 9♥, 10♠\n" +
            "C8: 8♣, 9♦, 10♥, J♠\n" +
            "C9: 9♣, 10♦, J♥, Q♠\n" +
            "C10: 10♣, J♦, Q♥, K♠\n" +
            "C11: J♣, Q♦, K♥\n" +
            "C12: Q♣, K♦, A♠\n" +
            "C13: K♣, A♥, 2♠\n" +
            "C14: A♦, 2♥, 3♠\n" +
            "Invalid source Pile index. Input new index for pile type C", output.toString());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    SimpleFreecellController<Card> c = new SimpleFreecellController<Card>(m,
            new InputStreamReader(System.in), null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullReadable() {
    SimpleFreecellController<Card> c = new SimpleFreecellController<Card>(m,
            null, System.out);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullModel() {
    SimpleFreecellController<Card> c = new SimpleFreecellController<Card>(null,
            new InputStreamReader(System.in), System.out);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullDeck() {
    Readable input = new StringReader("");
    Appendable output = new StringBuilder("");
    SimpleFreecellController<Card> c = new SimpleFreecellController<Card>(m, input, output);
    c.playGame(null, 14, 4, false);
  }

}
