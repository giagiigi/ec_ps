package jp.co.sint.webshop.utility;

import java.nio.charset.Charset; // 10.4.0 10616 �ǉ�
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale; // 10.2.4 10542 �ǉ�
import java.util.UUID;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;

/**
 * �p�X���[�h�E�n�b�V���֘A���[�e�B���e�B
 * 
 * @author System Integrator Corp.
 */
public final class PasswordUtil {

  private static final String DEFAULT_HASH_ALGORITHM = "SHA-1";
  // 10.3.0 X30022 �ǉ� ��������
  private static final int DEFAULT_STRETCHING = 3000;
  // 10.3.0 X30022 �ǉ� �����܂�
  // 10.2.0 X20038 �C�� ��������
  // private static final String DEFAULT_SECRET_KEY = "SI Web Shopping";
  static final String DEFAULT_SECRET_KEY = "SI Web Shopping";
  // 10.2.0 X20038 �C�� �����܂�
  // 10.2.0 X20038 �C�� ��������
  // private static final String DEFAULT_ALGORYTHM = "Blowfish";
  static final String DEFAULT_ALGORYTHM = "Blowfish";
  // 10.2.0 X20038 �C�� �����܂�
  //10.4.0 10616 �ǉ� ��������
  private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
  //10.4.0 10616 �ǉ� �����܂�
  

  private PasswordUtil() {
  }

  /**
   * �_�C�W�F�X�g��擾���܂�
   * 
   * @param input
   *          �_�C�W�F�X�g�쐬�Ώە�����
   * @return �_�C�W�F�X�g�������Ԃ��܂��B
   */
  public static String getDigest(String input) {
  // 10.3.0 X30022 �ǉ� ��������
    return getDigest(input, "", DEFAULT_HASH_ALGORITHM, 0);
  }
  /**
   * �\���g�𗘗p���āA�_�C�W�F�X�g��擾���܂��B
   * 
   * @param input
   *          �_�C�W�F�X�g�쐬�Ώە�����
   * @param salt
   *          �_�C�W�F�X�g�������Ɏg�p����\���g
   * @return �_�C�W�F�X�g�������Ԃ��܂��B
   */
  public static String getDigest(String input, String salt) {
    return getDigest(input, salt, DEFAULT_HASH_ALGORITHM, DEFAULT_STRETCHING);
  }
  /**
   * �_�C�W�F�X�g��擾���܂�
   * 
   * @param input
   *          �_�C�W�F�X�g�쐬�Ώە�����
   * @param salt
   *          �_�C�W�F�X�g�������Ɏg�p����\���g
   * @param algorithm
   *          �_�C�W�F�X�g�����A���S���Y��
   * @param times
   *          �_�C�W�F�X�g�̃X�g���b�`���O�񐔁B�[���������w�肳�ꂽ�ꍇ�̓X�g���b�`���O��s���܂���B
   * @return �_�C�W�F�X�g�������Ԃ��܂��B
   */
  public static String getDigest(String input, String salt, String algorithm, int times) {
  // 10.3.0 X30022 �ǉ� �����܂�
    String result = "";
    try {
      // 10.3.0 X30022 �C�� ��������
      // MessageDigest md = MessageDigest.getInstance(DEFAULT_HASH_ALGORITHM);
      // byte[] digest = md.digest(input.getBytes());
      MessageDigest md = MessageDigest.getInstance(algorithm);
      // 10.4.0 10616 �C�� ��������
      // md.update(salt.getBytes());
      // byte[] src = input.getBytes();
      md.update(salt.getBytes(DEFAULT_CHARSET));
      byte[] src = input.getBytes(DEFAULT_CHARSET);
      // 10.4.0 10616 �C�� �����܂�
      while (times-- > 0) {
        md.update(src);
      }
      byte[] digest = md.digest(src);
      // 10.3.0 X30022 �C�� �����܂�
      result = toHexString(digest);
    } catch (NoSuchAlgorithmException e) {
      Logger.getLogger(PasswordUtil.class).debug(e);
      result = "";
    }
    return result;
  }

  /**
   * �_�C�W�F�X�g������
   * 
   * @param value
   *          �_�C�W�F�X�g������
   * @param digest
   *          ��r�Ώƃ_�C�W�F�X�g������
   * @return ����ɂ�BĐ������ꂽ�_�C�W�F�X�g�����񂪁A����̃_�C�W�F�X�g������Ɠ������ꍇ�Atrue��Ԃ��܂��B<br>
   *         ����ɂ�BĐ������ꂽ�_�C�W�F�X�g�����񂪁A����̃_�C�W�F�X�g������Ɠ������Ȃ��ꍇ�Afalse��Ԃ��܂��B
   */
  public static boolean check(String value, String digest) {
    return getDigest(value).equals(digest);
  }

  /**
   * �o�C�g�z���16�i��������ɕϊ����܂��B
   * 
   * @param bytes
   *          �o�C�g�z��
   * @return 16�i���������Ԃ��܂��B
   */
  public static String toHexString(byte[] bytes) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < bytes.length; i++) {
      int val = bytes[i] & 0xFF;
      if (val < 0x10) {
        builder.append("0");
      }
      builder.append(Integer.toString(val, 16));
    }
    return builder.toString();
  }

  /**
   * 16�i���������o�C�g�z��ɕϊ����܂��B
   * 
   * @param hexString
   *          16�i��������
   * @return �o�C�g�z���Ԃ��܂��B
   */
  public static byte[] toByteArray(String hexString) {
    byte[] bytes = new byte[hexString.length() / 2];
    for (int i = 0; i < bytes.length; i++) {
      String sub = hexString.substring(i * 2, (i + 1) * 2);
      int t = Integer.parseInt(sub, 16);
      if (t >= 0x80) {
        t -= 0x100;
      }
      bytes[i] = (byte) t;
    }
    return bytes;
  }

  public static String generateTransactionToken() {
    return UUID.randomUUID().toString();
  }
  // 10.2.4 10542 �ǉ� ��������
  /**
   * �F�؂̂��߂̃����_���ȃg�[�N���𔭍s���܂��B
   * 
   * @return �����_���ȃg�[�N��
   */
  public static String generateRandomToken() {
    return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase(Locale.US);
  }
  // 10.2.4 10542 �ǉ� �����܂�

  /**
   * �f�t�H���g�̔閧���ƃA���S���Y���ŁA�������Í������܂��B
   * �Í����̌��ʂ͉�s�Ȃ���Base64Encode�{URLEncode���ꂽ�`���ŕԂ���܂��B
   * 
   * @param value
   *          �Ώە�����
   * @return �Í������ʂ�Ԃ��܂��B
   * @deprecated �V�K�Ɏg�p����ꍇ��encrypt(CypherConfig config, String value)��g�p���Ă��������B<!-- 10.2.0 X20038 �ǉ� -->
   */
  public static String encrypt(String value) {
    return encrypt(DEFAULT_ALGORYTHM, DEFAULT_SECRET_KEY, value);
  }
  // 10.2.0 X20038 �ǉ� ��������
  public static String encrypt(CipherConfig config, String value) {
    return encrypt(config.getAlgorithm(), config.getSecretKey(), value);
  }
  // 10.2.0 X20038 �ǉ� �����܂�

  /**
   * �A���S���Y���Ɣ閧����w�肵�āA�������Í������܂��B �Í����̌��ʂ͉�s�Ȃ���Base64Encode�{URLEncode���ꂽ�`���ŕԂ���܂��B
   * 
   * @param algorythm
   *          �A���S���Y��
   * @param key
   *          �閧��
   * @param value
   *          �Ώە�����
   * @return �Í������ʂ�Ԃ��܂��B
   */
  public static String encrypt(String algorythm, String key, String value) {
    Logger logger = Logger.getLogger(PasswordUtil.class);
    String result = "";
    try {
      // 10.4.0 10616 �C�� ��������
      // SecretKeySpec sksSpec = new SecretKeySpec(key.getBytes(), algorythm);
      SecretKeySpec sksSpec = new SecretKeySpec(key.getBytes(DEFAULT_CHARSET), algorythm);
      // 10.4.0 10616 �C�� �����܂�
      // 10.1.5 10234 �C�� ��������
      // Cipher cipher = Cipher.getInstance(DEFAULT_ALGORYTHM);
      Cipher cipher = Cipher.getInstance(algorythm);
      // 10.1.5 10234 �C�� �����܂�
      cipher.init(Cipher.ENCRYPT_MODE, sksSpec);
      // 10.4.0 10616 �C�� ��������
      // byte[] encrypted = cipher.doFinal(value.getBytes());
      byte[] encrypted = cipher.doFinal(value.getBytes(DEFAULT_CHARSET));
      // 10.4.0 10616 �C�� �����܂�
      result = base64Encode(encrypted).replace("\r\n", "");
      // 10.1.3 10178 �ǉ� ��������
      result = result.replace("+", "-");
      // 10.1.3 10178 �ǉ� �����܂�
      result = result.replace("/", "$");
      result = WebUtil.urlEncode(result);
    } catch (InvalidKeyException e) {
      logger.error(e);
    } catch (NoSuchPaddingException e) {
      logger.error(e);
    } catch (BadPaddingException e) {
      logger.error(e);
    } catch (IllegalBlockSizeException e) {
      logger.error(e);
    } catch (NoSuchAlgorithmException e) {
      logger.error(e);
    }
    return result;

  }

  /**
   * �f�t�H���g�̔閧���ƃA���S���Y���ŁA�Í������ꂽ������𕜍����܂��B
   * 
   * @param value
   *          Base64�G���R�[�h���ꂽ�Í���������(PasswordUtil.encrypt���\�b�h�̖߂�l)
   * @return �������ʂ�Ԃ��܂��B
   * @deprecated �V�K�Ɏg�p����ꍇ��decrypt(CypherConfig config, String value)��g�p���Ă��������B<!-- 10.2.0 X20038 �ǉ� -->
   */
  public static String decrypt(String value) {
    return decrypt(DEFAULT_ALGORYTHM, DEFAULT_SECRET_KEY, value);
  }

  // 10.2.0 X20038 �ǉ� ��������
  public static String decrypt(CipherConfig config, String value) {
    return decrypt(config.getAlgorithm(), config.getSecretKey(), value);
  }
  // 10.2.0 X20038 �ǉ� �����܂�
  /**
   * �A���S���Y���Ɣ閧����w�肵�āA�Í������ꂽ������𕜍����܂��B
   * 
   * @param algorythm
   *          �A���S���Y��
   * @param key
   *          �閧��
   * @param value
   *          Base64�G���R�[�h���ꂽ�Í���������(PasswordUtil.encrypt���\�b�h�̖߂�l)
   * @return �������ʂ�Ԃ��܂��B
   */
  public static String decrypt(String algorythm, String key, String value) {
    Logger logger = Logger.getLogger(PasswordUtil.class);
    String result = "";
    try {
      // 10.4.0 10616 �C�� ��������
      // SecretKeySpec sksSpec = new SecretKeySpec(key.getBytes(), algorythm);
      SecretKeySpec sksSpec = new SecretKeySpec(key.getBytes(DEFAULT_CHARSET), algorythm);
      // 10.4.0 10616 �C�� �����܂�
      // 10.1.5 10234 �C�� ��������
      // Cipher cipher = Cipher.getInstance(DEFAULT_ALGORYTHM);
      Cipher cipher = Cipher.getInstance(algorythm);
      // 10.1.5 10234 �C�� �����܂�
      cipher.init(Cipher.DECRYPT_MODE, sksSpec);
      value = WebUtil.urlDecode(value);
      // 10.1.3 10178 �ǉ� ��������
      value = value.replace("-", "+");
      // 10.1.3 10178 �ǉ� �����܂�
      value = value.replace("$", "/");
      byte[] decoded = base64Decode(value);
      byte[] decrypted = cipher.doFinal(decoded);
      // 10.4.0 10616 �C�� ��������
      // result = new String(decrypted);
      result = new String(decrypted, DEFAULT_CHARSET);
      // 10.4.0 10616 �C�� �����܂�
    } catch (InvalidKeyException e) {
      logger.error(e);
    } catch (NoSuchPaddingException e) {
      logger.error(e);
    } catch (BadPaddingException e) {
      logger.error(e);
    } catch (IllegalBlockSizeException e) {
      logger.error(e);
    } catch (NoSuchAlgorithmException e) {
      logger.error(e);
    }
    return result;
  }

  /**
   * ��������s�Ȃ���Base64Encode�{URLEncode���ꂽ�`���ňÍ������ĕԂ��܂��B
   * 
   * @param value
   *          �Ώە�����
   * @return �Í������ʂ�Ԃ��܂��B
   */
  public static String base64Encode(byte[] value) {
    // 10.4.0 10616 �C�� ��������
    // return new String(BASE64EncoderStream.encode(value));
    return new String(BASE64EncoderStream.encode(value), DEFAULT_CHARSET);
    // 10.4.0 10616 �C�� �����܂�
  }

  /**
   * ��������s�Ȃ���Base64Encode�{URLEncode���ꂽ�`���ňÍ������ĕԂ��܂��B
   * 
   * @param value
   *          �Ώە�����
   * @return �Í������ʂ�Ԃ��܂��B
   */
  public static byte[] base64Decode(String value) {
    // 10.4.0 10616 �C�� ��������
    // return BASE64DecoderStream.decode(value.getBytes());
    return BASE64DecoderStream.decode(value.getBytes(DEFAULT_CHARSET));
    // 10.4.0 10616 �C�� �����܂�
  }
}
