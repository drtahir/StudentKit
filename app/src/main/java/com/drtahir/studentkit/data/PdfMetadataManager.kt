package com.drtahir.studentkit.data

import android.content.Context
import android.net.Uri
import java.io.FileInputStream
import java.nio.charset.Charset

data class PdfMetadata(
    val title: String = "",
    val author: String = "",
    val subject: String = "",
    val keywords: String = "",
    val creator: String = ""
)

object PdfMetadataManager {
    fun extractMetadata(context: Context, uri: Uri): PdfMetadata {
        var title = ""
        var author = ""
        var subject = ""
        var keywords = ""
        var creator = ""
        
        try {
            val contentBuilder = StringBuilder()
            context.contentResolver.openFileDescriptor(uri, "r")?.use { pfd ->
                val size = pfd.statSize
                val chunk = 1024 * 1024 // 1MB
                
                FileInputStream(pfd.fileDescriptor).use { stream ->
                    val channel = stream.channel
                    if (size <= chunk * 2) {
                        val bytes = ByteArray(size.toInt())
                        stream.read(bytes)
                        contentBuilder.append(String(bytes, Charset.forName("ISO-8859-1")))
                    } else {
                        // Read first 1MB
                        val firstBytes = ByteArray(chunk)
                        stream.read(firstBytes)
                        contentBuilder.append(String(firstBytes, Charset.forName("ISO-8859-1")))
                        
                        // Read last 1MB
                        channel.position(size - chunk)
                        val lastBytes = ByteArray(chunk)
                        stream.read(lastBytes)
                        contentBuilder.append(String(lastBytes, Charset.forName("ISO-8859-1")))
                    }
                }
            }
            
            val content = contentBuilder.toString()
            
            title = extractPdfString(content, "/Title")
            author = extractPdfString(content, "/Author")
            subject = extractPdfString(content, "/Subject")
            keywords = extractPdfString(content, "/Keywords")
            creator = extractPdfString(content, "/Creator")
            
        } catch (e: Exception) {
            e.printStackTrace()
        }
        
        return PdfMetadata(title, author, subject, keywords, creator)
    }
    
    private fun extractPdfString(content: String, key: String): String {
        val idx = content.lastIndexOf(key) 
        if (idx != -1) {
            val start = idx + key.length
            var stringStart = -1
            var i = start
            while (i < content.length && i < start + 100) {
                if (content[i] == ' ' || content[i] == '\n' || content[i] == '\r') {
                    i++
                    continue
                }
                stringStart = i
                break
            }
            if (stringStart != -1) {
                if (content[stringStart] == '(') {
                    var depth = 1
                    val sb = StringBuilder()
                    var j = stringStart + 1
                    while (j < content.length && depth > 0 && j < stringStart + 2000) {
                        val c = content[j]
                        if (c == '\\') {
                            j++
                            if (j < content.length) {
                                val escaped = content[j]
                                if (escaped == 'n') sb.append('\n')
                                else if (escaped == 'r') sb.append('\r')
                                else if (escaped == 't') sb.append('\t')
                                else if (escaped == '(' || escaped == ')' || escaped == '\\') sb.append(escaped)
                                else if (escaped in '0'..'7') {
                                    var octal = escaped.toString()
                                    if (j + 1 < content.length && content[j+1] in '0'..'7') {
                                        j++
                                        octal += content[j]
                                        if (j + 1 < content.length && content[j+1] in '0'..'7') {
                                            j++
                                            octal += content[j]
                                        }
                                    }
                                    sb.append(octal.toInt(8).toChar())
                                } else {
                                    sb.append(escaped)
                                }
                            }
                        } else if (c == '(') {
                            depth++
                            sb.append(c)
                        } else if (c == ')') {
                            depth--
                            if (depth > 0) sb.append(c)
                        } else {
                            sb.append(c)
                        }
                        j++
                    }
                    return decodePdfString(sb.toString())
                } else if (content[stringStart] == '<') {
                    val end = content.indexOf(">", stringStart)
                    if (end != -1 && end < stringStart + 4000) {
                        val hex = content.substring(stringStart + 1, end).replace(Regex("\\s+"), "")
                        if (hex.length % 2 == 0) {
                            try {
                                val bytes = hex.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
                                return decodePdfString(String(bytes, Charset.forName("ISO-8859-1")))
                            } catch (e: Exception) {
                                // ignore
                            }
                        }
                    }
                }
            }
        }
        return ""
    }
    
    private fun decodePdfString(str: String): String {
        if (str.length >= 2 && str[0] == '\u00FE' && str[1] == '\u00FF') {
            val bytes = str.toByteArray(Charset.forName("ISO-8859-1"))
            return String(bytes, 2, bytes.size - 2, Charset.forName("UTF-16BE"))
        }
        return str
    }
}
