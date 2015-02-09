package jp.co.sint.webshop.ext.jd;

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

  private final String MESSAGE01 = "获取订单API:";
  
  private final String MESSAGE02 = "京东订单下载API:";
  
  private final String MESSAGE03 = "京东批量发货通知API:";

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
