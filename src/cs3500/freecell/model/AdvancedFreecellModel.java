package cs3500.freecell.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Modified Freecell Model with multi card moves.
 */
public class AdvancedFreecellModel extends SimpleFreecellModel {

  @Override
  public void move(PileType source, int pileNumber, int cardIndex,
                   PileType destination, int destPileNumber) {
    super.validPileIndex(source, pileNumber);
    Pile sourcePile = getPile(source).get(pileNumber);

    super.validPileIndex(destination, destPileNumber);
    Pile destPile = getPile(destination).get(destPileNumber);

    super.validCardIndex(sourcePile, cardIndex);
    Card sourceCard = sourcePile.returnCard(cardIndex);

    //Multi-Move check and move
    if ((sourcePile.cardsInPile.size() > cardIndex + 1)
            && destination.equals(PileType.CASCADE)
            && gameStart) {
      List<Card> sourceCards = new ArrayList<Card>();

      for (int i = 0; i < (sourcePile.cardsInPile.size() - (cardIndex)); i++) {
        Card c = sourcePile.returnCard(cardIndex + i);
        sourceCards.add(c);
      }

      if (validMultiMove(source, destination, sourcePile, sourceCards, destPile)) {
        sourcePile.removeCards(sourceCards);
        destPile.addCards(sourceCards);
      }
      else {
        throw new IllegalArgumentException("Not a valid multi-move!");
      }
    }

    //Single Move check and move
    else if ((sourcePile.cardsInPile.size() == cardIndex + 1)
            && super.validMove(source, destination, sourcePile, sourceCard,
            destPile)
            && gameStart) {
      sourcePile.removeCard(cardIndex);
      destPile.addCard(sourceCard);
    }
    else {
      throw new IllegalArgumentException("Not a valid move!");
    }
  }


  /**
   * Checks if multi move is valid or not.
   * @param source the source PileType
   * @param dest the destination PileType
   * @param sPile the source Pile
   * @param sCards the list of cards being moved
   * @param dPile the destination pile
   * @return whether the move is valid or not.
   */
  private boolean validMultiMove(PileType source, PileType dest, Pile sPile,
                                 List<Card> sCards, Pile dPile) {
    if (!validCardList(sCards)) {
      return false;
    }
    else if (!validFreeSpaces(sCards.size())) {
      return false;
    }
    else {
      return super.validMove(source, dest, sPile, sCards.get(0), dPile);
    }
  }

  private boolean validCardList(List<Card> cards) {
    boolean flag = true;
    for (int i = 0; i < cards.size() - 1; i++) {
      Card c1 = cards.get(i);
      Card c2 = cards.get(i + 1);

      if ((c1.getValue() != c2.getValue() + 1)
              || !orderedSuit(c1, c2)) {
        flag = false;
      }
    }
    return flag;
  }

  private boolean orderedSuit(Card c1, Card c2) {
    String suit1 = c1.getSuit();
    String suit2 = c2.getSuit();

    if (suit1.equals("♣") || suit1.equals("♠")) {
      return (suit2.equals("♦") || suit2.equals("♥"));
    } else {
      return (suit2.equals("♣") || suit2.equals("♠"));
    }
  }

  private boolean validFreeSpaces(int sCardSize) {
    return sCardSize <= ((getNumFreeOpenPiles() + 1) * Math.pow(2, getNumFreeCascadePiles()));
  }

  private int getNumFreeOpenPiles() {
    int n = 0;
    for (int i = 0; i < getNumOpenPiles(); i++) {
      if (getNumCardsInOpenPile(i) == 0) {
        n = n + 1;
      }
    }
    return n;
  }

  private int getNumFreeCascadePiles() {
    int k = 0;
    for (int i = 0; i < getNumCascadePiles(); i++) {
      if (getNumCardsInCascadePile(i) == 0) {
        k = k + 1;
      }
    }
    return k;
  }


  /**
   * returns the SimpleFreecellModel field (Pile) associated with the given Pile type.
   * @param source the Pile type wanted to be returned.
   * @return the pile in the game state.
   */

  private List<Pile> getPile(PileType source) {
    switch (source) {
      case CASCADE:
        return cascadePiles;
      case OPEN:
        return openPiles;
      case FOUNDATION:
        return foundationPiles;
      default:
        throw new IllegalArgumentException("Invalid pile type");
    }
  }


}
