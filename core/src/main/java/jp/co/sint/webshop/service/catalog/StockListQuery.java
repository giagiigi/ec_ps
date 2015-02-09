package jp.co.sint.webshop.service.catalog;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.domain.RatioType;


public final class StockListQuery extends AbstractQuery<StockListSearchInfo> {

	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = 1L;

	public static final String BASE_QUERY = " SELECT S.SHOP_CODE, " // ショップコード
			+ " S.SKU_CODE, " // SKUコード
			+ " CCD.SKU_NAME, " // SKU名称
			+ " S.COMMODITY_CODE, " // 商品コード
			+ " CCE.ON_STOCK_FLAG , " //在库品区分
			+ " S.STOCK_QUANTITY, " // EC在庫数量
			+ " S.ALLOCATED_QUANTITY, " // EC引当数量
			+ " S.RESERVED_QUANTITY, " // 予約数量
			+ " S.RESERVATION_LIMIT, " // 予約上限数
			+ " S.ONESHOT_RESERVATION_LIMIT, " // 注文毎予約上限数
			+ " S.STOCK_THRESHOLD, " // 安全在庫

			+ " D.STOCK_RATIO AS SHARE_RATIO, " //EC在分配比例
	    + " E.STOCK_RATIO AS SHARE_RATIO_JD, " //京东在库分配比例
	    + " F.STOCK_RATIO AS SHARE_RATIO_TMALL, " //天猫在库分配比例
      + " GET_ALL_STOCK(S.COMMODITY_CODE) AS STOCK_TOTAL, " // 总库存
      + " GET_TMALL_STOCK(S.COMMODITY_CODE) AS STOCK_TMALL, " // TMALL在庫数
      + " GET_TMALL_ALLOCATED_STOCK(S.COMMODITY_CODE) AS ALLOCATED_TMALL, " // TMALL引当数
      + " GET_JD_STOCK(S.COMMODITY_CODE) AS STOCK_JD, " // 京东在庫数
      + " GET_JD_ALLOCATED_STOCK(S.COMMODITY_CODE) AS ALLOCATED_JD, " // 京东引当数

			+ " S.SHARE_RECALC_FLAG, " // 在庫リーバランスフラグ
			+ " S.ORM_ROWID, " // データ行ID
			+ " S.CREATED_USER," // 作成ユーザ
			+ " S.CREATED_DATETIME, " // 作成日時
			+ " S.UPDATED_USER, " // 更新ユーザ
			+ " S.UPDATED_DATETIME " // 更新日時
			+ " FROM STOCK S " 
			+ " LEFT OUTER JOIN C_COMMODITY_DETAIL CCD ON ( CCD.SKU_CODE = S.SKU_CODE and S.SHOP_CODE=CCD.SHOP_CODE ) "
			+ "      INNER JOIN C_COMMODITY_HEADER CCH ON CCH.COMMODITY_CODE = S.SKU_CODE AND (CCH.SET_COMMODITY_FLG  <> 1 OR CCH.SET_COMMODITY_FLG IS NULL) "
			+ " LEFT OUTER JOIN C_COMMODITY_EXT CCE  ON( CCD.COMMODITY_CODE = CCE.COMMODITY_CODE and CCD.SHOP_CODE=CCE.SHOP_CODE) " 
      + " LEFT OUTER JOIN STOCK_RATIO D ON (CCD.COMMODITY_CODE = D.COMMODITY_CODE AND CCD.SHOP_CODE = D.SHOP_CODE"
          +	"  AND D.RATIO_TYPE = " + RatioType.EC.getValue() + ")"
      + " LEFT OUTER JOIN STOCK_RATIO E ON (CCD.COMMODITY_CODE = E.COMMODITY_CODE AND CCD.SHOP_CODE = E.SHOP_CODE"
          + "  AND E.RATIO_TYPE = " + RatioType.JD.getValue() + ")"
      + " LEFT OUTER JOIN STOCK_RATIO F ON (CCD.COMMODITY_CODE = F.COMMODITY_CODE AND CCD.SHOP_CODE = F.SHOP_CODE"
          + "  AND F.RATIO_TYPE = " + RatioType.TMALL.getValue() + ")"
			+ " WHERE 1 = 1";
	//根据SKU_CODE查询COMMODITY_CODE
	public static final String COMMODITY_CODE_QUERY="SELECT S.SKU_CODE,S.COMMODITY_CODE FROM STOCK a WHERE 1 = 1";
	
	public StockListQuery(StockListSearchCondition condition, String query) {
		StringBuilder builder = new StringBuilder();
		List<Object> params = new ArrayList<Object>();

		final String sql = query;
		if (condition != null) { // 検索条件設定

		  if(condition.getSearchCommodityCode().size()==0){
  			for(int i=0;i<condition.getSearchSkuCode().size();i++){
  			  if(i==0){
  		       builder.append(" AND ( S.SKU_CODE = ?");
  		       params.add(condition.getSearchSkuCode().get(i));
  			  }else{
            builder.append(" OR S.SKU_CODE = ? ");
            params.add(condition.getSearchSkuCode().get(i));
          }
  			  if((i+1)==condition.getSearchSkuCode().size()){
  			    builder.append(" OR S.SKU_CODE = ? )");
  			    params.add(condition.getSearchSkuCode().get(i));
  			  }
  			}
			}else {
        for(int i=0;i<condition.getSearchCommodityCode().size();i++){
          if(i==0){
             builder.append(" AND ( S.COMMODITY_CODE = ?");
             params.add(condition.getSearchCommodityCode().get(i));
          }else{
            builder.append(" OR S.COMMODITY_CODE = ? ");
            params.add(condition.getSearchCommodityCode().get(i));
          }
          if((i+1)==condition.getSearchCommodityCode().size()){
            builder.append(" OR S.COMMODITY_CODE = ? )");
            params.add(condition.getSearchCommodityCode().get(i));
          }
        }
      }
		}
		builder.append(" ORDER BY S.COMMODITY_CODE,S.SKU_CODE ");
		setSqlString(sql + builder.toString());
		setParameters(params.toArray());

		Logger logger = Logger.getLogger(this.getClass());
		logger.info("sql:" + sql + builder.toString());
	}

	@Override
	public Class<StockListSearchInfo> getRowType() {
		return StockListSearchInfo.class;
	}

}
