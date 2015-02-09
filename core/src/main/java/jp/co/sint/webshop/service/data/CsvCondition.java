package jp.co.sint.webshop.service.data;

import java.io.Serializable;

import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.service.LoginInfo;

public interface CsvCondition<S extends CsvSchema> extends Serializable {

  S getSchema();

  void setSchema(S schema);

  /**
   * ヘッダの有無を返します。
   * 
   * @return ヘッダありの場合はtrue。デフォルトはtrue。
   */
  boolean hasHeader();

  LoginInfo getLoginInfo();

  void setLoginInfo(LoginInfo loginInfo);
}
