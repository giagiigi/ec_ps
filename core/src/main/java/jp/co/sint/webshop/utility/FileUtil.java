package jp.co.sint.webshop.utility;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

import com.mortennobel.imagescaling.ResampleOp;

/**
 * @author System Integrator Corp.
 */
public class FileUtil implements Serializable {

  private static final long serialVersionUID = 1L;

  private final static int BUFF_SIZE = 10 * 1024;

  private static List<String> errMsgList = new ArrayList<String>();

  /**
   * @return the errMsg
   */
  public static List<String> getErrMsgList() {
    return errMsgList;
  }

  /**
   * 查找指定文件夹下是否存在某类文件 input: String path 待查找的文件夹路径 String fileType 文件类型 output:
   * List<File> 查找到的文件列表. 如果为null则只判断是否存在此类文件,而不返回查找到的文件列表 return: 0:没有此类文件;
   * 1:有此类文件;2:参数错误
   */
  public static int findFilesByFileType(String path, String fileType, List<File> fileList) {

    // 判断传进来的文件夹与要查找的文件类型是否为空
    if (!StringUtil.hasValueAllOf(path, fileType)) {
      return 0;
    }
    // 如果文件夹是空的,直接返回
    if (isDirectoryEmpty(path)) {
      return 0;
    }

    // 如果图片列表不为空,清空它
    if (fileList != null && fileList.size() > 0) {
      fileList.clear();
    }

    File file = new File(path);

    // 查找文件夹下所有以fileType为后缀的文件
    int nCount = 0;
    for (File item : file.listFiles()) {
      if (item.getName().toLowerCase().endsWith(fileType)) {
        nCount++;
        if (fileList != null) {
          fileList.add(item);
        }
      }
    }

    if (nCount > 0) {
      return 1;
    } else {
      return 0;
    }
  }

  /**
   * 查找指定文件夹下是否存在某类文件 input: String path 待查找的文件夹路径 String fileType 文件类型 output:
   * List<File> 查找到的文件列表. 如果为null则只判断是否存在此类文件,而不返回查找到的文件列表 return: 0:没有此类文件;
   * 1:有此类文件;2:参数错误
   */
  public static int findFilesByFileName(String path, String fileName, List<File> fileList) {

    // 判断传进来的文件夹与要查找的文件类型是否为空
    if (!StringUtil.hasValueAllOf(path, fileName)) {
      return 0;
    }
    // 如果文件夹是空的,直接返回
    if (isDirectoryEmpty(path)) {
      return 0;
    }

    // 如果图片列表不为空,清空它
    if (fileList != null && fileList.size() > 0) {
      fileList.clear();
    }

    File file = new File(path);

    // 查找文件夹下所有以fileType为后缀的文件
    int nCount = 0;
    for (File item : file.listFiles()) {
      if (item.getName().indexOf(fileName) != -1) {
        nCount++;
        if (fileList != null) {
          fileList.add(item);
        }
      }
    }

    if (nCount > 0) {
      return 1;
    } else {
      return 0;
    }
  }

  public static boolean isDirectoryExist(String path) {
    if (StringUtil.isNullOrEmpty(path)) {
      return false;
    }

    File Dir = new File(path);

    if (!Dir.exists()) {
      return Dir.mkdir();
    }

    if (!Dir.isDirectory()) {
      return false;
    }

    return true;
  }

  public static boolean isDirectoryEmpty(String path) {
    if (StringUtil.isNullOrEmpty(path)) {
      return false;
    }

    boolean bReturn = true;
    if (isDirectoryExist(path)) {
      String[] fileList = new File(path).list();
      if (fileList.length > 0) {
        bReturn = false;
      }
    }

    return bReturn;
  }

  /**
   * 检查文件是否已经在文件夹中存在
   * 
   * @param fileName
   *          文件名
   * @param desPath
   *          文件夹
   * @return 0:不存在;1:已存在;2:参数错误
   */
  public static int isFileExist(String fileName, String desPath) {
    // 数据过滤
    if (StringUtil.isNullOrEmpty(fileName) || StringUtil.isNullOrEmpty(desPath)) {
      return 2;
    }
    String[] desFileNameList = new File(desPath).list();

    // 如果目标文件夹是空,直接返回
    if (null == desFileNameList || desFileNameList.length < 1) {
      return 0;
    }

    // 遍历文件名列表,查找文件名是否重复
    int nReturn = 0;
    for (String desFileName : desFileNameList) {
      if (desFileName.equals(fileName)) {
        nReturn = 1;
        break;
      }
    }

    return nReturn;
  }

  /**
   * 将原文件copy到目标文件夹下
   * 
   * @param orgFile
   * @param desFile
   * @return 0:失败; 1:成功; 2:参数错误
   */
  public static int copyFile(String orgFile, String desFile) {
    if (StringUtil.isNullOrEmpty(orgFile) || StringUtil.isNullOrEmpty(desFile)) {
      return 0;
    }

    int nReturn = 0;
    // 开始copy文件
    try {
      // 在目标文件夹下创建文件
      File newFile = new File(desFile);
      if (!newFile.exists() || newFile.isDirectory()) {
        newFile.createNewFile();
      }

      FileInputStream input = new FileInputStream(orgFile);
      byte[] buff = new byte[BUFF_SIZE];

      int buffLength = -1;

      FileOutputStream output = new FileOutputStream(desFile);
      while ((buffLength = input.read(buff)) != -1) {
        output.write(buff, 0, buffLength);
      }
      output.flush();
      output.close();
      input.close();

      nReturn = 1;
    } catch (Exception e) {
      nReturn = 0;
    }

    return nReturn;
  }
  
  public static boolean moveEcToEc(String source, String destination) {
    boolean flag = false;
    File sourceFile = new File(source);
    File desFile = new File(destination);
    
    FileInputStream fileInput = null;
    FileOutputStream fileOutput = null;
    try {
      fileInput = new FileInputStream(sourceFile);
      fileOutput = new FileOutputStream(desFile);
      byte[] buffer = new byte[DIContainer.getWebshopConfig().getDataIOBufferSize()];
      int i = 0;
      while ((i = fileInput.read(buffer)) != -1) {
        fileOutput.write(buffer, 0, i);
      }
      IOUtil.close(fileInput);
      IOUtil.close(fileOutput);
      flag = true;
    } catch (IOException e) {
      e.printStackTrace();
      flag = false;
    } finally {
      IOUtil.close(fileInput);
      IOUtil.close(fileOutput);
    }
    return flag;
  }
  
  /**
   * 将JPG文件按指定宽高,重新设置JPG文件的尺寸
   * 
   * @param filePath
   *          图片存放路径
   * @param width
   *          图片宽
   * @param height
   *          图片高
   * @return 0:失败; 1:成功; 2:参数错误
   */
  public static int resetJPGFilesSize(String orgFilePath, String desFilePath, int width, int height) {
    if (StringUtil.isNullOrEmpty(orgFilePath) || StringUtil.isNullOrEmpty(desFilePath) || width < 0 || height < 0) {
      return 2;
    }

    File inputFile = new File(orgFilePath);
    File outFile = new File(desFilePath);
    String outPath = outFile.getAbsolutePath();

    byte[] byteArrayImage;
    try {
      InputStream input = new FileInputStream(inputFile);
      byteArrayImage = readBytesFromIS(input);
      input.read(byteArrayImage);
      resizeImage(byteArrayImage, outPath, width, height, "jpg");
    } catch (Exception e) {
      errMsgList.add("无法修改尺寸");
      return 0;
    }

    // File orgImg = new File(orgFilePath);
    //
    // try {
    // Image orgImgSrc = ImageIO.read(orgImg);
    //
    // BufferedImage imgBuff = new BufferedImage(width, height,
    // BufferedImage.TYPE_INT_RGB);
    // imgBuff.getGraphics().drawImage(orgImgSrc, 0, 0, width, height, null);
    //
    // FileOutputStream output = new FileOutputStream(desFilePath);
    // JPEGImageEncoder jpgEncoder = JPEGCodec.createJPEGEncoder(output);
    //
    // jpgEncoder.encode(imgBuff);
    //
    // output.flush();
    // output.close();
    // } catch (Exception e) {
    // errMsgList.add("无法修改尺寸");
    // return 0;
    // }

    return 1;
  }

  public static boolean resizeImage(InputStream input, String writePath, Integer width, Integer height, String format) {
    try {
      BufferedImage inputBufImage = ImageIO.read(input);
      ResampleOp resampleOp = new ResampleOp(width, height);// 转换
      BufferedImage rescaledTomato = resampleOp.filter(inputBufImage, null);
      ImageIO.write(rescaledTomato, format, new File(writePath));
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean resizeImage(File file, String writePath, Integer width, Integer height, String format) {
    try {
      BufferedImage inputBufImage = ImageIO.read(file);
      inputBufImage.getType();
      ResampleOp resampleOp = new ResampleOp(width, height);// 转换
      BufferedImage rescaledTomato = resampleOp.filter(inputBufImage, null);
      ImageIO.write(rescaledTomato, format, new File(writePath));
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  public static boolean resizeImage(byte[] RGBS, String writePath, Integer width, Integer height, String format) {
    InputStream input = new ByteArrayInputStream(RGBS);
    return resizeImage(input, writePath, width, height, format);
  }

  public static byte[] readBytesFromIS(InputStream is) throws IOException {
    int total = is.available();
    byte[] bs = new byte[total];
    is.read(bs);
    return bs;
  }

  public static String getErrMsg() {
    if (errMsgList.size() > 0) {
      return errMsgList.get(0);
    }

    return null;
  }
}
