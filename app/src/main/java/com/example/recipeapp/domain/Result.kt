package com.example.recipeapp.domain

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val errMessage: String) : Result<Nothing> //create a wrapper for error message
}