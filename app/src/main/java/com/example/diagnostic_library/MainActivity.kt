package com.example.diagnostic_library

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.diagnostic_lib.MyClass
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_hostname_input.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        check_person_info.setOnClickListener(this)
        hostname_check.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.check_person_info -> {
                val intent = Intent()
                intent.setClass(this, BasisInfoActivity::class.java)
                startActivity(intent)
            }
            R.id.hostname_check -> {
                showHostnameInputDialog()
            }
        }
    }

    private fun showHostnameInputDialog() {
        val rootView = LayoutInflater.from(this).inflate(R.layout.dialog_hostname_input, null, false)
        val etHostName = rootView.findViewById<EditText>(R.id.et_hostname)
        AlertDialog.Builder(this)
            .setView(rootView)
            .setNegativeButton("取消") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("确定") { dialog, _ ->
                if (TextUtils.isEmpty(etHostName.text.toString())) {
                    Toast.makeText(this, "输入不能为空!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                dialog.dismiss()
                val intent = Intent(this, ResultInfoActivity::class.java)
                intent.putExtra(ResultInfoActivity.KEY_HOSTNAME, etHostName.text.toString())
                startActivity(intent)
            }
            .create()
            .show()

    }
}