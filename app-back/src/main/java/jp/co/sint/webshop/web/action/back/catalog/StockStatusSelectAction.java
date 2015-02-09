package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.StockStatusCount;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.StockStatusBean;
import jp.co.sint.webshop.web.bean.back.catalog.StockStatusBean.StockStatusDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1040610:在庫状況設定のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class StockStatusSelectAction extends StockStatusBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_READ.isGranted(getLoginInfo()) || Permission.CATALOG_READ.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {

    // URLパラメータの取得
    // parameter[0]:在庫状況番号
    if (getRequestParameter().getPathArgs().length < 1) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      return false;
    }
    String stockStatusNo = getRequestParameter().getPathArgs()[0];

    for (StockStatusDetailBean detail : getBean().getList()) {
      if (detail.getStockStatusNo().equals(stockStatusNo)) {
        return true;
      }
    }
    addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
        Messages.getString("web.action.back.catalog.StockStatusSelectAction.0")));

    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    // nextBean用のBeanを生成
    StockStatusBean nextBean = getBean();

    if (getLoginInfo().isShop() || getConfig().isOne()) {
      nextBean.setSearchShopCode(getLoginInfo().getShopCode());
    }

    // 在庫状況一覧の取得
    List<StockStatusCount> stockStatusList = getStockStatusList(nextBean);

    // URLパラメータの取得
    // parameter[0]:在庫状況番号
    String stockStatusNo = getRequestParameter().getPathArgs()[0];

    List<StockStatusDetailBean> detailList = new ArrayList<StockStatusDetailBean>();

    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    nextBean.setShopList(utilService.getShopNames(false));

    for (StockStatusCount stockStatus : stockStatusList) {
      StockStatusDetailBean detail = new StockStatusDetailBean();
      setResultStockStatusList(stockStatus, detail);
      detailList.add(detail);

      if (detail.getStockStatusNo().equals(stockStatusNo)) {
        nextBean.setEdit(detail);
      }
    }

    if (StringUtil.isNullOrEmpty(nextBean.getEdit().getStockStatusNo())) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.catalog.StockStatusSelectAction.0")));
    }

    nextBean.setList(detailList);

    // 遷移先Beanを設定
    setRequestBean(nextBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    StockStatusBean reqBean = (StockStatusBean) getRequestBean();
    reqBean.setMode("update");
    setDisplayControl(reqBean);
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.StockStatusSelectAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104061005";
  }

}
