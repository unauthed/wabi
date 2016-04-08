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
package uk.urchinly.wabi.expose;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uk.urchinly.wabi.constants.MessagingConstants;
import uk.urchinly.wabi.events.UsageEvent;

@RestController
@CrossOrigin
public class DownloadController {

	private static final Logger logger = LoggerFactory.getLogger(DownloadController.class);

	@Value("${app.share.path}")
	private String appSharePath;

	@Autowired
	private AssetRepository assetMongoRepository;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@RequestMapping(method = RequestMethod.GET, value = "/assets")
	public Page<Asset> getAssets(@PageableDefault(page = 0, size = 20) Pageable pageable) {

		Page<Asset> assets = this.assetMongoRepository.findAll(pageable);

		return assets;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/assets/{assetId}")
	public Asset getAsset(@PathVariable String assetId) {

		Asset asset = this.assetMongoRepository.findOne(assetId);

		this.rabbitTemplate.convertAndSend(MessagingConstants.USAGE_ROUTE,
				new UsageEvent("view asset", asset.toString()));

		return asset;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/assets/user/{userId}")
	public Page<Asset> getUserAssets(@PathVariable String userId,
			@PageableDefault(page = 0, size = 20) Pageable pageable) {

		Page<Asset> assets = this.assetMongoRepository.findByUserId(userId, pageable);

		for (Asset asset : assets) {
			this.rabbitTemplate.convertAndSend(MessagingConstants.USAGE_ROUTE,
					new UsageEvent("view asset", asset.toString()));
		}

		if (!assets.hasContent()) {
			logger.debug("Download assets failed for userId {}, no asset found.", userId);
		}

		return assets;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/browse")
	public List<String> browseFiles(Model model) {

		File rootFolder = new File(appSharePath);

		return Arrays.stream(rootFolder.listFiles()).map(f -> f.getName()).collect(Collectors.toList());
	}

	@RequestMapping(value = "/download/{assetId}", method = RequestMethod.GET)
	public void download(HttpServletResponse response, @PathVariable("assetId") String assetId) throws IOException {

		Asset asset = this.assetMongoRepository.findOne(assetId);

		if (asset == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		File file = new File(appSharePath, asset.getFileName());

		if (file.canRead()) {
			this.rabbitTemplate.convertAndSend(MessagingConstants.USAGE_ROUTE,
					new UsageEvent("download asset", asset.toString()));

			response.setContentType(asset.getContentType());
			response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
			response.setContentLength((int) file.length());

			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} else {
			logger.warn("File '{}' not found.", file.getAbsolutePath());
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}