package com.fantasy.fantasy.acts

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.fantasy.common_sdk.base.BaseVMActivity
import com.fantasy.common_sdk.data.Constants
import com.fantasy.common_sdk.ext.navigation
import com.fantasy.common_sdk.ext.onSingleClick
import com.fantasy.fantasy.vms.MainViewModel
import com.fantasy.fantasy.databinding.ActivityMainBinding

@Route(path = Constants.Acts.ACT_APP_MAIN)
class MainActivity : BaseVMActivity<MainViewModel, ActivityMainBinding>() {

    override fun viewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun initView(savedInstanceState: Bundle?) {
        mViewBinding.tvSkip.onSingleClick {
            Constants.Acts.ACT_HOME.navigation()
        }

    }

    override fun createObserver() {

    }

}