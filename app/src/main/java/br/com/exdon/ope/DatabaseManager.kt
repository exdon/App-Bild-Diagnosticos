package br.com.exdon.ope

import androidx.room.Room

object DatabaseManager {

    private var dbInstance: LMSDatabase

    init {
        val contexto = LMSApplication.getInstance().applicationContext
        dbInstance = Room.databaseBuilder(
            contexto,
            LMSDatabase::class.java,
            "ope.sqllite"
        ).build()
    }

    fun getAgendamentoDAO(): AgendamentoDAO{
        return dbInstance.agendamentoDAO()
    }
}