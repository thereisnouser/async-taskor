package com.thereisnouser.asynctaskor.infrastructure.storage

import java.io.InputStream
import java.nio.file.Path

interface ObjectStorage {
    fun getSrc(key: String): InputStream
    fun getDst(key: String): InputStream
    fun putSrc(key: String, bytes: ByteArray, contentType: String)
    fun putDstFile(key: String, file: Path, contentType: String)
}