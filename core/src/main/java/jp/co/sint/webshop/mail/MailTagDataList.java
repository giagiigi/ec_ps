package jp.co.sint.webshop.mail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author System Integrator Corp.
 */
public class MailTagDataList {

  /**
   * tagList タグリストを保持
   */
  private List<MailTemplateTag> tagList;

  /**
   * タグ名と置換するタグの内容を保持するハッシュマップ
   */
  private HashMap<MailTemplateTag, String> tagDataList;

  /**
   * コンストラクタ タグリストとタグデータ用のマップを初期化した後に createTagListを呼び出す
   */
  public MailTagDataList() {
    tagList = new ArrayList<MailTemplateTag>();
    tagDataList = new HashMap<MailTemplateTag, String>();
  }

  /**
   * タグリストを取得
   */
  public List<MailTemplateTag> getTagList() {
    return tagList;
  }

  /**
   * @param tagName
   *          追加するタグ名
   * @param tagData
   *          追加するタグの内容
   */
  public void add(MailTemplateTag tagName, String tagData) {
    tagList.add(tagName);
    tagDataList.put(tagName, tagData);

  }

  public String getTagData(MailTemplateTag tagInfo) {
    return tagDataList.get(tagInfo);
  }

}
