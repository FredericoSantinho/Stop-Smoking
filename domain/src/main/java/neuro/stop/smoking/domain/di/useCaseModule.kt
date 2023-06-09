package neuro.stop.smoking.domain.di

import neuro.stop.smoking.domain.usecase.GetCurrentTimeMillisUseCase
import neuro.stop.smoking.domain.usecase.GetCurrentTimeMillisUseCaseImpl
import neuro.stop.smoking.domain.usecase.GetStartOfDayUseCase
import neuro.stop.smoking.domain.usecase.GetStartOfDayUseCaseImpl
import neuro.stop.smoking.domain.usecase.ObserveDailySmokedCigarettesUseCase
import neuro.stop.smoking.domain.usecase.ObserveDailySmokedCigarettesUseCaseImpl
import neuro.stop.smoking.domain.usecase.ObserveSmokedCigarettesUseCase
import neuro.stop.smoking.domain.usecase.ObserveSmokedCigarettesUseCaseImpl
import neuro.stop.smoking.domain.usecase.ObserveStartOfCurrentDayUseCase
import neuro.stop.smoking.domain.usecase.ObserveStartOfCurrentDayUseCaseImpl
import neuro.stop.smoking.domain.usecase.RemoveSmokedCigaretteUseCase
import neuro.stop.smoking.domain.usecase.RemoveSmokedCigaretteUseCaseImpl
import neuro.stop.smoking.domain.usecase.SaveSmokedCigaretteUseCase
import neuro.stop.smoking.domain.usecase.SaveSmokedCigaretteUseCaseImpl
import neuro.stop.smoking.domain.usecase.SetStartOfDayUseCase
import neuro.stop.smoking.domain.usecase.SetStartOfDayUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
	factory<SaveSmokedCigaretteUseCase> { SaveSmokedCigaretteUseCaseImpl(get(), get()) }
	factory<ObserveSmokedCigarettesUseCase> { ObserveSmokedCigarettesUseCaseImpl(get()) }
	factory<RemoveSmokedCigaretteUseCase> { RemoveSmokedCigaretteUseCaseImpl(get()) }
	single<GetStartOfDayUseCase> { GetStartOfDayUseCaseImpl(get()) }
	factory<ObserveDailySmokedCigarettesUseCase> {
		ObserveDailySmokedCigarettesUseCaseImpl(get(), get(), get())
	}
	factory<SetStartOfDayUseCase> { SetStartOfDayUseCaseImpl(get()) }
	factory<ObserveStartOfCurrentDayUseCase> { ObserveStartOfCurrentDayUseCaseImpl(get()) }
	factory<GetCurrentTimeMillisUseCase> { GetCurrentTimeMillisUseCaseImpl() }
}
