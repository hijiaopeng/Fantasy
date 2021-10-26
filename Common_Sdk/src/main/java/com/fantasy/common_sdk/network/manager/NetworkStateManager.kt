package com.fantasy.common_sdk.network.manager

import com.fantasy.common_sdk.event.EventLiveData

/**
 * 描述：
 *
 * @author JiaoPeng by 2021/5/7
 */
class NetworkStateManager private constructor() {

    val mNetworkStateCallback = EventLiveData<NetState>()

    companion object {
        val instance: NetworkStateManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkStateManager()
        }
    }

}