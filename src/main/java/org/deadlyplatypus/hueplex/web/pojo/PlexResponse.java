package org.deadlyplatypus.hueplex.web.pojo;

public abstract class PlexResponse {
  protected String _elementType;

  public String get_elementType() {
    return _elementType;
  }

  public void set_elementType(String _elementType) {
    this._elementType = _elementType;
  }

  @Override
  public String toString() {
    return "_elementType: " + _elementType;
  }
}
