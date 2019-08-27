import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane
import tornadofx.*
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


class AESText : View() {
    override val root: AnchorPane by fxml()

    @FXML
    private lateinit var encryptButton: Button
    @FXML
    private lateinit var decryptButton: Button
    @FXML
    private lateinit var textPlace: TextArea
    @FXML
    private lateinit var keyText: TextField

    init {
        title = "AES Text Encryption / Decryption"

        encryptButton.action {
            textPlace.text = textPlace.text.encrypt(keyText.text)
        }

        decryptButton.action {
            textPlace.text = textPlace.text.decrypt(keyText.text)
        }
    }

    private fun String.encrypt(password: String) = Base64.getEncoder().encodeToString(
            getCipher(password, Cipher.ENCRYPT_MODE)
                    .doFinal(this.toByteArray()))


    private fun String.decrypt(password: String) = String(
            getCipher(password, Cipher.DECRYPT_MODE)
                    .doFinal(Base64.getDecoder().decode(this)))


    private fun getCipher(pass: String, mode: Int): Cipher {
        val password: String = when {
            pass.length == 16 -> pass
            pass.length > 16 -> pass.substring(0, 16)
            else -> pass + "*".repeat(16 - pass.length)
        }

        val secretKeySpec = SecretKeySpec(password.toByteArray(), "AES")

        val iv = ByteArray(16)
        val charArray = password.toCharArray()

        for (i in 0 until charArray.size)
            iv[i] = charArray[i].toByte()

        val ivParameterSpec = IvParameterSpec(iv)

        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(mode, secretKeySpec, ivParameterSpec)
        return cipher
    }

}
