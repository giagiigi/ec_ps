package jp.co.sint.webshop.service.event;

import java.util.List;

import org.apache.log4j.Logger;

public final class ServiceEventHandler {

  private ServiceEventHandler() {
  }

  public static <E extends ServiceEvent, L extends ServiceEventListener<E>, T extends ServiceEventType<E, L>>void execute(
      List<L> listeners, E event, T type) {
    Logger logger = Logger.getLogger(ServiceEventHandler.class);
    if (listeners != null) {
      for (L listener : listeners) {
        try {
          type.execute(listener, event);
        } catch (RuntimeException ex) {
          logger.warn(ex);
        }
      }
    }
  }
}
