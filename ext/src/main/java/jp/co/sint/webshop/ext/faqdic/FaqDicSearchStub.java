package jp.co.sint.webshop.ext.faqdic;

import java.util.List;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.utility.PostalAddress;
import jp.co.sint.webshop.utility.PostalSearch;

public class FaqDicSearchStub implements PostalSearch {

  private Logger logger = Logger.getLogger(this.getClass());

  public FaqDicSearchStub() {
  }

  public FaqDicSearchStub(String zipDicDir) {
  }

  public PostalAddress get(String postalCode) {
    return null;
  }

  public List<PostalAddress> getAll(String postalCode) {
    return null;
  }

  public boolean isEnabled() {
    logger.debug(this.getClass().getSimpleName() + "#isEnabled():Stub class always retuns false;");
    return false;
  }

}
