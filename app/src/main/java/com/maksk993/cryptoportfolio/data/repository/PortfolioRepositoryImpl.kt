package com.maksk993.cryptoportfolio.data.repository

import com.maksk993.cryptoportfolio.data.models.room.AssetsDbDao
import com.maksk993.cryptoportfolio.data.models.room.entities.AssetDbEntity
import com.maksk993.cryptoportfolio.data.models.room.entities.UpdateAmountTuple
import com.maksk993.cryptoportfolio.domain.repository.PortfolioRepository
import com.maksk993.cryptoportfolio.domain.models.AssetItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PortfolioRepositoryImpl(private val dao : AssetsDbDao) : PortfolioRepository {
    override fun findAssetById(id : Long) : Flow<AssetItem?> {
        return dao.findAssetById(id).map { it?.toAssetItem() }
    }

    override suspend fun updateAmount(id : Long, newAmount : Float){
        dao.updateAmount(UpdateAmountTuple(id, newAmount))
    }

    override suspend fun addNewAsset(asset : AssetItem, amount : Float){
        dao.addAsset(AssetDbEntity.toDbEntity(asset, amount))
    }
}