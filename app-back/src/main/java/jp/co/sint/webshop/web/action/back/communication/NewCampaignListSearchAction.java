package jp.co.sint.webshop.web.action.back.communication;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.data.domain.CampaignMainType;
import jp.co.sint.webshop.data.dto.CampaignMain;
import jp.co.sint.webshop.service.communication.CampaignSearchCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.NewCampaignListBean;
import jp.co.sint.webshop.web.bean.back.communication.NewCampaignListBean.CampaignDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * 5106101003:促销活动管理のアクションクラスです
 * 
 * @author KS.
 */
public class NewCampaignListSearchAction extends WebBackAction<NewCampaignListBean> {
	
  private CampaignSearchCondition condition;

  protected CampaignSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected CampaignSearchCondition getSearchCondition() {
    return this.condition;
  }

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    condition = new CampaignSearchCondition();
  }
  
  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    if (Permission.CAMPAIGN_READ_SHOP.isGranted(getLoginInfo())
        || Permission.CAMPAIGN_READ_SITE.isGranted(getLoginInfo())) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    ValidationSummary summary = BeanValidator.validate(getBean());
    if (summary.hasError()) {
      getDisplayMessage().getErrors().addAll(summary.getErrorMessages());
      return false;
    }

    condition = getCondition();
    // 日付の大小関係チェック
    condition.setCampaignStartDateFrom(getBean().getCampaignStartDateFrom());
    condition.setCampaignEndDateTo(getBean().getCampaignEndDateTo());

    if (condition.isValid()) {
      return true;
    } else {
      if (StringUtil.hasValueAllOf(condition.getCampaignStartDateFrom(), condition.getCampaignEndDateTo())) {
        if (!StringUtil.isCorrectRange(condition.getCampaignStartDateFrom(), condition.getCampaignEndDateTo())) {
          addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM, "活动期间"));
        }
      }
    }

    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    NewCampaignListBean bean = getBean();
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    List<CampaignDetailBean> detailList = new ArrayList<CampaignDetailBean>();
    
    // 設置查詢條件
    condition = getCondition();
    condition.setCampaignCode(bean.getCampaignCode());
    condition.setCampaignName(bean.getCampaignName());
    condition.setCampaignNameEn(bean.getCampaignNameEn());
    condition.setCampaignNameJp(bean.getCampaignNameJp());
    condition.setCampaignStartDateFrom(bean.getCampaignStartDateFrom());
    condition.setCampaignEndDateTo(bean.getCampaignEndDateTo());
    condition.setCampaignType(bean.getCampaignType());
    condition.setSearchCampaignStatus(bean.getSearchCampaignStatus());
    SearchResult<CampaignMain> result = service.getCampaignList(condition);
    
    // オーバーフローチェック
    prepareSearchWarnings(result, SearchWarningType.BOTH);
    
    // 清空List
    bean.getList().clear();
    
    // 將查詢出的数据保存在BEAN中
    for (CampaignMain campaignMain : result.getRows()) {
      CampaignDetailBean detail = new CampaignDetailBean();
      detail.setCampaignCode(campaignMain.getCampaignCode());
      detail.setCampaignName(campaignMain.getCampaignName());
      
      // 活动类型下拉列表值的判斷
      if (CampaignMainType.SHIPPING_CHARGE_FREE.longValue().equals(campaignMain.getCampaignType())) {
        detail.setCampaignType(CampaignMainType.SHIPPING_CHARGE_FREE.getName());
      } else if (CampaignMainType.SALE_OFF.longValue().equals(campaignMain.getCampaignType())) {
        detail.setCampaignType(CampaignMainType.SALE_OFF.getName());
      } else if (CampaignMainType.GIFT.longValue().equals(campaignMain.getCampaignType())) {
        detail.setCampaignType(CampaignMainType.GIFT.getName());
      } else if (CampaignMainType.MULTIPLE_GIFT.longValue().equals(campaignMain.getCampaignType())) {
        detail.setCampaignType(CampaignMainType.MULTIPLE_GIFT.getName());
      }
      
      detail.setCampaignStartDate(DateUtil.toDateTimeString(campaignMain.getCampaignStartDate()));
      detail.setCampaignEndDate(DateUtil.toDateTimeString(campaignMain.getCampaignEndDate()));
      
      detailList.add(detail);
    }
    
    bean.setPagerValue(PagerUtil.createValue(result));
    bean.setList(detailList);
    
    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "促销活动一览画面检索处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106101003";
   }

}
