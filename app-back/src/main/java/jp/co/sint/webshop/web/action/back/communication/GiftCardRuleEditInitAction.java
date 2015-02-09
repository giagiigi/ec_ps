package jp.co.sint.webshop.web.action.back.communication;


import jp.co.sint.webshop.data.dto.GiftCardRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.GiftCardRuleEditBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

public class GiftCardRuleEditInitAction extends GiftCardRuleEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {

    if (super.authorize()) {
      
      BackLoginInfo login = getLoginInfo();
      
      //注册的时候
      if(getRequestParameter().getPathArgs().length == 0 
          && Permission.GIFT_CARD_RULE_READ_SHOP.isGranted(getLoginInfo()) == true
          && Permission.GIFT_CARD_RULE_UPDATE_SHOP.isGranted(getLoginInfo()) == false){
        return false;
      }
      
      // 如果有查看权限
      if (Permission.GIFT_CARD_RULE_READ_SHOP.isGranted(login)) {
        return true;
      }

    }
    
    return false;
  }
  
  public void prerender() {
    BackLoginInfo login = getLoginInfo();
    GiftCardRuleEditBean bean = getBean();
    // 如果只有查看权限,页面不可编辑,不显示更新和注册按钮
    if (Permission.GIFT_CARD_RULE_UPDATE_SHOP.isGranted(login) == false) {
      bean.setDisplayRegistButtonFlg(false);
      bean.setDisplayUpdateButtonFlg(false);
      bean.setDisplayExportButtonFlg(false);
    }
    
    if (Permission.GIFT_CARD_RULE_UPDATE_SHOP.isGranted(login) == true) {
      bean.setDisplayRegistButtonFlg(true);
      bean.setDisplayUpdateButtonFlg(true);
    }
    
    if (Permission.GIFT_CARD_RULE_IO.isGranted(login) == true) {
      bean.setDisplayExportButtonFlg(true);
      
    }
    
    setBean(bean);
    setRequestBean(getBean());
  
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
    String[] param = getRequestParameter().getPathArgs();
    GiftCardRule giftcardrule = new GiftCardRule();
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    
 // 更新或注册的情况下,显示信息
    if (getRequestParameter().getPathArgs().length == 2) {
      // 获得礼品卡编号
      String cardCode = getRequestParameter().getPathArgs()[1];
      if (getBean() != null) {
        // 显示提示信息 
        setCompleteMessage(cardCode);
      }
      // 将数据显示到页面
      showToBean(cardCode);
      setRequestBean(getBean());
      setBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
      //更新的情况下
    }
    if (getRequestParameter().getPathArgs().length == 1 || getRequestParameter().getPathArgs().length==3){
      
      if(getRequestParameter().getPathArgs().length==3 && !StringUtil.isNullOrEmpty(getRequestParameter().getPathArgs()[2]) && getRequestParameter().getPathArgs()[2].equals("succeed")){
          giftcardrule = communicationService.getGiftCardRule(getBean().getCardCode());
        if (giftcardrule == null) {
          throw new URLNotFoundException();

        } else {
          if  ("insert".equals(param[1])) {
            this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.REGISTER_SUCCESS_INFO));
          } else if ("update".equals(param[1])) {
            this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.DELETE_SUCCESS_INFO));
          } 
        }
      }
      
      String cardCode = getRequestParameter().getPathArgs()[0];
  
      // 将数据显示到页面
      showToBean(cardCode);
    
    }else{

    // 页面初期化设置
      initPageSet();
    }
    setRequestBean(getBean());
    setBean(getBean());
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
      return Messages.getString("web.action.back.communication.GiftCardRuleEditInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
      return "5106141003";
   }

}
