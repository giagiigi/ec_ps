package jp.co.sint.webshop.web.action.back.communication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.CampaignConditionFlg;
import jp.co.sint.webshop.data.domain.CampaignMainType;
import jp.co.sint.webshop.data.domain.CouponIssueType;
import jp.co.sint.webshop.data.domain.NewCampaignConditionType;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.dto.CampaignCondition;
import jp.co.sint.webshop.data.dto.CampaignDoings;
import jp.co.sint.webshop.data.dto.CampaignMain;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.Prefecture;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.communication.CampaignLine;
import jp.co.sint.webshop.service.communication.GiftCampaign;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.NewCampaignEditBean;
import jp.co.sint.webshop.web.bean.back.communication.NewCampaignEditBean.GiftCommodityBean;
import jp.co.sint.webshop.web.bean.back.communication.NewCampaignEditBean.PrefectureBean;
import jp.co.sint.webshop.web.bean.back.communication.NewCampaignEditBean.RelatedCommodityBean;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

public abstract class NewCampaignEditBaseAction extends WebBackAction<NewCampaignEditBean> {

  @Override
  public abstract boolean authorize();

  @Override
  public abstract WebActionResult callService();

  @Override
  public abstract boolean validate();

  public void setCampaignLine(NewCampaignEditBean bean, CampaignLine campaignLine) {
    CampaignMain campaignMain = new CampaignMain();
    CampaignCondition campaignCondition = new CampaignCondition();
    CampaignCondition condition = new CampaignCondition();
    CampaignDoings campaignDoings = new CampaignDoings();

    // 为dto赋值
    campaignMain.setCampaignCode(bean.getCampaignCode());
    campaignMain.setCampaignName(bean.getCampaignName());
    campaignMain.setCampaignNameEn(bean.getCampaignNameEn());
    campaignMain.setCampaignNameJp(bean.getCampaignNameJp());
    campaignMain.setCampaignStartDate(DateUtil.fromString(bean.getCampaignStartDate(), true));
    campaignMain.setCampaignEndDate(DateUtil.setSecond(DateUtil.fromString(bean.getCampaignEndDate(), true), 59));
    campaignMain.setMemo(bean.getRemarks());

    // 判断 免运费
    if (CampaignMainType.SHIPPING_CHARGE_FREE.getValue().equals(bean.getCampaignType())) {

      campaignMain.setCampaignType(CampaignMainType.SHIPPING_CHARGE_FREE.longValue());

      if (bean.getCheckList() != null && bean.getCheckList().size() > 1) {
        if (!StringUtil.isNullOrEmpty(bean.getOrderLimit())) {
          campaignMain.setOrderLimit(Long.valueOf(bean.getOrderLimit()));
        }

        campaignCondition.setCampaignCode(bean.getCampaignCode());

        campaignCondition.setCampaignConditionType(NewCampaignConditionType.ORDER_COMMODITY.longValue());

        if (!StringUtil.isNullOrEmpty(bean.getCampaignConditionFlg())) {
          campaignCondition.setCampaignConditionFlg(Long.valueOf(bean.getCampaignConditionFlg()));
        }
        if (!StringUtil.isNullOrEmpty(bean.getMaxCommodityNum())) {
          campaignCondition.setMaxCommodityNum(Long.valueOf(bean.getMaxCommodityNum()));
        }
        if (WebConstantCode.DISPLAY_EDIT.equals(bean.getDisplayMode())) {
          campaignCondition.setAttributrValue("");
        }
        campaignLine.getConditionList().add(campaignCondition);

        condition.setCampaignCode(bean.getCampaignCode());
        condition.setCampaignConditionType(NewCampaignConditionType.ORDER_ADDRESS.longValue());
        condition.setCampaignConditionFlg(CampaignConditionFlg.CAMPAIGN_CONDITION_CONTAIN.longValue());

        condition.setAttributrValue("");
        campaignLine.getConditionList().add(condition);

      } else if (bean.getCheckList() != null && bean.getCheckList().size() == 1) {

        if (NewCampaignConditionType.ORDER_ADDRESS.getValue().equals(bean.getCampaignConditionType())) {
          // 促销条件表.促销编号 = 画面.活动编号
          // 促销条件表.条件类型 = 对象类型(2:订单地址区域)
          // 促销条件表.对象商品 = 1：包含
          if (!StringUtil.isNullOrEmpty(bean.getOrderLimit())) {
            campaignMain.setOrderLimit(Long.valueOf(bean.getOrderLimit()));
          }
          condition.setCampaignCode(bean.getCampaignCode());
          condition.setCampaignConditionType(NewCampaignConditionType.ORDER_ADDRESS.longValue());
          condition.setCampaignConditionFlg(CampaignConditionFlg.CAMPAIGN_CONDITION_CONTAIN.longValue());
          condition.setAttributrValue("");
          campaignLine.getConditionList().add(condition);

        } else {

          if (!StringUtil.isNullOrEmpty(bean.getOrderLimit())) {
            campaignMain.setOrderLimit(Long.valueOf(bean.getOrderLimit()));
          }

          campaignCondition.setCampaignCode(bean.getCampaignCode());

          campaignCondition.setCampaignConditionType(NewCampaignConditionType.ORDER_COMMODITY.longValue());

          if (!StringUtil.isNullOrEmpty(bean.getCampaignConditionFlg())) {
            campaignCondition.setCampaignConditionFlg(Long.valueOf(bean.getCampaignConditionFlg()));
          }
          if (!StringUtil.isNullOrEmpty(bean.getMaxCommodityNum())) {
            campaignCondition.setMaxCommodityNum(Long.valueOf(bean.getMaxCommodityNum()));
          }
          campaignCondition.setAttributrValue("");
          campaignLine.getConditionList().add(campaignCondition);
        }

      }

    } else if (CampaignMainType.SALE_OFF.getValue().equals(bean.getCampaignType())
        && CouponIssueType.PROPORTION.getValue().equals(bean.getDiscountType())) {

      campaignMain.setCampaignType(CampaignMainType.SALE_OFF.longValue());

      // 促销行为
      campaignDoings.setCampaignCode(bean.getCampaignCode());
      campaignDoings.setCampaignType(CampaignMainType.SALE_OFF.longValue());
      campaignDoings.setAttributrValue(bean.getDiscoutRate());

      // 促销条件
      // 促销条件表.促销编号 = 画面.活动编号
      // 促销条件表.条件类型 = 对象类型(1:订单对象商品)
      // 促销条件表.对象商品 = 1：包含
      // 促销条件表.折扣区分 = 画面.折扣类型
      // 促销条件表.内容 = ""
      campaignCondition.setCampaignCode(bean.getCampaignCode());
      campaignCondition.setCampaignConditionType(NewCampaignConditionType.ORDER_COMMODITY.longValue());
      campaignCondition.setCampaignConditionFlg(CampaignConditionFlg.CAMPAIGN_CONDITION_CONTAIN.longValue());
      campaignCondition.setDiscountType(CouponIssueType.PROPORTION.longValue());
      campaignCondition.setAttributrValue("");
      if (!StringUtil.isNullOrEmpty(bean.getMaxCommodityNum())) {
        campaignCondition.setMaxCommodityNum(Long.valueOf(bean.getMaxCommodityNum()));
      }
      if (!StringUtil.isNullOrEmpty(bean.getUseLimit())) {
        campaignCondition.setUseLimit(Long.valueOf(bean.getUseLimit()));
      }

      campaignLine.getConditionList().add(campaignCondition);

      // 判断 折扣 金额
    } else if (CampaignMainType.SALE_OFF.getValue().equals(bean.getCampaignType())
        && CouponIssueType.FIXED.getValue().equals(bean.getDiscountType())) {

      campaignMain.setCampaignType(CampaignMainType.SALE_OFF.longValue());

      // 促销行为
      campaignDoings.setCampaignCode(bean.getCampaignCode());
      campaignDoings.setCampaignType(CampaignMainType.SALE_OFF.longValue());
      campaignDoings.setAttributrValue(bean.getDiscountMoney());

      // 促销条件
      campaignCondition.setCampaignCode(bean.getCampaignCode());
      campaignCondition.setCampaignConditionType(NewCampaignConditionType.ORDER_COMMODITY.longValue());
      campaignCondition.setCampaignConditionFlg(CampaignConditionFlg.CAMPAIGN_CONDITION_CONTAIN.longValue());
      campaignCondition.setDiscountType(CouponIssueType.FIXED.longValue());
      campaignCondition.setAttributrValue("");
      if (!StringUtil.isNullOrEmpty(bean.getMaxCommodityNum())) {
        campaignCondition.setMaxCommodityNum(Long.valueOf(bean.getMaxCommodityNum()));
      }
      if (!StringUtil.isNullOrEmpty(bean.getUseLimit())) {
        campaignCondition.setUseLimit(Long.valueOf(bean.getUseLimit()));
      }
      campaignLine.getConditionList().add(campaignCondition);

      // 判断 礼品多关联
    } else if (CampaignMainType.MULTIPLE_GIFT.getValue().equals(bean.getCampaignType())) {

      campaignMain.setCampaignType(CampaignMainType.MULTIPLE_GIFT.longValue());

      // 促销条件
      campaignCondition.setCampaignCode(bean.getCampaignCode());
      campaignCondition.setCampaignConditionType(NewCampaignConditionType.COMMODITY_RELATED.longValue());
      campaignCondition.setCampaignConditionFlg(Long.valueOf(bean.getCampaignConditionFlg()));

      if (!StringUtil.isNullOrEmpty(bean.getOrderAmount())) {
        campaignCondition.setOrderAmount(NumUtil.parse(bean.getOrderAmount()));
      }
      if (!StringUtil.isNullOrEmpty(bean.getAdvertCode())) {
        campaignCondition.setAdvertCode(bean.getAdvertCode());
      }

      campaignCondition.setAttributrValue("");
      campaignLine.getConditionList().add(campaignCondition);

      // 促销行为
      // 内容：默认为 ''
      campaignDoings.setCampaignCode(bean.getCampaignCode());
      campaignDoings.setCampaignType(CampaignMainType.MULTIPLE_GIFT.longValue());
      campaignDoings.setAttributrValue("");

      // 判断 礼品特定
    } else if (CampaignMainType.GIFT.getValue().equals(bean.getCampaignType())) {

      campaignMain.setCampaignType(CampaignMainType.GIFT.longValue());

      // 促销条件
      campaignCondition.setCampaignCode(bean.getCampaignCode());
      // 条件类型：对象商品
      campaignCondition.setCampaignConditionType(NewCampaignConditionType.ORDER_COMMODITY.longValue());
      // 对象商品：包含
      campaignCondition.setCampaignConditionFlg(CampaignConditionFlg.CAMPAIGN_CONDITION_CONTAIN.longValue());
      campaignCondition.setAttributrValue("");
      campaignLine.getConditionList().add(campaignCondition);

      // 促销行为
      campaignDoings.setCampaignCode(bean.getCampaignCode());
      campaignDoings.setCampaignType(CampaignMainType.GIFT.longValue());
      campaignDoings.setAttributrValue("");

    }
    campaignLine.setCampaignMain(campaignMain);
    campaignLine.setCampaignDoings(campaignDoings);

  }

  /**
   * 
   * @param bean
   * @param campaignLine
   */
  public void setModifyCampaignLine(NewCampaignEditBean bean, CampaignLine campaignLine) {

    // 为dto赋值
    campaignLine.getCampaignMain().setCampaignName(bean.getCampaignName());
    campaignLine.getCampaignMain().setCampaignNameEn(bean.getCampaignNameEn());
    campaignLine.getCampaignMain().setCampaignNameJp(bean.getCampaignNameJp());
    campaignLine.getCampaignMain().setCampaignStartDate(DateUtil.fromString(bean.getCampaignStartDate(), true));
    campaignLine.getCampaignMain().setCampaignEndDate(DateUtil.setSecond(DateUtil.fromString(bean.getCampaignEndDate(), true), 59));
    campaignLine.getCampaignMain().setMemo(bean.getRemarks());
    campaignLine.getCampaignMain().setUpdatedDatetime(bean.getUpdateDatetime());
    
    // 判断 免运费
    if (CampaignMainType.SHIPPING_CHARGE_FREE.getValue().equals(bean.getCampaignType())) {

      campaignLine.getCampaignMain().setCampaignType(CampaignMainType.SHIPPING_CHARGE_FREE.longValue());
      if (!StringUtil.isNullOrEmpty(bean.getOrderLimit())) {
        campaignLine.getCampaignMain().setOrderLimit(Long.valueOf(bean.getOrderLimit()));
      } else {
        campaignLine.getCampaignMain().setOrderLimit(null);
      }

      // 判断复选框是否被选中，根据选中情况来判断更新时是否将原数据删除
      if (bean.getCheckList() != null && bean.getCheckList().size() > 1) {
        for (int i = 0; i < campaignLine.getConditionList().size(); i++) {
          String type = NumUtil.toString(campaignLine.getConditionList().get(i).getCampaignConditionType());
          if (NewCampaignConditionType.ORDER_COMMODITY.getValue().equals(type)) {
            if (!StringUtil.isNullOrEmpty(bean.getMaxCommodityNum())) {
              campaignLine.getConditionList().get(i).setMaxCommodityNum(Long.valueOf(bean.getMaxCommodityNum()));
            } else {
              campaignLine.getConditionList().get(i).setMaxCommodityNum(null);
            }

            if (!StringUtil.isNullOrEmpty(bean.getCampaignConditionFlg())) {
              campaignLine.getConditionList().get(i).setCampaignConditionFlg(
                  Long.valueOf(bean.getCampaignConditionFlg()));
            } else {
              campaignLine.getConditionList().get(i).setCampaignConditionFlg(CampaignConditionFlg.CAMPAIGN_CONDITION_CONTAIN.longValue());
            }
          }
        }
      } else if (bean.getCheckList() != null && bean.getCheckList().size() == 1) {
        // 判断哪个被选中 移除区域
        if (NewCampaignConditionType.ORDER_COMMODITY.getValue().equals(bean.getCheckList().get(0))) {

          for (int i = 0; i < campaignLine.getConditionList().size(); i++) {
            String type = NumUtil.toString(campaignLine.getConditionList().get(i).getCampaignConditionType());
            if (NewCampaignConditionType.ORDER_COMMODITY.getValue().equals(type)) {
              if (!StringUtil.isNullOrEmpty(bean.getMaxCommodityNum())) {
                campaignLine.getConditionList().get(i).setMaxCommodityNum(Long.valueOf(bean.getMaxCommodityNum()));

              }else{
                campaignLine.getConditionList().get(i).setMaxCommodityNum(null);
              }
              if (!StringUtil.isNullOrEmpty(bean.getCampaignConditionFlg())) {
                campaignLine.getConditionList().get(i).setCampaignConditionFlg(
                    Long.valueOf(bean.getCampaignConditionFlg()));
              }else{
                campaignLine.getConditionList().get(i).setCampaignConditionFlg(CampaignConditionFlg.CAMPAIGN_CONDITION_CONTAIN.longValue());
              }
            }
          }
        }
      }

    } else if (CampaignMainType.SALE_OFF.getValue().equals(bean.getCampaignType())
        && CouponIssueType.PROPORTION.getValue().equals(bean.getDiscountType())) {

      campaignLine.getCampaignMain().setCampaignType(CampaignMainType.SALE_OFF.longValue());
      // 促销行为
      campaignLine.getCampaignDoings().setAttributrValue(bean.getDiscoutRate());

      // 促销条件
      campaignLine.getConditionList().get(0).setDiscountType(CouponIssueType.PROPORTION.longValue());
      campaignLine.getConditionList().get(0).setCampaignConditionFlg(CampaignConditionFlg.CAMPAIGN_CONDITION_CONTAIN.longValue());

      if (!StringUtil.isNullOrEmpty(bean.getMaxCommodityNum())) {
        campaignLine.getConditionList().get(0).setMaxCommodityNum(Long.valueOf(bean.getMaxCommodityNum()));
      } else {
        campaignLine.getConditionList().get(0).setMaxCommodityNum(null);
      }

      if (!StringUtil.isNullOrEmpty(bean.getUseLimit())) {
        campaignLine.getConditionList().get(0).setUseLimit(Long.valueOf(bean.getUseLimit()));
      } else {
        campaignLine.getConditionList().get(0).setUseLimit(null);
      }

      // 判断 折扣 金额
    } else if (CampaignMainType.SALE_OFF.getValue().equals(bean.getCampaignType())
        && CouponIssueType.FIXED.getValue().equals(bean.getDiscountType())) {

      campaignLine.getCampaignMain().setCampaignType(CampaignMainType.SALE_OFF.longValue());
      // 促销行为
      campaignLine.getCampaignDoings().setAttributrValue(bean.getDiscountMoney());

      // 促销条件
      campaignLine.getConditionList().get(0).setDiscountType(CouponIssueType.FIXED.longValue());
      campaignLine.getConditionList().get(0).setCampaignConditionFlg(CampaignConditionFlg.CAMPAIGN_CONDITION_CONTAIN.longValue());
      if (!StringUtil.isNullOrEmpty(bean.getMaxCommodityNum())) {
        campaignLine.getConditionList().get(0).setMaxCommodityNum(Long.valueOf(bean.getMaxCommodityNum()));
      } else {
        campaignLine.getConditionList().get(0).setMaxCommodityNum(null);
      }

      if (!StringUtil.isNullOrEmpty(bean.getUseLimit())) {
        campaignLine.getConditionList().get(0).setUseLimit(Long.valueOf(bean.getUseLimit()));
      } else {
        campaignLine.getConditionList().get(0).setUseLimit(null);
      }
      // 判断 礼品多关联
    } else if (CampaignMainType.MULTIPLE_GIFT.getValue().equals(bean.getCampaignType())) {

      campaignLine.getCampaignMain().setCampaignType(CampaignMainType.MULTIPLE_GIFT.longValue());

      // 促销条件
      campaignLine.getConditionList().get(0).setCampaignConditionFlg(Long.valueOf(bean.getCampaignConditionFlg()));

      if (!StringUtil.isNullOrEmpty(bean.getOrderAmount())) {
        campaignLine.getConditionList().get(0).setOrderAmount(NumUtil.parse(bean.getOrderAmount()));
      } else {
        campaignLine.getConditionList().get(0).setOrderAmount(null);
      }
      if (!StringUtil.isNullOrEmpty(bean.getAdvertCode())) {
        campaignLine.getConditionList().get(0).setAdvertCode(bean.getAdvertCode());
      } else {
        campaignLine.getConditionList().get(0).setAdvertCode(null);
      }

      // 判断 礼品特定
    } else if (CampaignMainType.GIFT.getValue().equals(bean.getCampaignType())) {

      campaignLine.getCampaignMain().setCampaignType(CampaignMainType.GIFT.longValue());
      campaignLine.getConditionList().get(0).setCampaignConditionFlg(CampaignConditionFlg.CAMPAIGN_CONDITION_CONTAIN.longValue());
      if (StringUtil.hasValue(bean.getGiftAmount())) {
        campaignLine.getCampaignMain().setGiftAmount(Long.parseLong(bean.getGiftAmount()));
      } else {
        campaignLine.getCampaignMain().setGiftAmount(null);
      }
      
      campaignLine.getCampaignMain().setMinCommodityNum(NumUtil.toLong(bean.getMinCommodityNum()));
    }

  }

  public void setNewCampaignEditBean(NewCampaignEditBean bean, CampaignLine campaignLine) {
    bean.setCampaignCode(campaignLine.getCampaignMain().getCampaignCode());
    bean.setCampaignName(campaignLine.getCampaignMain().getCampaignName());
    bean.setCampaignNameEn(campaignLine.getCampaignMain().getCampaignNameEn());
    bean.setCampaignNameJp(campaignLine.getCampaignMain().getCampaignNameJp());
    bean.setCampaignStartDate(DateUtil.toDateTimeString(campaignLine.getCampaignMain().getCampaignStartDate()));
    bean.setCampaignEndDate(DateUtil.toDateTimeString(campaignLine.getCampaignMain().getCampaignEndDate()));
    bean.setUpdateDatetime(campaignLine.getCampaignMain().getUpdatedDatetime());
    if(campaignLine.getCampaignMain().getGiftAmount() != null) {
      bean.setGiftAmount(campaignLine.getCampaignMain().getGiftAmount().toString());
    }
    
    List<CodeAttribute> campaignTypeList = new ArrayList<CodeAttribute>();
    campaignTypeList.add(new NameValue("请选择", ""));
    for (CodeAttribute code : CampaignMainType.values()) {
      campaignTypeList.add(new NameValue(code.getName(), code.getValue()));
    }
    bean.setCampaignTypeList(campaignTypeList);
    bean.setCampaignType(NumUtil.toString(campaignLine.getCampaignMain().getCampaignType()));

    if (CampaignMainType.SHIPPING_CHARGE_FREE.getValue().equals(bean.getCampaignType())) {

      // 对象类型初始化
      List<CodeAttribute> campaignConditionTypeList = new ArrayList<CodeAttribute>();
      campaignConditionTypeList.add(new NameValue(NewCampaignConditionType.ORDER_COMMODITY.getName(),
          NewCampaignConditionType.ORDER_COMMODITY.getValue()));
      campaignConditionTypeList.add(new NameValue(NewCampaignConditionType.ORDER_ADDRESS.getName(),
          NewCampaignConditionType.ORDER_ADDRESS.getValue()));

      bean.setCampaignConditionTypeList(campaignConditionTypeList);

      if (campaignLine.getConditionList().size() > 1) {
        bean.getCheckList().add(NumUtil.toString(campaignLine.getConditionList().get(0).getCampaignConditionType()));
        bean.getCheckList().add(NumUtil.toString(campaignLine.getConditionList().get(1).getCampaignConditionType()));

      } else if (campaignLine.getConditionList().size() == 1) {
        bean.getCheckList().add(NumUtil.toString(campaignLine.getConditionList().get(0).getCampaignConditionType()));
      }

      // 对象商品区分
      List<CodeAttribute> campaignConditionFlgList = new ArrayList<CodeAttribute>();
      campaignConditionFlgList.add(new NameValue(CampaignConditionFlg.CAMPAIGN_CONDITION_CONTAIN.getName(),
          CampaignConditionFlg.CAMPAIGN_CONDITION_CONTAIN.getValue()));
      campaignConditionFlgList.add(new NameValue(CampaignConditionFlg.CAMPAIGN_CONDITION_ONLY.getName(),
          CampaignConditionFlg.CAMPAIGN_CONDITION_ONLY.getValue()));
      bean.setCampaignConditionFlgList(campaignConditionFlgList);
      
      for (int i = 0; i < campaignLine.getConditionList().size(); i++) {
        if (NewCampaignConditionType.ORDER_COMMODITY.longValue().equals(campaignLine.getConditionList().get(i).getCampaignConditionType())) {
          bean.setCampaignConditionFlg(NumUtil.toString(campaignLine.getConditionList().get(i).getCampaignConditionFlg()));
          if (campaignLine.getConditionList().get(i).getMaxCommodityNum() != null) {
            bean.setMaxCommodityNum(NumUtil.toString(campaignLine.getConditionList().get(i).getMaxCommodityNum()));
          }
          break;
        } else {
          bean.setCampaignConditionFlg(CampaignConditionFlg.CAMPAIGN_CONDITION_CONTAIN.getValue());
        }
      }

    } else if (CampaignMainType.MULTIPLE_GIFT.getValue().equals(bean.getCampaignType())) {
      List<CodeAttribute> campaignConditionFlgList = new ArrayList<CodeAttribute>();
      campaignConditionFlgList.add(new NameValue(CampaignConditionFlg.CAMPAIGN_CONDITION_CONTAIN.getName(),
          CampaignConditionFlg.CAMPAIGN_CONDITION_CONTAIN.getValue()));
      campaignConditionFlgList.add(new NameValue(CampaignConditionFlg.CAMPAIGN_CONDITION_ONLY.getName(),
          CampaignConditionFlg.CAMPAIGN_CONDITION_ONLY.getValue()));
      bean.setCampaignConditionFlgList(campaignConditionFlgList);

      if (campaignLine.getConditionList() != null) {
        bean.setCampaignConditionFlg(NumUtil.toString(campaignLine.getConditionList().get(0).getCampaignConditionFlg()));
      }
      if (campaignLine.getConditionList() != null) {
        if (campaignLine.getConditionList().get(0).getOrderAmount() != null) {
          bean.setOrderAmount(NumUtil.toString(campaignLine.getConditionList().get(0).getOrderAmount()));
        }
        bean.setAdvertCode(campaignLine.getConditionList().get(0).getAdvertCode());
      }

    } else if (CampaignMainType.SALE_OFF.getValue().equals(bean.getCampaignType())) {
      
      // 折扣类型
      List<CodeAttribute> discountTypeList = new ArrayList<CodeAttribute>();
      discountTypeList.add(new NameValue(CouponIssueType.PROPORTION.getName(), CouponIssueType.PROPORTION.getValue()));
      discountTypeList.add(new NameValue(CouponIssueType.FIXED.getName(), CouponIssueType.FIXED.getValue()));
      bean.setDiscountTypeList(discountTypeList);
      
      // 设置默认值
      if (campaignLine.getConditionList() != null) {
        bean.setDiscountType(NumUtil.toString(campaignLine.getConditionList().get(0).getDiscountType()));
        if (campaignLine.getConditionList().get(0).getMaxCommodityNum() != null) {
          bean.setMaxCommodityNum(NumUtil.toString(campaignLine.getConditionList().get(0).getMaxCommodityNum()));
        }
        if (campaignLine.getConditionList().get(0).getUseLimit() != null) {
          bean.setUseLimit(NumUtil.toString(campaignLine.getConditionList().get(0).getUseLimit()));
        }

      }
      if (campaignLine.getCampaignDoings() != null && campaignLine.getConditionList() != null) {
        if (CouponIssueType.PROPORTION.longValue().equals(campaignLine.getConditionList().get(0).getDiscountType())) {
          bean.setDiscoutRate(campaignLine.getCampaignDoings().getAttributrValue());
        } else {
          bean.setDiscountMoney(campaignLine.getCampaignDoings().getAttributrValue());
        }

      }
      //赠品促销
    }else if (CampaignMainType.GIFT.getValue().equals(bean.getCampaignType())) {
      List<CodeAttribute> commodityNumTypeList = new ArrayList<CodeAttribute>();
      bean.setCommodityNumTypeList(commodityNumTypeList);
      
      if(campaignLine.getCampaignMain().getMinCommodityNum() !=null){
        bean.setMinCommodityNum(NumUtil.toString(campaignLine.getCampaignMain().getMinCommodityNum()));
      }
    }

    if (null != campaignLine.getCampaignMain().getOrderLimit()) {
      bean.setOrderLimit(NumUtil.toString(campaignLine.getCampaignMain().getOrderLimit()));
    }
    bean.setRemarks(campaignLine.getCampaignMain().getMemo());

    bean.setDisplayMode(WebConstantCode.DISPLAY_READONLY);
    bean.setDisplayLoginButtonFlg(false);
    bean.setDisplayUpdateButtonFlg(true);

    if (WebConstantCode.DISPLAY_READONLY.equals(bean.getDisplayMode())) {
      bean.setGiftList(getAllGiftCommodity(campaignLine.getCampaignMain().getCampaignCode()));
      bean.setRelatedList(getAllRelatedCommodity(campaignLine.getCampaignMain().getCampaignCode()));
      bean.setPrefectureBeanList(getAllDelivery(campaignLine.getCampaignMain().getCampaignCode()));
      bean.setRelatedNum(NumUtil.toString(bean.getRelatedList().size()));
      bean.setGiftNum(NumUtil.toString(bean.getGiftList().size()));
    }
  }

  // 查询关联商品信息
  private List<RelatedCommodityBean> getAllRelatedCommodity(String code) {
    List<RelatedCommodityBean> list = new ArrayList<RelatedCommodityBean>();
    // loadCampaignLine
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CampaignLine campaignLine = communicationService.loadCampaignLine(code);
    if (campaignLine != null) {
      List<CampaignCondition> cList = campaignLine.getConditionList();
      CampaignCondition condition = null;
      String attributeValue = "";
      if (cList != null && cList.size() > 0) {
        for (int i = 0; i < cList.size(); i++) {
          condition = cList.get(i);
          if (NewCampaignConditionType.ORDER_COMMODITY.longValue().equals(condition.getCampaignConditionType())) {
            attributeValue = condition.getAttributrValue();
            break;
          }
          if (NewCampaignConditionType.COMMODITY_RELATED.longValue().equals(condition.getCampaignConditionType())) {
            attributeValue = condition.getAttributrValue();
            break;
          }
        }
      }
      String[] array = new String[] {};
      if (!StringUtil.isNullOrEmpty(attributeValue)) {
        array = attributeValue.split(",");
      }
      CommodityHeader ommodityHeader = null;
      RelatedCommodityBean relatedCommodityBean = null;
      for (int i = 0; i < array.length; i++) {
        ommodityHeader = catalogService.loadBycommodityCode(getLoginInfo().getShopCode(), array[i], true, false);
        relatedCommodityBean = new RelatedCommodityBean();
        if (ommodityHeader != null) {
          relatedCommodityBean.setRelatedComdtyCode(ommodityHeader.getCommodityCode());
          relatedCommodityBean.setRelatedComdtyName(ommodityHeader.getCommodityName());
          list.add(relatedCommodityBean);
        }

      }
    }
    return list;
  }

  /**
   * 关联商品登录
   * @param
   * @param
   * @return
   */
  private String setRelatedBean(NewCampaignEditBean bean, CampaignLine campaignLine) {
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    CampaignLine line = communicationService.loadCampaignLine(bean.getCampaignCode());
    String attributeValue = "";
    
    if (line != null) {
      List<CampaignCondition> cList = campaignLine.getConditionList();
      CampaignCondition condition = null;

      if (cList != null && cList.size() > 0) {
        for (int i = 0; i < cList.size(); i++) {
          condition = cList.get(i);
          
          if (NewCampaignConditionType.ORDER_COMMODITY.longValue().equals(condition.getCampaignConditionType())) {
            attributeValue = condition.getAttributrValue();
            break;
          }
          
          if (NewCampaignConditionType.COMMODITY_RELATED.longValue().equals(condition.getCampaignConditionType())) {
            attributeValue = condition.getAttributrValue();
            break;
          }
        }
      }
    }
    
    return attributeValue;
  }

  // 判断是否已经存在
  public boolean isExists(NewCampaignEditBean bean, CampaignLine campaignLine) {
    boolean flg = false;
    String attributeValue = setRelatedBean(bean, campaignLine);
    if (StringUtil.isNullOrEmpty(attributeValue)) {
      attributeValue = bean.getRelatedCommodityBean().getRelatedComdtyCode();
    } else {
      String[] array = new String[] {};
      if (!StringUtil.isNullOrEmpty(attributeValue)) {
        array = attributeValue.split(",");
      }
      for (int i = 0; i < array.length; i++) {
        if (bean.getRelatedCommodityBean().getRelatedComdtyCode().equals(array[i])) {
          flg = true;
          break;
        }

      }
    }
    return flg;
  }

  // 拼接
  public String setJoin(NewCampaignEditBean bean, CampaignLine campaignLine) {
    String attributeValue = setRelatedBean(bean, campaignLine);
    if (!StringUtil.isNullOrEmpty(attributeValue)) {
      attributeValue += "," + bean.getRelatedCommodityBean().getRelatedComdtyCode();
    } else {
      attributeValue = bean.getRelatedCommodityBean().getRelatedComdtyCode();
    }
    return attributeValue;
  }

  // 验证添加的信息是否是关联商品
  public boolean isRelated(NewCampaignEditBean bean) {
    boolean flag = false;
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityHeader commodityHeader = catalogService.loadBycommodityCode(getLoginInfo().getShopCode(), 
        bean.getRelatedCommodityBean().getRelatedComdtyCode(), true, false);
    if (commodityHeader == null) {
      flag = true;
    }
    return flag;
  }

  // 验证添加的信息是否在商品表中存在
  public boolean isCommodity(NewCampaignEditBean bean, boolean relatedFlg, boolean giftFlg) {
    boolean flag = false;
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityHeader commodityHeader = null;

    if (relatedFlg) {
      commodityHeader = catalogService.loadBycommodityCode(getLoginInfo().getShopCode(), 
          bean.getRelatedCommodityBean().getRelatedComdtyCode(), true, false);
    }
    if (giftFlg) {
      commodityHeader = catalogService.loadBycommodityCode(getLoginInfo().getShopCode(), 
          bean.getGiftCommodityBean().getGiftComdtyCode(), false, true);
    }

    if (commodityHeader == null) {
      flag = true;
    }
    return flag;
  }

  // 折扣券的关联商品登录时，套餐商品时不可登录
  public boolean isSetCommodity(NewCampaignEditBean bean) {
    boolean flag = false;
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityHeader commodityHeader = catalogService.loadBycommodityCode(getLoginInfo().getShopCode(), bean
        .getRelatedCommodityBean().getRelatedComdtyCode(), true, false);
    if (commodityHeader != null && !SetCommodityFlg.OBJECTIN.longValue().equals(commodityHeader.getSetCommodityFlg())) {
      flag = false;
    } else {
      flag = true;
    }
    return flag;
  }

  // 查询赠品商品信息
  private List<GiftCommodityBean> getAllGiftCommodity(String code) {
    List<GiftCommodityBean> list = new ArrayList<GiftCommodityBean>();
    // loadCampaignLine
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CampaignLine campaignLine = communicationService.loadCampaignLine(code);
    if (campaignLine != null) {
      CampaignDoings doing = campaignLine.getCampaignDoings();
      String attributeValue = "";
      if (doing != null) {
        attributeValue = doing.getAttributrValue();
      }
      String[] array = new String[] {};
      if (!StringUtil.isNullOrEmpty(attributeValue)) {
        array = attributeValue.split(",");
      }
      CommodityHeader ommodityHeader = null;
      GiftCommodityBean giftCommodityBean = null;
      for (int i = 0; i < array.length; i++) {
        ommodityHeader = catalogService.loadBycommodityCode(getLoginInfo().getShopCode(), array[i], false, true);
        giftCommodityBean = new GiftCommodityBean();
        if (ommodityHeader != null) {
          giftCommodityBean.setGiftComdtyCode(ommodityHeader.getCommodityCode());
          giftCommodityBean.setGiftComdtyName(ommodityHeader.getCommodityName());
          list.add(giftCommodityBean);
        }
      }
    }
    return list;
  }

  // 关联赠品登录
  private String setGiftBean(NewCampaignEditBean bean, CampaignLine campaignLine) {
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    CampaignLine line = communicationService.loadCampaignLine(bean.getCampaignCode());
    CampaignDoings doing = new CampaignDoings();
    if (line.getCampaignDoings() != null) {
      doing = campaignLine.getCampaignDoings();
    }
    if (doing != null && !StringUtil.isNullOrEmpty(doing.getCampaignCode())) {
      return doing.getAttributrValue();
    }
    return "";
  }

  public boolean isGiftExists(NewCampaignEditBean bean, CampaignLine campaignLine) {
    boolean flg = false;
    String attributeValue = setGiftBean(bean, campaignLine);
    if (StringUtil.isNullOrEmpty(attributeValue)) {
      attributeValue = bean.getGiftCommodityBean().getGiftComdtyCode();
    } else {
      String[] array = new String[] {};
      if (!StringUtil.isNullOrEmpty(attributeValue)) {
        array = attributeValue.split(",");
      }
      for (int i = 0; i < array.length; i++) {
        if (bean.getGiftCommodityBean().getGiftComdtyCode().equals(array[i])) {
          flg = true;
          break;
        }

      }
    }
    return flg;
  }

  // 验证添加的信息是否是赠送商品
  public boolean isGift(NewCampaignEditBean bean) {
    boolean flag = false;
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityHeader ommodityHeader = catalogService.loadBycommodityCode(getLoginInfo().getShopCode(), bean
        .getGiftCommodityBean().getGiftComdtyCode(), false, true);
    if (ommodityHeader == null) {
      flag = true;
    }
    return flag;
  }

  // 赠品拼接
  public String setGiftJoin(NewCampaignEditBean bean, CampaignLine campaignLine) {
    String attributeValue = setGiftBean(bean, campaignLine);
    if (!StringUtil.isNullOrEmpty(attributeValue)) {
      attributeValue += "," + bean.getGiftCommodityBean().getGiftComdtyCode();
    } else {
      attributeValue = bean.getGiftCommodityBean().getGiftComdtyCode();
    }
    return attributeValue;
  }

  // 赠品登录时 验证 同一赠品不能同时适用于多个特定商品的促销活动
  public boolean isSamePeriod(NewCampaignEditBean bean) {
    boolean flag = false;
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    Date startDate = DateUtil.fromString(bean.getCampaignStartDate(), true);
    Date endDate = DateUtil.setSecond(DateUtil.fromString(bean.getCampaignEndDate()), 59);
    List<GiftCampaign> giftCampaignList = communicationService.getAllGiftCampaign(bean.getCampaignCode(), startDate, endDate);

    GiftCampaign giftCampaign = null;

    for (int i = 0; i < giftCampaignList.size(); i++) {
      giftCampaign = giftCampaignList.get(i);
      String attributeValue = giftCampaign.getAttributrValue();
      if (!StringUtil.isNullOrEmpty(attributeValue)) {
        String[] array = attributeValue.split(",");
        for (int j = 0; j < array.length; j++) {
          if (bean.getGiftCommodityBean().getGiftComdtyCode().equals(array[j])) {
            flag = true;
            break;
          }
        }
        if (flag) {
          break;
        }
      }
    }

    return flag;
  }

  // 更新时验证
  public String validateUpdateSamePeriod(NewCampaignEditBean bean) {
    String sameCommodityCode = "";
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    Date startDate = DateUtil.fromString(bean.getCampaignStartDate(), true);
    Date endDate = DateUtil.setSecond(DateUtil.fromString(bean.getCampaignEndDate()), 59);
    List<GiftCampaign> giftCampaignList = communicationService.getAllGiftCampaign(bean.getCampaignCode(), startDate,
        endDate);
    // 取出赠品编号并拼接成集合
    CampaignLine campaignLine = communicationService.loadCampaignLine(bean.getCampaignCode());
    String[] strArray = new String[] {};
    List<String> arrayToList = new ArrayList<String>();
    if (campaignLine.getCampaignDoings() != null) {
      strArray = campaignLine.getCampaignDoings().getAttributrValue().split(",");
      arrayToList = Arrays.asList(strArray);
    }

    GiftCampaign giftCampaign = null;
    for (int i = 0; i < giftCampaignList.size(); i++) {
      giftCampaign = giftCampaignList.get(i);
      String attributeValue = giftCampaign.getAttributrValue();
      if (!StringUtil.isNullOrEmpty(attributeValue)) {
        String[] array = attributeValue.split(",");

        for (int a = 0; a < array.length; a++) {
          for (int b = 0; b < arrayToList.size(); b++) {
            if (array[a].equals(arrayToList.get(b))) {
              if (StringUtil.isNullOrEmpty(sameCommodityCode)) {
                sameCommodityCode += arrayToList.get(b);
              } else {
                if (!sameCommodityCode.contains(arrayToList.get(b)))
                  sameCommodityCode += "," + arrayToList.get(b);
              }
            }
          }

        }

      }
    }

    return sameCommodityCode;
  }

  // 查询配送地址信息
  private List<PrefectureBean> getAllDelivery(String code) {
    List<PrefectureBean> list = new ArrayList<PrefectureBean>();
    List<Prefecture> prefectureList = new ArrayList<Prefecture>();

    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    prefectureList = communicationService.loadAll();
    CampaignLine campaignLine = communicationService.loadCampaignLine(code);
    if (campaignLine != null) {
      List<CampaignCondition> cList = campaignLine.getConditionList();
      CampaignCondition condition = null;
      String attributeValue = "";
      if (cList != null && cList.size() > 0) {
        for (int i = 0; i < cList.size(); i++) {
          condition = cList.get(i);
          if (NewCampaignConditionType.ORDER_ADDRESS.getValue().equals(condition.getCampaignConditionType() + "")) {
            attributeValue = condition.getAttributrValue();
            break;
          }
        }
      }
      String[] array = new String[] {};
      List<String> arrayToList = new ArrayList<String>();
      if (!StringUtil.isNullOrEmpty(attributeValue)) {
        array = attributeValue.split(",");
        arrayToList = Arrays.asList(array);
      }
      Prefecture prefecture = null;
      PrefectureBean prefectureBean = null;

      for (int j = 0; j < prefectureList.size(); j++) {
        prefecture = prefectureList.get(j);
        prefectureBean = new PrefectureBean();
        if (prefecture != null) {
          prefectureBean.setPrefectureCode(prefecture.getPrefectureCode());
          prefectureBean.setRegionBlockName(prefecture.getPrefectureName());
        }
        for (int i = 0; i < arrayToList.size(); i++) {

          if (prefectureList.get(j).getPrefectureCode().equals(arrayToList.get(i))) {
            prefectureBean.setSelected(true);
            break;
          } else {
            prefectureBean.setSelected(false);
          }

        }
        list.add(prefectureBean);
      }

    }
    return list;
  }

}
