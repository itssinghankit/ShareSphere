package com.example.sharesphere.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.sharesphere.data.remote.dto.chat.chatMessage.sendMsg.SendMsgData
import com.example.sharesphere.data.remote.dto.chat.chatMessage.sendMsg.SendMsgResDto
import com.example.sharesphere.domain.model.chat.chatMessages.GetAllMessagesModel
import formatDateTimeRelative

@RequiresApi(Build.VERSION_CODES.O)
fun SendMsgResDto.toGetAllMessagesModel(userId:String): GetAllMessagesModel {
    return this.data.run {
        GetAllMessagesModel(
           _id= _id,
            attachments = attachments,
            content = content,
            seenBy = seenBy,
            updatedAt = formatDateTimeRelative(updatedAt),
            senderId = sender._id,
            senderAvatar = sender.avatar,
            fullName = sender.fullName,
            isOwner = sender._id == userId,
            chatId = chat
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun SendMsgData.toGetAllMessagesModel(userId:String): GetAllMessagesModel {
    return this.run {
        GetAllMessagesModel(
            _id= _id,
            attachments = attachments,
            content = content,
            seenBy = seenBy,
            updatedAt = formatDateTimeRelative(updatedAt),
            senderId = sender._id,
            senderAvatar = sender.avatar,
            fullName = sender.fullName,
            isOwner = sender._id == userId,
            chatId =chat
        )
    }
}