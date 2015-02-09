package jp.co.sint.webshop.web.message.back.analysis;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class AnalysisMessages_zh_TW extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = new Object[][] {
        {
            // 表示モードエラー
            "display_mode_error", "表示形式不正确。"
        }, {
            // 検索条件範囲エラー
            "search_range_error", "请指定{0}比{1}小的值。"
        }, {
            // 権限エラー
            "permission_error", "没有权限。"
        }, {
            // 集計データ未存在エラー
            "no_summary_data_error", "没有{0}的总计数据。"
        }
    };
    return obj;
  }

}
