/**
 * Copyright (C) ${year} Urchinly <wabi@urchinly.uk>
 */
package uk.urchinly.wabi.entities;

import java.io.Serializable;
import java.util.List;

public interface WabiAsset extends Serializable {

	String getId();

	void setId(String id);

	String getUserId();

	void setUserId(String userId);

	String getFileName();

	void setFileName(String fileName);

	Double getPrice();

	void setPrice(Double price);

	List<String> getCategory();

	void setCategory(List<String> category);
}