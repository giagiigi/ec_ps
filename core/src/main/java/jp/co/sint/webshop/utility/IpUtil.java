package jp.co.sint.webshop.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import jp.co.sint.webshop.service.data.ContentsPath;

/**
 * @author kousen
 */
public class IpUtil implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private RandomAccessFile rIpFile;

  private long indexBegin;

  private long indexEnd;

  private long indexHowMany;

  private static byte REDIRECT_MODE_1 = 0x01;

  private static byte REDIRECT_MODE_2 = 0x02;

  public IpUtil() {
    ContentsPath path = DIContainer.get("contentsPath");
    File ipFileQQWry = new File(path.getIpPath());
    try {
      rIpFile = new RandomAccessFile(ipFileQQWry, "rw");
      indexBegin = readLong4(0);
      indexEnd = readLong4(4);
    } catch (FileNotFoundException e) {
      System.out.println("IpUtil:QQWry.dat not found");
    }
    indexHowMany = (indexEnd - indexBegin) / 7 - 1;
  }

  public static String getCountryString(String ipStr) {
    String nationName = "";
    try {
      if (StringUtil.hasValue(ipStr)) {
        String args[] = ipStr.split("\\.");
        if (args.length == 4) {
          int b0 = (int) Integer.parseInt(args[0]);
          int b1 = (int) Integer.parseInt(args[1]);
          int b2 = (int) Integer.parseInt(args[2]);
          int b3 = (int) Integer.parseInt(args[3]);
          int[] ipSchool = new int[] {
              b0, b1, b2, b3
          };
          IpAddress ipAddress = new IpAddress(ipSchool);
          IpUtil ipSearcher = new IpUtil();
          long ipIndex = ipSearcher.binSearchIndex(ipAddress);
          nationName = ipSearcher.getIpFieldFromIndex(ipIndex);
        }
      }
    } catch(Exception e) {
      nationName = "";
    }
    return nationName;
  }

  public String getIpFieldFromIndex(long indexLocation) {
    long endIpAddressFilePoint = readLong3(indexLocation * 7 + indexBegin + 4);
    String countryString = "";
    byte markByte = readByte(endIpAddressFilePoint + 4);
    if (markByte == REDIRECT_MODE_1) {
      // 读取国家偏移
      long countryOffset = readLong3(endIpAddressFilePoint + 5);
      // 跳转至偏移处,再检查一次标志字节，
      // 因为这个时候这个地方仍然可能是个重定向
      markByte = readByte(countryOffset);
      if (markByte == REDIRECT_MODE_2) {
        // 考虑第四种或者是第五种的情况：
        // 第 4-5 种情况
        long countryOffset2 = readLong3(countryOffset + 1);
        countryString = readString(countryOffset2);
      } else {
        // 第 2 种情况
        countryString = readString(countryOffset);
      }
    } else if (markByte == REDIRECT_MODE_2) {
      // 第 3 种情况
      long tempLong = readLong3(endIpAddressFilePoint + 5);
      countryString = readString(tempLong);
    } else {
      // 第 5 种情况
      countryString = readString(endIpAddressFilePoint + 4);
    }
    return countryString;
  }

  /**
   * @param ipAddress
   * @return
   */
  public long binSearchIndex(IpAddress ipAddress) {
    long low, high, mid;
    low = 0L;
    high = indexHowMany - 1;
    while (low <= high) {
      mid = (low + high) / 2;
      if (ipAddress.compareIp(this.getIpAddressFromIndex(mid)) == 0) {
        return mid;
      }
      if (ipAddress.compareIp(this.getIpAddressFromIndex(mid)) == 1) {
        low = mid + 1;
      } else {
        high = mid - 1;
      }
    }
    return high;
  }

  public IpAddress getIpAddressFromIndex(long indexLocation) {
    return getIpAddressFromFile(indexLocation * 7 + indexBegin);
  }

  /**
   * Describe <code>getIpAddressFromFile</code> 从IP在文件中的位置得到这个IP地址
   * 
   * @param FileLongPoint
   *          a <code>long</code> 一个IP的文件中的位置
   * @return an <code>IpAddress</code> 返回的IP地址
   */
  public IpAddress getIpAddressFromFile(long FileLongPoint) {
    byte byteIp[] = new byte[] {
        0, 0, 0, 0
    };
    int ip[] = new int[] {
        0, 0, 0, 0
    };

    try {
      rIpFile.seek(FileLongPoint);
      rIpFile.readFully(byteIp);
      ip[0] = byteIp[3];
      ip[1] = byteIp[2];
      ip[2] = byteIp[1];
      ip[3] = byteIp[0];

      for (int i = 0; i < 4; i++) {
        ip[i] = ip[i] < 0 ? (ip[i] + 256) : ip[i];
      }
    } catch (IOException e) {
      System.out.println("getIpAddressFromFile(long FileLongPoint)");
    }
    return new IpAddress(ip);
  }

  /**
   * Describe <code>readByte</code> 从文件中读取一个byte
   * 
   * @param offset
   *          a <code>long</code> 文件中的位置
   * @return a <code>byte</code> 读取的byte
   */
  private byte readByte(long offset) {
    byte b = 0;
    try {
      rIpFile.seek(offset);
      b = rIpFile.readByte();
      return b;
    } catch (IOException e) {
      return -1;
    }
  }

  /**
   * Describe <code>readLong3</code> 从文件中读取一个3位的长整数
   * 
   * @param offset
   *          a <code>long</code> 文件的位置
   * @return a <code>long</code> 读出的长整数
   */
  private long readLong3(long offset) {
    long ret = 0;
    byte[] b3 = new byte[] {
        0, 0, 0
    };
    try {
      rIpFile.seek(offset);
      rIpFile.readFully(b3);
      ret |= (b3[0] & 0xFF);
      ret |= ((b3[1] << 8) & 0xFF00);
      ret |= ((b3[2] << 16) & 0xFF0000);
      return ret;
    } catch (IOException e) {
      return -1;
    }
  }

  /**
   * Describe <code>readLong4</code> 从文件中读取一个4位的长整数
   * 
   * @param offset
   *          a <code>long</code> 文件的位置
   * @return a <code>long</code> 读出的长整数
   */
  private long readLong4(long offset) {
    long ret = 0;
    byte[] b4 = new byte[] {
        0, 0, 0, 0
    };
    try {
      rIpFile.seek(offset);
      rIpFile.readFully(b4);
      ret |= (b4[0] & 0xFF);
      ret |= ((b4[1] << 8) & 0xFF00);
      ret |= ((b4[2] << 16) & 0xFF0000);
      ret |= ((b4[3] << 32) & 0xFF000000);
      return ret;
    } catch (IOException e) {
      return -1;
    }
  }

  /**
   * 从offset偏移处读取一个以0结束的字符串
   * 
   * @param offset
   *          字符串起始偏移
   * @return 读取的字符串，出错返回空字符串
   */
  private String readString(long offset) {
    byte[] buf = new byte[1024];
    try {
      // 如果输入的参数为－1，从当前位置读取文件
      if (offset != -1) {
        rIpFile.seek(offset);
      }
      int i;
      for (i = 0, buf[i] = rIpFile.readByte(); buf[i] != 0; buf[++i] = rIpFile.readByte())
        ;
      if (i != 0) {
        try {
          String s = new String(buf, 0, i, "GBK");
          if (s.indexOf("CZ88") > 0) {
            return "";
          }
          return s;
        } catch (UnsupportedEncodingException e) {
          return new String(buf, 0, i);
        }
      }
    } catch (IOException e) {
      return "";
    }
    return "";
  }

  public static class IpAddress implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private int[] ip;

    public IpAddress(int[] inputIntArray) {
      ip = new int[4];
      ip[0] = inputIntArray[0];
      ip[1] = inputIntArray[1];
      ip[2] = inputIntArray[2];
      ip[3] = inputIntArray[3];
    }

    public int[] getIpArray() {
      return ip;
    }

    public int compareIp(IpAddress inputIpAddress) {
      int[] input_ip = inputIpAddress.getIpArray();
      for (int i = 0; i < 4; i++) {
        if (ip[i] < input_ip[i]) {
          return -1;
        }
        if (ip[i] > input_ip[i]) {
          return 1;
        }
      }
      return 0;
    }
  }

}
