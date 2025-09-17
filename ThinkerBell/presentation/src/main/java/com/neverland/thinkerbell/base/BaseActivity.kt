package com.neverland.thinkerbell.base

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.neverland.thinkerbell.custom.CustomToast
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    private var _binding: VB? = null
    val binding get() = _binding!!

    private var currentToast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflateBinding()
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
        initListener()
        setObserver()
    }

    // ViewBinding 인스턴스를 자동으로 생성하는 함수
    @Suppress("UNCHECKED_CAST")
    private fun inflateBinding(): VB {
        val clazz = (javaClass.genericSuperclass as? ParameterizedType)
            ?.actualTypeArguments?.get(0) as? Class<VB>
            ?: throw IllegalStateException("ViewBinding class not found")

        val method: Method = clazz.getMethod("inflate", LayoutInflater::class.java)
        return method.invoke(null, layoutInflater) as VB
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    protected fun showToast(msg: String) {
        currentToast?.cancel()
        currentToast = CustomToast.makeToast(this, msg)
        currentToast?.show()
    }

    protected abstract fun initView()
    protected open fun initListener() {}
    protected open fun setObserver() {}

    fun setStatusBarColor(colorId: Int, isLightIcon: Boolean) {
        binding.root.background = ContextCompat.getDrawable(this, colorId)
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = !isLightIcon
        }
    }

    fun replaceFragment(
        frameLayoutId: Int,
        fragment: Fragment,
        isAddBackStack: Boolean,
        isStackClear: Boolean = false,
    ) {
        val fragmentManager = supportFragmentManager
        if (isStackClear) fragmentManager.popBackStack(
            null,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )

        val ft = fragmentManager.beginTransaction()
        ft.replace(frameLayoutId, fragment)
        if (isAddBackStack) ft.addToBackStack(null)
        ft.commit()
    }
}
