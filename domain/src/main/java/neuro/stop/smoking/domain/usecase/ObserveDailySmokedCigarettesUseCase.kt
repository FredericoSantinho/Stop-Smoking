package neuro.stop.smoking.domain.usecase

import kotlinx.coroutines.flow.Flow
import neuro.stop.smoking.domain.dto.SmokedCigaretteDto
import java.util.Calendar

interface ObserveDailySmokedCigarettesUseCase {
	/**
	 * Observe daily smoked cigarettes.
	 *
	 * @return a Flow that will emit a map with daily smoked cigarettes each time it changes.
	 */
	fun observeDailySmokedCigarettes(): Flow<Map<Calendar, List<SmokedCigaretteDto>>>
}