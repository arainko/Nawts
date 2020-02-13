package com.arainko.nawts.fragments.uiBehaviors.abstracts

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel


abstract class FragmentUIBehavior <F: Fragment>(
    protected val fragment: F
)