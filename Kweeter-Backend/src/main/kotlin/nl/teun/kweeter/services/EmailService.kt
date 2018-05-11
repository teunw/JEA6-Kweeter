package nl.teun.kweeter.services

import io.github.cdimascio.dotenv.dotenv
import java.util.*
import javax.ejb.Stateless
import javax.mail.Message
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


@Stateless
class EmailService {

//    @Resource(name = "java:jboss/mail/Default")
//    private lateinit var session: Session

    fun getMailProperties(): Properties {
        val props = Properties()
        props["mail.smtp.host"] = dotenv()["MAIL_SMTP_SERVER"]
        props["mail.smtp.port"] = dotenv()["MAIL_SMTP_PORT"]
        return props
    }

    fun getPropsSession() = Session.getInstance(this.getMailProperties())

    fun sendMail(from: String = "info@kweeter.com", to: String, subject: String, text: String) {
        val message = MimeMessage(this.getPropsSession())
        message.setFrom(InternetAddress(from))
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to))
        message.subject = subject
        message.setText(text)

        Transport.send(message)
    }

}