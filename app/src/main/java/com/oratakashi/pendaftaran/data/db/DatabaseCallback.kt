package com.oratakashi.pendaftaran.data.db

sealed class DatabaseCallback {
    object Insert : DatabaseCallback()
    object Delete : DatabaseCallback()
}