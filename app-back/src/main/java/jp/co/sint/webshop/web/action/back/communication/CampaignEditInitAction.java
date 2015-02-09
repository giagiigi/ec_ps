package jp.co.sint.webshop.web.action.back.communication;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.DataIOService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.data.ContentsSearchCondition;
import jp.co.sint.webshop.service.data.ContentsType;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.UploadResult;
import jp.co.sint.webshop.web.bean.UploadSubBean;
import jp.co.sint.webshop.web.bean.UploadSubBean.ResultType;
import jp.co.sint.webshop.web.bean.back.communication.CampaignEditBean;
import jp.co.sint.webshop.web.config.WebFrameworkConstants;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

import org.apache.log4j.Logger;

/**
 * U1060320:キャンペーンマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CampaignEditInitAction extends WebBackAction<CampaignEditBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();

    // ショップ管理者で更新権限のあるユーザか、サイト管理者で更新権限があり、かつ一店舗モードの
    // 時のみアクセス可能
    boolean auth = Permission.CAMPAIGN_UPDATE_SHOP.isGranted(login)
        || (Permission.CAMPAIGN_UPDATE_SITE.isGranted(login) && getConfig().isOne());

    if (login.isShop() && getRequestParameter().getPathArgs().length > 0) {

      String shopCode = getRequestParameter().getPathArgs()[0];
      if (StringUtil.isNullOrEmpty(shopCode)) {
        return false;
      }

      auth &= shopCode.equals(login.getShopCode());
    }

    return auth;
  }

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

    Logger logger = Logger.getLogger(this.getClass());
    String[] args = getRequestParameter().getPathArgs();

    CampaignEditBean bean = getBean();

    if (args.length > 1) { // 更新時

      String shopCode = args[0];
      String campaignCode = args[1];
      logger.debug("shopCode = " + shopCode + ", campaignCode = " + campaignCode);

      CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
      Campaign campaign = service.getCampaign(shopCode, campaignCode);

      if (campaign != null) {
        bean.setShopCode(campaign.getShopCode());
        bean.setCampaignCode(campaign.getCampaignCode());
        bean.setCampaignName(campaign.getCampaignName());
        bean.setCampaignNameEn(campaign.getCampaignNameEn());
        bean.setCampaignNameJp(campaign.getCampaignNameJp());
        bean.setMemo(campaign.getMemo());
        bean.setCampaignDiscountRate(Long.toString(campaign.getCampaignDiscountRate()));
        bean.setCampaignStartDate(DateUtil.toDateString(campaign.getCampaignStartDate()));
        bean.setCampaignEndDate(DateUtil.toDateString(campaign.getCampaignEndDate()));
        bean.setUpdateDatetime(campaign.getUpdatedDatetime());
        bean.setRegisterActionFlg(false);
        bean.setReadonlyMode(WebConstantCode.DISPLAY_READONLY);
        bean.setPreviewButtonFlg(getPreviewButtonFlg(campaign.getShopCode(), campaign.getCampaignCode()));
        bean.setDisplayAssociateCommodityButton(true);
        // 更新時はファイルアップロードエリアを常に表示
        bean.setDisplayFileUploadArea(true);
        if (DIContainer.getWebshopConfig().isOne()) {
          bean.setOperatingModeIsOne(true);
        }
      } else {
        bean = new CampaignEditBean();
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.back.communication.CampaignEditInitAction.0")));
        bean.setDisplayAssociateCommodityButton(false);
        bean.setDisplayFileUploadArea(false);
        bean.setShopCode(getLoginInfo().getShopCode());
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }

      if (getBean().getSubBeanMap().get(WebFrameworkConstants.ATTRIBUTE_UPLOAD_BEAN) != null) {
        UploadSubBean messageBean = (UploadSubBean) getBean().getSubBeanMap().get(WebFrameworkConstants.ATTRIBUTE_UPLOAD_BEAN);
        List<UploadResult> resultList = new ArrayList<UploadResult>();
        if (messageBean != null) {
          resultList = messageBean.getUploadDetailList();
          if (messageBean.getResult().equals(ResultType.SUCCESS)) {
            addInformationMessage(WebMessage.get(CompleteMessage.UPLOAD_COMPLETE,
                Messages.getString("web.action.back.communication.CampaignEditInitAction.1")));
          } else {
            addErrorMessage(WebMessage.get(ActionErrorMessage.UPLOAD_FAILED,
                Messages.getString("web.action.back.communication.CampaignEditInitAction.1")));
          }
        }

        for (UploadResult ur : resultList) {

          for (String s : ur.getInformationMessage()) {
            addErrorMessage(s);
          }
          for (String s : ur.getWarningMessage()) {
            addErrorMessage(s);
          }
          for (String s : ur.getErrorMessage()) {
            addErrorMessage(s);
          }
        }
        // メッセージを表示したら削除する
        getBean().getSubBeanMap().remove(WebFrameworkConstants.ATTRIBUTE_UPLOAD_BEAN);
      }

    } else { // 新規登録時
      // 新規のときは関連付けボタン、ファイルアップロードエリア非表示
      bean = new CampaignEditBean();
      bean.setDisplayAssociateCommodityButton(false);
      bean.setDisplayFileUploadArea(false);
      bean.setShopCode(getLoginInfo().getShopCode());
      bean.setReadonlyMode(WebConstantCode.DISPLAY_EDIT);
    }

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * プレビューボタンの表示制御を行います<BR>
   * キャンペーンのコンテンツが存在する場合は表示、存在しない場合は非表示とします
   * 
   * @param shopCode
   * @param campaignCode
   * @return contents.contentsExists(condition)
   */
  private boolean getPreviewButtonFlg(String shopCode, String campaignCode) {
    // キャンペーンコンテンツ(index.html)のパスを取得
    ContentsSearchCondition condition = new ContentsSearchCondition();
    DataIOService contents = ServiceLocator.getDataIOService(getLoginInfo());
    if (DIContainer.getWebshopConfig().isOne()) {
      condition.setContentsType(ContentsType.CONTENT_SHOP_CAMPAIGN_SITE);
    } else {
      condition.setContentsType(ContentsType.CONTENT_SHOP_CAMPAIGN_SHOP);
    }
    condition.setShopCode(shopCode);
    condition.setCampaignCode(campaignCode);

    // コンテンツの存在チェック
    return contents.contentsExists(condition);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.CampaignEditInitAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106032001";
  }

}
