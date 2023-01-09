package br.digitalhouse.pokedex.ui.dashboard.model

data class Notify(
    val list : List<Itens>? = null
)

data class Itens(
    var title: String? = null,
    var description: String? = null,
    var date: String? = null
)
