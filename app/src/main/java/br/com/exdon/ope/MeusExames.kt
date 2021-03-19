package br.com.exdon.ope

class MeusExames {

    var id: Long = 0
    var titulo = ""
    var descricao = ""
    var imagem = ""

    override  fun toString() : String {
        return "Exame(nome=$titulo)"
    }

}