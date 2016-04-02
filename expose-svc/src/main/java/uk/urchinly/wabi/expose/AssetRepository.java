/**
 * Copyright (C) ${year} Urchinly <wabi@urchinly.uk>
 */
package uk.urchinly.wabi.expose;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import uk.urchinly.wabi.entities.Asset;

public interface AssetRepository extends MongoRepository<MongoAsset, String> {

	public List<Asset> findByUserId(String userId);

}