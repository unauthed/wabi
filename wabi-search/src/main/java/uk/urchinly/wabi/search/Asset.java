/**
 * Copyright (C) ${year} Urchinly <wabi@urchinly.uk>
 */
package uk.urchinly.wabi.search;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.util.MimeType;

import uk.urchinly.wabi.entities.AbstractEntity;
import uk.urchinly.wabi.entities.WabiAsset;

@Document(indexName = "asset", type = "asset", shards = 1, replicas = 0, refreshInterval = "-1")
public class Asset extends AbstractEntity implements WabiAsset {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String userId;

	private String fileName;

	private Double fileSize;

	private MimeType mimeType;

	private List<String> tags;

	public Asset() {
	}

	public Asset(String userId, String fileName, Double fileSize, MimeType mimeType, List<String> tags) {
		super();
		this.userId = userId;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.mimeType = mimeType;
		this.tags = tags;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getUserId() {
		return this.userId;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String getFileName() {
		return this.fileName;
	}

	@Override
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public Double getFileSize() {
		return this.fileSize;
	}

	@Override
	public void setFileSize(Double fileSize) {
		this.fileSize = fileSize;
	}

	@Override
	public MimeType getMimeType() {
		return this.mimeType;
	}

	@Override
	public void setMimeType(MimeType mimeType) {
		this.mimeType = mimeType;
	}

	@Override
	public List<String> getTags() {
		return this.tags;
	}

	@Override
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}