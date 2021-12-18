package com.hmju.sample.qualifiers

import javax.inject.Qualifier

@Qualifier
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.FIELD
)
annotation class KotlinxApiService