package jp.co.sint.webshop.mail;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.MailType;

public final class MailCompositionLocator {

  /**
   * default constructor
   */
  private MailCompositionLocator() {
  }

  /**
   * メールタイプから構造情報を取得する。
   * 
   * @param type
   * @return メールタイプの構造情報
   */
  public static List<MailComposition> fromMailType(MailType type) {
    List<MailComposition> compostionList = new ArrayList<MailComposition>();
    for (MailComposition c : MailComposition.values()) {
      if (c.getType() == type) {
        compostionList.add(c);
      }
    }
    return compostionList;
  }

}
