package com.rbhoompally.musiclist.util

import android.app.Activity
import android.content.Context


class SearchHistory(private var maxSize: Int) {
    private var lru: LruStack = LruStack(maxSize)

    fun addToSearches(context: Context, text: String) {
        val sharedPreferences = context.getSharedPreferences(
            "music_list_key",
            Activity.MODE_PRIVATE
        )
        val searches = sharedPreferences.getString("keys", "")
        lru.reset()
        val editor = sharedPreferences.edit()

        val history = searches?.split(",").orEmpty()
        for (i in history) {
            lru.push(i)
        }
        lru.pushHead(text)
        editor.putString("keys", lru.all)
        editor.apply()
    }

    fun getAllSearches(context: Context): List<String> {
        val sharedPreferences = context.getSharedPreferences(
            "music_list_key",
            Activity.MODE_PRIVATE
        )
        val searches = sharedPreferences.getString("keys", "")
        return searches?.split(",").orEmpty()
    }
}

class LruStack(private var size: Int) {
    private var stack: ArrayList<String> = ArrayList()

    val all: String
        get() {
            val str = StringBuilder()
            for (i in 0 until stack.size - 1) {
                str.append(stack[i] + ",")
            }

            return str.deleteCharAt(str.length - 1).toString()
        }

    fun pushHead(keyWord: String) {
        if (stack.remove(keyWord)) {
            stack.add(0, keyWord)
            return
        }

        if (stack.size > this.size - 1) {
            stack.removeAt(stack.size - 1)
            stack.add(0, keyWord)
        } else {
            stack.add(0, keyWord)
        }
    }

    fun push(keyWord: String) {
        if (stack.contains(keyWord)) {
            return
        }

        if (stack.size > this.size - 1) {
            return
        } else {
            stack.add(keyWord)
        }
    }

    fun reset() {
        stack.clear()
    }
}