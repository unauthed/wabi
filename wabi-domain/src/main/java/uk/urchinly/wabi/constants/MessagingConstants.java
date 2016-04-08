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
package uk.urchinly.wabi.constants;

public interface MessagingConstants {

	public static final String ASSET_INSERT_AUDIO_ROUTE = "asset.insert.audio";
	public static final String ASSET_UPDATE_AUDIO_ROUTE = "asset.update.audio";
	public static final String ASSET_ERROR_AUDIO_ROUTE = "asset.error.audio";

	public static final String ASSET_INSERT_VIDEO_ROUTE = "asset.insert.video";
	public static final String ASSET_UPDATE_VIDEO_ROUTE = "asset.update.video";
	public static final String ASSET_ERROR_VIDEO_ROUTE = "asset.error.video";

	public static final String ASSET_INSERT_IMAGE_ROUTE = "asset.insert.image";
	public static final String ASSET_UPDATE_IMAGE_ROUTE = "asset.update.image";
	public static final String ASSET_ERROR_IMAGE_ROUTE = "asset.error.image";

	public static final String ASSET_INSERT_PDF_ROUTE = "asset.insert.pdf";
	public static final String ASSET_INSERT_OFFICE_ROUTE = "asset.insert.office";
	public static final String ASSET_INSERT_OTHER_ROUTE = "asset.insert.other";

	public static final String AUDIT_ROUTE = "audit";
	public static final String USAGE_ROUTE = "usage";
}
