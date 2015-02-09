package jp.co.sint.webshop.service.catalog;

import java.util.Date;

/**
 * 消費税設定
 * 
 * @author System Integrator Corp.
 */
public interface TaxUtil {

  /**
   * システム日付時点の消費税率を取得します。
   * 
   * @return システム日付時点の消費税率
   */
  Long getTaxRate();

  /**
   * 日付を指定して、その時点の消費税率を取得します。
   * 
   * @return 指定した時点の消費税率
   */
  Long getTaxRate(Date date);

  /**
   * システム日付時点の消費税率適用開始日を取得します。
   * 
   * @return システム日付時点の消費税率適用開始日
   */
  Date getAppliedStartDate();

  /**
   * 日付を指定して、その時点の消費税率適用開始日を取得します。
   * 
   * @return 指定した時点の消費税率適用開始日
   */
  Date getAppliedStartDate(Date date);

}
