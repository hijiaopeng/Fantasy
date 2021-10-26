package com.fantasy.module_home.acts

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.fantasy.common_sdk.base.BaseVMActivity
import com.fantasy.common_sdk.data.Constants
import com.fantasy.module_home.databinding.ActivityHomeBinding
import com.fantasy.module_home.vms.HomeViewModel

@Route(path = Constants.Acts.ACT_HOME)
class HomeActivity : BaseVMActivity<HomeViewModel, ActivityHomeBinding>() {

    override fun viewBinding(): ActivityHomeBinding {
        return ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun createObserver() {

    }
}