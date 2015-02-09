package jp.co.sint.webshop.web.action.back.analysis;


import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.communication.PrivateCouponListSearchCondition;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvExportType;
import jp.co.sint.webshop.service.data.csv.PrivateCouponListExportCondition;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.WebExportAction;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.analysis.PrivateCouponBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;

/**
 * 
 * 
 * @author OB
 */
public class PrivateCouponExportAction extends PrivateCouponBaseAction implements WebExportAction {
	 /**
	   * 确认登录用户的权限，返回是否认可此程序的执行。
	   * 
	   * @return 认可此程序执行时返回true
	   */
	  public boolean authorize() {

		  BackLoginInfo login = getLoginInfo();
			if (null == login) {
				return false;
			}
		  return (Permission.ANALYSIS_DATA_SHOP.isGranted(login) && getConfig().isOne())
      || Permission.ANALYSIS_DATA_SITE.isGranted(getLoginInfo());

	  }

	  /**
	   * 验证存储在data model内输入值的有效性。
	   * 
	   * @return 输入值有效性返回true
	   */
	  @Override
	  public boolean validate() {
		  return true;
	  }

	  /**
	   * 运行应用程序。
	   * 
	   * @return 应用程序的结果
	   */
	  @Override
	  public WebActionResult callService() {

		  PrivateCouponBean searchBean = getBean();

	    // 取得查询结果列表
		  PrivateCouponListSearchCondition searchCondition = new PrivateCouponListSearchCondition();
	    setSearchCondition(searchCondition,searchBean );

	    PrivateCouponListExportCondition condition = CsvExportType.EXPORT_CSV_PRIVATE_COUPON_ANALYSIS.createConditionInstance();
	    condition.setSearchCondition(searchCondition);
	    this.exportCondition = condition;

	    setNextUrl("/download");
	    setRequestBean(searchBean);
	    return BackActionResult.RESULT_SUCCESS;
	  }

	  private CsvExportCondition<? extends CsvSchema> exportCondition;

	  public CsvExportCondition<? extends CsvSchema> getExportCondition() {
	    return exportCondition;
	  }

	  /**
	   * 取得Action名
	   * 
	   * @return Action名
	   */
	  public String getActionName() {
	    return "顾客别优惠券发行分析一览画面CSV导出处理";
	  }

	  /**
	   * OperationCode取得
	   * 
	   * @return OperationCode
	   */
	  public String getOperationCode() {
	    return "6107111003";
	  }

}
