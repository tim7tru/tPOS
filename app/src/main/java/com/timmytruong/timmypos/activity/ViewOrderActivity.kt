package com.timmytruong.timmypos.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.google.android.gms.common.internal.service.Common
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.model.OrderedItem
import com.timmytruong.timmypos.utils.CommonUtils
import com.timmytruong.timmypos.utils.constants.AppConstants
import kotlinx.android.synthetic.main.activity_view_order.*

class ViewOrderActivity : AppCompatActivity()
{
    private lateinit var orderedItemsArray: ArrayList<OrderedItem>

    private lateinit var buttonAnimation: Animation

    private var subtotal: Double = 0.0

    private var tax: Double = 0.0

    private var total: Double = 0.0

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_view_order)
    
        orderedItemsArray = intent.getSerializableExtra(AppConstants.ORDERED_ITEMS_ARRAY_LIST_INTENT_KEY) as ArrayList<OrderedItem>

        buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_click_anim)

        setOnClickListeners()

        calculateCosts()
    }

    private fun calculateCosts()
    {
        orderedItemsArray.forEach {
            subtotal += it.quantity * it.unitCost
            tax += (subtotal * AppConstants.HST_FACTOR) - subtotal
            total = subtotal + tax
        }

        setInitialTexts()
    }

    private fun setOnClickListeners()
    {
        buttonAnimation.setAnimationListener(AppConstants.interactableAnimListener)

        view_order_cancel.setOnClickListener {
            if (AppConstants.interactable)
            {
                view_order_cancel.startAnimation(buttonAnimation)

                finish()
            }
        }
    }

    private fun setInitialTexts()
    {
        view_order_subtotal_text.text = CommonUtils.formatGeneralCosts(AppConstants.DECIMAL_FORMAT.format(subtotal))

        view_order_tax_text.text = CommonUtils.formatGeneralCosts(AppConstants.DECIMAL_FORMAT.format(tax))

        view_order_total_text.text = CommonUtils.formatGeneralCosts(AppConstants.DECIMAL_FORMAT.format(total))
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus)
        {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }
}
