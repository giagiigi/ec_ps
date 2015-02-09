package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.co.sint.webshop.code.SequenceType;
import jp.co.sint.webshop.data.ConcurrencyFailureException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.TransactionManager;
import jp.co.sint.webshop.data.dao.StockDao;
import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.data.dto.CCommodityExt;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.data.dto.StockTemp;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.StockService;
import jp.co.sint.webshop.service.catalog.CatalogQuery;
import jp.co.sint.webshop.service.result.OrderServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceResultImpl;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.StockListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1800003:库存管理
 * 
 * @author System Integrator Corp.
 */
public class StockListRegisterAction extends WebBackAction<StockListBean> {

	/**
	 * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
	 * 
	 * @return アクションの実行を認可する場合はtrue
	 */
	@Override
	public boolean authorize() {
		return Permission.STOCK_MANAGEMENT_UPDATE.isGranted(getLoginInfo());
	}

	/**
	 * アクションを実行します。
	 * 
	 * @return アクションの実行結果
	 */
	@Override
	public WebActionResult callService() {
 		StockListBean reqBean = getBean();
		CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
		//判断有选中的sku编号
		if(!reqBean.getCheckedCode().get(0).equals("")){
		  // 2014/06/10 库存更新对应 ob_卢 add start
		  
	    List<String> tmallApiFailCodeList = new ArrayList<String>();                                      
	    List<String> jdApiFailCodeList = new ArrayList<String>();                                     
	    List<String> stockFailCodeList = new ArrayList<String>();
	    List<String> ecCynchroCodeList = new ArrayList<String>();
	    ServiceResultImpl result = new ServiceResultImpl();
	    
	    result = (ServiceResultImpl) service.updateStock(getLoginInfo().getShopCode(), reqBean.getCheckedCode(), tmallApiFailCodeList,
		      jdApiFailCodeList, stockFailCodeList, ecCynchroCodeList);
	    
	    if (result.hasError()) {
	      for (ServiceErrorContent error : result.getServiceErrorList()) {
	        if (error == OrderServiceErrorContent.ORDER_DOWN_INTERFACE_FAILED) {
	          addErrorMessage("当前京东订单同步失败，请等待一段时间再进行处理。");
	          setRequestBean(getBean());
	          setNextUrl(null);
	          return BackActionResult.RESULT_SUCCESS;
	        }
	      }
	    }
	    if(ecCynchroCodeList.size() > 0){
	      StringBuilder sb = new StringBuilder("以下商品尚未同期至EC系统中：<br/>");
	      createMessage(ecCynchroCodeList, sb);
	      reqBean.getErrorMsgList().add(sb.toString());
	    }
	    if(result.hasError()){
	      addErrorMessage("库存再分配处理失败，错误信息如下：");
	      if(tmallApiFailCodeList.size() > 0){
	        StringBuilder sb = new StringBuilder("以下商品淘宝连协失败:<br/>");
	        createMessage(tmallApiFailCodeList, sb);
	        reqBean.getErrorMsgList().add(sb.toString());
	      }
	      if(jdApiFailCodeList.size() > 0){
	        StringBuilder sb = new StringBuilder("以下商品京东连协失败:<br/>");
	        createMessage(jdApiFailCodeList, sb);
	        reqBean.getErrorMsgList().add(sb.toString());
	      }
	      if(stockFailCodeList.size() > 0){
	        StringBuilder sb = new StringBuilder("以下商品的有效库存小于0:<br/>");
	        createMessage(stockFailCodeList, sb);
	        reqBean.getErrorMsgList().add(sb.toString());
	      }
	    }

	    // 同期化处理
	    List<String> tmallCynchroFailCodeList = new ArrayList<String>();                 
	    List<String> jdCynchroFailCodeList = new ArrayList<String>();       
	    List<String> commodityHasNotCodeList = new ArrayList<String>();
	    StockService stockService = ServiceLocator.getStockService(getLoginInfo());
	    stockService.stockCynchroApi(tmallCynchroFailCodeList, jdCynchroFailCodeList, commodityHasNotCodeList);
	    
	    // 同期化失败 发送邮件
	    if(tmallCynchroFailCodeList.size() > 0 || jdCynchroFailCodeList.size() > 0 || commodityHasNotCodeList.size() > 0){
	      stockService.sendStockCynchroMail(tmallCynchroFailCodeList, jdCynchroFailCodeList, commodityHasNotCodeList);
	    }
      // 2014/06/10 库存更新对应 ob_卢 add end
			// 2014/06/10 库存更新对应 ob_卢 delete start
//
//			ServiceResult authResult = service.updateStockInfo(getLoginInfo().getShopCode(),reqBean.getCheckedCode());
//				if (authResult.hasError()) {
//			
//					for (ServiceErrorContent errorContent : authResult.getServiceErrorList()) {
//						if (errorContent.equals(CatalogServiceErrorContent.STOCK_STOCKTHRESHOLD_ALLOCATEDQUANTITY_ALLOCATEDTMALL_ERROR )){
//						  //安全库存数量+EC引当数+Tmall引当数
//							this.addErrorMessage(WebMessage.get(CatalogErrorMessage.STOCK_STOCKTHRESHOLD_ALLOCATEDQUANTITY_ALLOCATEDTMALL));
//						}else if(errorContent.equals(CatalogServiceErrorContent.STOCK_ALLOCATEDTMALL_STOCKTMALL_ERROR)){
//						  //T-Mall的引当数大于库存数
//							this.addErrorMessage(WebMessage.get(CatalogErrorMessage.STOCK_ALLOCATEDTMALL_STOCKTMALL));
//						}else if(errorContent.equals(CatalogServiceErrorContent.STOCK_THE_ALLOCATION_WAS_NOT_SUCCESSFUL_ERROR)){
//						  //库存在分配未成功
//							this.addErrorMessage(WebMessage.get(CatalogErrorMessage.STOCK_THE_ALLOCATION_WAS_NOT_SUCCESSFUL));
//						}else if(errorContent.equals(OrderServiceErrorContent.ORDER_DOWNLOADING)){
//              //订单下载中
//              this.addErrorMessage(WebMessage.get(CatalogErrorMessage.ORDER_DOWNLOADING));
//						}else if(errorContent.equals(OrderServiceErrorContent.ORDER_DOWN_INTERFACE_FAILED)){
//              //下载淘宝订单失败
//              this.addErrorMessage(WebMessage.get(CatalogErrorMessage.TMALL_DOWNLOADING_ERROR));
//					  }else if(errorContent.equals(CatalogServiceErrorContent.STOCK_TMALL_UP_ERROR)){
//              //上传淘宝商品信息失败
//              this.addErrorMessage(WebMessage.get(CatalogErrorMessage.TMALL_SKUUP_ERROR));
//						}else{
//							return BackActionResult.SERVICE_ERROR;
//						}
//					 }
//					 setRequestBean(reqBean);
//					 setNextUrl(null);
//					 return BackActionResult.RESULT_SUCCESS;
//				 } 
//				//上传淘宝并更新库存
//				int successFLG=updateStockAndUpTmall(getLoginInfo().getShopCode(),reqBean.getCheckedCode());
//				if(successFLG!=0){
//				  setRequestBean(reqBean);
//          setNextUrl(null);
//          return BackActionResult.RESULT_SUCCESS;
//				} else {
//				}
	      // 2014/06/10 库存更新对应 ob_卢 delete end
		}else{
			 this.addErrorMessage(WebMessage.get(CatalogErrorMessage.NOT_STOCK_SKU_SELECTED));
			 setRequestBean(reqBean);
			 setNextUrl(null);
			 return BackActionResult.RESULT_SUCCESS;
		}

		setNextUrl("/app/catalog/stock_list/search/" + WebConstantCode.COMPLETE_UPDATE);
		//20120323 os013 add start
		reqBean.setMode(WebConstantCode.COMPLETE_UPDATE);
		//20120323 os013 add end
		setRequestBean(reqBean);

		return BackActionResult.RESULT_SUCCESS;
	}
  // 2014/06/16 库存更新对应 ob_卢 add start
	 private void createMessage(List<String> apiFailCodeList, StringBuilder sb) {
	    Set<String> apiFailCodeSet = new HashSet<String>();
	    for (String commodityCode : apiFailCodeList) {
	      apiFailCodeSet.add(commodityCode);
	    }

	    int listSize = 0;
	    for (String commodityCode : apiFailCodeSet) {
	      listSize++;
	      sb.append(commodityCode);
	      if (listSize < apiFailCodeSet.size()) {
	        sb.append(",");
	      }
	      if (listSize % 8 == 0) {
	        sb.append("<br/>");
	      }
	    }
	  }
  //2014/06/16 库存更新对应 ob_卢 add end
	/**
	 * データモデルに格納された入力値の妥当性を検証します。
	 * 
	 * @return 入力値にエラーがなければtrue
	 */
	@Override
	public boolean validate() {
		StockListBean reqBean = getBean();
		getBean().setSearchShopCode(getLoginInfo().getShopCode());
	  boolean valid = validateBean(reqBean);
		//20120116 os013 add start
		for (int i = 0; i < reqBean.getCheckedCode().size(); i++) {
      String[] checked = reqBean.getCheckedCode().get(i).toString().split(",");
      //没有被选中时
      if(StringUtil.isNullOrEmpty(reqBean.getCheckedCode().get(i))){
        break;
      }
      Long shareRatio=NumUtil.toLong(checked[2]);
      // 2014/06/10 库存更新对应 ob_卢 add start
      Long shareRatioJd = 0L;
      Long shareRatioTmall = 0L;
      if(checked.length > 4){
        shareRatioJd = NumUtil.toLong(checked[4]);
      }
      if(checked.length > 5){
        shareRatioTmall = NumUtil.toLong(checked[5]);
      }
      //reqBean.setShareRatio(shareRatioJd);
      // 2014/06/10 库存更新对应 ob_卢 add end
      
      reqBean.setShareRatio(shareRatio);
      
       valid = validateBean(reqBean);
      // 2014/06/10 库存更新对应 ob_卢 update start
      //if(shareRatio >100){
      if(shareRatio + shareRatioJd + shareRatioTmall != 100L){
      // 2014/06/10 库存更新对应 ob_卢 update end

        valid=false;
        addErrorMessage(WebMessage.get(CatalogErrorMessage.SHARE_RATIO_NUMERICAL_SIZE));
        return valid;
      }
      return valid;
		}
		//20120116 os013 add end
		
		//2013年7月2日 add by twh start
//		if (reqBean.get){
//		  
//		}
		//2013年7月2日 add by twh end

		return valid;
	}

	/**
	 * Action名の取得
	 * 
	 * @return Action名
	 */
	public String getActionName() {
		return Messages.getString("web.action.back.catalog.StockListRegisterAction.0");
	}
	

	/**
	 * オペレーションコードの取得
	 * 
	 * @return オペレーションコード
	 */
	public String getOperationCode() {
		return "3180000305";
	}
  /**
   * 上传淘宝并更新库存
   * @param shopCode
   * @param checkedCode
   * @return
   */
  public int updateStockAndUpTmall(String shopCode, List<String> checkedCode) {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    TransactionManager txMgr = DIContainer.getTransactionManager();
    int returnFlg=0;
    //在库管理区分
    Long stock_management_type=0L;
    try {
      txMgr.begin(getLoginInfo());
      for (int i = 0; i < checkedCode.size(); i++) {
   
        String[] checked = checkedCode.get(i).toString().split(",");
        // SKUコード
        String skuCode = checked[0];
        
        StockDao sdao = DIContainer.getDao(StockDao.class);
        // 原库存信息取得
        Stock stock = sdao.load(shopCode, skuCode);

        // 安全在庫
        Long stockThreshold = NumUtil.toLong(checked[1]);

        Long shareRatio = NumUtil.toLong(checked[2]);
        // 20120116 os013 add start
        // EC在库割合(0-100)
//        if(!StringUtil.isNullOrEmptyAnyOf(checked[2])){
//          stock.setShareRatio(NumUtil.toLong(checked[2]));
//        }else{
//          stock.setShareRatio(null);
//        }
       
        // 20120116 os013 add end
        
        //20120215 os013 add start
        CCommodityExt extBean=new CCommodityExt();
        //在库品区分
        //在库品区分被选中
        extBean.setOnStockFlag(NumUtil.toLong(checked[3]));
        //在库管理区分
        //在库品时，
        if(extBean.getOnStockFlag().equals(1L)){
          stock_management_type=StockManagementType.WITH_QUANTITY.longValue();//在庫管理（在庫数表示）[2]
        }else{
          stock_management_type=StockManagementType.NOSTOCK.longValue();//在庫なし販売[1]
        }
        extBean.setCommodityCode(stock.getCommodityCode());
        extBean.setShopCode(shopCode);
        extBean.setOrmRowid(DatabaseUtil.generateSequence(SequenceType.C_COMMODITY_EXT_SEQ));
        extBean.setCreatedUser(this.getLoginInfo().getRecordingFormat());
        extBean.setCreatedDatetime(DateUtil.getSysdate());
        extBean.setUpdatedUser(this.getLoginInfo().getRecordingFormat());
        extBean.setUpdatedDatetime(DateUtil.getSysdate());
          //判断在库表中的Commodity_Code在C_Commodity_Ext表中存在
        boolean Existence = service.hasCommodityCode(shopCode,stock.getCommodityCode());
        //20120215 os013 add end
        //既存数据安全库存的值
        Long oldStockThreshold = stock.getStockThreshold();
       
        // 在库品区分检查
        Query query1 = new SimpleQuery(CatalogQuery.ON_STOCK_FLG_CHECK, stock.getSkuCode());
        StockTemp stockFlgCheck = DatabaseUtil.loadAsBean(query1, StockTemp.class);
        
        Long oldOnStockFlg=0L;
        
        //非在库品时
        if(extBean.getOnStockFlag().equals(2L)){
          // 在庫リーバランスフラグ
          stock.setShareRecalcFlag(0L);
          // EC库存比例
          stock.setShareRatio(100L);
          // 
        }
        // 在库区分不存在时
        if (stockFlgCheck != null) {
          // 非在库品时
          oldOnStockFlg=NumUtil.toLong(stockFlgCheck.getStockFlg());
        }
        // 分配条件：库存变化
        if (!(stock.getShareRatio().equals(shareRatio) && oldStockThreshold.equals(stockThreshold)&& oldOnStockFlg.equals(extBean.getOnStockFlag()))) {
          // 比例不一致的情况下，页面的比例放在stock里面
          stock.setShareRatio(shareRatio);
          // 淘宝上传
          int sucessFlg = service.TmallSku_Code_UP(skuCode, stock,extBean.getOnStockFlag(),"2");
          // 上传成功
          if (sucessFlg != 0) {
            // 错误信息上传未成功。
            this.addErrorMessage(WebMessage.get(CatalogErrorMessage.TMALL_SKUUP_ERROR,skuCode));
            returnFlg++;
          }
          
          //更新在库品区分
          if(Existence){
            txMgr.executeUpdate(CatalogQuery.UPDATE_ON_STOCK_FLAG, extBean.getOnStockFlag() , this.getLoginInfo().getRecordingFormat(),
              DateUtil.getSysdate(),stock.getCommodityCode()); 
            txMgr.executeUpdate(CatalogQuery.UPDATE_STOCK_SHARE_RATIO, stock.getShareRatio() , this.getLoginInfo().getRecordingFormat(),
                DateUtil.getSysdate(),stock.getCommodityCode()); 
          }else{
            txMgr.insert(extBean);
          }
          //更新商品Header，在库管理区分
          txMgr.executeUpdate(CatalogQuery.UPDATE_STOCK_MANAGEMENT_TYPE,  stock_management_type, this.getLoginInfo().getRecordingFormat(),
              DateUtil.getSysdate(),stock.getCommodityCode());
          //更新C商品Header导出标志
          txMgr.executeUpdate(CatalogQuery.UPDATE_EXPORT_FLG,  this.getLoginInfo().getRecordingFormat(),
              DateUtil.getSysdate(),stock.getCommodityCode());
        } else {
          // 更新库存信息
          txMgr.update(stock);
          //更新在库品区分
          if(Existence){
            txMgr.executeUpdate(CatalogQuery.UPDATE_ON_STOCK_FLAG, extBean.getOnStockFlag() , this.getLoginInfo().getRecordingFormat(),
                DateUtil.getSysdate(),stock.getCommodityCode());
          }else{
            txMgr.insert(extBean);
          }
          //更新商品Header，在库管理区分
          txMgr.executeUpdate(CatalogQuery.UPDATE_STOCK_MANAGEMENT_TYPE,  stock_management_type, this.getLoginInfo().getRecordingFormat(),
              DateUtil.getSysdate(),stock.getCommodityCode());
          //更新C商品Header导出标志
          txMgr.executeUpdate(CatalogQuery.UPDATE_EXPORT_FLG,  this.getLoginInfo().getRecordingFormat(),
              DateUtil.getSysdate(),stock.getCommodityCode());
        }
      }
      if(returnFlg>0){
        txMgr.rollback();
      }else{
        txMgr.commit();
      }
      return returnFlg;
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return returnFlg;
  }

}
