package jp.co.sint.webshop.service.data.csv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.CsvImportException;
import jp.co.sint.webshop.data.csv.CsvUtil;
import jp.co.sint.webshop.data.csv.sql.SqlImportDataSource;
import jp.co.sint.webshop.data.dao.NewCouponHistoryUseInfoDao;
import jp.co.sint.webshop.data.dao.NewCouponRuleDao;
import jp.co.sint.webshop.data.dao.NewCouponRuleUseInfoDao;
import jp.co.sint.webshop.data.dao.ShippingHeaderDao;
import jp.co.sint.webshop.data.domain.CouponType;
import jp.co.sint.webshop.data.domain.CustomerStatus;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.NewCouponHistory;
import jp.co.sint.webshop.data.dto.NewCouponHistoryUseInfo;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.data.dto.NewCouponRuleUseInfo;
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.validation.ValidatorUtil;

import org.apache.log4j.Logger;

public class PrivateCouponImportDataSource extends SqlImportDataSource<PrivateCouponCsvSchema, PrivateCouponImportCondition> {

  private NewCouponHistory couponHistory = null;
  
  private PreparedStatement insertStatement = null;
  
  private Connection connection = null;

  private String[] validateTarget = new String[] { "customerCode", "couponCode"/*, "issueReason"*/ };
  
  private String isCustomerExistsQuery = "SELECT CUSTOMER_CODE FROM CUSTOMER WHERE CUSTOMER_CODE = ? AND CUSTOMER_STATUS = ?";

  @Override
  protected void initializeResources() {
    //20120522 tuxinwei add start
    String insertQuery = "INSERT INTO NEW_COUPON_HISTORY ("
           + " coupon_issue_no, coupon_code, coupon_issue_detail_no, coupon_name, coupon_name_en, coupon_name_jp,"
           + " coupon_issue_type, coupon_issue_datetime, coupon_proportion,  "
           + " coupon_amount, min_use_order_amount, use_start_datetime, use_end_datetime,  "
           + " issue_reason, customer_code, issue_order_no, use_order_no, use_status,max_use_order_amount, use_type, "
           + " orm_rowid, created_user, created_datetime, updated_user, updated_datetime) "
           + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, " 
    	     + SqlDialect.getDefault().getNextvalNOprm("NEW_COUPON_HISTORY_SEQ") + ",?,?,?,?)";
    //20120522 tuxinwei add end
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
    	couponHistory = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), NewCouponHistory.class);
    } catch (Exception e) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE)));
    }
    DatabaseUtil.setUserStatus(couponHistory, ServiceLoginInfo.getInstance());

    summary.getErrors().addAll(BeanValidator.partialValidate(couponHistory, validateTarget).getErrors());
    
    if (!summary.hasError()) {
    	//判断优惠券规则编号的长度是否大于6
        if(couponHistory.getCouponCode().length()>6){
        	summary.getErrors().add(new ValidationResult(null, null, MessageFormat.format(Messages.getString("validation.LengthValidator.0"), 6)));
			return summary;
        }
        
		// 判断顾客是否存在
		Customer customer = DatabaseUtil.loadAsBean(new SimpleQuery(isCustomerExistsQuery, couponHistory.getCustomerCode(), CustomerStatus.MEMBER.getValue()), Customer.class);

		if(customer == null){
			summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, "顾客信息")));
			return summary;
		}
		// 判断顾客别优惠券发行规则是否存在
		NewCouponRuleDao dao = DIContainer.getDao(NewCouponRuleDao.class);
	    NewCouponRule newCouponRule = dao.load(couponHistory.getCouponCode());
	    if(newCouponRule == null){
	    	summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, "顾客别优惠券发行规则信息")));
			return summary;
	    }

		// 判断顾客别优惠券发行规则信息的发行方式是否是【特别会员发行】
	    if(!CouponType.SPECIAL_MEMBER_DISTRIBUTION.longValue().equals(newCouponRule.getCouponType())){
	    	summary.getErrors().add(new ValidationResult(null, null, "顾客别优惠券发行规则的发行方式不是「特别会员发行」，优惠券发行履历取入失败。"));
			return summary;
	    }

		// 判断当前时间是否在优惠券规则的利用其间以前
		if (!ValidatorUtil.isCorrectOrder(DateUtil.getSysdate(), newCouponRule.getMinUseStartDatetime())) {
			summary.getErrors().add(new ValidationResult(null, null, "优惠券发行规则已利用开始，优惠券发行履历取入失败。"));
			return summary;
		}
		
		//所有验证通过时，生成优惠券履历信息
	    if(!summary.hasError()){
	    	createNewCouponHistoryInfo(newCouponRule);
	    }
	}
    
    return summary;
  }

  @Override
  public void executeUpdate(List<String> csvLine) throws SQLException {
		Logger logger = Logger.getLogger(this.getClass());
    NewCouponHistoryUseInfoDao dao = DIContainer.getDao(NewCouponHistoryUseInfoDao.class);
    NewCouponRuleUseInfoDao useDao = DIContainer.getDao(NewCouponRuleUseInfoDao.class);
		try {
			PreparedStatement pstmt = insertStatement;
			List<Object> params = new ArrayList<Object>();
			
			params.add(couponHistory.getCouponIssueNo());
			params.add(couponHistory.getCouponCode());
			params.add(couponHistory.getCouponIssueDetailNo());
			params.add(couponHistory.getCouponName());
		  //20120522 tuxinwei add start
			params.add(couponHistory.getCouponNameEn());
			params.add(couponHistory.getCouponNameJp());
		  //20120522 tuxinwei add end
			params.add(couponHistory.getCouponIssueType());
			params.add(couponHistory.getCouponIssueDatetime());
			params.add(couponHistory.getCouponProportion());
			params.add(couponHistory.getCouponAmount());
			params.add(couponHistory.getMinUseOrderAmount());
			params.add(couponHistory.getUseStartDatetime());
			params.add(couponHistory.getUseEndDatetime());
			params.add(couponHistory.getIssueReason());
			params.add(couponHistory.getCustomerCode());
			params.add(couponHistory.getIssueOrderNo());
			params.add(couponHistory.getUseOrderNo());
			params.add(couponHistory.getUseStatus());
			params.add(couponHistory.getMaxUseOrderAmount());
			params.add(couponHistory.getUseType());
			params.add(ServiceLoginInfo.getInstance().getRecordingFormat());
			params.add(DateUtil.getSysdate());
			params.add(ServiceLoginInfo.getInstance().getRecordingFormat());
			params.add(DateUtil.getSysdate());
			List<NewCouponRuleUseInfo> list = useDao.load(couponHistory.getCouponCode());
			NewCouponHistoryUseInfo historyUseInfo = new NewCouponHistoryUseInfo();
			if(list != null && list.size() > 0){
			  for(NewCouponRuleUseInfo ruleInfo : list){
			    historyUseInfo.setBrandCode(ruleInfo.getBrandCode());
			    historyUseInfo.setCommodityCode(ruleInfo.getCommodityCode());
			    historyUseInfo.setCouponIssueNo(couponHistory.getCouponIssueNo());
			    historyUseInfo.setCouponUseNo(ruleInfo.getCouponUseNo()+1);
			    historyUseInfo.setImportCommodityType(ruleInfo.getImportCommodityType());
			    historyUseInfo.setCreatedUser(ServiceLoginInfo.getInstance().getRecordingFormat());
			    historyUseInfo.setCreatedDatetime(DateUtil.getSysdate());
			    historyUseInfo.setUpdatedUser(ServiceLoginInfo.getInstance().getRecordingFormat());
			    historyUseInfo.setUpdatedDatetime(DateUtil.getSysdate());
			    // 20131017 txw add start
			    historyUseInfo.setCategoryCode(ruleInfo.getCategoryCode());
			    // 20131017 txw add end
			    dao.insert(historyUseInfo);
			  }
			}
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

  /**
	 * 根据顾客别优惠券规则信息生成优惠券履历信息
	 * 
	 * @param newCouponRule
	 *            顾客别优惠券规则信息
	 */
	private void createNewCouponHistoryInfo(NewCouponRule newCouponRule) {
		String selelcMaiCouponIssueDetailNo = "SELECT COUPON_ISSUE_DETAIL_NO  FROM NEW_COUPON_HISTORY WHERE to_number(COUPON_ISSUE_DETAIL_NO, 'S999999') = "
			+ "(SELECT MAX(to_number(COUPON_ISSUE_DETAIL_NO, 'S999999'))  FROM NEW_COUPON_HISTORY) FOR UPDATE";
		
		//随即生成0~9999之间的一个整数
		Long number = Math.round(Math.random()*8999+1000);
		//把生成的随机数填充成四位数
		String num = number.toString();
		
		//优惠券明细编号
		int code = 0;
		
		List<NewCouponHistory> list = DatabaseUtil.loadAsBeanList(connection, new SimpleQuery(selelcMaiCouponIssueDetailNo), NewCouponHistory.class);
		if(list!=null && list.size()>0){
			code = Integer.parseInt(list.get(0).getCouponIssueDetailNo());
		}
		
		String couponIssueDetailNo = String.valueOf(code + 1);
		
		//优惠券编号
		String couponIssueNo = newCouponRule.getCouponCode() + num + StringUtil.toFormatString(couponIssueDetailNo, 6, false, "0");
		
		//优惠券编号
		couponHistory.setCouponIssueNo(couponIssueNo);
		//优惠券明细编号
		couponHistory.setCouponIssueDetailNo(couponIssueDetailNo);
		//优惠券发行类别
		couponHistory.setCouponIssueType(newCouponRule.getCouponIssueType());
		//优惠券名称
		couponHistory.setCouponName(newCouponRule.getCouponName());
		
    //20120522 tuxinwei add start
    //优惠券名称(英文)
    couponHistory.setCouponNameEn(newCouponRule.getCouponNameEn());
    
    //优惠券名称(日文)
    couponHistory.setCouponNameJp(newCouponRule.getCouponNameJp());
    //20120522 tuxinwei add end
    
		//优惠券发行日期
		couponHistory.setCouponIssueDatetime(DateUtil.getSysdate());
		//优惠券比例
		couponHistory.setCouponProportion(newCouponRule.getCouponProportion());
		//优惠券金额
		couponHistory.setCouponAmount(newCouponRule.getCouponAmount());
		couponHistory.setMinUseOrderAmount(newCouponRule.getMinUseOrderAmount());
		couponHistory.setMaxUseOrderAmount(newCouponRule.getMaxUseOrderAmount());
		//优惠券利用开始日时
		couponHistory.setUseStartDatetime(newCouponRule.getMinUseStartDatetime());
		//优惠券利用结束日时
		couponHistory.setUseEndDatetime(newCouponRule.getMinUseEndDatetime());
		couponHistory.setUseType(newCouponRule.getUseType());
//		if(StringUtil.isNullOrEmpty(couponHistory.getIssueReason())){
//			//发行理由
			couponHistory.setIssueReason(newCouponRule.getIssueReason());
//		}
		//使用状态
		couponHistory.setUseStatus(0L);
	}

}
