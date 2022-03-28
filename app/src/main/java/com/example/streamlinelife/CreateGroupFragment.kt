package com.example.streamlinelife
import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.fragment.findNavController
import com.maltaisn.icondialog.IconDialog
import com.maltaisn.icondialog.IconDialogSettings
import com.maltaisn.icondialog.data.Icon
import com.maltaisn.icondialog.pack.IconPack
import com.maltaisn.icondialog.pack.IconPackLoader
import com.maltaisn.iconpack.defaultpack.createDefaultIconPack
import top.defaults.colorpicker.ColorPickerPopup

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateGroupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateGroupFragment : Fragment(), IconDialog.Callback{

    var iconPack: IconPack? = null
    private lateinit var saveicon: Drawable
    private lateinit var showIconuserselected: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_group, container, false)
    }

    /**
     *
     * “How to create a color picker tool in android using color wheel and slider?,” GeeksforGeeks, 22-Oct-2020.
     * [Online]. Available: https://www.geeksforgeeks.org/how-to-create-a-color-picker-tool-in-android-using-color-wheel-and-slider/.
     *  [Accessed: 27-Mar-2022].
     *
     *  Maltaisn, “Example application · MALTAISN/icondialoglib wiki,” GitHub. [Online]. Available: https://github.com/maltaisn/icondialoglib/wiki/Example-application. [Accessed: 27-Mar-2022].
     *
     *  Maltaisn, “Maltaisn/icondialoglib: Material icon picker dialog for Android,” GitHub. [Online]. Available: https://github.com/maltaisn/icondialoglib. [Accessed: 27-Mar-2022].
     */
    @SuppressLint("ResourceType", "UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //group title
        val grptitle = view.findViewById<TextView>(R.id.grpNameInputInCreateGroup)

        // for color
        val pickcolor = view.findViewById<Button>(R.id.pickingcolor)
        val viewofcolor = view.findViewById<View>(R.id.showcoloruserselected)

        // buttons
        val savebutton = view.findViewById<Button>(R.id.savegroupcolorIncreategroup)
        val cancelbutton = view.findViewById<Button>(R.id.cancelButtonIncreategroup)

        //set color
        var defaultcolor = resources.getColor(R.color.GroupDefaultcolor)

        pickcolor.setOnClickListener {
            ColorPickerPopup.Builder(requireContext())
                .initialColor(defaultcolor)
                .enableBrightness(true)
                .enableAlpha(true)
                .okTitle("Choose Any Color")
                .cancelTitle("Cancel").showIndicator(true)
                .showValue(true)
                .build()
                .show(this.requireView(), object: ColorPickerPopup.ColorPickerObserver(){
                    override fun onColorPicked(color: Int){
                        defaultcolor = color
                        viewofcolor.setBackgroundColor(defaultcolor)
                    }
                })
        }

        val loader = IconPackLoader(requireContext())
        val iconPack = createDefaultIconPack(loader)
        iconPack.loadDrawables(loader.drawableLoader)
        this.iconPack = iconPack

        val pickingicons = view.findViewById<Button>(R.id.pickingicons)

        //getting the icon dialog
        val iconDialog = activity?.supportFragmentManager?.findFragmentByTag("icon-dialog") as IconDialog?
            ?: IconDialog.newInstance(IconDialogSettings())

        pickingicons.setOnClickListener {
            iconDialog.show(childFragmentManager,"icon-dialog")
        }

        // save button
        savebutton.setOnClickListener {
            if(grptitle.text.toString().trim().length != 0){
                val addgrp = DBSupport(requireContext())
                addgrp.addGroup(grptitle.text.toString(), 0, defaultcolor.toString(), saveicon.toString())
                Snackbar.make(view,"Create Group Successfully", Snackbar.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_createGroupFragment_to_homePage)
            }
            else{
                Snackbar.make(view,"Can't Store Group Without Title", Snackbar.LENGTH_LONG).show()
            }
        }
        cancelbutton.setOnClickListener {
            findNavController().navigate(R.id.action_createGroupFragment_to_homePage)
        }
    }

    /**
     *
     * Maltaisn, “Example application · MALTAISN/icondialoglib wiki,” GitHub. [Online]. Available: https://github.com/maltaisn/icondialoglib/wiki/Example-application. [Accessed: 27-Mar-2022].
     *
     * Maltaisn, “Maltaisn/icondialoglib: Material icon picker dialog for Android,” GitHub. [Online]. Available: https://github.com/maltaisn/icondialoglib. [Accessed: 27-Mar-2022].
     *
     * The icon pack to be displayed by the dialog.
     * All icon drawables in the pack must have been loaded, or they won't be displayed.
     *
     * If `null` is returned, the icon dialog will periodically try to get the icon
     * pack while showing a progress indicator, until it no longer returns `null`.
     */
    override val iconDialogIconPack: IconPack?
        get() = iconPack

    /**
     * Called when icons are selected and user confirms the selection.
     */
    override fun onIconDialogIconsSelected(dialog: IconDialog, icons: List<Icon>) {
        showIconuserselected = view!!.findViewById(R.id.showIconuserselected)
        icons.map { saveicon = it.drawable!!.mutate() }
        showIconuserselected.setImageDrawable(saveicon)
    }

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
