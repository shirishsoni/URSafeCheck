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


    // Lazy initialization for the Search bar variable
    lateinit var searchBar: MaterialSearchBar
    // Defining the list to provide the handler the last search suggestions
    private lateinit var lastSearches:List<String>
    // variables for chips of chemicals
    private val TAG = MainActivity::class.java.simpleName
    // variables for the PDF Viewer
    lateinit var SDS_FILE : String
    var pdfView: PDFView? = null
    var pageNumber: Int? = 0
    var pdfFileName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_sds)
        // fetching the view of the search bar
        searchBar  = findViewById(R.id.searchBar) as MaterialSearchBar
        searchBar.setHint("Methanol")
        // enabling the Audio input
        searchBar.setSpeechMode(true)
        searchBar.setOnSearchActionListener(this)


    }
    //chips for the files available offline
    fun onChipClick (view: View){
        // chip for the ammonia
        if((view as Chip) == Ammonia){
            // offline file for Ammonia
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

       // Handling the state and view of the search bar
        val state = if (true) "enabled" else "disabled"
        Toast.makeText(this, "Search $state", Toast.LENGTH_SHORT).show()
    }

    // Defining the state after the search keyword inserted by the user
    override fun onSearchConfirmed(text: CharSequence?) {
        val modifiedText:String = text.toString()
        // modifying the search query by appending the SDS
        val commonTextAppender = "SDS "
        // launching the browser to search online
        // when chemical is not available on the user's device
        // or server than we will search on internet for SDS
        startSearch("$modifiedText $commonTextAppender", true, null, true);

    }

    // methods for search bar
    override fun onButtonClicked(buttonCode: Int) {
        when (buttonCode) {
     //         MaterialSearchBar.BUTTON_NAVIGATION -> drawer.openDrawer(Gravity.LEFT)
     //       MaterialSearchBar.BUTTON_SPEECH -> openVoiceRecognizer()
        }
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    // Handling the page change event in the user device
    override fun onPageChanged(page: Int, pageCount: Int) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }
    //  Defining the view when we complete the loading of  PDF
    override fun loadComplete(nbPages: Int) {
        val meta = pdfView?.getDocumentMeta()
        printBookmarksTree(pdfView?.getTableOfContents() as MutableList<PdfDocument.Bookmark>, "-")
    }
    // Providing the attribute for PDF
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
    // This mutable Trees is responsible for handling the pdf and its operations
    fun printBookmarksTree(tree: MutableList<PdfDocument.Bookmark>, sep: String) {
        for (b in tree) {
            Log.e(TAG, String.format("%s %s, p %d", sep, b.title, b.pageIdx))
            if (b.hasChildren()) {
                printBookmarksTree(b.children, "$sep-")
            }
        }
    }
}
