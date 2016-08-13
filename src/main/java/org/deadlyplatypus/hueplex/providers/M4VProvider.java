package org.deadlyplatypus.hueplex.providers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.deadlyplatypus.hueplex.ColorSample;
import org.deadlyplatypus.hueplex.Constants;

import com.philips.lighting.model.PHLight.PHLightColorMode;
import com.philips.lighting.model.PHLightState;

public class M4VProvider extends VideoProvider {
  protected FFmpegFrameGrabber grabber;
  protected Java2DFrameConverter converter;

  public M4VProvider(String videoLocation, Point origin, Dimension dim) throws IOException {
    super(videoLocation, origin, dim);
    if (!videoLocation.endsWith("m4v")) {
      throw new IOException("Invalid video format");
    }
    this.grabber = new FFmpegFrameGrabber(this.videoLocation);
    this.converter = new Java2DFrameConverter();
  }

  public void updateLightState() {
    // TODO Auto-generated method stub

  }

  @Override
  public PHLightState getLightStateAtOffset(long offset) {
    try {
      this.grabber.start();
      int frameNumber = calculateFrameNumber(offset);
      this.grabber.setFrameNumber(frameNumber);
      BufferedImage img = this.converter.convert(this.grabber.grab());
      ColorSample sample = super.getDominantColor(img);
      PHLightState result = new PHLightState();
      result.setBrightness(sample.getBrightness());
      result.setColorMode(PHLightColorMode.COLORMODE_HUE_SATURATION);
      float[] hs =
          Color.RGBtoHSB(sample.getRgb().getRed(), sample.getRgb().getGreen(), sample.getRgb()
              .getBlue(), null);
      result.setHue((int) Math.ceil(hs[0] * Constants.MAX_HUE));
      result.setSaturation((int) Math.ceil(hs[1] * Constants.MAX_SAT));
      result.setTransitionTime(Constants.TRANSITION_TIME);
      return result;
    } catch (Exception e) {
      this.logger.error(String.format("Could not calculate offset %d for video %s: %s", offset,
          this.videoLocation.getAbsolutePath(), e.getMessage()));

      return null;
    } finally {
      try {
        this.grabber.stop();
      } catch (Exception e) {
        this.logger.error("Error stopping frame grabber: " + e.getMessage());
      }
    }
  }

  /**
   * Calculates the frame number of an offset in milliseconds
   * 
   * @param offset
   * @return
   */
  protected int calculateFrameNumber(long offset) {
    return (int) (Math.floor(offset / 1000l * this.grabber.getFrameRate()));
  }

  @Override
  public long getVideoLength() {
    return this.grabber.getLengthInTime();
  }
}
