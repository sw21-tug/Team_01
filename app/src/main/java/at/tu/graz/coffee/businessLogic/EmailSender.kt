package at.tu.graz.coffee.businessLogic

import android.os.AsyncTask
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class EmailSender : AsyncTask<String?, String?, Void?>() {
    private val username = "agilesoftwaredev2021@gmail.com"
    private val password = "Bismarck0104"
    private var props = Properties()

    override fun doInBackground(vararg p0: String?): Void? {
        send(p0[0], p0[1])
        return null
    }

    private fun send(to: String?, text: String?) {
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"
        props["mail.smtp.host"] = "smtp.gmail.com"
        props["mail.smtp.port"] = "587"
        val session = Session.getInstance(props, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(username, password)
            }
        })
        try {
            val message: Message = MimeMessage(session)
            message.setFrom(InternetAddress(username))
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to))
            message.subject = "Support-Request"
            message.setText(text)
            Transport.send(message)
        } catch (e: MessagingException) {
            e.printStackTrace()
        }
    }
}