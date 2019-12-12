package com.example.ursafecheck

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import android.util.Log


class UserMannualActivity : AppCompatActivity(), OnPageChangeListener, OnLoadCompleteListener {

    // variables for the PDF Viewer
    private val TAG = MainActivity::class.java.simpleName
    // The following file is saved in the assest folder
    val LSM_FILE = "Faculty of Science Laboratory Safety Manual.pdf"
    var pdfView: PDFView? = null
    var pageNumber: Int? = 0
    var pdfFileName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_mannual)

        pdfView = findViewById(R.id.pdfView) as PDFView
        displayFromAsset(LSM_FILE)
    }

   //Providing the attribute for PDF
    private fun displayFromAsset(assetFileName: String) {
        pdfFileName = assetFileName

        pageNumber?.let {
            pdfView?.fromAsset(LSM_FILE)?.defaultPage(it)?.enableSwipe(true)
                ?.swipeHorizontal(false)
                ?.onPageChange(this)
                ?.enableAnnotationRendering(true)
                ?.onLoad(this)
                ?.scrollHandle(DefaultScrollHandle(this))
                ?.load()
        }
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
