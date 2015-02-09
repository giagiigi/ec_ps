package jp.co.sint.webshop.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;

/**
 * @author System Integrator Corp.
 */
public class FTPFileUpload implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private final static int BUFF_SIZE = 10 * 1024;
  private final static String ENCODING = "UTF-8";
  private final static int TIME_OUT = 10 * 10000;
  private static String errMsg;

  /**
   * 通过ftp上传单个文件到指定服务器
   * 
   * input: String orgFilePath    原文件路径 例：d:\\1.jpg
   *        String serviceName    服务器地址
   *        int    port           端口号
   *        String username       用户名
   *        String password       密码
   *        String desFilePath    目的地路径 例：/base/image
   *        String desFileName    目标文件名 例：shangping1.jpg
   *        
   * return int 0：执行失败；1：执行成功
   */ 
  public static int uploadFile(String orgFilePath, String serviceName, int port, int connMode, String username,
      String password, String desFilePath, String desFileName) {
    if (StringUtil.isNullOrEmpty(orgFilePath) || StringUtil.isNullOrEmpty(serviceName) ||
        StringUtil.isNullOrEmpty(desFilePath) || StringUtil.isNullOrEmpty(desFileName))
    {
      setErrMsg("必要参数为空");
      return 0;
    }
    
    //创建FTP连接对象
    FTPClient ftp = new FTPClient();
    ftp.setDataTimeout(TIME_OUT);

    //创建FileInputStream
    FileInputStream fis = null; 
    
    try {
      //连接FTP服务器并登录
      if (port != 0) {
        ftp.connect(serviceName, port);
      } else {
        ftp.connect(serviceName);
      }
      
      ftp.login(username, password);
      
      if (0 == connMode) {
        ftp.enterLocalPassiveMode(); //设置被动链接模式
      } else if (1 == connMode) {
        ftp.enterLocalActiveMode(); //设置主动链接模式
      }
      
      //将图片文件转成输入流
      File srcFile = new File(orgFilePath); 
      fis = new FileInputStream(srcFile);
      
      //设置上传目录 
      // 20130910 txw add start
      ftp.changeWorkingDirectory(desFilePath);
      // 20130910 txw add end
      ftp.setBufferSize(BUFF_SIZE); 
      ftp.setControlEncoding(ENCODING);
      
      //设置文件类型（二进制） 
      ftp.setFileType(FTPClient.BINARY_FILE_TYPE); 
      ftp.storeFile(desFileName,fis);

    } catch (IOException e) {
      e.printStackTrace();
      setErrMsg("服务器连接失败！");
    } finally {
      //关闭IO
      IOUtils.closeQuietly(fis);
      try {
        //关闭FTP
        ftp.disconnect();
      } catch (IOException e) {
        e.printStackTrace();
        setErrMsg("关闭FTP连接发生异常！");
      }
    }
    
    return 1;
  }

  /**
   * @return the errMsg
   */
  public static String getErrMsg() {
    return errMsg;
  }

  /**
   * @param errMsg the errMsg to set
   */
  public static void setErrMsg(String errMsg) {
    FTPFileUpload.errMsg = errMsg;
  }
}
