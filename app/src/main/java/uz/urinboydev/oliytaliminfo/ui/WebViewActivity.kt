package uz.urinboydev.oliytaliminfo.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import uz.urinboydev.oliytaliminfo.databinding.ActivityWebviewBinding

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebviewBinding
    private var currentUrl: String = ""

    companion object {
        const val EXTRA_URL = "extra_url"
        const val EXTRA_TITLE = "extra_title"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentUrl = intent.getStringExtra(EXTRA_URL) ?: ""
        val title = intent.getStringExtra(EXTRA_TITLE) ?: ""

        setupWebView()
        setupUI(title)
        setupBackPressed()
        loadWebPage()
    }

    private fun setupBackPressed() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.webView.canGoBack()) {
                    binding.webView.goBack()
                } else {
                    finish()
                }
            }
        })
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        binding.webView.apply {
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    showLoading()
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    hideLoading()
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    showError("Sahifani yuklashda xatolik yuz berdi", "Internet aloqasini tekshirib, qayta urinib ko'ring")
                }
            }

            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                setSupportZoom(true)
            }
        }
    }

    private fun setupUI(title: String) {
        binding.toolbarTitle.text = title

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.errorLayout.retryButton.setOnClickListener {
            loadWebPage()
        }
    }

    private fun loadWebPage() {
        if (isNetworkAvailable()) {
            showLoading()
            binding.webView.loadUrl(currentUrl)
        } else {
            showError(
                "Internet aloqasi mavjud emas",
                "Internet aloqangizni tekshirib, qayta urinib ko'ring"
            )
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // API level 23 va undan yuqori versiyalar uchun
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }
        // API level 23 dan past versiyalar uchun
        else {
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.let { capabilities ->
                return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            } ?: false
        }
    }

    private fun showLoading() {
        binding.apply {
            webView.visibility = View.GONE
            errorLayout.root.visibility = View.GONE
            loadingLayout.root.visibility = View.VISIBLE
        }
    }

    private fun hideLoading() {
        binding.apply {
            loadingLayout.root.visibility = View.GONE
            errorLayout.root.visibility = View.GONE
            webView.visibility = View.VISIBLE
        }
    }

    private fun showError(title: String, message: String) {
        binding.apply {
            webView.visibility = View.GONE
            loadingLayout.root.visibility = View.GONE
            errorLayout.apply {
                root.visibility = View.VISIBLE
                errorTitleTextView.text = title
                errorMessageTextView.text = message
            }
        }
    }
}