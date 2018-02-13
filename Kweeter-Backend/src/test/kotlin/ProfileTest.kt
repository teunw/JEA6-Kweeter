import nl.teun.kweeter.domain.Profile
import org.junit.Test
import org.wildfly.common.Assert

class ProfileTest {

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