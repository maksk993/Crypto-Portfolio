package com.maksk993.cryptoportfolio.data.models.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.maksk993.cryptoportfolio.data.repository.CryptoRepositoryImpl
import com.maksk993.cryptoportfolio.domain.models.Account
import com.maksk993.cryptoportfolio.domain.models.AssetItem
import com.maksk993.cryptoportfolio.domain.models.PortfolioAssetItem

@Entity(
    tableName = "portfolio_assets",
    indices = [Index(value = ["symbol", "accountName"], unique = true)]
)
data class AssetDbEntity(
    @PrimaryKey(autoGenerate = true) val id : Long,
    @ColumnInfo(collate = ColumnInfo.NOCASE) val symbol : String,
    val price : Float,
    val amount : Float,
    val accountName: String
) {
    fun toPortfolioItem() : PortfolioAssetItem =
        PortfolioAssetItem(
            symbol = symbol,
            price = CryptoRepositoryImpl.actualPrices[symbol] ?: -1F,
            amount = amount,
            account = Account(accountName)
        )

    companion object {
        fun toDbEntity(item : PortfolioAssetItem) : AssetDbEntity = AssetDbEntity(
            id = 0,
            symbol = item.symbol,
            price = item.price,
            amount = item.amount,
            accountName = item.account.name
        )
    }
}
