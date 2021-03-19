package br.com.exdon.ope

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface AgendamentoDAO {

    @Query("SELECT * FROM agendamento where id=:id")
    fun getById(id: Long): Agendamento?

    @Query("SELECT * FROM agendamento")
    fun findAll(): List<Agendamento>

    @Insert
    fun insert(agendamento: Agendamento)

    @Delete
    fun delete(agendamento: Agendamento)

}