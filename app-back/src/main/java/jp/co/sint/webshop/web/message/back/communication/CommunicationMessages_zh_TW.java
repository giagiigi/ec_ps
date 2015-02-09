package jp.co.sint.webshop.web.message.back.communication;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CommunicationMessages_zh_TW extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // ページ遷移エラー
        {
            "move_action_error", "不正确的{0}被指定了。"
        },
        // コンテンツ未登録エラー
        {
            "contents_exist_error", "宣传活动的内容没有被登录。"
        },
        // 実施中アンケート削除不可エラー
        {
            "delete_enquete_error", "实施中的问卷调查不能被删除。"
        },
        // 回答済みアンケート更新不可エラー
        {
            "answered_enquete_error", "回答中不能存在问卷调查的{0}的{1}。"
        },
        // キャンペーン削除エラー
        {
            "delete_campaign_error", "关联订购信息不能删除宣传活动。({0})"
        },
        // 実施中キャンペーン削除エラー
        {
            "delete_effect_campaign_error", "实施中的宣传活动不能删除。"
        },
    };
    return obj;
  }

}
