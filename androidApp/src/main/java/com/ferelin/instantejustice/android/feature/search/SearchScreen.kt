package com.ferelin.instantejustice.android.feature.search

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ferelin.instantejustice.android.R
import com.ferelin.instantejustice.android.composeui.components.AppButton
import com.ferelin.instantejustice.android.composeui.components.AppCircularProgressIndicator
import com.ferelin.instantejustice.android.composeui.components.AppInfoCardError
import com.ferelin.instantejustice.android.composeui.components.AppInfoCardRequest
import com.ferelin.instantejustice.android.composeui.components.AppSearchFilter
import com.ferelin.instantejustice.android.composeui.components.AppSearchResultsList
import com.ferelin.instantejustice.android.composeui.components.AppSearchTextField
import com.ferelin.instantejustice.android.composeui.components.modifier.BOUNCE_SCALE_UP
import com.ferelin.instantejustice.android.composeui.components.modifier.bounceClick
import com.ferelin.instantejustice.android.composeui.theme.Black1
import com.ferelin.instantejustice.android.composeui.theme.Green1
import com.ferelin.instantejustice.android.composeui.theme.White2
import com.ferelin.instantejustice.android.feature.search.uistate.SearchBy
import com.ferelin.instantejustice.android.feature.search.uistate.SearchInstanteJusticeItemUi
import com.ferelin.instantejustice.android.feature.search.uistate.SearchInstanteJusticeTypeUi
import com.ferelin.instantejustice.android.feature.search.uistate.SearchScreenUiState
import com.ferelin.instantejustice.android.feature.search.uistate.errorMsgResource
import com.ferelin.instantejustice.android.utils.copyText
import com.ferelin.instantejustice.android.utils.openUrl
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

val SCREEN_HORIZONTAL_PADDING = 12.dp

@Composable
fun SearchScreenRoute(
    viewModel: SearchViewModel = koinViewModel()
) {
    val searchResults = viewModel.pagerSourceFlow.collectAsLazyPagingItems()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val onImeActionSearch: KeyboardActionScope.() -> Unit = remember {
        {
            viewModel.onSearchRequested()
        }
    }
    val onSearchInputChanged: (String) -> Unit = remember {
        {
            viewModel.onSearchTextChanged(it)
        }
    }
    val onCopyClick: (SearchInstanteJusticeItemUi) -> Unit = remember {
        {
            context.copyText(
                text = it.generateMessageToCopy(context),
                copyNotification = context.getString(R.string.notification_message_copied)
            )
        }
    }
    val onPdfClick: (SearchInstanteJusticeItemUi) -> Unit = remember {
        {
            context.openUrl(it.pdfUrl)
        }
    }
    val onJusticeTypeSelected: (SearchInstanteJusticeTypeUi) -> Unit = remember {
        {
            viewModel.onJusticeTypeSelected(it)
        }
    }
    val onSearchBySelected: (SearchBy) -> Unit = remember {
        {
            viewModel.onSearchBySelected(it)
        }
    }
    val onFillterApplied = remember {
        {
            viewModel.onFilterApplied()
        }
    }
    val onRetryClick = remember {
        {
            viewModel.onRetryClick()
        }
    }

    SearchScreen(
        uiState = uiState,
        searchResults = searchResults,
        onSearchInputChanged = onSearchInputChanged,
        onImeActionSearch = onImeActionSearch,
        onCopyClick = onCopyClick,
        onPdfClick = onPdfClick,
        onJusticeTypeSelected = onJusticeTypeSelected,
        onSearchBySelected = onSearchBySelected,
        onFilterSettingsApplied = onFillterApplied,
        onRetryRequestClick = onRetryClick
    )
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
private fun SearchScreen(
    uiState: SearchScreenUiState,
    searchResults: LazyPagingItems<SearchInstanteJusticeItemUi>,
    onSearchInputChanged: (String) -> Unit,
    onImeActionSearch: KeyboardActionScope.() -> Unit,
    onCopyClick: (SearchInstanteJusticeItemUi) -> Unit,
    onPdfClick: (SearchInstanteJusticeItemUi) -> Unit,
    onJusticeTypeSelected: (SearchInstanteJusticeTypeUi) -> Unit,
    onSearchBySelected: (SearchBy) -> Unit,
    onFilterSettingsApplied: () -> Unit,
    onRetryRequestClick: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = false
    )

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = modalSheetState) {
        snapshotFlow { modalSheetState.targetValue }
            .onEach {
                if (it != ModalBottomSheetValue.Hidden) {
                    keyboardController?.hide()
                }
            }
            .filterIsInstance(ModalBottomSheetValue.Hidden::class)
            .onEach { onFilterSettingsApplied() }
            .launchIn(this)
    }

    val onFilterClick = remember {
        {
            coroutineScope.launch {
                modalSheetState.show()
            }
            Unit
        }
    }

    ModalBottomSheetLayout(
        modifier = Modifier
            .navigationBarsPadding()
            .statusBarsPadding(),
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(
            topStart = MaterialTheme.shapes.medium.topStart,
            topEnd = MaterialTheme.shapes.medium.topEnd,
            bottomEnd = CornerSize(0.dp),
            bottomStart = CornerSize(0.dp)
        ),
        sheetContent = {
            AppSearchFilter(
                justiceType = uiState.selectedJusticeType,
                searchBy = uiState.searchBy,
                onJusticeTypeSelected = onJusticeTypeSelected,
                onSearchBySelected = onSearchBySelected
            )
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            backgroundColor = White2,
            topBar = {
                TopBar(
                    searchInput = uiState.searchInput,
                    onFilterClick = onFilterClick,
                    onSearchInputChanged = onSearchInputChanged,
                    onImeActionSearch = onImeActionSearch
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Crossfade(
                    targetState = searchResults.loadState.refresh,
                    label = "search screen crossfade animation refresh"
                ) { state ->
                    when (state) {
                        is LoadState.NotLoading -> {
                            if (uiState.searchInput.isEmpty()) {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    AppInfoCardRequest(
                                        modifier = Modifier.padding(
                                            start = SCREEN_HORIZONTAL_PADDING,
                                            end = SCREEN_HORIZONTAL_PADDING,
                                            top = 12.dp
                                        ),
                                        description = stringResource(
                                            id = R.string.title_make_your_first_request
                                        )
                                    )
                                }
                            } else {
                                AppSearchResultsList(
                                    modifier = Modifier.padding(top = 12.dp),
                                    items = searchResults,
                                    onCopyClick = onCopyClick,
                                    onPdfClick = onPdfClick,
                                    onRetryRequestClick = onRetryRequestClick
                                )
                            }
                        }

                        is LoadState.Error -> {
                            AppInfoCardError(
                                modifier = Modifier.padding(
                                    start = SCREEN_HORIZONTAL_PADDING,
                                    end = SCREEN_HORIZONTAL_PADDING,
                                    top = 12.dp
                                ),
                                errorDescription = stringResource(id = state.error.errorMsgResource()) + state.error.toString(),
                                onRetryClick = onRetryRequestClick
                            )
                        }

                        is LoadState.Loading -> {
                            Box(modifier = Modifier.fillMaxSize()) {
                                AppCircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
    searchInput: String,
    onFilterClick: () -> Unit,
    onSearchInputChanged: (String) -> Unit,
    onImeActionSearch: KeyboardActionScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = SCREEN_HORIZONTAL_PADDING)
    ) {
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = stringResource(id = R.string.title_app_header),
            style = MaterialTheme.typography.h1,
            color = Black1
        )
        Spacer(modifier = Modifier.height(18.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.weight(0.85f),
                elevation = 4.dp,
                shape = MaterialTheme.shapes.medium
            ) {
                AppSearchTextField(
                    searchInput = searchInput,
                    onSearchInputChanged = onSearchInputChanged,
                    onImeActionSearch = onImeActionSearch
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Surface(
                modifier = Modifier
                    .weight(0.15f)
                    .bounceClick(BOUNCE_SCALE_UP),
                elevation = 4.dp,
                shape = MaterialTheme.shapes.medium
            ) {
                AppButton(
                    modifier = Modifier.height(TextFieldDefaults.MinHeight + 3.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Green1),
                    contentPadding = PaddingValues(4.dp),
                    onClick = onFilterClick
                ) {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        painter = painterResource(id = R.drawable.ic_filter),
                        contentDescription = ""
                    )
                }
            }
        }
    }
}