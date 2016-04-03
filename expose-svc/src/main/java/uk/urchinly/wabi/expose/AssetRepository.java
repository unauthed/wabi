/**
 * Copyright (C) ${year} Urchinly <wabi@urchinly.uk>
 */
package uk.urchinly.wabi.expose;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import uk.urchinly.wabi.entities.WabiAsset;

public interface AssetRepository extends MongoRepository<Asset, String> {

	public List<WabiAsset> findByUserId(String userId);

}