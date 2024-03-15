package cs3500.freecell.controller;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.view.FreecellTextView;

/**
 * Class implementing methods for the controller.
 * @param <Card> A playing card defined in the model.
 */
public class SimpleFreecellController<Card> implements FreecellController<Card> {
  private final FreecellModel<Card> model;
  private final Readable rd;
  private final Appendable ap;

  /**
   * Constructor for a SimpleFreecellController.
   * @param model Model for the game that holds the game state.
   * @param rd Readable object.
   * @param ap Appendable object.
   */
  public SimpleFreecellController(FreecellModel<Card> model, Readable rd, Appendable ap) {
    if (model == null || rd == null || ap == null) {
      throw new IllegalArgumentException("an argument is null!");
    }
    else {
      this.model = model;
      this.rd = rd;
      this.ap = ap;
    }
  }

  @Override
  public void playGame(List<Card> deck, int numCascades, int numOpens, boolean shuffle) {

    FreecellTextView view = new FreecellTextView(ap, model);

    if (deck == null) {
      throw new IllegalArgumentException("Inputted Deck is null!");
    }
    try {
      model.startGame(deck, numCascades, numOpens, shuffle);
    }
    catch (IllegalArgumentException e) {
      renderMessageHelper(view, "Could not start game.");
      return;
    }

    renderBoardHelper(view);
    Scanner scan = new Scanner(rd);

    //runs the game until it is over
    while (!model.isGameOver()) {

      validNext(scan);
      String sourcePile = scan.next();

      if (gameQuitter(sourcePile)) {
        renderMessageHelper(view,"Game quit prematurely.");
        return;
      }

      while (!validPile(sourcePile)) {
        renderMessageHelper(view, "Invalid source Pile. Input new source Pile: ");

        validNext(scan);
        sourcePile = scan.next();
      }
      
      validNext(scan);
      String strCardIndex = scan.next();

      if (gameQuitter(strCardIndex)) {
        renderMessageHelper(view,"Game quit prematurely.");
        return;
      }

      int cardIndex;
      if (validInt(strCardIndex)) {
        cardIndex = Integer.parseInt(strCardIndex);
      }
      else {
        cardIndex = -1;
      }

      //checking card index until valid card index is inputted.
      while (!correctCardIndex(cardIndex)) {
        renderMessageHelper(view, "Invalid source Card index. Input new Card index: ");

        validNext(scan);
        strCardIndex = scan.next();

        if (gameQuitter(strCardIndex)) {
          renderMessageHelper(view,"Game quit prematurely.");
          return;
        }

        if (validInt(strCardIndex)) {
          cardIndex = Integer.parseInt(strCardIndex);
        }
        else {
          cardIndex = -1;
        }
      }

      validNext(scan);
      String destPile = scan.next();

      while (!validPile(destPile)) {
        renderMessageHelper(view, "Invalid destination Pile. " +
                  "Input new destination Pile:");
        validNext(scan);
        destPile = scan.next();
      }

      PileType sourcePT = returnPileType(sourcePile.substring(0, 1));
      PileType destPT = returnPileType(destPile.substring(0, 1));
      int sourcePileIndex = Integer.parseInt(sourcePile.substring(1));
      int destPileIndex = Integer.parseInt(destPile.substring(1));

      //Trying the move method with all valid piletypes and index's.
      try {
        model.move(sourcePT, sourcePileIndex - 1, cardIndex - 1, destPT,
                destPileIndex - 1);
        view = new FreecellTextView(model);
        renderBoardHelper(view);
      }
      catch (IllegalArgumentException e) {
        renderMessageHelper(view, "Invalid Move. Try again");
      }
    }

    renderMessageHelper(view, "Game Over.");
  }

  private PileType returnPileType(String str) throws IllegalArgumentException {
    switch (str) {
      case "C":
        return PileType.CASCADE;
      case "O":
        return PileType.OPEN;
      case "F":
        return PileType.FOUNDATION;
      default:
        throw new IllegalArgumentException("Invalid Pile Type");
    }
  }


  private boolean validPile(String sourcePile) {
    int i;
    String pileType = sourcePile.substring(0, 1);
    int sourcePileIndex;
    if (validInt(sourcePile.substring(1))) {
      sourcePileIndex = Integer.parseInt(sourcePile.substring(1));
    }
    else {
      sourcePileIndex = -1;
    }

    return (pileType.equals("C") || pileType.equals("O") || pileType.equals("F"))
            && (sourcePileIndex > 0);
  }

  private boolean correctCardIndex(int cIndex) {
    return cIndex > 0;
  }

  private void renderBoardHelper(FreecellTextView view) {
    try {
      view.renderBoard();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void renderMessageHelper(FreecellTextView view, String str) {
    try {
      view.renderMessage(str);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  private boolean gameQuitter(String str) {
    return (str.equals("Q") || str.equals("q"));
  }

  private boolean validInt(String str) {
    try {
      Integer.parseInt(str);
      return true;
    }
    catch (NumberFormatException e) {
      return false;
    }
  }

  private void validNext(Scanner scan) {
    if (!scan.hasNext()) {
      throw new IllegalStateException("Invalid input!");
    }
  }


}
