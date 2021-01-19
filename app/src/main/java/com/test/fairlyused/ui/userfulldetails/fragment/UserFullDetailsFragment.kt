package com.test.fairlyused.ui.userfulldetails.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.test.fairlyused.R
import com.test.fairlyused.ui.base.BaseFragment
import com.test.fairlyused.ui.userfulldetails.model.response.UserDetail
import com.test.fairlyused.ui.userfulldetails.viewmodel.UserFullDetailsViewModel
import kotlinx.android.synthetic.main.full_user_details_fragment.*


class UserFullDetailsFragment : BaseFragment() {

    private val args: UserFullDetailsFragmentArgs by navArgs()

    private val viewModel by viewModels<UserFullDetailsViewModel> { viewModelProviderFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.full_user_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchUserFullDetails(args.userId)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(viewModel){

            userFullDetailsEvent.observe(viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let { user ->
                    updateDetails(user)
                }
            })

            progressEvent.observe(viewLifecycleOwner, Observer { uiEvent ->
                uiEvent.getContentIfNotHandled()?.let {
                    if(it){
                        progress_layout.visibility = View.VISIBLE
                        profile_layout.visibility = View.GONE
                    }else{
                        progress_layout.visibility = View.GONE
                        profile_layout.visibility = View.VISIBLE
                    }
                    
                }
            })

            errorEvent.observe(viewLifecycleOwner, Observer { uiEvent ->
                uiEvent.getContentIfNotHandled()?.let {
                    showErrorDialog(it) {

                    }
                }
            })
        }

    }

    private fun updateDetails(data: UserDetail){
        et_firstname.setText(data.firstName)
        et_last_name.setText(data.lastName)
        et_email.setText(data.email)
        et_phone_number.setText(data.phone)
        et_gender.setText(data.gender)

        Glide.with(requireContext())
            .load(data.picture)
            .into(iv_profile_img)
    }

}
