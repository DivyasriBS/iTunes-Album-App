package com.tw.gojek.data.preference

interface PreferenceHelper {
    fun setString(key: String, value: String)
    fun getString(key: String): String
}