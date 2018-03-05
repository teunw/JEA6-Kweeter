package nl.teun.kweeter.test.domain

import nl.teun.kweeter.domain.Profile
import org.junit.Assert
import org.junit.Test

class ProfileDomainTest {

    @Test
    fun testProfile() {
        val testProfile = Profile()

        testProfile.setPassword("password")
        Assert.assertTrue(testProfile.checkPassword("password"))
        Assert.assertFalse(testProfile.checkPassword("anotherpassword"))
        Assert.assertFalse(testProfile.checkPassword(" password"))
        Assert.assertFalse(testProfile.checkPassword("password "))

        testProfile.setPassword("testpassword")
        Assert.assertTrue(testProfile.checkPassword("testpassword"))
        Assert.assertFalse(testProfile.checkPassword("anotherpassword"))
        Assert.assertFalse(testProfile.checkPassword(" password"))
        Assert.assertFalse(testProfile.checkPassword("password "))
    }

}