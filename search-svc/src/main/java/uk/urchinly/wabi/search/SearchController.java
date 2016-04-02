/**
 * Copyright (C) ${year} Urchinly <wabi@urchinly.uk>
 */
package uk.urchinly.wabi.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

	@Autowired
	private AssetRepository assetRepository;

	@RequestMapping(path = "/search", method = RequestMethod.GET)
	public List<SolrAsset> search(@RequestParam(value = "userId", defaultValue = "test") String userId) {
		return this.assetRepository.findByUserId(userId);
	}
}