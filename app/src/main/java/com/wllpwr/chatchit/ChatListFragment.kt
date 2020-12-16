package com.wllpwr.chatchit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


const val TAG = "ChatListFragment"

class ChatListFragment : Fragment() {

    private lateinit var chatListViewModel: ChatListViewModel
    private lateinit var chatRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatListViewModel =
                ViewModelProviders.of(this).get(ChatListViewModel::class.java)
        chatListViewModel.postContents("LARGE TEST MESSAGE")
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat_list, container, false)
        chatRecyclerView =
                view.findViewById(R.id.chat_recycler_view) as RecyclerView
        chatRecyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chatListViewModel.messageLiveData.observe(
                viewLifecycleOwner,
                { messages ->
                    Log.d(TAG, "$messages")
                    chatRecyclerView.adapter = ChatAdapter(messages)
                }
        )
    }

    companion object {
        fun newInstance(): ChatListFragment {
            return ChatListFragment()
        }
    }

    private inner class ChatHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idTextView: TextView = itemView.findViewById(R.id.id_box)
        val likeButton: Button = itemView.findViewById(R.id.like_button)
        val dislikeButton: Button = itemView.findViewById(R.id.dislike_button)
        val messageTextView: TextView = itemView.findViewById(R.id.message_textview)
        val clientTextView: TextView = itemView.findViewById(R.id.client_textView)

        // this could be condensed
        val bindId: (CharSequence) -> Unit = idTextView::setText
        val bindLikes: (CharSequence) -> Unit = likeButton::setText
        val bindDislikes: (CharSequence) -> Unit = dislikeButton::setText
        val bindMessage: (CharSequence) -> Unit = messageTextView::setText
        val bindClient: (CharSequence) -> Unit = clientTextView::setText


        override fun onClick(p0: View?) {
            chatListViewModel.likeContents()
        }
    }

    private inner class ChatAdapter(var messages: List<Message>) :
            RecyclerView.Adapter<ChatHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
            val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.list_item_message,
                    parent,
                    false
            )
            return ChatHolder(view)
        }

        override fun onBindViewHolder(holder: ChatHolder, position: Int) {
            val message = messages[position]
            holder.bindId(message.id)
            holder.bindMessage(message.message)
            holder.bindLikes(message.likes)
            holder.bindDislikes(message.dislikes)
            holder.bindClient(message.client)
        }

        override fun getItemCount(): Int = messages.size
    }

}