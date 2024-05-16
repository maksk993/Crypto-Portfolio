package com.maksk993.cryptoportfolio.data.repository

import com.maksk993.cryptoportfolio.data.models.room.DatabaseDao
import com.maksk993.cryptoportfolio.data.models.room.entities.AssetDbEntity
import com.maksk993.cryptoportfolio.domain.models.Account
import com.maksk993.cryptoportfolio.domain.models.AssetItem
import com.maksk993.cryptoportfolio.domain.repository.PortfolioRepository
import com.maksk993.cryptoportfolio.domain.models.PortfolioAssetItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PortfolioRepositoryImpl(private val dao : DatabaseDao) : PortfolioRepository {
    override suspend fun addAsset(asset : PortfolioAssetItem){
        dao.addAsset(AssetDbEntity.toDbEntity(asset))
    }

    override suspend fun removeAsset(asset: AssetItem, account: Account) {
        dao.removeAsset(asset.symbol, account.name)
    }

    override suspend fun getAllAssetsFromPortfolio(account: Account) : MutableList<PortfolioAssetItem?> {
        return withContext(Dispatchers.IO) {
            return@withContext dao.getAllAssetsFromPortfolio(account.name)
                .map { it?.toPortfolioItem() }.toMutableList()
        }
    }
}