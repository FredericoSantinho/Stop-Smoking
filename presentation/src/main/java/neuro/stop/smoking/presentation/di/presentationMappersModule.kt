package neuro.stop.smoking.presentation.di

import neuro.stop.smoking.presentation.mapper.SmokedCigaretteModelMapper
import neuro.stop.smoking.presentation.mapper.SmokedCigaretteModelMapperImpl
import org.koin.dsl.module

val presentationMappersModule = module {
	factory<SmokedCigaretteModelMapper> { SmokedCigaretteModelMapperImpl() }
}
