/**
 * Copyright (C) ${year} Urchinly <wabi@urchinly.uk>
 */
package uk.urchinly.wabi.search;

import java.util.List;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

import uk.urchinly.wabi.entities.Asset;

@SolrDocument
public class SolrAsset implements Asset {

	private static final long serialVersionUID = 1L;

	@Id
	@Field
	private String id;

	@Field
	private String userId;

	@Field
	private String fileName;

	@Field
	private Double price;

	@Field("cat")
	private List<String> category;

	public SolrAsset() {
	}

	public SolrAsset(String userId, String fileName, Double price, List<String> category) {
		super();
		this.userId = userId;
		this.fileName = fileName;
		this.price = price;
		this.category = category;
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
	public Double getPrice() {
		return this.price;
	}

	@Override
	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public List<String> getCategory() {
		return this.category;
	}

	@Override
	public void setCategory(List<String> category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Asset [id=" + this.id + ", userId=" + this.userId + ", fileName=" + this.fileName + ", price="
				+ this.price + ", category=" + this.category + "]";
	}

}