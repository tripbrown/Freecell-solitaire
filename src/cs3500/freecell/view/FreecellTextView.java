package cs3500.freecell.view;

import java.io.IOException;

import cs3500.freecell.model.FreecellModelState;

/**
 * implements FreecellView and toString.
 */
public class FreecellTextView implements FreecellView {
  private final FreecellModelState<?> model;
  private final Appendable ap;

  /**
   * constructor for FreecellTextView.
   * @param model state of the freecell game.
   */
  public FreecellTextView(FreecellModelState<?> model) {
    if (model == null) {
      throw new IllegalArgumentException("Model can't be null");
    }
    this.model = model;
    this.ap = System.out;
  }

  /**
   * constructor for FreecellTextView that takes in Appendable object the view uses as destination.
   * @param ap destination for view interface.
   * @param model FreecellModel.
   */
  public FreecellTextView(Appendable ap, FreecellModelState<?> model) {
    this.model = model;
    this.ap = ap;
  }

  @Override
  public String toString() {
    if (model.getNumOpenPiles() == -1) {
      return "";
    }
    else {
      return getString(model);
    }
  }

  private <T> String getString(FreecellModelState<T> m) {
    StringBuilder builder = new StringBuilder();

    //printing foundation cards
    for (int i = 0; i < 4; i++) {
      builder.append("F").append(i + 1).append(":");
      for (int j = 0; j < m.getNumCardsInFoundationPile(i);  j++) {
        if (m.getNumCardsInFoundationPile(i) == 0) {
          break;
        }
        else {
          T card = m.getFoundationCardAt(i, j);
          if (j == 0) {
            builder.append(" ").append(card.toString());
          }
          else {
            builder.append(", ").append(card.toString());
          }
        }
      }
      builder.append("\n");
    }

    //printing open cards
    for (int i = 0; i < m.getNumOpenPiles(); i++) {
      builder.append("O").append(i + 1).append(":");
      if (m.getNumCardsInOpenPile(i) == 0) {
        builder.append("\n");
      }
      else {
        T card = m.getOpenCardAt(i);
        builder.append(" ").append(card.toString());
        builder.append("\n");
      }
    }

    //printing cascade cards
    for (int i = 0; i < m.getNumCascadePiles(); i++) {
      builder.append("C").append(i + 1).append(":");
      for (int j = 0; j < m.getNumCardsInCascadePile(i); j++) {
        T card = m.getCascadeCardAt(i, j);
        if (j == 0) {
          builder.append(" ").append(card.toString());
        }
        else {
          builder.append(", ").append(card.toString());
        }
      }
      if (i < m.getNumCascadePiles() - 1) {
        builder.append("\n");
      }
    }
    return builder.toString();
  }

  @Override
  public void renderBoard() throws IOException {
    char[] chars = this.toString().toCharArray();
    for (char c: chars) {
      ap.append(c);
    }
    ap.append('\n');
    ap.append('\n');
  }

  @Override
  public void renderMessage(String message) throws IOException {
    char[] chars = message.toCharArray();
    for (char c: chars) {
      ap.append(c);
    }
    if (!message.equals("Could not start game.")) {
      ap.append('\n');
      ap.append('\n');
    }
  }
}
