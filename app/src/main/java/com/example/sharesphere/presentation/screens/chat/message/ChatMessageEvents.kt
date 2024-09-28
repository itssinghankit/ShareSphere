package com.example.sharesphere.presentation.screens.chat.message

sealed class ChatMessageEvents {
    object ResetErrorMessage:ChatMessageEvents()
    data class OnMessageValueChanged(val message:String):ChatMessageEvents()
    object OnMessageSendButtonClicked:ChatMessageEvents()
}