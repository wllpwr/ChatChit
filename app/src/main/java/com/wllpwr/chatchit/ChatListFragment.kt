package com.wllpwr.chatchit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


const val TAG = "ChatListFragment"
const val API_KEY = "c16e71b1-377d-4bf2-bf06-65a18198fe58"
const val EMAIL = "william.gesler@mymail.champlain.edu"

class ChatListFragment : Fragment() {

    private lateinit var chatRecyclerView: RecyclerView
    private var adapter: ChatAdapter? = null

    private val chatListViewModel: ChatListViewModel by lazy {
        ViewModelProviders.of(this).get(ChatListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ChatListFragment", "We got ${chatListViewModel.messages.size} messages.")
        val chatLiveData: LiveData<List<Message>> = ChitterGitter().fetchContents()
        chatLiveData.observe(
            this,
            { messages ->
                Log.d(TAG, "$messages")
            }

        )
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

        updateUI()
        return view
    }
    private fun updateUI(){
        val messages = chatListViewModel.messages
        adapter = ChatAdapter(messages)
        chatRecyclerView.adapter = adapter
    }

    companion object {
        fun newInstance(): ChatListFragment{
            return ChatListFragment()
        }
    }

    private inner class ChatHolder(view: View) : RecyclerView.ViewHolder(view){
        val idTextView: TextView = itemView.findViewById(R.id.id_box)

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
            holder.apply {
                idTextView.text = message.messageNo.toString()
            }
        }

        override fun getItemCount() = messages.size
    }

}