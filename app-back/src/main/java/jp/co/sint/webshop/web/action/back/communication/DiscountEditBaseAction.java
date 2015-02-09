package jp.co.sint.webshop.web.action.back.communication;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.DisplayFlgNew;
import jp.co.sint.webshop.data.dto.DiscountCommodity;
import jp.co.sint.webshop.data.dto.DiscountHeader;
import jp.co.sint.webshop.service.communication.DiscountInfo;
import jp.co.sint.webshop.service.communication.DiscountInfo.DiscountDetail;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.DiscountEditBean;
import jp.co.sint.webshop.web.bean.back.communication.DiscountEditBean.DiscountDetailBean;

public abstract class DiscountEditBaseAction extends WebBackAction<DiscountEditBean> {

  @Override
  public abstract boolean authorize();

  @Override
  public abstract WebActionResult callService();

  @Override
  public abstract boolean validate();

  public void setDiscountHeader(DiscountEditBean bean, DiscountHeader dh) {
    dh.setDiscountCode(bean.getDiscountCode());
    dh.setDiscountName(bean.getDiscountName());
    dh.setSoCouponFlg(NumUtil.toLong(bean.getSoCouponFlg()));
    dh.setDiscountStartDatetime(DateUtil.fromString(bean.getDiscountStartDatetime(), true));
    dh.setDiscountEndDatetime(DateUtil.fromString(bean.getDiscountEndDatetime(), true));
    dh.setCommodityTypeNum(NumUtil.toLong(bean.getCommodityTypeNum(),null));
  }

  public void setDiscountEditBean(DiscountEditBean bean, DiscountInfo discountInfo) {
    DiscountHeader dh = discountInfo.getDiscountHeader();

    bean.setDiscountCode(dh.getDiscountCode());
    bean.setDiscountName(dh.getDiscountName());
    bean.setDiscountStartDatetime(DateUtil.toDateTimeString(dh.getDiscountStartDatetime()));
    bean.setDiscountEndDatetime(DateUtil.toDateTimeString(dh.getDiscountEndDatetime()));
    bean.setCommodityTypeNum(NumUtil.toString(dh.getCommodityTypeNum()));
    
    if (dh.getSoCouponFlg() != null) {
      bean.setSoCouponFlg(dh.getSoCouponFlg().toString());
    } else {
      bean.setSoCouponFlg(DisplayFlgNew.ONCOUPON.getValue());
    }
   

    List<DiscountDetailBean> list = new ArrayList<DiscountDetailBean>();
    for (DiscountDetail dd : discountInfo.getDetailList()) {
      DiscountDetailBean detail = new DiscountDetailBean();
      detail.setCommodityCode(dd.getCommodityCode());
      detail.setCommodityName(dd.getCommodityName());
      if (dd.getCustomerMaxTotalNum() != null) {
        detail.setCustomerMaxTotalNum(NumUtil.toString(dd.getCustomerMaxTotalNum()));
      }
      if (dd.getDiscountPrice() != null) {
        detail.setDiscountPrice(NumUtil.toString(dd.getDiscountPrice()));
      }
      detail.setSaleEndDatetime(DateUtil.toDateString(dd.getSaleEndDatetime()));
      detail.setSaleStartDatetime(DateUtil.toDateString(dd.getSaleStartDatetime()));
      if (dd.getSiteMaxTotalNum() != null) {
        detail.setSiteMaxTotalNum(NumUtil.toString(dd.getSiteMaxTotalNum()));
      }
      detail.setStockQuantity(NumUtil.toString(dd.getStockQuantity()));
      detail.setUnitPrice(NumUtil.toString(dd.getUnitPrice()));
      if (dd.getUseFlg() != null) {
        detail.setUseFlg(dd.getUseFlg());
      }
      detail.setDiscountDirectionsCn(dd.getDiscountDirectionsCn());
      detail.setDiscountDirectionsJp(dd.getDiscountDirectionsJp());
      detail.setDiscountDirectionsEn(dd.getDiscountDirectionsEn());
      if (dd.getRankCn() != null) {
        detail.setRankcn(dd.getRankCn().toString());
        detail.setDisplayFlgcn(1L);
      } else {
        detail.setDisplayFlgcn(0L);
      }
      if (dd.getRankJp() != null) {
        detail.setRankjp(dd.getRankJp().toString());
        detail.setDisplayFlgjp(1L);
      } else {
        detail.setDisplayFlgjp(0L);
      }
      if (dd.getRankEn() != null) {
        detail.setRanken(dd.getRankEn().toString());
        detail.setDisplayFlgen(1L);
      } else {
        detail.setDisplayFlgen(0L);
      }

      
      list.add(detail);
    }
    bean.setList(list);
  }

  public void setDiscountCommodity(DiscountEditBean bean, DiscountCommodity dc) {
    DiscountDetailBean edit = bean.getEdit();
    dc.setDiscountCode(bean.getDiscountCode());
    dc.setCommodityCode(edit.getCommodityCode());
    dc.setDiscountPrice(new BigDecimal(edit.getDiscountPrice()));
    dc.setCustomerMaxTotalNum(NumUtil.toLong(edit.getCustomerMaxTotalNum()));
    dc.setSiteMaxTotalNum(NumUtil.toLong(edit.getSiteMaxTotalNum()));
    dc.setUseFlg(edit.getUseFlg());
    dc.setDiscountDirectionsCn(edit.getDiscountDirectionsCn());
    dc.setDiscountDirectionsJp(edit.getDiscountDirectionsJp());
    dc.setDiscountDirectionsEn(edit.getDiscountDirectionsEn());
    if (StringUtil.hasValue(edit.getRankcn())) {
      dc.setRankCn(Long.parseLong(edit.getRankcn()));
    }
    if (StringUtil.hasValue(edit.getRankjp())) {
      dc.setRankJp(Long.parseLong(edit.getRankjp()));
    }
    if (StringUtil.hasValue(edit.getRanken())) {
      dc.setRankEn(Long.parseLong(edit.getRanken()));
    }
  }

}
