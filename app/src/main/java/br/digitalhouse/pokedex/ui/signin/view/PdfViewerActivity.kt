package br.digitalhouse.pokedex.ui.signin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.databinding.ActivityPdfViewerBinding
import com.danjdt.pdfviewer.PdfViewer
import com.danjdt.pdfviewer.interfaces.OnErrorListener
import com.danjdt.pdfviewer.interfaces.OnPageChangedListener
import com.danjdt.pdfviewer.utils.PdfPageQuality
import com.danjdt.pdfviewer.view.PdfViewerRecyclerView
import java.io.IOException
import java.lang.Exception

class PdfViewerActivity : AppCompatActivity() {
    private val binding: ActivityPdfViewerBinding by lazy { ActivityPdfViewerBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnClickListener()
        pdfViewer()
    }

    private fun setOnClickListener() {
        binding.btnBack.setOnClickListener{
            finish()
        }
    }

    private fun pdfViewer(){
        PdfViewer.Builder(binding.rootView)
            .view(PdfViewerRecyclerView(this))
            .quality(PdfPageQuality.QUALITY_1080)
            .setZoomEnabled(true)
            .setMaxZoom(3f) //zoom multiplier
            .setOnPageChangedListener(object : OnPageChangedListener {
                override fun onPageChanged(page: Int, total: Int) {
                    binding.tvCounter.text = getString(R.string.pdf_page_counter, page, total)
                }

            })
            .setOnErrorListener(object : OnErrorListener {
                override fun onAttachViewError(e: Exception) {
                }

                override fun onFileLoadError(e: Exception) {
                }

                override fun onPdfRendererError(e: IOException) {
                }

            })
            .build()
            .load(R.raw.pdftermos)
    }
}