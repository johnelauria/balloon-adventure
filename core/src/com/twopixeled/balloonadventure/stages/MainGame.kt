package com.twopixeled.balloonadventure.stages

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.twopixeled.balloonadventure.assets.BeeLeft
import com.twopixeled.balloonadventure.assets.Ceiling
import com.twopixeled.balloonadventure.assets.Player
import com.twopixeled.balloonadventure.assets.Walls
import com.twopixeled.balloonadventure.configs.PIXEL_TO_METER
import com.twopixeled.balloonadventure.configs.SCREEN_HEIGHT
import com.twopixeled.balloonadventure.configs.SCREEN_WIDTH
import com.twopixeled.balloonadventure.listeners.MainGameContactListener

class MainGame : Stage() {
    private val player = Player(world)
    private val beeLeftAtlas = TextureAtlas(Gdx.files.internal("bee_left/bee_left.atlas"))
    private val camera = OrthographicCamera()

    init {
        Ceiling(world)
        Walls(world)
        assets.add(player)

        for (bee in 1..4) {
            val flyingBee = BeeLeft(world, beeLeftAtlas)
            flyingBee.randomisePosition()
            assets.add(flyingBee)
        }

        world.setContactListener(MainGameContactListener(this))
        camera.setToOrtho(false, SCREEN_WIDTH / PIXEL_TO_METER, SCREEN_HEIGHT / PIXEL_TO_METER)
    }

    override fun dispose() {
        beeLeftAtlas.dispose()
        super.dispose()
    }

    fun popPlayerBalloon() {
        player.popBalloon()
    }
}