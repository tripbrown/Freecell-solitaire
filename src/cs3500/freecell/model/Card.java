package cs3500.freecell.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class defining a playing Card.
 */
public final class Card {
  private final int value;
  private final String suit;

  /**
   * constructor for Card.
   * @param value value of the card. Between 1 and 13.
   * @param suit suit of the card. either spade, diamond, club, or heart.
   */
  public Card(int value, String suit) {
    if (0 < value && value < 14
            && (suit.equals("♠") || suit.equals("♦") || suit.equals("♣") || suit.equals("♥"))) {
      this.value = value;
      this.suit = suit;
    }

    else {
      throw new IllegalArgumentException("Invalid value or suit inputted!");
    }
  }

  @Override
  public String toString() {
    List<String> vals = new ArrayList<String>(Arrays.asList(
            "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"));
    String strVal = "";
    for (int i = 1; i < 14; i++) {
      if (value == i) {
        strVal = vals.get(i - 1);
      }
    }
    return strVal + suit;
  }

  public int getValue() {
    return value;
  }

  public String getSuit() {
    return suit;
  }
}
