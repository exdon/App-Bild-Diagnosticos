package br.com.exdon.ope

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row.view.*

class MyAdapterExame (val arrayList: ArrayList<Model>, val context: Context) :
RecyclerView.Adapter<MyAdapterExame.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(model: Model) {
            itemView.titleTv.text = model.title
            itemView.descriptionTv.text = model.desc
            itemView.imageIv.setImageResource(model.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(arrayList[position])

        holder.itemView.setOnClickListener {
            /*if (position == 0) {
                Toast.makeText(context, "Você clicou em Raio-x", Toast.LENGTH_SHORT).show()
            }
            if (position == 1) {
                Toast.makeText(context, "Você clicou em Ultrassonografia", Toast.LENGTH_SHORT).show()
            }
            if (position == 2) {
                Toast.makeText(context, "Você clicou em Tomografia Multislice", Toast.LENGTH_SHORT).show()
            }*/

            // get position of selected item
            val model = arrayList.get(position)
            // get title and description of selected item with  intent
            var gTitle: String = model.title
            var gDescription: String = model.desc
            // get image with intent, which position is selected
            var gImageView: Int = model.image

            // create intent in kotlin
            val intent = Intent(context, ExameDetailsActivity::class.java)
            // now put all these items with putExtra intent
            intent.putExtra("iTitle", gTitle)
            intent.putExtra("iDescription", gDescription)
            intent.putExtra("iImageView", gImageView)
            // start another activity
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}