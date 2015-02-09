package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.dto.SmsTemplateDetail;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.shop.SmsTemplateSuite;
import jp.co.sint.webshop.sms.SmsTagDataList;
import jp.co.sint.webshop.sms.SmsTemplateExpander;
import jp.co.sint.webshop.sms.SmsTemplateTag;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.SmstemplateEditBean;
import jp.co.sint.webshop.web.bean.back.shop.SmstemplateEditBean.SmsCompositionDetail;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.shop.ShopErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

import org.apache.log4j.Logger;

/**
 * メールテンプレートプレビュー処理<BR>
 * 項目タグにEnumで指定されたDummy情報を設定し<BR>
 * 構造タグを展開してプレビュー用HTMLを生成する<BR>
 * U1051110:メールテンプレートのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class SmstemplateEditPreviewAction extends WebBackAction<SmstemplateEditBean> {

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    getBean().setModeDiv("edit");
  }

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
    SmstemplateEditBean bean = getBean();
    boolean valid = validateBean(bean);
    for (SmsCompositionDetail detail : bean.getSmsDetailList()) {
      if (validateBean(detail)) {
        if (valid) {
          valid = true;
        }
      } else {
        valid = false;
        break;
      }
      // 項目タグ必須チェック
      for (CodeAttribute tag : detail.getSmsTagList()) {
        if (tag instanceof SmsTemplateTag) {
          SmsTemplateTag smsTag = (SmsTemplateTag) tag;
          if (smsTag.isRequired() && !detail.getSmsText().contains(smsTag.getValue())) {
            valid &= false;
            addErrorMessage(WebMessage.get(ShopErrorMessage.REQUIRED_TAG, "文本", smsTag.getName()));
            Logger logger = Logger.getLogger(this.getClass());
            logger.debug(WebMessage.get(ShopErrorMessage.REQUIRED_TAG, "文本", smsTag.getName()));
          }
        }
        // 構造タグ必須チェック
        if (tag instanceof NameValue && StringUtil.hasValue(tag.getValue())) {
          if (!detail.getSmsText().contains(tag.getValue())) {
            valid &= false;
            addErrorMessage(WebMessage.get(ShopErrorMessage.REQUIRED_TAG, "文本", tag.getName()));
            Logger logger = Logger.getLogger(this.getClass());
            logger.debug(WebMessage.get(ShopErrorMessage.REQUIRED_TAG, "文本", tag.getName()));
          }
        }
      }
    }
    return valid;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    SmstemplateEditBean bean = getBean();
    String previewText = "";

    // テンプレート展開用のDTO作成
    SmsTemplateSuite suite = new SmsTemplateSuite();

    List<SmsTemplateDetail> detailList = new ArrayList<SmsTemplateDetail>();
    for (SmsCompositionDetail d : bean.getSmsDetailList()) {
      SmsTemplateDetail detail = new SmsTemplateDetail();

      detail.setSmsType(bean.getSmsType());
      detail.setSmsText(d.getSmsText());
      detail.setSmsCompositionName(d.getSmsCompositionName());

      setTaxIncludedPriceDisplay(detail);
      detailList.add(detail);
    }
    suite.setSmsTemplateDetail(detailList);

    SmsTemplateExpander expander = new SmsTemplateExpander(suite);
    for (SmsCompositionDetail detail : bean.getSmsDetailList()) {
      SmsTagDataList tagDataList = new SmsTagDataList();
      for (CodeAttribute c : detail.getSmsTagList()) {
        if (c instanceof SmsTemplateTag) {

          SmsTemplateTag tag = (SmsTemplateTag) c;
          tagDataList.add(tag, tag.getDummyData());
        }
      }
      expander.addTagDataList(detail.getSmsType(), tagDataList);
    }

    previewText = expander.expandTemplate();

    previewText = Messages.getString("web.action.back.shop.SmstemplateEditPreviewAction.1") + previewText; //$NON-NLS-1$
    // 未判断TEXT/HTML
    previewText = previewText.replaceAll("\r\n", "<br/>");
    previewText = previewText.replaceAll("\n", "<br/>");

    bean.setPreviewText(previewText);
    bean.setModeDiv("preview");
//    List<SmsCompositionDetail> de = bean.getSmsDetailList();
//    if (de.size() > 0) {
//      de.get(0).setSmsType("1");
//      bean.setSmsDetailList(de);
//    }
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  private void setTaxIncludedPriceDisplay(SmsTemplateDetail detail) {
//    String[] list = {
      // SmsType.ORDER_DETAILS_PC.getValue(),
//      SmsType.ORDER_DETAILS_PC.getValue(), // 订单确认（手机）
      // SmsType.CANCELLED_ORDER.getValue(),
      // SmsType.PAYMENT_REMINDER.getValue(),
      // SmsType.RECEIVED_PAYMENT.getValue(),
      // SmsType.RESERVATION_DETAIL.getValue(),
      // SmsType.CANCELLED_RESERVATION.getValue(),
      // SmsType.SHIPPING_INFORMATION.getValue(),
      // SmsType.ORDER_DETAILS_PC.getValue()
//    };
//    List<String> displayTaxIncludedPriceSmsList = Arrays.asList(list);
//    if (displayTaxIncludedPriceSmsList.contains(detail.getSmsType())) {
//      detail.setSmsText("\r\n" + detail.getSmsText() + Messages.getString("web.action.back.shop.SmstemplateEditPreviewAction.2"));
//    }
  }

  @Override
  public void prerender() {
    SmstemplateEditBean bean = (SmstemplateEditBean) getRequestBean();
    bean.setDisplayPreviewRegisterButton(false);
    setRequestBean(bean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.SmstemplateEditPreviewAction.3");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105111004";
  }
}
