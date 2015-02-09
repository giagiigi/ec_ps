package jp.co.sint.webshop.service.data.csv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.csv.CsvImportException;
import jp.co.sint.webshop.data.csv.CsvUtil;
import jp.co.sint.webshop.data.csv.sql.SqlImportDataSource;
import jp.co.sint.webshop.data.dao.NewCouponRuleDao;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;

import org.apache.log4j.Logger;

public class PublicCouponImportDataSource extends SqlImportDataSource<PublicCouponCsvSchema, PublicCouponImportCondition> {

  private NewCouponRule couponRule = null;
  
  private PreparedStatement insertStatement = null;
  
  private Connection connection = null;

  private String[] validateTarget = new String[] { "couponCode","couponName","minUseOrderAmount","issueReason","memo","couponNameEn","couponNameJp","applicableArea","minUseStartDatetime","minUseEndDatetime","personalUseLimit","siteUseLimit","maxUseOrderAmount"};
  
//  private String isCustomerExistsQuery = "SELECT CUSTOMER_CODE FROM CUSTOMER WHERE CUSTOMER_CODE = ? AND CUSTOMER_STATUS = ?";

  @Override
  protected void initializeResources() {
    String insertQuery = "INSERT INTO new_coupon_rule ("
           + " coupon_code, coupon_name, coupon_name_en, coupon_name_jp, coupon_type,"
           + " coupon_issue_type, min_issue_order_amount, min_issue_start_datetime, min_issue_end_datetime, "
           + " coupon_proportion, coupon_amount, min_use_order_amount, min_use_start_datetime, min_use_end_datetime,  "
           // 20131017 txw add start
           + " personal_use_limit, site_use_limit, issue_reason, memo, applicable_objects, applicable_area, object_commodities, max_use_order_amount,before_after_discount_type,use_type,object_brand,object_category,"
           + " orm_rowid, created_user, created_datetime, updated_user, updated_datetime) "
           + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ?,?,?," 
           // 20131017 txw add end
    	     + SqlDialect.getDefault().getNextvalNOprm("NEW_COUPON_RULE_SEQ") + ",?,?,?,?)";
    Logger logger = Logger.getLogger(this.getClass());
    logger.debug("INSERT statement: " + insertQuery);
    
    try {
        connection = getConnection();
        insertStatement = connection.prepareStatement(insertQuery);
      } catch (Exception e) {
        throw new DataAccessException(e);
      }
  }

  public ValidationSummary validate(List<String> csvLine) {
    ValidationSummary summary = new ValidationSummary();
 
    try {
      couponRule = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), NewCouponRule.class);
    } catch (Exception e) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE)));
    }

    summary.getErrors().addAll(BeanValidator.partialValidate(couponRule, validateTarget).getErrors());
    
    // 判断顾客别优惠券发行规则信息的发行方式是否是【公共发行】
    if(couponRule.getCouponType() ==null ||couponRule.getCouponType() != 2){
      summary.getErrors().add(new ValidationResult(null, null, "顾客别优惠券发行规则的发行方式不是「公共发行」或为空，优惠券发行履历取入失败。"));
      return summary;
    }
    
    //判断优惠券发行类别
    if(couponRule.getCouponIssueType() ==null || (couponRule.getCouponIssueType() != 0 && couponRule.getCouponIssueType() != 1)){
      summary.getErrors().add(new ValidationResult(null, null, "优惠券发行类别只能是0或者1，优惠券发行履历取入失败。"));
      return summary;
    }
    
    
    //判断优惠券发行方式适用对象
    if(couponRule.getApplicableObjects()==null || (couponRule.getApplicableObjects() != 0 && couponRule.getApplicableObjects() != 1)){
      summary.getErrors().add(new ValidationResult(null, null, "优惠券发行方式适用对象只能是0或者1，优惠券发行履历取入失败。"));
      return summary;
    }
    
    
    //判断优惠比例
    if(couponRule.getCouponIssueType() != null && couponRule.getCouponIssueType() == 0){
      if(couponRule.getCouponProportion() == null || couponRule.getCouponProportion()<=0 || couponRule.getCouponProportion()>=100  ){
        summary.getErrors().add(new ValidationResult(null, null, "优惠券比例只能在0到100之间且不能为空，优惠券发行履历取入失败。"));
        return summary;
      }
    }
    
    //判断最小优惠金额
    if(couponRule.getMinIssueOrderAmount()!=null && Double.valueOf(couponRule.getMinIssueOrderAmount().toString()) <=0){
      summary.getErrors().add(new ValidationResult(null, null, "优惠券最小优惠金额不能为负数，优惠券发行履历取入失败。"));
      return summary;
    }
    
    
    //判断使用最小购买金额
    if(couponRule.getMinUseOrderAmount()!=null && Double.valueOf(couponRule.getMinUseOrderAmount().toString()) <=0){
      summary.getErrors().add(new ValidationResult(null, null, "优惠券最小购买金额不能为负数，优惠券发行履历取入失败。"));
      return summary;
    }
    
    //判断使用最大购买金额
    if((couponRule.getMaxUseOrderAmount()!=null && Double.valueOf(couponRule.getMaxUseOrderAmount().toString()) <=0) || (couponRule.getMaxUseOrderAmount()!=null && Double.valueOf(couponRule.getMaxUseOrderAmount().toString()) < Double.valueOf(couponRule.getMinUseOrderAmount().toString()))){
      summary.getErrors().add(new ValidationResult(null, null, "优惠券最大购买金额不能为负数，且不能比最小购买金额小，优惠券发行履历取入失败。"));
      return summary;
    }
    
    //判断个人最大利用回数
    if(Double.valueOf(couponRule.getPersonalUseLimit().toString()) <0){
      summary.getErrors().add(new ValidationResult(null, null, "优惠券个人最大利用回数不能为负数，优惠券发行履历取入失败。"));
      return summary;
    }
    
    //判断SITE最大利用回数
    if(Double.valueOf(couponRule.getSiteUseLimit().toString()) <0){
      summary.getErrors().add(new ValidationResult(null, null, "优惠券SITE最大利用回数不能为负数，优惠券发行履历取入失败。"));
      return summary;
    }
    
    //地域多于2个时，中间用；分隔
    if(couponRule.getApplicableArea() != null && couponRule.getApplicableArea().split("\\;").length > 1 ){
      String[] temp = couponRule.getApplicableArea().split("\\;");
      if(temp.length==1){
        summary.getErrors().add(new ValidationResult(null, null, "地域大于2个时中间请用”;“分隔"));
      }     
    }
    
		NewCouponRuleDao dao = DIContainer.getDao(NewCouponRuleDao.class);
	  NewCouponRule newCouponRule = dao.load(couponRule.getCouponCode());

    
	  // 判断媒体优惠券发行规则是否存在
	  if(newCouponRule != null){
	    summary.getErrors().add(new ValidationResult(null, null, "媒体优惠券发行规则信息已存在"));
	    return summary;
	  }
	    
	    
    return summary;
  }

  @Override
  public void executeUpdate(List<String> csvLine) throws SQLException {
		Logger logger = Logger.getLogger(this.getClass());

		try {
			PreparedStatement pstmt = insertStatement;
			List<Object> params = new ArrayList<Object>();
			
			params.add(couponRule.getCouponCode());
			params.add(couponRule.getCouponName());
			params.add(couponRule.getCouponNameEn());
			params.add(couponRule.getCouponNameJp());
			params.add(couponRule.getCouponType());
			params.add(couponRule.getCouponIssueType());
			params.add(couponRule.getMinIssueOrderAmount());
			params.add(couponRule.getMinIssueStartDatetime());
			params.add(couponRule.getMinIssueEndDatetime());
			params.add(couponRule.getCouponProportion());
			params.add(couponRule.getCouponAmount());
			params.add(couponRule.getMinUseOrderAmount());
			params.add(couponRule.getMinUseStartDatetime());
			params.add(couponRule.getMinUseEndDatetime());
			params.add(couponRule.getPersonalUseLimit());
			params.add(couponRule.getSiteUseLimit());
			params.add(couponRule.getIssueReason());
			params.add(couponRule.getMemo());
			params.add(couponRule.getApplicableObjects());
			params.add(couponRule.getApplicableArea());
			params.add(couponRule.getObjectCommodities());
			params.add(couponRule.getMaxUseOrderAmount());
			params.add(0);
			params.add(couponRule.getUseType());
			params.add(couponRule.getObjectBrand());
			// 20131017 txw add start
			params.add(couponRule.getObjectCategory());
	    // 20131017 txw add end
			params.add(ServiceLoginInfo.getInstance().getRecordingFormat());
			params.add(DateUtil.getSysdate());
			params.add(ServiceLoginInfo.getInstance().getRecordingFormat());
			params.add(DateUtil.getSysdate());
			

			logger.debug("INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));

			DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));

			int updCount = pstmt.executeUpdate();
			if (updCount != 1) {
				throw new CsvImportException();
			}
			connection.commit();
		  logger.info("commit 成功");
		} catch (SQLException e) {
			throw new CsvImportException(e);
		} catch (CsvImportException e) {
			throw e;
		} catch (RuntimeException e) {
			throw new CsvImportException(e);
		}
	}

}
