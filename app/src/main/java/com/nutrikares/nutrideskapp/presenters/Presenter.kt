package com.nutrikares.nutrideskapp.presenters

import com.nutrikares.nutrideskapp.views.View

interface Presenter<T : View>{
    var view: T?
    fun onDestroy(){ view = null }
}