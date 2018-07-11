package com.twopixeled.balloonadventure.stages

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.twopixeled.balloonadventure.assets.assetTypes.Asset
import com.twopixeled.balloonadventure.assets.assetTypes.Touchable

/**
 * Placeholder to group all assets and have them displayed together. E.g. main menu stage will
 * show buttons, whilst game stage will show characters, backgrounds, obstacles, etc.
 */
abstract class Stage {
    /**
     * List of all assets for this stage. Mind the order of assets. Assets on latter indices
     * will show above the assets on lower indices
     */
    protected val assets = mutableListOf<Asset>()
    protected var world = World(Vector2(0f, -50f), true)

    /**
     * Draws all the assets for this stage
     */
    fun draw(batch: SpriteBatch) {
        assets.forEach { asset: Asset -> asset.draw(batch) }
        for (c in 1..3) world.step(1 / 60f, 6, 2)
    }

    /**
     * Disposes all assets for this stage
     */
    fun dispose() {
        assets.forEach { asset: Asset -> asset.dispose() }
        world.dispose()
    }

    /**
     * Acquire all touchable assets from this stage
     */
    fun getTouchables(): List<Touchable> {
        return assets.filterIsInstance(Touchable::class.java)
    }
}