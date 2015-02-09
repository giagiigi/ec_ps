package jp.co.sint.webshop.utility;

import java.util.Random;

public final class RandomUtil {

  /**
   * 获得一个随即码【字母与数字组合】
   * 
   * @param codeLen
   *          随机码长度
   * @return
   */
  public static String getCharAndNumber(int codeLen) {
    // 根据指定长度生成字母和数字的随机数
    StringBuilder sb = new StringBuilder();
    Random rand = new Random();
    Random randdata = new Random();
    String num = "123456789";
    String cha = "ABCDEFGHIJKLMNPQRSTUVWXYZ";
    int data = 0;
    for (int i = 0; i < codeLen; i++) {
      int index = rand.nextInt(2);
      // 目的是随机选择生成数字，大小
      switch (index) {
        case 0:
          // 生成1~9
          data = randdata.nextInt(num.length());
          sb.append(num.charAt(data));
          break;
        case 1:
          // 产生A-Z{除O外}
          data = randdata.nextInt(cha.length());
          sb.append(cha.charAt(data));
          break;
      }
    }

    return sb.toString();
  }
}
