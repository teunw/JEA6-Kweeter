package nl.teun.kweeter.test.service

import nl.teun.kweeter.services.ValidatorServiceImpl
import org.jboss.arquillian.junit.Arquillian
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(Arquillian::class)
class ValidatorServiceTest {

    private val validatorService = ValidatorServiceImpl()

    @Test
    fun testUsernameValidation() {
        Assert.assertFalse(validatorService.isUsernameValid("invalid username !@#$!@#$!@#$"))
        Assert.assertTrue(validatorService.isUsernameValid("validusername"))
        Assert.assertFalse(validatorService.isUsernameValid("toolongusernametobotherwithsomehowineedtomakethismuchlongerthanneededformytest"))
        Assert.assertFalse(validatorService.isUsernameValid("short"))
    }

}