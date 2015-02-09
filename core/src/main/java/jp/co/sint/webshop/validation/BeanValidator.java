package jp.co.sint.webshop.validation;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.attribute.ValidatorClass;
import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;

/**
 * バリデーションフレームワークのコントローラです。
 * 
 * @author System Integrator Corp.
 */
public final class BeanValidator implements Serializable {

  /** Serial version uid */
  private static final long serialVersionUID = 1L;

  private static final String PKG_NAME = "jp.co.sint.webshop.data.attribute";

  private static String getGetterMethodName(String f) {
    return "get" + f.substring(0, 1).toUpperCase(Locale.getDefault()) + f.substring(1);
  }

  /**
   * 指定されたフィールドに設定された属性情報をもとに、オブジェクトを部分的に検証します。
   * 
   * @param <T>
   * @param obj
   *          検証対象のオブジェクト
   * @param fieldNames
   *          フィールド名
   * @return 検証結果を表すValidationSummaryオブジェクト
   */
  public static <T>ValidationSummary partialValidate(T obj, String... fieldNames) {
    Field[] fields = new Field[fieldNames.length];
    try {
      for (int i = 0; i < fields.length; i++) {
        fields[i] = obj.getClass().getDeclaredField(fieldNames[i]);
      }
    } catch (NoSuchFieldException e) {
      Logger logger = Logger.getLogger(BeanValidator.class);
      logger.warn(e);
    }
    return validate0(obj, fields);
  }

  /**
   * 指定されたカスタムバリデータを使用して、オブジェクトを検証します。
   * 
   * @param <T>
   * @param obj
   *          検証対象のオブジェクト
   * @param validator
   * @return 検証結果を表すValidationSummaryオブジェクト
   */
  public static <T>ValidationSummary validate(T obj, CustomValidator<T> validator) {
    return validator.validate(obj);
  }

  /**
   * オブジェクトのフィールドに設定された属性情報をもとに、オブジェクトを検証します。
   * 
   * @param <T>
   * @param obj
   *          検証対象のオブジェクト
   * @return 検証結果を表すValidationSummaryオブジェクト
   */
  public static <T>ValidationSummary validate(T obj) {
    return validate0(obj, obj.getClass().getDeclaredFields());
  }

  @SuppressWarnings("unchecked")
  private static ValidationSummary validate0(Object obj, Field[] fields) {

    Class<?> objectType = obj.getClass();
    Logger logger = Logger.getLogger(BeanValidator.class);
    logger.trace("Validation start: target = " + objectType.getName());
    List<ValidationResult> list = new ArrayList<ValidationResult>();

    List<String> excludes = Arrays.asList("createdUser", "updatedUser", "createdDatetime", "updatedDatetime");
    try {
      for (Field f : fields) {
        if (f.getAnnotations().length == 0) {
          continue;
        }
        //DTOのユーザ状態列は検証対象外とする
        if (obj instanceof WebshopEntity && excludes.contains(f.getName())) {
          continue;
        }
        String s = getGetterMethodName(f.getName());
        Object value = objectType.getMethod(s).invoke(obj);
        String field = f.getName();
        int dispOrder = Integer.MAX_VALUE;

        Metadata meta = f.getAnnotation(Metadata.class);
        if (meta != null) {
         //add by V10-CH 170 start
         field = StringUtil.coalesce(CodeUtil.getMetadata(f, "name", obj), meta.name());
         //add by V10-CH 170 end
         dispOrder = meta.order();
        }
        logger.trace("check target :" + field + "(" + f.getName() + ")");

        List<ValidationResult> errors = new ArrayList<ValidationResult>();
        for (Annotation ann : f.getAnnotations()) {

          Class<? extends Annotation> annType = ann.annotationType();
          String annotationName = annType.getCanonicalName();

          if (!annotationName.startsWith(PKG_NAME)) {
            continue;
          } else if (annotationName.startsWith(PKG_NAME + ".Metadata")) {
            continue;
          }
          logger.trace(" *annotation :" + ann.toString());
          logger.trace("  type       :" + f.getDeclaringClass().getName());
          logger.trace("  value      :" + value);

          ValidatorClass vc = annType.getAnnotation(ValidatorClass.class);
          if (vc != null) {

            Class<? extends Validator> validatorType = vc.value();
            try {
              Validator v = validatorType.newInstance();
              logger.trace("  validator  :" + v.getClass().getName());
              v.init(ann);
              if (v.isValid(value)) {
                logger.trace("  result : CORRECT");
              } else {
                logger.trace("  result : ERROR");

                ValidationResult error = new ValidationResult();
                error.setName(field);
                error.setDisplayOrder(dispOrder);
                error.setMessage(v.getMessage());
                error.setPriority(getPriority(ann));

                errors.add(error);
              }
            } catch (InstantiationException e) {
              logger.error(e);
            }
          } else {
            logger.trace("  validator  : VALIDATOR WAS NOT FOUND");
            // throw new ValidatorNotFoundException();
          }
        }

        if (errors.size() > 0) {
          Collections.sort(errors, new ValidationResultComparator());
          list.add(errors.get(0));
        }
      }

      if (obj instanceof Validatable) {
        Validatable b = (Validatable) obj;
        list.addAll(b.validate().getErrors());
      }

      Collections.sort(list, new Comparator<ValidationResult>() {

        public int compare(ValidationResult o1, ValidationResult o2) {
          return o1.getDisplayOrder() - o2.getDisplayOrder();
        }
      });

    } catch (IllegalAccessException e) {
      throw new ValidationFailureException(e);
    } catch (InvocationTargetException e) {
      throw new ValidationFailureException(e);
    } catch (NoSuchMethodException e) {
      throw new ValidationFailureException(e);
    }
    ValidationSummary summary = new ValidationSummary();
    summary.setErrors(list);

    return summary;
  }

  private BeanValidator() {
  }

  private static int getPriority(Annotation ann) {
    if (ann instanceof Required) {
      return 10000;
    } else if (ann instanceof Length) {
      return 9000;
    } else {
      return 8000;
    }
  }

  private static class ValidationResultComparator implements Serializable, Comparator<ValidationResult> {

    private static final long serialVersionUID = 1L;

    public int compare(ValidationResult o1, ValidationResult o2) {
      return (o1.getPriority() - o2.getPriority()) * -1;
    }
  }
}
