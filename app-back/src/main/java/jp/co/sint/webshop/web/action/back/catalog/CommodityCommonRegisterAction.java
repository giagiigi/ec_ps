package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.PartsCode;
import jp.co.sint.webshop.data.dto.CommodityLayout;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityCommonBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityCommonBean.CommodityCommonDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

import org.apache.log4j.Logger;

/**
 * U1040810:商品詳細レイアウトのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommodityCommonRegisterAction extends CommodityCommonBaseAction {

  /** 必ず最上位に表示しなくてはいけない項目 */
  private static final PartsCode TOP_PART = PartsCode.MAIN;

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth = false;
    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      auth = Permission.COMMODITY_UPDATE.isGranted(getLoginInfo());
    } else {
      auth = Permission.COMMODITY_UPDATE.isGranted(getLoginInfo()) && getLoginInfo().isShop();
    }

    return auth;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    Logger logger = Logger.getLogger(this.getClass());

    CommodityCommonBean reqBean = getBean();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    if (getLoginInfo().isShop() || getConfig().isOne()) {
      reqBean.setSearchShopCode(getLoginInfo().getShopCode());
    }

    List<CommodityLayout> layoutList = new ArrayList<CommodityLayout>();

    // 表示用データの詰め替え処理
    List<CommodityCommonDetailBean> displayList = reqBean.getDisplayList();
    if (displayList.size() > 0 && StringUtil.hasValue(displayList.get(0).getPartsCode())) {
      for (CommodityCommonDetailBean cd : displayList) {
        createCommodityLayoutdao(service, layoutList, cd, reqBean);
      }
    }

    // 非表示用データの詰め替え処理
    List<CommodityCommonDetailBean> unDisplayList = reqBean.getUnDisplayList();
    if (unDisplayList.size() > 0 && StringUtil.hasValue(unDisplayList.get(0).getPartsCode())) {
      for (CommodityCommonDetailBean cd : unDisplayList) {
        createCommodityLayoutdao(service, layoutList, cd, reqBean);
      }
    }

    ServiceResult result = service.registerCommodityLayout(layoutList);
    if (result.hasError()) {
      logger.error(Messages.log("web.action.back.catalog.CommodityCommonRegisterAction.0"));
      return BackActionResult.SERVICE_ERROR;
    }

    setNextUrl("/app/catalog/commodity_common/complete/" + WebConstantCode.COMPLETE_UPDATE);

    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * @param service
   * @param layoutList
   * @param cd
   */
  private void createCommodityLayoutdao(CatalogService service, List<CommodityLayout> layoutList, CommodityCommonDetailBean cd,
      CommodityCommonBean reqBean) {
    CommodityLayout layout = new CommodityLayout();
    layout.setShopCode(reqBean.getSearchShopCode());
    layout.setPartsCode(cd.getPartsCode());
    layout.setDisplayFlg(Long.parseLong(cd.getDisplayType()));
    layout.setDisplayOrder(Long.parseLong(cd.getDisplayOrder()));

    CommodityLayout tmpLayout = service.getCommodityLayout(reqBean.getSearchShopCode(), cd.getPartsCode());
    layout.setOrmRowid(tmpLayout.getOrmRowid());
    layout.setCreatedUser(tmpLayout.getCreatedUser());
    layout.setCreatedDatetime(tmpLayout.getCreatedDatetime());
    layout.setUpdatedUser(getLoginInfo().getLoginId());
    layout.setUpdatedDatetime(tmpLayout.getUpdatedDatetime());
    layoutList.add(layout);
  }

  /**
   * ドラッグアンドドロップ可能な形式に変換して返します。
   * 
   * @param source
   *          元になるリスト
   * @param display
   *          表示部用ならtrue、非表示部用ならfalse
   * @return
   */
  private List<CommodityCommonDetailBean> createDragableList(List<CommodityCommonDetailBean> source, boolean display) {
    List<CommodityCommonDetailBean> dest = new ArrayList<CommodityCommonDetailBean>();

    int index = 0;
    for (CommodityCommonDetailBean ccdb : source) {
      CommodityCommonDetailBean detailBean = new CommodityCommonDetailBean();
      if (display) {
        detailBean.setDisplayType("li1");
      } else {
        detailBean.setDisplayType("li2");
      }
      detailBean.setPartsCode(ccdb.getPartsCode());
      detailBean.setUpdatedDatetime(ccdb.getUpdatedDatetime());
      detailBean.setDisplayOrder("" + index);
      index++;
      dest.add(detailBean);
    }
    return dest;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    CommodityCommonBean bean = getBean();

    List<CommodityCommonDetailBean> displayList = bean.getDisplayList();
    boolean result = false;

    if (displayList.size() == 0) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.COMMODITY_LAYOUT_TOP_PARTS_ERROR, TOP_PART.getName()));
      result = false;
    } else {
      CommodityCommonDetailBean topDetail = displayList.get(0);
      PartsCode partsCode = PartsCode.fromValue(topDetail.getPartsCode());

      if (partsCode != null && partsCode.equals(TOP_PART)) {
        result = true;
      } else {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.COMMODITY_LAYOUT_TOP_PARTS_ERROR, TOP_PART.getName()));
        result = false;
      }
    }

    if (!result) {
      bean.setDisplayList(createDragableList(bean.getDisplayList(), true));
      bean.setUnDisplayList(createDragableList(bean.getUnDisplayList(), false));
      setBean(bean);
    }

    return result;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityCommonRegisterAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104081003";
  }

}
