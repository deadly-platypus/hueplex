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
      provider = new M4VProvider(args[0], new Point(0, 0), new Dimension(100, 100));
      PHLightState sample = provider.getLightStateAtOffset(0);
      System.out.println("Hue: " + sample.getHue());
      System.out.println("Sat: " + sample.getSaturation());
      System.out.println("Bri: " + sample.getBrightness());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
