package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.MailContentType;
import jp.co.sint.webshop.data.domain.MailType;
import jp.co.sint.webshop.data.dto.MailTemplateDetail;
import jp.co.sint.webshop.data.dto.MailTemplateHeader;
import jp.co.sint.webshop.mail.MailTagDataList;
import jp.co.sint.webshop.mail.MailTemplateExpander;
import jp.co.sint.webshop.mail.MailTemplateTag;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.shop.MailTemplateSuite;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.WebUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.MailtemplateEditBean;
import jp.co.sint.webshop.web.bean.back.shop.MailtemplateEditBean.MailCompositionDetail;
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
public class MailtemplateEditPreviewAction extends WebBackAction<MailtemplateEditBean> {

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
    MailtemplateEditBean bean = getBean();
    boolean valid = validateBean(bean);
    for (MailCompositionDetail detail : bean.getMailDetailList()) {
      if (validateBean(detail)) {
        if (valid) {
          valid = true;
        }
      } else {
        valid = false;
        break;
      }
      // 項目タグ必須チェック
      for (CodeAttribute tag : detail.getMailTagList()) {
        if (tag instanceof MailTemplateTag) {
          MailTemplateTag mailTag = (MailTemplateTag) tag;
          if (mailTag.isRequired() && !detail.getMailText().contains(mailTag.getValue())) {
            valid &= false;
            addErrorMessage(WebMessage.get(ShopErrorMessage.REQUIRED_TAG, detail.getMailCompositionName(), mailTag.getName()));
            Logger logger = Logger.getLogger(this.getClass());
            logger.debug(WebMessage.get(ShopErrorMessage.REQUIRED_TAG, detail.getMailCompositionName(), mailTag.getName()));
          }
        }
        // 構造タグ必須チェック
        if (tag instanceof NameValue && StringUtil.hasValue(tag.getValue())) {
          if (!detail.getMailText().contains(tag.getValue())) {
            valid &= false;
            addErrorMessage(WebMessage.get(ShopErrorMessage.REQUIRED_TAG, detail.getMailCompositionName(), tag.getName()));
            Logger logger = Logger.getLogger(this.getClass());
            logger.debug(WebMessage.get(ShopErrorMessage.REQUIRED_TAG, detail.getMailCompositionName(), tag.getName()));
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
    MailtemplateEditBean bean = getBean();
    String previewText = "";

    // テンプレート展開用のDTO作成
    MailTemplateSuite suite = new MailTemplateSuite();
    MailTemplateHeader header = new MailTemplateHeader();
    header.setMailSubject(bean.getSubject());
    header.setMailComposition(bean.getMailComposition());
    suite.setMailTemplateHeader(header);

    List<MailTemplateDetail> detailList = new ArrayList<MailTemplateDetail>();
    for (MailCompositionDetail d : bean.getMailDetailList()) {
      MailTemplateDetail detail = new MailTemplateDetail();
      detail.setMailType(bean.getMailType());
      detail.setMailTemplateBranchNo(Long.parseLong(d.getBranchNo()));
      detail.setParentMailTemplateBranchNo(Long.parseLong(d.getParentBranchNo()));
      detail.setMailTemplateDepth(d.getMailTemplateDepth());
      detail.setMailText(d.getMailText());
      detail.setMailCompositionName(d.getMailCompositionName());
      detail.setSubstitutionTag(d.getTemplateTag());

      // 税込価格表記追記
      setTaxIncludedPriceDisplay(detail);

      detailList.add(detail);
    }
    suite.setMailTemplateDetail(detailList);

    // メールテンプレート展開クラスの生成
    MailTemplateExpander expander = new MailTemplateExpander(suite);

    // テンプレートに置換する項目タグのデータを生成
    for (MailCompositionDetail detail : bean.getMailDetailList()) {
      MailTagDataList tagDataList = new MailTagDataList();
      for (CodeAttribute c : detail.getMailTagList()) {
        if (c instanceof MailTemplateTag) {

          MailTemplateTag tag = (MailTemplateTag) c;
          tagDataList.add(tag, tag.getDummyData());
        }
      }
      expander.addTagDataList(detail.getTemplateTag(), tagDataList);
    }

    // メールテンプレート展開処理
    previewText = expander.expandTemplate();
    String subject = bean.getSubject();

    if (bean.getContentType().equals(MailContentType.TEXT.getValue())) {
      previewText = WebUtil.escapeXml(previewText);
      subject = WebUtil.escapeXml(subject);
    }

    // 出来上がったテンプレートをプレビュー用に整形する
    previewText = Messages.getString("web.action.back.shop.MailtemplateEditPreviewAction.0") //$NON-NLS-1$
    + subject + Messages.getString("web.action.back.shop.MailtemplateEditPreviewAction.1") + previewText; //$NON-NLS-1$
    if (bean.getContentType().equals(MailContentType.TEXT.getValue())) {
      previewText = previewText.replaceAll("\r\n", "<br/>");
      previewText = previewText.replaceAll("\n", "<br/>");
    }

    bean.setPreviewText(previewText);
    bean.setModeDiv("preview");
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  private void setTaxIncludedPriceDisplay(MailTemplateDetail detail) {
    String[] list = {
        MailType.ORDER_DETAILS_PC.getValue(), MailType.ORDER_DETAILS_MOBILE.getValue(), MailType.CANCELLED_ORDER.getValue(),
        MailType.PAYMENT_REMINDER.getValue(), MailType.RECEIVED_PAYMENT.getValue(), MailType.RESERVATION_DETAIL.getValue(),
        MailType.CANCELLED_RESERVATION.getValue(), MailType.SHIPPING_INFORMATION.getValue(), MailType.ORDER_DETAILS_PC.getValue()
    };
    List<String> displayTaxIncludedPriceMailList = Arrays.asList(list);

    if (detail.getMailTemplateBranchNo().equals(1L)) {
      if (displayTaxIncludedPriceMailList.contains(detail.getMailType())) {
        detail.setMailText("\r\n" + detail.getMailText()
            + Messages.getString("web.action.back.shop.MailtemplateEditPreviewAction.2"));
      }
    }

  }

  @Override
  public void prerender() {
    MailtemplateEditBean bean = (MailtemplateEditBean) getRequestBean();
    bean.setDisplayPreviewRegisterButton(false);
    setRequestBean(bean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.MailtemplateEditPreviewAction.3");
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
