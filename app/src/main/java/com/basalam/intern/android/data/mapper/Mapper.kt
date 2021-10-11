package com.basalam.intern.android.data.mapper

interface Mapper<I, O> {
    fun map(from: I): O
}