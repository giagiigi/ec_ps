package jp.co.sint.webshop.service.data.csv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.csv.CsvImportException;
import jp.co.sint.webshop.data.csv.CsvUtil;
import jp.co.sint.webshop.data.csv.sql.SqlImportDataSource;
import jp.co.sint.webshop.data.dao.JdOrderHeaderDao;
import jp.co.sint.webshop.data.dao.OrderHeaderDao;
import jp.co.sint.webshop.data.dao.TmallOrderHeaderDao;
import jp.co.sint.webshop.data.domain.OrderType;
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.service.NewDeliverySlipNoImport;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;

import org.apache.log4j.Logger;

public class NewDeliverySlipNoImportDataSource extends SqlImportDataSource<NewDeliverySlipNoCsvSchema, NewDeliverySlipNoImportCondition> {

  private NewDeliverySlipNoImport entity = null;
  

  private String[] validateTarget = new String[] { "orderNo", "deliverySlipNo"/*, "issueReason"*/ };


  public ValidationSummary validate(List<String> csvLine) {
    ValidationSummary summary = new ValidationSummary();

    try {
      entity = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), NewDeliverySlipNoImport.class);
    } catch (Exception e) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE)));
    }
//    DatabaseUtil.setUserStatus(entity, ServiceLoginInfo.getInstance());

    summary.getErrors().addAll(BeanValidator.partialValidate(entity, validateTarget).getErrors());
    
    if (!summary.hasError()) {
    	  //判断运单号的长度是否大于500
        if(entity.getDeliverySlipNo().length()>500){
        	summary.getErrors().add(new ValidationResult(null, null, MessageFormat.format(Messages.getString("validation.LengthValidator.0"), 500)));
        	return summary;
        }
        //订单类型
        if(entity.getOrderNo().startsWith("T")){
          TmallOrderHeaderDao  dao =  DIContainer.getDao(TmallOrderHeaderDao.class);
          if(StringUtil.isNullOrEmpty(dao.load(entity.getOrderNo()))){
            summary.getErrors().add(new ValidationResult(null, null, "订单编号不存在"));
            return summary;
          }
          entity.setOrderType(OrderType.TMALL);
        }else if(entity.getOrderNo().startsWith("D")){
          JdOrderHeaderDao dao =  DIContainer.getDao(JdOrderHeaderDao.class);
          if(StringUtil.isNullOrEmpty(dao.load(entity.getOrderNo()))){
            summary.getErrors().add(new ValidationResult(null, null, "订单编号不存在"));
            return summary;
          }
          entity.setOrderType(OrderType.JD);
        }else{
          OrderHeaderDao dao =  DIContainer.getDao(OrderHeaderDao.class);
          if(StringUtil.isNullOrEmpty(dao.load(entity.getOrderNo()))){
            summary.getErrors().add(new ValidationResult(null, null, "订单编号不存在"));
            return summary;
          }
          entity.setOrderType(OrderType.EC);
        }
    }
    return summary;
  }

  @Override
  public void executeUpdate(List<String> csvLine) throws SQLException {
		Logger logger = Logger.getLogger(this.getClass());
		try {
		  
		  String sql =null;
		  
		  if(OrderType.JD.getValue().equals(entity.getOrderType().getValue())){
		    sql = " UPDATE JD_SHIPPING_HEADER SET DELIVERY_SLIP_NO=? WHERE ORDER_NO=? " ;
		  }else if(OrderType.TMALL.getValue().equals(entity.getOrderType().getValue())){
		    sql = " UPDATE TMALL_SHIPPING_HEADER SET DELIVERY_SLIP_NO=? WHERE ORDER_NO=? " ;
		  }else{
		    sql = " UPDATE SHIPPING_HEADER SET DELIVERY_SLIP_NO=? WHERE ORDER_NO=? " ;
		  }
		  
		  Connection connection =  getConnection();
		  PreparedStatement pstmt =connection.prepareStatement(sql);
       
			List<Object> params = new ArrayList<Object>();
			params.add(entity.getDeliverySlipNo());
			params.add(entity.getOrderNo());
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
