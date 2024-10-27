package com.lastbrand
import org.junit.Ignore
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
@Ignore
@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("test")
class BaseUnitTest {
    fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }
    fun <T> uninitialized(): T = null as T
}