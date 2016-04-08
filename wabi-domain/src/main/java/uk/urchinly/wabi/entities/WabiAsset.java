/**
 * Copyright (C) ${year} Urchinly <wabi@urchinly.uk>
 */
package uk.urchinly.wabi.entities;

import java.io.Serializable;
import java.util.List;

import org.springframework.util.MimeType;

public interface WabiAsset extends Serializable {

	String getId();

	void setId(String id);

	String getUserId();

	void setUserId(String userId);

	String getFileName();

	void setFileName(String fileName);

	Double getFileSize();

	void setFileSize(Double bytes);

	MimeType getMimeType();

	void setMimeType(MimeType mimeType);

	List<String> getTags();

	void setTags(List<String> tags);
}