package com.wang.mylibrary.permission

import androidx.fragment.app.FragmentActivity

object CheckPermission {

    private const val TAG = "InvisibleFragment"

    //FragmentManager类负责对应用的Fragment执行一些操作,如添加，移除或者替换它们，以及将他们添加到返回堆栈

    //访问FragmentManager
    //在Activity中，每个FragmentActivity及其子类（如AppCompatActivity）都可以通过 getSupportFragmentManager()
    //方法访问FragmentManager。
    //在Fragment中访问
    //Fragment也能够托管一个或多个子Fragment。在Fragment内,可以通过getChildFragmentManager()
    //获取对管理Fragment子级的FragmentManager的引用。如果您需要访问其宿主FragmentManager，可以使用getParentFragmentManager()。

    fun request(
        activity: FragmentActivity,
        vararg permissions: String,
        callback: PermissionCallback
    ) {
        val fragmentManager = activity.supportFragmentManager

        val exitedFragment = fragmentManager.findFragmentByTag(TAG)

        val fragment = if (exitedFragment != null) {
            exitedFragment as InvisibleFragment
        } else {
            val invisibleFragment = InvisibleFragment()



            fragmentManager.beginTransaction().add(invisibleFragment, TAG).commitNow()
            invisibleFragment
        }

        fragment.requestNow(callback, *permissions)
    }

}


//检查权限，并返回需要申请的权限列表
//    private List<String> checkPermission(Context context, String[] checkList) {
//        List<String> list = new ArrayList<>();
//        for (int i = 0; i < checkList.length; i++) {
//            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(context, checkList[i])) {
//                list.add(checkList[i]);
//            }
//        }
//        return list;
//    }
//
//    //申请权限
//    private void requestPermission(Activity activity, String requestPermissionList[]) {
//        ActivityCompat.requestPermissions(activity, requestPermissionList, 100);
//    }
//
//    //用户作出选择后，返回申请的结果
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == 100) {
//            for (int i = 0; i < permissions.length; i++) {
//                if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//                        Toast.makeText(MainActivity.this, "存储权限申请成功", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(MainActivity.this, "存储权限申请失败", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        }
//    }
//
//    //测试申请存储权限
//    private void testPermission(Activity activity) {
//        String[] checkList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
//        List<String> needRequestList = checkPermission(activity, checkList);
//        if (needRequestList.isEmpty()) {
//            Toast.makeText(MainActivity.this, "无需申请权限", Toast.LENGTH_SHORT).show();
//        } else {
//            requestPermission(activity, needRequestList.toArray(new String[needRequestList.size()]));
//        }
//    }
