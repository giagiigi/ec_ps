package jp.co.sint.webshop.data.csv.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.List;

import jp.co.sint.webshop.data.CsvResult;
import jp.co.sint.webshop.data.csv.CommitMode;
import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvData;
import jp.co.sint.webshop.data.csv.CsvDataType; // 10.1.1 10006 追加
import jp.co.sint.webshop.data.csv.CsvExportException;
import jp.co.sint.webshop.data.csv.CsvFilter;
import jp.co.sint.webshop.data.csv.CsvHandler;
import jp.co.sint.webshop.data.csv.CsvImportException;
import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.data.csv.CsvUtil;
import jp.co.sint.webshop.data.csv.ExportDataSource;
import jp.co.sint.webshop.data.csv.ImportDataSource;
import jp.co.sint.webshop.data.csv.TransactionMode;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvImportCondition;
import jp.co.sint.webshop.service.data.csv.NearCommodityCompareWarnDataSource;
import jp.co.sint.webshop.service.data.csv.NearCommodityWarnDataSource;
import jp.co.sint.webshop.service.data.csv.NoPicOrStopSellingDataSource;
import jp.co.sint.webshop.service.data.csv.ZsIfItemErpExportDataSource;
import jp.co.sint.webshop.service.data.csv.ZsIfSOErpExportDataSource;
import jp.co.sint.webshop.service.data.csv.ZsIfSOWmsExportDataSource;
import jp.co.sint.webshop.utility.DateUtil; // 10.1.1 10006 追加
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.IOUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;

import org.apache.log4j.Logger;
import org.supercsv.exception.SuperCSVException;
import org.supercsv.io.ICsvListReader;
import org.supercsv.io.ICsvListWriter;

public class CsvHandlerImpl implements CsvHandler {

  private int elapseScale = 10000;

  /**
   * elapseScaleを返します。
   * 
   * @return the elapseScale
   */
  public int getElapseScale() {
    return elapseScale;
  }

  /**
   * elapseScaleを設定します。
   * 
   * @param elapseScale
   *          設定する elapseScale
   */
  public void setElapseScale(int elapseScale) {
    this.elapseScale = elapseScale;
  }

  public <S extends CsvSchema, E extends CsvExportCondition<S>>CsvResult exportData(Writer out, ExportDataSource<S, E> csvLoader) {
    Logger logger = Logger.getLogger(this.getClass());
    logger.info(Messages.log("data.csv.impl.CsvHandlerImpl.0"));
    ICsvListWriter csvWriter = null;
    CsvData data = null;
    CsvResult result = new CsvResult();
    // 2012-01-06 yyq add start desc:error累计数， csvTitle名字
    int errors = 0;
    CsvSchema schema = csvLoader.getSchema();
    String csvTitleName = schema.getCsvId();
    // 2012-01-06 yyq add end desc:error累计数，csvTitle名字
    try {
      csvWriter = CsvUtil.getCsvWriter(out);
      int rowCount = 0;
      csvLoader.init();
      csvLoader.beforeExport();
      if (csvLoader.headerOnly()) {
        csvWriter.write(getHeader(schema.getColumns()));
        rowCount = 1;
      } else {
        data = csvLoader.getData();
        boolean isFirstLine = true;
        for (List<String> row : data) {
          if (isFirstLine && csvLoader.getCondition().hasHeader()) {
            csvWriter.writeHeader(getHeader(schema.getColumns()));
            isFirstLine = false;
          }
          fixFormat(row, schema.getColumns()); // 10.1.1 10006 追加

          if (csvTitleName.equals("ptmstr")) {
            ZsIfItemErpExportDataSource zsIfItemErpExportDataSource = new ZsIfItemErpExportDataSource();
            zsIfItemErpExportDataSource.subStringSkuName(row, schema.getColumns());
            zsIfItemErpExportDataSource.getCategory(row, schema.getColumns());
          }
          if (csvTitleName.equals("somstr")) {
            if(schema.toString().indexOf("Erp") > -1){
              ZsIfSOErpExportDataSource zsIfSOErpExportDataSource = new ZsIfSOErpExportDataSource();
              zsIfSOErpExportDataSource.subStringAddrName(row, schema.getColumns());
            }else{
              ZsIfSOWmsExportDataSource zsIfSOWmsExportDataSource = new ZsIfSOWmsExportDataSource();
              zsIfSOWmsExportDataSource.compartmentStockDeal(row, schema.getColumns());
              zsIfSOWmsExportDataSource.subStringAddrName(row, schema.getColumns());
            }
          }
          if (csvTitleName.equals("NoPicOrStopSelling")) {
            NoPicOrStopSellingDataSource noPicOrStopSellingDataSource = new NoPicOrStopSellingDataSource();
            noPicOrStopSellingDataSource.getCategoryAndNoPic(row, schema.getColumns());
          }
          // 20140227 txw add start
          if (csvTitleName.equals("NearCommodityWarn")) {
            NearCommodityWarnDataSource nearCommodityWarnDataSource = new NearCommodityWarnDataSource();
            if(!nearCommodityWarnDataSource.isNearCommodity(row, schema.getColumns())) {
              continue;
            }
          }
          if (csvTitleName.equals("NearCommodityCompareWarn")) {
            NearCommodityCompareWarnDataSource nearCommodityCompareWarnDataSource = new NearCommodityCompareWarnDataSource();
            if(!nearCommodityCompareWarnDataSource.isNearCommodity(row, schema.getColumns())) {
              continue;
            }
          }
          // 20140227 txw add end

          csvLoader.onFetchData(row);
          csvWriter.write(row);
          if (++rowCount % getElapseScale() == 0) {
            logger.info(MessageFormat.format(Messages.log("data.csv.impl.CsvHandlerImpl.1"), rowCount));
          }
        }
      }
      csvLoader.afterExport();
      result.setExportCount(rowCount);
      if (rowCount == 0) {
        logger.warn(Messages.log("data.csv.impl.CsvHandlerImpl.2"));
        errors++;
      }
    } catch (IOException e) {
      errors++;
      logger.error(e);
      throw new CsvExportException(e);
    } catch (SuperCSVException e) {
      errors++;
      logger.error(e);
      throw new CsvExportException(e);
    } catch (CsvExportException e) {
      errors++;
      logger.error(e);
      throw (e);
    } catch (Exception e) {
      errors++;
      logger.error(e);
      throw new CsvExportException(e);
    } finally {
      // 2012-01-06 yyq add start desc:判断error累计数，为0时事务提交，大于0时事务回滚（导出接口专用）
      if (errors == 0) {
        if (csvTitleName.equals("somstr") || csvTitleName.equals("cmmstr") || csvTitleName.equals("arpay")
            || csvTitleName.equals("ptmstr") || csvTitleName.equals("gift_card_rule")) {
          csvLoader.commit();
        }
      } else {
        if (csvTitleName.equals("somstr") || csvTitleName.equals("cmmstr") || csvTitleName.equals("arpay")
            || csvTitleName.equals("ptmstr") || csvTitleName.equals("gift_card_rule") ) {
          csvLoader.rollback();
        }
      }
      // 2012-01-06 yyq add end desc:判断error累计数，为0时事务提交，大于0时事务回滚（导出接口专用）
      if (!csvLoader.headerOnly()) {
        data.dispose();
      }
      csvLoader.dispose();
      if (result.getExportCount() > 0) {
        CsvUtil.close(csvWriter);
      }
    }
    logger.info(Messages.log("data.csv.impl.CsvHandlerImpl.3"));
    return result;
  }

  public <S extends CsvSchema, I extends CsvImportCondition<S>>CsvResult importData(Reader in, ImportDataSource<S, I> updater) {
    Logger logger = Logger.getLogger(this.getClass());
    logger.info(Messages.log("data.csv.impl.CsvHandlerImpl.4"));
    ICsvListReader csvInput = null;
    ICsvListWriter csvOut = null;
    ICsvListWriter csvErr = null;
    List<String> csvLine = null;
    CsvFilter filter = updater.getCondition().getFilter();
    PrintWriter csvLog = new PrintWriter(filter.getLogWriter());
    CsvResult result = new CsvResult();
    int maxErrors = updater.getMaxErrorCount();
    int correct = 0;
    int errors = 0;
    boolean bitFlag = false;
    int lineCount = 0;
    try {
      csvInput = CsvUtil.getCsvReader(in);
      csvOut = CsvUtil.getCsvWriter(filter.getOutputWriter());
      csvErr = CsvUtil.getCsvWriter(filter.getErrorWriter());
      updater.init();
      updater.beforeImport();

      if (updater.getCondition().hasHeader()) {
        csvInput.getCSVHeader(true);
      }

      while ((csvLine = csvInput.read()) != null) {
        int lineNo = csvInput.getLineNumber();

        // 10.1.3 10152 追加 ここから
        // SuperCsvが改行コード(CRLF、CR)をLFに変換してしまうので、
        // LFをWebShopping内で使用する改行コードのCRLFに再度変換する。
        for (int i = 0; i < csvLine.size(); i++) {
          String value = csvLine.get(i);
          csvLine.set(i, CsvUtil.convertLineFeedForImport(value));
        }
        // 10.1.3 10152 追加 ここまで

        try {
          lineCount++;
          if (!bitFlag && updater.setSchema(csvLine)) {
            bitFlag = true;
            continue;
          }
          CsvSchema schema =  updater.getSchema();
          String csvTitleName = schema.getCsvId();
      
          // 共通チェック(行サイズと型のチェック)
          validateSizeAndTypes(csvLine, updater.getSchema());
          // 個別チェック(CSV定義固有のチェック)
          ValidationSummary vs = updater.validate(csvLine);
          
          // 如果errorMessages仅包含毛利率信息，则直接update
          boolean isOnlyContainMessageOfGrossProfitRate = true;
          for(int i = 0; i < vs.getErrorMessages().size(); i++) {
            if(!vs.getErrorMessages().get(i).contains("毛利率")) {
              isOnlyContainMessageOfGrossProfitRate = false;
            }
          }
          
          if (vs.isValid() || vs.getErrorMessages().get(0).equals("已同期") || isOnlyContainMessageOfGrossProfitRate) {

            updater.update(csvLine);
            csvOut.write(csvLine);
            correct++;
            if (csvTitleName.equals("soship") ){
              // 设置从文件中读取的该行的错误信息为null，以便log日志输出判断第几行出错的信息
              result.addDetail(false, lineNo, "null");
            }
            if (updater.getCommitMode() == CommitMode.FOR_EACH) {
              updater.commit();
            }
            // 20120319 os013 add start
            //如果SKU商品已经同期，则报提示
            if (!vs.isValid()) {
              //result.addDetail(false, lineNo, "第" + lineNo + "行：SKU已同期，所以安全库存数不更新");
            }
            // 20120319 os013 add end
            
          } else {
            for (String s : vs.getErrorMessages()) {
              logger.warn(getFormattedErrorMessage(lineNo-1, s));
            }
            throw new CsvImportException(vs);
          }
          // 如果错误只包括毛利率错误，则画面也输出错误
          if(isOnlyContainMessageOfGrossProfitRate) {
            for (String s : vs.getErrorMessages()) {
              result.addDetail(false, lineNo, "第" + lineNo + "行，毛利率警告： " + s);
            }
          }
        } catch (CsvImportException ex) {
          errors++;
          //ex.printStackTrace();
          if (updater.getCommitMode() == CommitMode.FOR_EACH) {
            updater.rollback();
          }
          writeError(csvLog, lineNo, ex);

          if (ex.getSummary() == null) {
            result.addDetail(true, lineNo, Messages.getString("data.csv.impl.CsvHandlerImpl.5"));
          } else {
            result.addDetail(true, lineNo, ex.getSummary().getErrorMessages());
          }

          csvErr.write(csvLine);
          continue;
        }
        if (errors >= maxErrors) {
          result.setAborted(true);
          break;
        }
      }
      if (lineCount == 0) {
        result.addDetail(true, 1, Messages.getString("data.csv.impl.CsvHandlerImpl.13"));
        errors++;
      }
      TransactionMode transactionMode = updater.afterImport();
      if (updater.getCommitMode() == CommitMode.BULK_COMMIT) {
        switch (transactionMode) {
          case COMMIT_FORCE:
            logger.info(Messages.log("data.csv.impl.CsvHandlerImpl.6"));
            updater.commit();
            break;
          case ROLLBACK_FORCE:
            logger.info(Messages.log("data.csv.impl.CsvHandlerImpl.7"));
            updater.rollback();
            break;
          case DEPEND_ON_RESULT:
            if (errors == 0) {
              logger.info(Messages.log("data.csv.impl.CsvHandlerImpl.8"));
              updater.commit();
            } else {
              logger.info(Messages.log("data.csv.impl.CsvHandlerImpl.9"));
              updater.rollback();
            }
            break;
          default:
            throw new CsvImportException();
        }
      }
      if (StringUtil.hasValue(updater.getMessage())) {
        result.setMessage(updater.getMessage());
      }
      // result.setCorrectCount(correct);
      // result.setErrorCount(errors);
      // if (updater.getCommitMode() == CommitMode.BULK_COMMIT && errors > 0) {
      // result.setCorrectCount(0);
      // }
    } catch (IOException e) {
      logger.error(e);
      updater.rollback();
      errors++;
      result.setAborted(true);
    } catch (SuperCSVException e) {
      logger.error(e);
      updater.rollback();
      errors++;
      result.setAborted(true);
    } catch (RuntimeException e) {
      logger.error(e);
      updater.rollback();
      errors++;
      result.setAborted(true);
    } finally {
      CsvUtil.close(csvInput);
      CsvUtil.close(csvOut);
      CsvUtil.close(csvErr);
      IOUtil.close(csvLog);
      if (updater.getCommitMode() == CommitMode.BULK_COMMIT && errors > 0) {
        result.setCorrectCount(0);
      }
      result.setCorrectCount(correct);
      result.setErrorCount(errors);
      updater.dispose();
    }
    logger.info(Messages.log("data.csv.impl.CsvHandlerImpl.10"));
    return result;
  }

  // 2012-01-09 yyq add start desc:接口使用的csv导入共通
  public <S extends CsvSchema, I extends CsvImportCondition<S>>CsvResult importDataIf(Reader in, ImportDataSource<S, I> updater) {
    Logger logger = Logger.getLogger(this.getClass());
    logger.info(Messages.log("data.csv.impl.CsvHandlerImpl.4"));
    ICsvListReader csvInput = null;
    ICsvListWriter csvOut = null;
    ICsvListWriter csvErr = null;
    List<String> csvLine = null;
    CsvFilter filter = updater.getCondition().getFilter();
    PrintWriter csvLog = new PrintWriter(filter.getLogWriter());
    CsvResult result = new CsvResult();
    int maxErrors = updater.getMaxErrorCount();
    int correct = 0;
    int errors = 0;
    try {

      csvInput = CsvUtil.getCsvReader(in);
      csvOut = CsvUtil.getCsvWriter(filter.getOutputWriter());
      csvErr = CsvUtil.getCsvWriter(filter.getErrorWriter());
      updater.init();
      boolean headerFlag = true;
      int lineCount = 0;

      if (updater.getCondition().hasHeader()) {
        csvInput.getCSVHeader(true);
      }

      while ((csvLine = csvInput.read()) != null) {
        int lineNo = csvInput.getLineNumber();

        for (int i = 0; i < csvLine.size(); i++) {
          String value = csvLine.get(i);
          csvLine.set(i, CsvUtil.convertLineFeedForImport(value));
        }

        try {
          lineCount++;
          // 共通チェック(行サイズと型のチェック)
          validateSizeAndTypes(csvLine, updater.getSchema());
          // 個別チェック(CSV定義固有のチェック)
          ValidationSummary vs = updater.validate(csvLine);
          if (vs.isValid()) {
            updater.update(csvLine);
            csvOut.write(csvLine);
            correct++;
            // 设置从文件中读取的该行的错误信息为null，以便log日志输出判断第几行出错的信息
            result.addDetail(false, lineNo, "null");
          } else {
            for (String s : vs.getErrorMessages()) {
              logger.warn(getFormattedErrorMessage(lineNo, s));
            }
            throw new CsvImportException(vs);
          }
        } catch (CsvImportException ex) {
          errors++;
          writeError(csvLog, lineNo, ex);

          if (ex.getSummary() == null) {
            result.addDetail(true, lineNo, Messages.getString("data.csv.impl.CsvHandlerImpl.5"));
          } else {
            result.addDetail(true, lineNo, ex.getSummary().getErrorMessages());
          }

          csvErr.write(csvLine);
          continue;
        }
        if (errors >= maxErrors) {
          result.setAborted(true);
          break;
        }
      }
      if (lineCount == 0 && headerFlag) {
        result.addDetail(true, 1, Messages.getString("data.csv.impl.CsvHandlerImpl.13"));
        errors++;
      }
      if (StringUtil.hasValue(updater.getMessage())) {
        result.setMessage(updater.getMessage());
      }
    } catch (IOException e) {
      logger.error(e);
      errors++;
      result.setAborted(true);
      result.addDetail(true, errors, Messages.getString("data.csv.impl.CsvHandlerImpl.14"));
    } catch (SuperCSVException e) {
      logger.error(e);
      errors++;
      result.setAborted(true);
      result.addDetail(true, errors, e.toString());
    } catch (RuntimeException e) {
      logger.error(e);
      errors++;
      result.setAborted(true);
      result.addDetail(true, errors, e.toString());
    } finally {
      CsvUtil.close(csvInput);
      CsvUtil.close(csvOut);
      CsvUtil.close(csvErr);
      IOUtil.close(csvLog);
      result.setCorrectCount(correct);
      result.setErrorCount(errors);
      if (errors > 0) {
        //result.setCorrectCount(0);
        updater.rollback();
      }
      if (errors == 0) {
        updater.commit();
      }
    }
    logger.info(Messages.log("data.csv.impl.CsvHandlerImpl.10"));

    return result;
  }

  private static String[] getHeader(List<CsvColumn> columns) {
    String[] header = new String[columns.size()];
    for (int i = 0; i < columns.size(); i++) {
      header[i] = StringUtil.coalesce(columns.get(i).getLogicalName(), "");
    }
    return header;
  }

  private void writeError(PrintWriter out, int lineNo, CsvImportException ex) {
    if (ex.getSummary() != null) {
      for (String msg : ex.getSummary().getErrorMessages()) {
        out.println(getFormattedErrorMessage(lineNo, msg));
      }
    } else {
      out.println(getFormattedErrorMessage(lineNo, ex.getMessage()));
    }
    out.flush();
  }

  private String getFormattedErrorMessage(int lineNo, String message) {
    return MessageFormat.format(Messages.getString("data.csv.impl.CsvHandlerImpl.11"), lineNo, message);
  }

  private void validateSizeAndTypes(List<String> csvLine, CsvSchema schema) {
    ValidationSummary vs = new ValidationSummary();
    if (csvLine.size() != schema.getColumns().size()) {
      vs.getErrors().add(new ValidationResult(null, null, Messages.getString("data.csv.impl.CsvHandlerImpl.12")));
      throw new CsvImportException(vs);
    }
    for (int i = 0; i < schema.getColumns().size(); i++) {
      CsvColumn column = schema.getColumns().get(i);
      ValidationResult vr = column.getDataType().validate(csvLine.get(i));
      if (!vr.isCorrect()) {
        vr.setName(column.getLogicalName());
        vs.getErrors().add(vr);
      }
    }
    if (vs.hasError()) {
      throw new CsvImportException(vs);
    }

  }

  // 10.1.1 10006 追加 ここから
  /**
   * 受け取ったCSVデータに含まれる最小日時/最大日時を空に変換します。<BR>
   * CsvDataTypeがDATE型かDATETIME型のデータのみを変換します。
   * 
   * @param row
   *          CSV一行分のデータ
   * @param columnList
   *          CsvColumnのリスト
   */
  private void fixFormat(List<String> row, List<CsvColumn> columnList) {
    for (int i = 0; i < row.size(); i++) {
      CsvDataType dataType = columnList.get(i).getDataType();
      if ((dataType.equals(CsvDataType.DATE) || dataType.equals(CsvDataType.DATETIME)) && !StringUtil.isNullOrEmpty(row.get(i))) {
        String columnData = row.get(i);
        String min = DateUtil.toDateTimeString(DateUtil.getMin(), DateUtil.DEFAULT_DATETIME_FORMAT);
        String max = DateUtil.toDateTimeString(DateUtil.getMax(), DateUtil.DEFAULT_DATETIME_FORMAT);
        String target = min + "|" + max;
        if (columnData.matches(target)) {
          row.set(i, "");
        }
      }
    }
  }

  // 10.1.1 10006 追加 ここまで
 //2012-02-29 OS011 add start desc:接口使用的csv导入共通有重复SKU时使用
  public <S extends CsvSchema, I extends CsvImportCondition<S>>CsvResult importDataIfSkuExist(Reader in, ImportDataSource<S, I> updater) {
    Logger logger = Logger.getLogger(this.getClass());
    logger.info(Messages.log("data.csv.impl.CsvHandlerImpl.4"));
    ICsvListReader csvInput = null;
    ICsvListWriter csvOut = null;
    ICsvListWriter csvErr = null;
    List<String> csvLine = null;
    CsvFilter filter = updater.getCondition().getFilter();
    PrintWriter csvLog = new PrintWriter(filter.getLogWriter());
    CsvResult result = new CsvResult();
    int maxErrors = updater.getMaxErrorCount();
    int correct = 0;
    int errors = 0;
    int checkSku = 0;
    try {

      csvInput = CsvUtil.getCsvReader(in);
      csvOut = CsvUtil.getCsvWriter(filter.getOutputWriter());
      csvErr = CsvUtil.getCsvWriter(filter.getErrorWriter());
      updater.init();
      boolean headerFlag = true;
      int lineCount = 0;

      if (updater.getCondition().hasHeader()) {
        csvInput.getCSVHeader(true);
      }
      
      //sku重复处理
      while ((csvLine = csvInput.read()) != null) {
        int lineNo = csvInput.getLineNumber();

        for (int i = 0; i < csvLine.size(); i++) {
          String value = csvLine.get(i);
          csvLine.set(i, CsvUtil.convertLineFeedForImport(value));
        }
        
        try {
          lineCount++;
          // 共通チェック(行サイズと型のチェック)
          validateSizeAndTypes(csvLine, updater.getSchema());
          // 個別チェック(CSV定義固有のチェック)
          ValidationSummary vs = updater.validate(csvLine);
          if(vs.isValid()){
            csvOut.write(csvLine);
            // 设置从文件中读取的该行的错误信息为null，以便log日志输出判断第几行出错的信息
            result.addDetail(false, lineNo, "null");
            correct++;
          }else{
            checkSku++;
            for (String s : vs.getErrorMessages()) {
              logger.warn(getFormattedErrorMessage(lineNo, s));
            }
            throw new CsvImportException(vs);
          }

        }catch (CsvImportException ex) {
          checkSku++;
          errors++;
          writeError(csvLog, lineNo, ex);

          if (ex.getSummary() == null) {
            result.addDetail(true, lineNo, Messages.getString("data.csv.impl.CsvHandlerImpl.5"));
          } else {
            result.addDetail(true, lineNo, ex.getSummary().getErrorMessages());
          }

          csvErr.write(csvLine);
          continue;
        }
        if (errors >= maxErrors) {
          result.setAborted(true);
          break;
        }
      }
      //检查通过
      if(checkSku==0&&lineCount>0){
          updater.update(csvLine);
      }

      if (lineCount == 0 && headerFlag) {
        result.addDetail(true, 1, Messages.getString("data.csv.impl.CsvHandlerImpl.13"));
        errors++;
      }
      if (StringUtil.hasValue(updater.getMessage())) {
        result.setMessage(updater.getMessage());
      }
    } catch (IOException e) {
      logger.error(e);
      errors++;
      result.setAborted(true);
      result.addDetail(true, errors, Messages.getString("data.csv.impl.CsvHandlerImpl.14"));
    } catch (SuperCSVException e) {
      logger.error(e);
      errors++;
      result.setAborted(true);
      result.addDetail(true, errors, e.toString());
    } catch (RuntimeException e) {
      logger.error(e);
      errors++;
      result.setAborted(true);
      result.addDetail(true, errors, e.toString());
    } finally {
      CsvUtil.close(csvInput);
      CsvUtil.close(csvOut);
      CsvUtil.close(csvErr);
      IOUtil.close(csvLog);
      result.setCorrectCount(correct);
      result.setErrorCount(errors);
      if (errors > 0) {
        //result.setCorrectCount(0);
        updater.rollback();
      }
      if (errors == 0) {
        updater.commit();
      }
    }
    logger.info(Messages.log("data.csv.impl.CsvHandlerImpl.10"));

    return result;
  }
//2012-02-29 OS011 add start desc:接口使用的csv导入共通有重复SKU时使用
}
