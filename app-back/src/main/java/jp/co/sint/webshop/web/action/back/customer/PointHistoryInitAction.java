package jp.co.sint.webshop.web.action.back.customer;

import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.service.customer.CustomerPointInfo;
import jp.co.sint.webshop.service.customer.PointStatusAllSearchInfo;
import jp.co.sint.webshop.service.customer.PointStatusListSearchCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.customer.PointHistoryBean;
import jp.co.sint.webshop.web.bean.back.customer.PointHistoryBean.PointHistoryEdit;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1030140:ポイント履歴のアクションクラスです

 * 
 * @author System Integrator Corp.
 */
public class PointHistoryInitAction extends PointHistoryBaseAction {

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
    return true;
  }

  /**
   * アクションを実行します。

   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    PointHistoryBean bean = new PointHistoryBean();
    CustomerService cs = ServiceLocator.getCustomerService(getLoginInfo());

    String[] urlParam = getRequestParameter().getPathArgs();

    // 顧客ポイント関連情報表示
	//delete by os012 20111213 start
//    if (urlParam.length < 1) {
    
//      setNextUrl("/app/common/dashboard/init/");
//      setRequestBean(getBean());
//
//      return BackActionResult.RESULT_SUCCESS;
    	
//    }
    CustomerPointInfo info = cs.getCustomerPointInfo(urlParam[0]);
//    if (info == null || cs.isWithdrawed(urlParam[0])) {
//      setNextUrl("/app/common/dashboard/init/");
//      setRequestBean(getBean());
//
//      return BackActionResult.RESULT_SUCCESS;
//    }
  //delete by os012 20111213 end
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
	    bean.setUpdatedDatetime(info.getUpdatedDatetime());
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

    PointHistoryBean nextBean = bean;
    nextBean.getList().clear();

    if (pList != null) {
      // オーバーフローチェック

      prepareSearchWarnings(pList, SearchWarningType.OVERFLOW);

      nextBean.setPagerValue(PagerUtil.createValue(pList));

      List<PointStatusAllSearchInfo> pointList = pList.getRows();
      setPointHistoryList(nextBean, pointList);
    }
    // 登録部初期化
    new PointHistoryEdit();

    // ボタン表示設定

    BackLoginInfo login = getLoginInfo();
    bean.setUpdateMode(Permission.CUSTOMER_POINT_INVEST.isGranted(login));
    bean.setDeleteMode(Permission.CUSTOMER_POINT_DELETE.isGranted(login));

    // ショップのリスト取得
    UtilService service = ServiceLocator.getUtilService(getLoginInfo());
    bean.setShopList(service.getShopNamesDefaultAllShop(false, false));

    this.setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名

   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.PointHistoryInitAction.0");
  }

  /**
   * オペレーションコードの取得

   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103014003";
  }

}
