import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;

import org.deadlyplatypus.hueplex.providers.M4VProvider;
import org.deadlyplatypus.hueplex.providers.VideoProvider;

import com.philips.lighting.model.PHLightState;

/**
 * 
 */

/**
 * @author Derrick
 * 
 */
public class HuePlex {

  /**
   * @param args
   */
  public static void main(String[] args) {
    VideoProvider provider;
    try {
      provider =
          new M4VProvider("C:\\Users\\Derrick\\Videos\\Wall-e-1.m4v", new Point(0, 0),
              new Dimension(100, 100));
      PHLightState sample = provider.getLightStateAtOffset(0);
      System.out.println("Hue: " + sample.getHue());
      System.out.println("Sat: " + sample.getSaturation());
      System.out.println("Bri: " + sample.getBrightness());
      System.exit(0);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}
