package com.example.streamlinelife
import android.graphics.Color
import android.graphics.drawable.Icon
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateGroupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateGroupFragment : Fragment(){
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_group, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        var selectedGroupColor = 0;
//        val ICON_DIALOG_TAG = "icon-dialog"
//        val mPickColorButton = view.findViewById<Button>(R.id.pick_color_button);
//        val mColorPreview = view.findViewById<View>(R.id.preview_selected_color);
//        var mDefaultColor = 0;
//        mPickColorButton.setOnClickListener { v ->
//            ColorPickerPopup.Builder(view.context).initialColor(
//                Color.RED
//            )                .enableBrightness(
//                    true
//                )
//                .enableAlpha(
//                    true
//                )
//                .okTitle(
//                    "Choose"
//                )
//                .cancelTitle(
//                    "Cancel"
//                )
//                .showIndicator(
//                    true
//                )
//                .showValue(
//                    true
//                )
//                .build()
//                .show(
//                    v,
//                    object : ColorPickerPopup.ColorPickerObserver() {
//                        override fun onColorPicked(color: Int) {
//                            mDefaultColor = color
//                            selectedGroupColor = color
//                            System.out.println(color)
//                            mColorPreview.setBackgroundColor(mDefaultColor)
//                        }
//                    })
//
//            view.findViewById<Button>(R.id.cancel_button).setOnClickListener {
//                findNavController().navigate(R.id.action_createGroupFragment_to_homePage)
//            }
//
//            val db = DBSupport(view.context)
//            val parentLayout: View = view.findViewById(R.id.createReminderFragment)
//            val groupNameInputField = view.findViewById<TextView>(R.id.groupNameInputField)
//            view.findViewById<Button>(R.id.saveGroup).setOnClickListener {
//                if(groupNameInputField.toString()==null){
//                    Snackbar.make(parentLayout, "Please add group name", Snackbar.LENGTH_LONG).show()
//                }
//                var createGroupSuccess = db.addGroup(groupNameInputField.toString(),0);
//                if(createGroupSuccess){
//                    Snackbar.make(parentLayout, "Group Created Successfully", Snackbar.LENGTH_LONG).show()
//                    findNavController().navigate(R.id.action_createGroupFragment_to_homePage)
//                }
//            }
//
//            // code for icon input
//            val groupIconInputField = view.findViewById<TextView>(R.id.groupIconInputField)
//            groupIconInputField.setInputType(InputType.TYPE_NULL);
//            groupIconInputField.setKeyListener(null);
//            val fragmentManager = (activity as FragmentActivity).supportFragmentManager
//            val iconDialog = fragmentManager.findFragmentByTag(ICON_DIALOG_TAG) as IconDialog?
//                ?: IconDialog.newInstance(IconDialogSettings())
//            view.findViewById<TextInputEditText>(R.id.groupIconInputField).setOnClickListener {
//                iconDialog.show(fragmentManager, ICON_DIALOG_TAG)
//            }
//        }
    }
//    override val iconDialogIconPack: IconPack?
//        get() {
//            val mainActivity = MainActivity();
//            return mainActivity.iconPack
//        }
//
//    override fun onIconDialogIconsSelected(
//        dialog: IconDialog,
//        icons: List<com.maltaisn.icondialog.data.Icon>
//    ) {
////        view.findViewById<TextView>(R.id.groupIconInputField).text = icons.map { it.id }.toString()
//        // Show a toast with the list of selected icon IDs.
//        Toast.makeText(this.context, "Icons selected: ${icons.map { it.id }}", Toast.LENGTH_SHORT).show()
//    }

    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreateGroupFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
                CreateGroupFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}