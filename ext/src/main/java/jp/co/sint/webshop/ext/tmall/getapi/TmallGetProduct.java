package jp.co.sint.webshop.ext.tmall.getapi;

import jp.co.sint.webshop.ext.tmall.LogOutPutRecord;
import jp.co.sint.webshop.ext.tmall.TmallConstant;
import jp.co.sint.webshop.service.tmall.TmallCommodityHeader;
import jp.co.sint.webshop.service.tmall.TmallGetProductService;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Product;
import com.taobao.api.request.ProductGetRequest;
import com.taobao.api.response.ProductGetResponse;

public class TmallGetProduct implements TmallGetProductService {

  /**
   * 获取一个产品的信息 api:taobao.product.get
   */
  int ifId = 4;

  public String getProduct(TmallCommodityHeader tch) {

    Logger logger = Logger.getLogger(this.getClass());

    // 获取url
    TmallConstant tc = DIContainer.get(TmallConstant.class.getSimpleName());

    LogOutPutRecord logtool = new LogOutPutRecord(tc.getApiLogpath(), "");

    TaobaoClient client = new DefaultTaobaoClient(tc.getReqUrl(), tc.getAppKey(), tc.getAppSercet());
    ProductGetRequest req = new ProductGetRequest();
    req.setFields("product_id");
    // 产品ID暂时不用
    // req.setProductId("");
    // 子类目
    if (!StringUtil.isNullOrEmpty(tch.getCid())) {
      req.setCid(Long.parseLong(tch.getCid()));
      // 判断是优惠券返回 天猫优惠券
      if (isYhq(tch.getCid())) {
        return "tmallyhq";
      }
    }
    if (!StringUtil.isNullOrEmpty(tch.getKeyProps())) {
      req.setProps(tch.getKeyProps());
    } else {
      req.setProps(tch.getProps());
    }
    try {
      ProductGetResponse response = client.execute(req, tc.getSessionKey());
      if (!StringUtil.isNullOrEmpty(response.getErrorCode())) {
        String subMsg = "";
        String msg = "";
        if (!StringUtil.isNullOrEmpty(response.getSubMsg())) {
          subMsg = response.getSubMsg();
        } else {
          subMsg = "";
        }
        if (!StringUtil.isNullOrEmpty(response.getMsg())) {
          msg = response.getMsg();
        } else {
          msg = "";
        }
        logger.error(response.getErrorCode() + "_" + subMsg + "_" + msg);
        logtool.log("taobao.product.get（产品ID获取失败）", ifId);
        logtool.log(response.getErrorCode() + "_" + subMsg + "_" + msg, ifId);
        logtool.log(logtool.printHorizontalLine());
        return null;
      } else {
        Product pro = response.getProduct();
        if (pro == null) {
          logtool.log("taobao.product.get（淘宝上暂无该产品）", ifId);
          logtool.log(logtool.printHorizontalLine());
          return null;
        }
        if (pro.getProductId() != null) {
          logtool.log("taobao.product.get（产品ID获取成功）", ifId);
          logtool.log(logtool.printHorizontalLine());
          return pro.getProductId() + "";
        } else {
          return null;
        }
      }
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    return null;
  }

  // 判断商品类目是否属于优惠券
  public boolean isYhq(String cid) {
    boolean flag = false;
    // 子类目
    String[] str = {
        "50050570", "50015752", "50050550", "50050551", "50050553", "50050560", "50026035", "50170003", "50015766", "50050548",
        "50015770", "50019104", "50050559", "50050557", "50015764", "50019064", "50050738", "50050558", "50015765", "50050739",
        "50050556", "50050549", "50015769", "50132004", "50015753", "50106011", "50050569", "50050663", "50050555", "50015754",
        "50050547", "50050554", "50050735", "50015763"
    };
    for (int i = 0; i < str.length; i++) {
      if (cid.equals(str[i])) {
        flag = true;
      }
    }

    return flag;
  }
}
