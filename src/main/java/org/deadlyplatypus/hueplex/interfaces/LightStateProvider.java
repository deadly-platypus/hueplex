/**
 * 
 */
package org.deadlyplatypus.hueplex.interfaces;

import com.philips.lighting.model.PHLightState;


/**
 * @author Derrick
 * 
 *         Any media that can change a light must implement these methods. A LightStateProvider is
 *         intended to be some kind of media that can be displayed on the TV. This could be music,
 *         movies, TV shows, or whatever.
 */
public interface LightStateProvider {
  /**
   * @return the latest real-time computed {@link PHLightState}
   */
  PHLightState getCurrentLightState();

  /**
   * Change the current {@link PHLightState}
   */
  void updateLightState();
}
