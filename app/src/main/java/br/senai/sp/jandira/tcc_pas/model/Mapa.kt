package br.senai.sp.jandira.tcc_pas.model

data class NominatimAddressItem(
    val place_id: Int = 0,
    val lat: String = "",
    val lon: String = "",
    val display_name: String = "",
    val address: NominatimAddress = NominatimAddress() // aqui o retrofit já vai chamar a classe abaixo e preencher ela
)

data class NominatimAddress(
    val postcode: String = "",
    val city: String = "",
    val state: String = "",
    val country: String = ""
)