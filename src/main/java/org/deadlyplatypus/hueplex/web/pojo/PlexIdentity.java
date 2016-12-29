package org.deadlyplatypus.hueplex.web.pojo;

/**
 * Represents one Plex Media Server
 * @author Derrick
 *
 */
public class PlexIdentity extends PlexResponse {
  protected String machineIdentifier;
  protected String version;

  public String getMachineIdentifier() {
    return machineIdentifier;
  }

  public void setMachineIdentifier(String machineIdentifier) {
    this.machineIdentifier = machineIdentifier;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder(super.toString() + Character.LINE_SEPARATOR);
    result.append("machineIdentifer: " + machineIdentifier + Character.LINE_SEPARATOR);
    result.append("version: " + version);
    return result.toString();
  }
}
