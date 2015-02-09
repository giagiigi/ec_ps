package jp.co.sint.webshop.service.event;

public interface ServiceEventType<E extends ServiceEvent, L extends ServiceEventListener<E>> {

  void execute(L listener, E event);
}
