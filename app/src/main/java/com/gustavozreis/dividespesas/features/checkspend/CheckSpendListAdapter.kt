package com.gustavozreis.dividespesas.features.checkspend

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gustavozreis.dividespesas.R
import com.gustavozreis.dividespesas.data.spends.models.Spend
import java.text.DecimalFormat

class CheckSpendListAdapter(private val context: Context?, val spendList: MutableList<Spend>)
    : RecyclerView.Adapter <CheckSpendListAdapter.SpendListItemViewHolder>() {

    inner class SpendListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val spendIcon: ImageView = itemView.findViewById(R.id.imageview_spend_icon)
        val userNameAndData: TextView = itemView.findViewById(R.id.textview_user_name_and_date)
        val spendType: TextView = itemView.findViewById(R.id.textview_spend_type)
        val spendValue: TextView = itemView.findViewById(R.id.textview_spend_value)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SpendListItemViewHolder {
        return SpendListItemViewHolder(LayoutInflater.from(context).inflate(R.layout.check_spend_list_item, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SpendListItemViewHolder, position: Int) {
        holder.userNameAndData.text = "${spendList[position].spendUser} | ${spendList[position].spendDate}"
        holder.spendType.text = spendList[position].spendType
        holder.spendValue.text = formatValueToString(spendList[position].spendValue)
        val spendId: String = spendList[position].spendId
    }

    override fun getItemCount(): Int = spendList.size

    private fun formatValueToString(value: Double): String {
        val valueDouble = DecimalFormat("0.00").format(value)
        val valueString = valueDouble.toString()
        val valueStringFinal = "R$ ${valueString.replace(".", ",")}"
        return valueStringFinal
    }

}