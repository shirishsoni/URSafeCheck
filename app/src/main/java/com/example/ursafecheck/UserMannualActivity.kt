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

    private val TAG = MainActivity::class.java.simpleName
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
    override fun onPageChanged(page: Int, pageCount: Int) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }

    override fun loadComplete(nbPages: Int) {
        val meta = pdfView?.getDocumentMeta()
        printBookmarksTree(pdfView?.getTableOfContents() as MutableList<PdfDocument.Bookmark>, "-")
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
