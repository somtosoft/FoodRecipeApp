package com.example.foodrecipeapp.presentation.ui.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.foodrecipeapp.presentation.components.GenericDialogInfo
import com.example.foodrecipeapp.presentation.components.PositiveAction
import java.util.LinkedList
import java.util.Queue

class DialogQueue {

    // Queue for "First-In-First-Out" behavior
    val queue: MutableState<Queue<GenericDialogInfo>> = mutableStateOf(LinkedList())

    fun removeHeadMessage(){
        if (queue.value.isNotEmpty()) {
            val update = queue.value
            update.remove() // remove first (oldest message)
            queue.value = java.util.ArrayDeque() // force recompose (bug?)
            queue.value = update
        }
    }

    fun appendErrorMessage(title: String, description: String){
        queue.value.offer(
            GenericDialogInfo.Builder()
                .title(title)
                .onDismiss(this::removeHeadMessage)
                .description(description)
                .positiveAction(
                    PositiveAction(
                        positiveBtnTxt = "Ok",
                        onPositiveAction = this::removeHeadMessage,
                    )
                )
                .build()
        )
    }
}