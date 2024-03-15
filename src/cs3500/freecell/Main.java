package cs3500.freecell;

import java.io.InputStreamReader;

import cs3500.freecell.controller.SimpleFreecellController;
import cs3500.freecell.model.Card;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.FreecellModelCreator;

/**
 * Runs a full game of Freecell.
 */
public class Main {

  /**
   * Constructor for Freecell.
   * @param args String arguments.
   */
  public static void main(String[] args) {
    FreecellModelCreator gameCreator = new FreecellModelCreator();
    FreecellModel<Card> model = gameCreator.create(FreecellModelCreator.GameType.MULTIMOVE);
    SimpleFreecellController<Card> controller = new SimpleFreecellController<Card>(model,
            new InputStreamReader(System.in), System.out);
    try {
      controller.playGame(model.getDeck(), 8, 4, true);
    }
    catch (IllegalArgumentException e) {
      System.out.println("Something went wrong");
    }
  }
}
