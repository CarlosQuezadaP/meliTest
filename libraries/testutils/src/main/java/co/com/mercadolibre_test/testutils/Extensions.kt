package co.com.mercadolibre_test.testutils

import io.mockk.MockK
import io.mockk.MockKDsl
import kotlin.reflect.KClass

inline fun <reified T : Any> relaxedMockk(
    name: String? = null,
    vararg moreInterface: KClass<*>,
    block: T.() -> Unit = {}
): T = MockK.useImpl {
    MockKDsl.internalMockk(
        name,
        true,
        *moreInterface,
        relaxUnitFun = true,
        block = block
    )
}
