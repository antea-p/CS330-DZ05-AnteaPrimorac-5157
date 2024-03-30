package rs.ac.metropolitan.cs330_dz05_anteaprimorac_5157

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DialogActivity : AppCompatActivity() {
    var items =
        arrayOf<CharSequence>("FIT", "Fakultet za menadžment", "Fakultet digitalnih umetnosti")
    var itemsChecked = BooleanArray(items.size)
    var progressDialog: ProgressDialog? = null

    /**
     * Poziva se prvim definisanjem aktivnosti.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(v: View?) {
        showDialog(0)
    }

    fun onClick2(v: View?) {
        //---prikazuje dijalog---
        val dialog = ProgressDialog.show(
            this, "Nešto se dešava.", "Sačekajte...", true
        )
        Thread {
            try {
                //---simulacija da nešto radi---
                Thread.sleep(5000)
                runOnUiThread {
                    //---odjavljuje dijalog---
                    dialog.dismiss()
                    Toast.makeText(baseContext, "Zadatak završen", Toast.LENGTH_SHORT).show()
                    val url = "https://eliotakira.com/neko/"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }.start()
    }

    fun onClick3(v: View?) {
        showDialog(1)
        progressDialog!!.progress = 0
        Thread {
            for (i in 1..15) {
                try {
                    //---simulacija da nešto radi---
                    Thread.sleep(1000)
                    //---osvežavanje dijaloga---
                    progressDialog!!.incrementProgressBy((100 / 15))
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            progressDialog!!.dismiss()
            val downloadUrl = "https://www.skype.com/en/thank-you-skype/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(downloadUrl))
            startActivity(intent)
        }.start()
    }

    override fun onCreateDialog(id: Int): Dialog? {
        when (id) {
            0 -> {
                return AlertDialog.Builder(this)
                    .setIcon(R.mipmap.ic_launcher)
                    .setTitle("Dijalog sa malo teksta...")
                    .setPositiveButton("OK") { dialog, whichButton ->
                        val selectedUrls = mutableListOf<String>()

                        itemsChecked.forEachIndexed { index, isChecked ->
                            if (isChecked) {
                                val url = when (index) {
                                    0 -> "https://www.metropolitan.ac.rs/osnovne-studije/fakultet-informacionih-tehnologija/"
                                    1 -> "https://www.metropolitan.ac.rs/osnovne-studije/fakultet-za-menadzment/"
                                    2 -> "https://www.metropolitan.ac.rs/fakultet-digitalnih-umetnosti-2/"
                                    else -> ""
                                }
                                if (url.isNotEmpty()) {
                                    selectedUrls.add(url)
                                }
                            }
                        }

                        selectedUrls.forEach { urlString ->
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlString))
                            startActivity(intent)
                        }

                        Toast.makeText(baseContext, "OK je kliknut!", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Cancel") { dialog, whichButton ->
                        Toast.makeText(baseContext, "Cancel je kliknut!", Toast.LENGTH_SHORT).show()
                    }
                    .setMultiChoiceItems(items, itemsChecked) { dialog, which, isChecked ->
                        itemsChecked[which] = isChecked
                        val statusMessage = "${items[which]} ${if (isChecked) "čekiran" else "nečekiran"}!"
                        Toast.makeText(baseContext, statusMessage, Toast.LENGTH_SHORT).show()
                    }.create()
            }
            1 -> {
                progressDialog = ProgressDialog(this)
                progressDialog!!.setIcon(R.mipmap.ic_launcher)
                progressDialog!!.setTitle("Preuzimanje datoteka...")
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_POSITIVE, "OK"
                ) { dialog, whichButton ->
                    Toast.makeText(
                        baseContext,
                        "OK je kliknut!", Toast.LENGTH_SHORT
                    ).show()
                }
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_NEGATIVE, "Cancel"
                ) { dialog, whichButton ->
                    Toast.makeText(
                        baseContext,
                        "Cancel je kliknut!", Toast.LENGTH_SHORT
                    ).show()
                }
                return progressDialog
            }

        }
        return null
    }
}