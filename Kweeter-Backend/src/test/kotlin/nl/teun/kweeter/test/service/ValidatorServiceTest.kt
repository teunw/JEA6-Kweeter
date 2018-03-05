package nl.teun.kweeter.test.service

import nl.teun.kweeter.services.ValidatorService
import org.junit.Assert
import org.junit.Test

class ValidatorServiceTest {

    private val validatorService : ValidatorService = ValidatorService()

    @Test
    fun testUsernameValidation() {
        Assert.assertFalse(validatorService.isUsernameValid("invalid username !@#$!@#$!@#$"))
        Assert.assertTrue(validatorService.isUsernameValid("validusername"))
        Assert.assertFalse(validatorService.isUsernameValid("toolongusernametobotherwithsomehowineedtomakethismuchlongerthanneededformytest"))
        Assert.assertFalse(validatorService.isUsernameValid("short"))
    }

}