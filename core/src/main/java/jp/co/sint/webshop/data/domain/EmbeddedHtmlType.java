//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// ���̃t�@�C���̓R�[�h��`�h�L�������g���玩����������܂��B
// ���ڕҏW���Ȃ��ŉ������B
//
package jp.co.sint.webshop.data.domain;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * �u���ߍ���HTML�敪�v�̃R�[�h��`��\���񋓃N���X�ł��B
 *
 * @author System Integrator Corp.
 *
 */
public enum EmbeddedHtmlType implements CodeAttribute {

  /** �u�����������(PC)�v��\���l�ł��B */
  DECRIBE_CN("中文", "0"),
  DECRIBE_EN("英文", "1"),
  DECRIBE_JP("日文", "2"),
  DECRIBE_TMALL("TMALL", "3");

  private String name;

  private String value;

  private EmbeddedHtmlType(String name, String value) {
    this.name = name;
    this.value = value;
  }
  /**
   * �R�[�h���̂�Ԃ��܂��B
   * @return �R�[�h����
   */
  public String getName() {
    return name;
  }

  /**
   * �R�[�h�l��Ԃ��܂��B
   * @return �R�[�h�l
   */
  public String getValue() {
    return this.value;
  }

  /**
   * Long�^�̃R�[�h�l��Ԃ��܂��B
   * @return �R�[�h�l
   */
  public Long longValue() {
    return Long.valueOf(this.value);
  }

  /**
   * �w�肳�ꂽ�R�[�h�����ߍ���HTML�敪��Ԃ��܂��B
   *
   * @param name �R�[�h��
   * @return ���ߍ���HTML�敪
   */
  public static EmbeddedHtmlType fromName(String name) {
    for (EmbeddedHtmlType p : EmbeddedHtmlType.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * �w�肳�ꂽ�R�[�h�l���ߍ���HTML�敪��Ԃ��܂��B
   *
   * @param value �R�[�h�l
   * @return ���ߍ���HTML�敪
   */
  public static EmbeddedHtmlType fromValue(String value) {
    for (EmbeddedHtmlType p : EmbeddedHtmlType.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * �w�肳�ꂽ�R�[�h�l���ߍ���HTML�敪��Ԃ��܂��B
   *
   * @param value �R�[�h�l
   * @return ���ߍ���HTML�敪
   */
  public static EmbeddedHtmlType fromValue(Long value) {
    return fromValue(Long.toString(value));
  }

  /**
   * �w�肳�ꂽ�R�[�h�l���L��ǂ�����Ԃ��܂��B
   *
   * @param value �R�[�h�l
   * @return �R�[�h�l���L��ł����true
   */
  public static boolean isValid(String value) {
    if (StringUtil.hasValue(value)) {
      for (EmbeddedHtmlType p : EmbeddedHtmlType.values()) {
        if (p.getValue().equals(value)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * �w�肳�ꂽ�R�[�h�l���L��ǂ�����Ԃ��܂��B
   *
   * @param value �R�[�h�l
   * @return �R�[�h�l���L��ł����true
   */
  public static boolean isValid(Long value) {
    return isValid(Long.toString(value));
  }
}
