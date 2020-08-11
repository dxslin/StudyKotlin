package com.slin.study.kotlin.ui.transition

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.constant.ConstantResource
import kotlinx.android.synthetic.main.activity_transition_list.*
import kotlin.random.Random


/**
 * author: slin
 * date: 2020/8/6
 * description:
 *
 */
class TransitionListActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition_list)
        setShowBackButton(true)

        val data = mutableListOf<TransitionData>()
        for (i in 1..20) {
            data.add(
                TransitionData(
                    ConstantResource.IMAGE_RESOURCE[Random.nextInt(
                        0,
                        ConstantResource.IMAGE_RESOURCE.size
                    )],
                    "Title_$i",
                    "this is abstract_$i",
                    getString(R.string.transition_page_detail)
                )
            )
        }

        val adapter = TransitionAdapter()
        rv_list.layoutManager = LinearLayoutManager(this)
        rv_list.adapter = adapter
        rv_list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))

        adapter.setList(data)
    }

    inner class TransitionAdapter :
        BaseQuickAdapter<TransitionData, ViewHolder>(R.layout.item_horizontal_transition) {
        override fun convert(holder: ViewHolder, item: TransitionData) {
            holder.ivPhoto.setImageResource(item.imgRes)
            holder.tvTitle.text = item.title
            holder.tvAbstract.text = item.abstract
            holder.tvDetail.text = item.detail
            holder.itemView.setOnClickListener {
                val p1 = androidx.core.util.Pair(
                    holder.ivPhoto as View,
                    ViewCompat.getTransitionName(holder.ivPhoto)
                )
                val p2 = androidx.core.util.Pair(
                    holder.tvTitle as View,
                    ViewCompat.getTransitionName(holder.tvTitle)
                )
                val p3 = androidx.core.util.Pair(
                    holder.tvAbstract as View,
                    ViewCompat.getTransitionName(holder.tvAbstract)
                )
                val p4 = androidx.core.util.Pair(
                    holder.tvDetail as View,
                    ViewCompat.getTransitionName(holder.tvDetail)
                )
                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@TransitionListActivity,
                    p1,
                    p2,
                    p3,
                    p4
                );
                val intent =
                    Intent(this@TransitionListActivity, TransitionDetailActivity::class.java)
                intent.putExtra(TransitionDetailActivity.INTENT_TRANSITION_DATA, item)
                startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

}


class ViewHolder(view: View) : BaseViewHolder(view) {
    val ivPhoto: ImageView = getView(R.id.iv_photo)
    val tvTitle: TextView = getView(R.id.tv_title)
    val tvAbstract: TextView = getView(R.id.tv_abstract)
    val tvDetail: TextView = getView(R.id.tv_detail)

}

data class TransitionData(
    val imgRes: Int,
    val title: String,
    val abstract: String,
    val detail: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(imgRes)
        parcel.writeString(title)
        parcel.writeString(abstract)
        parcel.writeString(detail)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TransitionData> {
        override fun createFromParcel(parcel: Parcel): TransitionData {
            return TransitionData(parcel)
        }

        override fun newArray(size: Int): Array<TransitionData?> {
            return arrayOfNulls(size)
        }
    }
}
