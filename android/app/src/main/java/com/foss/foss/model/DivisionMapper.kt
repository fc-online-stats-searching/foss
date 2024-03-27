package com.foss.foss.model

object DivisionMapper {

    fun Division.toUiModel(): DivisionUiModel = when (this) {
        Division.NONE -> DivisionUiModel.NONE
        Division.SUPER_CHAMPIONS -> DivisionUiModel.SUPER_CHAMPIONS
        Division.CHAMPIONS -> DivisionUiModel.CHAMPIONS
        Division.SUPER_CHALLENGER -> DivisionUiModel.SUPER_CHALLENGER
        Division.CHALLENGER1 -> DivisionUiModel.CHALLENGER1
        Division.CHALLENGER2 -> DivisionUiModel.CHALLENGER2
        Division.CHALLENGER3 -> DivisionUiModel.CHALLENGER3
        Division.WORLD_CLASS1 -> DivisionUiModel.WORLD_CLASS1
        Division.WORLD_CLASS2 -> DivisionUiModel.WORLD_CLASS2
        Division.WORLD_CLASS3 -> DivisionUiModel.WORLD_CLASS3
        Division.PROFESSIONAL1 -> DivisionUiModel.PROFESSIONAL1
        Division.PROFESSIONAL2 -> DivisionUiModel.PROFESSIONAL2
        Division.PROFESSIONAL3 -> DivisionUiModel.PROFESSIONAL3
        Division.SEMI_PROFESSIONAL1 -> DivisionUiModel.SEMI_PROFESSIONAL1
        Division.SEMI_PROFESSIONAL2 -> DivisionUiModel.SEMI_PROFESSIONAL2
        Division.SEMI_PROFESSIONAL3 -> DivisionUiModel.SEMI_PROFESSIONAL3
        Division.PROSPECT1 -> DivisionUiModel.PROSPECT1
        Division.PROSPECT2 -> DivisionUiModel.PROSPECT2
        Division.PROSPECT3 -> DivisionUiModel.PROSPECT3
    }
}
