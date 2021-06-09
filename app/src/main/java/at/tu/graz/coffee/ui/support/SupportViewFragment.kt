package at.tu.graz.coffee.ui.support

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import at.tu.graz.coffee.R
import at.tu.graz.coffee.businessLogic.EmailSender
import kotlinx.android.synthetic.main.fragment_support.*
import android.widget.Toast

class SupportViewFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_support, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_sendEmail?.setOnClickListener {
            val id: Int = radio_group.checkedRadioButtonId

            val emailToSend = if (id == rdbtn_Owner.id) {
                "smartcoffeehelp@gmail.com"
            } else {
                "smartcoffeehelp@gmail.com"
            }

            EmailSender().execute(emailToSend, txt_supportMsg.text.toString())

            txt_supportMsg.text.clear()

            Toast.makeText(activity, R.string.emailConfirm, Toast.LENGTH_SHORT).show()
        }
    }
}