package jp.co.sint.webshop.web.message.back.analysis;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class AnalysisMessages_ja_JP extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = new Object[][] {
        {
            // 表示モードエラー
            "display_mode_error", "表示モードが不正です。"
        }, {
            // 検索条件範囲エラー
            "search_range_error", "{0}は{1}より小さい値を指定してください。"
        }, {
            // 権限エラー
            "permission_error", "権限がありません。"
        }, {
            // 集計データ未存在エラー
            "no_summary_data_error", "{0}の集計データがありません。"
        }
    };
    return obj;
  }

}
