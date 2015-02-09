package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.AddressBean;
import jp.co.sint.webshop.web.bean.back.order.AddressBean.CustomerAddressBean;


/**
 * U2020210:ゲスト情報入力のアクションクラスです
 * 
 * @author Ob.
 */
public class AddressInitAction extends WebBackAction<AddressBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    AddressBean bean = getBean();
    if (bean == null) {
      setNextUrl("/app/common/index");
      return false;
    }
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
	  AddressBean bean = getBean();
	    CustomerAddressBean address = bean.getAddress();
	    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
	    
	    bean.setAddressScript(s.createAddressScript());
	    address.setAddressPrefectureList(s.createPrefectureList());
	    address.setAddressCityList(s.createCityList(address.getAddressPrefectureCode()));
	    address.setAddressAreaList(s.createAreaList(address.getAddressPrefectureCode(), address.getAddressCityCode()));
	    
	    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

@Override
public boolean authorize() {
	return Permission.ORDER_UPDATE_SITE.isGranted(getLoginInfo());
}
/**
 * Action名の取得
 * 
 * @return Action名
 */
public String getActionName() {
  return "新接受订货登录（收货地址登录）初期表示处理";
}

/**
 * オペレーションコードの取得
 * 
 * @return オペレーションコード
 */
public String getOperationCode() {
  return "1102020001";
}

}
