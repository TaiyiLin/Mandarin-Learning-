package com.taiyilin.mandarinlearning.teacherClassroomDetail

import kotlinx.coroutines.CoroutineScope
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.taiyilin.mandarinlearning.data.Message
import com.taiyilin.mandarinlearning.databinding.ItemClassroomMessageLeftBinding
import com.taiyilin.mandarinlearning.databinding.ItemClassroomMessageRightBinding
import com.taiyilin.mandarinlearning.login.UserManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException


private const val MSG_FROM_SENDER = 0
private const val MSG_FROM_RECEIVER = 1

class TeacherCLRDetailAdapter : ListAdapter<DataItem, RecyclerView.ViewHolder>(DiffCallback) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MSG_FROM_SENDER -> {
                SenderLeftViewHolder.from(parent)
            }
            MSG_FROM_RECEIVER -> {
                TeacherCLRDetailAdapter.ReceiverRightViewHolder.from(parent)
            }
            else -> throw ClassCastException("Unknown ViewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TeacherCLRDetailAdapter.SenderLeftViewHolder -> {
                val msgItem = getItem(position) as DataItem.SenderMsg
                holder.bind(msgItem.message)
            }
            is TeacherCLRDetailAdapter.ReceiverRightViewHolder -> {
                val msgItem = getItem(position) as DataItem.ReceiverMsg
                holder.bind(msgItem.message)
            }
        }
    }


    //00 建viewHolder
    class SenderLeftViewHolder(val binding: ItemClassroomMessageLeftBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(msg: Message) {
            binding.msgLayout = msg
        }

        companion object {
            fun from(parent: ViewGroup): SenderLeftViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemClassroomMessageLeftBinding.inflate(layoutInflater, parent, false)

                return SenderLeftViewHolder(binding)
            }
        }
    }

    class ReceiverRightViewHolder(val binding: ItemClassroomMessageRightBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(msg: Message) {
            binding.msgLayout = msg
        }

        companion object {
            fun from(parent: ViewGroup): ReceiverRightViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemClassroomMessageRightBinding.inflate(layoutInflater, parent, false)

                return ReceiverRightViewHolder(binding)
            }
        }
    }

    //02 舊的類別轉成新的有標籤的類別 分類
    //(list)是舊的list
    fun separateMsgSubmitList(list: List<Message>) {
        adapterScope.launch {
            //itemList是新的List
            val itemList = mutableListOf<DataItem>()
            list.let {
                for (msg in it) {
                    if (msg.senderId == UserManager.userUID) {
                        itemList.add(DataItem.SenderMsg(msg))
                    } else {
                        itemList.add(DataItem.ReceiverMsg(msg))
                    }
                }

            }

            withContext(Dispatchers.Main) {
                submitList(itemList)
            }
        }
    }


    //03 標籤對應viewType
    override fun getItemViewType(position: Int): Int {

        return when (getItem(position)) {
            is DataItem.SenderMsg -> MSG_FROM_SENDER
            else -> MSG_FROM_RECEIVER
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem === newItem
        }
        //TODO
        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

}

sealed class DataItem {

    data class SenderMsg(val message: Message) :
        DataItem() {
        override var id: String = message.senderId
    }

    data class ReceiverMsg(val message: Message) :
       DataItem() {
        override var id: String = message.receiverId
    }

    abstract var id: String
}