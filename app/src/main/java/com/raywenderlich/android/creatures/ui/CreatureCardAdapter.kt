package com.raywenderlich.android.creatures.ui

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.android.creatures.R
import com.raywenderlich.android.creatures.app.inflate
import com.raywenderlich.android.creatures.model.Creature
import kotlinx.android.synthetic.main.list_item_creature.view.*
import kotlinx.android.synthetic.main.list_item_creature.view.creatureImage
import kotlinx.android.synthetic.main.list_item_creature.view.nickname
import kotlinx.android.synthetic.main.list_item_creature_card.view.*

class CreatureCardAdapter(private val creatures: MutableList<Creature>): RecyclerView.Adapter<CreatureCardAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : View.OnClickListener, RecyclerView.ViewHolder(itemView) {
        private lateinit var creature: Creature

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(creature: Creature) {
            this.creature = creature
            val context = itemView.context
            val imageResource =
            itemView.creatureImage.setImageResource(
                    context.resources.getIdentifier(creature.uri, null, context.packageName))
            itemView.nickname.text = creature.nickname
        }

        override fun onClick(view: View?) {
            view?.let {
                val context = it.context
                val intent = CreatureActivity.newIntent(context, creature.id)
                context.startActivity(intent)
            }
        }

       private fun setBackgroundColors(context: Context, imageResources: Int) {
           val image = BitmapFactory.decodeResource(context.resources, imageResources)
           Palette.from(image).generate { palette ->
               palette?.let {
                val backgroundColor = it.getDominantColor(ContextCompat.getColor(context, R.color.colorPrimary))
                   itemView.creatureCard.setBackgroundColor(backgroundColor)
                   itemView.nicknameHolder.setBackgroundColor(backgroundColor)
                   val textColor = if (isColorDark(backgroundColor)) Color.WHITE else Color.BLACK
                   itemView.nickname.setTextColor(textColor)
               }
           }


       }

        private fun isColorDark(color: Int): Boolean {
            val darkness = 1 - (0.299 * Color.red(color) +
                                0.587 * Color.green(color) +
                                0.114 * Color.blue(color)) / 255
            return darkness >= 0.5
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.list_item_creature_card))
    }

    override fun getItemCount() = creatures.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(creatures[position])
    }

}