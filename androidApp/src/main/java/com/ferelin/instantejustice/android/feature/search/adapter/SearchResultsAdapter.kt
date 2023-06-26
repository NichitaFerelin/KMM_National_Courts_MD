package com.ferelin.instantejustice.android.feature.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ferelin.instantejustice.android.R
import com.ferelin.instantejustice.android.databinding.ItemCourtClaimsCasesBinding
import com.ferelin.instantejustice.android.databinding.ItemCourtDecisionBinding
import com.ferelin.instantejustice.android.databinding.ItemCourtRulingBinding
import com.ferelin.instantejustice.android.databinding.ItemHearingsAgendaBinding
import com.ferelin.instantejustice.android.databinding.ItemPublicSummonedBinding
import com.ferelin.instantejustice.domain.InstanteJusticeItem

private const val VIEW_TYPE_COURT_DECISIONS = 0
private const val VIEW_TYPE_COURT_RULING = 1
private const val VIEW_TYPE_COURT_CLAIMS_CASES = 2
private const val VIEW_TYPE_HEARINGS_AGENDA = 3
private const val VIEW_TYPE_PUBLIC_SUMMONS = 4

class SearchResultsAdapter :
    PagingDataAdapter<InstanteJusticeItem, SearchResultsAdapter.ItemViewHolder>(
        InstanteJusticeItemComparator
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return when (viewType) {
            VIEW_TYPE_COURT_DECISIONS -> {
                CourtDecisionViewHolder(
                    ItemCourtDecisionBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            VIEW_TYPE_COURT_RULING -> {
                CourtRulingViewHolder(
                    ItemCourtRulingBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            VIEW_TYPE_COURT_CLAIMS_CASES -> {
                CourtClaimsViewHolder(
                    ItemCourtClaimsCasesBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            VIEW_TYPE_HEARINGS_AGENDA -> {
                HearingsAgendaViewHolder(
                    ItemHearingsAgendaBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            VIEW_TYPE_PUBLIC_SUMMONS -> {
                PublicSummonedViewHolder(
                    ItemPublicSummonedBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> error("Cannot create view holder for viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position)!!, position)
    }

    override fun getItemViewType(position: Int): Int {
        return when (this.getItem(position)) {
            is InstanteJusticeItem.CourtDecisionItem -> VIEW_TYPE_COURT_DECISIONS
            is InstanteJusticeItem.CourtClaimAndCaseItem -> VIEW_TYPE_COURT_CLAIMS_CASES
            is InstanteJusticeItem.CourtRulingItem -> VIEW_TYPE_COURT_RULING
            is InstanteJusticeItem.HearingsAgendaItem -> VIEW_TYPE_HEARINGS_AGENDA
            is InstanteJusticeItem.PublicSummonItem -> VIEW_TYPE_PUBLIC_SUMMONS
            else -> error("Unknown InstanteJusticeItem[${this.getItem(position)}] to bind")
        }
    }

    abstract class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        open fun bind(item: InstanteJusticeItem, position: Int) {
            view.background = if (position % 2 != 0) {
                AppCompatResources.getDrawable(view.context, R.drawable.item_search_result_light)
            } else {
                AppCompatResources.getDrawable(view.context, R.drawable.item_search_result_white)
            }
        }
    }

    class HearingsAgendaViewHolder(
        private val itemBinding: ItemHearingsAgendaBinding
    ) : ItemViewHolder(itemBinding.root) {

        override fun bind(item: InstanteJusticeItem, position: Int) {
            item as InstanteJusticeItem.HearingsAgendaItem

            itemBinding.textViewCaseName.text = item.caseName
            itemBinding.textViewCaseNumber.text = item.caseNumber
            itemBinding.textViewHearingDate.text = item.hearingDate
            itemBinding.textViewHearingHour.text = item.hearingHour
            itemBinding.textViewCaseObjectDescription.text = item.caseObject
            itemBinding.textViewCaseTypeDescription.text = item.caseType
            itemBinding.textViewHearingResultDescription.text = item.hearingResult

            itemBinding.textViewCourtInfo.text = itemBinding.root.context
                .getString(R.string.court_info, item.courtName, item.courtRoom, item.judgeName)
        }
    }

    class CourtClaimsViewHolder(
        private val itemBinding: ItemCourtClaimsCasesBinding
    ) : ItemViewHolder(itemBinding.root) {

        override fun bind(item: InstanteJusticeItem, position: Int) {
            item as InstanteJusticeItem.CourtClaimAndCaseItem

            itemBinding.textViewCaseName.text = item.caseName
            itemBinding.textViewCaseNumberDescription.text = item.caseNumber
            itemBinding.textViewCaseNumbeReferencerDescription.text = item.caseReferenceNumber
            itemBinding.textViewCaseStatusDescription.text = item.caseStatus
            itemBinding.textViewCaseTypeDescription.text = item.caseType
            itemBinding.textViewCaseRegistrationDescription.text = item.registrationDate
        }
    }

    class CourtDecisionViewHolder(
        private val itemBinding: ItemCourtDecisionBinding
    ) : ItemViewHolder(itemBinding.root) {

        override fun bind(item: InstanteJusticeItem, position: Int) {
            item as InstanteJusticeItem.CourtDecisionItem

            itemBinding.textViewCaseName.text = item.caseName
            itemBinding.textViewCaseNumber.text = item.caseNumber
            itemBinding.textViewCaseSubjectDescription.text = item.caseSubject
            itemBinding.textViewCaseTypeDescription.text = item.caseType
            itemBinding.textViewCaseDateOfPronouncementDescription.text = item.dateOfPronouncement
            itemBinding.textViewRegistrationDateDescription.text = item.registrationDate
            itemBinding.textViewCasePublicationDateDescription.text = item.publicationDate

            itemBinding.textViewCourtInfo.text = itemBinding.root.context
                .getString(R.string.court_info_short, item.courtName, item.judgeName)
        }
    }

    class CourtRulingViewHolder(
        private val itemBinding: ItemCourtRulingBinding
    ) : ItemViewHolder(itemBinding.root) {

        override fun bind(item: InstanteJusticeItem, position: Int) {
            item as InstanteJusticeItem.CourtRulingItem

            itemBinding.textViewCaseName.text = item.caseName
            itemBinding.textViewCaseNumber.text = item.caseNumber
            itemBinding.textViewCaseSubjectDescription.text = item.caseSubject
            itemBinding.textViewCaseTypeDescription.text = item.caseType
            itemBinding.textViewRegistrationDateDescription.text = item.registrationDate
            itemBinding.textViewCasePublicationDateDescription.text = item.publicationDate

            itemBinding.textViewCourtInfo.text = itemBinding.root.context
                .getString(R.string.court_info_short, item.courtName, item.judgeName)
        }
    }

    class PublicSummonedViewHolder(
        private val itemBinding: ItemPublicSummonedBinding
    ) : ItemViewHolder(itemBinding.root) {

        override fun bind(item: InstanteJusticeItem, position: Int) {
            item as InstanteJusticeItem.PublicSummonItem

            itemBinding.textViewCaseName.text = item.caseName
            itemBinding.textViewCaseNumber.text = item.caseNumber
            itemBinding.textViewHearingDate.text = item.hearingDate
            itemBinding.textViewHearingHour.text = item.hearingHour
            itemBinding.textViewCaseObjectDescription.text = item.caseObject
            itemBinding.textViewSummonedPersonDescription.text = item.summonedPerson

            itemBinding.textViewCourtInfo.text = itemBinding.root.context
                .getString(R.string.court_info, item.courtName, item.courtRoom, item.judgeName)
        }
    }
}