package org.example.service

interface MessageSyncService {
    suspend fun sendMessage(id: Long)
}