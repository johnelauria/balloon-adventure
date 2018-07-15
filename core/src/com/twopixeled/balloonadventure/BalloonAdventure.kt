package com.twopixeled.balloonadventure

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.twopixeled.balloonadventure.listeners.InputListener
import com.twopixeled.balloonadventure.stages.MainGame
import com.twopixeled.balloonadventure.stages.Stage

class BalloonAdventure : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var mainGame: Stage

    override fun create() {
        val inputListener = InputListener()

        batch = SpriteBatch()
        mainGame = MainGame()
        inputListener.touchables = mainGame.getTouchables()
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT)
        batch.begin()
        mainGame.draw(batch)
        batch.end()
    }

    override fun dispose() {
        mainGame.dispose()
        batch.dispose()
    }
}
