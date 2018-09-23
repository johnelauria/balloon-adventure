package com.twopixeled.balloonadventure.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.twopixeled.balloonadventure.assets.assetTypes.Asset

/**
 * Displays a circular icon to indicate where to touch to make the character jump
 */
class JumpIcon : Asset {
    private val jumpIconTexture = Texture(Gdx.files.internal("jump.png"))
    private val jumpIconSprite = Sprite(jumpIconTexture)

    init {
        jumpIconSprite.setSize(Gdx.graphics.width / 6f, Gdx.graphics.height / 4f)
        jumpIconSprite.setAlpha(0.5f)
        jumpIconSprite.setPosition(
                (Gdx.graphics.width / 1.17f) - (jumpIconSprite.width / 2),
                (Gdx.graphics.height / 4.5f) - (jumpIconSprite.height / 2)
        )
    }

    override fun draw(batch: SpriteBatch) {
        jumpIconSprite.draw(batch)
    }

    override fun dispose() {
        jumpIconTexture.dispose()
    }
}