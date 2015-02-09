package jp.co.sint.webshop.ext.chinapay.wap;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sint.webshop.data.domain.PaymentStatus;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

/**
 * 银联支付画面返回
 * @author kousen
 *
 */
public class ChinapayWapComplete extends HttpServlet {

  /** */
  private static final long serialVersionUID = 1L;
  //private static final String PURCHASE_MPRSP = "Purchase.MPRsp"; //商户发往银联平台的应答--消费交易
  private static final String PURCHASEADVICE_MPRSP = "PurchaseAdvice.MPRsp"; //商户发往银联平台的应答--消费通知交易
  private static final String RESPONSE_SUCCESS = "000"; //交易成功
  private static final String RESPONSE_FAIL = "027"; //报文生成错误
  
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    this.doPost(req, resp);
  }

  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = resp.getWriter();
    CupMobile cm = new CupMobile();
    String orderId = "";
    try {
    	String reqXml = readRequestInputStream(req);
    	System.out.println(reqXml);
    	// 从xml中获得bean
    	cm = createBeanFromXml(reqXml);
    	
		if (StringUtil.isNullOrEmpty(cm)) {
			out.print("NG");
			return;
		}else {
			orderId = reverseToOrderId(cm.getOrderId());
			OrderService service = ServiceLocator.getOrderService(ServiceLoginInfo.getInstance());
	        OrderContainer container = service.getOrder(orderId);
	        OrderHeader header = container.getOrderHeader();
	        if (header != null) {
	            if (!PaymentStatus.PAID.longValue().equals(header.getPaymentStatus())) {
	              // 订单支付状态设置：已支付
	              header.setPaymentStatus(PaymentStatus.PAID.longValue());
	              // 支付日设置
	              header.setPaymentDate(DateUtil.parseString(cm.getSettleDate(), "yyyyMMdd"));
	              // 支付编号设置
	              header.setPaymentReceiptNo(orderId);
	              // 实收金额设置
	              header.setPaidPrice((new BigDecimal(cm.getTransAmount())).divide(new BigDecimal(100)));
	              // 支付信息更新
	              ServiceResult svcResult = service.settlePhantomOrder(header);
	              if (svcResult.hasError()) {
	            	 out.print(createResp(cm,RESPONSE_FAIL));
	            	 return;
	              }
	            }
				out.print(createResp(cm,RESPONSE_SUCCESS));
			}else{
				out.print(createResp(cm,RESPONSE_FAIL));
			}
		}
	    
	} catch (Exception e) {
		out.print("NG");
	}
    
  }
    
  /**
   * 从request中取得xml流文件
   * @param req
   * @return
   */
  private String readRequestInputStream(HttpServletRequest req){
	  
	int BUFSIZE = 1024 * 8;               
	int rtnPos = 0;
	byte[] buffs = new byte[ BUFSIZE * 8 ];
	StringBuffer sb = new StringBuffer();
	ServletInputStream sis;
	
	try {
		sis = req.getInputStream();
		while((rtnPos = sis.readLine( buffs, 0, buffs.length )) != -1 ){
            String strBuff = new String( buffs, 0, rtnPos );
            sb.append(strBuff);
		}
	} catch (IOException e) {
		return "";
	}
	    
	    return sb.toString();
  }
  
  /**
   * 创建响应流
   * @param bean
   * @return
   */
  private String createResp(CupMobile bean, String responseCode){
	  
	  StringBuffer sb = new StringBuffer("");
	  sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
	  sb.append("<cupMobile version=\""+bean.getCupMobileVersion()+"\" application=\""+bean.getCupMobileApplication()+"\">");
	  sb.append("<transaction type=\""+PURCHASEADVICE_MPRSP+"\">");
	  sb.append("<submitTime>"+bean.getSubmitTime()+"</submitTime>");
	  sb.append("<order id=\""+bean.getOrderId()+"\">");
	  sb.append("<generateTime>"+bean.getOrderGenerateTime()+"</generateTime>");
	  sb.append("</order>");
	  sb.append("<transAmount currency=\""+bean.getTransAmountCurrency()+"\">"+bean.getTransAmount()+"</transAmount>");
	  sb.append("<terminal id=\""+bean.getTerminalId()+"\"/>");
	  sb.append("<merchant id=\""+bean.getMerchantId()+"\"/>");
	  sb.append("<accountNumber1>"+bean.getAccountNumber1()+"</accountNumber1>");
	  sb.append("<settleDate>"+bean.getSettleDate()+"</settleDate>");
	  sb.append("<transSerialNumber>"+bean.getTransSerialNumber()+"</transSerialNumber>");
	  sb.append("<responseCode>"+responseCode+"</responseCode>");
	  sb.append("<billAmount>"+bean.getBillAmount()+"</billAmount>");
	  sb.append("</transaction>");
	  sb.append("</cupMobile>");
	  System.out.println(sb.toString());
	  return sb.toString();
	  
  }
  
  /**
   * 转换xml成CupMobile
   * @param xml
   * @return
   */
  @SuppressWarnings("unchecked")
  private CupMobile createBeanFromXml(String xml){
	CupMobile bean = new CupMobile();
	try {
		StringReader read = new StringReader(xml);
	    InputSource source = new InputSource(read);
	    SAXBuilder builder = new SAXBuilder();
	    Document document;
	    
	    document = builder.build(source);
		Element root = document.getRootElement();
		
		if (StringUtil.isNullOrEmpty(root)) {
			return null;
		}
		// 获取根目录属性application, version
		if (!StringUtil.isNullOrEmpty(root.getName()) && root.getName().equals("cupMobile")) {
			//支付应用名称
			if (!StringUtil.isNullOrEmpty(root.getAttribute("application"))) {
				bean.setCupMobileApplication(root.getAttribute("application").getValue());
			}
			// 版本号
			if (!StringUtil.isNullOrEmpty(root.getAttribute("version"))) {
				bean.setCupMobileVersion(root.getAttribute("version").getValue());
			}
		}
		
		// 获取一级子节点transaction
	    List<Element> childNodes = root.getChildren();
	    
	    if (childNodes.size() == 1 && childNodes.get(0).getName().equals("transaction")) {
	    	
	    	Element elementHeader = childNodes.get(0);
	    	// 交易类型
	    	if (!StringUtil.isNullOrEmpty(elementHeader.getAttribute("type"))) {
	    		bean.setTransactionType(elementHeader.getAttribute("type").getValue());
			}
	    	
	    	childNodes = elementHeader.getChildren();
	    	// 获取二级子节点，并封装成bean
	        for (int j = 0; j < childNodes.size(); j++) {
	     	  elementHeader = (Element) childNodes.get(j);
	     	  // 交易提交时间  
	     	  if (elementHeader.getName().equals("submitTime")) {
	     		bean.setSubmitTime(elementHeader.getContent(0).getValue());
	     	  }
              // 商户代码
		  	  if (elementHeader.getName().equals("merchant")) {
		  		bean.setMerchantId(elementHeader.getAttributeValue("id"));
			  }
		  	  // 订单号 , 订单生成时间 
		  	  if (elementHeader.getName().equals("order")) {
		  		bean.setOrderId(elementHeader.getAttributeValue("id"));
		  		bean.setOrderGenerateTime(elementHeader.getChildText("generateTime"));
			  }
		  	  // 清算日期 
		  	  if (elementHeader.getName().equals("settleDate")) {
		  		bean.setSettleDate(elementHeader.getContent(0).getValue());
		  	  }
		  	  // 交易传输时间
		  	  if (elementHeader.getName().equals("transmitTime")) {
		  		bean.setTransmitTime(elementHeader.getContent(0).getValue());
		  	  }
		  	  // 扣账金额
		  	  if (elementHeader.getName().equals("billAmount")) {
		  		bean.setBillAmount(elementHeader.getContent(0).getValue());
		  	  }
		  	  // 账户1
		  	  if (elementHeader.getName().equals("accountNumber1")) {
		  		bean.setAccountNumber1(elementHeader.getContent(0).getValue());
		  	  }
		  	  // 交易流水号
		  	  if (elementHeader.getName().equals("transSerialNumber")) {
		  		bean.setTransSerialNumber(elementHeader.getContent(0).getValue());
		  	  }
		  	  // 子商户/终端代码
    		  if (elementHeader.getName().equals("terminal")) {
    			bean.setTerminalId(elementHeader.getAttributeValue("id"));
		  	  }
    		  // 交易币种， 交易金额
		  	  if (elementHeader.getName().equals("transAmount")) {
		  		bean.setTransAmountCurrency(elementHeader.getAttributeValue("currency"));
		  		bean.setTransAmount(elementHeader.getContent(0).getValue());
			  }
	        }
	        
		} else {
			
			return null;
		}
	    
	} catch (Exception e) {
		return null;
	}
	  return bean;
  }
  
  private String reverseToOrderId(String orderId){
	  if (StringUtil.isNullOrEmpty(orderId)) {
		return "";
	  }
	  while(orderId.startsWith("0")){
		  orderId = orderId.substring(1);
	  }
	  return orderId;
  }
}
