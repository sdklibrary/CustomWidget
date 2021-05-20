package com.pretty.library.widget.pager

import androidx.annotation.IntDef

@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
@IntDef(PagerStatus.GONE, PagerStatus.LOADING, PagerStatus.EMPTY, PagerStatus.ERROR)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class PagerStatus {

    companion object {
        const val GONE = 0
        const val LOADING = 1
        const val EMPTY = 2
        const val ERROR = 3
    }
}
