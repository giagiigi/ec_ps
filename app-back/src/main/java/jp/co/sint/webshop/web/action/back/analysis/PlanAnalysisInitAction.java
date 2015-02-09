package jp.co.sint.webshop.web.action.back.analysis;


import jp.co.sint.webshop.data.domain.PlanType;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.PlanAnalysisBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;

/**
 * U1071210:企划分析一覧のアクションクラスです
 * 
 * @author OB
 */
public class PlanAnalysisInitAction extends WebBackAction<PlanAnalysisBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
	    // 分析参照_ショップの権限を持つユーザは一店舗モードでないときのみ参照可能
	    // 分析参照_サイトの権限を持つユーザは常に参照可能
	    return (Permission.ANALYSIS_READ_SHOP.isGranted(getLoginInfo()) && !getConfig().isOne())
	        || Permission.ANALYSIS_READ_SITE.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
	    String[] args = getRequestParameter().getPathArgs();
	    if(args.length!=1)
	    {
	    	getBean().setDisplaySearchButton(Boolean.FALSE);
	    	addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL) );
	    	return false;
	    }
    	if(!args[0].equals(PlanType.PROMOTION.getValue())&&!args[0].equals(PlanType.FEATURE.getValue()))
    	{
	    	getBean().setDisplaySearchButton(Boolean.FALSE);
	    	addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
	    	return false;
    	}
		getBean().setPlanTypeMode(getRequestParameter().getPathArgs()[0]);
    return true;
  }
  
  public void init() {
		setBean(new PlanAnalysisBean());
	}
  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
	PlanAnalysisBean bean = getBean();
	//设置默认的统计类别
	bean.setSearchStatisticsType(NumUtil.toString(1));
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
	if (PlanType.PROMOTION.getValue().equals(getBean().getPlanTypeMode())) {
	  return "促销企划分析画面初期表示处理";
	} else if(PlanType.FEATURE.getValue().equals(getBean().getPlanTypeMode())) {
      return "特集企划分析画面初期表示处理";
	}
    return "";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    if (PlanType.PROMOTION.getValue().equals(getBean().getPlanTypeMode())) {
	  return "6107121001";
	} else if(PlanType.FEATURE.getValue().equals(getBean().getPlanTypeMode())) {
      return "6107131001";
	}
    return "";
   }
}
