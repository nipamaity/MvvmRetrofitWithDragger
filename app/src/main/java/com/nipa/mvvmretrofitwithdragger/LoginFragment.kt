package com.nipa.mvvmretrofitwithdragger

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.nipa.mvvmretrofitwithdragger.databinding.FragmentLoginBinding
import com.nipa.mvvmretrofitwithdragger.globle.Globle.Companion.getPreferenceBoolean
import com.nipa.mvvmretrofitwithdragger.globle.Globle.Companion.getPreferenceString
import com.nipa.mvvmretrofitwithdragger.globle.Globle.Companion.isUserNameValid
import com.nipa.mvvmretrofitwithdragger.model.Resource
import com.nipa.mvvmretrofitwithdragger.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    lateinit var _binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)


        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        _binding.btnLogin.setOnClickListener { view -> validateLoginInfo() }

        viewModel.login.observe(viewLifecycleOwner, Observer { resources -> when(resources){
            is Resource.Error -> {

                loading("",false)
                showToast("Error !"+resources.message)
            }
            is Resource.Loading -> loading("Wait ...",true)
            is Resource.Success -> {
                loading("",false)
                showToast("Login Success")
                findNavController().navigate(R.id.action_loginFragment_to_feedFragment)
            }
        } })

    }
    private fun setData(){
        if(getPreferenceBoolean(requireContext(),"remember")){
          val userid=  getPreferenceString(requireContext(),"userId")
            val pass=  getPreferenceString(requireContext(),"password")
            _binding.edEmail.setText(userid)
            _binding.edPass.setText(pass)
            _binding.checkMe.isChecked=true

        }
    }

    fun validateLoginInfo(){
        if(!isUserNameValid(_binding.edEmail.text.toString())){
            _binding.edEmail.setError("Email Address not valid")
            _binding.edEmail.requestFocus()
            return
        }
        if( _binding.edPass.text.toString().isBlank()){
            _binding.edPass.setError("Password not blank")
            _binding.edPass.requestFocus()
            return
        }
         viewModel.login(_binding.edEmail.text.toString(),_binding.edPass.text.toString(),_binding.checkMe.isChecked)

    }

    private fun showToast(message : String) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, message, Toast.LENGTH_LONG).show()
    }
    fun loading(message: String, isshow: Boolean) {

        _binding.includeLoading.llLoader.isVisible = isshow
        _binding.includeLoading.tvProgress.text = message

    }
}