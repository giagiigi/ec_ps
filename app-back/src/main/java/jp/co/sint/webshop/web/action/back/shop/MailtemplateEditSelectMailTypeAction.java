package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.data.domain.MailType;
import jp.co.sint.webshop.data.domain.PointFunctionEnabledFlg;
import jp.co.sint.webshop.data.dto.MailTemplateDetail;
import jp.co.sint.webshop.data.dto.MailTemplateHeader;
import jp.co.sint.webshop.data.dto.PointRule;
import jp.co.sint.webshop.mail.CustomerTag;
import jp.co.sint.webshop.mail.MailTemplateTag;
import jp.co.sint.webshop.mail.MailTemplateUtil;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.shop.MailTemplateSuite;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.MailtemplateEditBean;
import jp.co.sint.webshop.web.bean.back.shop.MailtemplateEditBean.MailCompositionDetail;
import jp.co.sint.webshop.web.bean.back.shop.MailtemplateEditBean.MailTag;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * メールテンプレート選択時の情報取得Action。<BR>
 * 情報メールの場合<BR>
 * メールタイプは情報メール固定、テンプレート番号は画面で選択されたメールタイプを使い<BR>
 * メールテンプレートヘッダとメールテンプレート明細から情報を取得する<BR>
 * 情報メールはヘッダと明細が1:1になるはずなので、明細の１行目を構造情報として設定する<BR>
 * <BR>
 * その他の場合<BR>
 * メールタイプは画面で選択されたメールタイプ、テンプレート番号は0番固定を使い<BR>
 * メールテンプレートヘッダとメールテンプレート明細から情報を取得する<BR>
 * その他の場合はヘッダと明細が1:nになるはずなので、全明細情報を構造情報として設定する<BR>
 * U1051110:メールテンプレートのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class MailtemplateEditSelectMailTypeAction extends WebBackAction<MailtemplateEditBean> {

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
    MailtemplateEditBean bean = getBean();

    String mailType = "";
    Long templateNo;
    if (isInformationMail()) {
      if (bean.getMailType() == null) {
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }
      mailType = MailType.INFORMATION.getValue();
      templateNo = Long.parseLong(bean.getMailType());
    } else {
      mailType = bean.getMailType();
      templateNo = 0L;
    }

    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
    MailTemplateSuite suite = service.getMailTemplateConfig(getLoginInfo().getShopCode(), mailType, templateNo);
    MailTemplateHeader header = suite.getMailTemplateHeader();
    if (header == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.shop.MailtemplateEditSelectMailTypeAction.0")));
      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }
    List<MailTemplateDetail> detailList = suite.getMailTemplateDetail();
    if (detailList == null || detailList.size() <= 0) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.shop.MailtemplateEditSelectMailTypeAction.0")));
      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }

    List<MailCompositionDetail> cDetailList = new ArrayList<MailCompositionDetail>();
    if (isInformationMail()) {
      MailCompositionDetail detail = copyDetailToComposition(detailList.get(0));
      bean.setNewMailTypeName(detail.getMailCompositionName());
      cDetailList.add(detail);
      cDetailList.add(copyDetailToComposition(detailList.get(1)));
    } else {
      cDetailList.addAll(createOtherMailData(header, detailList));
    }

    bean.setSubject(header.getMailSubject());
    bean.setFromAddress(header.getFromAddress());
    bean.setBccAddress(header.getBccAddress());
    bean.setContentType(NumUtil.toString(header.getMailContentType()));
    bean.setMailComposition(header.getMailComposition());
    bean.setMailDetailList(cDetailList);
    bean.setMailTypeName(MailType.fromValue(mailType).getName());
    bean.setSenderName(header.getMailSenderName());
    bean.setUpdateDatetime(header.getUpdatedDatetime());

    bean.setModeDiv("edit");
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    MailtemplateEditBean bean = (MailtemplateEditBean) getRequestBean();
    LoginInfo login = getLoginInfo();
    if (Permission.SHOP_MANAGEMENT_DELETE_SITE.isGranted(login)) {
      bean.setDisplayDeleteButton(true);
    } else {
      bean.setDisplayDeleteButton(false);
    }
    bean.setDisplayInsertButton(false);
    setRequestBean(bean);
  }

  /**
   * 情報メール以外のメールテンプレートが<BR>
   * 選択された場合のテンプレート情報（構造タグ単位）生成処理<BR>
   * 使用できるタグ一覧も本メソッドにて構造タグごとに生成する<BR>
   * 
   * @param header
   * @param detailList
   * @return cDetailList
   */
  private List<MailCompositionDetail> createOtherMailData(MailTemplateHeader header, List<MailTemplateDetail> detailList) {
    List<MailCompositionDetail> cDetailList = new ArrayList<MailCompositionDetail>();

    Map<Long, MailCompositionDetail> detailMapWithBranchNo = new HashMap<Long, MailCompositionDetail>();
    for (MailTemplateDetail detail : detailList) {
      MailCompositionDetail c = copyDetailToComposition(detail);

      // 構造タグ情報を親テンプレートのタグリストに追加
      MailCompositionDetail parent = detailMapWithBranchNo.get(detail.getParentMailTemplateBranchNo());
      if (parent != null) {
        List<MailTag> parentTagList = parent.getMailTagList();
        MailTag tag = new MailTag();
        tag.setName(detail.getMailCompositionName());
        tag.setValue(detail.getSubstitutionTag());
        tag.setRequired(true);
        parentTagList.add(0, tag);
      }

      detailMapWithBranchNo.put(detail.getMailTemplateBranchNo(), c);
      cDetailList.add(c);
    }
    return cDetailList;
  }

  /**
   * DTOのMailTemplateDetailをBean用のMailCompositionDetailに詰め替えて返す<BR>
   * 
   * @param detail
   * @return c
   */
  private MailCompositionDetail copyDetailToComposition(MailTemplateDetail detail) {
    SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());
    PointRule pointRule = service.getPointRule();
    boolean notUsePoint = pointRule.getPointFunctionEnabledFlg().equals(PointFunctionEnabledFlg.DISABLED.longValue());

    MailCompositionDetail c = new MailCompositionDetail();
    c.setBranchNo(Long.toString(detail.getMailTemplateBranchNo()));
    c.setParentBranchNo(Long.toString(detail.getParentMailTemplateBranchNo()));
    c.setMailCompositionName(detail.getMailCompositionName());
    c.setTemplateTag(detail.getSubstitutionTag());
    List<MailTag> tagList = new ArrayList<MailTag>();
    for (MailTemplateTag tag : MailTemplateUtil.getUsableTagList(MailType.fromValue(detail.getMailType()), detail
        .getSubstitutionTag())) {
      if (notUsePoint && isUsePointTagName(tag.getName())) {
        continue;
      }
      MailTag composition = new MailTag();
      composition.setName(tag.getName());
      composition.setValue(tag.getValue());
      composition.setRequired(tag.isRequired());
      composition.setTagDiv(tag.getTagDiv());
      composition.setDummyData(tag.getDummyData());
      tagList.add(composition);
    }

    if (MailTemplateUtil.getUsableTagList(MailType.fromValue(detail.getMailType()), detail.getSubstitutionTag()).size() == 0) {
      MailTag composition = new MailTag();
      composition.setName(Messages.getString("web.action.back.shop.MailtemplateEditSelectMailTypeAction.1"));
      composition.setValue("");
      composition.setRequired(false);
      tagList.add(composition);
    }

    c.setMailTagList(tagList);
    c.setMailTemplateDepth(detail.getMailTemplateDepth());
    c.setMailText(detail.getMailText());
    c.setUpdateDatetime(detail.getUpdatedDatetime());

    return c;
  }

  /**
   * 選択されたメールタイプが情報メールかどうか判定する
   * 
   * @return true-情報メール false-その他
   */
  private boolean isInformationMail() {
    return getBean().getTemplateDiv().equals("1");
  }

  /**
   * ポイントに関するタグかどうか判定する
   * 
   * @param tagName
   * @return true-ポイントを使用するタグ false-ポイントを使用しないタグ
   */
  private boolean isUsePointTagName(String tagName) {
    boolean result = false;

    if (tagName.equals(CustomerTag.REST_POINT.getName())) {
      result = true;
    }

    return result;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.MailtemplateEditSelectMailTypeAction.2");
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
