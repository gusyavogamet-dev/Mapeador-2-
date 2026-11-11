package com.example.keymapper

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.widget.*

class MainActivity : Activity() {
    companion object { @Volatile var capturing = false }

    private var pendingKey: Int? = null
    private lateinit var info: TextView
    private lateinit var list: TextView
    private lateinit var etX: EditText
    private lateinit var etY: EditText

    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        val root = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(40, 60, 40, 40) }

        root.addView(Button(this).apply {
            text = "1. Ativar serviço de acessibilidade"
            setOnClickListener { startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)) }
        })
        root.addView(Button(this).apply {
            text = "2. Capturar tecla (depois aperte uma tecla)"
            setOnClickListener { capturing = true; pendingKey = null; info.text = "Aperte uma tecla do teclado..." }
        })
        info = TextView(this).apply { text = "Nenhuma tecla capturada" }
        root.addView(info)

        etX = EditText(this).apply { hint = "X (pixels)"; inputType = 2 }
        etY = EditText(this).apply { hint = "Y (pixels)"; inputType = 2 }
        root.addView(etX); root.addView(etY)

        root.addView(Button(this).apply {
            text = "3. Salvar mapeamento"
            setOnClickListener {
                val k = pendingKey; val x = etX.text.toString().toFloatOrNull(); val y = etY.text.toString().toFloatOrNull()
                if (k == null || x == null || y == null) toast("Capture a tecla e preencha X e Y")
                else { MappingStore.save(this@MainActivity, k, x, y); refresh(); toast("Salvo!") }
            }
        })
        root.addView(Button(this).apply {
            text = "Limpar tudo"
            setOnClickListener { MappingStore.clear(this@MainActivity); refresh() }
        })
        list = TextView(this)
        root.addView(list)
        setContentView(ScrollView(this).apply { addView(root) })
        refresh()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (capturing) {
            pendingKey = keyCode
            capturing = false
            info.text = "Tecla: ${KeyEvent.keyCodeToString(keyCode)}"
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun refresh() {
        val m = MappingStore.all(this)
        list.text = if (m.isEmpty()) "\nSem mapeamentos."
        else "\nMapeamentos:\n" + m.entries.joinToString("\n") { "${KeyEvent.keyCodeToString(it.key)} → (${it.value})" }
    }

    private fun toast(t: String) = Toast.makeText(this, t, Toast.LENGTH_SHORT).show()
}
