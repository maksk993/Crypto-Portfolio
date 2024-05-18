package com.maksk993.cryptoportfolio.data.models.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.maksk993.cryptoportfolio.domain.models.Account
import com.maksk993.cryptoportfolio.domain.models.Asset

@Entity(
    tableName = "portfolio_assets",
    indices = [Index(value = ["symbol", "accountName"], unique = true)]
)
data class AssetDbEntity(
    @PrimaryKey(autoGenerate = true) val id : Long,
    @ColumnInfo(collate = ColumnInfo.NOCASE) val symbol : String,
    val amount : Float,
    val accountName: String
) {
    fun toPortfolioItem() : Asset =
        Asset(
            symbol = symbol,
            amount = amount,
            account = Account(accountName)
        )

    companion object {
        fun toDbEntity(item : Asset) : AssetDbEntity = AssetDbEntity(
            id = 0,
            symbol = item.symbol,
            amount = item.amount,
            accountName = item.account?.name ?: ""
        )
    }
}
