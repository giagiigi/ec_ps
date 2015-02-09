package jp.co.sint.webshop.utility;

import java.util.Date;

/**
 * 日付の期間を表すオブジェクトです。
 * 
 * @author System Integrator Corp.
 */
public class DateRange extends Range<Date> {

  private static final long serialVersionUID = 1L;

  public DateRange() {
    super();
  }

  public DateRange(Date start, Date end) {
    super(start, end);
  }

  @Override
  public Date getStart() {
    return DateUtil.immutableCopy(super.getStart());
  }

  @Override
  public Date getEnd() {
    return DateUtil.immutableCopy(super.getEnd());
  }

  @Override
  public void setStart(Date start) {
    super.setStart(DateUtil.immutableCopy(start));
  }

  @Override
  public void setEnd(Date end) {
    super.setEnd(DateUtil.immutableCopy(end));
  }

}
