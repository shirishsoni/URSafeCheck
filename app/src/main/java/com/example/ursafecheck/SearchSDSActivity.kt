package com.example.ursafecheck

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mancj.materialsearchbar.MaterialSearchBar
import android.widget.Toast


class SearchSDSActivity : AppCompatActivity(), MaterialSearchBar.OnSearchActionListener {

    lateinit var searchBar: MaterialSearchBar
    private lateinit var lastSearches:List<String>

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


}
