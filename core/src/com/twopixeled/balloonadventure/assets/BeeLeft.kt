package com.twopixeled.balloonadventure.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.*
import com.twopixeled.balloonadventure.assets.assetTypes.Asset
import java.util.Random

class BeeLeft(world: World, beeAtlas: TextureAtlas) : Asset {
    private val beeAnimation: Animation<TextureRegion>
    private val beeBody: Body
    private val beeWidth = Gdx.graphics.width / 20f
    private val beeHeight = Gdx.graphics.height / 14f
    private var animationTime = 0f
    private var isMovingUpDown = false
    private var verticalVelocity = 3f

    init {
        val beeBodyDef = BodyDef()
        val beeShape = PolygonShape()
        val beeFixtureDef = FixtureDef()

        beeAnimation = Animation(0.1f, beeAtlas.regions)
        beeBodyDef.type = BodyDef.BodyType.KinematicBody
        beeBodyDef.position.set(Gdx.graphics.width / 1.5f, Gdx.graphics.height / 2f)

        beeBody = world.createBody(beeBodyDef)
        beeShape.setAsBox(beeWidth / 2, beeHeight / 2)

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
                beeBody.position.x - beeWidth / 2,
                beeBody.position.y - beeHeight / 2,
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
        val minX = Gdx.graphics.width * 1.1f
        val maxX = Gdx.graphics.width * 2.8f
        isMovingUpDown = random.nextBoolean()
        verticalVelocity = if (isMovingUpDown) 3f else 0f

        setBeePosition(
                random.nextFloat() * (maxX - minX) + minX,
                random.nextInt(Gdx.graphics.height).toFloat()
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
        if (beeBody.position.x < beeWidth * -2) {
            randomisePosition()
        } else {
            if (isMovingUpDown && beeBody.position.y > Gdx.graphics.height || beeBody.position.y < 0)
                verticalVelocity *= -1

            setBeePosition(
                    beeBody.position.x - 3f,
                    beeBody.position.y + verticalVelocity
            )
        }
    }
}