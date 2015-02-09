package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.CommodityLayout;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityCommonBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityCommonBean.CommodityCommonDetailBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;

/**
 * U1040810:商品詳細レイアウトのアクションの基底クラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class CommodityCommonBaseAction extends WebBackAction<CommodityCommonBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public abstract boolean authorize();

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public abstract WebActionResult callService();

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public abstract boolean validate();

  /**
   * @param reqBean
   * @param layoutList
   * @param displayList
   * @param unDisplayList
   */
  public void setCommodityLayout(CommodityCommonBean reqBean, List<CommodityLayout> layoutList,
      List<CommodityCommonDetailBean> displayList, List<CommodityCommonDetailBean> unDisplayList) {

    for (CommodityLayout cl : layoutList) {
      CommodityCommonDetailBean detailBean = new CommodityCommonDetailBean();
      detailBean.setPartsCode(cl.getPartsCode());
      detailBean.setDisplayOrder(Long.toString(cl.getDisplayOrder()));

      if (cl.getDisplayFlg() == 0) {
        detailBean.setDisplayType("li2");
        unDisplayList.add(detailBean);
      } else {
        detailBean.setDisplayType("li1");
        displayList.add(detailBean);
      }
    }

    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    reqBean.setShopList(utilService.getShopNames(true));

    reqBean.setDisplayList(displayList);
    reqBean.setUnDisplayList(unDisplayList);
  }

  /**
   * ショップごとに設定されている商品詳細レイアウトとデフォルトで登録されているデータと比較し、<BR>
   * パーツコードに過不足がないことを確認します<BR>
   * 
   * @param displayList
   * @param unDisplayList
   * @param defaultLayoutList
   * @return パーツコードの過不足有無
   */
  public boolean isNotExistPartsCode(List<CommodityCommonDetailBean> displayList, List<CommodityCommonDetailBean> unDisplayList,
      List<CommodityLayout> defaultLayoutList) {

    // パーツコードの数が等しいことを確認
    if ((displayList.size() + unDisplayList.size()) != defaultLayoutList.size()) {
      return true;
    }

    // 各ショップに設定されている表示用商品レイアウト情報を比較用オブジェクトにセット
    SortedMap<String, String> shopMap = new TreeMap<String, String>();
    for (CommodityCommonDetailBean cd : displayList) {
      shopMap.put(cd.getPartsCode(), cd.getPartsName());
    }

    // 各ショップに設定されている非表示用商品レイアウト情報を比較用オブジェクトにセット
    for (CommodityCommonDetailBean cd : unDisplayList) {
      shopMap.put(cd.getPartsCode(), cd.getPartsName());
    }

    // デフォルトで登録されている商品レイアウト情報を比較用オブジェクトにセット
    List<String> defaultList = new ArrayList<String>();
    for (CommodityLayout cl : defaultLayoutList) {
      defaultList.add(cl.getPartsCode());
    }

    // ショップごとの商品レイアウトとデフォルトの商品レイアウトを比較
    for (String str : defaultList) {
      if (!shopMap.containsKey(str)) {
        return true;
      }
    }

    return false;
  }

  /**
   * 画面に表示するボタンの制御を行います。
   */
  public void setDisplayControl() {
    BackLoginInfo login = getLoginInfo();
    CommodityCommonBean nextBean = (CommodityCommonBean) getRequestBean();

    // 更新ボタン表示に必要な権限があるかどうか
    boolean updateButtonDisplayFlg = false;
    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      updateButtonDisplayFlg = Permission.COMMODITY_UPDATE.isGranted(getLoginInfo());
    } else {
      updateButtonDisplayFlg = Permission.COMMODITY_UPDATE.isGranted(getLoginInfo()) && login.isShop();
    }

    if (updateButtonDisplayFlg) {
      nextBean.setRegisterButtonDisplayFlg(getDisplayMessage().getErrors().size() == 0);
      nextBean.setResetButtonDisplayFlg(getDisplayMessage().getErrors().size() != 0);
    } else {
      nextBean.setRegisterButtonDisplayFlg(false);
      nextBean.setResetButtonDisplayFlg(false);
    }

    if (getLoginInfo().isSite()) {
      // 一店舗版のときは検索ボタンを出さない
      nextBean.setSearchButtonDisplayFlg(!getConfig().isOne());
    } else {
      nextBean.setSearchButtonDisplayFlg(false);
    }

    setRequestBean(nextBean);
  }

}
