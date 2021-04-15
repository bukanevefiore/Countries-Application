package com.examp.countries.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

class BaseViewModel : ViewModel(),CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = TODO("Not yet implemented")
}