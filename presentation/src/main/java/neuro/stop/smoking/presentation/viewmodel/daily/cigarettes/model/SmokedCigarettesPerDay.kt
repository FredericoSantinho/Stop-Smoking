package neuro.stop.smoking.presentation.viewmodel.daily.cigarettes.model

import neuro.stop.smoking.domain.dto.SmokedCigaretteDto

data class SmokedCigarettesPerDay(val date: String, val smokedCigarettes: List<SmokedCigaretteDto>)