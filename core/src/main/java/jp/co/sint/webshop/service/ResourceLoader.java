package jp.co.sint.webshop.service;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 文字列リソース
 * 
 * @author System Integrator Corp.
 */
public final class ResourceLoader {

  private ResourceLoader() {
  }

  public static String getResource(WebshopStringResource resource) {
    ClassLoader loader = ClassLoader.getSystemClassLoader();
    Properties prop = new Properties();
    String s = "";
    try {
      URL url = loader.getResource(resource.getResourceLocation());
      prop.loadFromXML(url.openStream());
      s = prop.getProperty(resource.name());

    } catch (IOException e) {
      Logger.getLogger(ResourceLoader.class).debug(e);
      s = "";
    }
    return s;
  }

  public static interface WebshopStringResource {

    String getResourceLocation();

    String name();
  }

  public static enum CommonResource implements WebshopStringResource {
    /** テスト用(1) */
    TEST,
    /** テスト用(2) */
    EMPTY;

    public String getResourceLocation() {
      return "jp/co/sint/webshop/service/resource/common.resource.xml";
    }
  }

  public static enum OrderResource implements WebshopStringResource {
    /** テスト用(1) */
    TEST,
    /** テスト用(2) */
    EMPTY;

    public String getResourceLocation() {
      return "jp/co/sint/webshop/service/resource/order.resource.xml";
    }
  }

  public static enum CustomerResource implements WebshopStringResource {
    /** テスト用(1) */
    TEST,
    /** テスト用(2) */
    EMPTY;

    public String getResourceLocation() {
      return "jp/co/sint/webshop/service/resource/customer.resource.xml";
    }
  }

  public static enum CatalogResource implements WebshopStringResource {
    /** テスト用(1) */
    TEST,
    /** テスト用(2) */
    EMPTY,
    /** (例)カテゴリ検索SQL */
    CATEGORY_SEARCH_SQL,
    /** (例)商品検索SQL */
    COMMODITY_SEARCH_SQL,
    /** カテゴリ属性値検索SQL */
    CATEGORY_ATTRIBUTE_VALUE_SQL,
    /** カテゴリ陳列商品検索SQL */
    CATEGORY_COMMODITY_SEARCH_SQL,
    /** ギフト関連付き商品数取得SQL */
    RELATED_GIFT_COUNT_SQL;
    

    public String getResourceLocation() {
      return "jp/co/sint/webshop/service/resource/catalog.resource.xml";
    }
  }

  public static enum ShopResource implements WebshopStringResource {
    /** テスト用(1) */
    TEST,
    /** テスト用(2) */
    EMPTY;
    public String getResourceLocation() {
      return "jp/co/sint/webshop/service/resource/shop.resource.xml";
    }
  }

  public static enum CommunicationResource implements WebshopStringResource {
    /** テスト用(1) */
    TEST,
    /** テスト用(2) */
    EMPTY;
    public String getResourceLocation() {
      return "jp/co/sint/webshop/service/resource/communication.resource.xml";
    }
  }

  public static enum AnalysisResource implements WebshopStringResource {
    /** テスト用(1) */
    TEST,
    /** テスト用(2) */
    EMPTY;
    public String getResourceLocation() {
      return "jp/co/sint/webshop/service/resource/analysis.resource.xml";
    }
  }

  public static enum DataResource implements WebshopStringResource {
    /** テスト用(1) */
    TEST,
    /** テスト用(2) */
    EMPTY;
    public String getResourceLocation() {
      return "jp/co/sint/webshop/service/resource/data.resource.xml";
    }
  }

}
