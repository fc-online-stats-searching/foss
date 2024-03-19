package com.foss.foss.design.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.foss.foss.R
import com.foss.foss.design.FossTheme

@Composable
fun NicknameSearchingTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
    isFocused: Boolean = false,
    placeHolderString: String = stringResource(id = R.string.common_request_searching_nickname)
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        cursorBrush = SolidColor(FossTheme.colors.fossWt),
        textStyle = FossTheme.typography.body01.copy(color = FossTheme.colors.fossWt),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions {
            onSearch()
            keyboardController?.hide()
        },
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = FossTheme.padding.BasicHorizontalPadding)
                    .height(44.dp)
                    .background(
                        color = FossTheme.colors.fossGray800,
                        shape = RoundedCornerShape(corner = CornerSize(5.dp))
                    )
                    .fillMaxWidth()
            ) {
                Icon(
                    modifier = Modifier
                        .padding(start = 10.dp),
                    tint = FossTheme.colors.fossGray300,
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.padding(3.dp))
                if (!isFocused && value.isEmpty()) {
                    Text(
                        style = FossTheme.typography.body01,
                        text = placeHolderString,
                        color = FossTheme.colors.fossGray300
                    )
                }
                innerTextField()
            }
        }
    )
}
