package nsoushi.mocktest

import io.kotlintest.specs.BehaviorSpec
import mockit.Deencapsulation
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Matchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

/**
 * @author nsoushi
 */
class PpapImplTest : BehaviorSpec() {

    init {
        given("right hand param and left hand param") {
            var leftHand: String
            var rightHand: String

            `when`("right hand is 'Pen', left hand is 'Apple'") {
                rightHand = "a Pen"
                leftHand = "an Apple"

                then("woo method should return `ApplePen.`") {
                    val pikoTaro = PpapImpl(rightHand, leftHand)

                    pikoTaro.rightHand(true) shouldBe "I have a Pen."
                    pikoTaro.rightHand(false) shouldBe "Pen"

                    pikoTaro.leftHand(true) shouldBe "I have an Apple."
                    pikoTaro.leftHand(false) shouldBe "Apple"

                    pikoTaro.woo(pikoTaro.rightHand(false), pikoTaro.leftHand(false)) shouldBe "ApplePen."
                }
            }
        }
    }
}

class PpapSong_MockTest : BehaviorSpec() {
    init {
        given("ppap sing method") {

            `when`("mock up, all method") {

                then("sing method should return `I have a Ebi. I have a Bin. woo EbiInBin.`") {

                    val ppapMock: PpapImpl = mock(PpapImpl::class.java)

                    val ppapSong = PpapSong(ppapMock)

                    Mockito.`when`(ppapMock.rightHand(sing = true)).thenReturn("I have a Ebi.")
                    Mockito.`when`(ppapMock.leftHand(sing = true)).thenReturn("I have a Bin.")

                    Mockito.`when`(ppapMock.rightHand(sing = false)).thenReturn("Ebi")
                    Mockito.`when`(ppapMock.leftHand(sing = false)).thenReturn("Bin")

                    Mockito.`when`(ppapMock.woo("Ebi", "Bin")).thenReturn("EbiInBin.")

                    val actual = ppapSong.sing()

                    actual shouldBe "I have a Ebi. I have a Bin. woo EbiInBin."
                }
            }
        }
    }
}

class PpapSong_MockTest_Using_Spy : BehaviorSpec() {
    init {
        given("ppap sing method") {

            `when`("mock up, using spy") {

                then("sing method should return `I have a Pen. I have an PineApple. woo PineApplePen.`") {

                    val target: PpapImpl = PpapImpl(right = "a Pen", left = "an Apple")
                    val spy = spy(target)

                    val ppapSong = PpapSong(spy)

                    Mockito.`when`(spy.leftHand(sing = true)).thenReturn("I have a PineApple.")
                    Mockito.`when`(spy.leftHand(sing = false)).thenReturn("PineApple")

                    val actual = ppapSong.sing()

                    actual shouldBe "I have a Pen. I have a PineApple. woo PineApplePen."
                }
            }
        }
    }
}

class PpapSong_MockTest_Using_Any_Parameter : BehaviorSpec() {
    init {
        given("ppap sing method") {

            `when`("mock up, using any parameter") {

                then("sing method should return `I have a Pen. I have an Apple. woo PenPineAppleApplePen.`") {

                    val target: PpapImpl = PpapImpl(right = "a Pen", left = "an Apple")
                    val spy = spy(target)
                    val ppapSong = PpapSong(spy)

                    Mockito.`when`(spy.woo(anyString(), anyString())).thenReturn("PenPineAppleApplePen.")

                    val actual = ppapSong.sing()

                    actual shouldBe "I have a Pen. I have an Apple. woo PenPineAppleApplePen."
                }
            }
        }
    }
}

@RunWith(PowerMockRunner::class)
@PrepareForTest(PpapImpl::class)
class PpapSong_Private_Method_MockTest {

    @Test
    fun rightHand_test() {

        val target: PpapImpl = PpapImpl(right = "a Pen", left = "an Apple")
        val spy = PowerMockito.spy(target)

        PowerMockito.doReturn("Ebi").`when`(spy, "getObj", "a Pen")

        val actual = spy.rightHand(false)

        assertThat(actual, `is`("Ebi"))
    }
}

class PpapSong_Private_MethodTest : BehaviorSpec() {
    init {
        given("getObj method") {

            val target = PpapImpl(right = "a Pen", left = "an Apple")
            var param: String

            `when`("param is 'a Pen'") {
                then("method should return 'Pen'") {
                    param = "a Pen"
                    val actual = Deencapsulation.invoke<String>(target, "getObj", param)
                    actual shouldBe "Pen"
                }
            }

            `when`("param is 'an Apple'") {
                then("method should return 'Apple'") {
                    param = "an Apple"
                    val actual = Deencapsulation.invoke<String>(target, "getObj", param)
                    actual shouldBe "Apple"
                }
            }

            `when`("param is 'Ebi'") {
                then("method throws exception") {
                    param = "Ebi"
                    shouldThrow<ArrayIndexOutOfBoundsException> {
                        Deencapsulation.invoke<String>(target, "getObj", param)
                    }
                }
            }
        }
    }
}

