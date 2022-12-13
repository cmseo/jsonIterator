package com.example.myapplication.test

object JsonFiles {
    val monitoring = """
        "nodeInfo": {
          "ep1": {
            "OnOffCluster": {
              "OnOff": true
            }
          }
        }
    """.trimIndent()

    val mo: String
        get() = monitoring
}