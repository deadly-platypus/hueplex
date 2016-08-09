/**
 * 
 */
package org.deadlyplatypus.hueplex.providers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.awt.Color;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Derrick
 * 
 */
public class VideoProviderTest {

  /**
   * Test method for
   * {@link org.deadlyplatypus.hueplex.providers.VideoProvider#getDominantColor(java.awt.image.BufferedImage)}
   * .
   */
  @Test
  @Ignore
  public void testGetDominantColor() {
    fail("Not yet implemented");
  }

  /**
   * Test method for
   * {@link org.deadlyplatypus.hueplex.providers.VideoProvider#calculateBrightness(java.awt.Color)}.
   */
  @Test
  public void testCalculateBrightness() {
    assertEquals(new Integer(0), VideoProvider.calculateBrightness(Color.BLACK));
  }

  /**
   * Test method for
   * {@link org.deadlyplatypus.hueplex.providers.VideoProvider#getLightStateAtOffset(int)}.
   */
  @Test
  @Ignore
  public void testGetLightStateAtOffset() {
    fail("Not yet implemented");
  }

}
