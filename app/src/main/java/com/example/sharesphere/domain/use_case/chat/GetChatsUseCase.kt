package com.example.sharesphere.domain.use_case.chat

import com.example.sharesphere.domain.repository.ChatRepositoryInterface
import javax.inject.Inject

class GetChatsUseCase @Inject constructor(
    private val chatRepository: ChatRepositoryInterface
) {
    suspend operator fun invoke()= chatRepository.getChats()
}