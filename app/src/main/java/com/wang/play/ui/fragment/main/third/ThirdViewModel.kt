package com.wang.play.ui.fragment.main.third

import androidx.lifecycle.ViewModel
import com.wang.play.MyApplication
import com.wang.play.datasource.room.database.AppDatabase

class ThirdViewModel : ViewModel() {

    //操作数据库
    private val useDatabase = AppDatabase.getInstance(MyApplication.context)?.pictureDao()

    //page: >=1
    //count: [10, 50]
//    suspend fun loadBeautiful(adapter: TestAdapter, page: String, count: String) {
//
//        try {
//            val response: Beautiful =
//                CreateService.create2<BeautifulAPI>().beautifulLoad(page, count)
//            var tempNumber: Long = 1
//
//            val pictureList = mutableListOf<Picture>()
//
//            for (it in response.data) {
//                val temp = Picture(it._id, it.desc, it.url, it.type)
//                temp.uid = tempNumber++
//                pictureList.add(temp)
//            }
//
//            MyApplicationLogUtil.d("Today_one", "1111")
//
//            useDatabase?.updatePicture(*pictureList.toTypedArray())
//
//            MyApplicationLogUtil.d("Today_one", "222")
//
//            withContext(Dispatchers.Main) {
//                adapter.submitList(response.data)
//            }
//
////
////            val test: List<Beautiful.Data> = listOf(
////                Beautiful.Data(
////                    "1",
////                    "http://gank.io/images/ccf0316264d245018fc651cffa6e90de"
////                ),
////                Beautiful.Data("2", "http://gank.io/images/ccf0316264d245018fc651cffa6e90de")
////            )
////            MyApplicationLogUtil.d("AWER", "这里是成功的")
////            withContext(Dispatchers.Main) { adapter.submitList(test) }
//
//        } catch (e: Exception) {
//            // MyApplicationLogUtil.d("AWER", e.printStackTrace())
//            //  MyApplicationLogUtil.d("AWER", e.message)
//
//            MyApplicationLogUtil.d("Today_one", "${e.printStackTrace()}     ${e.message}")
//
////            withContext(Dispatchers.Main) {
////                Toast.makeText(
////                    MyApplication.context,
////                    "${e.printStackTrace()}     ${e.message}",
////                    Toast.LENGTH_LONG
////                ).show()
////            }
//
//            withContext(Dispatchers.Main) {
//                Toast.makeText(
//                    MyApplication.context,
//                    "请检查网络连接",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//
//    }


}