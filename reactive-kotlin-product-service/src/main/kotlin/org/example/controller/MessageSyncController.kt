package org.example.controller

import org.example.service.MessageSyncService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product-sync")
class MessageSyncController(private val messageSyncService: MessageSyncService) {

    @PostMapping("/{id}")
    suspend fun postMessage(@PathVariable id: Long): Long {
        messageSyncService.sendMessage(id)
        return id
    }
}