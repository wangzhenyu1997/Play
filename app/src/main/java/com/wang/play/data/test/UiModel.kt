package com.wang.play.data.test

sealed class UiModel {
    data class HitItem(val hit: Hit) : UiModel()
    data class SeparatorItem(val description: String, val isTitle: Boolean = false) : UiModel()
}


