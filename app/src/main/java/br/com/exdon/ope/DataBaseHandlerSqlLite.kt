package br.com.exdon.ope

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.lang.RuntimeException

val DATABASE_NAME = "Bild_DB"
val TABLE_NAME = "tb_agendamento"
val COL_NOMEPACIENTE = "nome_paciente"
val COL_CONVENIO = "convenio"
val COL_EXAME = "exame"
val COL_UNIDADE = "unidade"
val COL_DATAEXAME = "data_exame"
val COL_HORAEXAME = "hora_exame"
val COL_OBSERVACAO = "observacao"
val COL_ID = "id_agendamento"

class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) { //context, DataBase Name, CursorFactory and DataBase Version
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE" + " " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NOMEPACIENTE + " VARCHAR(256)," +
                COL_CONVENIO + " VARCHAR(100)," +
                COL_EXAME + " VARCHAR(100)," +
                COL_UNIDADE + " VARCHAR(100)," +
                COL_DATAEXAME + " DATE," +
                COL_HORAEXAME + " TIME," +
                COL_OBSERVACAO + " VARCHAR(256) )"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(dadosAgendamento : DadosAgendamento) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_NOMEPACIENTE, dadosAgendamento.nome_paciente)
        cv.put(COL_CONVENIO, dadosAgendamento.convenio)
        cv.put(COL_EXAME, dadosAgendamento.exame)
        cv.put(COL_UNIDADE, dadosAgendamento.unidade)
        cv.put(COL_DATAEXAME, dadosAgendamento.data_exame)
        cv.put(COL_HORAEXAME, dadosAgendamento.hora_exame)
        cv.put(COL_OBSERVACAO, dadosAgendamento.observacao)
        var result = db.insert(TABLE_NAME, null, cv)
        if (result == -1.toLong())
            Toast.makeText(context, "Falha no Processo!", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Carregando Dados...", Toast.LENGTH_SHORT).show()
    }

    fun readData() : MutableList<DadosAgendamento>{
        var list : MutableList<DadosAgendamento> = ArrayList()

        val db = this.readableDatabase
        val query = "SELECT * FROM " + TABLE_NAME
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                var dadosAgendamento = DadosAgendamento()
                dadosAgendamento.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                dadosAgendamento.nome_paciente = result.getString(result.getColumnIndex(COL_NOMEPACIENTE))
                dadosAgendamento.convenio = result.getString(result.getColumnIndex(COL_CONVENIO))
                dadosAgendamento.exame = result.getString(result.getColumnIndex(COL_EXAME))
                dadosAgendamento.unidade = result.getString(result.getColumnIndex(COL_UNIDADE))
                dadosAgendamento.data_exame = result.getString(result.getColumnIndex(COL_DATAEXAME))
                dadosAgendamento.hora_exame = result.getString(result.getColumnIndex(COL_HORAEXAME))
                dadosAgendamento.observacao = result.getString(result.getColumnIndex(COL_OBSERVACAO))
                list.add(dadosAgendamento)
            } while (result.moveToNext())
        }

        result.close()
        db.close()

        return list
    }

    fun deleteData(id: Int) {
        val db = this.writableDatabase

        db.delete(TABLE_NAME, COL_ID+"=?", arrayOf(id.toString()))

        db.close()
    }

    fun updateSqlite(id: Int, convenio: String = "", exame: String = "", unidade: String = "", dataExame: String ="", horaExame: String = "", obs: String = "") {
        val db = this.writableDatabase
        var cv = ContentValues().apply {
            put(COL_CONVENIO, convenio)
            put(COL_EXAME, exame)
            put(COL_UNIDADE, unidade)
            put(COL_DATAEXAME, dataExame)
            put(COL_HORAEXAME, horaExame)
            put(COL_OBSERVACAO, obs)

        }
        db.update(TABLE_NAME, cv, COL_ID + "=${id}", arrayOf())
    }

    fun updateData() {
        var list : MutableList<DadosAgendamento> = ArrayList()

        val db = this.writableDatabase
        val query = "SELECT * FROM " + TABLE_NAME
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                var cv = ContentValues()
                cv.put(COL_DATAEXAME, result.getString(result.getColumnIndex(COL_DATAEXAME)) +1)
                cv.put(COL_HORAEXAME, result.getString(result.getColumnIndex(COL_HORAEXAME)) +1)
                db.update(TABLE_NAME, cv, COL_ID + "=? AND " + COL_NOMEPACIENTE + "=?",
                    arrayOf(result.getString(result.getColumnIndex(COL_ID)),
                        result.getString(result.getColumnIndex(COL_NOMEPACIENTE)),
                        result.getString(result.getColumnIndex(COL_EXAME)),
                        result.getString(result.getColumnIndex(COL_UNIDADE)),
                        result.getString(result.getColumnIndex(COL_CONVENIO)),
                        result.getString(result.getColumnIndex(COL_DATAEXAME)),
                        result.getString(result.getColumnIndex(COL_HORAEXAME)),
                        result.getString(result.getColumnIndex(COL_OBSERVACAO))))
            } while (result.moveToNext())
        }

        result.close()
        db.close()
    }

}