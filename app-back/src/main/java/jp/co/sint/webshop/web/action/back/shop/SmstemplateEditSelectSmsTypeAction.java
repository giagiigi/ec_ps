package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.SmsType;
import jp.co.sint.webshop.data.dto.SmsTemplateDetail;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.shop.SmsTemplateSuite;
import jp.co.sint.webshop.sms.SmsComposition;
import jp.co.sint.webshop.sms.SmsTemplateTag;
import jp.co.sint.webshop.sms.SmsTemplateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.SmstemplateEditBean;
import jp.co.sint.webshop.web.bean.back.shop.SmstemplateEditBean.SmsCompositionDetail;
import jp.co.sint.webshop.web.bean.back.shop.SmstemplateEditBean.SmsTag;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * 
 * @author System Integrator Corp.
 */
public class SmstemplateEditSelectSmsTypeAction extends WebBackAction<SmstemplateEditBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    LoginInfo login = getLoginInfo();
    return (Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(login) || Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(login));
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
    SmstemplateEditBean bean = getBean();

    String smsType = "";
    if(StringUtil.isNotNull(bean.getSmsType())){
      smsType = bean.getSmsType();
      ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
      SmsTemplateSuite suite = service.getSmsTemplateConfig(smsType);
      List<SmsTemplateDetail> detailList = suite.getSmsTemplateDetail();
      if (detailList == null || detailList.size() <= 0) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.back.shop.SmstemplateEditSelectSmsTypeAction.0")));
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }
      
      List<SmsCompositionDetail> cDetailList = new ArrayList<SmsCompositionDetail>();

      int i = 0;
      // saikou 2011/12/28 ob add start
      if (SmsType.COUPON_END.getValue().equals(bean.getSmsType())) {
    	SmsCompositionDetail detail = copyDetailToComposition(detailList.get(0), SmsComposition.COUPON_END_MAIN.getSubstitutionTag());
        detail.setBranchNo(1);
        detail.setSmsCompositionName(SmsComposition.COUPON_END_MAIN.getName());
        cDetailList.add(detail);
	  } else if(SmsType.COUPON_START.getValue().equals(bean.getSmsType())){
	    SmsCompositionDetail detail = copyDetailToComposition(detailList.get(0), SmsComposition.COUPON_START_MAIN.getSubstitutionTag());
        detail.setBranchNo(1);
        detail.setSmsCompositionName(SmsComposition.COUPON_START_MAIN.getName());
        cDetailList.add(detail);
	  } else {
	  // saikou 2011/12/28 ob add end
        for (SmsComposition sc : SmsComposition.getShippingTag()) {
          SmsCompositionDetail detail = copyDetailToComposition(detailList.get(0), sc.getSubstitutionTag());
          detail.setBranchNo(i);
          detail.setSmsCompositionName(sc.getName());
          i++;
          cDetailList.add(detail);
        }
	  }
     
      bean.setSmsText(detailList.get(0).getSmsText());
      //Add by V10-CH end
      bean.setSmsTypeName(SmsType.fromValue(smsType).getName());
      bean.setModeDiv("edit");
      bean.setSmsDetailList(cDetailList);
      bean.setUpdateDatetime(detailList.get(0).getUpdatedDatetime());
      bean.setSmsUseFlgDiv(detailList.get(0).getSmsUseFlg().toString());
      setRequestBean(bean);
    }else{
      bean.setSmsDetailList(new ArrayList<SmsCompositionDetail>());
      bean.setModeDiv("init");
      setRequestBean(bean);
    }
    
    return BackActionResult.RESULT_SUCCESS;
  }

  //Add by V10-CH start
  private SmsCompositionDetail copyDetailToComposition(SmsTemplateDetail detail, String substitutionTag) {
    SmsCompositionDetail c = new SmsCompositionDetail();
    c.setSmsCompositionName(detail.getSmsCompositionName());
    c.setTemplateTag(detail.getTemplateTag());
    List<SmsTag> tagList = new ArrayList<SmsTag>();
    for (SmsTemplateTag tag : SmsTemplateUtil.getUsableTagList(SmsType.fromValue(detail.getSmsType()), substitutionTag)) {
      SmsTag composition = new SmsTag();  
      composition.setName(tag.getName());
      composition.setValue(tag.getValue());
      composition.setRequired(tag.isRequired());
      composition.setTagDiv(tag.getTagDiv());
      composition.setDummyData(tag.getDummyData());
      tagList.add(composition);
    }

    if (SmsTemplateUtil.getUsableTagList(SmsType.fromValue(detail.getSmsType()), substitutionTag).size() == 0) {
      SmsTag composition = new SmsTag();
      composition.setName(Messages.getString("web.action.back.shop.SmstemplateEditSelectSmsTypeAction.1"));
      composition.setValue("");
      composition.setRequired(false);
      tagList.add(composition);
    }
    c.setSmsType(detail.getSmsType());
    c.setSmsTagList(tagList);
    c.setSmsText(detail.getSmsText());
    c.setUpdateDatetime(detail.getUpdatedDatetime());
    c.setSmsUseFlg(detail.getSmsUseFlg());
    return c;
  }
  //Add by V10-CH end
  
  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
//  public void prerender() {
//    SmstemplateEditBean bean = (SmstemplateEditBean) getRequestBean();
//    LoginInfo login = getLoginInfo();
//    if (Permission.SHOP_MANAGEMENT_DELETE_SITE.isGranted(login)) {
//      bean.setDisplayDeleteButton(true);
//    } else {
//      bean.setDisplayDeleteButton(false);
//    }
//    bean.setDisplayInsertButton(false);
//    setRequestBean(bean);
//  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.SmstemplateEditSelectSmsTypeAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105111007";
  }

}
