package com.twopixeled.balloonadventure.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.*
import com.twopixeled.balloonadventure.assets.assetTypes.Asset
import com.twopixeled.balloonadventure.configs.PIXEL_TO_METER
import com.twopixeled.balloonadventure.configs.SCREEN_HEIGHT
import com.twopixeled.balloonadventure.configs.SCREEN_WIDTH
import java.util.Random

class BeeLeft(world: World, beeAtlas: TextureAtlas) : Asset {
    private val beeAnimation: Animation<TextureRegion>
    private val beeBody: Body
    private val beeWidth = 70f
    private val beeHeight = 90f
    private var animationTime = 0f
    private var isMovingUpDown = false
    private var verticalVelocity = 2f

    init {
        val beeBodyDef = BodyDef()
        val beeShape = PolygonShape()
        val beeFixtureDef = FixtureDef()

        beeAnimation = Animation(0.1f, beeAtlas.regions)
        beeBodyDef.type = BodyDef.BodyType.KinematicBody
        beeBodyDef.position.set(
                SCREEN_WIDTH / 2 / PIXEL_TO_METER,
                SCREEN_HEIGHT / 2 / PIXEL_TO_METER
        )

        beeBody = world.createBody(beeBodyDef)
        beeShape.setAsBox(beeWidth / 2 / PIXEL_TO_METER, beeHeight / 2 / PIXEL_TO_METER)

        beeFixtureDef.shape = beeShape
        beeFixtureDef.density = 1f

        beeBody.createFixture(beeFixtureDef)
        beeBody.userData = this
        beeShape.dispose()
    }

    override fun draw(batch: SpriteBatch) {
        animationTime += Gdx.graphics.deltaTime * 1.5f
        val beeRegion = beeAnimation.getKeyFrame(animationTime, true)

        batch.draw(
                beeRegion,
                (beeBody.position.x * PIXEL_TO_METER) - beeWidth / 2,
                (beeBody.position.y * PIXEL_TO_METER) - beeHeight / 2,
                beeWidth / 2,
                beeHeight / 2,
                beeWidth,
                beeHeight,
                1f,
                1f,
                1f
        )

        moveBee()
    }

    override fun dispose() {
        // beeAtlas is disposed by MainGame
    }

    /**
     * Moves this bee to a random position from the right most side of the screen. Basically
     * giving an illusion as if another bee is passing through
     */
    fun randomisePosition() {
        val random = Random()
        val minX = SCREEN_WIDTH * 1.2f
        val maxX = SCREEN_WIDTH * 2
        isMovingUpDown = random.nextBoolean()
        verticalVelocity = if (isMovingUpDown) 3 / PIXEL_TO_METER else 0f

        setBeePosition(
                (random.nextFloat() * (maxX - minX) + minX) / PIXEL_TO_METER,
                random.nextInt(SCREEN_HEIGHT.toInt()).toFloat() / PIXEL_TO_METER
        )
    }

    /**
     * Sets the position of the bee in the given x & y coordinates
     */
    private fun setBeePosition(x: Float, y: Float) {
        beeBody.setTransform(x, y, 1f)
    }

    /**
     * Moves the bee from right to left. By change, the bee may also move up and down, or just
     * fly straight
     */
    private fun moveBee() {
        val beePosX = beeBody.position.x
        val beePosY = beeBody.position.y

        if (beePosX < -beeWidth / PIXEL_TO_METER) {
            randomisePosition()
        } else {
            if (isMovingUpDown && beePosY < SCREEN_HEIGHT || beePosY < 0)
                verticalVelocity *= -1

            setBeePosition(
                    beePosX - (5 / PIXEL_TO_METER),
                    beePosY + verticalVelocity
            )
        }
    }
}