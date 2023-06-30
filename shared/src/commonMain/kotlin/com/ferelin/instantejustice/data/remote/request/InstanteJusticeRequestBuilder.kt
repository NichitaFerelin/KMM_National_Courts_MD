package com.ferelin.instantejustice.data.remote.request

import com.ferelin.instantejustice.data.remote.InstanteJusticeApi
import com.ferelin.instantejustice.domain.InstanteJusticeSupportLanguage
import com.ferelin.instantejustice.domain.InstanteJusticeType

class InstanteJusticeRequestBuilder(
    val language: InstanteJusticeSupportLanguage,
    val justiceType: InstanteJusticeType
) {
    var resultUrl: String = "${InstanteJusticeApi.BASE_URL}${language.key}/${justiceType.key}?"
        private set

    var caseName: String = ""
        set(value) {
            resultUrl += "&Denumire_dosar=$value"
            field = value
        }

    var caseNameV2: String = ""
        set(value) {
            resultUrl += "&Denumirea_dosarului=$value"
            field = value
        }

    var caseNumber: String = ""
        set(value) {
            resultUrl += "&Numarul_cazului=$value"
            field = value
        }

    var caseNumberV2: String = ""
        set(value) {
            resultUrl += "&Numarul_dosarului=$value"
            field = value
        }

    var caseObject: String = ""
        set(value) {
            resultUrl += "&Obiectul_cauzei=$value"
            field = value
        }

    var caseSubject: String = ""
        set(value) {
            resultUrl += "&Tematica_dosarului=$value"
            field = value
        }

    var caseType: String = ""
        set(value) {
            resultUrl += "&Tipul_dosarului=$value"
            field = value
        }

    var date: String = ""
        set(value) {
            resultUrl += "&date=$value"
            field = value
        }

    var page: Int = -1
        set(value) {
            if (page == -1) {
                resultUrl += "&page=$value"
            } else {
                resultUrl = resultUrl.replace("page=$page", "page=$value")
            }
            field = value
        }

    var court: String = ""
        set(value) {
            resultUrl += "&Instance=$value"
            field = value
        }

    var solrDoc: String = ""
        set(value) {
            resultUrl += "&solr_document_3=$value"
            field = value
        }

    var summoned: String = ""
        set(value) {
            resultUrl += "&PersoanaCitata=$value"
            field = value
        }

    var judge: String = ""
        set(value) {
            resultUrl += "&solr_document_2=$value"
            field = value
        }

    var state: String = ""
        set(value) {
            resultUrl += "&Statutul_dosarului=$value"
            field = value
        }

    init {
        court = "All"
        caseType = "All"
    }

    override fun equals(other: Any?): Boolean {
        return other is InstanteJusticeRequestBuilder && this.resultUrl == other.resultUrl
    }

    override fun hashCode(): Int {
        return resultUrl.hashCode()
    }

    fun isEmpty(): Boolean =
        !(caseName.isNotEmpty() || caseNameV2.isNotEmpty() || caseNumber.isNotEmpty() ||
                caseNumberV2.isNotEmpty() || caseObject.isNotEmpty() || caseSubject.isNotEmpty() ||
                date.isNotEmpty() || solrDoc.isNotEmpty() || summoned.isNotEmpty())
}