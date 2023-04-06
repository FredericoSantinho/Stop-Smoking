package neuro.stop.smoking.data.di

import neuro.stop.smoking.data.repository.NewSmokedCigaretteIdRepositoryImpl
import neuro.stop.smoking.data.repository.ObserveSmokedCigarettesRepositoryImpl
import neuro.stop.smoking.data.repository.ObserveStartOfDayRepositoryImpl
import neuro.stop.smoking.data.repository.RemoveCigaretteRepositoryImpl
import neuro.stop.smoking.data.repository.SaveSmokedCigaretteRepositoryImpl
import neuro.stop.smoking.domain.repository.NewSmokedCigaretteIdRepository
import neuro.stop.smoking.domain.repository.ObserveSmokedCigarettesRepository
import neuro.stop.smoking.domain.repository.ObserveStartOfDayRepository
import neuro.stop.smoking.domain.repository.RemoveCigaretteRepository
import neuro.stop.smoking.domain.repository.SaveSmokedCigaretteRepository
import org.koin.dsl.module

val repositoryModule = module {
	factory<NewSmokedCigaretteIdRepository> { NewSmokedCigaretteIdRepositoryImpl(get()) }
	factory<SaveSmokedCigaretteRepository> { SaveSmokedCigaretteRepositoryImpl(get()) }
	factory<ObserveSmokedCigarettesRepository> { ObserveSmokedCigarettesRepositoryImpl(get()) }
	factory<RemoveCigaretteRepository> { RemoveCigaretteRepositoryImpl(get()) }
	factory<ObserveStartOfDayRepository> { ObserveStartOfDayRepositoryImpl(get()) }
}
