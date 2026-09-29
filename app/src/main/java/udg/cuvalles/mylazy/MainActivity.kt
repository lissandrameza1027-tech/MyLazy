package udg.cuvalles.mylazy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import udg.cuvalles.mylazy.ui.theme.MyLazyTheme

val tripulacion = listOf(
    Data(R.drawable.luffy, "Monkey D. Luffy", "Capitán de los Piratas de Sombrero de Paja y usuario de la Fruta Hito Hito: Modelo Nika.", 300000),
    Data(R.drawable.zoro, "Roronoa Zoro", "Espadachín del estilo de tres espadas (Santoryu) con el sueño de ser el mejor.", 111100),
    Data(R.drawable.nami, "Nami", "Navegante experta en el clima y cartógrafa de la tripulación.", 36600),
    Data(R.drawable.usopp, "Usopp", "Francotirador e inventor que sueña con convertirse en un valiente guerrero del mar.", 50000),
    Data(R.drawable.sanji, "Sanji", "Cocinero del barco que pelea únicamente usando sus poderosas patadas.", 103200),
    Data(R.drawable.chopper, "Tony Tony Chopper", "Médico reno de nariz azul que comió la Fruta Hito Hito.", 1000),
    Data(R.drawable.robin, "Nico Robin", "Arqueóloga capaz de leer los Poneglyphs y hacer florecer partes de su cuerpo.", 93000),
    Data(R.drawable.franky, "Franky", "Carpintero e ingeniero cyborg que construyó el Thousand Sunny.", 39400),
    Data(R.drawable.brook, "Brook", "Músico esqueleto revivido por la Fruta Yomi Yomi y maestro esgrimista.", 38300),
    Data(R.drawable.jinbei, "Jinbe", "Timonel gyojin y maestro indiscutible del Karate Gyojin.", 110000)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyLazyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var elementoSeleccionadoId by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Text(
                text = "Tripulación",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )
        }

        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(tripulacion) { item ->
                    CardFilaHorizontal(item = item)
                }
            }
        }

        item {
            Text(
                text = "Detalle de Miembros",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
            )
        }

        items(tripulacion) { item ->
            val estaSeleccionado = elementoSeleccionadoId == item.nombre

            PersonaCardAnimada(
                item = item,
                estaSeleccionado = estaSeleccionado,
                alHacerClick = {
                    elementoSeleccionadoId = if (estaSeleccionado) null else item.nombre
                }
            )
        }
    }
}

@Composable
fun CardFilaHorizontal(item: Data) {
    Card(
        modifier = Modifier
            .width(105.dp)
            .height(130.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = item.foto),
                contentDescription = item.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.nombre.split(" ").last(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1
            )
        }
    }
}

@Composable
fun PersonaCardAnimada(
    item: Data,
    estaSeleccionado: Boolean,
    alHacerClick: () -> Unit
) {
    var mostrarTexto by remember { mutableStateOf(false) }

    val colorFondo by animateColorAsState(
        targetValue = if (estaSeleccionado) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        },
        animationSpec = tween(durationMillis = 350),
        label = "animacionColor"
    )

    val elevacionCard by animateDpAsState(
        targetValue = if (estaSeleccionado) 8.dp else 2.dp,
        animationSpec = tween(durationMillis = 300),
        label = "animacionElevacion"
    )

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { alHacerClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = elevacionCard),
        colors = CardDefaults.cardColors(containerColor = colorFondo),
        border = if (estaSeleccionado) BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary) else null
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .animateContentSize(animationSpec = tween(durationMillis = 300))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = item.foto),
                    contentDescription = item.nombre,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(75.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.nombre,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Recompensa: ${item.likes} Berries",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                    )
                }
            }

            if (mostrarTexto) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { mostrarTexto = !mostrarTexto },
                modifier = Modifier.align(Alignment.End),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = if (mostrarTexto) "Ocultar info" else "Ver info")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyLazyTheme {
        MainScreen()
    }
}