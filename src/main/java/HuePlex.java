import org.deadlyplatypus.hueplex.web.PlexWebAPIClient;
import org.deadlyplatypus.hueplex.web.pojo.PlexIdentity;

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
    PlexWebAPIClient client = new PlexWebAPIClient("http://hda.home.com:32400", null, null);
    PlexIdentity ident = client.getServerIdentity();
    System.out.println(ident.toString());
  }
}
