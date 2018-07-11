package com.twopixeled.balloonadventure.stages

import com.twopixeled.balloonadventure.assets.BeeLeft
import com.twopixeled.balloonadventure.assets.Player

class MainGame : Stage() {

    init {
        assets.add(Player(world))

        for (bee in 1..12) {
            val flyingBee = BeeLeft(world)
            flyingBee.randomisePosition()
            assets.add(flyingBee)
        }
    }
}