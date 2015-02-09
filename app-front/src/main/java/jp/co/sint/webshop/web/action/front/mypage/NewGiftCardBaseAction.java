package jp.co.sint.webshop.web.action.front.mypage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.CardStatus;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.CustomerCardInfo;
import jp.co.sint.webshop.data.dto.CustomerCardUseInfo;
import jp.co.sint.webshop.data.dto.GiftCardReturnConfirm;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.customer.OwnerCardDetail;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.NewGiftCardBean;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;

/**
 * U2030110:マイページのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class NewGiftCardBaseAction extends WebFrontAction<NewGiftCardBean> {

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
  public abstract WebActionResult callService();

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  public boolean isCallCreateAttribute() {
    return true;
  }

  /**
   * キャンセル完了表示
   */
  @Override
  public void prerender() {
  }

  public NewGiftCardBean initNewGiftCardBean(NewGiftCardBean beam, Customer customer) {

    NewGiftCardBean bean = new NewGiftCardBean();
    FrontLoginInfo login = getLoginInfo();
    String customerCode = login.getCustomerCode();
    // 是否更新密码
    if (StringUtil.hasValue(customer.getPaymentPassword())) {
      bean.setUpdatePasswordFlg(true);
    } else {
      bean.setUpdatePasswordFlg(false);
    }
    CustomerService service = ServiceLocator.getCustomerService(login);
    CustomerCardInfo cardInfo = service.getCustomerCardInfoByCustomerCode(customerCode);
    CustomerCardUseInfo cardUseInfo = service.getCustomerCardUseInfoBycustomerCode(customerCode);
    CustomerCardInfo cardInfoUnable = service.getCustomerCardInfoByCustomerCodeUnable(customerCode);
    GiftCardReturnConfirm confirmPrice = service.getGiftCardReturnConfirm(customerCode);

    bean.setTotalDenomination(cardInfo.getDenomination().toString());

    bean.setAvalibleDenomination(cardInfo.getDenomination().add(confirmPrice.getReturnAmount())
        .subtract(cardUseInfo.getUseAmount()).subtract(cardInfoUnable.getDenomination()).toString());

    List<OwnerCardDetail> list = service.getCustomerCardInfo(customerCode);
    //可用礼品卡数量
    Long avalibleGiftCardNum =0L;
    if (list != null && list.size() > 0) {
      bean.setList(list);
      for(OwnerCardDetail detail:list){
        if(BigDecimalUtil.isAbove(detail.getAvalibleAmount(), BigDecimal.ZERO) &&
            DateUtil.getSysdate().before(detail.getCardEndDate()) && 
            CardStatus.ACTIVATION.longValue().equals(detail.getCardStatus())){
          avalibleGiftCardNum ++;
        }
      }
    } else {
      bean.setList(new ArrayList<OwnerCardDetail>());
    }

    List<String> rechargeList = new ArrayList<String>();
    List<String> endDateList = new ArrayList<String>();
    for (OwnerCardDetail info : bean.getList()) {
      if (DateUtil.getSysdate().after(info.getCardEndDate())) {
        info.setCardStatus(-1L);
      }
      rechargeList.add(DateUtil.toDateTimeString(info.getRechargeDate(), "yyyy-MM-dd HH:mm:ss"));
      endDateList.add(DateUtil.toDateTimeString(info.getCardEndDate(), "yyyy-MM-dd"));
    }
    bean.setRechargeList(rechargeList);
    bean.setEndDateList(endDateList);
    bean.setDisFlg(true);
    
//    
//    Long giftCardNum =ServiceLocator.getCommunicationService(login).getGiftCardCount(login.getCustomerCode());
//    if(giftCardNum == null ) {
//      giftCardNum=0L;
//    }
    bean.setGiftCardNum(avalibleGiftCardNum.toString()); 
    
    
    return bean;

  }

}
