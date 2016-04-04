/**
 * Copyright (C) ${year} Urchinly <wabi@urchinly.uk>
 */
package uk.urchinly.wabi.search;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

public interface AssetRepository extends ElasticsearchCrudRepository<Asset, String> {

	List<Asset> findByUserId(String userId);
}