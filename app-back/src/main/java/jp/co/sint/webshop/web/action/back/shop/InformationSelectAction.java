package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.InformationBean;
import jp.co.sint.webshop.web.bean.back.shop.InformationBean.InformationDetail;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1051210:お知らせ編集のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class InformationSelectAction extends WebBackAction<InformationBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    InformationBean bean = getBean();
    InformationDetail detailBean = bean.getListInformation().get(Integer.valueOf(bean.getSelectInformationDetail()));
    bean.setRegisterInformation(detailBean);
    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.InformationSelectAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105121004";
  }

}
