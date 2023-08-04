package com.nipa.mvvmretrofitwithdragger.adaptor

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.nipa.mvvmretrofitwithdragger.databinding.AdaptorFeedBinding
import com.nipa.mvvmretrofitwithdragger.globle.FeedClickInterface
import com.nipa.mvvmretrofitwithdragger.model.Feeds
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import javax.inject.Inject

class FeedsAdaptor (val context: Context, var feedList: List<Feeds>,var onFeedClickListener: FeedClickInterface): RecyclerView.Adapter<FeedsAdaptor.MyViewHolder>() {
    inner class MyViewHolder(val binding: AdaptorFeedBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedsAdaptor.MyViewHolder {
        val binding = AdaptorFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedsAdaptor.MyViewHolder, position: Int) {

        holder.binding.tvDes.text=feedList.get(position).title+
        holder.binding.tvDes.setOnClickListener {
            youLiked(position)
        }

       /* Picasso.get().load(feedList.get(position).thumbnailUrl).into(holder.binding.ivImage, object :
            Callback {
            override fun onSuccess() {
                holder.binding.progressBar.visibility = ProgressBar.GONE
            }

            override fun onError(e: Exception?) {
                holder.binding.progressBar.visibility = ProgressBar.GONE
                // Handle error if image loading fails
            }
        })*/
        /*if(feedList.get(position).liked){
            holder.binding.tvLiked.text=" You Like"
            holder.binding.tvLiked.setTextColor(Color.BLUE)
        }else{
            holder.binding.tvLiked.text="Like"
            holder.binding.tvLiked.setTextColor(Color.BLACK)
        }*/
       /* holder.binding.tvLiked.setOnClickListener {
            if(onFeedClickListener!=null){
                onFeedClickListener.getFeed(position)
            }
        }*/

    }
    fun youLiked(aPos:Int){

        feedList.get(aPos).liked=true
        notifyDataSetChanged()

        AlertDialog.Builder(context)
            .setTitle("Like it")
            .setMessage("A Pdf invoice is generated. saved path :")
            .setPositiveButton(android.R.string.yes) { _, _ ->  }
            .setNegativeButton(android.R.string.no) { _, _ ->  }
            .show()

    }
    override fun getItemId(position: Int):Long{
      return  feedList.get(position).id.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return feedList.size
    }
}