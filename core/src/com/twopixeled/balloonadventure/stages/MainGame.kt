package com.twopixeled.balloonadventure.stages

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.twopixeled.balloonadventure.assets.BeeLeft
import com.twopixeled.balloonadventure.assets.Ceiling
import com.twopixeled.balloonadventure.assets.Player
import com.twopixeled.balloonadventure.assets.Walls
import com.twopixeled.balloonadventure.listeners.MainGameContactListener

class MainGame : Stage() {
    private val player = Player(world)
    private val beeLeftAtlas = TextureAtlas(Gdx.files.internal("bee_left/bee_left.atlas"))

    init {
        Ceiling(world)
        Walls(world)
        assets.add(player)

        for (bee in 1..12) {
            val flyingBee = BeeLeft(world, beeLeftAtlas)
            flyingBee.randomisePosition()
            assets.add(flyingBee)
        }

        world.setContactListener(MainGameContactListener(this))
    }

    override fun dispose() {
        beeLeftAtlas.dispose()
        super.dispose()
    }

    fun popPlayerBalloon() {
        player.popBalloon()
    }
}