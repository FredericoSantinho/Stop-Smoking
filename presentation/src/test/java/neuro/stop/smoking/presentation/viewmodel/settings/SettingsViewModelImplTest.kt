package neuro.stop.smoking.presentation.viewmodel.settings

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SettingsViewModelImplTest {

	@Test
	fun testTitle() {
		val settingsViewModel = SettingsViewModelImpl()

		assertEquals(SettingsTitle, settingsViewModel.title.value)
	}

	@Test
	fun testOnBackClick() {
		val settingsViewModel = SettingsViewModelImpl()

		assertNull(settingsViewModel.uiEvent.value)

		settingsViewModel.onBackClick()

		assertEquals(UiEvent.NavigateBack, settingsViewModel.uiEvent.value)

		settingsViewModel.eventConsumed()

		assertNull(settingsViewModel.uiEvent.value)
	}

	@Test
	fun testOnChangeStartOfDayClick() {
		val settingsViewModel = SettingsViewModelImpl()

		assertNull(settingsViewModel.uiEvent.value)

		settingsViewModel.onChangeStartOfDayClick()

		assertEquals(UiEvent.NavigateToChangeStartOfDay, settingsViewModel.uiEvent.value)

		settingsViewModel.eventConsumed()

		assertNull(settingsViewModel.uiEvent.value)
	}
}