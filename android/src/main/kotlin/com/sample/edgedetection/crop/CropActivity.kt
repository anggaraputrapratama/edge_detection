package com.sample.edgedetection.crop

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.sample.edgedetection.EdgeDetectionHandler
import com.sample.edgedetection.R
import com.sample.edgedetection.base.BaseActivity
import com.sample.edgedetection.view.PaperRectangle
import kotlinx.android.synthetic.main.activity_crop.*


class CropActivity : BaseActivity(), ICropView.Proxy {

    private var showMenuItems = false

    private lateinit var mPresenter: CropPresenter

    private lateinit var initialBundle: Bundle;

    override fun prepare() {
        this.initialBundle = intent.getBundleExtra(EdgeDetectionHandler.INITIAL_BUNDLE) as Bundle;
        // this.title = initialBundle.getString(EdgeDetectionHandler.CROP_TITLE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        paper.post {
            //we have to initialize everything in post when the view has been drawn and we have the actual height and width of the whole view
            mPresenter.onViewsReady(paper.width, paper.height)
        }
    }

    override fun provideContentViewId(): Int = R.layout.activity_crop


    override fun initPresenter() {
        val initialBundle = intent.getBundleExtra(EdgeDetectionHandler.INITIAL_BUNDLE) as Bundle;
        mPresenter = CropPresenter(this, this, initialBundle)
        findViewById<ImageView>(R.id.crop).setOnClickListener {
            Log.e(TAG, "Crop touched!")
            mPresenter.crop()
//            changeMenuVisibility(true)
            setContentView(R.layout.crop_activity)


            findViewById<ImageView>(R.id.action_label).setOnClickListener {
                Log.e(TAG, "Saved touched!")
                // Bug fix: prevent clicking more than one time
                it.isEnabled = false
                mPresenter.save()
                setResult(Activity.RESULT_OK)
                System.gc()
                finish()
            }

            findViewById<ImageView>(R.id.rotation_image).setOnClickListener {
                Log.e(TAG, "Rotate touched!")
                mPresenter.rotate()

            }



        }

    }

    override fun getPaper(): ImageView = paper

    override fun getPaperRect(): PaperRectangle = paper_rect

    override fun getCroppedPaper(): ImageView = picture_cropped

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.crop_activity_menu, menu)

//        menu.setGroupVisible(R.id.enhance_group, showMenuItems)

        menu.findItem(R.id.rotation_image).isVisible = showMenuItems

//        menu.findItem(R.id.gray).title =
//            initialBundle.getString(EdgeDetectionHandler.CROP_BLACK_WHITE_TITLE) as String
//        menu.findItem(R.id.reset).title =
//            initialBundle.getString(EdgeDetectionHandler.CROP_RESET_TITLE) as String

        if (showMenuItems) {
            menu.findItem(R.id.action_label).isVisible = true
            findViewById<ImageView>(R.id.crop).visibility = View.GONE
        } else {
            menu.findItem(R.id.action_label).isVisible = false
            findViewById<ImageView>(R.id.crop).visibility = View.VISIBLE
        }

        return super.onCreateOptionsMenu(menu)
    }


    private fun changeMenuVisibility(showMenuItems: Boolean) {
        this.showMenuItems = showMenuItems
        invalidateOptionsMenu()
    }

    // handle button activities
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        if (item.itemId == R.id.action_label) {
            Log.e(TAG, "Saved touched!")
            // Bug fix: prevent clicking more than one time
            item.setEnabled(false)
            //
            mPresenter.save()
            setResult(Activity.RESULT_OK)
            System.gc()
            finish()
            return true
        } else if (item.itemId == R.id.rotation_image) {
            Log.e(TAG, "Rotate touched!")
            mPresenter.rotate()
            return true
        }
//        else if (item.itemId == R.id.gray) {
//            Log.e(TAG, "Black White touched!")
//            mPresenter.enhance()
//            return true
//        } else if (item.itemId == R.id.reset) {
//            Log.e(TAG, "Reset touched!")
//            mPresenter.reset()
//            return true
//        }

        return super.onOptionsItemSelected(item)
    }
}
