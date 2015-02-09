package jp.co.sint.webshop.ext.tmall.getapi;

import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import jp.co.sint.webshop.data.dto.TmallCategory;
import jp.co.sint.webshop.ext.tmall.TmallConstant;
import jp.co.sint.webshop.ext.tmall.TmallUtil;
import jp.co.sint.webshop.utility.DIContainer;

public class TmallGetChildRenCategoryList {

  /**
   * 获得一级类目下的子类目
   */
  private static String createRequestParam(String parentCid) {

    TreeMap<String, String> apiparamsMap = new TreeMap<String, String>();

    // 获取key
    TmallConstant tc = DIContainer.get(TmallConstant.class.getSimpleName());

    // 组装协议参数。
    apiparamsMap.put("method", TmallUtil.COMMODITY_CATEGORY_LIST);
    apiparamsMap.put("app_key", tc.getAppKey());
    apiparamsMap.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    apiparamsMap.put("session", tc.getSessionKey());
    apiparamsMap.put("format", "xml");
    apiparamsMap.put("v", "2.0");
    // 组装应用参数
    apiparamsMap.put("fields", "cid,parent_cid,name,is_parent");
    apiparamsMap.put("parent_cid", parentCid);
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
  public List<TmallCategory> getChildrenCategory(String parentCid) {

    Logger logger = Logger.getLogger(this.getClass());
    List<TmallCategory> tmallCategoryList = null;

    // 获取url
    TmallConstant tc = DIContainer.get(TmallConstant.class.getSimpleName());

    // 获得请求参数
    String paramStr = createRequestParam(parentCid);
    // 调用api获得结果
    String resultStr = TmallUtil.getResult(tc.getReqUrl(), paramStr);
    // 对返回的xml结果字符串，进行解析，组装成java对象
    StringReader read = new StringReader(resultStr);
    InputSource source = new InputSource(read);
    SAXBuilder builder = new SAXBuilder();

    Document document;

    try {
      tmallCategoryList = new ArrayList<TmallCategory>();
      document = builder.build(source);
      Element root = document.getRootElement();
      Element elementItemCats = root.getChild("item_cats");
      List<Element> listElementItemCats = elementItemCats.getChildren();
      Element elementItemCat = null;
      for (int i = 0; i < listElementItemCats.size(); i++) {
        TmallCategory tmallCategory = new TmallCategory();
        elementItemCat = (Element) listElementItemCats.get(i);
        tmallCategory.setCategoryCode(elementItemCat.getChildText("cid"));
        tmallCategory.setCategoryName(elementItemCat.getChildText("name"));
        tmallCategory.setParentCode(elementItemCat.getChildText("parent_cid"));
        if (elementItemCat.getChildText("is_parent").equals("true")) {
          tmallCategory.setIsParent(1L);
        } else {
          tmallCategory.setIsParent(0L);
        }
        tmallCategoryList.add(tmallCategory);
      }

    } catch (JDOMException e) {
      logger.error("获得子类目失败");
      Logger.getLogger(this.getClass()).error(e);
      e.printStackTrace();
    } catch (IOException e) {
      logger.error("获得子类目失败");
      Logger.getLogger(this.getClass()).error(e);
      e.printStackTrace();
    }
    return tmallCategoryList;
  }
}
