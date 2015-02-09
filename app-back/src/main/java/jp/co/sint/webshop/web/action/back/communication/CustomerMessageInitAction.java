package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.CustomerMessageBean;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1061210:限时限量折扣一览初期表示处理
 * 
 * @author KS.
 */
public class CustomerMessageInitAction extends WebBackAction<CustomerMessageBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.MESSAGE_READ_SHOP.isGranted(getLoginInfo());
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

  public void init() {
    CustomerMessageBean bean = new CustomerMessageBean();
    setBean(bean);
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CustomerMessageBean bean = getBean();

    // 新建按钮显示设定
    // commented by zhangfeng 2014/3/14
    // bean.setUpdateAuthorizeFlg(Permission.DISCOUNT_UPDATE_SHOP.isGranted(getLoginInfo()));

    // 删除按钮显示设定
    bean.setDeleteAuthorizeFlg(Permission.MESSAGE_DELETE_SHOP.isGranted(getLoginInfo()));

    // 活动进行状态初期设定
    // bean.setSearchDiscountStatus(ActivityStatus.IN_PROGRESS.getValue());

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.bean.back.communication.CustomerMessageInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106121001";
  }
}
