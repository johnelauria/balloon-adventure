package com.twopixeled.balloonadventure.stages

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.twopixeled.balloonadventure.assets.BeeLeft
import com.twopixeled.balloonadventure.assets.Player

class MainGame : Stage() {
    private val beeLeftAtlas = TextureAtlas(Gdx.files.internal("bee_left/bee_left.atlas"))

    init {
        assets.add(Player(world))

        for (bee in 1..12) {
            val flyingBee = BeeLeft(world, beeLeftAtlas)
            flyingBee.randomisePosition()
            assets.add(flyingBee)
        }
    }

    override fun dispose() {
        beeLeftAtlas.dispose()
        super.dispose()
    }
}