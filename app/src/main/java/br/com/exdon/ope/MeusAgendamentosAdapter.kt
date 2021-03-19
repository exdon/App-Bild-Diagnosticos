package br.com.exdon.ope

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

// define o construtor que recebe a lista de agendamentos e eo evento de clique
class MeusAgendamentosAdapter (
    val agendamento: List<Agendamento>,
    val onClick: (Agendamento) -> Unit): RecyclerView.Adapter<MeusAgendamentosAdapter.MeusAgendamentosViewHolder>() {

    // ViewHolder com os elementos da tela
    class MeusAgendamentosViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardTitle: TextView
        val cardNomePaciente: TextView
        val cardConvenio: TextView
        var cardUnidade: TextView
        val cardDataExame: TextView
        val observacao: TextView

        init {
            cardTitle = view.findViewById(R.id.card_title)
            cardNomePaciente = view.findViewById(R.id.card_nomePaciente)
            cardConvenio = view.findViewById(R.id.card_convenio)
            cardUnidade = view.findViewById(R.id.card_unidade)
            cardDataExame = view.findViewById(R.id.card_dataExame)
            observacao = view.findViewById(R.id.card_observacao)
        }

    }

    // Quantidade de disciplinas na lista
    override fun getItemCount() = this.agendamento.size

    // Inflar layout do adapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeusAgendamentosViewHolder {
        // Infla view no adapter
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_meus_exames, parent, false)
        // retornar ViewHolder
        val holder = MeusAgendamentosViewHolder(view)
        return holder
    }

    // bind para atualizar Views com os dados
    override fun onBindViewHolder(holder: MeusAgendamentosViewHolder, position: Int) {
        val context = holder.itemView.context

        // recuperar objeto disciplina
        val agendamento = agendamento[position]

        // atualizar dados de disciplina

        holder.cardTitle.text = agendamento.exame

        // adiciona evento de clique
        holder.itemView.setOnClickListener { onClick(agendamento) }
    }
}