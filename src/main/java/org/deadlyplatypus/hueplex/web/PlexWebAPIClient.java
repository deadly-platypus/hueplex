package org.deadlyplatypus.hueplex.web;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.deadlyplatypus.hueplex.web.pojo.PlexIdentity;

public class PlexWebAPIClient {
  public static final String ACCEPT_HEADER = "Accept";

  public static final String IDENTITY_PATH = "identity";

  protected WebTarget serverRoot;
  protected ObjectMapper mapper;

  public PlexWebAPIClient(String serverLocation, String username, String password) {
    Client client = ClientBuilder.newClient();
    client.property(ACCEPT_HEADER, MediaType.APPLICATION_JSON);
    this.serverRoot = client.target(serverLocation);
  }

  public PlexIdentity getServerIdentity() {
    PlexIdentity ident =
        (PlexIdentity) this.serverRoot.path(IDENTITY_PATH).request().buildGet()
            .invoke(PlexIdentity.class);

    return ident;
  }
}
