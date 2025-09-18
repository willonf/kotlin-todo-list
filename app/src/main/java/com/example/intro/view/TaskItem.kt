package com.example.intro.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.intro.R
import com.example.intro.model.TaskModel
import com.example.intro.ui.theme.GreenRadioButtonSelected
import com.example.intro.ui.theme.RedRadioButtonSelected
import com.example.intro.ui.theme.ShapePriorityCard
import com.example.intro.ui.theme.YellowRadioButtonSelected


fun getPriorityText (level: Int): Map<String, Any> {
    return when(level) {
        1 -> mapOf("priorityText" to "Baixa", "color" to GreenRadioButtonSelected)
        2 -> mapOf("priorityText" to "Média", "color" to YellowRadioButtonSelected)
        3 -> mapOf("priorityText" to "Alta", "color" to RedRadioButtonSelected)
        else -> mapOf("priorityText" to "Sem prioridade", "color" to Color.Gray)
    }
}

@Composable
fun TaskItem(
    task: TaskModel
) {
    Card(
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified,
            disabledContainerColor = Color.Unspecified,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        ConstraintLayout (
            modifier = Modifier.padding(20.dp)
        ) {
            val (txtTitle, txtDescription, txtPriority,
                cardPriorityColor, btnDelete) = createRefs()

            Text(
                text = "Estudar para a prova",
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.constrainAs(txtTitle) {
                    top.linkTo(
                        anchor = parent.top,
                        margin = 10.dp
                    )
                    start.linkTo(
                        anchor = parent.start,
                        margin = 10.dp
                    )
                }
            )

            Text(
                text = "Revisar todos os slides das aulas",
                modifier = Modifier.constrainAs(txtDescription) {
                    top.linkTo(
                        anchor = txtTitle.bottom,
                        margin = 20.dp
                    )
                    start.linkTo(
                        anchor = parent.start,
                        margin = 10.dp
                    )
                }
            )

            Text(
                text = getPriorityText(task.priority!!)["priorityText"] as String,
                modifier = Modifier.constrainAs(txtPriority) {
                    top.linkTo(
                        anchor = txtDescription.bottom,
                        margin = 15.dp
                    )
                    start.linkTo(
                        anchor = parent.start,
                        margin = 10.dp
                    )
                }
            )

            Card(
                shape = ShapePriorityCard.small,
                colors = CardDefaults.cardColors(
                    containerColor = getPriorityText(task.priority)["color"] as Color
                ),
                modifier = Modifier
                    .size(30.dp)
                    .constrainAs(cardPriorityColor) {
                        start.linkTo(
                            anchor = txtPriority.end,
                            margin = 15.dp
                        )
                        top.linkTo(
                            anchor = txtDescription.bottom,
                            margin = 5.dp
                        )
                    }
            ) {}

            IconButton(
                onClick = {},
                modifier = Modifier.constrainAs(btnDelete) {
                    start.linkTo(cardPriorityColor.end)
                    top.linkTo(cardPriorityColor.top)
                }
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_delete),
                    contentDescription = ""
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskItemPreview() {

}