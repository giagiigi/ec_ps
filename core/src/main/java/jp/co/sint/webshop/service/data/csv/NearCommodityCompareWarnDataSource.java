package jp.co.sint.webshop.service.data.csv;

import java.math.BigDecimal;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.data.dao.CommodityHeaderDao;
import jp.co.sint.webshop.data.dao.CommodityPreviousDayDao;
import jp.co.sint.webshop.data.dao.CommodityProductionDateDao;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.CommodityPreviousDay;
import jp.co.sint.webshop.data.dto.CommodityProductionDate;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;

public class NearCommodityCompareWarnDataSource extends SqlExportDataSource<NearCommodityCompareWarnCsvSchema, NearCommodityCompareWarnCondition> {

  public Query getExportQuery() {
    String sql = "SELECT ('''' || COMMODITY_CODE) AS COMMODITY_CODE, COMMODITY_NAME, '' AS COMMODITY_TYPE FROM COMMODITY_HEADER WHERE SHELF_LIFE_DAYS IS NOT NULL ORDER BY COMMODITY_CODE";
    Query q = new SimpleQuery(sql);
    return q;
  }

  public boolean isNearCommodity(List<String> row, List<CsvColumn> columnList) {
    WebshopConfig config = DIContainer.getWebshopConfig();
    String shopCode = config.getSiteShopCode();
    CommodityPreviousDayDao commodityPreviousDayDao = DIContainer.getDao(CommodityPreviousDayDao.class);
    boolean flg = false;
    String commodityType = "";
    CommodityPreviousDay commodityPreviousDay = null;
    
    for (int i = 0; i < row.size(); i++) {
      String columnName = columnList.get(i).getPhysicalName();
      if (columnName.equals("COMMODITY_CODE") || columnName.equals("commodity_code")) {
        String commodityCode = row.get(i);
        flg = checkCommodity(shopCode, commodityCode.replace("'", ""));
        
        commodityPreviousDay = commodityPreviousDayDao.load(commodityCode.replace("'", ""));
        if(flg) {
          if(commodityPreviousDay == null) {
            commodityType = "新增";
          } else {
            flg = false;
          }
        } else {
          if(commodityPreviousDay != null) {
            commodityType = "删除";
            flg = true;
          }
        }
      }
      
      if (flg && (columnName.equals("COMMODITY_TYPE") || columnName.equals("commodity_type"))) {
        row.set(i, commodityType);
      }
    }

    return flg;
  }

  private boolean checkCommodity(String shopCode, String commodityCode) {
    boolean flg = false;
    CommodityHeaderDao commodityHeaderDao = DIContainer.getDao(CommodityHeaderDao.class);
    CommodityHeader ch = commodityHeaderDao.load(shopCode, commodityCode);
    if (ch != null && ch.getShelfLifeDays() != null) {
      CommodityProductionDateDao commodityProductionDateDao = DIContainer.getDao(CommodityProductionDateDao.class);
      String originalCommodityCode = commodityCode;
      if(StringUtil.hasValue(ch.getOriginalCommodityCode()) || (ch.getSetCommodityFlg() != null && ch.getSetCommodityFlg().equals(SetCommodityFlg.OBJECTIN.longValue())) || ch.getCommodityType().equals(CommodityType.GIFT.longValue())) {
        return false;
      }
      CommodityProductionDate commodityProductionDate = commodityProductionDateDao.load(originalCommodityCode);
      if (commodityProductionDate != null) {
        Long useSurplusDate = ch.getShelfLifeDays()
            - DateUtil.getDaysFromTwoDateString(DateUtil.toDateString(commodityProductionDate.getEarlistDate()), DateUtil
                .getSysdateString());
        Long nearSurplusDate = 0L;

        if (ch.getShelfLifeDays() >= 0 && ch.getShelfLifeDays() <= 365) {
          nearSurplusDate = (new BigDecimal(ch.getShelfLifeDays().toString())).divide(new BigDecimal("4"), 0).longValue();//BigDecimalUtil.divide(ch.getShelfLifeDays(), 4).setScale(0, BigDecimal.ROUND_UP).longValue();
        } else if (ch.getShelfLifeDays() >= 366 && ch.getShelfLifeDays() <= 730) {
          nearSurplusDate = (new BigDecimal(ch.getShelfLifeDays().toString())).divide(new BigDecimal("5"), 0).longValue();// BigDecimalUtil.divide(ch.getShelfLifeDays(), 5).setScale(0, BigDecimal.ROUND_UP).longValue();
        } else {
          nearSurplusDate = (new BigDecimal(ch.getShelfLifeDays().toString())).divide(new BigDecimal("6"), 0).longValue();//BigDecimalUtil.divide(ch.getShelfLifeDays(), 6).setScale(0, BigDecimal.ROUND_UP).longValue();
        }

        if (useSurplusDate <= nearSurplusDate) {
          flg = true;
        }
      }
    }
    return flg;
  }
  
}
