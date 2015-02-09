package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.UserAccessLog;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.shop.UserAccessLogSearchCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.AccessLogBean;
import jp.co.sint.webshop.web.bean.back.shop.AccessLogBean.AccessLogDetail;
import jp.co.sint.webshop.web.log.back.AccessLogDiv;
import jp.co.sint.webshop.web.log.back.AccessLogUtil;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.shop.ShopErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1050940:管理側アクセスログのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class AccessLogSearchAction extends WebBackAction<AccessLogBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().isAdmin();
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean valid = false;

    AccessLogDetail detail = getBean().getSearchCondition();

    if (StringUtil.isNullOrEmpty(detail.getOperation()) || AccessLogDiv.isValid(detail.getOperation())) {
      valid = true;
    } else {
      addErrorMessage(WebMessage.get(ShopErrorMessage.CODE_FAILED,
          Messages.getString("web.action.back.shop.AccessLogSearchAction.0")));
      valid = false;
    }

    if (validateBean(detail)) {
      if (ValidatorUtil.moreThan(detail.getDatetimeFrom(), detail.getDatetimeTo())) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR));
        valid = false;
      }
    } else {
      valid = false;
    }

    return valid;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    AccessLogBean bean = getBean();

    UserAccessLogSearchCondition condition = new UserAccessLogSearchCondition();
    AccessLogDetail searchCondition = getBean().getSearchCondition();

    condition.setShopCode(searchCondition.getShopCode());
    if (getLoginInfo().isShop()) {
      condition.setShopCode(getLoginInfo().getShopCode());
    }
    condition.setLoginId(searchCondition.getLoginId());
    condition.setUserName(searchCondition.getUserName());
    condition.setDatetimeFrom(searchCondition.getDatetimeFrom());
    condition.setDatetimeTo(searchCondition.getDatetimeTo());
    condition.setOperationCode(searchCondition.getOperation());
    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);

    SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());
    SearchResult<UserAccessLog> logSearchResult = service.getUserAccessLog(condition);
    List<AccessLogDetail> detailList = new ArrayList<AccessLogDetail>();

    bean.setPagerValue(PagerUtil.createValue(logSearchResult));

    for (UserAccessLog log : logSearchResult.getRows()) {
      AccessLogDetail detail = new AccessLogDetail();
      detail.setShopCode(log.getShopCode());
      detail.setUserName(log.getUserName());
      detail.setOperation(AccessLogUtil.getActionName(log.getOperationCode()));
      detail.setDate(DateUtil.toDateTimeString(log.getAccessDatetime()));
      detail.setIpAddress(log.getIpAddress());

      detailList.add(detail);
    }

    bean.setSearchResult(detailList);

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.AccessLogSearchAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105094002";
  }

}
