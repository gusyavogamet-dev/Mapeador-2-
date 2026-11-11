package com.example.keymapper

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent

class MapperService : AccessibilityService() {

    override fun onKeyEvent(event: KeyEvent): Boolean {
        // Deixa o próprio app receber as teclas enquanto você configura
        if (MainActivity.capturing) return false
        val pos = MappingStore.get(this, event.keyCode) ?: return false
        if (event.action == KeyEvent.ACTION_DOWN && event.repeatCount == 0) {
            tap(pos.first, pos.second)
        }
        return true // consome a tecla (ela não chega ao app de baixo)
    }

    private fun tap(x: Float, y: Float) {
        val path = Path().apply { moveTo(x, y) }
        val stroke = GestureDescription.StrokeDescription(path, 0, 50)
        dispatchGesture(GestureDescription.Builder().addStroke(stroke).build(), null, null)
    }

    override fun onAccessibilityEvent(e: AccessibilityEvent?) {}
    override fun onInterrupt() {}
}
