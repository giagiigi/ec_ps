package jp.co.sint.webshop.web.message.back.communication;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CommunicationMessages_en_US extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // ページ遷移エラー
        {
            "move_action_error", "Invalid {0} error."
        },
        // コンテンツ未登録エラー
        {
            "contents_exist_error", "The campaign contents has not been registered yet."
        },
        // 実施中アンケート削除不可エラー
        {
            "delete_enquete_error", "Unable to delete the enquete currently underway."
        },
        // 回答済みアンケート更新不可エラー
        {
            "answered_enquete_error", "Unable to {1} {0} of the enquete with the answer exist."
        },
        // キャンペーン削除エラー
        {
            "delete_campaign_error", "Unable to delete the campaign related to order information. ({0})"
        },
        // 実施中キャンペーン削除エラー
        {
            "delete_effect_campaign_error", "Unable to delete campaign currently underway."
        },
    };
    return obj;
  }

}
