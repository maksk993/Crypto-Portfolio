package com.maksk993.cryptoportfolio.data.models.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.maksk993.cryptoportfolio.data.models.room.entities.AssetDbEntity
import com.maksk993.cryptoportfolio.data.models.room.entities.UpdateAmountTuple
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetsDbDao {
    @Update(entity = AssetDbEntity::class)
    suspend fun updateAmount(updateAmountTuple : UpdateAmountTuple)

    @Insert
    suspend fun addAsset(assetDbEntity: AssetDbEntity)

    @Query("SELECT * FROM portfolio_assets WHERE id = :id")
    fun findAssetById(id : Long) : Flow<AssetDbEntity?>
}