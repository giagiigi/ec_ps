package jp.co.sint.webshop.sms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.data.dto.SmsTemplateDetail;
import jp.co.sint.webshop.service.shop.SmsTemplateSuite;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.NumUtil;

import org.apache.log4j.Logger;

public final class SmsTemplateExpander {

  private SmsTemplateSuite smsTemplateSuie;

  private Map<String, List<SmsTagDataList>> tagDataMap = new HashMap<String, List<SmsTagDataList>>();

  public SmsTemplateExpander(SmsTemplateSuite templateSuite) {
    this.smsTemplateSuie = templateSuite;
  }

  public void addTagDataList(String compositionTag, SmsTagDataList... tagDataList) {
    List<SmsTagDataList> list = new ArrayList<SmsTagDataList>();
    if (tagDataMap.containsKey(compositionTag)) {
      list = tagDataMap.get(compositionTag);
    }
    list.addAll(Arrays.asList(tagDataList));
    tagDataMap.put(compositionTag, list);
  }

  public String expandTemplate() {
    long maxNo = 1L;

    String smsText = smsTemplateSuie.getSmsTemplateDetail().get(0).getSmsText();

    Map<Long, SmsTemplateDetail> detailMap = new HashMap<Long, SmsTemplateDetail>();

    // for (SmsTemplateDetail detail : smsTemplateSuie.getSmsTemplateDetail()) {
    // if (maxNo < Long.parseLong(detail.getSmsType())) {
    // maxNo = Long.parseLong(detail.getSmsType());
    // }
    // detailMap.put(Long.parseLong(detail.getSmsType()), detail);
    // }
    detailMap.put(Long.parseLong(smsTemplateSuie.getSmsTemplateDetail().get(0).getSmsType()), smsTemplateSuie
        .getSmsTemplateDetail().get(0));

    String smsTextTmp = "";
    for (Long i = maxNo; i > 0; i--) {
      SmsTemplateDetail expandItem = detailMap.get(NumUtil.toLong(smsTemplateSuie.getSmsTemplateDetail().get(0).getSmsType()));
      if (expandItem == null) {
        continue;
      }
      List<SmsTagDataList> tagDataList = this.tagDataMap.get(expandItem.getSmsType());
      if (expandItem.getSmsText() == null) {
        Logger.getLogger(this.getClass()).error(Messages.log("sms.SmsTemplateExpander.0"));
        throw new RuntimeException();
      } else if (tagDataList == null) {
        smsTextTmp = expandItem.getSmsText();
      } else {
        smsTextTmp = expandItem.getSmsText();
        for (SmsTagDataList tagData : tagDataList) {
          smsTextTmp = expandItemTags(smsTextTmp, tagData);
        }
      }

      if (expandItem.getSmsType().equals("0")) {
        // ルート直下の構造タグの場合ルートに対して置換処理を行う
        smsText = replaceFirstString(smsText, expandItem.getSmsType(), smsTextTmp);
      } else {
        // ルート直下以外は親テンプレートの構造に展開
        SmsTemplateDetail parentItem = detailMap.get(Long.parseLong(expandItem.getSmsType()));
        if (parentItem == null) {
          continue;
        }
        // String parentSmsText = replaceFirstString(parentItem.getSmsText(),
        // expandItem.getTemplateTag(), smsTextTmp);
        // parentItem.setSmsText(parentSmsText);
      }
    }
    return smsTextTmp;
  }

  /**
   * 件名を取得する。<BR>
   * 
   * @return 件名
   */

  public SmsTemplateDetail getSmsTemplateDetail(String smsType) {
    for (SmsTemplateDetail detail : smsTemplateSuie.getSmsTemplateDetail()) {
      if (detail.getSmsType().equals(smsType)) {
        return detail;
      }
    }
    return null;
  }

  public void setSmsTemplateDetail(SmsTemplateDetail templateDetail) {
    List<SmsTemplateDetail> detailList = new ArrayList<SmsTemplateDetail>();

    for (SmsTemplateDetail detail : smsTemplateSuie.getSmsTemplateDetail()) {
      if (detail.getSmsType().equals(templateDetail.getSmsType())) {
        detailList.add(templateDetail);
      } else {
        detailList.add(detail);
      }
    }

    smsTemplateSuie.setSmsTemplateDetail(detailList);
  }

  public String expandItemTags(String originalText, SmsTagDataList itemTags) {

    String tmpText;

    tmpText = originalText;

    for (SmsTemplateTag tag : itemTags.getTagList()) {
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
