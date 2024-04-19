package com.maksk993.cryptoportfolio.data.models.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.maksk993.cryptoportfolio.data.models.room.entities.AssetDbEntity

@Dao
interface AssetsDbDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAsset(assetDbEntity: AssetDbEntity)

    @Query("SELECT * FROM portfolio_assets")
    fun getAllAssetsFromPortfolio() : MutableList<AssetDbEntity?>
}