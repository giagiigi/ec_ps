package jp.co.sint.webshop.configure;

import java.io.Serializable;

/**
 * 広告に対応する
 * 
 * @author System Integrator Corp.
 */
public class CouponNoticeValue implements Serializable {

	private static final long serialVersionUID = 1L;

	// 优惠券开始时间
	private Long beforeDayStart;

	// 优惠券结束时间
	private Long beforeDayEnd;

	// 优惠券开始类型
	private Long beforeStartType;

	// 优惠券结束类型
	private Long beforeEndType;

	/**
	 * @return the beforeDayStart
	 */
	public Long getBeforeDayStart() {
		return beforeDayStart;
	}

	/**
	 * @param beforeDayStart the beforeDayStart to set
	 */
	public void setBeforeDayStart(Long beforeDayStart) {
		this.beforeDayStart = beforeDayStart;
	}

	/**
	 * @return the beforeDayEnd
	 */
	public Long getBeforeDayEnd() {
		return beforeDayEnd;
	}

	/**
	 * @param beforeDayEnd the beforeDayEnd to set
	 */
	public void setBeforeDayEnd(Long beforeDayEnd) {
		this.beforeDayEnd = beforeDayEnd;
	}

	/**
	 * @return the beforeStartType
	 */
	public Long getBeforeStartType() {
		return beforeStartType;
	}

	/**
	 * @param beforeStartType the beforeStartType to set
	 */
	public void setBeforeStartType(Long beforeStartType) {
		this.beforeStartType = beforeStartType;
	}

	/**
	 * @return the beforeEndType
	 */
	public Long getBeforeEndType() {
		return beforeEndType;
	}

	/**
	 * @param beforeEndType the beforeEndType to set
	 */
	public void setBeforeEndType(Long beforeEndType) {
		this.beforeEndType = beforeEndType;
	}
}
