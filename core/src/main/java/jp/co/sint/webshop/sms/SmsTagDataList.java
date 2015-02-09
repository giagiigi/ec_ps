package jp.co.sint.webshop.sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author System Integrator Corp.
 */
public class SmsTagDataList {

  /**
   * tagList タグリストを保持
   */
  private List<SmsTemplateTag> tagList;

  /**
   * タグ名と置換するタグの内容を保持するハッシュマップ
   */
  private HashMap<SmsTemplateTag, String> tagDataList;

  /**
   * コンストラクタ タグリストとタグデータ用のマップを初期化した後に createTagListを呼び出す
   */
  public SmsTagDataList() {
    tagList = new ArrayList<SmsTemplateTag>();
    tagDataList = new HashMap<SmsTemplateTag, String>();
  }

  /**
   * タグリストを取得
   */
  public List<SmsTemplateTag> getTagList() {
    return tagList;
  }

  /**
   * @param tagName
   *          追加するタグ名
   * @param tagData
   *          追加するタグの内容
   */
  public void add(SmsTemplateTag tagName, String tagData) {
    tagList.add(tagName);
    tagDataList.put(tagName, tagData);

  }

  public String getTagData(SmsTemplateTag tagInfo) {
    return tagDataList.get(tagInfo);
  }

}
