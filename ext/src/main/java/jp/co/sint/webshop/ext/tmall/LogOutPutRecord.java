package jp.co.sint.webshop.ext.tmall;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 记录log日志文件的工具类
 */

public class LogOutPutRecord {

  private PrintWriter logPrint;

  private String logFile = "";

  private String path = "";

  private String logName = "Api_";

  private final String MESSAGE01 = "商品下架API:";

  private final String MESSAGE02 = "商品图片上传API:";

  private final String MESSAGE03 = "商品SKU删除API:";

  private final String MESSAGE04 = "产品ID获取API:";

  private final String MESSAGE05 = "SKU获取API:";

  private final String MESSAGE06 = "获取淘宝系统时间API:";

  private final String MESSAGE07 = "商品Header新增API:";

  private final String MESSAGE08 = "商品Header更新API:";

  private final String MESSAGE09 = "商品SKU新增API:";

  private final String MESSAGE10 = "商品SKU更新API:";

  private final String MESSAGE11 = "产品上传API:";

  private final String MESSAGE12 = "查询单个商品信息API:";
  
  private final String MESSAGE13 = "查询单笔退货明细API:";
  
  private final String MESSAGE14 = "Alipay批量发货通知API:";
  
  private final String MESSAGE15 = "更新收货信息API:";
  
  private final String MESSAGE16 = "Cod批量发货通知API:";
  
  private final String MESSAGE17 = "商品库存更新API:";
  
  private final String MESSAGE18 = "查询单笔订单交易状态API:";
  


  SimpleDateFormat slf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

  /**
   * 配置没有头的log
   */
  public LogOutPutRecord() {
    checkDate();
  }

  /**
   * 配置log文件名的头。
   * 
   * @param logName
   *          log日志的名称
   */
  public LogOutPutRecord(String logName) {
    this.logName = logName;
    checkDate();
  }

  public LogOutPutRecord(String pathStr, String temp) {
    path = pathStr;
    checkDate();
  }

  /**
   * 得到log文件名
   */
  private String getLogFile() {
    String date = "";
    Calendar cd = Calendar.getInstance();
    int y = cd.get(Calendar.YEAR);
    int m = cd.get(Calendar.MONTH) + 1;
    int d = cd.get(Calendar.DAY_OF_MONTH);
    date = path + logName + y;
    if (m < 10)
      date += 0;
    date += m;
    if (d < 10)
      date += 0;
    date += d + ".log";
    return date;
  }

  /**
   * 配置log属性，如果没有新建log文件
   */
  private void newLog() {
    logFile = getLogFile();
    try {
      logPrint = new PrintWriter(new FileWriter(logFile, true), true);
    } catch (IOException e) {
      (new File("./log")).mkdir();
      try {
        logPrint = new PrintWriter(new FileWriter(logFile, true), true);
      } catch (IOException ex) {
        System.err.println("无法打开日志文件：" + logFile);
        logPrint = new PrintWriter(System.err);
      }
    }
  }

  /**
   * 检查日期如果当天日志不存在改变新建log文件
   */
  private void checkDate() {
    if (logFile == null || logFile.trim().equals("") || !logFile.equals(getLogFile())) {
      if (path != null && !path.equals("")) {
        newLog();
      }
    }
  }

  /**
   * 将各个接口的操作文本信息写入日志文件，缺省为userdir/log。
   * 
   * @param msg
   *          文本信息
   * @param num
   *          接口区分编号
   */
  public void log(String msg, int num) {
    checkDate();
    switch (num) {
      case 1:
        logPrint.println(slf.format(new Date()) + ", " + MESSAGE01 + msg);
        break;
      case 2:
        logPrint.println(slf.format(new Date()) + ", " + MESSAGE02 + msg);
        break;
      case 3:
        logPrint.println(slf.format(new Date()) + ", " + MESSAGE03 + msg);
        break;
      case 4:
        logPrint.println(slf.format(new Date()) + ", " + MESSAGE04 + msg);
        break;
      case 5:
        logPrint.println(slf.format(new Date()) + ", " + MESSAGE05 + msg);
        break;
      case 6:
        logPrint.println(slf.format(new Date()) + ", " + MESSAGE06 + msg);
        break;
      case 7:
        logPrint.println(slf.format(new Date()) + ", " + MESSAGE07 + msg);
        break;
      case 8:
        logPrint.println(slf.format(new Date()) + ", " + MESSAGE08 + msg);
        break;
      case 9:
        logPrint.println(slf.format(new Date()) + ", " + MESSAGE09 + msg);
        break;
      case 10:
        logPrint.println(slf.format(new Date()) + ", " + MESSAGE10 + msg);
        break;
      case 11:
        logPrint.println(slf.format(new Date()) + ", " + MESSAGE11 + msg);
        break;
      case 12:
        logPrint.println(slf.format(new Date()) + ", " + MESSAGE12 + msg);
        break;
      case 13:
        logPrint.println(slf.format(new Date()) + ", " + MESSAGE13 + msg);
        break;
      case 14:
        logPrint.println(slf.format(new Date()) + ", " + MESSAGE14 + msg);
        break;
      case 15:
        logPrint.println(slf.format(new Date()) + ", " + MESSAGE15 + msg);
        break;
      case 16:
        logPrint.println(slf.format(new Date()) + ", " + MESSAGE16 + msg);
        break;
      case 17:
        logPrint.println(slf.format(new Date()) + ", " + MESSAGE17 + msg);
        break;
      case 18:
        logPrint.println(slf.format(new Date()) + ", " + MESSAGE18 + msg);
        break;
    }
  }

  /**
   * 每个接口执行完成后，打印分隔横线。
   * 
   * @param msg
   *          文本信息
   */
  public void log(String msg) {
    checkDate();
    logPrint.println(msg);
  }

  /**
   * 将文本信息与异常写入日志文件
   */
  public void log(Throwable e, String msg, int num) {
    checkDate();
    switch (num) {
      case 1:
        logPrint.println(slf.format(new Date()) + ": " + MESSAGE01 + msg);
        e.printStackTrace(logPrint);
        break;
      case 2:
        logPrint.println(slf.format(new Date()) + ": " + MESSAGE02 + msg);
        e.printStackTrace(logPrint);
        break;
      case 3:
        logPrint.println(slf.format(new Date()) + ": " + MESSAGE03 + msg);
        e.printStackTrace(logPrint);
        break;
      case 4:
        logPrint.println(slf.format(new Date()) + ": " + MESSAGE04 + msg);
        e.printStackTrace(logPrint);
        break;
      case 5:
        logPrint.println(slf.format(new Date()) + ": " + MESSAGE05 + msg);
        e.printStackTrace(logPrint);
        break;
      case 6:
        logPrint.println(slf.format(new Date()) + ": " + MESSAGE06 + msg);
        e.printStackTrace(logPrint);
        break;
      case 7:
        logPrint.println(slf.format(new Date()) + ": " + MESSAGE07 + msg);
        e.printStackTrace(logPrint);
        break;
      case 8:
        logPrint.println(slf.format(new Date()) + ", " + MESSAGE08 + msg);
        e.printStackTrace(logPrint);
        break;
      case 9:
        logPrint.println(slf.format(new Date()) + ", " + MESSAGE09 + msg);
        e.printStackTrace(logPrint);
        break;
      case 10:
        logPrint.println(slf.format(new Date()) + ", " + MESSAGE10 + msg);
        e.printStackTrace(logPrint);
        break;
      case 11:
        logPrint.println(slf.format(new Date()) + ", " + MESSAGE11 + msg);
        e.printStackTrace(logPrint);
        break;
      case 12:
        logPrint.println(slf.format(new Date()) + ", " + MESSAGE12 + msg);
        e.printStackTrace(logPrint);
        break;
      case 13:
        logPrint.println(slf.format(new Date()) + ", " + MESSAGE13 + msg);
        e.printStackTrace(logPrint);
        break;
      case 14:
        logPrint.println(slf.format(new Date()) + ", " + MESSAGE14 + msg);
        e.printStackTrace(logPrint);
        break;
      case 15:
        logPrint.println(slf.format(new Date()) + ", " + MESSAGE15 + msg);
        e.printStackTrace(logPrint);
        break;
      case 16:
        logPrint.println(slf.format(new Date()) + ", " + MESSAGE16 + msg);
        e.printStackTrace(logPrint);
        break;
      case 17:
        logPrint.println(slf.format(new Date()) + ", " + MESSAGE17 + msg);
        e.printStackTrace(logPrint);
        break;
      case 18:
        logPrint.println(slf.format(new Date()) + ", " + MESSAGE18 + msg);
        e.printStackTrace(logPrint);
        break;
    }
  }

  public String printHorizontalLine() {
    StringBuffer sb = new StringBuffer("-");
    for (int t = 0; t < 180; t++) {
      sb.append("-");
    }
    return sb.toString();
  }
}
