package jp.co.sint.webshop.service.data;

import java.net.URI;

public interface WebContents {

  ContentsType getContents();

  boolean isArchived();

  URI getUri();

}
