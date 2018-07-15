package com.twopixeled.balloonadventure.listeners

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor
import com.twopixeled.balloonadventure.assets.assetTypes.Touchable

class InputListener : InputProcessor {
    var touchables = listOf<Touchable>()

    init {
        val im = InputMultiplexer()

        im.addProcessor(this)
        Gdx.input.inputProcessor = im
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }

    override fun scrolled(amount: Int): Boolean {
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    override fun keyDown(keycode: Int): Boolean {
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        touchables.forEach { touchable : Touchable ->
            touchable.touchDown(screenX.toFloat(), normalisedY(screenY))
        }
        return false
    }

    /**
     * Calculation for the correct y position when players click / drag across the screen
     */
    private fun normalisedY(screenY: Int): Float {
        return Gdx.graphics.height - screenY.toFloat()
    }
}