package jp.co.sint.webshop.web.bean.front;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.web.bean.UIMainBean;

/**
 * UIMainBeanを拡張した抽象クラスです。
 * 
 * @author System Integrator Corp.
 */
public abstract class UIFrontBean extends UIMainBean {

  private static final long serialVersionUID = 1L; // 10.1.4 K00149 追加

  /**
   * サブJSPを設定します。<br>
   * ヘッダ部で表示するカテゴリ情報の取得がデフォルトとして設定されています。<br>
   * ヘッダ部が不要な場合は本メソッドをオーバライドして、空メソッドを実装してください。
   */
  @Override
  public void setSubJspId() {
    addSubJspId("/common/header");
  }

  /**
   * ページ固有のパンくずリスト情報を取得します。
   * 
   * @return ページ固有のパンくずリスト情報
   */
  public List<CodeAttribute> getPageTopicPath() {
    return new ArrayList<CodeAttribute>();
  }
}
