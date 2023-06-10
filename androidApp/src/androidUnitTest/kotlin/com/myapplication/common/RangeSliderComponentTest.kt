package com.myapplication.common

import androidx.compose.material3.RangeSlider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTouchInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@MediumTest
@RunWith(AndroidJUnit4::class)
class RangeSliderComponentTest {

    private val tag = "slider"

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun disabledRangeSliderValueCanBeModifyProgrammatically() {
        val state = mutableStateOf(0f..1f)

        rule.setContent {
            RangeSlider(
                modifier = Modifier.testTag(tag),
                value = state.value,
                enabled = false,
                onValueChange = { state.value = it }
            )
        }

        Truth.assertThat(SemanticsProperties.Disabled.name).isEqualTo("Disabled")
        Truth.assertThat(state.value).isEqualTo(0f..1f)

        state.value = 0.5f..0.5f

        Truth.assertThat(state.value).isEqualTo(0.5f..0.5f)
        Truth.assertThat(SemanticsProperties.Disabled.name).isEqualTo("Disabled")
    }

    @Test
    fun disabledRangeSliderDoesntChangeValueAfterDrag (){
        val state = mutableStateOf(0f..1f)
        var slop = 0f

        rule.setContent {
            slop = LocalViewConfiguration.current.touchSlop

            RangeSlider(
                modifier = Modifier.testTag(tag),
                value = state.value,
                enabled = false,
                onValueChange = { state.value = it }
            )
        }

        rule.onNodeWithTag(tag)
            .performTouchInput {
                down(centerRight)
                moveBy(Offset(-slop, 0f))
                moveBy(Offset(-width.toFloat(), 0f))
                up()
            }

        Truth.assertThat(SemanticsProperties.Disabled.name).isEqualTo("Disabled")
        Truth.assertThat(state.value).isEqualTo(0f..1f)
    }

    @Test
    fun onValueChangeFinishedCallbackIsTriggeredAfterDrag(){
        val state = mutableStateOf(0.5f..1f)
        var slop = 0f

        val onValueChangeFinishedCounter = mutableStateOf(0)

        rule.setContent {
            slop = LocalViewConfiguration.current.touchSlop
            RangeSlider(
                modifier = Modifier.testTag(tag),
                value = state.value,
                onValueChange = {
                    state.value = it
                },
                onValueChangeFinished = {
                    onValueChangeFinishedCounter.value += 1
                }
            )
        }

        rule.onNodeWithTag(tag)
            .performTouchInput {
                down(centerRight)
                moveBy(Offset(-slop, 0f))
                moveBy(Offset(-width.toFloat(), 0f))
                up()
            }

        Truth.assertThat(onValueChangeFinishedCounter.value).isEqualTo(1)

        rule.onNodeWithTag(tag)
            .performTouchInput {
                down(center)
                moveBy(Offset(-slop, 0f))
                moveBy(Offset(-width.toFloat(), 0f))
                up()
            }
        Truth.assertThat(onValueChangeFinishedCounter.value).isEqualTo(2)
    }

    @Test
    fun onValueChangeCallbackIsTriggeredAfterDrag(){
        val state = mutableStateOf(0.5f..1f)
        var slop = 0f

        val onValueChangeCounter = mutableStateOf(0)

        rule.setContent {
            slop = LocalViewConfiguration.current.touchSlop
            RangeSlider(
                modifier = Modifier.testTag(tag),
                value = state.value,
                onValueChange = {
                    state.value = it
                    onValueChangeCounter.value += 1
                                },
            )
        }

        rule.onNodeWithTag(tag)
            .performTouchInput {
                down(centerRight)
                moveBy(Offset(-slop, 0f))
                moveBy(Offset(-width.toFloat(), 0f))
                up()
            }

        Truth.assertThat(onValueChangeCounter.value).isEqualTo(1)

        rule.onNodeWithTag(tag)
            .performTouchInput {
                down(center)
                moveBy(Offset(-slop, 0f))
                moveBy(Offset(-width.toFloat(), 0f))
                up()
            }
        Truth.assertThat(onValueChangeCounter.value).isEqualTo(2)
    }

    @Test
    fun sliderValueChangesAfterTapOnTrack() {
        val state = mutableStateOf(0f..1f)

        rule.setContent {
            RangeSlider(
                modifier = Modifier.testTag(tag),
                value = state.value,
                onValueChange = { state.value = it }
            )
        }

        Truth.assertThat(state.value).isEqualTo(0f..1f)

        rule.onNodeWithTag(tag)
            .performTouchInput {
                down(Offset(centerX + 50, centerY))
                up()
            }

        Truth.assertThat(state.value).isNotEqualTo(0f..1f)
    }

    @Test
    fun sliderValueChangesAfterDrag(){
        val state = mutableStateOf(0f..1f)
        var slop = 0f

        rule.setContent {
            slop = LocalViewConfiguration.current.touchSlop

            RangeSlider(
                modifier = Modifier.testTag(tag),
                value = state.value,
                onValueChange = { state.value = it }
            )
        }

        rule.onNodeWithTag(tag)
            .performTouchInput {
                down(centerRight)
                moveBy(Offset(-slop, 0f))
                moveBy(Offset(-width.toFloat(), 0f))
                up()
            }

        Truth.assertThat(state.value).isNotEqualTo(0f..1f)
    }

    @Test
    fun sliderMaximumValueCanNotBeLessThanMinimal(){
        val state = mutableStateOf(0.9f..1f)
        var slop = 0f

        rule.setContent {
            slop = LocalViewConfiguration.current.touchSlop

            RangeSlider(
                modifier = Modifier.testTag(tag),
                value = state.value,
                onValueChange = { state.value = it }
            )
        }

        rule.onNodeWithTag(tag)
            .performTouchInput {
                down(centerRight)
                moveBy(Offset(-slop, 0f))
                moveBy(Offset(-width.toFloat(), 0f))
                up()
            }

        Truth.assertThat(state.value).isEqualTo(0.9f..0.9f)
    }
}