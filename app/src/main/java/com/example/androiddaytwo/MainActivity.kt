package com.example.androiddaytwo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androiddaytwo.ui.HomeScreen
import com.example.androiddaytwo.ui.ProductDetailsScreen
import com.example.androiddaytwo.ui.theme.AndroiddaytwoTheme
import com.example.androiddaytwo.viewmodel.ShopViewModel

import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.androiddaytwo.ui.CartSidebarContent
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroiddaytwoTheme {
                val shopViewModel: ShopViewModel = viewModel()
                EcommerceApp(shopViewModel)
            }
        }
    }
}

@Composable
fun EcommerceApp(viewModel: ShopViewModel) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                CartSidebarContent(
                    cartItems = cartItems,
                    onRemoveItem = { viewModel.removeFromCart(it) },
                    onUpdateQuantity = { item, qty -> viewModel.updateCartItemQuantity(item, qty) },
                    onCheckout = {
                        viewModel.clearCart()
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    onClose = {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )
            }
        },
        gesturesEnabled = true
    ) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen(
                    viewModel = viewModel,
                    onProductClick = { productId ->
                        navController.navigate("details/$productId")
                    },
                    onCartClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }
            composable(
                route = "details/{productId}",
                arguments = listOf(navArgument("productId") { type = NavType.IntType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getInt("productId") ?: return@composable
                ProductDetailsScreen(
                    productId = productId,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onCartClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }
        }
    }
}
