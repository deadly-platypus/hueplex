package org.deadlyplatypus.hueplex.providers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.deadlyplatypus.hueplex.ColorSample;
import org.deadlyplatypus.hueplex.Constants;
import org.deadlyplatypus.hueplex.interfaces.LightStateProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
  protected Logger logger;

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
    this.logger = LoggerFactory.getLogger(this.getClass());
    this.origin = origin;
    this.dim = dim;

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
    // Keep track of how many times a hue in a given bin appears
    // in the image.
    // Hue values range [0 .. 360), so dividing by 10, we get 36
    // bins.
    int[] colorBins = new int[Constants.MAX_COLOR / 10];

    // The bin with the most colors. Initialize to -1 to prevent
    // accidentally
    // thinking the first bin holds the dominant color.
    int maxBin = -1;

    // Keep track of sum hue/saturation/value per hue bin, which
    // we'll use to
    // compute an average to for the dominant color.
    float[] sumHue = new float[Constants.MAX_COLOR / 10];
    float[] sumSat = new float[Constants.MAX_COLOR / 10];
    float[] sumVal = new float[Constants.MAX_COLOR / 10];
    float[] hsv = new float[3];

    for (int row = this.origin.y; row < this.origin.y + this.dim.height; row++) {
      for (int col = this.origin.x; col < this.origin.x + this.dim.width; col++) {

        Color c = new Color(img.getRGB(col, row));

        hsv = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);

        // We compute the dominant color by putting colors
        // in bins based on their hue.
        int bin = (int) Math.floor(hsv[0] / 10.0f);

        // Update the sum hue/saturation/value for this bin.
        sumHue[bin] = sumHue[bin] + hsv[0];
        sumSat[bin] = sumSat[bin] + hsv[1];
        sumVal[bin] = sumVal[bin] + hsv[2];

        // Increment the number of colors in this bin.
        colorBins[bin]++;

        // Keep track of the bin that holds the most colors.
        if (maxBin < 0 || colorBins[bin] > colorBins[maxBin]) {
          maxBin = bin;
        }
      }
    }

    // maxBin may never get updated if the image holds only transparent
    // and/or black/white pixels.
    ColorSample sample = new ColorSample();
    if (maxBin < 0) {
      sample.setRgb(Color.BLACK);
      return sample;
    }

    // Return a color with the average hue/saturation/value of
    // the bin with the most colors.
    hsv[0] = sumHue[maxBin] / colorBins[maxBin];
    hsv[1] = sumSat[maxBin] / colorBins[maxBin];
    hsv[2] = sumVal[maxBin] / colorBins[maxBin];

    int rgb = Color.HSBtoRGB(hsv[0], hsv[1], hsv[2]);
    Color rgbcolor = new Color(rgb);
    sample.setBrightness(calculateBrightness(rgbcolor));
    sample.setRgb(rgbcolor);

    return sample;
  }

  /**
   * @see http://alienryderflex.com/hsp.html
   * @param c
   * @return
   */
  protected static Integer calculateBrightness(Color c) {
    return (int) Math.sqrt(c.getRed() * c.getRed() * .241 + c.getGreen() * c.getGreen() * .691
        + c.getBlue() * c.getBlue() * .068);
  }

  /**
   * Get the {@link PHLightState} of the video at the supplied offset
   * 
   * @param offset
   * @return
   */
  public abstract PHLightState getLightStateAtOffset(long offset);

  public abstract long getVideoLength();
}
