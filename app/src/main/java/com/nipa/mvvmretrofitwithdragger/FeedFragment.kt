package com.nipa.mvvmretrofitwithdragger

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nipa.mvvmretrofitwithdragger.adaptor.FeedsAdaptor
import com.nipa.mvvmretrofitwithdragger.databinding.FragmentFeedBinding
import com.nipa.mvvmretrofitwithdragger.globle.FeedClickInterface
import com.nipa.mvvmretrofitwithdragger.model.Feeds
import com.nipa.mvvmretrofitwithdragger.model.Resource
import com.nipa.mvvmretrofitwithdragger.viewmodel.FeedsViewModel
import com.nipa.mvvmretrofitwithdragger.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FeedFragment : Fragment() {

    lateinit var dataListAdaptor: FeedsAdaptor
    lateinit var _binding: FragmentFeedBinding
    private val viewModel: FeedsViewModel by viewModels()
    var feedList= ArrayList<Feeds>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.feeds.observe(viewLifecycleOwner, Observer {
            resources -> when(resources){
            is Resource.Error -> {

                loading("",false)
                showToast("Error !"+resources.message)
            }
            is Resource.Loading -> loading("Wait Feed data retriving...",true)
            is Resource.Success -> {
                loading("",false)
               // Log.d("nipaerror",resources.data.toString())
                feedList.clear()
                feedList.addAll(ArrayList(resources.data))
                if(feedList.size>0){
                    refreshAdaptor()
                }
            }
        }
        })

    }

    fun refreshAdaptor() {
        if (::dataListAdaptor.isInitialized) {
            dataListAdaptor.notifyDataSetChanged()


        } else {
            dataListAdaptor=FeedsAdaptor(requireContext(),feedList,object :FeedClickInterface{
                override fun getFeed(aPos: Int) {
                    var feeds:Feeds=feedList.get(aPos)
                    if( feeds.liked){
                        feeds.liked=false
                    }else{
                        feeds.liked=false
                    }
                    feedList.removeAt(aPos)
                    feedList.add(aPos,feeds)
                    refreshAdaptor()
                }
            })
            val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, true)
            //layoutManager.stackFromEnd = true
            _binding.recyclerView.layoutManager = layoutManager
            _binding.recyclerView.itemAnimator = DefaultItemAnimator()
            _binding.recyclerView.adapter = dataListAdaptor
        }

    }

    fun loading(message: String, isshow: Boolean) {

        _binding.includeLoading.llLoader.isVisible = isshow
        _binding.includeLoading.tvProgress.text = message

    }
    private fun showToast(message : String) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, message, Toast.LENGTH_LONG).show()
    }
}