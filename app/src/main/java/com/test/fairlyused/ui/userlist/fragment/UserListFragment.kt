package com.test.fairlyused.ui.userlist.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.test.fairlyused.R
import com.test.fairlyused.di.Injectable
import com.test.fairlyused.ui.userlist.model.response.UserSummary
import com.test.fairlyused.ui.userlist.viewmodel.UserListViewModel
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.test.fairlyused.ui.base.BaseFragment
import com.test.fairlyused.utils.afterTextChanged
import kotlinx.android.synthetic.main.user_list_fragment.*
import javax.inject.Inject


class UserListFragment : BaseFragment(),UserListAdapter.OnItemClickListener {


    private val viewModel by viewModels<UserListViewModel> { viewModelProviderFactory }

    var adapter = UserListAdapter(mutableListOf(),this)

    private  var fullUserList :List<UserSummary> = emptyList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.user_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_items.adapter = adapter

        et_search.afterTextChanged {query ->
            if(!query.isNullOrBlank()){
                val updated = fullUserList.filter { it.lastName.toLowerCase().contains(query.toLowerCase()) }
                adapter.sumbitItems(updated)
            }else{
                adapter.sumbitItems(fullUserList)
            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(viewModel){
            progressEvent.observe(viewLifecycleOwner, Observer {uiEvent ->
                uiEvent.getContentIfNotHandled()?.let {
                    if(it){
                        progress_layout.visibility = View.VISIBLE
                        rv_items.visibility = View.GONE
                    }else{
                        progress_layout.visibility = View.GONE
                        rv_items.visibility = View.VISIBLE
                    }
                }
            })
            errorEvent.observe(viewLifecycleOwner, Observer {uiEvent ->
                uiEvent.getContentIfNotHandled()?.let {
                    showErrorDialog(it,secondaryButton = {
                        viewModel.fetchUserList()
                    })
                }
            })

            userListEvent.observe(viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let {list ->
                    et_search.setText("")
                    adapter.sumbitItems(list)
                    fullUserList = list

                }

            })

        }

    }

    override fun onItemSelected(item: UserSummary) {
        val action =UserListFragmentDirections.actionUserListFragmentToUserFullDetailsFragment(item.id)
        findNavController().navigate(action)
    }


}
