package jp.co.sint.webshop.web.message.back.customer;

import jp.co.sint.webshop.message.MessageType;

/**
 * メッセージのenumです。
 * 
 * @author System Integrator Corp.
 */
public enum CustomerErrorMessage implements MessageType {

	/** 本人アドレス削除エラーメッセージ */
	DELETE_SELF_ADDRESS_ERROR("delete_self_address_error"),

	/** 受注未確定顧客削除エラーメッセージ */
	DELETE_CUSTOMER_ORDER_ERROR("delete_customer_order_error"),

	/** 本人アドレス編集エラーメッセージ */
	EDIT_SELF_ADDRESS_ERROR("edit_self_address_error"),

	/** 情報メール送信対象未存在エラーメッセージ */
	INFORMATION_MAIL_NO_DATA("information_mail_no_data"),

	/** 情報メール送信対象未存在エラーメッセージ */
	INFORMATION_MAIL_ERROR_DATA("information_mail_error_data"),

	/** ポイント最大値超過エラー */
	POINT_OVERFLOW("point_overflow"),

	/** ポイント追加失敗エラー */
	POINT_INSERT_FAILURE_ERROR("point_insert_failure"),

	/** パスワードポリシーエラー */
	PASSWORD_POLICY_ERROR("password_policy_error"),

	/** ポイントシステム停止中登録エラー */
	POINT_SYSTEM_DISABLED_REGISTER("point_system_disabled_register"),

	/** ポイントシステム停止中削除エラー */
	POINT_SYSTEM_DISABLED_DELETE("point_system_disabled_delete"),

	/** 情報メール未存在エラー */
	NOT_EXIST_INFORMATION_MAIL_ERROR("not_exist_information_mail_error"),

	/** ゼロポイント追加エラー */
	POINT_INSERT_ZERO_ERROR("point_insert_zero_error"),

	// add by V10-CH start
	/** ポイントシステム停止中登録エラー */
	COUPON_SYSTEM_DISABLED_REGISTER("coupon_system_disabled_register"),

	/** ポイントシステム停止中登録エラー */
	COUPON_ISSUE_NOT_EXISTS_REGISTER("coupon_issue_not_exists_register"),

	/** ポイントシステム停止中登録エラー */
	OUT_OF_COUPON_ISSUE_DATE("out_of_coupon_issue_date"),

	// add by V10-CH end

	NOT_IS_NULL("手机号与验证码不能为空！");

	private String messagePropertyId;

	private CustomerErrorMessage(String messagePropertyId) {
		this.messagePropertyId = messagePropertyId;
	}

	/**
	 * メッセージプロパティIDを取得します。
	 * 
	 * @return メッセージプロパティID
	 */
	public String getMessagePropertyId() {
		return this.messagePropertyId;
	}

	/**
	 * メッセージリソースを取得します。
	 * 
	 * @return メッセージリソース
	 */
	public String getMessageResource() {
		return "jp.co.sint.webshop.web.message.back.customer.CustomerMessages";
	}

}
