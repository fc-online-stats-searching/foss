package com.foss.foss.model

object FcEventMapper {

    fun FcEvent.toUiModel(): FcEventUiModel = FcEventUiModel(
        nexonURL = nexonURL,
        imageURL = imageURL,
        description = description
    )
}
