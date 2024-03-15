import org.junit.Assert;
import org.junit.Test;

import cs3500.freecell.model.AdvancedFreecellModel;
import cs3500.freecell.model.Card;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.SimpleFreecellModel;

/**
 * tests for the FreecellModelCreator class.
 */
public class FreecellModelCreatorTest {
  FreecellModelCreator fmc = new FreecellModelCreator();

  @Test
  public void testCreate() {
    FreecellModel<Card> game1 = fmc.create(FreecellModelCreator.GameType.SINGLEMOVE);
    FreecellModel<Card> game2 = fmc.create(FreecellModelCreator.GameType.MULTIMOVE);

    Assert.assertTrue(game1 instanceof SimpleFreecellModel);
    Assert.assertTrue(game2 instanceof AdvancedFreecellModel);
  }
}
