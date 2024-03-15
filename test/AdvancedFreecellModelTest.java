import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import cs3500.freecell.model.AdvancedFreecellModel;
import cs3500.freecell.model.Card;
import cs3500.freecell.model.PileType;
import cs3500.freecell.view.FreecellTextView;

import static org.junit.Assert.assertEquals;

/**
 * testing for AdvancedFreecellModel.
 */
public class AdvancedFreecellModelTest {
  AdvancedFreecellModel m = new AdvancedFreecellModel();
  List<Card> deck = m.getDeck();

  @Test
  public void testValidMultiMove() {
    m.startGame(deck, 13, 4, false);

    m.move(PileType.CASCADE, 11,  3, PileType.OPEN, 0);
    m.move(PileType.CASCADE, 10,  3, PileType.CASCADE, 11);
    m.move(PileType.CASCADE, 11,  2, PileType.CASCADE, 12);

    FreecellTextView tv = new FreecellTextView(m);
    String game = tv.toString();
    Assert.assertEquals(game, "F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: Q♠\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, A♦, A♥, A♠\n" +
            "C2: 2♣, 2♦, 2♥, 2♠\n" +
            "C3: 3♣, 3♦, 3♥, 3♠\n" +
            "C4: 4♣, 4♦, 4♥, 4♠\n" +
            "C5: 5♣, 5♦, 5♥, 5♠\n" +
            "C6: 6♣, 6♦, 6♥, 6♠\n" +
            "C7: 7♣, 7♦, 7♥, 7♠\n" +
            "C8: 8♣, 8♦, 8♥, 8♠\n" +
            "C9: 9♣, 9♦, 9♥, 9♠\n" +
            "C10: 10♣, 10♦, 10♥, 10♠\n" +
            "C11: J♣, J♦, J♥\n" +
            "C12: Q♣, Q♦\n" +
            "C13: K♣, K♦, K♥, K♠, Q♥, J♠");
  }

  //not valid move from cascade pile to cascade pile
  @Test (expected = IllegalArgumentException.class)
  public void testInvalidMultiMove() {
    m.startGame(deck, 13, 4, false);

    m.move(PileType.CASCADE, 11,  3, PileType.OPEN, 0);
    m.move(PileType.CASCADE, 10,  3, PileType.CASCADE, 11);
    m.move(PileType.CASCADE, 12,  3, PileType.OPEN, 1);
    m.move(PileType.CASCADE, 11,  2, PileType.CASCADE, 12);
  }

  //not valid list of cards to move
  @Test (expected = IllegalArgumentException.class)
  public void testInvalidMultiCards() {
    m.startGame(deck, 13, 4, false);
    m.move(PileType.CASCADE, 11,  1, PileType.CASCADE, 12);
  }

  //too many open piles used to make multi move
  @Test (expected = IllegalArgumentException.class)
  public void testInvalidMultiMoveOpens() {
    m.startGame(deck, 13, 4, false);

    m.move(PileType.CASCADE, 11,  3, PileType.OPEN, 0);
    m.move(PileType.CASCADE, 1,  3, PileType.OPEN, 1);
    m.move(PileType.CASCADE, 2,  3, PileType.OPEN, 2);
    m.move(PileType.CASCADE, 3,  3, PileType.OPEN, 3);
    m.move(PileType.CASCADE, 10,  3, PileType.CASCADE, 11);
    m.move(PileType.CASCADE, 11,  2, PileType.CASCADE, 12);
  }

  //too many open piles used, but valid move because open cascade pile
  @Test
  public void testValidMultiMoveOpens() {
    m.startGame(deck, 13, 4, false);

    m.move(PileType.CASCADE, 11,  3, PileType.OPEN, 0);
    m.move(PileType.CASCADE, 1,  3, PileType.OPEN, 1);
    m.move(PileType.CASCADE, 2,  3, PileType.OPEN, 2);
    m.move(PileType.CASCADE, 3,  3, PileType.OPEN, 3);

    m.move(PileType.CASCADE, 0,  3, PileType.FOUNDATION, 0);
    m.move(PileType.CASCADE, 0,  2, PileType.FOUNDATION, 1);
    m.move(PileType.CASCADE, 0,  1, PileType.FOUNDATION, 2);
    m.move(PileType.CASCADE, 0,  0, PileType.FOUNDATION, 3);

    m.move(PileType.CASCADE, 10,  3, PileType.CASCADE, 11);
    m.move(PileType.CASCADE, 11,  2, PileType.CASCADE, 12);

    FreecellTextView tv = new FreecellTextView(m);
    String game = tv.toString();
    Assert.assertEquals(game, "F1: A♠\n" +
            "F2: A♥\n" +
            "F3: A♦\n" +
            "F4: A♣\n" +
            "O1: Q♠\n" +
            "O2: 2♠\n" +
            "O3: 3♠\n" +
            "O4: 4♠\n" +
            "C1:\n" +
            "C2: 2♣, 2♦, 2♥\n" +
            "C3: 3♣, 3♦, 3♥\n" +
            "C4: 4♣, 4♦, 4♥\n" +
            "C5: 5♣, 5♦, 5♥, 5♠\n" +
            "C6: 6♣, 6♦, 6♥, 6♠\n" +
            "C7: 7♣, 7♦, 7♥, 7♠\n" +
            "C8: 8♣, 8♦, 8♥, 8♠\n" +
            "C9: 9♣, 9♦, 9♥, 9♠\n" +
            "C10: 10♣, 10♦, 10♥, 10♠\n" +
            "C11: J♣, J♦, J♥\n" +
            "C12: Q♣, Q♦\n" +
            "C13: K♣, K♦, K♥, K♠, Q♥, J♠");
  }

  //Invalid destination open pile
  @Test (expected = IllegalArgumentException.class)
  public void testInvalidMultiMoveOpenDestination() {
    m.startGame(deck, 13, 4, false);

    m.move(PileType.CASCADE, 11,  3, PileType.OPEN, 0);
    m.move(PileType.CASCADE, 10,  3, PileType.CASCADE, 11);
    m.move(PileType.CASCADE, 11,  2, PileType.OPEN, 1);
  }

  //Invalid destination foundation pile
  @Test (expected = IllegalArgumentException.class)
  public void testInvalidMultiMoveFoundDestination() {
    m.startGame(deck, 13, 4, false);

    m.move(PileType.CASCADE, 11,  3, PileType.OPEN, 0);
    m.move(PileType.CASCADE, 10,  3, PileType.CASCADE, 11);
    m.move(PileType.CASCADE, 11,  2, PileType.FOUNDATION, 1);
  }

  @Test
  public void testGetDeck() {

    Assert.assertEquals(deck.size(), 52, 0.1);

    Card card1 = new Card(1, "♣");
    Assert.assertEquals(deck.get(0).toString(), card1.toString());
    Card card2 = new Card(2, "♣");
    Assert.assertEquals(deck.get(1).toString(), card2.toString());
    Card card13 = new Card(1, "♦");
    Assert.assertEquals(deck.get(13).toString(), card13.toString());
    Card card26 = new Card(1, "♥");
    Assert.assertEquals(deck.get(26).toString(), card26.toString());

  }

  @Test
  public void testStartGame() {

    assertEquals(m.getNumCascadePiles(), -1);
    assertEquals(m.getNumOpenPiles(), -1);

    m.startGame(deck, 6, 4, false);
    FreecellTextView tv = new FreecellTextView(m);
    String game = tv.toString();
    assertEquals(m.getNumCascadePiles(), 6);
    assertEquals(m.getNumOpenPiles(), 4);
    Assert.assertEquals(game, "F1:\n" +
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

  @Test (expected = IllegalArgumentException.class)
  public void testStartGameCascadeFail() {
    m.startGame(deck, 2, 4, false);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testStartGameOpenFail() {
    m.startGame(deck, 6, 0, false);
  }

  @Test
  public void testStartGameShuffle() {
    //deck is not shuffled when inputted into start game and if shuffle was false, the deck
    // would be dealt as seen in testStartGame
    assertEquals(m.getNumCascadePiles(), -1);
    assertEquals(m.getNumOpenPiles(), -1);
    m.startGame(deck, 6, 4, true);

    Assert.assertNotEquals(m.getCascadeCardAt(0, 8),
            new Card(10, "♠"));
    Assert.assertNotEquals(m.getCascadeCardAt(1, 8),
            new Card(11, "♠"));
    Assert.assertNotEquals(m.getCascadeCardAt(2, 8),
            new Card(12, "♠"));
    Assert.assertNotEquals(m.getCascadeCardAt(3, 8),
            new Card(13, "♠"));
  }

  @Test
  public void testRestartStartGame() {
    assertEquals(m.getNumCascadePiles(), -1);
    assertEquals(m.getNumOpenPiles(), -1);

    m.startGame(deck, 6, 4, false);
    m.move(PileType.CASCADE, 0, 8, PileType.OPEN, 0);
    m.move(PileType.CASCADE, 1, 8, PileType.OPEN, 1);
    m.move(PileType.CASCADE, 2, 8, PileType.OPEN, 2);
    m.move(PileType.CASCADE, 3, 8, PileType.OPEN, 3);

    m.startGame(deck, 6, 4, false);
    FreecellTextView tv = new FreecellTextView(m);
    String game = tv.toString();
    assertEquals(m.getNumCascadePiles(), 6);
    assertEquals(m.getNumOpenPiles(), 4);
    Assert.assertEquals(game, "F1:\n" +
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


  @Test
  public void testMoveCascade2Open() {
    m.startGame(deck, 6, 4, false);
    assertEquals(m.getNumCardsInOpenPile(2), 0);
    //valid move: cascade card to open pile
    m.move(PileType.CASCADE, 1, 8, PileType.OPEN, 2);
    assertEquals(m.getNumCardsInOpenPile(2), 1);
    FreecellTextView tv = new FreecellTextView(m);
    String game = tv.toString();
    Assert.assertEquals(game, "F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2:\n" +
            "O3: J♠\n" +
            "O4:\n" +
            "C1: A♣, 7♣, K♣, 6♦, Q♦, 5♥, J♥, 4♠, 10♠\n" +
            "C2: 2♣, 8♣, A♦, 7♦, K♦, 6♥, Q♥, 5♠\n" +
            "C3: 3♣, 9♣, 2♦, 8♦, A♥, 7♥, K♥, 6♠, Q♠\n" +
            "C4: 4♣, 10♣, 3♦, 9♦, 2♥, 8♥, A♠, 7♠, K♠\n" +
            "C5: 5♣, J♣, 4♦, 10♦, 3♥, 9♥, 2♠, 8♠\n" +
            "C6: 6♣, Q♣, 5♦, J♦, 4♥, 10♥, 3♠, 9♠");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidMoveCascade2Open() {
    m.startGame(deck, 6, 4, false);
    m.move(PileType.CASCADE, 1, 8, PileType.OPEN, 2);
    m.move(PileType.CASCADE, 1, 7, PileType.OPEN, 2);
  }

  @Test
  public void testMoveCascade2Foundation() {
    Collections.reverse(deck);
    Card c1 = new Card(1, "♣");
    m.startGame(deck, 13, 4, false);
    Assert.assertEquals(m.getCascadeCardAt(12, 3).toString(), c1.toString());
    m.move(PileType.CASCADE, 12, 3, PileType.FOUNDATION, 2);
    Card c2 = new Card(1, "♦");
    Assert.assertEquals(m.getCascadeCardAt(12, 2).toString(), c2.toString());
    Assert.assertEquals(m.getFoundationCardAt(2, 0).toString(), c1.toString());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidMoveCascade2Foundation() {
    m.startGame(deck, 6, 4, false);
    m.move(PileType.CASCADE, 1, 8, PileType.FOUNDATION, 2);
  }

  @Test public void testMoveCascade2Cascade() {
    m.startGame(deck, 6, 4, false);
    m.move(PileType.CASCADE, 0, 8, PileType.OPEN, 0);
    m.move(PileType.CASCADE, 0, 7, PileType.OPEN, 1);
    m.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 2);

    Card c3 = new Card(11, "♥");
    Assert.assertEquals(m.getCascadeCardAt(2, 9).toString(), c3.toString());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidMoveCascade2Cascade() {
    m.startGame(deck, 6, 4, false);
    m.move(PileType.CASCADE, 1, 8, PileType.OPEN, 2);

    m.move(PileType.OPEN, 2, 0, PileType.CASCADE, 1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetCascadeCardArgumentFail() {
    m.startGame(deck, 6, 4, false);
    Card fail = m.getCascadeCardAt(8, 10);
  }

  @Test (expected = IllegalStateException.class)
  public void testGetCascadeCardStateFail() {
    Card fail = m.getCascadeCardAt(8, 10);
  }

  @Test
  public void testIsGameOver() {
    Collections.reverse(deck);
    m.startGame(deck, 13, 4, false);

    Assert.assertFalse(m.isGameOver());

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 13; j++) {
        m.move(PileType.CASCADE, 12 - j, 3 - i, PileType.FOUNDATION, i);
      }
    }
    FreecellTextView tv = new FreecellTextView(m);
    String game = tv.toString();
    Assert.assertEquals(game, "F1: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n" +
            "F2: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F3: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F4: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "O1:\n" + "O2:\n" + "O3:\n" + "O4:\n" + "C1:\n" + "C2:\n" + "C3:\n" +
            "C4:\n" + "C5:\n" + "C6:\n" + "C7:\n" + "C8:\n" + "C9:\n" + "C10:\n" +
            "C11:\n" + "C12:\n" + "C13:");
    Assert.assertTrue(m.isGameOver());
  }
}
