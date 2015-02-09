package jp.co.sint.webshop.web.bean.back.data;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.data.CsvImportType;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1080210:データ一括取込のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class DataImportBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<CodeAttribute> importDataTypeList = new ArrayList<CodeAttribute>();

  /** 取込データ種別の名前 */
  @Required
  @Metadata(name = "取込データ種別", order = 1)
  private String importDataType;

  /** CSVタイトル行 */
  @Required
  @Metadata(name = "CSVタイトル行", order = 2)
  private String csvHeaderCondition = WebConstantCode.VALUE_TRUE;

  private List<CodeAttribute> csvHeaderList = new ArrayList<CodeAttribute>();

  /**
   * csvHeaderListを取得します。
   * 
   * @return csvHeaderList
   */
  public List<CodeAttribute> getCsvHeaderList() {
    return csvHeaderList;
  }

  /**
   * csvHeaderListを設定します。
   * 
   * @param csvHeaderList
   *          csvHeaderList
   */
  public void setCsvHeaderList(List<CodeAttribute> csvHeaderList) {
    this.csvHeaderList = csvHeaderList;
  }

  /**
   * csvHeaderConditionを取得します。
   * 
   * @return csvHeaderCondition
   */
  public String getCsvHeaderCondition() {
    return csvHeaderCondition;
  }

  /**
   * importDataTypeを取得します。
   * 
   * @return importDataType
   */
  public String getImportDataType() {
    return importDataType;
  }

  /**
   * csvHeaderConditionを設定します。
   * 
   * @param csvHeaderCondition
   *          csvHeaderCondition
   */
  public void setCsvHeaderCondition(String csvHeaderCondition) {
    this.csvHeaderCondition = csvHeaderCondition;
  }

  /**
   * importDataTypeを設定します。
   * 
   * @param importDataType
   *          importDataType
   */
  public void setImportDataType(String importDataType) {
    this.importDataType = importDataType;
  }

  /**
   * importDataTypeListを取得します。
   * 
   * @return importDataTypeList
   */
  public List<CodeAttribute> getImportDataTypeList() {
    return importDataTypeList;
  }

  /**
   * importDataTypeListを設定します。
   * 
   * @param importDataTypeList
   *          importDataTypeList
   */
  public void setImportDataTypeList(List<CodeAttribute> importDataTypeList) {
    this.importDataTypeList = importDataTypeList;
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {

  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    setCsvHeaderList(createHeaderTypeList());
    setImportDataType(reqparam.get("uploadType"));
    if (StringUtil.hasValue(reqparam.get("useHeader"))) {
      setCsvHeaderCondition(reqparam.get("useHeader"));
    }
  }

  /**
   * importDataTypeListを取得します。
   * 
   * @param login
   * @param config
   * @return importDataTypeList
   */
  public List<CodeAttribute> getImportDataTypeList(BackLoginInfo login, WebshopConfig config) {
    List<CodeAttribute> list = new ArrayList<CodeAttribute>();

    for (CsvImportType type : CsvImportType.getCsvImportTypeSet(DIContainer.getWebshopConfig().getOperatingMode())) {
      boolean granted = false;
      for (Permission permissin : type.getPermissions()) {
        granted |= permissin.isGranted(login);
      }
      if (granted) {
        list.add(new NameValue(type.getName(), type.getValue()));
      }
    }

    return list;
  }

  /**
   * ヘッダ有無選択肢のリストを作成します。
   */
  public List<CodeAttribute> createHeaderTypeList() {
    List<CodeAttribute> list = new ArrayList<CodeAttribute>();

    list.add(new NameValue(Messages.getString("web.bean.back.data.DataImportBean.0"), WebConstantCode.VALUE_TRUE)); 
    list.add(new NameValue(Messages.getString("web.bean.back.data.DataImportBean.1"), WebConstantCode.VALUE_FALSE)); 

    return list;
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1080210";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.data.DataImportBean.2");
  }

}
