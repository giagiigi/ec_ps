package jp.co.sint.webshop.web.action.back.service;

import jp.co.sint.webshop.data.dao.NewCouponHistoryDao;
import jp.co.sint.webshop.data.dto.NewCouponHistory;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.service.MemberInfoBean;
import jp.co.sint.webshop.web.text.back.Messages;

public class MemberInfoUpdateAction extends WebBackAction<MemberInfoBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().hasPermission(Permission.SERVICE_USER_DATA_READ);
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

    MemberInfoBean bean = getBean();

    String params[] = getRequestParameter().getPathArgs();
    if (params.length < 1) {
      setRequestBean(bean);
      return BackActionResult.SERVICE_ERROR;
    }

    NewCouponHistoryDao dao = DIContainer.getDao(NewCouponHistoryDao.class);
    
    NewCouponHistory item = dao.load(params[0]);
    item.setUseStatus(2L);
    
    dao.update(item);
    
    setNextUrl("/app/service/member_info/history/coupon");
    
    setRequestBean(bean);
    
    return BackActionResult.RESULT_SUCCESS;
  }

  // 20110708 shiseido add end

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
    return Messages.getString("web.action.back.service.MemberInfoHistoryAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "8109011004";
  }
}
