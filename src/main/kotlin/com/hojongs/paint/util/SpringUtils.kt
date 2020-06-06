package com.hojongs.paint.util

import org.springframework.util.CollectionUtils

fun Map<String, List<String>>.toQueryParams() = CollectionUtils.toMultiValueMap(this)
