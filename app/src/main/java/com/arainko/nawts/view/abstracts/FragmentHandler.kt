package com.arainko.nawts.view.abstracts

import androidx.fragment.app.Fragment


abstract class FragmentHandler <F: Fragment>(
    protected val fragment: F
)