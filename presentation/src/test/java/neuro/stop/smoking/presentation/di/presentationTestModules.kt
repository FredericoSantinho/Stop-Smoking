package neuro.stop.smoking.presentation.di

import neuro.stop.smoking.data.di.daoModule
import neuro.stop.smoking.data.di.repositoryModule
import neuro.stop.smoking.data.di.test.memoryDatabaseModule

val presentationTestModules = listOf(
	viewModelModule,
	useCaseModule,
	repositoryModule,
	daoModule,
	memoryDatabaseModule,
	presentationMappersModule,
	contextModule
)
