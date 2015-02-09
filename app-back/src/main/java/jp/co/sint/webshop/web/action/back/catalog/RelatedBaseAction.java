package jp.co.sint.webshop.web.action.back.catalog;

import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.RelatedBaseEvent;
import jp.co.sint.webshop.service.catalog.RelatedSearchConditionBaseEvent;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedBean;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedBean.RelatedDetailBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040160:関連付けアクションの基底クラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class RelatedBaseAction extends WebBackAction<RelatedBean> {

  private static final String COMMODITY_A = "commodity_a";

  private static final String COMMODITY_B = "commodity_b";

  private static final String TAG = "tag";

  private static final String CAMPAIGN = "campaign";

  private static final String GIFT = "gift";

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public abstract boolean authorize();

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public abstract WebActionResult callService();

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public abstract boolean validate();

  /**
   * @param reqBean
   * @return condition
   */
  public RelatedSearchConditionBaseEvent setSearchCondition(RelatedBean reqBean) {
    RelatedSearchConditionBaseEvent condition = new RelatedSearchConditionBaseEvent();
    condition.setShopCode(reqBean.getSearchShopCode());
    condition.setEffectualCode(reqBean.getEffectualCode());
    condition.setSearchCommodityCodeStart(reqBean.getSearchCommodityCodeStart());
    condition.setSearchCommodityCodeEnd(reqBean.getSearchCommodityCodeEnd());
    condition.setSearchCommodityName(reqBean.getSearchCommodityName());
    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);
    return condition;
  }

  /**
   * @param relatedBean
   * @param relatedList
   */
  public void setNextBean(RelatedBean relatedBean, List<RelatedBaseEvent> relatedList) {
    for (RelatedBaseEvent rt : relatedList) {
      RelatedDetailBean detail = new RelatedDetailBean();
      detail.setShopCode(rt.getShopCode());
      detail.setCommodityCode(rt.getCommodityCode());
      detail.setCommodityName(rt.getCommodityName());
      detail.setAppliedCampaignName(rt.getAppliedCampaign());
      detail.setDisplayOrder(rt.getDisplayOrder());
      // 20130805 txw add start
      if (rt.getSortNum() != null) {
        detail.setSortNum(NumUtil.toString(rt.getSortNum()));
      }
      if (rt.getSortNumJp() != null) {
        detail.setSortNumJp(NumUtil.toString(rt.getSortNumJp()));
      }
      if (rt.getSortNumEn() != null) {
        detail.setSortNumEn(NumUtil.toString(rt.getSortNumEn()));
      }
      detail.setLangTab(rt.getLang());
      // 20130805 txw add end

      if (rt.isInteraction()) {
        detail.setInteraction(Messages.getString("web.action.back.catalog.RelatedBaseAction.0"));
      } else {
        detail.setInteraction(Messages.getString("web.action.back.catalog.RelatedBaseAction.1"));
      }
      detail.setUpdatedDatetime(rt.getUpdatedDatetime());
      relatedBean.getList().add(detail);
    }
  }

  /**
   * @param relatedBean
   */
  public void setDisplayControl(RelatedBean relatedBean) {
    if ((Permission.COMMODITY_UPDATE.isGranted(getLoginInfo()) && relatedBean.getPictureMode().equals(COMMODITY_A))
        || (Permission.COMMODITY_UPDATE.isGranted(getLoginInfo()) && relatedBean.getPictureMode().equals(TAG))
        || (Permission.COMMODITY_UPDATE.isGranted(getLoginInfo()) && relatedBean.getPictureMode().equals(GIFT))
        || (((Permission.CAMPAIGN_UPDATE_SITE.isGranted(getLoginInfo()) && getConfig().isOne()) || Permission.CAMPAIGN_UPDATE_SHOP
            .isGranted(getLoginInfo())) && relatedBean.getPictureMode().equals(CAMPAIGN))) {
      relatedBean.setRegisterTableDisplayFlg(true);
    } else {
      relatedBean.setRegisterTableDisplayFlg(false);
    }

    if ((Permission.COMMODITY_UPDATE.isGranted(getLoginInfo()) && relatedBean.getPictureMode().equals(COMMODITY_A))
        || (Permission.COMMODITY_UPDATE.isGranted(getLoginInfo()) && relatedBean.getPictureMode().equals(TAG))
        || (((Permission.CAMPAIGN_UPDATE_SITE.isGranted(getLoginInfo()) && getConfig().isOne()) || Permission.CAMPAIGN_UPDATE_SHOP
            .isGranted(getLoginInfo())) && relatedBean.getPictureMode().equals(CAMPAIGN))) {
      relatedBean.setUpdateTableDisplayFlg(relatedBean.getList().size() > 0);
    } else {
      relatedBean.setUpdateTableDisplayFlg(false);
    }

    if ((Permission.COMMODITY_DELETE.isGranted(getLoginInfo()) && !relatedBean.getPictureMode().equals(COMMODITY_B))
        || (((Permission.CAMPAIGN_DELETE_SITE.isGranted(getLoginInfo()) && getConfig().isOne()) || Permission.CAMPAIGN_DELETE_SHOP
            .isGranted(getLoginInfo())) && relatedBean.getPictureMode().equals(CAMPAIGN))) {
      relatedBean.setDeleteTableDisplayFlg(relatedBean.getList().size() > 0);
    } else {
      relatedBean.setDeleteTableDisplayFlg(false);
    }

    if ((Permission.COMMODITY_DATA_IO.isGranted(getLoginInfo()) && !relatedBean.getPictureMode().equals(COMMODITY_B))
        || (((Permission.CAMPAIGN_DATA_IO_SITE.isGranted(getLoginInfo()) && getConfig().isOne()) || Permission.CAMPAIGN_DATA_IO_SHOP
            .isGranted(getLoginInfo())) && relatedBean.getPictureMode().equals(CAMPAIGN))) {
      relatedBean.setImportTableDisplayFlg(true);
    } else {
      relatedBean.setImportTableDisplayFlg(false);
    }

    if (Permission.COMMODITY_UPDATE.isGranted(getLoginInfo()) && relatedBean.getPictureMode().equals(COMMODITY_A)) {
      relatedBean.setEditMode(WebConstantCode.DISPLAY_EDIT);
    } else {
      relatedBean.setEditMode(WebConstantCode.DISPLAY_READONLY);
    }
  }

  /**
   * @return shopName
   */
  public String getShopName() {
    String shopName = "";
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    List<CodeAttribute> shopList = utilService.getShopNames(false);

    if (getLoginInfo().isShop()) {
      shopName = getLoginInfo().getShopName();

    } else {
      for (CodeAttribute cb : shopList) {
        if (cb.getValue().equals(getBean().getSearchShopCode())) {
          shopName = cb.getName();
          break;
        }
      }
    }
    return shopName;
  }
}
