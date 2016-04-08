/**
 * Wabi-Sabi DAM solution
 * Open source Digital Asset Management platform of great simplicity and beauty.
 * Copyright (C) 2016 Urchinly <wabi-sabi@urchinly.uk>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
  * Copyright (C) ${year} Urchinly <wabi@urchinly.uk>
 */
package uk.urchinly.wabi.events;

import java.util.List;

import uk.urchinly.wabi.entities.WabiAsset;

public class AssetEvent extends AbstractEvent implements WabiAsset {

	private static final long serialVersionUID = 1L;

	private String id;

	private String userId;

	private String fileName;

	private Double fileSize;

	private String contentType;

	private List<String> tags;

	public AssetEvent() {
	}

	public AssetEvent(String userId, String fileName, Double fileSize, String contentType, List<String> tags) {
		super();
		this.userId = userId;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.contentType = contentType;
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
	public String getContentType() {
		return this.contentType;
	}

	@Override
	public void setContentType(String contentType) {
		this.contentType = contentType;
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
