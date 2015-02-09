package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.StockListSearchCondition;
import jp.co.sint.webshop.service.catalog.StockListSearchInfo;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.StockListBean;
import jp.co.sint.webshop.web.bean.back.catalog.TagBean;
import jp.co.sint.webshop.web.bean.back.catalog.StockListBean.StockListDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1800003:库存管理
 * 
 * @author System Integrator Corp.
 */
public class StockListSearchAction extends WebBackAction<StockListBean> {

	private StockListSearchCondition condition;

	private StockListSearchCondition getCondition() {
		return PagerUtil
				.createSearchCondition(getRequestParameter(), condition);
	}

	@SuppressWarnings("unused")
	private StockListSearchCondition getSearchCondition() {
		return this.condition;
	}

	/**
	 * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
	 * 
	 * @return アクションの実行を認可する場合はtrue
	 */
	@Override
	public boolean authorize() {
		return true;
	}

	@Override
	public void init() {

		condition = new StockListSearchCondition();

	}

	/**
	 * データモデルに格納された入力値の妥当性を検証します。
	 * 
	 * @return 入力値にエラーがなければtrue
	 */
	@Override
	public boolean validate() {
		StockListBean bean = getBean();

		boolean isValid = validateBean(bean);
		if (!isValid) {
			bean.setPagerValue(new TagBean().getPagerValue());
			bean.getList().clear();
		}
		return isValid;
	}

	/**
	 * アクションを実行します。
	 * 
	 * @return アクションの実行結果
	 */
	@SuppressWarnings("unchecked")
	@Override
	public WebActionResult callService() {
		StockListBean bean = getBean();
		CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
		List<StockListDetailBean> list = new ArrayList<StockListDetailBean>();

		condition.setSearchShopCode(bean.getSearchShopCode());
		// ページング情報の追加
		condition = getCondition();
		
		// 去掉首尾空字符
		bean.setSearchSkuCode(bean.getSearchSkuCode().trim());  
		
	  ArrayList skuCodeList = new ArrayList(); //用户输入商品编号列表
	  
		// SKU编号为空时查询全部「前台SKU编号非空验证排除这种情况」
		if (StringUtil.isNullOrEmpty(bean.getSearchSkuCode())) {    
		  //在库品可以更新
		  bean.setUpdateFlg("0");

			// 在库状态一览
		  List<StockListSearchInfo> StockList = service.getStockListInfo(condition);
			for (StockListSearchInfo ss : StockList) {
				StockListDetailBean stockListDetail = new StockListDetailBean();
				setResultStockList(ss, stockListDetail,condition,"0");
				list.add(stockListDetail);
			}
		
		} else {
      String[] skuCode = bean.getSearchSkuCode().split("\r\n");   // SKU编号获得
      // 去除输入的相同SKU编号
      for (int i = 0; i < skuCode.length; i++) {
        // 去掉空行,最多输入20个商品编号
        if (!StringUtil.isNullOrEmpty(skuCode[i]) && i < 20) {
          skuCodeList.add(skuCode[i].trim());
        }
      }
      // 在库品更新检查
      if (!StringUtil.isNullOrEmpty(bean.getCommodityLink())) {
        bean.setUpdateFlg("0");   // 在库品可以更新
      } else {
        bean.setUpdateFlg("1");   // 在库品不可以更新
      }
      condition.setSearchSkuCode(skuCodeList);
      // 商品关联
      condition.setCommodityLink(bean.getCommodityLink());
      // 查询
      List<StockListSearchInfo> StockList = service.getStockListInfo(condition);
      for (StockListSearchInfo ss : StockList) {
        StockListDetailBean stockListDetail = new StockListDetailBean();
        setResultStockList(ss, stockListDetail, condition, bean.getCommodityLink());
        list.add(stockListDetail);
      }
    }
		List<StockListDetailBean> detaillist = new ArrayList<StockListDetailBean>();
		for(StockListDetailBean li:list ){
		  List<CCommodityHeader> headerlist=service.getOriginalCode(li.getCommodityCode());
		  if(headerlist.size()>0){
		    if(StringUtil.hasValue(headerlist.get(0).getOriginalCommodityCode())){
		      continue;
		    }else{
		      detaillist.add(li);
		    }
		  }
		}
		
		PagerValue value = new PagerValue();
		value.setPageSize(detaillist.size());
		bean.setPagerValue(value);
		bean.setList(detaillist);
		
		if(list.size()==0){
	    SearchResult<StockListSearchInfo> result=new SearchResult<StockListSearchInfo>();
	    // 结果件数0件，警告
	    prepareSearchWarnings(result, SearchWarningType.BOTH);
		}
		bean.setMode("");
		
		if(bean.getErrorMsgList().size() > 0){
		  for(String msg : bean.getErrorMsgList()){
		    addErrorMessage(msg);
		  }
		  bean.getErrorMsgList().clear();
		}
		setRequestBean(bean);

		return BackActionResult.RESULT_SUCCESS;
	}

	/**
	 * Action名の取得
	 * 
	 * @return Action名
	 */
	public String getActionName() {
		return Messages
				.getString("web.action.back.catalog.StockListSearchAction.0");
	}

	/**
	 * オペレーションコードの取得
	 * 
	 * @return オペレーションコード
	 */
	public String getOperationCode() {
		return "3180000304";
	}

	private String commodityName="";
	/**
	 * 検索結果を検索結果リスト用Beanにセットします
	 * 
	 * @param ss
	 * @param stockStatusDetail
	 */
	public void setResultStockList(StockListSearchInfo stockList,
			StockListDetailBean stockListDetail,StockListSearchCondition condition,String commodityLink) {
		// ショップコード
		stockListDetail.setShopCode(stockList.getShopCode());
		//设置行颜色
		stockListDetail.setRowColor("0");
		if(condition.getSearchSkuCode().size()>0&& commodityLink.equals("1")){
		  for(int i=0;i<condition.getSearchSkuCode().size();i++){
		  //是否有检索sku编号
		    if(stockList.getSkuCode().equals(condition.getSearchSkuCode().get(i))){
		      stockListDetail.setRowColor("1");
		    } 
		  }
		}
		// SKUコード
		stockListDetail.setSkuCode(stockList.getSkuCode());
		//商品编号
		stockListDetail.setCommodityCode(stockList.getCommodityCode());
		
		//商品编号
		if(commodityName.equals("")){
		  commodityName=stockList.getCommodityCode();
		  //商品编号
	    stockListDetail.setCommodityCodeShow(stockList.getCommodityCode());
		}
		//判断是否重复
		if (!commodityName.equals(stockList.getCommodityCode())){
		  stockListDetail.setCommodityCodeShow(stockList.getCommodityCode());
		  commodityName=stockList.getCommodityCode();
		}
		// SKU名稱
		stockListDetail.setSkuname(stockList.getSkuName());
		// 商品名稱
		stockListDetail.setCommodityName(stockList.getCommodityName());
		// EC在庫数量
		stockListDetail.setStockQuantity(stockList.getStockQuantity());
		// EC引当数量
		stockListDetail.setAllocatedQuantity(stockList.getAllocatedQuantity());
		// 予約数量
		stockListDetail.setReservedQuantity(stockList.getReservedQuantity());
		// 予約上限数
		stockListDetail.setReservationLimit(stockList.getReservationLimit());
		// 注文毎予約上限数
		stockListDetail.setOneshotReservationLimit(stockList
				.getOneshotReservationLimit());
		// 安全在庫
		stockListDetail.setStockThreshold(stockList.getStockThreshold());
		// 総在庫
		stockListDetail.setStockTotal(stockList.getStockTotal());
		// TMALL在庫数
		stockListDetail.setStockTmall(stockList.getStockTmall());
		// TMALL引当数
		stockListDetail.setAllocatedTmall(stockList.getAllocatedTmall());
		// 在庫リーバランスフラグ
		stockListDetail.setShareRecalcFlag(stockList.getShareRecalcFlag());
		// EC在庫割合(0-100)
		stockListDetail.setShareRatio(stockList.getShareRatio());
		//在库品区分
		stockListDetail.setOnStockFlag(stockList.getOnStockFlag());
		// 2014/06/10 库存更新对应 ob_卢 add start
		// 京东在库数量
		stockListDetail.setStockJd(stockList.getStockJd());
		// 京东引当数
		stockListDetail.setAllocatedJd(stockList.getAllocatedJd());
		// 京东在库分配比例
		stockListDetail.setShareRatioJd(stockList.getShareRatioJd());
    // 天猫在库分配比例
    stockListDetail.setShareRatioTmall(stockList.getShareRatioTmall());
    // 2014/06/10 库存更新对应 ob_卢 add end
	}
  /**
   * 画面表示に必要な項目を設定・初期化します。

   */
  @Override
  public void prerender() {
    StockListBean bean = (StockListBean) getRequestBean();

    if (getRequestParameter().getPathArgs().length > 0) {
      String completeParam = getRequestParameter().getPathArgs()[0];
      if (completeParam.equals(WebConstantCode.COMPLETE_UPDATE)) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE,
            Messages.getString("web.action.back.catalog.StockListRegister.0")));
      }
    }

    setRequestBean(bean);
  }
}
