package org.deadlyplatypus.hueplex.web;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.deadlyplatypus.hueplex.web.pojo.PlexIdentity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlexWebAPIClient {
  private Logger logger = LoggerFactory.getLogger(getClass());

  public static final String ACCEPT_HEADER = "Accept";

  public static final String IDENTITY_PATH = "identity";

  protected WebTarget serverRoot;

  public PlexWebAPIClient(String serverLocation, String username, String password) {
    Client client = ClientBuilder.newClient();
    this.serverRoot = client.target(serverLocation);
    this.serverRoot.property(ACCEPT_HEADER, MediaType.APPLICATION_JSON);
  }

  public PlexIdentity getServerIdentity() {
    WebTarget get = this.serverRoot.path(IDENTITY_PATH);
    PlexIdentity ident =
        (PlexIdentity) get.request().header(ACCEPT_HEADER, MediaType.APPLICATION_JSON).buildGet()
            .invoke(PlexIdentity.class);

    return null;
  }
}
