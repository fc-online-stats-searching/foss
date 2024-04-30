package com.foss.foss.feature.matchsearching.recentmatch

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.foss.foss.R
import com.foss.foss.design.FossTheme
import com.foss.foss.model.MatchTypeUiModel

@Composable
fun MatchTypeSpinner(
    selected: MatchTypeUiModel,
    matchTypes: List<MatchTypeUiModel>,
    onSelectionChanged: (selection: MatchTypeUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var matchTypesButtonSize by remember { mutableStateOf(Size.Zero) }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        Box {
            Button(
                colors = ButtonDefaults.buttonColors(FossTheme.colors.fossGray700),
                contentPadding = PaddingValues(0.dp),
                shape = RoundedCornerShape(corner = CornerSize(5.dp)),
                onClick = { expanded = !expanded },
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(bottom = 6.dp)
                    .height(26.dp)
                    .onGloballyPositioned { coordinates ->
                        matchTypesButtonSize = coordinates.size.toSize()
                    }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 21.dp)
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = stringResource(id = selected.resId),
                        style = FossTheme.typography.body04,
                        color = Color.White
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_drop_down_arrow),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 10.dp)
                            .width(14.dp)
                            .height(14.dp)
                    )
                }
            }
            MatchTypeDropDownMenu(
                expanded = expanded,
                onDismiss = { expanded = false },
                matchTypeWidth = with(LocalDensity.current) {
                    matchTypesButtonSize.width.toDp()
                },
                matchTypes = matchTypes,
                onSelectionChanged = onSelectionChanged
            )
        }
    }
}

@Composable
fun MatchTypeDropDownMenu(
    expanded: Boolean,
    onDismiss: (Boolean) -> Unit,
    matchTypeWidth: Dp,
    matchTypes: List<MatchTypeUiModel>,
    onSelectionChanged: (selection: MatchTypeUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(surface = FossTheme.colors.fossGray700),
        shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(5.dp))
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onDismiss(false) },
            modifier = modifier
                .width(matchTypeWidth)
        ) {
            matchTypes.forEach { entry ->
                DropdownMenuItem(
                    onClick = {
                        onDismiss(false)
                        onSelectionChanged(entry)
                    },
                    text = {
                        Text(
                            text = stringResource(id = entry.resId),
                            color = FossTheme.colors.fossWt,
                            style = FossTheme.typography.body04,
                            modifier = Modifier.align(Alignment.Start)
                        )
                    },
                    modifier = modifier.height(26.dp)
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun RecentMatchTypeSpinnerPreview() {
    MatchTypeSpinner(
        selected = MatchTypeUiModel.ALL,
        matchTypes = listOf(),
        onSelectionChanged = {}
    )
}
