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

        val likeButton: Button = view.findViewById(R.id.like_button)
        val dislikeButton: Button = view.findViewById(R.id.dislike_button)
        val messageTextView: TextView = view.findViewById(R.id.message_textview)
        val clientTextView: TextView = view.findViewById(R.id.client_textView)

        fun bind(message: Message) {


            likeButton.text = message.likes
            dislikeButton.text = message.dislikes
            messageTextView.text = message.message
            clientTextView.text = message.client


            dislikeButton.setOnClickListener {
                ChitterGitter().dislikeContents(message.id)
                var counter = (dislikeButton.text as String).toInt()
                counter++
                dislikeButton.text = counter.toString()
                dislikeButton.isEnabled = false
            }
            likeButton.setOnClickListener {
                ChitterGitter().likeContents(message.id)
                var counter = (likeButton.text as String).toInt()
                counter++
                likeButton.text = counter.toString()
                likeButton.isEnabled = false
            }
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
            holder.bind(message)

        }

        override fun getItemCount(): Int = messages.size
    }

}