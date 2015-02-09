package jp.co.sint.webshop.web.action.back.communication;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.GiftCardRule;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.GiftCardRuleEditBean;
import jp.co.sint.webshop.web.bean.back.communication.GiftCardRuleListBean;
import jp.co.sint.webshop.web.bean.back.communication.GiftCardRuleListBean.GiftCardRuleListBeanDetail;

/**
 * U1060110:アンケート管理のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class GiftCardRuleListBaseAction extends WebBackAction<GiftCardRuleListBean> {

  public void setBeanToDto(GiftCardRule dto, GiftCardRuleEditBean giftbean) {
    dto.setCardCode(giftbean.getCardCode());
    dto.setCardName(giftbean.getCardName());
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

  protected void initPageSet() {
    // TODO Auto-generated method stub
    GiftCardRuleListBean bean = new GiftCardRuleListBean();

    setRequestBean(bean);
    setBean(bean);

  }

  public List<GiftCardRuleListBeanDetail> createList(List<GiftCardRule> resultList) {
    List<GiftCardRuleListBeanDetail> list = new ArrayList<GiftCardRuleListBeanDetail>();
    for (GiftCardRule giftCardRule : resultList) {
      GiftCardRuleListBeanDetail detail = new GiftCardRuleListBeanDetail();

      detail.setCardCode(giftCardRule.getCardCode());
      detail.setCardName(giftCardRule.getCardName());
      detail.setEffectiveYears(giftCardRule.getEffectiveYears().toString());
      detail.setWeight(giftCardRule.getWeight().toString());
      detail.setUnitPrice(giftCardRule.getUnitPrice().toString());
      detail.setDenomination(giftCardRule.getDenomination().toString());

      list.add(detail);
    }

    return list;
  }

  public void setDtoToDetailBean(GiftCardRule dto) {

    GiftCardRuleListBeanDetail bean = new GiftCardRuleListBeanDetail();
    bean.setCardCode(dto.getCardCode());
    bean.setCardName(dto.getCardName());
    bean.setEffectiveYears(Long.toString(dto.getEffectiveYears()));
    bean.setWeight(dto.getWeight().toString());
    bean.setUnitPrice(dto.getUnitPrice().toString());
    bean.setDenomination(dto.getDenomination().toString());
  }

}
