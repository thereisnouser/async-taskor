package com.thereisnouser.asynctaskor.infrastructure.storage

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.InputStream
import java.nio.file.Path

@Component
class MinioObjectStorage(
    private val s3: S3Client,
    @Value("\${application.storage.bucket.src}") private val srcBucket: String,
    @Value("\${application.storage.bucket.dst}") private val dstBucket: String,
) : ObjectStorage {

    override fun getSrc(key: String): InputStream {
        TODO("Not yet implemented")
    }

    override fun getDst(key: String): InputStream {
        TODO("Not yet implemented")
    }

    override fun putSrc(key: String, bytes: ByteArray, contentType: String) {
        s3.putObject(
            PutObjectRequest.builder().bucket(srcBucket).key(key).contentType(contentType).build(),
            RequestBody.fromBytes(bytes)
        )
    }

    override fun putDstFile(key: String, file: Path, contentType: String) {
        TODO("Not yet implemented")
    }

}