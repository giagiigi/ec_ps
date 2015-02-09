package jp.co.sint.webshop.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import jp.co.sint.webshop.data.CsvResult;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.CsvExportException;
import jp.co.sint.webshop.data.csv.CsvHandler;
import jp.co.sint.webshop.data.csv.CsvImportException;
import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.data.csv.ExportDataSource;
import jp.co.sint.webshop.data.csv.ImportDataSource;
import jp.co.sint.webshop.service.DataIOService;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.data.CampaignContents;
import jp.co.sint.webshop.service.data.CategoryContents;
import jp.co.sint.webshop.service.data.CommodityImage;
import jp.co.sint.webshop.service.data.Contents;
import jp.co.sint.webshop.service.data.ContentsFile;
import jp.co.sint.webshop.service.data.ContentsInfo;
import jp.co.sint.webshop.service.data.ContentsListResult;
import jp.co.sint.webshop.service.data.ContentsPath;
import jp.co.sint.webshop.service.data.ContentsSearchCondition;
import jp.co.sint.webshop.service.data.ContentsType;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvImportCondition;
import jp.co.sint.webshop.service.data.DataQuery;
import jp.co.sint.webshop.service.data.FileFilter;
import jp.co.sint.webshop.service.data.GiftImage;
import jp.co.sint.webshop.service.data.ImageInfo;
import jp.co.sint.webshop.service.data.ImageType;
import jp.co.sint.webshop.service.data.IndexFloorImage;
import jp.co.sint.webshop.service.data.IndexTopImage;
import jp.co.sint.webshop.service.data.ShopComplianceContents;
import jp.co.sint.webshop.service.data.ShopMobileContents;
import jp.co.sint.webshop.service.data.ShopinfoContents;
import jp.co.sint.webshop.service.data.SkuImage;
import jp.co.sint.webshop.service.fastpay.FastpayAlipayManager;
import jp.co.sint.webshop.service.fastpay.FastpayAlipayResult;
import jp.co.sint.webshop.service.fastpay.FastpayAlipayResultBean;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.DataIOServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceResultImpl;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.IOUtil;
import jp.co.sint.webshop.utility.ImageUploadConfig;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

public class DataIOServiceImpl extends AbstractServiceImpl implements DataIOService {

  private static final long serialVersionUID = 1L;

  private static final String REGEX_PATTERN_FILENAME = "[-A-Za-z0-9_\\./()]*";

  private static final String COMMODITY_DETAIL_IMAGENAME_PC = "[-A-Za-z0-9_\\.\\+]*";

  private static final String COMMODITY_THUMBNAIL_IMAGENAME_PC = "[-A-Za-z0-9_\\.\\+]*_t";

  private static final String COMMODITY_THUMBNAIL_IMAGENAME_MOBILE = "[-A-Za-z0-9_\\.\\+]*_mt";

  private static final String SKU_DETAIL_IMAGENAME_PC = "[-A-Za-z0-9_\\.\\+]*_k";

  private static final String SKU_DETAIL_IMAGENAME_MOBILE = "[-A-Za-z0-9_\\.\\+]*_mk";

  private static final String GIFT_IMAGENAME = "[-A-Za-z0-9_\\.\\+]*_g";

  // 20131029 txw add start
  private static final String INDEX_IMAGENAME = "[-A-Za-z0-9_\\.\\+]*";

  // 20131029 txw add end

  public ServiceResult deleteContents(ContentsInfo info) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    String contentsPath = getContentsDirectoryPath(info);
    try {
      File file = new File(contentsPath);
      if (!file.exists()) {
        serviceResult.addServiceError(DataIOServiceErrorContent.CONTENTS_DELETE_ERROR);
        return serviceResult;
      }
      deleteAll(file);
    } catch (Exception e) {
      logger.error("DataIoService ERROR: " + e);
      serviceResult.addServiceError(DataIOServiceErrorContent.CONTENTS_DELETE_ERROR);
    }
    return serviceResult;
  }

  public ServiceResult deleteImage(Contents contents) {
    Logger logger = Logger.getLogger(this.getClass());

    String filePath = contents.getContentsFile().getPath();
    logger.debug("fileName=" + filePath);

    ServiceResultImpl serviceResult = new ServiceResultImpl();

    try {
      File file = new File(filePath);
      if (file.exists() && file.isFile() && !file.delete()) {
        serviceResult.addServiceError(DataIOServiceErrorContent.CONTENTS_DELETE_ERROR);
        logger.error(Messages.log("service.impl.DataIOServiceImpl.0"));
        return serviceResult;
      }
    } catch (Exception e) {
      logger.error("DataIoService ERROR: " + e);
      serviceResult.addServiceError(DataIOServiceErrorContent.CONTENTS_DELETE_ERROR);
    }

    return serviceResult;
  }

  /**
   * コンテンツのディレクトリパス情報を取得します
   * 
   * @param info
   * @return コンテンツのディレクトリパス
   */
  public String getContentsDirectoryPath(ContentsInfo info) {
    Logger logger = Logger.getLogger(this.getClass());
    String contentsDirectoryPath = "";

    try {
      ContentsPath path = DIContainer.get("contentsPath");
      StringBuilder builder = new StringBuilder();
      builder.append(path.getContentsSharedPath());
      builder.append(getContentsPath(info));
      contentsDirectoryPath = builder.toString();
    } catch (NoSuchBeanDefinitionException e) {
      logger.error(e);
    }

    logger.debug("getContentsDirectoryPath=" + contentsDirectoryPath);
    return contentsDirectoryPath;
  }

  /**
   * 指定した場所に配置するファイルの一覧を取得します。
   * 
   * @param filePath
   * @param depthLevel
   * @return コンテンツ分類のリスト
   */
  private List<ContentsListResult> getFileList(String filePath, Long depthLevel) {

    File file = new File(filePath);
    String[] fileList = file.list();
    List<ContentsListResult> resultList = new ArrayList<ContentsListResult>();

    if (file.exists()) {
      Arrays.sort(fileList);
      for (int i = 0; i < fileList.length; i++) {
        File f = new File(filePath + getFileSeparator() + fileList[i]);
        ContentsListResult result = new ContentsListResult();
        if (f.isDirectory()) {
          result.setDirectoryPath(f.getPath());
          result.setFileName(f.getName());
          result.setSize("");
          result.setUpdateDateTime("");
          result.setDepthLevel(depthLevel);
          result.setDirectory(true);
          resultList.add(result);
          resultList.addAll(getFileList(f.getPath(), depthLevel + 1));
        }
      }
      for (int i = 0; i < fileList.length; i++) {
        File f = new File(filePath + getFileSeparator() + fileList[i]);
        ContentsListResult result = new ContentsListResult();
        if (f.isFile()) {
          result.setDirectoryPath(f.getPath());
          result.setFileName(f.getName());
          // ファイルサイズをKB単位に変換します(小数点以下切り上げ)
          result.setSize((int) Math.ceil(Double.parseDouble(Long.toString(f.length())) / 1024) + "KB");
          result.setUpdateDateTime(DateUtil.toDateTimeString(f.lastModified()));
          result.setDepthLevel(depthLevel);
          result.setDirectory(false);
          resultList.add(result);
        }
      }
    }
    // File#listFilesは順序保証してないので並べ替える
    Collections.sort(resultList);
    return resultList;
  }

  /**
   * zipファイルを解凍して、指定したアップロード先へ配置します<br>
   * 
   * @param zipFilePath
   * @param uploadPath
   * @return true:配置成功 false:配置失敗
   */
  private boolean zipContentsMelt(String zipFilePath, String uploadPath) {

    Logger logger = Logger.getLogger(this.getClass());

    boolean result = true;

    ZipFile zip = null;
    try {
      zip = new ZipFile(zipFilePath);
    } catch (IOException e) {
      logger.error(e);
      return false;
    }

    String zipMeltPath = getZipMeltPath();

    // ZIPファイルを解凍する
    result &= extractZipFile(zip, zipMeltPath);

    // 解凍したファイルをアップロード先へ配置する
    if (result) {
      ServiceResult serviceResult = moveDirectory(zipMeltPath, uploadPath);
      if (serviceResult.hasError()) {
        result = false;
      }
    }

    // 10.1.2 10090 追加 ここから
    // 解凍したフォルダを削除する
    deleteAll(new File(zipMeltPath));
    // 10.1.2 10090 追加 ここまで

    return result;
  }

  /**
   * zipファイルの解凍先(テンポラリ)を取得します<br>
   * 
   * @return zipファイルの解凍先
   */
  private String getZipMeltPath() {
    Logger logger = Logger.getLogger(this.getClass());
    String zipMeltPath = "";
    try {
      ContentsPath path = DIContainer.get("contentsPath");

      StringBuilder builder = new StringBuilder();
      builder.append(path.getZipMeltPath());
      builder.append(new SimpleDateFormat("yyyyMMddHHmmSS").format(DateUtil.getSysdate()) + getFileSeparator());

      zipMeltPath = builder.toString();
    } catch (NoSuchBeanDefinitionException e) {
      logger.error(e);
    }

    return zipMeltPath;
  }

  /**
   * 指定のファイルが指定の移動先ディレクトリに移動可能かどうか判定します。
   * 
   * @param source
   *          移動するファイル
   * @param destDir
   *          移動先ディレクトリ
   */
  private boolean movable(File source, String destDir) {
    if (source.isFile()) {
      File target = new File(destDir + getFileSeparator() + getFileName(source.getPath()));
      return !target.exists() || target.canWrite();
    }
    if (source.isDirectory()) {
      boolean result = true;
      for (File subFile : source.listFiles()) {
        result &= movable(subFile, destDir + getFileSeparator() + getFileName(source.getPath()));
      }
      return result;
    }
    return false;
  }

  /**
   * 指定のファイルを指定の移動先ディレクトリに移動します。
   * 
   * @param
   */
  private boolean moveTo(File source, String destDir) {
    boolean result = true;
    FileInputStream fis = null;
    FileOutputStream fos = null;
    BufferedOutputStream out = null;
    BufferedInputStream in = null;
    Logger logger = Logger.getLogger(this.getClass());

    if (source.isFile()) {
      try {
        File to = new File(destDir);
        if (!to.exists()) {
          if (!to.mkdirs()) {
            logger.error(Messages.log("service.impl.DataIOServiceImpl.1"));
          }
        }
        fis = new FileInputStream(source);
        fos = new FileOutputStream(destDir + getFileSeparator() + getFileName(source.getPath()));
        in = new BufferedInputStream(fis);
        out = new BufferedOutputStream(fos);
        int c;
        byte[] buffer = new byte[DIContainer.getWebshopConfig().getDataIOBufferSize()];
        while ((c = in.read(buffer)) != -1) {
          out.write(buffer, 0, c);
        }
      } catch (FileNotFoundException e) {
        logger.error(e);
        result = false;
      } catch (IOException e) {
        logger.error(e);
        result = false;
      } finally {
        IOUtil.flush(out);
        IOUtil.close(out);
        IOUtil.close(in);
        IOUtil.close(fos);
        IOUtil.close(fis);
      }
    }
    if (source.isDirectory()) {
      for (File file : source.listFiles()) {
        result &= moveTo(file, destDir + getFileSeparator() + getFileName(source.getPath()));
      }
    }
    return result;
  }

  /**
   * 指定したディレクトリ以下の階層全てのフォルダとファイルを移動します<br>
   * 
   * @param from
   * @param to
   */
  private ServiceResult moveDirectory(String from, String to) {
    ServiceResultImpl result = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());

    File dir = new File(from);

    if (!dir.isDirectory()) {
      result.addServiceError(DataIOServiceErrorContent.FILE_MOVE_ERROR);
      return result;
    }

    File[] files = dir.listFiles();

    // 全てのファイルが移動可能かどうか判定します。
    boolean movable = true;
    for (File file : files) {
      movable &= movable(file, to);
    }

    if (!movable) {
      result.addServiceError(DataIOServiceErrorContent.FILE_OVERWRITE_FAILURE_ERROR);
      return result;
    }

    boolean moveResult = true;
    for (File file : files) {
      moveResult &= moveTo(file, to);
      if (!file.delete()) {
        logger.error(Messages.log("service.impl.DataIOServiceImpl.2") + getFileName(file.getPath()));
      }
    }
    if (!moveResult) {
      result.addServiceError(DataIOServiceErrorContent.FILE_MOVE_ERROR);
    }
    return result;
  }

  /**
   * Zipファイルを指定されたパスに解凍します<br>
   * 
   * @param zip
   * @param meltPath
   * @return true:解凍成功 false:解凍失敗
   */
  private boolean extractZipFile(ZipFile zip, String meltPath) {
    Logger logger = Logger.getLogger(this.getClass());
    boolean result = true;
    try {
      for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements();) {
        ZipEntry target = (ZipEntry) entries.nextElement();
        if (!isValid(target.getName()) && !target.isDirectory()) {
          logger.error(target.getName() + Messages.log("service.impl.DataIOServiceImpl.6"));
          result &= false;
        } else if (!extentionCheck(getFileType(target.getName())) && !target.isDirectory()) {
          logger.error(target.getName() + Messages.log("service.impl.DataIOServiceImpl.5"));
          result &= false;
        } else {
          getEntry(zip, target, meltPath);
        }
      }
    } catch (Exception e) {
      logger.error(e);
      return false;
    } finally {
      IOUtil.close(zip);
    }
    return result;
  }

  /**
   * Zipファイル内のエントリーを指定されたパスに配置します<br>
   * 
   * @param zip
   * @param target
   * @param meltPath
   */
  private void getEntry(ZipFile zip, ZipEntry target, String meltPath) throws IOException {
    BufferedInputStream inputStream = null;
    Logger logger = Logger.getLogger(this.getClass());

    try {
      File file = new File(meltPath + target.getName());

      if (target.isDirectory() && !file.exists()) {
        if (!file.mkdirs()) {
          logger.error(Messages.log("service.impl.DataIOServiceImpl.4"));
        }
      } else {
        inputStream = new BufferedInputStream(zip.getInputStream(target));
        String parentName;
        parentName = file.getParent();

        if (parentName != null) {
          File dir = new File(parentName);
          if (!dir.exists()) {
            if (!dir.mkdirs()) {
              logger.error(Messages.log("service.impl.DataIOServiceImpl.3"));
            }
          }
        }
        FileOutputStream fos = null;
        BufferedOutputStream outputStream = null;
        try {
          fos = new FileOutputStream(file);
          outputStream = new BufferedOutputStream(fos);
          byte[] buffer = new byte[DIContainer.getWebshopConfig().getDataIOBufferSize()];
          int c;
          while ((c = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, c);
          }
        } catch (IOException ioEx) {
          throw ioEx;
        } finally {
          IOUtil.close(outputStream);
          IOUtil.close(fos);
        }
      }
    } catch (IOException e) {
      throw e;
    } finally {
      IOUtil.close(inputStream);
    }
  }

  /**
   * ファイル名から拡張子を取得します<br>
   * 
   * @param fileName
   * @return ファイルの拡張子
   */
  private static String getFileType(String fileName) {

    if (StringUtil.isNull(fileName)) {
      return "";

    }
    if (fileName.lastIndexOf(".") > 0) {
      return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    } else {
      return "";
    }
  }

  public boolean contentsExists(ContentsSearchCondition condition) {
    StringBuilder builder = new StringBuilder();
    builder.append(getContentsDirectoryPath(condition));
    builder.append(getContentsFile(condition));

    File file = new File(builder.toString());
    return file.exists();
  }

  public SearchResult<ContentsListResult> getPartialContentsList(ContentsSearchCondition condition) {

    String contentsPath = getContentsDirectoryPath(condition);
    List<ContentsListResult> files = getFileList(contentsPath, 0L);

    condition.getPageSize();
    condition.getCurrentPage();
    int currentPage = condition.getCurrentPage();
    if (currentPage < 0) {
      currentPage = 0;
    }
    int pageSize = condition.getPageSize();
    if (pageSize < 0) {
      pageSize = 0;
    }
    int start = (condition.getCurrentPage() - 1) * condition.getPageSize();
    if (start < 0) {
      start = 0;
    }
    List<ContentsListResult> parts = new ArrayList<ContentsListResult>();
    for (int i = start; i < start + pageSize; i++) {
      if (i < files.size()) {
        parts.add(files.get(i));
      } else {
        break;
      }
    }
    SearchResult<ContentsListResult> result = new SearchResult<ContentsListResult>();
    result.setRows(parts);
    result.setRowCount(files.size());
    result.setCurrentPage(currentPage);
    result.setMaxFetchSize(condition.getMaxFetchSize());
    result.setOverflow(false);
    result.setPageSize(pageSize);

    return result;
  }

  public List<ContentsListResult> getContentsList(ContentsSearchCondition condition) {
    String contentsPath = getContentsDirectoryPath(condition);
    return getFileList(contentsPath, 0L);
  }

  public String getContentsUrl(ContentsSearchCondition condition) {
    Logger logger = Logger.getLogger(this.getClass());
    String contentsDirectoryUrl = "";

    try {
      ContentsPath path = DIContainer.get("contentsPath");
      StringBuilder builder = new StringBuilder();
      builder.append(path.getContentsServerPath());
      builder.append(getContentsPath(condition));
      builder.append(getContentsFile(condition));
      contentsDirectoryUrl = builder.toString();
    } catch (NoSuchBeanDefinitionException e) {
      logger.error(e);
    }

    logger.debug("getContentsUrl=" + contentsDirectoryUrl);
    return contentsDirectoryUrl;
  }

  /**
   * コンテンツのパス情報を取得します<br>
   * 
   * @param info
   * @return コンテンツのパス情報
   */
  private String getContentsPath(ContentsInfo info) {
    ContentsSearchCondition condition = new ContentsSearchCondition();
    condition.setContentsType(info.getContentsType());

    Logger logger = Logger.getLogger(this.getClass());
    try {
      if (info.getContentsType().equals(ContentsType.CONTENT_SITE_CATEGORY)) {
        // カテゴリトップコンテンツの場合
        CategoryContents contents = (CategoryContents) info;
        condition.setCategoryCode(contents.getCategoryCode());
      } else if (info.getContentsType().equals(ContentsType.CONTENT_SHOP_CAMPAIGN_SITE)
          || info.getContentsType().equals(ContentsType.CONTENT_SHOP_CAMPAIGN_SHOP)) {
        // キャンペーンコンテンツの場合
        CampaignContents contents = (CampaignContents) info;
        condition.setShopCode(contents.getShopCode());
        condition.setCampaignCode(contents.getCampaignCode());
      } else if (info.getContentsType().equals(ContentsType.CONTENT_SHOP_SHOPINFO_SITE)
          || info.getContentsType().equals(ContentsType.CONTENT_SHOP_SHOPINFO_SHOP)) {
        // ショップ情報コンテンツの場合
        ShopinfoContents contents = (ShopinfoContents) info;
        condition.setShopCode(contents.getShopCode());
      } else if (info.getContentsType().equals(ContentsType.IMAGE_DATA_SHOP_COMMODITY)) {
        if (info instanceof CommodityImage) {
          // 商品画像の場合
          CommodityImage image = (CommodityImage) info;
          condition.setShopCode(image.getShopCode());
        } else if (info instanceof SkuImage) {
          // SKU画像の場合
          SkuImage image = (SkuImage) info;
          condition.setShopCode(image.getShopCode());
        }
      } else if (info.getContentsType().equals(ContentsType.IMAGE_DATA_SHOP_GIFT)) {
        // ギフト画像の場合
        GiftImage image = (GiftImage) info;
        condition.setShopCode(image.getShopCode());
      } else if (info.getContentsType().equals(ContentsType.CONTENT_SHOP_COMPLIANCE)) {
        ShopComplianceContents contents = (ShopComplianceContents) info;
        condition.setShopCode(contents.getShopCode());
      } else if (info.getContentsType().equals(ContentsType.CONTENT_SHOP_MOBILE)) {
        ShopMobileContents contents = (ShopMobileContents) info;
        condition.setShopCode(contents.getShopCode());
        // 20121024 txw add start
      } else if (info.getContentsType().equals(ContentsType.INDEX_TOP_IMAGE)) {
        // 首页TOP图片
        IndexTopImage image = (IndexTopImage) info;
        condition.setShopCode(image.getShopCode());
        condition.setTopImgLanguageCode(image.getLanguageCode());
      } else if (info.getContentsType().equals(ContentsType.INDEX_FLOOR_IMAGE)) {
        // 首页楼层图片
        IndexFloorImage image = (IndexFloorImage) info;
        condition.setShopCode(image.getShopCode());
        condition.setFloorImgLanguageCode(image.getLanguageCode());
      }
      // 20121024 txw add end
    } catch (NoSuchBeanDefinitionException e) {
      logger.error(e);
    }

    return getContentsPath(condition);
  }

  /**
   * コンテンツのパス情報を取得します<br>
   * 
   * @param condition
   * @return コンテンツのパス情報
   */
  private String getContentsPath(ContentsSearchCondition condition) {

    Logger logger = Logger.getLogger(this.getClass());
    String contentsPath = "";

    try {
      ContentsPath path = DIContainer.get("contentsPath");
      StringBuilder builder = new StringBuilder();

      if (condition.getContentsType().equals(ContentsType.CONTENT_SITE_TOPPAGE)) {
        // トップページコンテンツの場合
        builder.append(path.getTopPath());
      } else if (condition.getContentsType().equals(ContentsType.CONTENT_SITE_GUIDE)) {
        // ヘルプコンテンツの場合
        builder.append(path.getAboutusPath());
      } else if (condition.getContentsType().equals(ContentsType.CONTENT_SITE_ABOUTUS)) {
        builder.append(path.getAboutusPath());
      } else if (condition.getContentsType().equals(ContentsType.CONTENT_SITE_RULE)) {
        // 利用規約コンテンツの場合
        builder.append(path.getRulePath());
        // } else if
        // (condition.getContentsType().equals(ContentsType.CONTENT_SITE_COMPLIANCE))
        // {
        // // 特定商取引法に基づく表記コンテンツの場合
        // builder.append(path.getCompliancePath());
      } else if (condition.getContentsType().equals(ContentsType.CONTENT_SHOP_COMPLIANCE)) {
        // 特定商取引法に基づく表記(ショップ管理)コンテンツの場合
        builder.append("/shop/" + condition.getShopCode() + path.getShopCompliancePath());
        // 2010/04/27 ShiKui Add Start.
        // } else if
        // (condition.getContentsType().equals(ContentsType.CONTENT_SITE_PRIVACY))
        // {
        // // 個人情報保護コンテンツの場合
        // builder.append(path.getPrivacyPath());
        // 2010/04/27 ShiKui Add End.
      } else if (condition.getContentsType().equals(ContentsType.CONTENT_SITE_CATEGORY)) {
        // カテゴリトップコンテンツの場合
        builder.append(path.getCategoryPath() + "/" + condition.getCategoryCode());
      } else if (condition.getContentsType().equals(ContentsType.CONTENT_SITE_LOGIN)) {
        // ログイン広告コンテンツの場合
        builder.append(path.getLoginPath());
      } else if (condition.getContentsType().equals(ContentsType.CONTENT_SITE_THANKYOU)) {
        // 注文完了広告コンテンツの場合
        builder.append(path.getThanksPath());
      } else if (condition.getContentsType().equals(ContentsType.CONTENT_SITE_ENQUETE)) {
        // アンケート完了広告コンテンツの場合
        builder.append(path.getEnquetePath());
      } else if (condition.getContentsType().equals(ContentsType.CONTENT_SHOP_CAMPAIGN_SITE)
          || condition.getContentsType().equals(ContentsType.CONTENT_SHOP_CAMPAIGN_SHOP)) {
        // キャンペーンコンテンツの場合
        builder.append("/shop/" + condition.getShopCode() + path.getCampaignPath() + "/" + condition.getCampaignCode());
      } else if (condition.getContentsType().equals(ContentsType.CONTENT_SHOP_SHOPINFO_SITE)
          || condition.getContentsType().equals(ContentsType.CONTENT_SHOP_SHOPINFO_SHOP)) {
        // ショップ情報コンテンツの場合
        builder.append("/shop/" + condition.getShopCode() + path.getShopinfoPath());
      } else if (condition.getContentsType().equals(ContentsType.IMAGE_DATA_SHOP_COMMODITY)) {
        // 商品画像の場合
        builder.append("/shop/" + condition.getShopCode() + path.getCommodityPath());
      } else if (condition.getContentsType().equals(ContentsType.IMAGE_DATA_SHOP_GIFT)) {
        // ギフト画像の場合
        builder.append("/shop/" + condition.getShopCode() + path.getGiftPath());
      } else if (condition.getContentsType().equals(ContentsType.CONTENT_SITE_MOBILE)) {
        // 携帯用コンテンツ(サイト)の場合
        builder.append(path.getSiteinfoMobilePath());
      } else if (condition.getContentsType().equals(ContentsType.CONTENT_SHOP_MOBILE)) {
        // 携帯用コンテンツ(ショップ)の場合
        builder.append("/shop/" + condition.getShopCode() + path.getShopinfoMobilePath());
        // 20131029 txw add start
      } else if (condition.getContentsType().equals(ContentsType.INDEX_TOP_IMAGE)) {
        ImageUploadConfig iuConfig = DIContainer.getImageUploadConfig();
        // 首页TOP图片场合
        builder.append("/" + condition.getTopImgLanguageCode() + iuConfig.getTopImgPath());
      } else if (condition.getContentsType().equals(ContentsType.INDEX_FLOOR_IMAGE)) {
        ImageUploadConfig iuConfig = DIContainer.getImageUploadConfig();
        // 首页楼层图片场合
        builder.append("/" + condition.getFloorImgLanguageCode() + iuConfig.getTagsImgPath());
      }
      // 20131029 txw add end
      contentsPath = builder.toString();
    } catch (NoSuchBeanDefinitionException e) {
      logger.error(e);
    }

    return contentsPath;
  }

  /**
   * コンテンツのファイル名を取得します<br>
   * 
   * @param condition
   * @return コンテンツのファイル名
   */
  private String getContentsFile(ContentsSearchCondition condition) {
    Logger logger = Logger.getLogger(this.getClass());
    String contentsFile = "";

    try {
      ContentsFile path = DIContainer.get("contentsFile");
      StringBuilder builder = new StringBuilder();

      if (condition.getContentsType().equals(ContentsType.CONTENT_SITE_TOPPAGE)

      || condition.getContentsType().equals(ContentsType.CONTENT_SITE_CATEGORY)
          || condition.getContentsType().equals(ContentsType.CONTENT_SITE_LOGIN)
          || condition.getContentsType().equals(ContentsType.CONTENT_SITE_THANKYOU)
          || condition.getContentsType().equals(ContentsType.CONTENT_SITE_ENQUETE)
          || condition.getContentsType().equals(ContentsType.CONTENT_SHOP_CAMPAIGN_SHOP)
          || condition.getContentsType().equals(ContentsType.CONTENT_SHOP_CAMPAIGN_SITE)
          || condition.getContentsType().equals(ContentsType.CONTENT_SHOP_SHOPINFO_SHOP)
          || condition.getContentsType().equals(ContentsType.CONTENT_SHOP_SHOPINFO_SITE)
          || condition.getContentsType().equals(ContentsType.CONTENT_SITE_RULE)
          || condition.getContentsType().equals(ContentsType.CONTENT_SITE_GUIDE)
      // ||
      // condition.getContentsType().equals(ContentsType.CONTENT_SITE_COMPLIANCE)
      // 2010/04/27 ShiKui Add Start.
      // ||
      // condition.getContentsType().equals(ContentsType.CONTENT_SITE_PRIVACY)
      // 2010/04/27 ShiKui Add End.
      ) {
        builder.append(path.getContentsIndexHtml());
      } else if (condition.getContentsType().equals(ContentsType.IMAGE_DATA_SHOP_COMMODITY)) {
        // 商品詳細画像の場合
        builder.append(path.getCommodityImagePrefix() + condition.getCommodityCode() + path.getImageExtension());
      } else if (condition.getContentsType().equals(ContentsType.IMAGE_DATA_SHOP_GIFT)) {
        // ギフト画像の場合
        // 10.1.3 10063 修正 ここから
        // builder.append(path.getGiftImagePrefix() + condition.getGiftCode() +
        // path.getImageExtension());
        builder.append("/" + condition.getGiftCode() + ImageType.GIFT_IMAGE.getSuffix() + path.getImageExtension());
        // 10.1.3 10063 修正 ここまで
      }
      contentsFile = builder.toString();
    } catch (NoSuchBeanDefinitionException e) {
      logger.error(e);
    }

    return contentsFile;
  }

  /**
   * コンテンツのディレクトリパス情報を取得します
   * 
   * @param condition
   * @return コンテンツのディレクトリパス
   */
  private String getContentsDirectoryPath(ContentsSearchCondition condition) {
    Logger logger = Logger.getLogger(this.getClass());
    String contentsDirectoryPath = "";

    try {
      ContentsPath path = DIContainer.get("contentsPath");
      StringBuilder builder = new StringBuilder();
      builder.append(path.getContentsSharedPath());
      builder.append(getContentsPath(condition));
      contentsDirectoryPath = builder.toString();
    } catch (NoSuchBeanDefinitionException e) {
      logger.error(e);
    }

    logger.debug("getContentsDirectoryPath=" + contentsDirectoryPath);
    return contentsDirectoryPath;
  }

  private void addFile(InputStream in, OutputStream out, ContentsInfo info, String type) {
    Logger logger = Logger.getLogger(this.getClass());
    try {
      int c;
      byte[] buffer = new byte[DIContainer.getWebshopConfig().getDataIOBufferSize()];
      while ((c = in.read(buffer)) != -1) {
        out.write(buffer, 0, c);
      }
    } catch (IOException e) {
      logger.error(e);
    } finally {
      IOUtil.flush(out);
      IOUtil.close(out);
    }
  }

  public ServiceResult addContents(ContentsInfo info) {

    ServiceResultImpl serviceResult = new ServiceResultImpl();

    String path = getContentsDirectoryPath(info);
    // フォルダが存在しない場合は作成
    File f = new File(path);
    if (!f.exists()) {
      if (!f.mkdirs()) {
        serviceResult.addServiceError(DataIOServiceErrorContent.DIRECTORY_CREATION_ERROR);
        return serviceResult;
      }
    }

    if (getFileType(getFileName(info.getContentsFile().getName())).equals("zip")) {
      // 圧縮ファイルを解凍してアップロードする
      if (!zipContentsMelt(info.getContentsFile().getPath(), path)) {
        serviceResult.addServiceError(DataIOServiceErrorContent.ZIPMELT_ERROR);
        return serviceResult;
      }
    } else {
      serviceResult.addServiceError(DataIOServiceErrorContent.CONTENTS_EXTENSION_ERROR);
      return serviceResult;
    }
    return serviceResult;
  }

  public ServiceResult addImage(InputStream in, ImageInfo info) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    String path = getContentsDirectoryPath(info);
    // フォルダが存在しない場合は作成
    File f = new File(path);
    if (!f.exists()) {
      if (!f.mkdirs()) {
        serviceResult.addServiceError(DataIOServiceErrorContent.DIRECTORY_CREATION_ERROR);
        return serviceResult;
      }
    }

    String fileName = getImageFileName(info);
    if (!isValid(fileName)) {
      serviceResult.addServiceError(DataIOServiceErrorContent.FILENAME_INCLUDE_JAPANESE_ERROR);
      return serviceResult;
    }
    String extention = getFileType(getFileName(info.getContentsFile().getPath()));
    if (extention.equals("jpg")) {
      FileOutputStream fos = null;
      BufferedOutputStream out = null;
      if (checkImageFileName(info)) {
        try {
          File target = new File(path + getFileSeparator() + fileName);
          if (!target.exists() || target.canWrite()) {
            fos = new FileOutputStream(target);
            out = new BufferedOutputStream(fos);
            this.addFile(in, out, info, "image");
          } else {
            serviceResult.addServiceError(DataIOServiceErrorContent.FILE_OVERWRITE_FAILURE_ERROR);
          }
        } catch (FileNotFoundException e) {
          logger.error(e);
        } finally {
          IOUtil.close(out);
          IOUtil.close(fos);
        }
      } else {
        serviceResult.addServiceError(DataIOServiceErrorContent.CONTENTS_FILE_NAME_ERROR);
        return serviceResult;
      }
    } else if (extention.equals("zip")) {
      ZipFile zip = null;
      try {
        zip = new ZipFile(info.getContentsFile());
      } catch (IOException e) {
        logger.error(e);
        serviceResult.addServiceError(DataIOServiceErrorContent.ZIPMELT_ERROR);
        return serviceResult;
      }

      if (checkFileNameInZip(info, zip)) {
        try {
          for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements();) {
            ZipEntry target = entries.nextElement();
            getEntry(zip, target, path + getFileSeparator());
          }
        } catch (IOException e) {
          logger.error(e);
          serviceResult.addServiceError(DataIOServiceErrorContent.ZIPMELT_ERROR);
          return serviceResult;
          // 10.1.2 10090 追加 ここから
        } finally {
          IOUtil.close(zip);
          // 10.1.2 10090 追加 ここまで
        }
      } else {
        // 10.1.2 10090 追加 ここから
        IOUtil.close(zip);
        // 10.1.2 10090 追加 ここまで
        serviceResult.addServiceError(DataIOServiceErrorContent.ZIPMELT_ERROR);
        return serviceResult;
      }
    } else {
      serviceResult.addServiceError(DataIOServiceErrorContent.CONTENTS_EXTENSION_ERROR);
      return serviceResult;
    }

    return serviceResult;
  }

  private boolean checkImageFileName(ImageInfo info) {

    if (info.getContentsType().equals(ContentsType.IMAGE_DATA_SHOP_COMMODITY)) {
      return checkCommodityImageFileName(info);
    } else if (info.getContentsType().equals(ContentsType.IMAGE_DATA_SHOP_GIFT)) {
      return checkGiftImageFileName(info);
      // 20131029 txw add start
    } else if (info.getContentsType().equals(ContentsType.INDEX_TOP_IMAGE)
        || info.getContentsType().equals(ContentsType.INDEX_FLOOR_IMAGE)) {
      return checkIndexImageFileName(info);
    }
    // 20131029 txw add end
    return false;
  }

  private boolean checkCommodityImageFileName(ImageInfo info) {

    String fileName = getImageFileName(info);
    String shopCode = info.getShopCode();

    if (StringUtil.isNullOrEmptyAnyOf(fileName, shopCode)) {
      return false;
    }

    return commodityOrSkuCodeInFileNameExists(shopCode, fileName);
  }

  private boolean checkGiftImageFileName(ImageInfo info) {

    String fileName = getImageFileName(info);
    String shopCode = info.getShopCode();

    if (StringUtil.isNullOrEmptyAnyOf(fileName, shopCode)) {
      return false;
    }

    return giftCodeInFileNameExists(shopCode, fileName);
  }

  // 20131029 txw add start
  private boolean checkIndexImageFileName(ImageInfo info) {

    String fileName = getImageFileName(info);
    String shopCode = info.getShopCode();

    if (StringUtil.isNullOrEmptyAnyOf(fileName, shopCode)) {
      return false;
    }

    return indexImgNameInFileNameExists(shopCode, fileName);
  }

  private boolean indexImgNameInFileNameExists(String shopCode, String fileName) {

    Logger logger = Logger.getLogger(this.getClass());
    boolean result = false;

    // ファイル名のみを取得（拡張子切り落とし）
    String extention = getFileType(fileName);
    String name = fileName.substring(0, fileName.lastIndexOf("." + extention));

    if (extention.equals("jpg")) {
      // 画像タイプ（命名規則タイプ）の判定
      if (name.matches(INDEX_IMAGENAME)) {
        return true;
      } else if (name.matches(REGEX_PATTERN_FILENAME)) {
        logger.error(fileName + Messages.log("service.impl.DataIOServiceImpl.11"));
      } else {
        logger.error(fileName + Messages.log("service.impl.DataIOServiceImpl.12"));
        return result;
      }
    } else {
      logger.error(fileName + Messages.log("service.impl.DataIOServiceImpl.13"));
    }

    return result;
  }

  // 20131029 txw add end
  private boolean checkFileNameInZip(ImageInfo info, ZipFile zip) {

    for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements();) {
      ZipEntry target = (ZipEntry) entries.nextElement();

      if (info.getContentsType().equals(ContentsType.IMAGE_DATA_SHOP_COMMODITY)) {
        if (!checkCommodityFileNameInZip(info, target.getName())) {
          return false;
        }
      } else if (info.getContentsType().equals(ContentsType.IMAGE_DATA_SHOP_GIFT)) {
        if (!checkGiftFileNameInZip(info, target.getName())) {
          return false;
        }
      }
    }

    return true;
  }

  private boolean checkCommodityFileNameInZip(ImageInfo info, String fileName) {

    String shopCode = info.getShopCode();
    if (StringUtil.isNullOrEmptyAnyOf(shopCode, fileName)) {
      return false;
    }

    return commodityOrSkuCodeInFileNameExists(shopCode, fileName);
  }

  private boolean checkGiftFileNameInZip(ImageInfo info, String fileName) {

    String shopCode = info.getShopCode();

    if (StringUtil.isNullOrEmptyAnyOf(shopCode, fileName)) {
      return false;
    }

    return giftCodeInFileNameExists(shopCode, fileName);
  }

  /** そのコード値が何のコードを表すのか指定します。 */
  private enum CodeType {
    /** SKUコード */
    SKU_CODE,
    /** 商品コード */
    COMMODITY_CODE;
  };

  private boolean commodityOrSkuCodeInFileNameExists(String shopCode, String fileName) {

    Logger logger = Logger.getLogger(this.getClass());
    boolean result = false;

    // ファイル名のみを取得（拡張子切り落とし）
    String extention = getFileType(fileName);
    String name = fileName.substring(0, fileName.lastIndexOf("." + extention));

    if (extention.equals("jpg")) {
      CodeType type = null;
      String code = "";

      // 画像タイプ（命名規則タイプ）の判定
      if (name.matches(SKU_DETAIL_IMAGENAME_MOBILE)) {
        type = CodeType.SKU_CODE;
        code = name.substring(0, name.lastIndexOf("_mk"));
      } else if (name.matches(COMMODITY_THUMBNAIL_IMAGENAME_MOBILE)) {
        type = CodeType.COMMODITY_CODE;
        code = name.substring(0, name.lastIndexOf("_mt"));
      } else if (name.matches(SKU_DETAIL_IMAGENAME_PC)) {
        type = CodeType.SKU_CODE;
        code = name.substring(0, name.lastIndexOf("_k"));
      } else if (name.matches(COMMODITY_THUMBNAIL_IMAGENAME_PC)) {
        type = CodeType.COMMODITY_CODE;
        code = name.substring(0, name.lastIndexOf("_t"));
      } else if (name.matches(COMMODITY_DETAIL_IMAGENAME_PC)) {
        type = CodeType.COMMODITY_CODE;
        code = name;
      } else {
        logger.error(fileName + Messages.log("service.impl.DataIOServiceImpl.7"));
        return result;
      }

      Query query;
      if (type == null) {
        logger.error(fileName + Messages.log("service.impl.DataIOServiceImpl.8"));
        return false;
      }
      switch (type) {
        case COMMODITY_CODE:
          query = new SimpleQuery(DataQuery.GET_COMMODITY_CODE_EXISTS, shopCode, code);
          result = DatabaseUtil.executeScalar(query) != null;
          break;
        case SKU_CODE:
          query = new SimpleQuery(DataQuery.GET_SKU_CODE_EXISTS, shopCode, code);
          result = DatabaseUtil.executeScalar(query) != null;
          break;
        default:
          result = false;
          break;
      }
      if (!result) {
        logger.error(fileName + Messages.log("service.impl.DataIOServiceImpl.9"));
      }
    } else {
      logger.error(fileName + Messages.log("service.impl.DataIOServiceImpl.10"));
    }
    return result;
  }

  private boolean giftCodeInFileNameExists(String shopCode, String fileName) {

    Logger logger = Logger.getLogger(this.getClass());
    boolean result = false;

    // ファイル名のみを取得（拡張子切り落とし）
    String extention = getFileType(fileName);
    String name = fileName.substring(0, fileName.lastIndexOf("." + extention));

    if (extention.equals("jpg")) {
      // 画像タイプ（命名規則タイプ）の判定
      String code = null;
      if (name.matches(GIFT_IMAGENAME)) {
        code = name.substring(0, name.lastIndexOf("_g"));
      } else if (name.matches(REGEX_PATTERN_FILENAME)) {
        logger.error(fileName + Messages.log("service.impl.DataIOServiceImpl.11"));
      } else {
        logger.error(fileName + Messages.log("service.impl.DataIOServiceImpl.12"));
        return result;
      }

      if (StringUtil.hasValue(code)) {
        Query query = new SimpleQuery(DataQuery.GET_GIFT_EXISTS, shopCode, code);
        result = DatabaseUtil.executeScalar(query) != null;
      }
    } else {
      logger.error(fileName + Messages.log("service.impl.DataIOServiceImpl.13"));
    }

    return result;
  }

  private void deleteAll(File file) {
    if (file.isDirectory()) {
      File[] f = file.listFiles();
      for (int i = 0; i < f.length; i++) {
        deleteAll(f[i]);
      }
    }

    if (!file.delete()) {
      // 10.1.2 10090 追加 ここから
      Logger logger = Logger.getLogger(this.getClass());
      logger.warn(Messages.log("service.impl.DataIOServiceImpl.14") + file.getAbsolutePath());
      // 10.1.2 10090 追加 ここまで
      return;
    }
  }

  public String getContentsData(String path) {
    Logger logger = Logger.getLogger(this.getClass());

    StringBuilder builder = new StringBuilder();
    Reader reader = null;
    BufferedReader bufReader = null;

    try {
      reader = IOUtil.getReader(new File(path));

      if (reader instanceof BufferedReader) {
        bufReader = (BufferedReader) reader;
      } else {
        bufReader = new BufferedReader(reader);
      }

      String line;
      while ((line = bufReader.readLine()) != null) {
        builder.append(line);
        builder.append("\n");
      }
      reader.close();
    } catch (IOException e) {
      logger.error(e);
    } finally {
      IOUtil.close(bufReader);
      IOUtil.close(reader);
    }

    return builder.toString();
  }

  public ServiceResult addText(InputStream in, ContentsInfo info) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    String path = getContentsDirectoryPath(info);
    // フォルダが存在しない場合は作成
    File f = new File(path);
    if (!f.exists()) {
      if (!f.mkdirs()) {
        serviceResult.addServiceError(DataIOServiceErrorContent.DIRECTORY_CREATION_ERROR);
        return serviceResult;
      }
    }
    BufferedOutputStream out = null;
    if (!getFileType(info.getContentsFile().getName()).equals("txt")) {
      serviceResult.addServiceError(DataIOServiceErrorContent.CONTENTS_EXTENSION_ERROR);
      return serviceResult;
    } else if (!isValid(info.getContentsFile().getName())) {
      serviceResult.addServiceError(DataIOServiceErrorContent.FILENAME_INCLUDE_JAPANESE_ERROR);
      return serviceResult;
    } else {
      try {
        File target = new File(path + getFileSeparator() + info.getContentsFile().getName());
        if (!target.exists() || target.canWrite()) {
          out = new BufferedOutputStream(new FileOutputStream(target));
          this.addFile(in, out, info, "txt");
        } else {
          serviceResult.addServiceError(DataIOServiceErrorContent.FILE_OVERWRITE_FAILURE_ERROR);
        }
      } catch (FileNotFoundException e) {
        logger.error(e);
      } finally {
        IOUtil.close(out);
      }
    }
    return serviceResult;
  }

  private boolean imageExists(ImageInfo info, boolean isPc) {
    String shopCode = info.getShopCode();
    String code = "";
    String suffix = info.getImageType().getSuffix();
    // 画像ファイル名のコード値、接尾辞の取得
    if (info instanceof CommodityImage) {
      CommodityImage commodityImage = (CommodityImage) info;
      code = commodityImage.getCommodityCode();
    } else if (info instanceof SkuImage) {
      SkuImage skuImage = (SkuImage) info;
      code = skuImage.getSkuCode();
    }

    // 画像ファイル格納パスの取得
    ContentsPath path = DIContainer.get("contentsPath");
    StringBuilder builder = new StringBuilder();
    builder.append(path.getContentsSharedPath());
    builder.append("/shop/" + shopCode + path.getCommodityPath());
    File imageDirectory = new File(builder.toString());
    String target = code + suffix + ".jpg";

    // 画像ファイル有無の判定
    if (imageDirectory.isDirectory()) {
      // 10.1.4 10192 修正 ここから
      // for (String fileName : imageDirectory.list()) {
      // if (fileName.equals(target)) {
      // return true;
      // }
      // }
      return new File(imageDirectory, target).exists();
      // 10.1.4 10192 修正 ここまで
    }
    return false;
  }

  private List<String> detailImageExists(ImageInfo info, boolean isPc) {
    List<String> lsStr = new ArrayList<String>();
    String shopCode = info.getShopCode();
    String code = "";
    // 画像ファイル名のコード値、接尾辞の取得
    if (info instanceof CommodityImage) {
      CommodityImage commodityImage = (CommodityImage) info;
      code = commodityImage.getCommodityCode();
    } else if (info instanceof SkuImage) {
      SkuImage skuImage = (SkuImage) info;
      code = skuImage.getSkuCode();
    }

    // 画像ファイル格納パスの取得
    ContentsPath path = DIContainer.get("contentsPath");
    StringBuilder builder = new StringBuilder();
    builder.append(path.getContentsSharedPath());
    builder.append("/shop/" + shopCode + path.getDetailCommodityPath());
    File imageDirectory = new File(builder.toString());
    String target = "";

    // 画像ファイル有無の判定
    if (imageDirectory.isDirectory()) {

      for (int i = 1; i < 5; i++) {
        target = code + "_" + i + ".jpg";
        if (new File(imageDirectory, target).exists()) {
          lsStr.add(target);
        }
      }
    }
    return lsStr;
  }

  public boolean thumbnailImageExists(String shopCode, String commodityCode, boolean isPc) {
    CommodityImage info = new CommodityImage(shopCode, commodityCode);
    if (isPc) {
      info.setImageType(ImageType.COMMODITY_THUMBNAIL_PC);
    } else {
      info.setImageType(ImageType.COMMODITY_THUMBNAIL_MOBILE);
    }
    return imageExists(info, isPc);
  }

  public boolean commodityImageExists(String shopCode, String commodityCode, boolean isPc) {
    CommodityImage info = new CommodityImage(shopCode, commodityCode);
    if (isPc) {
      info.setImageType(ImageType.COMMODITY_IMAGE_PC);
    } else {
      info.setImageType(ImageType.COMMODITY_IMAGE_MOBILE);
    }
    return imageExists(info, isPc);
  }

  public boolean skuImageExists(String shopCode, String skuCode, boolean isPc) {
    SkuImage info = new SkuImage(shopCode, skuCode);
    if (isPc) {
      info.setImageType(ImageType.SKU_IMAGE_PC);
    } else {
      info.setImageType(ImageType.SKU_IMAGE_MOBILE);
    }
    return imageExists(info, isPc);
  }

  public List<String> readDetailImages(String shopCode, String skuCode, boolean isPc) {

    SkuImage info = new SkuImage(shopCode, skuCode);
    if (isPc) {
      info.setImageType(ImageType.SKU_IMAGE_PC);
    } else {
      info.setImageType(ImageType.SKU_IMAGE_MOBILE);
    }
    return detailImageExists(info, isPc);
  }

  private boolean isValid(Object value) {
    if (value == null || value instanceof Number) {
      return true;
    }
    return ValidatorUtil.patternMatches(REGEX_PATTERN_FILENAME, value);
  }

  private boolean extentionCheck(Object value, String allowedExtentionPattern) {
    if (value == null || value instanceof Number) {
      return true;
    }
    return ValidatorUtil.patternMatches(allowedExtentionPattern, value);
  }

  private boolean extentionCheck(Object value) {
    return extentionCheck(value, DIContainer.getWebshopConfig().getAllowedUploadExtention());
  }

  public ContentsListResult getRandomContents(ContentsSearchCondition condition) {
    String contentsPath = getContentsDirectoryPath(condition);
    return getFileRandom(contentsPath, 0L);
  }

  private ContentsListResult getFileRandom(String filePath, Long depthLevel) {

    File file = new File(filePath);
    String[] fileList = file.list(new FileFilter());

    if (fileList == null) {
      return new ContentsListResult();
    }

    Random rnd = new Random();
    ContentsListResult result = new ContentsListResult();
    if (fileList.length > 0) {
      int ran = rnd.nextInt(fileList.length);

      if (file.exists()) {
        Arrays.sort(fileList);

        File f = new File(filePath + getFileSeparator() + fileList[ran]);

        result.setDirectoryPath(f.getPath());
        result.setFileName(f.getName());

      }
    }
    return result;
  }

  public <S extends CsvSchema, C extends CsvExportCondition<S>>ServiceResult exportCsv(S schema, Writer writer, C condition) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl result = new ServiceResultImpl();
    ExportDataSource<S, C> dataSource = null;
    try {
      CsvHandler handler = DIContainer.getCsvHandler();
      dataSource = DIContainer.get(schema.getExportConfigureId());
      dataSource.setSchema(schema);
      condition.setLoginInfo(getLoginInfo());
      dataSource.setCondition(condition);
      CsvResult csvResult = handler.exportData(writer, dataSource);
      logger.info(csvResult.getMessage());
      if (csvResult.getExportCount() == 0) {
        result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      }
    } catch (CsvExportException ex) {
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      logger.error(ex);
    } catch (Exception ex) {
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      logger.error(ex);
    } finally {
      if (dataSource != null) {
        dataSource.dispose();
      }
    }
    return result;
  }

  public <S extends CsvSchema, C extends CsvImportCondition<S>>CsvResult importCsv(S schema, Reader reader, C condition) {
    Logger logger = Logger.getLogger(this.getClass());
    ImportDataSource<S, C> dataSource = null;
    CsvResult result = new CsvResult();
    try {
      CsvHandler handler = DIContainer.getCsvHandler();
      dataSource = DIContainer.get(schema.getImportConfigureId());
      dataSource.setSchema(schema);
      condition.setLoginInfo(getLoginInfo());
      dataSource.setCondition(condition);
      result = handler.importData(reader, dataSource);
      logger.info(result.getMessage());
    } catch (CsvImportException ex) {
      logger.error(ex);
      result.setAborted(true);
    } catch (Exception ex) {
      logger.error(ex);
      result.setAborted(true);
    } finally {
      if (dataSource != null) {
        dataSource.dispose();
      }
    }
    return result;
  }

  // 2012-01-09 yyq add start desc:接口使用的csv导入共通
  @Override
  public <S extends CsvSchema, C extends CsvImportCondition<S>>CsvResult importCsvIf(S schema, Reader reader, C condition) {
    Logger logger = Logger.getLogger(this.getClass());
    ImportDataSource<S, C> dataSource = null;
    CsvResult result = new CsvResult();
    try {
      CsvHandler handler = DIContainer.getCsvHandler();
      dataSource = DIContainer.get(schema.getImportConfigureId());
      dataSource.setSchema(schema);
      condition.setLoginInfo(getLoginInfo());
      dataSource.setCondition(condition);
      result = handler.importDataIf(reader, dataSource);
      logger.info(result.getMessage());
    } catch (CsvImportException ex) {
      logger.error(ex);
      result.setAborted(true);
    } catch (Exception ex) {
      logger.error(ex);
      result.setAborted(true);
    } finally {
      if (dataSource != null) {
        dataSource.dispose();
      }
    }
    return result;
  }

  // 2012-02-29 OS011 add start desc:接口使用的csv导入共通SKU重复
  @Override
  public <S extends CsvSchema, C extends CsvImportCondition<S>>CsvResult importCsvIfSkuExist(S schema, Reader reader, C condition) {
    Logger logger = Logger.getLogger(this.getClass());
    ImportDataSource<S, C> dataSource = null;
    CsvResult result = new CsvResult();
    try {
      CsvHandler handler = DIContainer.getCsvHandler();
      dataSource = DIContainer.get(schema.getImportConfigureId());
      dataSource.setSchema(schema);
      condition.setLoginInfo(getLoginInfo());
      dataSource.setCondition(condition);
      result = handler.importDataIfSkuExist(reader, dataSource);
      logger.info(result.getMessage());
    } catch (CsvImportException ex) {
      logger.error(ex);
      result.setAborted(true);
    } catch (Exception ex) {
      logger.error(ex);
      result.setAborted(true);
    } finally {
      if (dataSource != null) {
        dataSource.dispose();
      }
    }
    return result;
  }

  // 2012-02-29 OS011 add end desc:接口使用的csv导入共通SKU重复
  private List<File> getImageFileList(ImageInfo info) {

    List<File> imageFileList = new ArrayList<File>();

    ContentsPath path = DIContainer.get("contentsPath");
    String code = "";

    // 商品、ギフトそれぞれの画像の格納フォルダパスの構築
    StringBuilder builder = new StringBuilder();
    builder.append(path.getContentsSharedPath());
    builder.append("/shop/" + info.getShopCode());
    if (info instanceof CommodityImage) {
      builder.append(path.getCommodityPath() + "/");
      CommodityImage commodityImage = (CommodityImage) info;
      code = commodityImage.getCommodityCode();
    } else if (info instanceof SkuImage) {
      builder.append(path.getCommodityPath() + "/");
      SkuImage skuImage = (SkuImage) info;
      code = skuImage.getSkuCode();
    } else if (info instanceof GiftImage) {
      builder.append(path.getGiftPath() + "/");
      GiftImage giftImage = (GiftImage) info;
      code = giftImage.getGiftCode();
    }

    // 画像ファイル名の接尾辞正規表現を構築
    // 例. 商品画像 (|_t|_mt|_m)
    StringBuilder suffixis = new StringBuilder();
    if (info.getImageType() == null) {
      // imageType == null : 商品またはSKUに関連付く画像すべてを一括で取得する場合
      suffixis.append("(");
      for (String suffix : info.getSuffixies()) {
        suffixis.append(suffix + "|");
      }
      suffixis.append(")");
    } else {
      // imageType != null : サムネイルなど単一画像を指定して取得する場合
      ImageType imageType = info.getImageType();
      suffixis.append(imageType.getSuffix());
    }

    // 画像格納フォルダから、ImageInfoで指定した画像種類のファイルのみ取得
    File imageDirectory = new File(builder.toString());
    if (imageDirectory.isDirectory()) {
      for (File file : imageDirectory.listFiles()) {
        if (file.getName().matches(code + suffixis.toString() + ".jpg")) {
          imageFileList.add(file);
        }
      }
    }
    return imageFileList;
  }

  public ServiceResult deleteImage(ImageInfo info) {

    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl result = new ServiceResultImpl();

    List<File> deleteFileList = getImageFileList(info);

    try {
      for (File file : deleteFileList) {
        boolean deleteFailed = false;
        if (file.exists() && file.isFile()) {
          deleteFailed = !file.delete();
        }

        if (deleteFailed) {
          result.addServiceError(DataIOServiceErrorContent.FILE_DELETE_ERROR);
        }
      }
    } catch (SecurityException e) {
      result.addServiceError(DataIOServiceErrorContent.FILE_DELETE_ERROR);
      logger.error(e);
    }
    return result;
  }

  private String getImageFileName(ImageInfo info) {
    String fileName = info.getUploadFileName();
    if (StringUtil.hasValue(fileName)) {
      return fileName;
    } else {
      return getFileName(info.getContentsFile().getPath());
    }
  }

  private String getFileName(String path) {
    String pattrens = "[\\\\ | /]";
    String[] pathSplit = path.split(pattrens);

    return pathSplit[pathSplit.length - 1];
  }

  private String getFileSeparator() {
    return System.getProperty("file.separator");
  }

  // 20111223 os013 add start
  public FastpayAlipayResultBean getFastpayAlipayBean() {
    FastpayAlipayManager paymentManager = new FastpayAlipayManager();
    FastpayAlipayResult result = paymentManager.fastLogin();
    return result.getResultBean();
  }
  // 20111223 os013 add end
}
