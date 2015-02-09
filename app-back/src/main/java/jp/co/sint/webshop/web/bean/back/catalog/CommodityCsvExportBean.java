package jp.co.sint.webshop.web.bean.back.catalog;

import java.util.ArrayList;
import java.util.List;
import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1040710:入荷お知らせのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CommodityCsvExportBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String searchExportTemplateFlg;
  
  private String searchExportObject;
  
  private String combineObject = "0";

  private String searchAppoint;

  private String searchCondition;

  private String searchFlg;

  private String searchAppointExportObject;

  /** 商品コード */
  private String searchCommodityCode;

  /** SKUコード */
  private String searchSkuCode;

  // 商品基本
  private String[] headerItem;

  // 商品明细
  private String[] detailItem;

  // 商品基本+明细
  private String[] headerDetailItem;

  // 库存
  private String[] stockItem;

  // 分类设定
  private String[] categoryAttributeValueItem;

  private boolean displayShopList = false;

  private boolean registerButtonDisplayFlg = true;
  
  private boolean displayCsvExportButton = true;

  private PagerValue pagerValue;

  private List<String> searchExportItem = new ArrayList<String>();

  private List<NameValue> combineObjectList = NameValue.asList("0:"
          + "全部" + "/1:"
          + "普通商品" + "/2:"
          + "组合商品" );
  
  // 自由指定
  private List<NameValue> exportObjectList = NameValue.asList("0:"
  // 下载商品基本表
      + Messages.getString("web.bean.back.catalog.ExportObjectList.5") + "/1:"
      // 下载商品明细
      + Messages.getString("web.bean.back.catalog.ExportObjectList.6") + "/2:"
      // 下载商品基本+明细表
      + Messages.getString("web.bean.back.catalog.ExportObjectList.7") + "/3:"
      // + Messages.getString("web.bean.back.catalog.ExportObjectList.8")+ "/4:"
      // 下载商品和商品分类关联设置
      + Messages.getString("web.bean.back.catalog.ExportObjectList.9"));

  // 指定模版选项
  private List<NameValue> searchAppointList = NameValue.asList("9:"
  // 指定模版
      + Messages.getString("web.bean.back.catalog.ExportObjectList.10"));

  // 指定模版
  private List<NameValue> exportObjectList1 = NameValue.asList("0:"
  // 商品基本信息模版
      + Messages.getString("web.bean.back.catalog.ExportObjectList.11") + "/1:"
      // 商品销售属性模版
      + Messages.getString("web.bean.back.catalog.ExportObjectList.12") + "/2:"
      // 商品销售设定模版
      + Messages.getString("web.bean.back.catalog.ExportObjectList.13") + "/3:"
      // 商品显示设定模版
      + Messages.getString("web.bean.back.catalog.ExportObjectList.14") + "/4:"
      // 特卖商品设定模版
      + Messages.getString("web.bean.back.catalog.ExportObjectList.15") + "/5:"
      // 特集商品设定模版
      + Messages.getString("web.bean.back.catalog.ExportObjectList.16") + "/6:"
      // + Messages.getString("web.bean.back.catalog.ExportObjectList.17") +
      // 商品分类关联设置模版
      + Messages.getString("web.bean.back.catalog.ExportObjectList.18"));

  // 商品基本表
  public CodeAttribute[] getSearchExportItemList() {
    return new CodeAttribute[] {
        // 商品编号
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.19"), "0"),
        // 商品名称
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.20"), "1"),
        // 商品英文名称
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.21"), "2"),
        // 商品日文名称
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.139"), "3"),
        // 品牌编号
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.22"), "4"),
        // 淘宝类目ID
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.23"), "5"),
        // 供应商编号
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.24"), "6"),
        // 特卖活动区分
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.25"), "7"),
        // 特集活动区分
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.26"), "8"),
        // 商品说明1
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.27"), "9"),
        // 商品说明1英文
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.140"), "10"),
        // 商品说明1日文
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.141"), "11"),
        // 商品说明2
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.28"), "12"),
        // 商品说明2英文
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.142"), "13"),
        // 商品说明2日文
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.143"), "14"),
        // 商品说明3
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.29"), "15"),
        // 商品说明3英文
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.144"), "16"),
        // 商品说明3日文
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.145"), "17"),
        // 商品简短说明（列表用）
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.30"), "18"),
        // 商品简短说明（列表用）英文
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.146"), "19"),
        // 商品简短说明（列表用）日文
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.147"), "20"),
        // 查询关键字
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.31"), "21"),
        // 销售开始日期
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.32"), "22"),
        // 销售结束日期
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.33"), "23"),
        // 特价开始日期
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.34"), "24"),
        // 特价结束日期
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.35"), "25"),
        // 特别警示内容区分
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.36"), "26"),
        // 销售属性ID1
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.37"), "27"),
        // 销售属性名称1
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.38"), "28"),
        // 销售属性名称1英文
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.148"), "29"),
        // 销售属性名称1日文
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.149"), "30"),
        // 销售属性ID2
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.39"), "31"),
        // 销售属性名称2
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.40"), "32"),
        // 销售属性名称2英文
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.150"), "33"),
        // 销售属性名称2日文
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.151"), "34"),
        // 销售标志
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.41"), "35"),
        // 退货受理区分
      //  new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.43"), "24"),
        //20120315 os013 add start
        
        //供货商换货
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.138"), "38"),
        //供货商退货
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.137"), "37"),
        //顾客退货
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.136"), "36"),
        
        //20120315 os013 add end
        // 供应商到货日数
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.44"), "39"),
        // 产地CODE
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.45"), "40"),
        // 大件物品标志
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.46"), "41"),
        // 保质期管理标志
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.47"), "42"),
        // 保管天数
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.48"), "43"),
        // 采购负责人编号
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.49"), "44"),
        // 原材料1
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.50"), "45"),
        // 原材料2
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.51"), "46"),
        // 原材料3
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.52"), "47"),
        // 原材料4
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.53"), "48"),
        // 原材料5
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.54"), "49"),
        // 原材料6
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.55"), "50"),
        // 原材料7
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.56"), "51"),
        // 原材料8
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.57"), "52"),
        // 原材料9
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.58"), "53"),
        // 原材料10
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.59"), "54"),
        // 原材料11
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.60"), "55"),
        // 原材料12
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.61"), "56"),
        // 原材料13
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.62"), "57"),
        // 原材料14
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.63"), "58"),
        // 原材料15
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.64"), "59"),
        // 成分名1
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.65"), "60"),
        // 成分1
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.66"), "61"),
        // 成分名2
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.67"), "62"),
        // 成分2
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.68"), "63"),
        // 成分名3
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.69"), "64"),
        // 成分3
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.70"), "65"),
        // 成分名4
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.71"), "66"),
        // 成分4
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.72"), "67"),
        // 成分名5
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.73"), "68"),
        // 成分5
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.74"), "69"),
        // 成分名6
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.75"), "70"),
        // 成分6
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.76"), "71"),
        // 成分名7
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.77"), "72"),
        // 成分7
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.78"), "73"),
        // 成分名8
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.79"), "74"),
        // 成分8
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.80"), "75"),
        // 成分名9
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.81"), "76"),
        // 成分9
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.82"), "77"),
        // 成分名10
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.83"), "78"),
        // 成分10
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.84"), "79"),
        // 成分名11
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.85"), "80"),
        // 成分11
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.86"), "81"),
        // 成分名12
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.87"), "82"),
        // 成分12
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.88"), "83"),
        // 成分名13
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.89"), "84"),
        // 成分13
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.90"), "85"),
        // 成分名14
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.91"), "86"),
        // 成分14
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.92"), "87"),
        // 成分名15
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.93"), "88"),
        // 成分15
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.94"), "89"),
        // 生产许可证号
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.162"), "90"),
        // 产品标准号
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.163"), "91"),
        // 厂名
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.164"), "92"),
        // 厂址
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.165"), "93"),
        // 厂家联系方式
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.166"), "94"),
        // 配料表
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.167"), "95"),
        // 储藏方法
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.168"), "96"),
        // 保质期
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.169"), "97"),
        // 食品添加剂
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.170"), "98"),
        // 供货商
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.171"), "99"),
        // 生产开始日期
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.172"), "100"),
        // 生产结束日期
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.173"), "101"),
        // 进货开始日期
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.174"), "102"),
        // 进货结束日期
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.175"), "103"),
        // 入库效期
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.179"), "104"),
        // 出库效期
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.180"), "105"),
        // 失效预警
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.181"), "106"),
        // tmall满就送(赠品标志)
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.182"), "107"),
        // 商品区分
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.178"), "108"),
        // 进口商品区分
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.183"), "109"),
        // 清仓商品区分
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.184"), "110"),
        // Asahi商品区分
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.185"), "111"),
        // hot商品区分
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.186"), "112"),
        // include商品区分
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.187"), "113"),
        // 预留区分1*
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.189"), "114"),
        // 预留区分2*
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.190"), "115"),
        // 预留区分3*
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.191"), "116"),
        // 预留区分4*
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.192"), "117"),
        // 预留区分5*
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.193"), "118"),
        // TMALL检索关键字
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.194"), "119"),
        // English热卖标志
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.195"), "120"),
        // Japan热卖标志
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.196"), "121"),
        // 20130809 txw add start
        // TITLE
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.197"), "122"),
        // TITLE(英文)
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.198"), "123"),
        // TITLE(日文)
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.199"), "124"),
        // DESCRIPTION
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.200"), "125"),
        // DESCRIPTION(英文)
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.201"), "126"),
        // DESCRIPTION(日文)
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.202"), "127"),
        // KEYWORD
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.203"), "128"),
        // KEYWORD(英文)
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.204"), "129"),
        // KEYWORD(日文)
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.205"), "130"),
        // 20130809 txw add end
        // 组合商品原商品编号
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.206"), "131"),
        // 组合数
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.207"), "132"),
        // 検索Keyword中文
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.208"), "133"),
        // 検索Keyword日文
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.209"), "134"),
        // 検索Keyword英文
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.210"), "135"),
        // 2014/04/25 京东WBS对应 ob_卢 add start
        // 广告词
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.211"), "136"),
        // 2014/04/25 京东WBS对应 ob_卢 add end
    };
  }

  // 商品明细+库存
  public CodeAttribute[] getSearchExportItemList1() {
    return new CodeAttribute[] {
        //SKU编号
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.95"), "0"),
        //SKU商品名称
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.96"), "1"),
        //采购价格
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.97"), "2"),
        //希望售价
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.98"), "3"),
        //最低售价
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.126"), "4"),
        //定价销售标志
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.127"), "5"),
        //官网售价
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.101"), "6"),
        //淘宝售价
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.102"), "7"),
        //官网特价
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.103"), "8"),
        //淘宝特价
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.104"), "9"),
        //EC显示标志
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.128"), "10"),
        //TMALL显示标志
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.129"), "11"),
        //销售属性值1ID
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.107"), "12"),
        //销售属性值1内容
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.108"), "13"),
        //销售属性值1内容(英文)
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.152"), "14"),
        //销售属性值1内容(日文)
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.153"), "15"),
        //销售属性值2ID
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.109"), "16"),
        //销售属性值2内容
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.110"), "17"),
        //销售属性值2内容(英文)
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.154"), "18"),
        //销售属性值2内容(日文)
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.155"), "19"),
        //库存警告数
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.111"), "20"),
        //最小采购数
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.112"), "21"),
        //最大采购数
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.113"), "22"),
        //最小单位采购数量
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.114"), "23"),
        //商品重量
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.99"), "24"),
        //税率区分
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.135"), "25"),
        //箱规
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.188"), "26"),
        //内包装数
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.130"), "27"),
        //内包装单位
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.131"), "28"),
        //平均单价计算组合数
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.132"), "29"),
        //容量
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.105"), "30"),
        //容量单位
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.106"), "31"),
        //平均单价计算容量
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.133"), "32"),
        // 2014/04/29 京东WBS对应 ob_卢 add start
        // JD使用标志
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.212"), "33"),
        // 2014/04/29 京东WBS对应 ob_卢 add end
        
        // 平均计算成本
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.213"), "36"),
        
        // 2014/04/29 京东WBS对应 ob_卢 update start
//        //库存平衡比例
//        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.134"), "33"),
//        //安全库存数
//        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.116"), "34"),
        //库存平衡比例
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.134"), "34"),
        //安全库存数
        new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.116"), "35"),
        // 2014/04/29 京东WBS对应 ob_卢 update end

    };
  }

  // 商品和商品分类关联
  public CodeAttribute[] getSearchExportItemList4() {
    return new CodeAttribute[] {
            //商品编号
            new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.120"), "0"),
            //商品分类编号
            new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.121"), "1"),
            //商品分类属性产地CODE
            new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.122"), "2"),       
            //商品分类属性值2
            new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.123"), "3"),     
            //商品分类属性值2英文
            new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.158"), "4"),     
            //商品分类属性值2日文
            new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.159"), "5"),
            //商品分类属性值3
            new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.124"), "6"),
            //商品分类属性值3英文
            new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.160"), "7"),
            //商品分类属性值3日文
            new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.161"), "8"),
            //更新标志
            new NameValue(Messages.getString("web.bean.back.catalog.ExportObjectList.125"), "9"),
    };
  }

  public String getSearchAppoint() {
    return searchAppoint;
  }

  public void setSearchAppoint(String searchAppoint) {
    this.searchAppoint = searchAppoint;
  }

  /**
   * @return the searchExportTemplateFlg
   */
  public String getSearchExportTemplateFlg() {
    return searchExportTemplateFlg;
  }

  /**
   * @param searchExportTemplateFlg
   *          the searchExportTemplateFlg to set
   */
  public void setSearchExportTemplateFlg(String searchExportTemplateFlg) {
    this.searchExportTemplateFlg = searchExportTemplateFlg;
  }

  /**
   * @return the searchSkuCode
   */
  public String getSearchSkuCode() {
    return searchSkuCode;
  }

  public String[] getStockItem() {
    return stockItem;
  }

  public void setStockItem(String[] stockItem) {
    this.stockItem = stockItem;
  }

  public String[] getCategoryAttributeValueItem() {
    return categoryAttributeValueItem;
  }

  public void setCategoryAttributeValueItem(String[] categoryAttributeValueItem) {
    this.categoryAttributeValueItem = categoryAttributeValueItem;
  }

  public List<NameValue> getExportObjectList1() {
    return exportObjectList1;
  }

  public void setExportObjectList1(List<NameValue> exportObjectList1) {
    this.exportObjectList1 = exportObjectList1;
  }

  /**
   * searchSkuCodeを設定します。
   * 
   * @param searchSkuCode
   *          searchSkuCode
   */
  public void setSearchSkuCode(String searchSkuCode) {
    this.searchSkuCode = searchSkuCode;
  }

  /**
   * displayShopListを取得します。
   * 
   * @return displayShopList
   */
  public boolean isDisplayShopList() {
    return displayShopList;
  }

  /**
   * displayShopListを設定します。
   * 
   * @param displayShopList
   *          displayShopList
   */
  public void setDisplayShopList(boolean displayShopList) {
    this.displayShopList = displayShopList;
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
    // // 取得顾客输入的商品编号
    // this.setSearchCommodityCode(reqparam.get("searchCommodityCode"));
    // // 取得顾客输入的SKU编号
    // this.setSearchSkuCode(reqparam.get("searchSkuCode"));

    // 清空
    this.setSearchCommodityCode("");
    this.setSearchSkuCode("");
    this.setSearchExportObject("9");
    this.setSearchExportTemplateFlg(reqparam.get("searchExportTemplateFlg"));
    this.setSearchCondition(reqparam.get("searchCondition"));
    this.setSearchFlg(reqparam.get("searchFlg"));
    if (reqparam.get("searchFlg") != null && reqparam.get("searchFlg") != "") {
      // 判断顾客输入的是商品编号还是SKU编号
      if (reqparam.get("searchFlg").equals("3")) {
        // 取得顾客输入的商品编号
        this.setSearchCommodityCode(reqparam.get("searchCondition"));
      }
      if (reqparam.get("searchFlg").equals("4")) {
        // 取得顾客输入的SKU编号
        this.setSearchSkuCode(reqparam.get("searchCondition"));
      }
    }
    // 自由指定
    if (StringUtil.hasValue(reqparam.get("combineObject"))){
      this.setCombineObject(reqparam.get("combineObject"));
    }
    this.setSearchExportObject(reqparam.get("searchExportObject"));
    this.setSearchAppoint(reqparam.get("searchAppoint"));
    // 指定模版
    this.setSearchAppointExportObject(reqparam.get("searchAppointExportObject"));
    // 取得商品基本
    this.setHeaderItem(reqparam.getAll("headerItem"));
    // 取得商品明细
    this.setDetailItem(reqparam.getAll("detailItem"));
    // 取得商品库存
    // this.setStockItem(reqparam.getAll("stockItem"));
    // 取得商品和商品分类关联
    this.setCategoryAttributeValueItem(reqparam.getAll("categoryAttributeValueItem"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1800001";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.catalog.CommodityCsvExportBean.0");
  }

  /**
   * pagerValueを取得します。
   * 
   * @return pagerValue
   */
  public PagerValue getPagerValue() {
    return pagerValue;
  }

  /**
   * pagerValueを設定します。
   * 
   * @param pagerValue
   *          pagerValue
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }

  public void setRegisterButtonDisplayFlg(boolean registerButtonDisplayFlg) {
    this.registerButtonDisplayFlg = registerButtonDisplayFlg;
  }

  public boolean isRegisterButtonDisplayFlg() {
    return registerButtonDisplayFlg;
  }

  /**
   * @return the exportObjectList
   */
  public List<NameValue> getExportObjectList() {
    return exportObjectList;
  }

  /**
   * @param exportObjectList
   *          the exportObjectList to set
   */
  public void setExportObjectList(List<NameValue> exportObjectList) {
    this.exportObjectList = exportObjectList;
  }

  public List<NameValue> getSearchAppointList() {
    return searchAppointList;
  }

  public void setSearchAppointList(List<NameValue> searchAppointList) {
    this.searchAppointList = searchAppointList;
  }

  /**
   * @return the searchExportObject
   */
  public String getSearchExportObject() {
    return searchExportObject;
  }

  /**
   * @param searchExportObject
   *          the searchExportObject to set
   */
  public void setSearchExportObject(String searchExportObject) {
    this.searchExportObject = searchExportObject;
  }

  public String getSearchAppointExportObject() {
    return searchAppointExportObject;
  }

  public void setSearchAppointExportObject(String searchAppointExportObject) {
    this.searchAppointExportObject = searchAppointExportObject;
  }

  /**
   * @return the searchExportItem
   */
  public List<String> getSearchExportItem() {
    return searchExportItem;
  }

  /**
   * @param searchExportItem
   *          the searchExportItem to set
   */
  public void setSearchExportItem(List<String> searchExportItem) {
    this.searchExportItem = searchExportItem;
  }

  /**
   * @return the searchCommodityCode
   */
  public String getSearchCommodityCode() {
    return searchCommodityCode;
  }

  /**
   * @param searchCommodityCode
   *          the searchCommodityCode to set
   */
  public void setSearchCommodityCode(String searchCommodityCode) {
    this.searchCommodityCode = searchCommodityCode;
  }

  /**
   * @return the headerItem
   */
  public String[] getHeaderItem() {
    return ArrayUtil.immutableCopy(headerItem);
  }

  /**
   * @param headerItem
   *          the headerItem to set
   */
  public void setHeaderItem(String[] headerItem) {
    this.headerItem = ArrayUtil.immutableCopy(headerItem);
  }

  /**
   * @return the detailItem
   */
  public String[] getDetailItem() {
    return ArrayUtil.immutableCopy(detailItem);
  }

  /**
   * @param detailItem
   *          the detailItem to set
   */
  public void setDetailItem(String[] detailItem) {
    this.detailItem = ArrayUtil.immutableCopy(detailItem);
  }

  /**
   * @return the headerDetailItem
   */
  public String[] getHeaderDetailItem() {
    return ArrayUtil.immutableCopy(headerDetailItem);
  }

  /**
   * @param headerDetailItem
   *          the headerDetailItem to set
   */
  public void setHeaderDetailItem(String[] headerDetailItem) {
    this.headerDetailItem = ArrayUtil.immutableCopy(headerDetailItem);
  }

  public String getSearchCondition() {
    return searchCondition;
  }

  public void setSearchCondition(String searchCondition) {
    this.searchCondition = searchCondition;
  }

  public String getSearchFlg() {
    return searchFlg;
  }

  public void setSearchFlg(String searchFlg) {
    this.searchFlg = searchFlg;
  }

  
  /**
   * @return the displayCsvExportButton
   */
  public boolean isDisplayCsvExportButton() {
    return displayCsvExportButton;
  }

  
  /**
   * @param displayCsvExportButton the displayCsvExportButton to set
   */
  public void setDisplayCsvExportButton(boolean displayCsvExportButton) {
    this.displayCsvExportButton = displayCsvExportButton;
  }

  
  /**
   * @return the combineObject
   */
  public String getCombineObject() {
    return combineObject;
  }

  
  /**
   * @param combineObject the combineObject to set
   */
  public void setCombineObject(String combineObject) {
    this.combineObject = combineObject;
  }

  
  /**
   * @return the combineObjectList
   */
  public List<NameValue> getCombineObjectList() {
    return combineObjectList;
  }

  
  /**
   * @param combineObjectList the combineObjectList to set
   */
  public void setCombineObjectList(List<NameValue> combineObjectList) {
    this.combineObjectList = combineObjectList;
  }

}
