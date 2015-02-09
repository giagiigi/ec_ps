package jp.co.sint.webshop.web.message.back.analysis;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class AnalysisMessages_en_US extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = new Object[][] {
        {
            // 表示モードエラー
            "display_mode_error", "Invalid display mode."
        }, {
            // 検索条件範囲エラー
            "search_range_error", "{0} have to be smaller value than {1}."
        }, {
            // 権限エラー
            "permission_error", "No permission"
        }, {
            // 集計データ未存在エラー
            "no_summary_data_error", "There is no summary data of {0}."
        }
    };
    return obj;
  }

}
