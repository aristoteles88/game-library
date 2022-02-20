package com.aristotelesjunior.gamelibrary.ui.library

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aristotelesjunior.gamelibrary.database.GameDB
import com.aristotelesjunior.gamelibrary.database.Platform

class LibraryViewModel : ViewModel() {

    private var _platformLiveData = MutableLiveData<ArrayList<Platform>>()
    private val _platformArray = ArrayList<Platform>()

    fun LibraryViewModel() {
        this._platformLiveData = MutableLiveData<ArrayList<Platform>>()

        // call your Rest API in init method
//        init()
    }
    fun getLibraryMutableLiveData(): MutableLiveData<ArrayList<Platform>> {
        return this._platformLiveData
    }

//    fun init() {
//        populateList()
//        userLiveData.setValue(userArrayList)
//    }
//
//    fun populateList() {
//        val platformDao = GameDB.getInstance(context).platformDao()
//        val platform = Platform()
//        platform.name =
//        user.setDescription("Best rating movie")
//        userArrayList = ArrayList()
//        userArrayList.add(user)
//        userArrayList.add(user)
//        userArrayList.add(user)
//        userArrayList.add(user)
//        userArrayList.add(user)
//        userArrayList.add(user)
//    }
}