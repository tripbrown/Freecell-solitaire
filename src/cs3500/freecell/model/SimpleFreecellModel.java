package cs3500.freecell.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * class implementing and writing methods for FreecellModel.
 */
public class SimpleFreecellModel implements FreecellModel<Card> {
  Random rand = new Random();
  List<Pile> cascadePiles;
  List<Pile> foundationPiles;
  List<Pile> openPiles;
  boolean gameStart;

  /**
   * constructor for SimpleFreecellModel.
   */
  public SimpleFreecellModel() {
    this.cascadePiles = new ArrayList<>();
    this.foundationPiles = new ArrayList<>();
    this.openPiles = new ArrayList<>();
    this.gameStart = false;
  }

  /**
   * creates a new deck of 52 cards, each with a value and a suit.
   * @return a List of 52 cards.
   */
  public List<Card> getDeck() {
    List<Card> cards = new ArrayList<Card>();
    List<Integer> values = new ArrayList<Integer>(Arrays.asList(
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13));
    List<String> suits = new ArrayList<String>(Arrays.asList("♣", "♦", "♥", "♠"));

    for (int i = 0; i < 4; i = i + 1) {
      for (int j = 0; j < 13; j = j + 1) {
        cards.add(new Card(values.get(j), suits.get(i)));
      }
    }
    return cards;
  }

  /**
   * checks if there are any duplicate cards in deck.
   * @param cards deck of 52 cards.
   * @return whether the deck had duplicate cards.
   */
  private boolean duplicateCards(List<Card> cards) {
    boolean flag = false;
    for (int i = 0; i < 52; i++) {
      for (int j = i + 1; j < 52; j++) {
        if (cards.get(i).getValue() == cards.get(j).getValue()
                && cards.get(i).getSuit().equals(cards.get(j).getSuit())) {
          flag = true;
          break;
        }
      }
    }
    return flag;
  }

  @Override
  public void startGame(List<Card> deck, int numCascadePiles, int numOpenPiles, boolean shuffle) {
    if (deck.size() != 52) {
      throw new IllegalArgumentException("deck is not 52 cards!");
    }

    else if (duplicateCards(deck)) {
      throw new IllegalArgumentException("duplicate cards in deck!");
    }

    else if (numCascadePiles < 4 || numOpenPiles < 1) {
      throw new IllegalArgumentException("invalid number of cascade and/or open piles!");
    }

    else {
      cascadePiles = new ArrayList<Pile>(numCascadePiles);
      openPiles = new ArrayList<Pile>(numOpenPiles);
      foundationPiles = new ArrayList<Pile>(4);

      for (int i = 0; i < numCascadePiles; i++) {
        cascadePiles.add(new Pile(PileType.CASCADE));
      }

      for (int i = 0; i < numOpenPiles; i++) {
        openPiles.add(new Pile(PileType.OPEN));
      }

      for (int i = 0; i < 4; i++) {
        foundationPiles.add(new Pile(PileType.FOUNDATION));
      }

      if (shuffle) {
        Collections.shuffle(deck);
      }

      int cardCounter = 0;
      for (int i = 0; i < 52 / numCascadePiles + 1; i++) {
        for (int j = 0; j < numCascadePiles; j++) {
          if (cardCounter < 52) {
            cascadePiles.get(j).addCard(deck.get(cardCounter));
            cardCounter++;
          } else {
            break;
          }
        }
      }
      gameStart = true;
    }
  }

  /**
   * moves a picked card to the wanted destination pile.
   * @param source         the type of the source pile see @link{PileType}
   * @param pileNumber     the pile number of the given type, starting at 0
   * @param cardIndex      the index of the card to be moved from the source
   *                       pile, starting at 0
   * @param destination    the type of the destination pile (see
   * @param destPileNumber the pile number of the given type, starting at 0
   * @throws IllegalArgumentException when not a valid move
   */
  @Override
  public void move(PileType source, int pileNumber, int cardIndex,
                   PileType destination, int destPileNumber)
          throws IllegalArgumentException {

    validPileIndex(source, pileNumber);
    Pile sourcePile = getPile(source).get(pileNumber);

    validCardIndex(sourcePile, cardIndex);
    Card sourceCard = sourcePile.returnCard(cardIndex);

    validPileIndex(source, pileNumber);
    Pile destPile = getPile(destination).get(destPileNumber);

    if ((sourcePile.cardsInPile.size() == cardIndex + 1)
            && validMove(source, destination, sourcePile, sourceCard, destPile)
            && gameStart) {
      sourcePile.removeCard(cardIndex);
      destPile.addCard(sourceCard);
    }
    else {
      throw new IllegalArgumentException("Not a valid move!");
    }
  }

  /**
   * determines if the user can move the source card to the destination pile they place it on.
   * @param source type of pile user is drawing card from
   * @param destination type of pile user is placing card on
   * @param sourceCard the card the user is drawing
   * @param destPile the pile user is putting source card on
   * @return if the move is valid
   */
  protected boolean validMove(PileType source, PileType destination, Pile sourcePile,
                            Card sourceCard, Pile destPile) {
    //Card lastDestCard = destPile.lastCard();
    switch (destination) {
      case CASCADE:
        return validCascadeMove(sourcePile, sourceCard, destPile);
      case OPEN:
        return validOpenMove(destPile);
      case FOUNDATION:
        return validFoundMove(sourceCard, destPile);
      default:
        throw new IllegalArgumentException("Invalid pile type");
    }
    /*
    return switch (destination) {
      case CASCADE -> validCascadeMove(sourcePile, sourceCard, destPile);
      case OPEN -> validOpenMove(destPile);
      case FOUNDATION -> validFoundMove(sourceCard, destPile);
    };
     */
  }

  /**
   * Checks if the source card can go above the last card in the destination cascade pile.
   * @param sourcePile pile user is picking card from.
   * @param sourceCard card user is moving.
   * @param destPile the destination pile.
   * @return if the move is possible.
   */
  private boolean validCascadeMove(Pile sourcePile, Card sourceCard, Pile destPile) {

    if (destPile.isEmpty()) {
      return true;
    }

    else {
      Card lastDestCard = destPile.lastCard();
      int sourceVal = sourceCard.getValue();
      String sourceSuit = sourceCard.getSuit();
      int destVal = lastDestCard.getValue();
      String destSuit = lastDestCard.getSuit();

      if (destSuit.equals("♣") || destSuit.equals("♠")) {
        return sourceVal == destVal - 1
                && (sourceSuit.equals("♦") || sourceSuit.equals("♥"));
      } else {
        return sourceVal == destVal - 1
                && (sourceSuit.equals("♣") || sourceSuit.equals("♠"));
      }
    }
  }

  /**
   * Checks if the source card can go above the destination open pile.
   * @param destPile the open pile that the user is trying to move card to.
   * @return if the open pile is empty.
   */
  private boolean validOpenMove(Pile destPile) {
    return destPile.isEmpty();
  }

  /**
   * Checks if the source card can go above the last card in the destination foundation pile.
   * @param sourceCard the card user is moving.
   * @param destPile the destination foundation deck.
   * @return if the lastDestCard is the same suit and one value under the sourceCard.
   */
  private boolean validFoundMove(Card sourceCard, Pile destPile) {
    if (destPile.isEmpty()) {
      return sourceCard.getValue() == 1;
    }
    else {
      Card lastDestCard = destPile.lastCard();
      return (sourceCard.getValue() == lastDestCard.getValue() + 1)
              && (sourceCard.getSuit().equals(lastDestCard.getSuit()));
    }
  }

  protected void validPileIndex(PileType source, int pileNumber) {
    try {
      Pile p = getPile(source).get(pileNumber);
    }
    catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Not a valid move!");
    }
  }

  protected void validCardIndex(Pile sourcePile, int cardIndex) {
    try {
      Card c = sourcePile.returnCard(cardIndex);
    }
    catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Not a valid move!");
    }
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

  @Override
  public boolean isGameOver() {
    return (getNumCardsInFoundationPile(0) == 13
            && getNumCardsInFoundationPile(1) == 13
            && getNumCardsInFoundationPile(2) == 13
            && getNumCardsInFoundationPile(3) == 13);
  }

  @Override
  public int getNumCardsInFoundationPile(int index) {
    if (index > 3 || index < 0) {
      throw new IllegalArgumentException("invalid index inputted!");
    }
    if (!gameStart) {
      throw new IllegalStateException("game hasn't started!");
    }

    else {
      return this.foundationPiles.get(index).cardsInPile.size();
    }
  }

  @Override
  public int getNumCascadePiles() {
    if (gameStart) {
      return cascadePiles.size();
    }
    else {
      return -1;
    }
  }

  @Override
  public int getNumCardsInCascadePile(int index) {
    if (!gameStart) {
      throw new IllegalStateException("game hasn't started!");
    }
    if (index > cascadePiles.size() - 1 || index < 0) {
      throw new IllegalArgumentException("invalid index inputted!");
    }

    else {
      Pile cPile = cascadePiles.get(index);
      return cPile.cardsInPile.size();
    }
  }

  @Override
  public int getNumCardsInOpenPile(int index) {
    if (!gameStart) {
      throw new IllegalStateException("game hasn't started!");
    }
    if (index > openPiles.size() - 1 || index < 0) {
      throw new IllegalArgumentException("invalid index inputted!");
    }
    else {
      Pile oPile = openPiles.get(index);
      return oPile.cardsInPile.size();
    }
  }

  @Override
  public int getNumOpenPiles() {
    if (gameStart) {
      return openPiles.size();
    }
    else {
      return -1;
    }
  }

  @Override
  public Card getFoundationCardAt(int pileIndex, int cardIndex) {
    if (pileIndex > 3) {
      throw new IllegalArgumentException("invalid pile index inputted!");
    }
    if (cardIndex > (getNumCardsInFoundationPile(pileIndex) - 1)) {
      throw new IllegalArgumentException("invalid card index inputted!");
    }
    if (!gameStart) {
      throw new IllegalStateException("game hasn't started!");
    }

    Pile fPile = foundationPiles.get(pileIndex);
    return fPile.cardsInPile.get(cardIndex);

  }

  @Override
  public Card getCascadeCardAt(int pileIndex, int cardIndex) {
    if (!gameStart) {
      throw new IllegalStateException("game hasn't started!");
    }
    if (pileIndex > cascadePiles.size() - 1
            || cardIndex > getNumCardsInCascadePile(pileIndex) - 1 ) {
      throw new IllegalArgumentException("invalid pile index inputted!");
    }

    Pile cPile = cascadePiles.get(pileIndex);
    return cPile.cardsInPile.get(cardIndex);

  }

  @Override
  public Card getOpenCardAt(int pileIndex) {
    if (!gameStart) {
      throw new IllegalStateException("game hasn't started!");
    }

    if (getNumCardsInOpenPile(pileIndex) == 0) {
      return null;
    }

    if (pileIndex > openPiles.size() - 1) {
      throw new IllegalArgumentException("invalid pile index inputted!");
    }

    else {
      Pile oPile = openPiles.get(pileIndex);
      return oPile.cardsInPile.get(0);
    }
  }
}
