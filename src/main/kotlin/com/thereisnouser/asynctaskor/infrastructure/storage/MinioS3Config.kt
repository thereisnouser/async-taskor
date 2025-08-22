package com.thereisnouser.asynctaskor.infrastructure.storage

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.S3Configuration
import java.net.URI

@Configuration
class MinioS3Config(
    @Value("\${application.storage.endpoint}") private val endpoint: String,
    @Value("\${application.storage.accessKey}") private val accessKey: String,
    @Value("\${application.storage.secretKey}") private val secretKey: String,
) {

    @Bean
    fun s3Client(): S3Client {
        return S3Client.builder()
            .endpointOverride(URI.create(endpoint))
            .credentialsProvider(
                StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey))
            )
            .region(Region.AWS_GLOBAL)
            .serviceConfiguration(
                S3Configuration.builder().pathStyleAccessEnabled(true).build()
            )
            .build()
    }

}