package jp.co.sint.webshop.ext.fastpay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import jp.co.sint.webshop.data.domain.CustomerStatus;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.PaymentMethod;
import jp.co.sint.webshop.service.CustomerService;

import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.utility.DecryptUtil;


public class FastpayAlipayResponseReturn extends HttpServlet {

	/**
   * 
   */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Logger logger = Logger.getLogger(this.getClass());
		response.setContentType("text/plain;charset=UTF-8");
		//获取支付宝GET过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
			params.put(name, valueStr);
		}

		// 获取支付宝的通知返回参数
		String tmall_user_id = params.get("user_id"); // 支付宝用户号
		String partnerID = "";
		
	//支付宝信息取得
    OrderService service = ServiceLocator.getOrderService(ServiceLoginInfo.getInstance());
    PaymentMethod method =  service.getAlipayPayMethodInfo();;
    if (method != null) {
      partnerID= method.getMerchantId();
    }
	  //计算得出通知验证结果
    String responseTxt =Verify(request.getParameter("notify_id"),partnerID);
    //验证成功
    if(responseTxt.equals("true")){
      String real_name = params.get("real_name"); // 支付宝用户姓名
      String email = request.getParameter("email"); // 用户支付宝登录帐号
      CustomerService customerService = ServiceLocator.getCustomerService(ServiceLoginInfo.getInstance());
      //支付宝 用户信息登录（新用户时）
      ServiceResult svcResult = customerService.settleFastpay(tmall_user_id,real_name,email);
      
      Customer customer = new Customer();
      // 查询支付宝用户号
      customer = customerService.getCustomerCode(tmall_user_id, CustomerStatus.WITHDRAWED.getValue());
      
       if (svcResult.hasError()) {
         logger.error("支付宝用户  "+tmall_user_id+"  支付宝快捷登录失败");
          return;
       }else{
         request.getRequestDispatcher("/app/customer/fastpay/init/" +DecryptUtil.encode64(customer.getCustomerCode())).forward(request, response);
         return;
       }
    }else{
      logger.error("支付宝用户  "+tmall_user_id+"  支付宝快捷登录失败");
      request.getRequestDispatcher("/app/common/index").forward(request, response);
      return;
    }
		
	}

	/**
	 * *功能：获取远程服务器ATN结果,验证返回URL
	 * 
	 * @param notify_id
	 *            通知校验ID
	 * @return 服务器ATN结果 验证结果集： invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 true
	 *         返回正确信息 false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	 */
	public static String Verify(String notify_id, String partnerID) {
		// 获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求
		String veryfy_url = "";
		veryfy_url = "https://mapi.alipay.com/gateway.do?service=notify_verify";
		veryfy_url = veryfy_url + "&partner=" + partnerID + "&notify_id="
				+ notify_id;
		String responseTxt = CheckUrl(veryfy_url);
		return responseTxt;
	}

	/**
	 * *功能：获取远程服务器ATN结果
	 * 
	 * @param urlvalue
	 *            指定URL路径地址
	 * @return 服务器ATN结果 验证结果集： invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 true
	 *         返回正确信息 false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	 */
	public static String CheckUrl(String urlvalue) {
		String inputLine = "";

		try {
			URL url = new URL(urlvalue);
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			inputLine = in.readLine().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputLine;
	}
}
