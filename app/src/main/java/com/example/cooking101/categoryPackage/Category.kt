package com.example.cooking101.categoryPackage

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Category(

    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int
)