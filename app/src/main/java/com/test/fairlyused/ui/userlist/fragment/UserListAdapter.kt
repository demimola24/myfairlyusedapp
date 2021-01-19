package com.test.fairlyused.ui.userlist.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.fairlyused.R
import com.test.fairlyused.ui.userlist.model.response.UserSummary
import kotlinx.android.synthetic.main.user_list_item.view.*

class UserListAdapter(private var bankList : MutableList<UserSummary>, private  var delegate : OnItemClickListener): RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = bankList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(bankList[position],position)
        holder.bindSelectedBank(bankList[position],delegate)
    }

    fun sumbitItems(filteredList: List<UserSummary>) {
        bankList = mutableListOf()
        bankList.addAll(filteredList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        
        fun bindView(userSummary: UserSummary, position: Int) {
            val randomNumber: Int = position%7

            itemView.tv_item_name.text = userSummary.lastName
            itemView.iv_icon.text = userSummary.title
        }

        fun bindSelectedBank(position: UserSummary, delegate: OnItemClickListener?) {
            itemView.setOnClickListener {
                delegate?.onItemSelected(position)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemSelected(item: UserSummary)
    }
}
