package com.ubaya.coffeeshoptycoon

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_simulation.view.*
import kotlinx.android.synthetic.main.item_simulation.view.*

class SimulationAdapter (val context: Context, val outcomes: Array<String>): RecyclerView.Adapter<SimulationAdapter.SimulationViewHolder>(){
    class SimulationViewHolder(val v: View): RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimulationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var v = inflater.inflate(R.layout.item_simulation, parent,
            false)
        return SimulationViewHolder(v)    }

    override fun onBindViewHolder(holder: SimulationViewHolder, position: Int) {
        holder.v.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.animation)

        holder.v.txtOutcomes.setText(outcomes[position])
    }

    override fun getItemCount(): Int {
        return outcomes.size
    }


}