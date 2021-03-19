package br.com.exdon.ope

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Agendamento::class), version = 1) //se eu tiver outra Entidade class basta colocar (Agendamento::class, OutraClass::class,....)
abstract class LMSDatabase: RoomDatabase() {
    abstract fun agendamentoDAO(): AgendamentoDAO
    // se existir outra entidade basta colocar aqui  abstract fun outraClass(): OutraClass

}