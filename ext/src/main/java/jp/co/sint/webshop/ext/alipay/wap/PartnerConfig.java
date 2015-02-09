package jp.co.sint.webshop.ext.alipay.wap;

import java.io.Serializable;


public class PartnerConfig implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//合作商户ID。用签约支付宝账号登录ms.alipay.com后，在账户信息页面获取。
    private String partner;
    //商户收款的支付宝账号
    private String seller;
    // 商户（MD5）KEY
    private String key;
    // 创建交易请求URL
    private String reqUrl;
    // 返回商户url
    private String wapCallback;
    // 通知的url
    private String wapNotify;
    // 商品名称
    //private String subject;
    // 商品价格
    //private String totalFee;
    
	/**
	 * @return the partner
	 */
	public String getPartner() {
		return partner;
	}
	/**
	 * @param partner the partner to set
	 */
	public void setPartner(String partner) {
		this.partner = partner;
	}
	/**
	 * @return the seller
	 */
	public String getSeller() {
		return seller;
	}
	/**
	 * @param seller the seller to set
	 */
	public void setSeller(String seller) {
		this.seller = seller;
	}
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * @return the reqUrl
	 */
	public String getReqUrl() {
		return reqUrl;
	}
	/**
	 * @param reqUrl the reqUrl to set
	 */
	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}
	/**
	 * @return the wapCallback
	 */
	public String getWapCallback() {
		return wapCallback;
	}
	/**
	 * @param wapCallback the wapCallback to set
	 */
	public void setWapCallback(String wapCallback) {
		this.wapCallback = wapCallback;
	}
	/**
	 * @return the wapNotify
	 */
	public String getWapNotify() {
		return wapNotify;
	}
	/**
	 * @param wapNotify the wapNotify to set
	 */
	public void setWapNotify(String wapNotify) {
		this.wapNotify = wapNotify;
	}
}


