package com.chernybro.loftcoin.data.local.dao

import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.lifecycle.LiveData
import androidx.room.Insert
import com.chernybro.loftcoin.data.local.entities.RoomCoin
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
abstract class CoinsDao {

    @Query("SELECT * FROM RoomCoin")
    abstract fun fetchAll(): LiveData<List<RoomCoin>>

    @Query("SELECT * FROM RoomCoin ORDER BY price DESC")
    abstract fun fetchAllSortByPrice(): LiveData<List<RoomCoin>>

    @Query("SELECT * FROM RoomCoin ORDER BY rank ASC")
    abstract fun fetchAllSortByRank(): LiveData<List<RoomCoin>>

    @WorkerThread
    @Query("SELECT COUNT(id) FROM RoomCoin")
    abstract fun coinsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(coins: List<RoomCoin>)
}