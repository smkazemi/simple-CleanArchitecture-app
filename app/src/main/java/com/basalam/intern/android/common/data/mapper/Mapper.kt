package com.basalam.intern.android.common.data.mapper

interface Mapper<I, O> {
    fun map(from: I): O
}