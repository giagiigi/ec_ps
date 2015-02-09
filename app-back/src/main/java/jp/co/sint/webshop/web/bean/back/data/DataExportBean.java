package jp.co.sint.webshop.web.bean.back.data;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.data.CsvExportType;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1080110:データ一括出力のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class DataExportBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<CodeAttribute> exportDataTypeList = new ArrayList<CodeAttribute>();

  /** 出力するマスタの名前 */
  @Required
  @Metadata(name = "出力データ種別", order = 1)
  private String exportDataType;

  /** CSVタイトル行 */
  @Required
  @Metadata(name = "CSVタイトル行", order = 2)
  private String csvHeaderCondition = WebConstantCode.VALUE_TRUE;

  private List<CodeAttribute> csvHeaderList = new ArrayList<CodeAttribute>();

  /**
   * csvHeaderConditionを取得します。
   * 
   * @return csvHeaderCondition
   */
  public String getCsvHeaderCondition() {
    return csvHeaderCondition;
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
   * exportDataTypeを取得します。
   * 
   * @return exportDataType
   */
  public String getExportDataType() {
    return exportDataType;
  }

  /**
   * exportDataTypeListを取得します。
   * 
   * @return exportDataTypeList
   */
  public List<CodeAttribute> getExportDataTypeList() {
    return exportDataTypeList;
  }

  /**
   * exportDataTypeを設定します。
   * 
   * @param exportDataType
   *          exportDataType
   */
  public void setExportDataType(String exportDataType) {
    this.exportDataType = exportDataType;
  }

  /**
   * exportDataTypeListを設定します。
   * 
   * @param exportDataTypeList
   *          exportDataTypeList
   */
  public void setExportDataTypeList(List<CodeAttribute> exportDataTypeList) {
    this.exportDataTypeList = exportDataTypeList;
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
    setExportDataType(reqparam.get("exportType"));
    if (StringUtil.hasValue(reqparam.get("useHeader"))) {
      setCsvHeaderCondition(reqparam.get("useHeader"));
    }
  }

  /**
   * 出力データ種別のリストを取得します。
   * 
   * @param login
   * @param config
   * @return 出力データ種別のリスト
   */
  public List<CodeAttribute> getExportDataTypeList(BackLoginInfo login, WebshopConfig config) {
    List<CodeAttribute> list = new ArrayList<CodeAttribute>();
    for (CsvExportType type : CsvExportType.getCsvExportTypeSet(DIContainer.getWebshopConfig().getOperatingMode())) {
      boolean granted = false;
      for (Permission permission : type.getPermissions()) {
        granted |= permission.isGranted(login);
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

    list.add(new NameValue(Messages.getString("web.bean.back.data.DataExportBean.0"), WebConstantCode.VALUE_TRUE));
    list.add(new NameValue(Messages.getString("web.bean.back.data.DataExportBean.1"), WebConstantCode.VALUE_FALSE));

    return list;
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1080110";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.data.DataExportBean.2");
  }

}
