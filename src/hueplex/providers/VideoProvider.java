package hueplex.providers;

import hueplex.ColorSample;
import hueplex.interfaces.LightStateProvider;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.philips.lighting.model.PHLightState;

/**
 * Analyzes a {@link Dimension} sized area of a video starting at the supplied {@link Point}.
 * 
 * Uses code from https://github.com/carstena/game-to-philips-hue
 * 
 * @author Derrick
 * 
 */
public abstract class VideoProvider implements LightStateProvider {

  protected File videoLocation;
  protected Point origin;
  protected Dimension dim;
  protected PHLightState currentLightStatus;

  protected VideoProvider(String videoLocation, Point origin, Dimension dim) throws IOException {
    this.videoLocation = new File(videoLocation);
    if (!this.videoLocation.exists()) {
      throw new FileNotFoundException(videoLocation + " does not exist.");
    } else if (!this.videoLocation.canRead()) {
      throw new IOException(videoLocation + " cannot be read.");
    } else if (this.videoLocation.isDirectory()) {
      throw new IOException(videoLocation + " is a directory.");
    }
    // TODO: Check if File is a video type

  }

  public PHLightState getCurrentLightState() {
    return currentLightStatus;
  }

  /**
   * Calculates the dominant {@link Color} and brightness for a given {@link BufferedImage}. This is
   * mainly taken from https://github.com/carstena/game-to-philips-hue
   * 
   * @param img
   * @return
   */
  protected ColorSample getDominantColor(BufferedImage img) {
    return null;
  }

  /**
   * Get the {@link PHLightState} of the video at the current offset
   * 
   * @param offset
   * @return
   */
  public abstract PHLightState getLightStateAtOffset(int offset);
}
