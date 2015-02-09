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
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1030140:ポイント履歴のアクションクラスです

 * 
 * @author System Integrator Corp.
 */
public class PointHistoryCompleteAction extends PointHistoryBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。

   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    // 参照権限チェック
    BackLoginInfo login = getLoginInfo();
    return Permission.CUSTOMER_POINT_READ.isGranted(login);
  }

  /**
   * アクションを実行します。

   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CustomerService cs = ServiceLocator.getCustomerService(getLoginInfo());

    // 検索条件を取得

    PointHistoryBean bean = (PointHistoryBean) getBean();

    PointStatusListSearchCondition condition = new PointStatusListSearchCondition();

    String[] urlParam = getRequestParameter().getPathArgs();
    // 存在チェック
    if (urlParam.length > 0) {
      if (cs.isNotFound(urlParam[0]) || cs.isWithdrawed(urlParam[0])) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.back.customer.PointHistoryCompleteAction.0")));
        return BackActionResult.RESULT_SUCCESS;
      }
    } else {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.customer.PointHistoryCompleteAction.0")));
      return BackActionResult.RESULT_SUCCESS;
    }

    // 検索条件を取得

    condition.setSearchCustomerCode(urlParam[0]);
    setSearchCondition(bean, condition);

    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);

    // 検索結果リストを取得
    SearchResult<PointStatusAllSearchInfo> pList = cs.findPointStatusCustomerInfo(condition);

    // 入力された検索条件を保持
    PointHistoryBean nextBean = bean;
    nextBean.getList().clear();

    // ページ情報を追加
    nextBean.setPagerValue(PagerUtil.createValue(pList));

    // 結果一覧を作成
    List<PointStatusAllSearchInfo> pointList = pList.getRows();
    setPointHistoryList(nextBean, pointList);

    // 顧客ポイント関連情報表示

    CustomerPointInfo info = cs.getCustomerPointInfo(urlParam[0]);
  //modify by os012 20111213 start
    CustomerInfo customerinfo = cs.getCustomer(urlParam[0]);
    if(info!=null)
    {
	    nextBean.setCustomerCode(info.getCustomerCode());
	    nextBean.setEmail(info.getEmail());
	    nextBean.setLastName(info.getLastName());
	    nextBean.setFirstName(info.getFirstName());
	    nextBean.setLastNameKana(info.getLastNameKana());
	    nextBean.setFirstNameKana(info.getFirstNameKana());
	    nextBean.setPhoneNumber(info.getPhoneNumber());
	    //Add by V10-CH start
	    nextBean.setMobileNumber(info.getMobileNumber());
	    //Add by V10-CH end 
	      if (info.getRestPoint() != null) {
            nextBean.setRestPoint(NumUtil.toString(info.getRestPoint()));
	      }
	      if (info.getTemporaryPoint() != null) {
	        nextBean.setTemporaryPoint(NumUtil.toString(info.getTemporaryPoint()));
	      }
	      if (info.getExpiredPointDate() != null) {
	        nextBean.setExpiredPointDate(DateUtil.toDateString(info.getExpiredPointDate()));
	      }
	      nextBean.setUpdatedDatetime(info.getUpdatedDatetime());
    }
    else if(info==null && customerinfo!=null)
    {
    	nextBean.setCustomerCode(customerinfo.getCustomer().getCustomerCode());
    	nextBean.setEmail(customerinfo.getCustomer().getEmail());
    	nextBean.setLastName(customerinfo.getCustomer().getLastName());
    	nextBean.setFirstName(customerinfo.getCustomer().getFirstName());
    	nextBean.setLastNameKana(customerinfo.getCustomer().getLastNameKana());
    	nextBean.setFirstNameKana(customerinfo.getCustomer().getFirstNameKana());
 	    if (customerinfo.getAddress()!=null)
 	    {
 	    	nextBean.setPhoneNumber(customerinfo.getAddress().getPhoneNumber());
	 	    //Add by V10-CH start
	 	   nextBean.setMobileNumber(customerinfo.getAddress().getMobileNumber());
	 	    
	 	    //Add by V10-CH end
 	    } 
 	   nextBean.setRestPoint(NumUtil.toString(0));  
 	   nextBean.setTemporaryPoint(NumUtil.toString(0));   
 	   nextBean.setUpdatedDatetime(customerinfo.getCustomer().getUpdatedDatetime());
    }
    
  
    if (urlParam.length > 1) {
      if (urlParam[1].equals("delete")) {
        addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE,
            Messages.getString("web.action.back.customer.PointHistoryCompleteAction.1")));
      } else if (urlParam[1].equals("register")) {
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE,
            Messages.getString("web.action.back.customer.PointHistoryCompleteAction.2")));
      }
    }


    // ボタン表示設定

    BackLoginInfo login = getLoginInfo();
    nextBean.setUpdateMode(Permission.CUSTOMER_POINT_INVEST.isGranted(login));
    nextBean.setDeleteMode(Permission.CUSTOMER_POINT_DELETE.isGranted(login));

    // ポイント登録部初期化
    nextBean.getEdit().setDescription("");
    nextBean.getEdit().setIssuedPoint("");

    setRequestBean(nextBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない

   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  /**
   * Action名の取得
   * 
   * @return Action名

   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.PointHistoryCompleteAction.3");
  }

  /**
   * オペレーションコードの取得

   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103014001";
  }

}
