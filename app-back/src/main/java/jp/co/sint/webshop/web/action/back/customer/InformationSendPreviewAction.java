package jp.co.sint.webshop.web.action.back.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.domain.MailContentType;
import jp.co.sint.webshop.data.domain.SmsContentType;
import jp.co.sint.webshop.data.dto.MailTemplateDetail;
import jp.co.sint.webshop.data.dto.MailTemplateHeader;
import jp.co.sint.webshop.mail.MailTagDataList;
import jp.co.sint.webshop.mail.MailTemplateExpander;
import jp.co.sint.webshop.mail.MailTemplateTag;
import jp.co.sint.webshop.service.shop.MailTemplateSuite;
import jp.co.sint.webshop.utility.WebUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.customer.InformationSendBean;
import jp.co.sint.webshop.web.bean.back.customer.InformationSendBean.MailDetail;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1030610:情報メール送信のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class InformationSendPreviewAction extends InformationSendBaseAction {

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    getBean().setModeDiv("edit");
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    InformationSendBean bean = getBean();
    String previewText = "";

    // テンプレート展開用のDTO作成
    MailTemplateSuite suite = new MailTemplateSuite();
    MailTemplateHeader header = new MailTemplateHeader();
    header.setMailSubject(bean.getSubject());
    header.setMailComposition(bean.getMailComposition());
    suite.setMailTemplateHeader(header);
    List<MailTemplateDetail> detailList = new ArrayList<MailTemplateDetail>();
    for (MailDetail d : bean.getMailDetailList()) {
      MailTemplateDetail detail = new MailTemplateDetail();
      detail.setMailTemplateBranchNo(Long.parseLong(d.getBranchNo()));
      detail.setParentMailTemplateBranchNo(Long.parseLong(d.getParentBranchNo()));
      detail.setMailTemplateDepth(d.getMailTemplateDepth());
      detail.setMailText(d.getMailText());
      detail.setMailCompositionName(d.getMailCompositionName());
      detail.setSubstitutionTag(d.getTemplateTag());
      detailList.add(detail);
    }
    suite.setMailTemplateDetail(detailList);

    // メールテンプレート展開クラスの生成
    MailTemplateExpander expander = new MailTemplateExpander(suite);

    // テンプレートに置換する項目タグのデータを生成
    for (MailDetail detail : bean.getMailDetailList()) {
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

    if (bean.getContentType().equals(MailContentType.TEXT.getValue())) {
      previewText = WebUtil.escapeXml(previewText);
    }
    
    if (bean.getContentType().equals(SmsContentType.TEXT.getValue())) {
      previewText = WebUtil.escapeXml(previewText);
    }

    // 出来上がったテンプレートをプレビュー用に整形する
    previewText = Messages.getString("web.action.back.customer.InformationSendPreviewAction.0") + bean.getSubject()
    + Messages.getString("web.action.back.customer.InformationSendPreviewAction.1") + previewText;
    previewText = previewText.replaceAll("\r\n", "<BR>");
    previewText = previewText.replaceAll("\n", "<BR>");

    bean.setPreviewText(previewText);
    bean.setModeDiv("preview");

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.InformationSendPreviewAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103061006";
  }

}
