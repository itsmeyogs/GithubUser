package com.dicoding.githubuser.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_user")
class FavUser(
    @PrimaryKey(autoGenerate = false)
    var username: String= "",
    var avatarUrl: String? =""
)