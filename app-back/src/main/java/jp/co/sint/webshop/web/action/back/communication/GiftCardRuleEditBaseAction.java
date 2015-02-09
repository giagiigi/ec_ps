package jp.co.sint.webshop.web.action.back.communication;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.CancelFlg;
import jp.co.sint.webshop.data.domain.CsvFlg;
import jp.co.sint.webshop.data.dto.GiftCardIssueHistory;
import jp.co.sint.webshop.data.dto.GiftCardRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.GiftCardRuleEditBean;
import jp.co.sint.webshop.web.bean.back.communication.GiftCardRuleEditBean.GiftCardRuleEditBeanDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

public abstract class GiftCardRuleEditBaseAction extends WebBackAction<GiftCardRuleEditBean> {

  public boolean authorize() {

    BackLoginInfo login = getLoginInfo();
    if (null == login) {
      return false;
    }
    return true;
  }
  
  public void setBeanToDto(GiftCardRule dto, GiftCardRuleEditBean giftbean) {
    dto.setCardCode(giftbean.getCardCode());
    dto.setCardName(giftbean.getCardName().trim());
    dto.setEffectiveYears(Long.parseLong(giftbean.getEffectiveYears()));
    dto.setWeight(new BigDecimal(giftbean.getWeight()));
    dto.setUnitPrice(new BigDecimal(giftbean.getUnitPrice()));
    dto.setDenomination(new BigDecimal(giftbean.getDenomination()));
  }

  public void setDtoToBean(GiftCardRule dto, GiftCardRuleEditBean giftbean) {
    giftbean.setCardCode(dto.getCardCode());
    giftbean.setCardName(dto.getCardName());
    giftbean.setEffectiveYears(Long.toString(dto.getEffectiveYears()));
    giftbean.setWeight(dto.getWeight().toString());
    giftbean.setUnitPrice(dto.getUnitPrice().toString());
    giftbean.setDenomination(dto.getDenomination().toString());
  }

  public void showToBean(String cardCode) {

    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    GiftCardRule giftcardrule = communicationService.getGiftCardRule(cardCode);
    List<GiftCardIssueHistory> detaillist = communicationService.getdetail(cardCode);

    if (giftcardrule != null) {
      GiftCardRuleEditBean bean = new GiftCardRuleEditBean();
      bean.setCardCode(giftcardrule.getCardCode());
      bean.setCardName(giftcardrule.getCardName());
      bean.setEffectiveYears(Long.toString(giftcardrule.getEffectiveYears()));
      bean.setWeight(giftcardrule.getWeight().toString());
      bean.setUnitPrice(giftcardrule.getUnitPrice().toString());
      bean.setDenomination(giftcardrule.getDenomination().toString());

      List<GiftCardRuleEditBeanDetail> list = new ArrayList<GiftCardRuleEditBeanDetail>();
      for (GiftCardIssueHistory gift : detaillist) {
        GiftCardRuleEditBeanDetail detailbean = new GiftCardRuleEditBeanDetail();
        detailbean.setCardHistoryNo(NumUtil.toString(gift.getCardHistoryNo()));
        detailbean.setCsvFlg(CsvFlg.fromValue(gift.getCsvFlg()).getName());
        detailbean.setIssueDate(DateUtil.toDateTimeString(gift.getIssueDate()));
        detailbean.setSueNum(NumUtil.toString(gift.getIssueNum()));
        detailbean.setCancelFlg(CancelFlg.fromValue(gift.getCancelFlg()).getName());
        list.add(detailbean);
      }
      bean.setList(list);
      // 显示更新按钮
      
      bean.setDisplayRegisterButton(false);
      bean.setDisplayUpdateButton(true);
      bean.setChoicesAreaDisplay(false);

      setBean(bean);
      setRequestBean(bean);

    } else {
      // 页面初期化设置
      initPageSet();
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_DEFAULT_ERROR));
    }
  }

  /**
   * 根据免邮促销编号验证免邮促销是否存在
   * 
   * @param freePostageCode
   *          :免邮促销编号
   * @return true:存在; false:不存在;
   */
  public boolean existGiftCard(String cardCode) {

    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    GiftCardRule giftCardRule = communicationService.getGiftCardRule(cardCode);

    if (giftCardRule == null) {
      return false;
    }
    return true;

  }

  protected void initPageSet() {
    GiftCardRuleEditBean bean = new GiftCardRuleEditBean();

    // 按钮设置
    bean.setDisplayRegisterButton(true);
    bean.setDisplayUpdateButton(false);
    setRequestBean(bean);
    setBean(bean);

  }
  
  
  public void setCompleteMessage(String cardCode) {

    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    // 根据规则编号得到优惠规则对象
    GiftCardRule giftcardrule = communicationService.getGiftCardRule(cardCode);

    if (giftcardrule != null) {

      // 注册时
      String action = getRequestParameter().getPathArgs()[0];
      if (WebConstantCode.COMPLETE_INSERT.equals(action)) {
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, "礼品卡规则"));
      }
      // 更新时
      if (WebConstantCode.COMPLETE_UPDATE.equals(action)) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, "礼品卡规则"));
      }
      if (WebConstantCode.Gift_ISSUE.equals(action)) {
        addInformationMessage("礼品卡规则发行成功");
      }
      if (WebConstantCode.Gift_CANCEL.equals(action)) {
        addInformationMessage("礼品卡取消标志修改成功");
      }
    }
  }
  
  
  
  
  

  /**
   * 根据礼品卡编号验证礼品卡是否存在
   * 
   * @param couId
   *          :礼品卡编号
   * @return true:存在; false:不存在;
   */
  public boolean checktoDuplicate(String CardCode) {

    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

 
    GiftCardRule dto = communicationService.selectCardCode(CardCode);

    // 如果 对象不存在,提示错误信息
    if (dto == null) {
      return false;
    }
    return true;

  }

  
}
