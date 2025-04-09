package com.wafiqz0085.assesment1.navigation

sealed class Screen (val route: String){
    data object Home: Screen("mainScreen")
    data object About: Screen("aboutScreen")
}