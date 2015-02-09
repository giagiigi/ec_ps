package jp.co.sint.webshop.configure;

import java.io.Serializable;

import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * 顧客情報登録時に表示させるサンプルデータです。 applicationContext-configure.xmlの内容を読み込んで初期化します。
 * 
 * @author System Integrator Corp.
 */
public class StockValue implements Serializable {

  private static final long serialVersionUID = 1L;
  private String stockMessage="";
/**
 * @param stockMessage the stockMessage to set
 */
public void setStockMessage(String stockMessage) {
	this.stockMessage = stockMessage;
}
/**
 * @return the stockMessage
 */
public String getStockMessage() {
	return StringUtil.coalesce(CodeUtil.getEntry("stockMessage"), stockMessage);
}
}
