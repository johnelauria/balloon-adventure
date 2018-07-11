package com.twopixeled.balloonadventure.assets.assetTypes

import com.badlogic.gdx.graphics.g2d.SpriteBatch

interface Asset {
    fun draw(batch: SpriteBatch)
    fun dispose()
}