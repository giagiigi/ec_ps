package jp.co.sint.webshop.service.impl;

import java.lang.reflect.Method;
import java.text.MessageFormat;

import jp.co.sint.webshop.service.StatefulService;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

public class ServiceMethodInterceptor implements MethodInterceptor {

  public Object invoke(MethodInvocation invocation) throws Throwable {
    Logger logger = Logger.getLogger(this.getClass());
    Method method = invocation.getMethod();
    Class<?> declaringClass = method.getDeclaringClass();
    if (!declaringClass.equals(StatefulService.class)) {
      logger.debug(MessageFormat.format("called method : {0}#{1}()", declaringClass.getSimpleName(), method.getName()));
      for (int i = 0; i < invocation.getArguments().length; i++) {
        Object arg = invocation.getArguments()[i];
        logger.debug("arguments[" + i + "]  : " + arg);
      }
      Object result = invocation.proceed();
      return result;
    } else {
      return invocation.proceed();
    }
  }
}
