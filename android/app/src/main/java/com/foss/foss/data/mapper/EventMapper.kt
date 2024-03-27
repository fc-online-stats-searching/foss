package com.foss.foss.data.mapper

import com.foss.foss.data.dto.FcEventDTO
import com.foss.foss.model.FcEvent

object EventMapper {

    /**
     * todo: 의미는 없지만 코드의 일관성을 지키기 위해 DTO -> DOMAIN의 과정을 거친다.
     */
    fun FcEventDTO.toDomainModel(): FcEvent = FcEvent(
        nexonURL = nexonURL,
        imageURL = imageURL,
        description = description
    )
}
