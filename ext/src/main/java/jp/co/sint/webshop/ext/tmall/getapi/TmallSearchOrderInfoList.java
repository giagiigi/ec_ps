package jp.co.sint.webshop.ext.tmall.getapi;

import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import jp.co.sint.webshop.configure.TmallSendMailConfig;
import jp.co.sint.webshop.ext.tmall.TmallConstant;
import jp.co.sint.webshop.ext.tmall.TmallUtil;
import jp.co.sint.webshop.mail.MailInfo;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.MailingService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.tmall.TmallTradeDetail;
import jp.co.sint.webshop.service.tmall.TmallTradeHeader;
import jp.co.sint.webshop.service.tmall.TmallSearchOrderInfoListService;
import jp.co.sint.webshop.service.tmall.TmallShippingDelivery;
import jp.co.sint.webshop.service.tmall.TmallTradeInfoList;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.PromotionDetail;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.ItemGetRequest;
import com.taobao.api.request.TradeFullinfoGetRequest;
import com.taobao.api.response.ItemGetResponse;
import com.taobao.api.response.TradeFullinfoGetResponse;

public class TmallSearchOrderInfoList implements TmallSearchOrderInfoListService {

  /**
   * 查询订单和发货数据封装到List
   */

  private String isNetError = "ISNOTNETERROR";

  Logger logger = Logger.getLogger(this.getClass());

  private static String createRequestParam(String tid) {

    TreeMap<String, String> apiparamsMap = new TreeMap<String, String>();

    // 获取key
    TmallConstant tc = DIContainer.get(TmallConstant.class.getSimpleName());

    // 组装协议参数。
    apiparamsMap.put("method", TmallUtil.ORDER_DETAIL_NO);
    apiparamsMap.put("app_key", tc.getAppKey());
    apiparamsMap.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    apiparamsMap.put("session", tc.getSessionKey());
    apiparamsMap.put("format", "xml");
    apiparamsMap.put("v", "2.0");
    // 组装应用参数
    apiparamsMap
        .put(
            "fields",
            "tid,created,buyer_nick,buyer_email,cod_fee,commission_fee,adjust_fee,receiver_name,receiver_zip,receiver_state,receiver_city,receiver_district,receiver_address,"
                + "receiver_phone,pay_time,alipay_no,receiver_mobile,payment,received_payment,discount_fee,end_time,buyer_message,status,modified,cod_status,"
                + "type,point_fee,real_point_fee,buyer_obtain_point_fee,invoice_name,orders.num_iid,orders.sku_id,orders.outer_iid,orders.outer_sku_id,orders.title,"
                + "orders.sku_properties_name,orders.num,orders.adjust_fee,orders.price,orders.discount_fee,orders.created,orders.modified,orders.refund_status,orders.refund_id,"
                + "post_fee,consign_time");
    apiparamsMap.put("tid", tid);
    // 生成签名
    String sign = TmallUtil.sign(apiparamsMap, tc.getAppSercet());
    // 组装协议参数sign
    apiparamsMap.put("sign", sign);

    StringBuilder param = new StringBuilder();
    for (Iterator<Map.Entry<String, String>> it = apiparamsMap.entrySet().iterator(); it.hasNext();) {
      Map.Entry<String, String> e = it.next();
      param.append("&").append(e.getKey()).append("=").append(e.getValue());
    }
    return param.toString().substring(1);
  }

  @SuppressWarnings("unchecked")
  public TmallTradeInfoList searchOrderDetailList(String startTime, String endTime) {

    CatalogService Catalogserv = ServiceLocator.getCatalogService(ServiceLoginInfo.getInstance());
    
    // 获取url
    TmallConstant tc = DIContainer.get(TmallConstant.class.getSimpleName());

    TmallSearchOrderTidList txotl = new TmallSearchOrderTidList();
    List<String> list = txotl.searchOrderTidList(startTime, endTime);
    TmallTradeInfoList ttil = null;

    List<TmallTradeHeader> orderHeaderList = null;
    List<TmallShippingDelivery> shippingHeaderList = null;

    if (list != null && list.size() > 0) {
      if (!list.get(0).equals("NETERROR")) {
        ttil = new TmallTradeInfoList();
        orderHeaderList = new ArrayList<TmallTradeHeader>();
        shippingHeaderList = new ArrayList<TmallShippingDelivery>();

        try {
          for (int i = 0; i < list.size(); i++) {
            // 获得请求参数
            String paramStr = createRequestParam(list.get(i).toString());
            // 调用api获得结果
            String resultStr = TmallUtil.getResult(tc.getReqUrl(), paramStr);
            // 对返回的xml结果字符串，进行解析，组装成java对象
            StringReader read = new StringReader(resultStr);
            InputSource source = new InputSource(read);
            SAXBuilder builder = new SAXBuilder();
            Document document;

            document = builder.build(source);
            Element root = document.getRootElement();
            List<Element> childNodes = root.getChildren();
            Element elementHeader = null;
            if (childNodes != null) {
              int childNodesNum = childNodes.size();
              boolean repeatOrder = false;
              logger.info("childNodesNum : " + childNodesNum);
              for (int j = 0; j < childNodes.size(); j++) {
                int odListNum = 0;
                double hlgChildDiscountFee = 0.0;
                // orderHeader信息取得start
                TmallTradeHeader toh = new TmallTradeHeader();
                elementHeader = (Element) childNodes.get(j);
                toh.setTid(elementHeader.getChildText("tid"));
                if (!StringUtil.isNullOrEmpty(elementHeader.getChildText("tid"))) {
                  logger.info("TID :================== " + elementHeader.getChildText("tid"));
                } else {
                  if (!StringUtil.isNullOrEmpty(elementHeader.getChildText("buyer_nick"))) {
                    logger.info("buyer_nick :================== " + elementHeader.getChildText("buyer_nick"));
                  }
                }
                toh.setCreated(elementHeader.getChildText("created"));
                if (!StringUtil.isNullOrEmpty(elementHeader.getChildText("buyer_nick"))) {
                  if (elementHeader.getChildText("buyer_nick").length() > 20) {
                    toh.setBuyerNick(StringUtil.parse(elementHeader.getChildText("buyer_nick").substring(0, 20)));
                  } else {
                    toh.setBuyerNick(StringUtil.parse(elementHeader.getChildText("buyer_nick")));
                  }
                } else {
                  toh.setBuyerNick("匿名用户");
                }

                if (StringUtil.isNullOrEmpty(elementHeader.getChildText("buyer_email"))) {
                  toh.setBuyerEmail(elementHeader.getChildText("tid") + "@TMALL.COM.CN");
                } else {
                  toh.setBuyerEmail(elementHeader.getChildText("buyer_email"));
                }

                // 邮箱长度处理
                String tohZip = elementHeader.getChildText("receiver_zip");
                if (StringUtil.isNullOrEmpty(tohZip)) {
                  toh.setReceiverZip("000000");
                } else {
                  if (tohZip.length() < 6) {
                    int lenZip = 6 - tohZip.length();
                    for (int z = 0; z < lenZip; z++) {
                      tohZip = tohZip + "0";
                    }
                  }
                  toh.setReceiverZip(tohZip);
                }

                toh.setReceiverState(elementHeader.getChildText("receiver_state"));

                // 淘宝地址转换
                if (StringUtil.isNullOrEmpty(elementHeader.getChildText("receiver_city"))) {
                  toh.setReceiverCity(elementHeader.getChildText("receiver_district"));
                  toh.setReceiverDistrict(elementHeader.getChildText(""));
                } else {
                  if (elementHeader.getChildText("receiver_city").equals("海东市")) {
                    toh.setReceiverCity("海东地区");
                  } else {
                    toh.setReceiverCity(elementHeader.getChildText("receiver_city"));
                  }
                  toh.setReceiverDistrict(elementHeader.getChildText("receiver_district"));
                }

                if (elementHeader.getChildText("receiver_address").length() > 100) {
                  toh.setReceiverAddress(StringUtil.parse(elementHeader.getChildText("receiver_address").substring(0, 100)));
                } else {
                  toh.setReceiverAddress(StringUtil.parse(elementHeader.getChildText("receiver_address")));
                }
                toh.setReceiverPhone(elementHeader.getChildText("receiver_phone"));
                toh.setPayTime(elementHeader.getChildText("pay_time"));
                toh.setAlipayNo(elementHeader.getChildText("alipay_no"));
                if (!StringUtil.isNullOrEmpty(elementHeader.getChildText("receiver_mobile"))
                    && elementHeader.getChildText("receiver_mobile").length() > 11) {
                  toh.setReceiverMobile(StringUtil.parse(elementHeader.getChildText("receiver_mobile").substring(1, 12)));
                } else {
                  toh.setReceiverMobile(elementHeader.getChildText("receiver_mobile"));
                }
                toh.setPayment(elementHeader.getChildText("payment"));
                toh.setReceivedPayment(elementHeader.getChildText("received_payment"));
                toh.setEndTime(elementHeader.getChildText("end_time"));
                toh.setBuyerMessage(StringUtil.parse(elementHeader.getChildText("buyer_message")));
                toh.setStatus(elementHeader.getChildText("status"));
                toh.setModified(elementHeader.getChildText("modified"));
                toh.setCodStatus(elementHeader.getChildText("cod_status"));
                toh.setType(elementHeader.getChildText("type"));
                toh.setPointFee(elementHeader.getChildText("point_fee"));
                toh.setReal_point_fee(elementHeader.getChildText("real_point_fee"));
                toh.setCommissionFee(elementHeader.getChildText("commission_fee"));

                // 卖家手动修改金额
                BigDecimal adjustFee = BigDecimal.ZERO;
                toh.setInvoiceName(elementHeader.getChildText("invoice_name"));
                // 买家获得积分
                toh.setBuyerObtainPointFee(elementHeader.getChildText("buyer_obtain_point_fee"));
                // orderHeader信息取得end

                // orderDetail信息取得start
                logger.info("TOH : " + toh.getTid());

                List<Element> childNodesList = elementHeader.getChildren("orders");
                Element elementDetail = null;
                logger.info("TOH : " + toh.getTid());
                int childNodesListNum = childNodesList.size();
                logger.info("childNodesListNum : " + childNodesListNum);

                for (int k = 0; k < childNodesList.size(); k++) {
                  List<TmallTradeDetail> orderDetailList = new ArrayList<TmallTradeDetail>();
                  elementDetail = (Element) childNodesList.get(k);
                  List<Element> odList = elementDetail.getChildren();
                  Element elementod = null;
                  odListNum = odList.size();
                  logger.info("odListNum : " + odListNum);
                  for (int odIdx = 0; odIdx < odList.size(); odIdx++) {
                    TmallTradeDetail tod = new TmallTradeDetail();
                    elementod = odList.get(odIdx);
                    tod.setNumIid(elementod.getChildText("num_iid"));
                    tod.setSkuId(elementod.getChildText("sku_id"));
                    tod.setOuterIid(elementod.getChildText("outer_iid"));
                    if (StringUtil.hasValue(elementod.getChildText("outer_sku_id"))
                        && elementod.getChildText("outer_sku_id").equals("日式休闲丸子 干脆面 台湾进口方便面95g")) {
                      tod.setOuterSkuId(null);
                    } else {
                      tod.setOuterSkuId(elementod.getChildText("outer_sku_id"));
                    }
                    // 20150204 hdh add start 多SKU下载
                    if (Catalogserv.getCommodityMasterByTmallCode(tod.getOuterIid(), tod.getNumIid()) != null) {
                      if (Catalogserv.getCommoditySkuByTmallCode(tod.getOuterIid(), tod.getOuterSkuId()) == null) {
                        logger.error("子商品SKU编号不存在 ,商品编号:" + tod.getOuterIid() + ",SKU编号:" + tod.getOuterSkuId());
                        throw new RuntimeException("子商品SKU编号不存在 ,商品编号:" + tod.getOuterIid() + ",SKU编号:" + tod.getOuterSkuId());
                      } else {
                        tod.setOuterIid(tod.getOuterSkuId());
                      }
                    }
                    // 20150204 hdh add end
                    if (elementod.getChildText("title").length() > 50) {
                      tod.setTitle(elementod.getChildText("title").substring(0, 50));
                    } else {
                      tod.setTitle(elementod.getChildText("title"));
                    }
                    String sku_properties_name = elementod.getChildText("sku_properties_name");
                    if (sku_properties_name != null) {
                      String[] properties = sku_properties_name.split(";");
                      if (properties.length > 1) {
                        if (!StringUtil.isNullOrEmpty(properties[0])) {
                          if (properties[0].length() > 20) {
                            tod.setSkuPropertiesNameOne(properties[0].substring(0, 20));
                          }
                        }
                        if (!StringUtil.isNullOrEmpty(properties[1])) {
                          if (properties[1].length() > 20) {
                            tod.setSkuPropertiesNameTwo(properties[1].substring(0, 20));
                          }
                        }
                      }
                      if (properties.length == 1) {
                        if (!StringUtil.isNullOrEmpty(properties[0])) {
                          if (properties[0].length() > 20) {
                            tod.setSkuPropertiesNameOne(properties[0].substring(0, 20));
                          }
                        }
                      }
                    }
                    // 卖家手动修改金额累加
                    if (!StringUtil.isNullOrEmpty(elementod.getChildText("adjust_fee"))) {
                      String adjustFeeStr = elementod.getChildText("adjust_fee");
                      adjustFee = adjustFee.add(BigDecimal.valueOf(-Double.parseDouble(adjustFeeStr)));
                    }

                    tod.setPrice1(elementod.getChildText("price"));
                    tod.setPrice2(elementod.getChildText("price"));
                    tod.setCreated(elementHeader.getChildText("created"));
                    tod.setModified(elementHeader.getChildText("modified"));
                    tod.setRefundStatus(elementod.getChildText("refund_status"));

                    // 限时折扣和搭配套餐处理start
                    int childNum = 1;
                    double childDiscountFee = 0.0;
                    BigDecimal childOrderPrice = BigDecimal.ZERO;
                    tod.setNum(elementod.getChildText("num"));
                    if (!StringUtil.isNullOrEmpty(elementod.getChildText("num"))) {
                      String childNumStr = elementod.getChildText("num");
                      childNum = Integer.parseInt(childNumStr);
                    }
                    if (!StringUtil.isNullOrEmpty(elementod.getChildText("discount_fee"))) {
                      String childDiscountFeeStr = elementod.getChildText("discount_fee");
                      childDiscountFee = Double.parseDouble(childDiscountFeeStr);
                    }
                    childOrderPrice = BigDecimal.valueOf(childDiscountFee / childNum);

                    // 判断是否是欢乐逛start
                    if (isHlg(elementHeader, tc)) {
                      tod.setChildOrderPrice("0");
                      hlgChildDiscountFee += childDiscountFee;
                    } else {
                      tod.setChildOrderPrice(childOrderPrice.toString());
                    }
                    // 判断是否是欢乐逛end

                    // 限时折扣和搭配套餐处理end

                    tod.setRefundId(elementod.getChildText("refund_id"));
                    if (elementod.getChildText("refund_id") != null) {
                      TmallSearchRefundInfo tsri = new TmallSearchRefundInfo();
                      String payment = tsri.searchRefundPayment(elementod.getChildText("refund_id"));
                      tod.setRefundPayment(payment);
                    }
                    orderDetailList.add(tod);
                  }

                  List<TmallTradeDetail> reatList = new ArrayList<TmallTradeDetail>();
                  // 判断订单明细中是否有重复商品start
                  int odNum = orderDetailList.size();
                  Set<String> set = new HashSet<String>();
                  for (int od = 0; od < orderDetailList.size(); od++) {
                    TmallTradeDetail ttd = new TmallTradeDetail();
                    ttd = (TmallTradeDetail) orderDetailList.get(od);
                    set.add(ttd.getOuterIid());
                  }
                  if (odNum != set.size()) {
                    // repeatOrder = true;

                    Map<String, TmallTradeDetail> map = new HashMap<String, TmallTradeDetail>();
                    for (int od = 0; od < orderDetailList.size(); od++) {
                      TmallTradeDetail ttd = new TmallTradeDetail();
                      ttd = (TmallTradeDetail) orderDetailList.get(od);
                      if (map.get(ttd.getOuterIid()) != null) {
                        int f = Integer.parseInt(map.get(ttd.getOuterIid()).getNum()) + Integer.parseInt(ttd.getNum());
                        String pStr1 = map.get(ttd.getOuterIid()).getChildOrderPrice(); 
                        String pStr2 = ttd.getChildOrderPrice(); 
                        double pdou = (Double.parseDouble(pStr1)+Double.parseDouble(pStr2))/f;
                        map.get(ttd.getOuterIid()).setChildOrderPrice(pdou + "");
                        map.get(ttd.getOuterIid()).setNum(f + "");
                      } else {
                        map.put(ttd.getOuterIid(), ttd);
                      }
                    }
                    Iterator it = map.values().iterator();
                    while (it.hasNext()) {
                      TmallTradeDetail ttd = new TmallTradeDetail();
                      ttd = (TmallTradeDetail) it.next();
                      reatList.add(ttd);
                    }

                  }
                  // 判断订单明细中是否有重复商品end
                  if (reatList != null && reatList.size() > 0) {
                    // 封装tmall 满就送信息
                    odListNum = reatList.size() + getMjsOrderDetail(reatList, elementHeader, tc, toh, hlgChildDiscountFee);
                    // 将orderDetail的集合赋值到orderHeader
                    toh.setOrderDetailList(reatList);
                  } else {
                    odListNum = odListNum + getMjsOrderDetail(orderDetailList, elementHeader, tc, toh, hlgChildDiscountFee);
                    // 将orderDetail的集合赋值到orderHeader
                    toh.setOrderDetailList(orderDetailList);
                  }

                }
                // orderDetail信息取得end
                toh.setAdjustFee(adjustFee.toString());

                // ShippingHeader信息取得start
                TmallShippingDelivery tsh = new TmallShippingDelivery();
                tsh.setTid(elementHeader.getChildText("tid"));

                // 截取收货人姓名长度
                String tempStr = elementHeader.getChildText("receiver_name");
                if (tempStr != null && !tempStr.equals("")) {
                  String reStr = StringUtil.subStringByByte(tempStr, 20);
                  tsh.setReceiverName(StringUtil.parse(reStr));
                }
                // 邮箱长度处理
                String tshZip = elementHeader.getChildText("receiver_zip");
                if (StringUtil.isNullOrEmpty(tshZip)) {
                  tsh.setReceiverZip("000000");
                } else {
                  if (tshZip.length() < 6) {
                    int lenZip = 6 - tshZip.length();
                    for (int z = 0; z < lenZip; z++) {
                      tshZip = tshZip + "0";
                    }
                  }
                  tsh.setReceiverZip(tshZip);
                }

                tsh.setReceiverState(elementHeader.getChildText("receiver_state"));

                if (StringUtil.isNullOrEmpty(elementHeader.getChildText("receiver_city"))) {
                  tsh.setReceiverCity(elementHeader.getChildText("receiver_district"));
                  tsh.setReceiverDistrict(elementHeader.getChildText(""));
                } else {
                  if (elementHeader.getChildText("receiver_city").equals("海东市")) {
                    tsh.setReceiverCity("海东地区");
                  } else {
                    tsh.setReceiverCity(elementHeader.getChildText("receiver_city"));
                  }
                  tsh.setReceiverDistrict(elementHeader.getChildText("receiver_district"));
                }
                if (elementHeader.getChildText("receiver_address").length() > 100) {
                  tsh.setReceiverAddress(StringUtil.parse(elementHeader.getChildText("receiver_address").substring(0, 100)));
                } else {
                  tsh.setReceiverAddress(StringUtil.parse(elementHeader.getChildText("receiver_address")));
                }

                tsh.setReceiverPhone(elementHeader.getChildText("receiver_phone"));

                if (!StringUtil.isNullOrEmpty(elementHeader.getChildText("receiver_mobile"))
                    && elementHeader.getChildText("receiver_mobile").length() > 11) {
                  tsh.setReceiverMobile(StringUtil.parse(elementHeader.getChildText("receiver_mobile").substring(1, 12)));
                } else {
                  tsh.setReceiverMobile(elementHeader.getChildText("receiver_mobile"));
                }
                BigDecimal postFee = BigDecimal.ZERO;
                if (!StringUtil.isNullOrEmpty(elementHeader.getChildText("cod_fee"))) {
                  String codFee = elementHeader.getChildText("cod_fee");
                  postFee = BigDecimal.valueOf(Double.parseDouble(codFee));
                }
                if (!StringUtil.isNullOrEmpty(elementHeader.getChildText("post_fee"))) {
                  String postFeeStr = elementHeader.getChildText("post_fee");
                  postFee = BigDecimal.valueOf(Double.parseDouble(postFeeStr)).add(postFee);
                }
                tsh.setPostFee(postFee.toString());

                tsh.setConsignTime(elementHeader.getChildText("consign_time"));

                if (StringUtil.isNullOrEmpty(toh.getTid())) {
                  logger.error("该订单在API中没有淘宝交易号tid返回，将提示网络中断，停止下载，保持下载开始时间不变。");
                  setIsNetError("NETERROR");
                  return null;
                }

                if (repeatOrder) {
                  logger.error("订单交易号：" + toh.getTid() + "明细商品重复！");
                  sendMail(toh.getTid());
                }

                // 判断订单明细中是否有重复商品新增了repeatOrder
                if (toh.getOrderDetailList() != null && toh.getOrderDetailList().size() > 0
                    && odListNum == toh.getOrderDetailList().size() && !repeatOrder) {
                  toh.setOrderDetailNums((long) odListNum);
                  // 封装orderHeader集合信息
                  orderHeaderList.add(toh);
                  // ShippingHeader信息取得
                  shippingHeaderList.add(tsh);
                }
              }
            }
          }
        } catch (JDOMException e) {
          logger.error(e);
          e.printStackTrace();
          setIsNetError("NETERROR");
          return null;
        } catch (Exception e) {
          logger.error(e);
          e.printStackTrace();
          setIsNetError("NETERROR");
          return null;
        }
        if (orderHeaderList != null && shippingHeaderList != null) {
          ttil.setOrderHeaderList(orderHeaderList);
          ttil.setShippingHeaderList(shippingHeaderList);
        }
      } else {
        setIsNetError("NETERROR");
      }
    }
    return ttil;
  }

  // tmall 满就送
  private int getMjsOrderDetail(List<TmallTradeDetail> orderDetailList, Element elementHeader, TmallConstant tc,
      TmallTradeHeader toh, Double hlgChildDiscountFee) throws Exception {
    // 优惠详情(
    int odListNum = 0;
    BigDecimal disMjsPrice = BigDecimal.ZERO;
    TaobaoClient client = new DefaultTaobaoClient(tc.getReqUrl(), tc.getAppKey(), tc.getAppSercet());
    TradeFullinfoGetRequest req = new TradeFullinfoGetRequest();
    req.setFields("promotion_details");
    req.setTid(Long.parseLong(elementHeader.getChildText("tid")));
    TradeFullinfoGetResponse response = client.execute(req, tc.getSessionKey());
    Trade trade = response.getTrade();
    if (trade != null) {
      List<PromotionDetail> proList = trade.getPromotionDetails();
      if (proList != null) {
        logger.info("proList : " + proList.size());
        for (int d = 0; d < proList.size(); d++) {
          PromotionDetail pd = (PromotionDetail) proList.get(d);
          String disType = pd.getPromotionId();
          double discountFee = 0.0d;
          if (disType != null) {
            if (disType.indexOf("mjs") >= 0) {
              if (!StringUtil.isNullOrEmpty(pd.getDiscountFee())) {
                discountFee = Double.parseDouble(pd.getDiscountFee());
              }
              disMjsPrice = disMjsPrice.add(BigDecimal.valueOf(discountFee));
            }
            // 满就送礼品追加 start
            if (StringUtil.hasValue(pd.getGiftItemId())) {
              TmallTradeDetail todMjs = new TmallTradeDetail();
              TaobaoClient clientMjs = new DefaultTaobaoClient(tc.getReqUrl(), tc.getAppKey(), tc.getAppSercet());
              ItemGetRequest reqMjs = new ItemGetRequest();
              reqMjs.setFields("outer_id");
              reqMjs.setNumIid(Long.parseLong(pd.getGiftItemId()));
              ItemGetResponse responseMjs = clientMjs.execute(reqMjs, tc.getSessionKey());
              todMjs.setNumIid(pd.getGiftItemId());
              todMjs.setOuterIid(responseMjs.getItem().getOuterId());
              todMjs.setTitle(pd.getGiftItemName());
              todMjs.setPrice1("0");
              todMjs.setPrice2("0");
              todMjs.setCreated(elementHeader.getChildText("created"));
              todMjs.setModified(elementHeader.getChildText("modified"));
              todMjs.setRefundStatus("NO_REFUND");
              todMjs.setNum(pd.getGiftItemNum());
              todMjs.setChildOrderPrice("0");
              logger.info("todMjs : " + pd.getGiftItemId() + "-" + pd.getGiftItemName());
              orderDetailList.add(todMjs);
              odListNum++;
            }
            // 满就送礼品追加 end
          }
        }
      }
    }

    toh.setMjsDiscount(disMjsPrice.toString());
    // 总的优惠金额-满就送剩余的优惠金额
    if (!StringUtil.isNullOrEmpty(elementHeader.getChildText("discount_fee"))) {
      toh.setDiscountFee(BigDecimal.valueOf(Double.parseDouble(elementHeader.getChildText("discount_fee"))).add(
          BigDecimal.valueOf(-disMjsPrice.doubleValue()).add(BigDecimal.valueOf(hlgChildDiscountFee))).toString());
    }
    // 优惠详情
    return odListNum;
  }

  // 判断是否参与了欢乐逛活动
  private boolean isHlg(Element elementHeader, TmallConstant tc) throws Exception {
    // 优惠详情(
    TaobaoClient client = new DefaultTaobaoClient(tc.getReqUrl(), tc.getAppKey(), tc.getAppSercet());
    TradeFullinfoGetRequest req = new TradeFullinfoGetRequest();
    req.setFields("promotion_details");
    req.setTid(Long.parseLong(elementHeader.getChildText("tid")));
    TradeFullinfoGetResponse response = client.execute(req, tc.getSessionKey());
    Trade trade = response.getTrade();
    if (trade != null) {
      List<PromotionDetail> proList = trade.getPromotionDetails();
      if (proList != null) {
        logger.info("proList : " + proList.size());
        for (int d = 0; d < proList.size(); d++) {
          PromotionDetail pd = (PromotionDetail) proList.get(d);
          String disType = pd.getPromotionName();
          if (disType != null) {
            if (disType.indexOf("+") >= 0) {
              return true;
            }
          }
        }
      }
    }
    return false;
  }

  public String getIsNetError() {
    return isNetError;
  }

  public void setIsNetError(String isNetError) {
    this.isNetError = isNetError;
  }

  // 订单下载出现重复商品明细 发出警告邮件
  public void sendMail(String tid) {
    // TODO sand emall
    MailInfo mailInfo = new MailInfo();
    StringBuffer sb = new StringBuffer();
    sb.append("<BR>重复明细订单交易号:<BR>" + tid);
    mailInfo.setText(sb.toString());
    mailInfo.setSubject("【品店】淘宝订单下载出现重复商品明细");
    mailInfo.setSendDate(DateUtil.getSysdate());

    TmallSendMailConfig tmllMailSend = DIContainer.get(TmallSendMailConfig.class.getSimpleName());
    mailInfo.setFromInfo(tmllMailSend.getMailFromAddr(), tmllMailSend.getMailFromName());
    String[] mailToAddrArray = tmllMailSend.getMailToAddr().split(";");
    String[] mailToNameArray = tmllMailSend.getMailToName().split(";");
    for (int i = 0; i < mailToAddrArray.length; i++) {
      if (i >= mailToNameArray.length) {
        mailInfo.addToList(mailToAddrArray[i], mailToAddrArray[i]);
      } else {
        mailInfo.addToList(mailToAddrArray[i], mailToNameArray[i]);
      }
    }
    mailInfo.setContentType(MailInfo.CONTENT_TYPE_HTML);
    MailingService svc = ServiceLocator.getMailingService(ServiceLoginInfo.getInstance());
    svc.sendImmediate(mailInfo);
  }
}
