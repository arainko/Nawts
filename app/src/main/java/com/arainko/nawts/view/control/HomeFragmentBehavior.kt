package com.arainko.nawts.view.control

import android.view.View
import androidx.core.app.SharedElementCallback
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.arainko.nawts.R
import com.arainko.nawts.view.containters.BottomSheetCustomizerFragment
import com.arainko.nawts.view.containters.HomeFragment
import com.arainko.nawts.view.abstracts.FragmentUIBehavior
import com.arainko.nawts.view.abstracts.HolderBehavior
import com.arainko.nawts.view.abstracts.StartDragListener
import com.arainko.nawts.persistence.entities.Note
import com.arainko.nawts.view.containters.HomeFragmentDirections
import com.arainko.nawts.view.containters.MainActivity
import com.arainko.nawts.view.elements.NoteHolder
import com.google.android.material.transition.Hold
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragmentBehavior(fragment: HomeFragment, private val modelActions: NoteViewModel) :
    FragmentUIBehavior<HomeFragment>(fragment),
    HolderBehavior<NoteHolder>,
    StartDragListener {

    val fabOnClickListener = View.OnClickListener {
//        val options = navOptions {
//            anim {
//                enter = R.anim.slide_in_right
//                exit = R.anim.slide_out_left
//                popEnter = R.anim.slide_in_left
//                popExit = R.anim.slide_out_right
//            }
//        }

        val emptyNote = Note("", "")
        fragment.fab.transitionName = emptyNote.transitionName
        val extras = FragmentNavigatorExtras(
            fragment.fab to emptyNote.transitionName
        )

        val action = HomeFragmentDirections
            .actionToEditingFragment(emptyNote, modelActions.maxOrder)

        findNavController(fragment).navigate(action, extras)
    }

    val recyclerViewSwipeToDismissListener: ItemTouchHelper =
        SwipeDragCallback(fragment, modelActions).callBack


    override fun onHolderClick(holder: NoteHolder) {
        // TODO: SharedElementTransition for holders
        MainActivity.holderPosition = holder.adapterPosition
//        val options = navOptions {
//            anim {
//                enter = R.anim.slide_in_right
//                exit = R.anim.slide_out_left
//                popEnter = R.anim.slide_in_left
//                popExit = R.anim.slide_out_right
//            }
//        }

        val extras = FragmentNavigatorExtras(
            holder.itemView to holder.itemView.transitionName
        )

        val action = HomeFragmentDirections
            .actionToEditingFragment(holder.note, modelActions.maxOrder)

        findNavController(fragment).navigate(action, extras)
    }


    override fun onHolderLongClick(holder: NoteHolder): Boolean {
        val bottomSheet =
            BottomSheetCustomizerFragment(
                modelActions,
                holder,
                fragment.noteAdapter
            )
        bottomSheet.show(fragment.activity!!.supportFragmentManager, "COS")
        return true
    }

    override fun requestDrag(viewHolder: RecyclerView.ViewHolder) {
        recyclerViewSwipeToDismissListener.startDrag(viewHolder)
    }

    val onExitSharedElementCallback = object : SharedElementCallback() {

        override fun onMapSharedElements(
            names: MutableList<String>?,
            sharedElements: MutableMap<String, View>?
        ) {
            val selectedViewHolder = fragment.recyclerView
                .findViewHolderForAdapterPosition(MainActivity.holderPosition)

            if (selectedViewHolder?.itemView == null) {
                return
            }

            names?.get(0)?.let { sharedElements?.put(it, selectedViewHolder.itemView) }
        }
    }


}