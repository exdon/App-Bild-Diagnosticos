package br.com.exdon.ope

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionsUtils {
    fun validate(activity: Activity, requestCode: Int, vararg permissions: String ): Boolean {
        val list = ArrayList<String>()
        for (p in permissions) {
            val ok = ContextCompat.checkSelfPermission(activity, p) == PackageManager.PERMISSION_GRANTED

            if (!ok) {
                list.add(p)
            }
        }

        if (list.isEmpty()) return true

        val newP = arrayOfNulls<String>(list.size)
        list.toArray(newP)
        ActivityCompat.requestPermissions(activity, newP, requestCode)
        return false
    }
}