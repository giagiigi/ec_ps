package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityDeleteBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityListBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityDeleteBean.CommodityDeleteListBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityListBean.CommodityListResult;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1040110:商品マスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommodityListDeleteAction extends WebBackAction<CommodityListBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_DELETE.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (getBean().getCheckedCommodityList().size() < 1
        || !StringUtil.hasValueAllOf(ArrayUtil.toArray(getBean().getCheckedCommodityList(), String.class))) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,
          Messages.getString("web.action.back.catalog.CommodityListDeleteAction.0")));
      return false;
    }
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    // nextBeanを生成
    CommodityDeleteBean nextBean = new CommodityDeleteBean();

    // 削除対象商品リストを作成
    List<CommodityDeleteListBean> deleteList = new ArrayList<CommodityDeleteListBean>();
    Map<String, CommodityListResult> commodityMap = listToMap(getBean().getList());

    for (String commodityCode : getBean().getCheckedCommodityList()) {
      if (commodityMap.containsKey(commodityCode)) {
        CommodityListResult commodity = commodityMap.get(commodityCode);
        CommodityDeleteListBean deleteCommodity = new CommodityDeleteListBean();
        deleteCommodity.setShopCode(commodity.getShopCode());
        deleteCommodity.setCommodityCode(commodity.getCommodityCode());
        deleteCommodity.setCommodityName(commodity.getCommodityName());
        deleteCommodity.setUpdatedDatetime(commodity.getUpdatedDatetime());
        deleteList.add(deleteCommodity);
      }
    }

    // nextBeanに削除商品リストを設定
    nextBean.setList(deleteList);

    setRequestBean(nextBean);
    setNextUrl("/app/catalog/commodity_delete/init");

    // 遷移元情報を保存
    DisplayTransition.add(getBean(), "/app/catalog/commodity_list/search/back", getSessionContainer());

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * @param list
   * @return map
   */
  private Map<String, CommodityListResult> listToMap(List<CommodityListResult> list) {
    Map<String, CommodityListResult> map = new HashMap<String, CommodityListResult>();
    for (CommodityListResult commodity : list) {
      map.put(commodity.getCommodityCode(), commodity);
    }
    return map;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityListDeleteAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104011001";
  }

}
