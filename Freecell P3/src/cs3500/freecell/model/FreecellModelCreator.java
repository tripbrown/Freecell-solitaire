package cs3500.freecell.model;

/**
 * Creates a Freecell game either allowing multi-card moves or not.
 */
public class FreecellModelCreator {
  /**
   * enum stating if game is either allowing multi-card moves or only single moves.
   */
  public enum GameType {
    SINGLEMOVE, MULTIMOVE;
  }

  /**
   * creates a Freecell game.
   * @param type the Game type.
   * @return the proper model based on the GameType.
   */
  public static FreecellModel<Card> create(GameType type) {
    switch (type) {
      case SINGLEMOVE:
        return new SimpleFreecellModel();
      case MULTIMOVE:
        return new AdvancedFreecellModel();
      default:
        throw new IllegalArgumentException("Invaid GameType inserted!");
    }
  }
}
