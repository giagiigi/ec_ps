package jp.co.sint.webshop.mail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.data.dto.MailTemplateDetail;
import jp.co.sint.webshop.data.dto.MailTemplateHeader;
import jp.co.sint.webshop.service.shop.MailTemplateSuite;
import jp.co.sint.webshop.text.Messages;

import org.apache.log4j.Logger;

/**
 * メールテンプレート展開クラス<BR>
 * 展開方法<BR>
 * コンストラクタで渡されたテンプレート情報のテンプレート明細のうち一番枝版が大きい明細から展開する<BR>
 * 展開する際に、addTagDataListメソッドにて設定された置換情報を私用して 項目タグを展開する。<BR>
 * addTagDataListでは、構造タグごとに置換する項目タグを設定し<BR>
 * 特定の構造タグを複数回繰り返す場合、同一構造タグで複数登録することで<BR>
 * 展開時に、繰返し展開される<BR>
 * 
 * @author System Integrator Corp.
 */
public final class MailTemplateExpander {

  private MailTemplateSuite mailTemplateSuie;

  private Map<String, List<MailTagDataList>> tagDataMap = new HashMap<String, List<MailTagDataList>>();

  public MailTemplateExpander(MailTemplateSuite templateSuite) {
    this.mailTemplateSuie = templateSuite;
  }

  /**
   * 置換する項目タグデータ情報を設定する<BR>
   * ex) 受注明細に商品名が存在する場合<BR>
   * TagDataList tagData = new TagDataList();<BR>
   * tagData.addTagData("[@ORDER_NO@]", "0001");<BR>
   * addTagDataList("[#ORDER_DETAIL#]", tagData);<BR>
   * <BR>
   * ex2) 受注明細が複数件存在する場合<BR>
   * TagDataList tagData1 = new TagDataList();<BR>
   * tagData1.addTagData("[@ORDER_NO@]", "0001");<BR>
   * TagDataList tagData2 = new TagDataList();<BR>
   * tagData2.addTagData("[@ORDER_NO@]", "0002");<BR>
   * addTagDataList("[#ORDER_DETAIL#]", tagData1, tagData2);<BR>
   * <BR>
   * また、同一構造タグの置換情報はインスタンス内で保持する為<BR>
   * 本メソッドを２回に分けて設定することも可能。<BR>
   * <BR>
   * ex3) 受注明細が複数件存在する場合<BR>
   * TagDataList tagData1 = new TagDataList();<BR>
   * tagData1.addTagData("[@ORDER_NO@]", "0001");<BR>
   * TagDataList tagData2 = new TagDataList();<BR>
   * tagData2.addTagData("[@ORDER_NO@]", "0002");<BR>
   * addTagDataList("[#ORDER_DETAIL#]", tagData1);<BR>
   * addTagDataList("[#ORDER_DETAIL#]", tagData2);<BR>
   * <BR>
   * ex2)とex3)を設定しexpandTemplateメソッドでテンプレートを展開した場合<BR>
   * 受注明細情報は２回展開される<BR>
   * 
   * @param compositionTag
   *          置換する項目タグが存在する構造の構造タグ
   * @param tagDataList
   *          項目タグと置換するデータをセットにした情報のリストクラス
   */
  public void addTagDataList(String compositionTag, MailTagDataList... tagDataList) {
    List<MailTagDataList> list = new ArrayList<MailTagDataList>();
    if (tagDataMap.containsKey(compositionTag)) {
      list = tagDataMap.get(compositionTag);
    }
    list.addAll(Arrays.asList(tagDataList));
    tagDataMap.put(compositionTag, list);
  }

  /**
   * テンプレート展開処理<BR>
   * コンストラクタで渡された１テンプレート分のテンプレート情報を元に<BR>
   * addTagDataListで渡された項目タグの置換情報を使用して<BR>
   * テンプレートを展開する。<BR>
   * <BR>
   * 展開時に、項目タグの置換情報が存在しなかった場合、そのままタグが表示されます<BR>
   * ex)同報配信メールを展開する場合は顧客情報はaddTagDataListせずに展開する<BR>
   * 
   * @return 展開後のメール情報
   */
  public String expandTemplate() {
    String mailText = mailTemplateSuie.getMailTemplateHeader().getMailComposition();
    Long maxBranchNo = 0L;

    Map<Long, MailTemplateDetail> detailMap = new HashMap<Long, MailTemplateDetail>();

    for (MailTemplateDetail detail : mailTemplateSuie.getMailTemplateDetail()) {
      if (maxBranchNo < detail.getMailTemplateBranchNo()) {
        maxBranchNo = detail.getMailTemplateBranchNo();
      }
      detailMap.put(detail.getMailTemplateBranchNo(), detail);
    }

    for (Long i = maxBranchNo; i > 0; i--) {
      MailTemplateDetail expandItem = detailMap.get(i);
      if (expandItem == null) {
        continue;
      }
      List<MailTagDataList> tagDataList = this.tagDataMap.get(expandItem.getSubstitutionTag());
      String mailTextTmp = "";
      if (expandItem.getMailText() == null) {
        Logger.getLogger(this.getClass()).error(Messages.log("mail.MailTemplateExpander.0"));
        throw new RuntimeException();
      } else if (tagDataList == null) {
        mailTextTmp = expandItem.getMailText();
      } else {
        for (MailTagDataList tagData : tagDataList) {
          mailTextTmp += expandItemTags(expandItem.getMailText(), tagData);
        }
      }

      if (expandItem.getParentMailTemplateBranchNo() == 0L) {
        // ルート直下の構造タグの場合ルートに対して置換処理を行う
        mailText = replaceFirstString(mailText, expandItem.getSubstitutionTag(), mailTextTmp);
      } else {
        // ルート直下以外は親テンプレートの構造に展開
        MailTemplateDetail parentItem = detailMap.get(expandItem.getParentMailTemplateBranchNo());
        if (parentItem == null) {
          continue;
        }
        String parentMailText = replaceFirstString(parentItem.getMailText(), expandItem.getSubstitutionTag(), mailTextTmp);
        parentItem.setMailText(parentMailText);
      }
    }

    return mailText;
  }

  /**
   * 件名を取得する。<BR>
   * 
   * @return 件名
   */
  public String getMailSubject() {
    MailTemplateHeader header = mailTemplateSuie.getMailTemplateHeader();
    if (header != null) {
      return header.getMailSubject();
    }
    return "";
  }

  /**
   * 置換タグを元に構造タグ情報を取得する
   * 
   * @param substitutionTag
   * @return 置換タグから得られる構造タグ情報
   */
  public MailTemplateDetail getMailTemplateDetail(String substitutionTag) {
    for (MailTemplateDetail detail : mailTemplateSuie.getMailTemplateDetail()) {
      if (detail.getSubstitutionTag().equals(substitutionTag)) {
        return detail;
      }
    }
    return null;
  }

  /**
   * 構造タグ情報を渡されたものに置き換える
   * 
   * @param templateDetail
   */
  public void setMailTemplateDetail(MailTemplateDetail templateDetail) {
    List<MailTemplateDetail> detailList = new ArrayList<MailTemplateDetail>();

    for (MailTemplateDetail detail : mailTemplateSuie.getMailTemplateDetail()) {
      if (detail.getSubstitutionTag().equals(templateDetail.getSubstitutionTag())) {
        detailList.add(templateDetail);
      } else {
        detailList.add(detail);
      }
    }

    mailTemplateSuie.setMailTemplateDetail(detailList);
  }

  /**
   * 項目タグを展開する
   * 
   * @param originalText
   *          展開される元のメール本文
   * @param itemTags
   *          タグリスト
   * @return 展開後のメール
   */
  public String expandItemTags(String originalText, MailTagDataList itemTags) {

    String tmpText;

    tmpText = originalText;

    for (MailTemplateTag tag : itemTags.getTagList()) {
      // 当てはまる全てのタグを置換
      if (itemTags.getTagData(tag) != null) {
        String tagStr = tag.getValue();
        tmpText = tmpText.replace(tagStr, itemTags.getTagData(tag));
      } else {
        tmpText = tmpText.replace(tag.getValue(), "");
      }
    }

    return tmpText;
  }

  /**
   * @param orgString
   *          置換前の文字列
   * @param oldString
   *          置換対象の文字列
   * @param newString
   *          新しい文字列
   * @return 置換前の文字列から、最初に出現する置換対象の文字列を置換した 文字列を返す
   */
  private String replaceFirstString(String orgString, String oldString, String newString) {
    String firstString;
    String endString;
    int index;

    index = orgString.indexOf(oldString);

    if (index != -1) {
      firstString = orgString.substring(0, index);
      endString = orgString.substring(orgString.indexOf(oldString) + oldString.length());
      return firstString + "\r\n" + newString + endString;
    } else {
      return orgString;
    }

  }
}
