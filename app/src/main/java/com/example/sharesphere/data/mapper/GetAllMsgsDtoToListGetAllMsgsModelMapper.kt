package com.example.sharesphere.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.sharesphere.data.remote.dto.chat.chatMessage.getAllMsgs.GetAllMsgsDto
import com.example.sharesphere.domain.model.chat.chatMessages.GetAllMessagesModel
import formatDateTimeRelative

@RequiresApi(Build.VERSION_CODES.O)
fun GetAllMsgsDto.toListGetAllMsgsModel(
    myUserId: String
): List<GetAllMessagesModel> {
    return this.data.map { item ->
        GetAllMessagesModel(
            _id = item._id,
            attachments = item.attachments,
            content = item.content,
            seenBy = item.seenBy,
            updatedAt = formatDateTimeRelative(item.updatedAt),
            senderId = item.sender._id,
            senderAvatar = item.sender.avatar,
            fullName = item.sender.fullName,
            isOwner = item.sender._id == myUserId,
            chatId = item.chat
        )
    }


}