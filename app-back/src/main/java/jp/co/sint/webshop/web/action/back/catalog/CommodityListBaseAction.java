package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.domain.DisplayClientType;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.SkuStatus;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.catalog.CommodityListSearchCondition;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityListBean;

/**
 * U1040110:商品マスタのアクションの基底クラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class CommodityListBaseAction extends WebBackAction<CommodityListBean> {

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    CommodityListBean bean = getBean();

    // 検索条件「在庫状況」の初期化
    List<CodeAttribute> searchStockStatusList = new ArrayList<CodeAttribute>();
    for (SkuStatus sk : SkuStatus.values()) {
      searchStockStatusList.add(sk);
    }
    bean.setSearchStockStatusList(searchStockStatusList);

    setBean(bean);
  }

  public void setCondition(CommodityListSearchCondition condition) {
    condition.setSearchShopCode(getBean().getSearchShopCode());
    condition.setSearchCommodityCodeStart(getBean().getSearchCommodityCodeStart());
    condition.setSearchCommodityCodeEnd(getBean().getSearchCommodityCodeEnd());
    condition.setSearchSkuCode(getBean().getSearchSkuCode());
    condition.setSearchCommodityName(getBean().getSearchCommodityName());
    condition.setSearchCommoditySearchWords(getBean().getSearchCommoditySearchWords());
    condition.setSearchSaleStartDateRangeFrom(getBean().getSearchSaleStartDateRangeFrom());
    condition.setSearchSaleStartDateRangeTo(getBean().getSearchSaleStartDateRangeTo());
    condition.setSearchSaleEndDateRangeFrom(getBean().getSearchSaleEndDateRangeFrom());
    condition.setSearchSaleEndDateRangeTo(getBean().getSearchSaleEndDateRangeTo());
    condition.setSearchStockStatus(getBean().getSearchStockStatus());
    condition.setSearchStockQuantityStart(getBean().getSearchStockQuantityStart());
    condition.setSearchStockQuantityEnd(getBean().getSearchStockQuantityEnd());
    condition.setSearchSaleType(getBean().getSearchSaleType());
    condition.setSearchSaleStatus(getBean().getSearchSaleStatus());
    condition.setSearchArrivalGoods(getBean().getSearchArrivalGoods());
    // add by tangwh 2012-11-16 start
    condition.setSearchCommodityType(getBean().getSearchCommodityType());
    condition.setSearchSetcommodityflg(getBean().getSearchSetcommodityflg());
    condition.setCombination(getBean().getCombination());
    // add by tangwh 2012-11-16 end

    // 検索条件「表示クライアント」の設定
    if (StringUtil.hasValueAllOf(getBean().getSearchDisplayClientPc(), getBean().getSearchDisplayClientMobile())) {
      // PC、携帯両方チェックされている場合、表示クライアント区分=(すべて,PCのみ,携帯のみ)を検索
      condition.setSearchDisplayClientType(new String[] {
          DisplayClientType.ALL.getValue(), DisplayClientType.PC.getValue(), DisplayClientType.MOBILE.getValue()
      });
    } else if (StringUtil.hasValue(getBean().getSearchDisplayClientPc())) {
      // PCだけチェックされている場合、表示クライアント区分=(すべて,PCのみ)を検索
      condition.setSearchDisplayClientType(new String[] {
          DisplayClientType.ALL.getValue(), DisplayClientType.PC.getValue()
      });
    } else if (StringUtil.hasValue(getBean().getSearchDisplayClientMobile())) {
      // 携帯だけチェックされている場合、表示クライアント区分=(すべて,携帯のみ)を検索
      condition.setSearchDisplayClientType(new String[] {
          DisplayClientType.ALL.getValue(), DisplayClientType.MOBILE.getValue()
      });
    }

    if (StringUtil.hasValue(getBean().getSearchSafetyStock())) {
      condition.setNotSafetyStock(true);
    }
  }

  /**
   * 画面に表示するボタンの制御を行います。
   * 
   * @param bean
   */
  public void setDisplayControl(CommodityListBean bean) {
    if (getLoginInfo().isSite() && !getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      bean.setDisplayShopList(true);
      bean.setDisplayShopNameColumn(true);
    }

    if (Permission.COMMODITY_UPDATE.isGranted(getLoginInfo())) {
      bean.setDisplayUpdateButton(true);
    }
    if (Permission.COMMODITY_DELETE.isGranted(getLoginInfo())) {
      bean.setDisplayDeleteButton(true);
    }
    if (Permission.COMMODITY_DATA_IO.isGranted(getLoginInfo())) {
      bean.setDisplayCsvExportButton(true);
    }
  }

}
