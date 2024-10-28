import java.awt.BorderLayout
import java.awt.Dimension
import java.io.File
import javax.swing.*
import javax.swing.filechooser.FileNameExtensionFilter

fun main() {
    /**
     * We use invokeLater to run the GUI on another Thread.
     */
    SwingUtilities.invokeLater {
        MarkdownViewerApp()
    }
}

/**
 * Simple desktop application in Kotlin that allows users to load and display the contents of Markdown files in plain text format.
 * @author Marko Jovicic
 */
class MarkdownViewerApp : JFrame() {

    private val textArea = JTextArea().apply {
        isEditable = false
        lineWrap = true
        wrapStyleWord = true
    }

    /**
     * Creates a panel with a button for loading Markdown files.
     */
    private fun createButtonPanel(): JPanel {
        val loadButton = JButton("Load Markdown (.md) File").apply {
            addActionListener { loadAndShowMarkdownFile() }
            preferredSize = Dimension(200, 40)
        }
        return JPanel().apply {
            add(loadButton)
        }
    }

    init {
        title = "Markdown Reader"
        defaultCloseOperation = EXIT_ON_CLOSE
        size = Dimension(600, 400)
        layout = BorderLayout()

        val scrollPane = JScrollPane(textArea)

        add(scrollPane, BorderLayout.CENTER)
        add(createButtonPanel(), BorderLayout.SOUTH)
        isVisible = true
    }

    /**
     * Open File Explorer, load .md file and display it's content.
     */
    private fun loadAndShowMarkdownFile() {
        val fileChooser = JFileChooser().apply {
            fileFilter = FileNameExtensionFilter("Markdown Files", "md")
        }

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            val selectedFile = fileChooser.selectedFile
            if (selectedFile.extension.equals("md", ignoreCase = true)) {
                displayFileContent(selectedFile)
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Please select a Markdown (.md) file.",
                    "Invalid File Type",
                    JOptionPane.WARNING_MESSAGE
                )
            }
        }
    }

    private fun displayFileContent(file: File) {
        /**
         * As we don't have to format text, we'll just read whole file at once.
         */
        try {
            val content = file.readText()
            textArea.text = content
        } catch (e: Exception) {
            JOptionPane.showMessageDialog(
                this,
                "Error reading file, file.readText(): ${e.message}",
                "File Read Error",
                JOptionPane.ERROR_MESSAGE
            )
        }
    }
}
