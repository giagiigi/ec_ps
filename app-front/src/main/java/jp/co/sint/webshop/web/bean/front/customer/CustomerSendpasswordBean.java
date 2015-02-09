package jp.co.sint.webshop.web.bean.front.customer;

import jp.co.sint.webshop.data.attribute.Email;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2030410:パスワード再登録URL送信のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerSendpasswordBean extends UIFrontBean {

	/** serial version uid */
	private static final long serialVersionUID = 1L;

	/** メールアドレス */
	@Required
	@Length(256)
	@Email
	@Metadata(name = "メールアドレス")
	private String email;

	private boolean sendMail = false;



	/**
	 * @return the sendMail
	 */
	public boolean isSendMail() {
		return sendMail;
	}

	/**
	 * @param sendMail the sendMail to set
	 */
	public void setSendMail(boolean sendMail) {
		this.sendMail = sendMail;
	}

	/**
	 * emailを取得します。
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * emailを設定します。
	 * 
	 * @param email
	 *            email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * サブJSPを設定します。
	 */
	@Override
	public void setSubJspId() {
	}

	/**
	 * リクエストパラメータから値を取得します。
	 * 
	 * @param reqparam
	 *            リクエストパラメータ
	 */
	public void createAttributes(RequestParameter reqparam) {
		reqparam.copy(this);
	}

	/**
	 * モジュールIDを取得します。
	 * 
	 * @return モジュールID
	 */
	public String getModuleId() {
		return "U2030410";
	}

	/**
	 * モジュール名を取得します。
	 * 
	 * @return モジュール名
	 */
	public String getModuleName() {
		return Messages
				.getString("web.bean.front.customer.CustomerSendpasswordBean.0");
	}
}
