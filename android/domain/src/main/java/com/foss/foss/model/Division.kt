package com.foss.foss.model

enum class Division(val value: Int) {
    NONE(0),
    SUPER_CHAMPIONS(800),
    CHAMPIONS(900),
    SUPER_CHALLENGER(1000),
    CHALLENGER1(1100),
    CHALLENGER2(1200),
    CHALLENGER3(1300),
    WORLD_CLASS1(2000),
    WORLD_CLASS2(2100),
    WORLD_CLASS3(2200),
    PROFESSIONAL1(2300),
    PROFESSIONAL2(2400),
    PROFESSIONAL3(2500),
    SEMI_PROFESSIONAL1(2600),
    SEMI_PROFESSIONAL2(2700),
    SEMI_PROFESSIONAL3(2800),
    PROSPECT1(2900),
    PROSPECT2(3000),
    PROSPECT3(3100);

    companion object {

        private const val NO_SUCH_ELEMENT_MESSAGE = "value에 해당하는 Division이 존재하지 않습니다."

        fun instanceOf(value: Int): Division {
            return entries.find { it.value == value }
                ?: throw NoSuchElementException(NO_SUCH_ELEMENT_MESSAGE)
        }
    }
}

// {
//    "divisionId": 2600,
//    "divisionName": "세미프로1"
// },
// {
//    "divisionId": 2700,
//    "divisionName": "세미프로2"
// },
// {
//    "divisionId": 2800,
//    "divisionName": "세미프로3"
// },
// {
//    "divisionId": 2900,
//    "divisionName": "유망주1"
// },
// {
//    "divisionId": 3000,
//    "divisionName": "유망주2"
// },
// {
//    "divisionId": 3100,
//    "divisionName": "유망주3"
// }
