/**
 * Copyright (C) ${year} Urchinly <wabi@urchinly.uk>
 */
package uk.urchinly.wabi.search;

import java.util.List;

import org.springframework.data.solr.repository.SolrCrudRepository;

import uk.urchinly.wabi.entities.Asset;

public interface AssetRepository extends SolrCrudRepository<Asset, String> {

	List<Asset> findByUserId(String userId);
}