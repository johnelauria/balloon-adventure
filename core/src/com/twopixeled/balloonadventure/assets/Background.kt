package com.twopixeled.balloonadventure.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.twopixeled.balloonadventure.assets.assetTypes.Asset

class Background : Asset {
    private val bgTexture = Texture("background.png")
    private val bgSprite1 = Sprite(bgTexture)
    private val bgSprite2 = Sprite(bgTexture)

    init {
        val spriteWidth = Gdx.graphics.width * 2f
        val spriteHeight = Gdx.graphics.height.toFloat()

        bgSprite1.setSize(spriteWidth, spriteHeight)
        bgSprite1.setPosition(0f, 0f)

        bgSprite2.setSize(spriteWidth, spriteHeight)
        bgSprite2.setPosition(spriteWidth, 0f)
    }

    override fun draw(batch: SpriteBatch) {
        bgSprite1.draw(batch)
        bgSprite2.draw(batch)

        bgSprite1.setPosition(bgSprite1.x - 2, 0f)
        bgSprite2.setPosition(bgSprite2.x - 2, 0f)
        loopBackground()
    }

    override fun dispose() {
        bgTexture.dispose()
    }

    /**
     * If the background has completely moved left most of the screen, move it to right most side
     * to loop
     */
    private fun loopBackground() {
        if (bgSprite1.x + bgSprite1.width < 0) {
            bgSprite1.x = bgSprite2.x + bgSprite2.width
        }

        if (bgSprite2.x + bgSprite2.width < 0) {
            bgSprite2.x = bgSprite1.x + bgSprite1.width
        }
    }
}