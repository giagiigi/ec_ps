package jp.co.sint.webshop.web.action.front.mypage;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.CustomerCardInfo;
import jp.co.sint.webshop.data.dto.CustomerCardUseInfo;
import jp.co.sint.webshop.data.dto.GiftCardReturnConfirm;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.customer.OwnerCardDetail;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.OwnerCardBean;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;

/**
 * U2030110:マイページのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class OwnerCardInitAction extends WebFrontAction<OwnerCardBean> {

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
    OwnerCardBean bean = getBean();
    FrontLoginInfo login = getLoginInfo();
    String customerCode = login.getCustomerCode();
    CustomerService service = ServiceLocator.getCustomerService(login);
    CustomerCardInfo cardInfo = service.getCustomerCardInfoByCustomerCode(customerCode);
    CustomerCardUseInfo cardUseInfo = service.getCustomerCardUseInfoBycustomerCode(customerCode);
    CustomerCardInfo cardInfoUnable = service.getCustomerCardInfoByCustomerCodeUnable(customerCode);
    GiftCardReturnConfirm confirmPrice = service.getGiftCardReturnConfirm(customerCode);
    
    bean.setTotalDenomination(cardInfo.getDenomination().toString());
    
    bean.setAvalibleDenomination(cardInfo.getDenomination().add(confirmPrice.getReturnAmount()).subtract(cardUseInfo.getUseAmount()).subtract(cardInfoUnable.getDenomination()).toString());
    
    List<OwnerCardDetail> list = service.getCustomerCardInfo(customerCode);
    if (list != null && list.size() > 0) {
      bean.setList(list);
    } else {
      bean.setList(new ArrayList<OwnerCardDetail>());
    }
    
    List<String> rechargeList = new ArrayList<String>();
    List<String> endDateList = new ArrayList<String>();
    for (OwnerCardDetail info : bean.getList()) {
      if (DateUtil.getSysdate().after(info.getCardEndDate())) {
        info.setCardStatus(-1L);
      }
      rechargeList.add( DateUtil.toDateTimeString(info.getRechargeDate(),"yyyy-MM-dd HH:mm:ss"));
      endDateList.add( DateUtil.toDateTimeString(info.getCardEndDate(),"yyyy-MM-dd"));
    }
    bean.setRechargeList(rechargeList);
    bean.setEndDateList(endDateList);
    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;
  }

 
  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  public boolean isCallCreateAttribute() {
    return false;
  }


}
