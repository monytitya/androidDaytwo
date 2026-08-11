package com.example.androiddaytwo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.androiddaytwo.model.CartItem
import com.example.androiddaytwo.model.Category
import com.example.androiddaytwo.model.Product

import androidx.compose.ui.graphics.Brush
import androidx.compose.material.icons.automirrored.filled.ArrowForward

@Composable
fun HeroSection(
    modifier: Modifier = Modifier,
    onShopNow: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp)
            .clip(RoundedCornerShape(24.dp))
    ) {
        AsyncImage(
            model = "https://images.unsplash.com/photo-1542291026-7eec264c27ff?q=80&w=800&auto=format&fit=crop",
            contentDescription = "Featured Product",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                    )
                )
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Surface(
                color = Color.White.copy(alpha = 0.2f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(
                    text = "Limited Offer",
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            Text(
                text = "First Purchase Enjoy\na Special Offer",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 24.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onShopNow,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
                shape = RoundedCornerShape(24.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Shop Now", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcommerceSearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit = {}
) {
    var text by remember { mutableStateOf("") }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        TextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            placeholder = { Text("Search shoes...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(24.dp)),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            onClick = { /* TODO: Filter */ },
            modifier = Modifier
                .size(48.dp)
                .background(MaterialTheme.colorScheme.primary, CircleShape)
        ) {
            Icon(Icons.Default.Settings, contentDescription = "Filter", tint = Color.White)
        }
    }
}

@Composable
fun CategoryItem(
    category: Category,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = category.imageUrl,
            contentDescription = category.name,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color.LightGray),
            contentScale = ContentScale.Crop
        )
        Text(
            text = category.name,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun ProductCard(
    product: Product,
    onClick: () -> Unit = {},
    onFavoriteToggle: (Boolean) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(160.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            var isFavorite by remember { mutableStateOf(product.isFavorite) }
            IconButton(
                onClick = {
                    isFavorite = !isFavorite
                    onFavoriteToggle(isFavorite)
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .background(Color.White.copy(alpha = 0.7f), CircleShape)
                    .size(32.dp)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) Color.Red else Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun ShopBottomNavigation(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier.clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
    ) {
        val items: List<Pair<String, ImageVector>> = listOf(
            Pair("Home", Icons.Default.Home),
            Pair("Cart", Icons.Default.ShoppingCart),
            Pair("Orders", Icons.Default.Settings),
            Pair("Profile", Icons.Default.Person)
        )
        items.forEachIndexed { index, pair ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = { onItemSelected(index) },
                icon = { Icon(pair.second, contentDescription = pair.first) },
                label = { Text(pair.first) }
            )
        }
    }
}

@Composable
fun CartSidebarContent(
    cartItems: List<CartItem>,
    onRemoveItem: (CartItem) -> Unit,
    onUpdateQuantity: (CartItem, Int) -> Unit,
    onCheckout: () -> Unit,
    onClose: () -> Unit
) {
    val totalPrice = cartItems.sumOf { it.product.price * it.quantity }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(300.dp)
            .background(Color.White)
            .padding(16.dp)
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Your Cart",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, contentDescription = "Close")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (cartItems.isEmpty()) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text(text = "Your cart is empty", color = Color.Gray)
            }
        } else {
            androidx.compose.foundation.lazy.LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(cartItems.size) { index ->
                    val item = cartItems[index]
                    CartSidebarItem(
                        item = item,
                        onRemove = { onRemoveItem(item) },
                        onUpdateQuantity = { onUpdateQuantity(item, it) }
                    )
                }
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Total", fontWeight = FontWeight.Bold)
            Text(text = "$${String.format("%.2f", totalPrice)}", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onCheckout,
            enabled = cartItems.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Confirm Checkout")
        }
    }
}

@Composable
fun CartSidebarItem(
    item: CartItem,
    onRemove: () -> Unit,
    onUpdateQuantity: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = item.product.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.product.name, fontWeight = FontWeight.Bold, maxLines = 1)
            Text(text = "Size: ${item.size}", fontSize = 12.sp, color = Color.Gray)
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                IconButton(
                    onClick = { onUpdateQuantity(item.quantity - 1) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(Icons.Default.Remove, contentDescription = "Decrease", modifier = Modifier.size(16.dp))
                }
                Text(
                    text = item.quantity.toString(),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = { onUpdateQuantity(item.quantity + 1) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Increase", modifier = Modifier.size(16.dp))
                }
            }
            
            Text(text = "$${item.product.price * item.quantity}", fontWeight = FontWeight.SemiBold)
        }
        IconButton(onClick = onRemove) {
            Icon(Icons.Default.Delete, contentDescription = "Remove", tint = Color.Red, modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun SizeSelector(
    sizes: List<String> = listOf("S", "M", "L", "XL"),
    selectedSize: String,
    onSizeSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier.padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        sizes.forEach { size ->
            val isSelected = size == selectedSize
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray.copy(alpha = 0.3f))
                    .clickable { onSizeSelected(size) }
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = size,
                    color = if (isSelected) Color.White else Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun QuantitySelector(
    quantity: Int,
    onQuantityChange: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color.LightGray.copy(alpha = 0.2f), RoundedCornerShape(24.dp))
            .padding(4.dp)
    ) {
        IconButton(onClick = { if (quantity > 1) onQuantityChange(quantity - 1) }) {
            Icon(Icons.Default.Remove, contentDescription = "Decrease")
        }
        Text(
            text = quantity.toString(),
            modifier = Modifier.padding(horizontal = 16.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        IconButton(onClick = { onQuantityChange(quantity + 1) }) {
            Icon(Icons.Default.Add, contentDescription = "Increase")
        }
    }
}
