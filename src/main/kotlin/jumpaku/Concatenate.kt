package jumpaku

fun concatenate(strings: List<String>): String = strings.reduce { s0, s1 -> s0 + s1 }