package tianyinews.tianyi.com.network

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.GsonBuilder
import com.rz.commonlibrary.network.BaseNetworkApi
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.internal.cache.CacheInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tianyinews.tianyi.com.network.api.HomeService
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/5 4:07 下午
 * @Description:
 */

//双重校验锁式-单例 封装NetApiService 方便直接快速调用简单的接口
val apiService: HomeService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    NetworkApi.INSTANCE.getApi(HomeService::class.java, HomeService.NEWS_URL)
}

//双重校验锁式-单例 封装NetApiService 方便直接快速调用简单的接口
val apiVideoService: HomeService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    NetworkApi.INSTANCE.getApi(HomeService::class.java, HomeService.VIDEO_URL)
}



class NetworkApi: BaseNetworkApi() {

    companion object{
        val INSTANCE: NetworkApi by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkApi()
        }
    }

    /**
     * 实现重写父类的setRetrofitBuilder方法，
     * 在这里可以对Retrofit.Builder做任意操作，比如添加GSON解析器，protobuf等
     */
    override fun setRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder {
        return builder.apply {
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        }
    }

    /**
     * 实现重写父类的setHttpClientBuilder方法，
     * 在这里可以添加拦截器，可以对 OkHttpClient.Builder 做任意操作
     */
    override fun setHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        builder.apply {
            //设置缓存配置 缓存最大10M
//            cache(Cache(File(appContext.cacheDir, "cxk_cache"), 10 * 1024 * 1024))
            //添加Cookies自动持久化
//            cookieJar(cookieJar)
            //示例：添加公共heads 注意要设置在日志拦截器之前，不然Log中会不显示head信息
//            addInterceptor(MyHeadInterceptor())
            //添加缓存拦截器 可传入缓存天数，不传默认7天
//            addInterceptor(CacheInterceptor())
            // 日志拦截器
//            addInterceptor(LogInterceptor())
            //超时时间 连接、读、写
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(5, TimeUnit.SECONDS)
            writeTimeout(5, TimeUnit.SECONDS)
        }
        return builder
    }

//    val cookieJar: PersistentCookieJar by lazy {
//        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(appContext))
//    }
}