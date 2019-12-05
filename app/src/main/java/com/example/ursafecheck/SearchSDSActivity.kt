package com.example.ursafecheck

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.mancj.materialsearchbar.MaterialSearchBar
import android.widget.Toast
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.google.android.material.chip.Chip
import com.shockwave.pdfium.PdfDocument
import kotlinx.android.synthetic.main.activity_search_sds.*
import java.io.File


class SearchSDSActivity : AppCompatActivity(), MaterialSearchBar.OnSearchActionListener, OnPageChangeListener, OnLoadCompleteListener {


    lateinit var searchBar: MaterialSearchBar
    private lateinit var lastSearches:List<String>
    // variables for chips
    private val TAG = MainActivity::class.java.simpleName
    lateinit var SDS_FILE : String
    var pdfView: PDFView? = null
    var pageNumber: Int? = 0
    var pdfFileName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_sds)
        searchBar  = findViewById(R.id.searchBar) as MaterialSearchBar
        searchBar.setHint("Methanol")
        searchBar.setSpeechMode(true)
        searchBar.setOnSearchActionListener(this)

    //    lastSearches = loadSearchSuggestionFromDisk()
       // searchBar.setLastSuggestions(list)
     //   searchBar.inflateMenu(R.menu.Main)
     //   searchBar.getMenu().setOnMenuItemClickListener(this)


    }
    fun onChipClick (view: View){

        if((view as Chip) == Ammonia){

            SDS_FILE = "Ammonia.pdf"
            pdfView = findViewById(R.id.pdfView)
            displayFromAsset(SDS_FILE)
        }
    }

   /* override fun onDestroy() {
        super.onDestroy()
    //    saveSearchSuggestionToDisk(searchBar.getLastSuggestions());
    }*/
    override fun onSearchStateChanged(enabled: Boolean) {

        val state = if (true) "enabled" else "disabled"
        Toast.makeText(this, "Search $state", Toast.LENGTH_SHORT).show()
    }

    override fun onSearchConfirmed(text: CharSequence?) {
        val modifiedText:String = text.toString()
        val commonTextAppender = "SDS "
        startSearch("$modifiedText $commonTextAppender", true, null, true);

    }

    override fun onButtonClicked(buttonCode: Int) {
        when (buttonCode) {
   //         MaterialSearchBar.BUTTON_NAVIGATION -> drawer.openDrawer(Gravity.LEFT)
     //       MaterialSearchBar.BUTTON_SPEECH -> openVoiceRecognizer()
        }
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    // code for local SDS chips

    override fun onPageChanged(page: Int, pageCount: Int) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }

    override fun loadComplete(nbPages: Int) {
        val meta = pdfView?.getDocumentMeta()
        printBookmarksTree(pdfView?.getTableOfContents() as MutableList<PdfDocument.Bookmark>, "-")
    }

    private fun displayFromAsset(assetFileName: String) {
        pdfFileName = assetFileName

        pageNumber?.let {
            pdfView?.fromAsset(SDS_FILE)?.defaultPage(it)?.enableSwipe(true)
                ?.swipeHorizontal(false)
                ?.onPageChange(this)
                ?.enableAnnotationRendering(true)
                ?.onLoad(this)
                ?.scrollHandle(DefaultScrollHandle(this))
                ?.load()
        }
    }

    fun printBookmarksTree(tree: MutableList<PdfDocument.Bookmark>, sep: String) {
        for (b in tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.title, b.pageIdx))

            if (b.hasChildren()) {
                printBookmarksTree(b.children, "$sep-")
            }
        }
    }
}
