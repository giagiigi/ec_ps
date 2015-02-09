package jp.co.sint.webshop.web.action.back.customer;

import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.service.customer.CustomerPointInfo;
import jp.co.sint.webshop.service.customer.PointStatusAllSearchInfo;
import jp.co.sint.webshop.service.customer.PointStatusListSearchCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.customer.PointHistoryBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1030140:ポイント履歴のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class PointHistorySearchAction extends PointHistoryBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    return Permission.CUSTOMER_POINT_READ.isGranted(login);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    PointHistoryBean bean = (PointHistoryBean) getBean();
    return validateBean(bean);
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    PointHistoryBean bean = (PointHistoryBean) getBean();
    CustomerService cs = ServiceLocator.getCustomerService(getLoginInfo());

    String[] urlParam = getRequestParameter().getPathArgs();
    // 存在チェック
    if (urlParam.length > 0) {
      if (cs.isNotFound(urlParam[0]) || cs.isWithdrawed(urlParam[0])) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.back.customer.PointHistorySearchAction.0"))); //$NON-NLS-1$
        return BackActionResult.RESULT_SUCCESS;
      }
    } else {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.customer.PointHistorySearchAction.0"))); //$NON-NLS-1$
      return BackActionResult.RESULT_SUCCESS;
    }

    CustomerPointInfo info = cs.getCustomerPointInfo(urlParam[0]);
    //modify by os012 20111213 start
    CustomerInfo customerinfo = cs.getCustomer(urlParam[0]);
    if (info != null)
    { 
	    bean.setCustomerCode(info.getCustomerCode());
	    bean.setEmail(info.getEmail());
	    bean.setLastName(info.getLastName());
	    bean.setFirstName(info.getFirstName());
	    bean.setLastNameKana(info.getLastNameKana());
	    bean.setFirstNameKana(info.getFirstNameKana());
	    bean.setPhoneNumber(info.getPhoneNumber());
	    //Add by V10-CH start
	    bean.setMobileNumber(info.getMobileNumber());
	    //Add by V10-CH end    
	    if (info.getRestPoint() != null) {
	      bean.setRestPoint(NumUtil.toString(info.getRestPoint()));
	    }
	    if (info.getTemporaryPoint() != null) {
	      bean.setTemporaryPoint(NumUtil.toString(info.getTemporaryPoint()));
	    }
	    if (info.getExpiredPointDate() != null) {
	      bean.setExpiredPointDate(DateUtil.toDateString(info.getExpiredPointDate()));
	    }
    } 
    else if(info==null && customerinfo!=null)
    {
    	bean.setCustomerCode(customerinfo.getCustomer().getCustomerCode());
 	    bean.setEmail(customerinfo.getCustomer().getEmail());
 	    bean.setLastName(customerinfo.getCustomer().getLastName());
 	    bean.setFirstName(customerinfo.getCustomer().getFirstName());
 	    bean.setLastNameKana(customerinfo.getCustomer().getLastNameKana());
 	    bean.setFirstNameKana(customerinfo.getCustomer().getFirstNameKana());
 	    if (customerinfo.getAddress()!=null)
 	    {
	 	    bean.setPhoneNumber(customerinfo.getAddress().getPhoneNumber());
	 	    //Add by V10-CH start
	 	    bean.setMobileNumber(customerinfo.getAddress().getMobileNumber());
	 	    
	 	    //Add by V10-CH end
 	    } 
 	      bean.setRestPoint(NumUtil.toString(0));  
 	      bean.setTemporaryPoint(NumUtil.toString(0));   
    }
  //modify by os012 20111213 end
    
    // 検索条件を取得
    PointStatusListSearchCondition condition = new PointStatusListSearchCondition();
    condition.setSearchCustomerCode(urlParam[0]);
    setSearchCondition(bean, condition);

    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);

    // ポイント履歴取得
    SearchResult<PointStatusAllSearchInfo> pList = cs.findPointStatusCustomerInfo(condition);

    bean.getList().clear();
    // 件数0件,オーバーフローチェック
    prepareSearchWarnings(pList, SearchWarningType.BOTH);

    bean.setPagerValue(PagerUtil.createValue(pList));
    List<PointStatusAllSearchInfo> pointList = pList.getRows();
    setPointHistoryList(bean, pointList);

    // ボタン表示設定
    BackLoginInfo login = getLoginInfo();
    bean.setUpdateMode(Permission.CUSTOMER_POINT_INVEST.isGranted(login));
    bean.setDeleteMode(Permission.CUSTOMER_POINT_DELETE.isGranted(login));

    this.setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.PointHistorySearchAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103014005";
  }

}
