package jp.co.sint.webshop.web.action.back.analysis;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.service.AnalysisService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.analysis.CustomerStatisticsSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.CustomerStatisticsBean;
import jp.co.sint.webshop.web.bean.back.analysis.CustomerStatisticsBean.CustomerStatisticsDetail;
import jp.co.sint.webshop.web.bean.back.analysis.CustomerStatisticsBean.CustomerStatisticsItem;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.analysis.AnalysisErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1070510:顧客分析のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerStatisticsInitAction extends WebBackAction<CustomerStatisticsBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    // 分析参照_サイトの権限を持つユーザのみアクセス可能
    return Permission.ANALYSIS_READ_SITE.isGranted(getLoginInfo());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CustomerStatisticsBean bean = new CustomerStatisticsBean();

    AnalysisService service = ServiceLocator.getAnalysisService(getLoginInfo());

    List<CustomerStatisticsSummary> resultList = service.getCustomerStatistics();

    if (resultList.isEmpty()) {
      addErrorMessage(WebMessage.get(AnalysisErrorMessage.NO_SUMMARY_DATA_ERROR,
          Messages.getString("web.action.back.analysis.CustomerStatisticsInitAction.0")));
      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }

    List<CustomerStatisticsDetail> detailList = new ArrayList<CustomerStatisticsDetail>();
    for (CustomerStatisticsSummary s : resultList) {
      CustomerStatisticsItem item = new CustomerStatisticsItem();
      item.setCustomerAmount(s.getCustomerAmount());
      item.setStatisticsItem(s.getStatisticsItem());

      boolean exists = false;
      for (CustomerStatisticsDetail d : detailList) {
        if (d.getStatisticsGroup().equals(s.getStatisticsGroup())) {
          d.getStatisticsDetail().add(item);
          exists = true;
        }
      }
      if (!exists) {
        CustomerStatisticsDetail detail = new CustomerStatisticsDetail();
        detail.setStatisticsGroup(s.getStatisticsGroup());
        detail.getStatisticsDetail().add(item);
        detailList.add(detail);
      }
    }
    bean.setCustomerStatisticsResult(detailList);

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.analysis.CustomerStatisticsInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107051001";
  }

}
