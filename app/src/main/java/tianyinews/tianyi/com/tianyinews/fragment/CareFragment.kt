package tianyinews.tianyi.com.tianyinews.fragment

import android.os.Bundle
import android.view.View
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.base.BaseFragment
import tianyinews.tianyi.com.tianyinews.databinding.CarefragmentBinding
import tianyinews.tianyi.com.tianyinews.viewmodel.CareVideModel

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/27.
 */
class CareFragment : BaseFragment<CareVideModel,CarefragmentBinding>() {

    override fun layoutId(): Int =R.layout.carefragment

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initData() {
        super.initData()
        mDatabind.careZhuantaiImg.visibility = View.VISIBLE
    }
}