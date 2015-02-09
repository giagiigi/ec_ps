package jp.co.sint.webshop.web.action.back.communication;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.ActivityStatus;
import jp.co.sint.webshop.data.domain.CampaignMainType;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.NewCampaignListBean;

/**
 * 5106101001:促销活动管理のアクションクラスです
 * 
 * @author KS.
 */
public class NewCampaignListInitAction extends WebBackAction<NewCampaignListBean> {

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
    return true;
  }

  public void init() {
    NewCampaignListBean bean = new NewCampaignListBean();
    setBean(bean);
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    NewCampaignListBean bean = getBean();
    List<CodeAttribute> campaignDetailTypeList = new ArrayList<CodeAttribute>();

    // 新建按钮显示设定
    if (Permission.CAMPAIGN_UPDATE_SHOP.isGranted(getLoginInfo())
        || Permission.CAMPAIGN_UPDATE_SITE.isGranted(getLoginInfo())) {
      bean.setUpdateAuthorizeFlg(true);
    } else {
      bean.setUpdateAuthorizeFlg(false);
    }

    // 删除按钮显示设定
    if (Permission.CAMPAIGN_DELETE_SHOP.isGranted(getLoginInfo())
        || Permission.CAMPAIGN_DELETE_SITE.isGranted(getLoginInfo())) {
      bean.setDeleteAuthorizeFlg(true);
    } else {
      bean.setDeleteAuthorizeFlg(false);
    }

    // 活动进行状态初期设定
    bean.setSearchCampaignStatus(ActivityStatus.IN_PROGRESS.getValue());

    // 活动类型下拉框选项设定
    campaignDetailTypeList.add(new NameValue("全部", ""));
    for (CampaignMainType type : CampaignMainType.values()) {
      campaignDetailTypeList.add(type);
    }

    bean.setCampaignDetailTypeList(campaignDetailTypeList);

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "促销活动一览画面初期表示处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106101001";
  }
}
