package com.example.sneakerapp.helpers

import android.content.Context
import android.content.Context.MODE_PRIVATE

class prefsHelper {
    //share prefs are used in key value approach
    //will use companion object
    companion object{
        //save to prefs
        fun savePrefs(context: Context, key:String, value:String){
            val savePrefs = context.getSharedPreferences("storage", MODE_PRIVATE)
            val editor = savePrefs.edit()
            editor.putString(key,value)
            editor.apply()
        }//end of save function
        //        get from prefs
        fun getPrefs(context:Context,key:String):String{
            val prefs = context.getSharedPreferences("storage", MODE_PRIVATE)
            val input_name = prefs.getString(key,"")
            return input_name.toString()
        }//end of get
        //remove an item from prefs

        fun clearPrefsByKey(context: Context,key:String){
            val prefs = context.getSharedPreferences("storage", MODE_PRIVATE)
            val editor = prefs.edit()
            editor.remove(key)
            editor.apply()

        }//end of clear by key
        //clear all items from prefs
        fun clearPrefs(context: Context){
            val prefs = context.getSharedPreferences("storage", MODE_PRIVATE)
            val editor = prefs.edit()
            editor.clear()
            editor.apply()
        }//end of clear
    }
}