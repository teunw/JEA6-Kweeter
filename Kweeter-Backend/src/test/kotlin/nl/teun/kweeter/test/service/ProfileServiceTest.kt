package nl.teun.kweeter.test.service

import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.services.ProfileService
import nl.teun.kweeter.services.ProfileServiceImpl
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.junit.Arquillian
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.JavaArchive
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(Arquillian::class)
class ProfileServiceTest {

    @Mock
    private lateinit var profileService: ProfileService

    @InjectMocks
    private lateinit var profileServiceImpl: ProfileServiceImpl

    companion object {
        @Deployment
        fun createDeployment(): JavaArchive {
            return ShrinkWrap.create(JavaArchive::class.java)
                    .addClass(Profile::class.java)
        }
    }

    private var testProfile: Profile = Profile()

    @Before
    fun beforeTests() {
        MockitoAnnotations.initMocks(this)
        Assert.assertEquals("Test profile invalid, should be empty!", testProfile, Profile())
        testProfile = Profile()
                .setUsername("testUsername")
                .setEmail("test@email.com")
                .setDisplayName("Display Name")
    }

    @Test
    fun testProfileFindById() {
        val zeroProfile = Profile()
                .setUsername("testUsername")
                .setEmail("test@email.com")
                .setDisplayName("Display Name")
        Mockito
                .`when`(profileService.findById(0))
                .thenReturn(zeroProfile)

        Assert.assertEquals(zeroProfile.username, "testUsername")
        Assert.assertEquals(zeroProfile.email, "test@email.com")
        Assert.assertEquals(zeroProfile.displayName, "Display Name")
    }

    @Test(expected = Exception::class)
    fun testProfileCreation() {
        val zeroProfile = Profile()
                .setUsername("testUsername")
                .setEmail("test@email.com")
                .setDisplayName("Display Name")
        Mockito.`when`(profileService.createProfile(zeroProfile)).thenThrow(Exception("Works"))
        profileService.createProfile(zeroProfile)
    }

    @Test(expected = Exception::class)
    fun testProfileUpdate() {
        Mockito.`when`(profileService.updateProfile(testProfile)).thenThrow(Exception("Works"))
        profileService.updateProfile(testProfile)
    }

    @Test
    fun testProfileServiceFindall() {
        Mockito.`when`(profileService.findAll()).thenReturn(listOf())
        val list = profileService.findAll()
        Assert.assertTrue(list.isEmpty())
    }

}