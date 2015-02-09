package jp.co.sint.webshop.web.message.back.communication;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CommunicationMessages_ja_JP extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // ページ遷移エラー
        {
            "move_action_error", "不正な{0}が指定されました。"
        },
        // コンテンツ未登録エラー
        {
            "contents_exist_error", "キャンペーンのコンテンツは登録されていません。"
        },
        // 実施中アンケート削除不可エラー
        {
            "delete_enquete_error", "実施中のアンケートは削除できません。"
        },
        // 回答済みアンケート更新不可エラー
        {
            "answered_enquete_error", "回答が存在するアンケートの{0}を{1}することはできません。"
        },
        // キャンペーン削除エラー
        {
            "delete_campaign_error", "受注情報に関連しているキャンペーンは削除できません。({0})"
        },
        // 実施中キャンペーン削除エラー
        {
            "delete_effect_campaign_error", "実施中のキャンペーンは削除できません。"
        },
    };
    return obj;
  }

}
