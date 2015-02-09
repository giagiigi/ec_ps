package jp.co.sint.webshop.data.domain;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

public enum NewReserveCommodityType5 implements CodeAttribute{



	/** 「清仓商品」を表す値です。 */
	Asahi("是", "1"),

	/** 「普通商品」を表す値です。 */
	GENERALGOODS("不是", "9");

	private String name;

	private String value;

	private NewReserveCommodityType5(String name, String value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * コード名称を返します。
	 * 
	 * @return コード名称
	 */
	public String getName() {
		return StringUtil.coalesce(CodeUtil.getName(this), this.name);
	}

	/**
	 * コード値を返します。
	 * 
	 * @return コード値
	 */
	public String getValue() {
		return StringUtil.coalesce(CodeUtil.getValue(this), this.value);
	}

	/**
	 * Long型のコード値を返します。
	 * 
	 * @return コード値
	 */
	public Long longValue() {
		return Long.valueOf(this.value);
	}

	/**
	 * 指定されたコード名を持つ入荷お知らせ可能フラグを返します。
	 * 
	 * @param name
	 *            コード名
	 * @return 入荷お知らせ可能フラグ
	 */
	public static ReserveCommodityType3 fromName(String name) {
		for (ReserveCommodityType3 p : ReserveCommodityType3.values()) {
			if (p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * 指定されたコード値を持つ入荷お知らせ可能フラグを返します。
	 * 
	 * @param value
	 *            コード値
	 * @return 入荷お知らせ可能フラグ
	 */
	public static ReserveCommodityType3 fromValue(String value) {
		for (ReserveCommodityType3 p : ReserveCommodityType3.values()) {
			if (p.getValue().equals(value)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * 指定されたコード値を持つ入荷お知らせ可能フラグを返します。
	 * 
	 * @param value
	 *            コード値
	 * @return 入荷お知らせ可能フラグ
	 */
	public static ReserveCommodityType3 fromValue(Long value) {
		return fromValue(Long.toString(value));
	}

	/**
	 * 指定されたコード値が有効かどうかを返します。
	 * 
	 * @param value
	 *            コード値
	 * @return コード値が有効であればtrue
	 */
	public static boolean isValid(String value) {
		if (StringUtil.hasValue(value)) {
			for (ReserveCommodityType3 p : ReserveCommodityType3.values()) {
				if (p.getValue().equals(value)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 指定されたコード値が有効かどうかを返します。
	 * 
	 * @param value
	 *            コード値
	 * @return コード値が有効であればtrue
	 */
	public static boolean isValid(Long value) {
		return isValid(Long.toString(value));
	}


}
