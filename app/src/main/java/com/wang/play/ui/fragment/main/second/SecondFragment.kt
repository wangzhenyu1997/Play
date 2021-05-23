package com.wang.play.ui.fragment.main.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.wang.mylibrary.util.MyApplicationLogUtil
import com.wang.play.MyApplication
import com.wang.play.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private val secondViewModel: SecondViewModel by viewModels()
    private lateinit var binding: FragmentSecondBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            FragmentSecondBinding.inflate(inflater, container, false)

        return binding.root


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.button3.setOnClickListener {
            secondViewModel.loadNews(MyApplication.APP_KEY)
        }

        secondViewModel.news.observe(viewLifecycleOwner) {
            if(it == null)
            {
                MyApplicationLogUtil.d("TodayOne","当前数据为空")
            }else{
                MyApplicationLogUtil.d("TodayOne",it.reason)
            }
        }


    }

    // findNavController().popBackStack()
    //sharedElementEnterTransition = TransitionInflater.from(requireContext())
    //            .inflateTransition(R.transition.shared_image)


}


//最后是UI层。
//
//
//
//class ProjectFragment : Fragment {
//
//    override fun initData() {
//        //请求数据，调用loadProjectTree
//        mViewModel?.loadProjectTree()
//        mViewModel?.mProjectTreeLiveData?.observe(this, Observer {
//            //更新UI
//        })
//    }
//
//UI层开始调用ViewModel的请求方法执行网络请求，LiveData注册一个观察者，观察数据变化，并且更新UI。
//
//
//
//到这里，网络请求的逻辑基本上通顺了。
//
//
//
//在一切环境正常的情况下，上面的请求是可以的，但是app还存在网络不畅，异常，数据为null的情况，上述就不在满足要求了，接下来就开始对数据异常的情况进行处理。
//
//
//
//2
//网络请求异常处理
//
//
//对于协程异常的处理，Android开发者的官网上也给出了答案 ，直接对网络请求进行一个try-catch处理，发生异常了，直接在catch中做出相应动作就ok了，我们就来看看具体实现。
//
//https://developer.android.google.cn/kotlin/coroutines?hl=zh-cn
//
//
//
//class ProjectViewModel : ViewModel(){
//      //LiveData
//      val mProjectTreeLiveData = MutableLiveData<List<ProjectTree>>()
//      fun loadProjectTree() {
//        viewModelScope.launch(Dispatchers.IO) {
//          try {
//                  val data = mRepo.loadProjectTree()
//                  mProjectTreeLiveData.postValue(data)
//               } catch (e: Exception) {
//                    //异常
//                    error(e)
//               } finally {
//
//               }
//        }
//    }
//}
//
//还是在ViewModel层，对mRepo.loadProjectTree()的请求加上了try-catch块，当发生异常时根据Exception类型对用户做出提示。
//
//
//
//到这里，异常的来源已经找到了，接着就需要将异常显示在UI层来提醒用户。我们都知道mProjectTreeLiveData利用PostValue将数据分发给了UI，如法炮制，也就可以利用LiveData将异常也分发给UI。
//
//
//
//说干就干。
//
//
//
//3
//网络请求状态封装
//
//
//1、 [Error状态]
//
//
//依旧在ViewModel层，我们新添加一个针对异常的LiveData：errorLiveData。
//
//
//
//class ProjectViewModel : ViewModel(){
//      //异常LiveData
//      val errorLiveData = MutableLiveData<Throwable>()
//      //LiveData
//      val mProjectTreeLiveData = MutableLiveData<List<ProjectTree>>()
//      fun loadProjectTree() {
//        viewModelScope.launch(Dispatchers.IO) {
//          try {
//                  val data = mRepo.loadProjectTree()
//                  mProjectTreeLiveData.postValue(data)
//               } catch (e: Exception) {
//                    //异常
//                    error(e)
//                    errorLiveData.postValue(e)
//               } finally {
//
//               }
//        }
//    }
//}
//
//在UI层，利用errorLiveData注册一个观察者，如果有异常通知，则显示异常的UI（UI层代码省略）。这样确实可以实现我们一开始要的功能：请求成功则显示成功界面，失败显示异常界面。但是有一个问题，就是不够优雅，如果有多个ViewModel，多个UI，那就要每个页面都要写errorLiveData，很冗余。
//
//
//
//那我们可以将公共方法抽离出来，新建一个BaseViewModel类。
//
//
//
//open class BaseViewModel : ViewModel() {
//     val errorLiveData = MutableLiveData<Throwable>()
//
//     fun launch(
//          block: suspend () -> Unit,
//          error: suspend (Throwable) -> Unit,
//          complete: suspend () -> Unit
//     ) {
//          viewModelScope.launch(Dispatchers.IO) {
//               try {
//                    block()
//               } catch (e: Exception) {
//                    error(e)
//               } finally {
//                    complete()
//               }
//          }
//     }
//
//
//}
//
//除了定义errorLiveData外，还将新建协程的操作放到其中，开发者只需要将每个ViewModel继承BaseViewModel，重写launch（）即可，那么上面的案例中的ViewModel就修改成下面这种。
//
//
//
//class ProjectViewModel : BaseViewModel(){
//
//      //LiveData
//      val mProjectTreeLiveData = MutableLiveData<List<ProjectTree>>()
//      fun loadProjectTree() {
//        launch(
//            {
//                val state = mRepo.loadProjectTree()
//                mProjectTreeLiveData.postValue(state.data)
//            },
//            {
//                errorLiveData.postValue(it)
//            },
//            {
//                loadingLiveData.postValue(false)
//            }
//        )
//    }
//}
//
//同样的，UI层也可以新建一个BaseFragment抽象类，在onViewCreated中利用errorLiveData注册观察者，收到异常通知，则进行相应的动作。
//
//
//
//abstract class BaseFragment<T : ViewDataBinding, VM : BaseViewModel> : Fragment(){
//
//   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        mViewModel = getViewModel()
//
//        mViewModel?.errorLiveData?.observe(viewLifecycleOwner, Observer {
//            Log.d(TAG, "onViewCreated: error ")
//            showError()
//            throwableHandler(it)
//        })
//    }
//}
//
//每个子Fragment只需要继承BaseFragment即可，具体的异常监听就不用开发者管理。
//
//
//
//2、 [Loading状态]
//
//
//除了异常状态外，请求必不可少的就是Loading，这里Loading分为两种，一种是整个页面替换为Loading，例如Recyclerview列表时，就可以直接整个页面先Loading，而后显示数据；还有一种是数据界面不替换，只是个Loading Dialog显示在上层，例如点击登录时，需要一个loading。
//
//
//
//Loading和异常处理的思路一致，可以在BaseViewModel中添加一个LoadingLiveData，数据类型为Boolean，在每个请求一开始LoadingLiveData.postValue(true)，结束请求或者请求异常时，就LoadingLiveData.postValue(false)。UI层BaseFragment中，则可以监听LoadingLiveData发出的是true还是false，以便对Loading的显示和隐藏进行控制。
//
//
//
//ViewModel层：
//
//
//
//open class BaseViewModel : ViewModel() {
//     //加载中
//     val loadingLiveData = SingleLiveData<Boolean>()
//     //异常
//     val errorLiveData = SingleLiveData<Throwable>()
//
//     fun launch(
//          block: suspend () -> Unit,
//          error: suspend (Throwable) -> Unit,
//          complete: suspend () -> Unit
//     ) {
//          loadingLiveData.postValue(true)
//          viewModelScope.launch(Dispatchers.IO) {
//               try {
//                    block()
//               } catch (e: Exception) {
//                    Log.d(TAG, "launch: error ")
//                    error(e)
//               } finally {
//                    complete()
//               }
//          }
//     }
//}
//
//在BaseViewModel 中launch一开始就通知Loading显示，在try-catch-finally代码块的finally中将请求结束的通知分发出去。
//
//
//
//UI层：
//
//
//
//abstract class BaseFragment<T : ViewDataBinding, VM : BaseViewModel> : Fragment(){
//
//   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        mViewModel = getViewModel()
//        //Loading 显示隐藏的监听
//        mViewModel?.loadingLiveData?.observe(viewLifecycleOwner, Observer {
//            if (it) {
//                //show loading
//                showLoading()
//            } else {
//
//                dismissLoading()
//            }
//        })
//
//        //请求异常的监听
//        mViewModel?.errorLiveData?.observe(viewLifecycleOwner, Observer {
//            Log.d(TAG, "onViewCreated: error ")
//            showError()
//            throwableHandler(it)
//        })
//    }
//}
//
//注册一个loading的观察者，当通知为true时，显示loading，false则隐藏。
//
//
//
//3、 [Empty状态]
//
//
//数据为空的状态发生在请求成功后，对于这种情况，可以直接在UI层中，请求成功的监听中对数据是否为null进行判断。
//
//
//
//到这里，网络请求的基本封装已经完成，但是在运行测试的过程中，存在几个问题需要去解决，例如网络不通的情况下try-catch却不会抛出异常。接下来就开始进行二次封装。
//
//
//
//4
//暴露问题二次封装
//
//
//问题一：网络请求异常，try-catch却不会将异常抛出。
//
//
//因为业务场景比较复杂，只依赖try-catch来获取异常，明显也会有所遗漏，那这种情况下我们可以直接以服务器返回的code，作为请求状态的依据。以上面Wanandroid的api为例，当errorCode=0时，则表示请求成功，其他的值都表示失败，那这就好办了。
//
//
//
//我们新建一个密封类ResState，存放Success和Error状态，
//
//
//
//sealed class ResState<out T : Any> {
//    data class Success<out T : Any>(val data: T) : ResState<T>()
//    data class Error(val exception: Exception) : ResState<Nothing>()
//}
//
//对Repository层请求返回的数据进行code判断处理，新建一个BaseRepository类。
//
//
//
//open class BaseRepository() {
//
//    suspend fun <T : Any> executeResp(
//        resp: BaseResp<T>, successBlock: (suspend CoroutineScope.() -> Unit)? = null,
//        errorBlock: (suspend CoroutineScope.() -> Unit)? = null
//    ): ResState<T> {
//        return coroutineScope {
//            if (resp.errorCode == 0) {
//                successBlock?.let { it() }
//                ResState.Success(resp.data)
//            } else {
//                Log.d(TAG, "executeResp: error")
//                errorBlock?.let { it() }
//                ResState.Error(IOException(resp.errorMsg))
//            }
//        }
//    }
//
//}
//
//当errorCode == 0时，将ResState置为Success并将数据返回，errorCode !=0时，则将状态置为Error并将Exception返回。而子Repository则只需要继承BaseRepository即可。
//
//
//
//class ProjectRepo : BaseRepository() {
//
//    suspend fun loadProjectTree(): ResState<List<ProjectTree>> {
//        return executeResp(mService.loadProjectTree())
//    }
//
//修改后返回值用ResState<>包裹，并直接将请求的结果传给executeResp（）方法，而ViewModel中也做出相应的修改。
//
//
//
//class ProjectViewModel : BaseViewModel() {
//    val mProjectTreeLiveData = MutableLiveData<List<ProjectTree>>()
//
//    fun loadProjectTree() {
//        launch(
//            {
//                val state = mRepo.loadProjectTree()
//                //添加ResState判断
//                if (state is ResState.Success) {
//                    mProjectTreeLiveData.postValue(state.data)
//                } else if (state is ResState.Error) {
//                    Log.d(TAG, "loadProjectTree: ResState.Error")
//                    errorLiveData.postValue(state.exception)
//                }
//            },
//            {
//                errorLiveData.postValue(it)
//            },
//            {
//                loadingLiveData.postValue(false)
//            }
//        )
//    }
//}
//
//ViewModel层新增了一个ResState判断，通过请求的返回值ResState，如果是ResState.Success则将数据通知给UI，如果是ResState.Error，则将异常通知给UI。
//
//
//
//以服务器返回的code值进行判断，无疑是最准确的。
//
//
//
//问题二：errorLiveData注册观察者一次后，不管请求失败还是成功，它还是会收到通知。
//
//
//这是MutableLiveData的一个特性，只要当注册的观察者处于前台时，都会收到通知。那这个特性又影响了什么呢？我在errorLiveData的监听中，对不同的异常进行了Toast的弹出提醒，如果每次进入一个页面，虽然请求成功了，但是因为errorLiveData还是能接收到通知，就会弹出一个Toast提醒框。现象如下：
//
//
//
//图片
//
//
//
//那我们针对MutableLiveData将其修改为单事件响应的liveData，只有一个接收者能接收到信息，可以避免不必要的业务的场景中的事件消费通知。
//
//
//
//class SingleLiveData<T> : MutableLiveData<T>() {
//
//    private val mPending = AtomicBoolean(false)
//
//    @MainThread
//    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
//
//        if (hasActiveObservers()) {
//            Log.w(TAG, "多个观察者存在的时候，只会有一个被通知到数据更新")
//        }
//
//        super.observe(owner, Observer { t ->
//            if (mPending.compareAndSet(true, false)) {
//                observer.onChanged(t)
//            }
//        })
//
//    }
//
//    override fun setValue(value: T?) {
//        mPending.set(true)
//        super.setValue(value)
//    }
//
//    @MainThread
//    fun call() {
//        value = null
//    }
//
//    companion object {
//        private const val TAG = "SingleLiveData"
//    }
//}
//
//
//将BaseViewModel中的MutableLiveData替换为SingleLiveData就可以了。