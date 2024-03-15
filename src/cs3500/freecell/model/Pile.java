package cs3500.freecell.model;

import java.util.ArrayList;
import java.util.List;

/**
 * class for Pile of cards.
 */
public class Pile {

  PileType type;
  List<Card> cardsInPile;

  public Pile(PileType type) {
    this.type = type;
    this.cardsInPile = new ArrayList<Card>();
  }

  public void addCard(Card card) {
    cardsInPile.add(card);
  }

  public void addCards(List<Card> cards) {
    cardsInPile.addAll(cards);
  }

  public Card returnCard(int index) {
    return cardsInPile.get(index);
  }

  public void removeCard(int index) {
    cardsInPile.remove(index);
  }

  public void removeCards(List<Card> cards) {
    cardsInPile.removeAll(cards);
  }

  public Card lastCard() {
    int size = cardsInPile.size();
    return cardsInPile.get(size - 1);
  }

  public boolean isEmpty() {
    return cardsInPile.size() == 0;
  }
}
