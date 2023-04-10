package neuro.stop.smoking.presentation.viewmodel.test.mocks

import neuro.stop.smoking.presentation.model.SmokedCigaretteModel

fun smokedCigaretteModelMock(
	smokedCigaretteId: Long = 29L,
	time: String = smokedCigaretteId.toString()
): SmokedCigaretteModel = SmokedCigaretteModel(smokedCigaretteId, time)

fun smokedCigaretteModelMockList(n: Long = 3): List<SmokedCigaretteModel> =
	LongRange(1, n).map { smokedCigaretteModelMock(it) }
