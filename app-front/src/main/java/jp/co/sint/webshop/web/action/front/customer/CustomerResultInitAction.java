package jp.co.sint.webshop.web.action.front.customer;

import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.List;

import jp.co.sint.webshop.data.domain.AdvertType;
import jp.co.sint.webshop.data.dto.Advert;
import jp.co.sint.webshop.data.dto.CustomerAttribute;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.customer.CustomerResultBean;
import jp.co.sint.webshop.web.webutility.BackTransitionInfo;

/**
 * U2030240:お客様情報登録完了のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerResultInitAction extends WebFrontAction<CustomerResultBean> {

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
    CustomerResultBean bean = (CustomerResultBean) getBean();

    // セッションから顧客コードを取得
    bean.setCustomerCode(getLoginInfo().getCustomerCode());

    // 顧客属性が存在するかチェック
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    List<CustomerAttribute> getAttributeList = service.getAttributeList();
    if (getAttributeList.size() > 0) {
      bean.setHasCustomerAttributeFlg(true);
    } else {
      bean.setHasCustomerAttributeFlg(false);
    }

    BackTransitionInfo afterLoginInfo = getSessionContainer().getAfterLoginInfo();
    if (StringUtil.hasValue(afterLoginInfo.getUrl())) {
      bean.setHasNextUrl(true);
      bean.setEscapeNextUrl(afterLoginInfo.getUrl());
    } else {
      bean.setHasNextUrl(false);
      if (bean.isUpdateFlg()) {
        bean.setEscapeNextUrl("/app/mypage/mypage");
      } else {
        bean.setEscapeNextUrl("/app/common/index");
      }
    }

    // Add by V10-CH start
    SiteManagementService ss = ServiceLocator.getSiteManagementService(getLoginInfo());
    try {
      List<Advert> adList = ss.getEnabledAdvert(AdvertType.CUSTOMER_REGISTER_COMPLETE.getValue());
      for (Advert ad : adList) {
        CustomerInfo ci = service.getCustomer(bean.getCustomerCode());
        bean.setAdvertUrl(MessageFormat.format(DIContainer.getAdvertValue().getAdvertUrl1(), ad.getAdvertText(), URLEncoder.encode(
            ci.getCustomer().getEmail(), "UTF-8")));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Add by V10-CH end

    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;
  }
}
