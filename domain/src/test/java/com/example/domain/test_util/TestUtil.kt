package com.example.domain.test_util

import com.example.data.model.Data

object TestUtil {
    fun previewNewsData(uuid: String = "1") = Data(
        listOf("A", "B"),
        "A primeira-ministra de Barbados mostrou-se confiante de que dentro de alguns meses seja possível concretizar \\\"o sonho\\\" de ter ligações aéreas diretas entre ...",
        "https://media-manager.noticiasaominuto.com/1280/naom_6312f6d20c8aa.jpg",
        "general",
        "en",
        "2022-09-03T07:41:34.000000Z",
        "Análise: Demorámos a adaptar-nos ao jogo do Sporting. Devíamos ter feito um pouco mais daquilo que fizemos na segunda parte, apertar um pouco mais com o Spor...",
        "noticiasaominuto.com",
        "Primeira-ministra de Barbados confiante em ligações diretas para África",
        "www.v3ihf3498fhvbieqvb.com/waf3io4qjf40jv43/3fq34j",
        uuid,
    )

    fun previewNewsDataList(uuid: Int = 1, count: Int = 5): List<Data> {
        val list = mutableListOf<Data>()
        for (i in 1..count) {
            list.add(previewNewsData((uuid + i - 1).toString()))
        }
        return list
    }
}