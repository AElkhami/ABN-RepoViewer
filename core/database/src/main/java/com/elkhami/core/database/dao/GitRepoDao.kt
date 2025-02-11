package com.elkhami.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elkhami.core.database.entity.GitRepoEntity

@Dao
interface GitRepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repoList: List<GitRepoEntity>)

    @Query("SELECT COUNT(*) FROM git_repo")
    suspend fun count(): Int

    @Query("SELECT * FROM git_repo")
    fun getGitRepoList(): PagingSource<Int, GitRepoEntity>

    @Query("DELETE FROM git_repo")
    suspend fun clearAll()
}