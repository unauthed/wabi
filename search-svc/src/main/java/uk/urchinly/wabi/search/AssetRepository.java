/**
 * Copyright (C) ${year} Urchinly <wabi@urchinly.uk>
 */
package uk.urchinly.wabi.search;

import java.util.List;

import org.springframework.data.solr.repository.SolrCrudRepository;

public interface AssetRepository extends SolrCrudRepository<SolrAsset, String> {

	List<SolrAsset> findByUserId(String userId);
}