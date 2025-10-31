package com.sarang.torang.util

import com.sarang.torang.di.torang_security_di.Encrypt

interface TorangRepositoryEncrypt {
    fun encrypt(text: String): String
    fun decrypt(text: String) : String
}