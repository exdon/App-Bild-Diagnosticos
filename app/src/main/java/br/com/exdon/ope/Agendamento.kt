package br.com.exdon.ope

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "agendamento")
class Agendamento : Serializable{

    @PrimaryKey
    var id:Long = 0
    var nomePaciente = ""
    var convenio = ""
    var exame = ""
    var unidade = ""
    var dataExame = ""
    var observacao = ""

    override fun toString(): String {
        return "Agendamento(nomePaciente='$nomePaciente')"
    }

}