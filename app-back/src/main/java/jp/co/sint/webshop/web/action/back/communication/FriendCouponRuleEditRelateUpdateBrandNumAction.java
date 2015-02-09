package jp.co.sint.webshop.web.action.back.communication;


import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Quantity;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.dto.FriendCouponRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.FriendCouponRuleEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1060320:キャンペーンマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class FriendCouponRuleEditRelateUpdateBrandNumAction extends FriendCouponRuleEditBaseAction {

  
  @Quantity
  @Range(min = 0, max = 9999999)
  @Metadata(name = "数量")
  private String selectLimitNum;
  
  
  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();

    // ショップ管理者で更新権限のあるユーザか、サイト管理者で更新権限があり、かつ一店舗モードの
    // 時のみアクセス可能
    boolean auth = Permission.FriendCouponRule_UPDATE_SHOP.isGranted(login);

    if (getRequestParameter().getPathArgs() != null && getRequestParameter().getPathArgs().length > 0) {
      if ("delete".equals(getRequestParameter().getPathArgs()[0])) {
        auth = Permission.FriendCouponRule_DELETE_SHOP.isGranted(login);
      }
    }

    return auth;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  public boolean validate() {
    String brandCode = getRequestParameter().getPathArgs()[0];
    selectLimitNum = getRequestParameter().get("brandNum"+"_"+brandCode);
    if (!validateBean(this)) {
      return false;
    }
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  public WebActionResult callService() {
    FriendCouponRuleEditBean bean = getBean();
    FriendCouponRule rule = new FriendCouponRule();
    // 为dto设置值
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    ServiceResult result = null;
    
    if(StringUtil.isNullOrEmpty(selectLimitNum)){
      selectLimitNum = "";
    }
    
    String brandCode = getRequestParameter().getPathArgs()[0];
    if (getRequestParameter().getPathArgs() != null && getRequestParameter().getPathArgs().length > 0) {
      rule = communicationService.selectFriendCouponRule(bean.getFriendCouponRuleNo());
      if (rule != null) {
        String str = "";
        for (int i = 0; i < bean.getBrandDetailBeanList().size(); i++) {
          
          if (brandCode.equals(bean.getBrandDetailBeanList().get(i).getBrandCode())) {
            bean.getBrandDetailBeanList().get(i).setLimitedNum(selectLimitNum);
          }

          if (StringUtil.isNullOrEmpty(str)) {
            str += bean.getBrandDetailBeanList().get(i).getBrandCode() + ":" + bean.getBrandDetailBeanList().get(i).getLimitedNum();
          } else {
            str += ";" + bean.getBrandDetailBeanList().get(i).getBrandCode() + ":"
                + bean.getBrandDetailBeanList().get(i).getLimitedNum();
          }
        }
        rule.setObjectBrand(str);
        result = communicationService.updateFriendCouponRule(rule);
        
        if (result.hasError()) {
          this.addErrorMessage(WebMessage.get(CommunicationErrorMessage.UPDATE_FAILED_ERROR, "关联品牌"));
          return BackActionResult.RESULT_SUCCESS;
        } else {
          setNextUrl("/app/communication/friend_coupon_rule_edit/init/" + bean.getFriendCouponRuleNo() +"/relatedUpdate/succeed");
        }
      }
    }

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.bean.back.communication.NewCampaignEditInitAction.005");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106102006";
  }

  
  /**
   * @return the selectCommodityRank
   */
  public String getSelectCommodityRank() {
    return selectLimitNum;
  }

  
  /**
   * @param selectCommodityRank the selectCommodityRank to set
   */
  public void setSelectCommodityRank(String selectCommodityRank) {
    this.selectLimitNum = selectCommodityRank;
  }

  
  /**
   * @return the selectLimitNum
   */
  public String getSelectLimitNum() {
    return selectLimitNum;
  }

  
  /**
   * @param selectLimitNum the selectLimitNum to set
   */
  public void setSelectLimitNum(String selectLimitNum) {
    this.selectLimitNum = selectLimitNum;
  }

}
