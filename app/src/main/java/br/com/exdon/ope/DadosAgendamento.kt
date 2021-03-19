package br.com.exdon.ope

class DadosAgendamento {

    var id: Int = 0
    var nome_paciente = ""
    var convenio = ""
    var exame = ""
    var unidade = ""
    var data_exame = ""
    var hora_exame = ""
    var observacao = ""

    constructor(nomePaciente: String, convenio: String, exame: String, unidade: String, dataExame: String, horaExame: String, observacao: String) {
        this.nome_paciente = nomePaciente
        this.convenio = convenio
        this.exame = exame
        this.unidade = unidade
        this.data_exame = dataExame
        this.hora_exame = horaExame
        this.observacao = observacao
    }

    constructor() {

    }

}