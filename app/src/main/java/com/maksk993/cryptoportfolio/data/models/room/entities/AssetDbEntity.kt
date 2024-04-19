package com.maksk993.cryptoportfolio.data.models.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.maksk993.cryptoportfolio.data.repository.CryptoRepositoryImpl
import com.maksk993.cryptoportfolio.domain.models.AssetItem
import com.maksk993.cryptoportfolio.domain.models.PortfolioAssetItem

@Entity(
    tableName = "portfolio_assets",
    indices = [
        Index("symbol", unique = true)
    ]
)
data class AssetDbEntity(
    @PrimaryKey(autoGenerate = true) val id : Long,
    @ColumnInfo(collate = ColumnInfo.NOCASE) val symbol : String,
    val price : Float,
    val amount : Float
) {
    fun toPortfolioItem() : PortfolioAssetItem =
        PortfolioAssetItem(symbol, CryptoRepositoryImpl.actualPrices[symbol] ?: 0F, amount)

    companion object {
        fun toDbEntity(item : AssetItem, _amount: Float) : AssetDbEntity = AssetDbEntity(
            id = 0,
            symbol = item.symbol,
            price = item.price,
            amount = _amount
        )
    }
}
